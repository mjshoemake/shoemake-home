package mjs.database;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import mjs.aggregation.OrderedMap;
import mjs.database.DataLayerException;
import mjs.database.Field;
import mjs.utils.BeanUtils;
import mjs.utils.DateUtils;
import mjs.utils.FormatUtils;
import org.apache.log4j.Logger;


/**
 * The DataManager class is the class that retrieves from and sends
 * data to the database. It sends commands to the driver which then
 * handles the actual communication with JDBC and the database. <p>
 *
 * The AbstractDataManager contains the data manager helper methods.
 * These can be used directly for more control over the process. Or,
 * one of the DataManager child classes can be used instead to take
 * advantage of the simpler interface. To use directly, see a child
 * class for examples.
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractDataManager
{
   /**
    * The driver to use to access the data.
    */
   private DatabaseDriver driver = null;

   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Action".
    */
   protected Logger log = Logger.getLogger("Model");

   /**
    * The log4j logger to use when writing log messages to the "Core"
    * category.
    */
   protected Logger logCore = Logger.getLogger("Core");

   /**
    * The log4j logger to use when writing log messages to the "Core"
    * category.
    */
   protected Logger logResultSet = Logger.getLogger("ResultSet");

   /**
    * A flag to determine whether this transaction is alive or not.
    */
   private boolean transactionClosed = true;

   /**
    * The database connection that the transaction's queries will be
    * executed against.
    */
   private Connection connection = null;

   /**
    * Constructor.
    *
    * @param newDriver  Description of Parameter
    */
   public AbstractDataManager(DatabaseDriver newDriver) throws DataLayerException
   {
       setDriver(newDriver);
       if (driver == null)
           throw new DataLayerException("Unable to create database manager.  Driver is null.");
   }

   /**
    * Creates and opens a DatabaseTransaction to use to talk to the
    * database.
    *
    * @return                     Description of Return Value
    * @throws DataLayerException
    */
   public Connection open() throws DataLayerException
   {
      if (driver == null)
         throw new DataLayerException("Unable to open transaction.  DatabaseDriver is null.");

      if (! transactionClosed)
         close(false);

      try
      {
         connection = driver.getConnection();
         if (connection == null)
            log.debug("Unable to open connection.  Connection received from ConnectionManager is null.");
         else
            log.debug("Connection Open.  Received valid connection from ConnectionManager.");

         if (connection == null)
            throw new DataLayerException("Unable to open transaction.  Connection received from connection pool is null.");

         connection.setAutoCommit(false);
         transactionClosed = false;
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (SQLException se)
      {
         throw new DataLayerException("Unable to open transaction.  Failed to set Connection AutoCommit.", se);
      }
      catch (Exception e)
      {
         throw new DataLayerException("Unable to open transaction.", e);
      }

      return connection;
   }

   /**
    * Closes the transaction. If unsuccessful, the transaction
    * performs a rollback.
    *
    * @param successful           Description of Parameter
    * @throws DataLayerException
    */
   public void close(boolean successful) throws DataLayerException
   {
      // If connection is null or transactionClosed is true, exit.
      if (connection == null && ! transactionClosed)
         throw new DataLayerException("Error completing database transaction.  Illegal state.  Connection is null but transaction not showing closed.");

      if (connection == null)
         return;

      try
      {
         if (successful)
         {
            log.debug("Committing transaction...");
            connection.commit();
         }
         else
         {
            log.debug("Rolling back transaction due to unsuccessful execution...");
            connection.rollback();
         }

         close();
      }
      catch (SQLException se)
      {
         throw new DataLayerException("Error completing database transaction.", se);
      }
      catch (Exception e)
      {
         throw new DataLayerException("Error closing database connection.", e);
      }
   }

   /**
    * Closes the current transaction and releases the connection back
    * to the connection pool.
    *
    * @throws DataLayerException
    */
   public void close() throws DataLayerException
   {
      // If connection is null or transactionClosed is true, exit.
      if (connection == null && ! transactionClosed)
         throw new DataLayerException("Error completing database transaction.  Illegal state.  Connection is null but transaction not showing closed.");

      if (connection == null)
      {
         log.debug("Transaction already closed...");
         return;
      }

      try
      {
         driver.releaseConnection(connection);
         connection = null;
         transactionClosed = true;
         log.debug("Transaction closed successfully.");
      }
      catch (Exception e)
      {
         throw new DataLayerException("Error closing database connection.", e);
      }
   }

   /**
    * Populate the values for this prepared statement based on the
    * property values of the bean. This method accepts the location to
    * the mapping file to load.
    *
    * @param bean                 Object
    * @param statement            PreparedStatement
    * @param mappingFile          String
    * @param pds                  PropertyDescriptor[]
    * @throws DataLayerException
    */
   public void populatePreparedStatementValues(Object bean, PreparedStatement statement, String mappingFile, PropertyDescriptor[] pds) throws DataLayerException
   {
      try
      {
         OrderedMap mapping = driver.loadMapping(mappingFile);

         populatePreparedStatementValues(bean, statement, mapping, pds);
      }
      catch (DataLayerException ex)
      {
         throw ex;
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Error populating PreparedStatement attributes.", e);
      }
   }

   /**
    * Populate the values for this prepared statement based on the
    * property values of the bean. This method accepts the already
    * loaded mapping file in the form of a hashtable.
    *
    * @param bean                 Object
    * @param statement            PreparedStatement
    * @param mapping              Hashtable
    * @param pds                  PropertyDescriptor[]
    * @throws DataLayerException
    */
   public void populatePreparedStatementValues(Object bean, PreparedStatement statement, OrderedMap mapping, PropertyDescriptor[] pds) throws DataLayerException
   {
      try
      {
         if (pds == null || pds.length == 0)
            throw new DataLayerException("Error preparing to save bean. Bean Discriptors are missing.");

         int fieldNum = 0;

         for (int i = 0; i < pds.length; i++)
         {
            //PropertyDescriptor pd = pds[i];        	 
            Method method = pds[i].getReadMethod();
            Object value = null;
            if (method != null) {

            	Object[] args = {};
               value = method.invoke(bean, args);
            }   

            String fieldName = pds[i].getName().toLowerCase();
            Field fieldDef = (Field)(mapping.get(fieldName));
            
            
            if (fieldDef != null && ! fieldDef.getType().equalsIgnoreCase("key"))
            {
               setPreparedStatementParam(statement, pds[i], value, fieldDef, fieldNum);
               fieldNum++;
            }
            
         }
         log.debug("Populated prepared statement values.  ParamCount: " + fieldNum);
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (Exception e)
      {
         throw new DataLayerException("Failed in getInsertSql()", e);
      }
   }

   /**
    * For a specific field, set the value in the prepared statement.
    *
    * @param statement            PreparedStatement
    * @param prop                 PropertyDescriptor
    * @param value                Object
    * @param fieldDef             The new PreparedStatementParam
    * value.
    * @param index                int
    * @throws DataLayerException
    */
   private void setPreparedStatementParam(PreparedStatement statement, PropertyDescriptor prop, Object value, Field fieldDef, int index) throws DataLayerException
   {
      try
      {
         if (fieldDef == null)
             throw new DataLayerException("Error populating PreparedStatement attributes.  FieldDefinition is null.");

         // Statement param indexes start with 1, not 0.
         index++;

         String fieldName = prop.getName().toLowerCase();
         String fieldType = fieldDef.getType();

         if (fieldType.equals("key") && value == null)
         {
            //statement.setString(index, "");
            logCore.debug("   " + fieldName + " (" + fieldType + ") = ''  IGNORED.  Should get next PK as part of insert statement.");
            return;
         }

         if (value != null)
            logCore.debug("   " + fieldName + " (" + fieldType + ") = '" + value.toString() + "'");
         else
            logCore.debug("   " + fieldName + " (" + fieldType + ") = null");

         if (fieldType.equals("string"))
         {
            // value is a String.
            if (value != null)
            {
               String temp = FormatUtils.parseString(value.toString(), fieldDef);

               statement.setString(index, temp);
            }
            else
               statement.setNull(index, java.sql.Types.VARCHAR);
         }
         else if (fieldType.equals("key"))
         {
            // value is a primary key.
            //if (value != null)
               //statement.setString(index, value.toString());
            //else
            //   statement.setString(index, "");
         }
         else if (fieldType.equals("date"))
         {
            java.util.Date date = null;

            if (value != null)
            {
               if (String.class.isAssignableFrom(prop.getPropertyType()))
                  date = FormatUtils.parseDate(value.toString(), fieldDef);
               else if (java.util.Date.class.isAssignableFrom(prop.getPropertyType()))
                  date = (java.util.Date)value;
               else
                  throw new DataLayerException("Error parsing value for field " + fieldName + ".  Type expected to be Date or String.");
               statement.setTimestamp(index, new Timestamp(date.getTime()));
            }
            else
               statement.setNull(index, java.sql.Types.TIMESTAMP);
         }
         else if (fieldType.equals("long"))
         {
            Long num = null;

            if (value != null)
            {
               if (String.class.isAssignableFrom(prop.getPropertyType()))
                  num = FormatUtils.parseLong(value.toString());
               else if (Long.class.isAssignableFrom(prop.getPropertyType()))
                  num = (Long)value;
               else
                  throw new DataLayerException("Error parsing value for field " + fieldName + ".  Type expected to be Long or String.");
               statement.setLong(index, num.longValue());
            }
            else
               statement.setNull(index, java.sql.Types.NUMERIC);
         }
         else if (fieldType.equals("int"))
         {
            Integer num = null;

            if (value != null)
            {
               if (String.class.isAssignableFrom(prop.getPropertyType())) {
                  if (value.toString().trim().equals("")) {
                     statement.setNull(index, java.sql.Types.NUMERIC);
                  } else {
                     num = FormatUtils.parseInteger(value.toString());
                     statement.setInt(index, num.intValue());
                  }
               } else if (Integer.class.isAssignableFrom(prop.getPropertyType())) {
                  num = (Integer)value;
                  statement.setInt(index, num.intValue());
               } else
                  throw new DataLayerException("Error parsing value for field " + fieldName + ".  Type expected to be Integer or String.");
            }
            else
               statement.setNull(index, java.sql.Types.NUMERIC);
         }
         else if (fieldType.equals("boolean"))
         {
            if (value != null)
            {
               Boolean item = null;

               if (String.class.isAssignableFrom(prop.getPropertyType()))
                  item = FormatUtils.parseBoolean(value.toString(), fieldDef);
               else if (Boolean.class.isAssignableFrom(prop.getPropertyType()))
                  item = (Boolean)value;
               else
                  throw new DataLayerException("Error parsing value for field " + fieldName + ".  Type expected to be Boolean or String.");

               if (item.booleanValue())
                  statement.setString(index, "Y");
               else
                  statement.setString(index, "N");
            }
            else
               statement.setNull(index, java.sql.Types.CHAR);
         }
         else if (fieldType.equals("double") || fieldType.equals("float"))
         {
            if (value != null)
            {
               BigDecimal item = null;

               if (String.class.isAssignableFrom(prop.getPropertyType()))
                  item = FormatUtils.parseBigDecimal(value.toString(), fieldDef);
               else if (BigDecimal.class.isAssignableFrom(prop.getPropertyType()))
                  item = (BigDecimal)value;
               else
                  throw new DataLayerException("Error parsing value for field " + fieldName + ".  Type expected to be BigDecimal or String.");
               statement.setBigDecimal(index, item);
            }
            else
               statement.setNull(index, java.sql.Types.NUMERIC);
         }
         else
         {
            throw new DataLayerException("Error populating PreparedStatement attributes.  Unrecognized data type (" + value.getClass() + ") for field " + fieldDef.getName() + ".");
         }
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (java.lang.Exception e)
      {
    	 if (value == null)
             throw new DataLayerException("Error populating PreparedStatement attribute " + fieldDef.getName() + " with value null.", e);
    	 else    		 
             throw new DataLayerException("Error populating PreparedStatement attribute " + fieldDef.getName() + " with value " + value.toString() + ".", e);
      }
   }

   /**
    * Generates the SQL to use to store a bean of this class in the
    * database. This query should be used with prepared statements
    * only.
    *
    * @param table                String
    * @param mappingFile          String
    * @param type                 Class
    * @param keyAlreadyPopulated  Description of Parameter
    * @return                     String
    * @throws DataLayerException
    */
   public String getInsertSqlForPreparedStatement(String table, String mappingFile, Class type) throws DataLayerException
   {
      try
      {
         if (driver == null)
            throw new DataLayerException("Error populating PreparedStatement attributes.  Driver is null.");

         log.debug("Loading mapping file...");

         OrderedMap mapping = driver.loadMapping(mappingFile);

         return getInsertSqlForPreparedStatement(table, mapping, type);
      }
      catch (DataLayerException ex)
      {
         throw ex;
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Error populating PreparedStatement attributes.", e);
      }
   }
   
   /**
    * Generates the SQL to use to store a bean of this class in the
    * database. This query should be used with prepared statements
    * only.
    *
    * @param table                   Description of Parameter
    * @param mapping                 Description of Parameter
    * @param type                    Description of Parameter
    * @param keyAlreadyPopulated     Description of Parameter
    * @return                        The value of the
    * InsertSqlForPreparedStatement property.
    * @exception DataLayerException  Description of Exception
    */
   public String getInsertSqlForPreparedStatement(String table, OrderedMap mapping, Class type) throws DataLayerException
   {
      //Object[] args = {};
      //Object newobj = null;
      StringBuffer sql = new StringBuffer("insert into ");

      sql.append(table);
      sql.append(" (");

      try
      {
         PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(type, mapping);

         if (pds == null || pds.length == 0)
            throw new DataLayerException("Error creating Insert SQL. Bean Discriptors are missing.");
         if (mapping == null)
            throw new DataLayerException("Error creating Insert SQL. Mapping is null.");

         boolean first = true;

         for (int i = 0; i < pds.length; i++)
         {
            String fieldName = pds[i].getName();
            Field fieldDef = (Field)(mapping.get(fieldName.toLowerCase()));
            if (! fieldDef.getType().toLowerCase().equals("key"))
            {
                if (! first)
                   sql.append(", ");
                else
                   first = false;
                sql.append(pds[i].getName());
            }    
         }
         sql.append(") values (");

         first = true;
         int paramCount = 0;
         for (int i = 0; i < pds.length; i++)
         {
            String fieldName = pds[i].getName();
            Field fieldDef = (Field)(mapping.get(fieldName.toLowerCase()));

            if (! fieldDef.getType().toLowerCase().equals("key"))
            {
                if (! first)
                    sql.append(", ");
                 else
                    first = false;

               // Not a primary key.
               sql.append("?");
               paramCount++;
            }
         }

         sql.append(") ");
         logCore.debug("Insert SQL: " + sql.toString() + "  SQL paramCount: " + paramCount);
      }
      catch (Exception e)
      {
         throw new DataLayerException("Failed in getInsertSqlGeneric()", e);
      }

      return sql.toString();
   }
   
   /**
    * Generate the SQL to use to update a bean of this class in the
    * database. This query should be used with prepared statements
    * only.
    *
    * @param table                   Description of Parameter
    * @param mapping                 Description of Parameter
    * @param type                    Description of Parameter
    * @param whereClause             Description of Parameter
    * @return                        The value of the
    * UpdateSqlForPreparedStatement property.
    * @exception DataLayerException  Description of Exception
    */
   protected String getUpdateSqlForPreparedStatement(String table, OrderedMap mapping, Class type, String whereClause) throws DataLayerException
   {
      //Method method = null;
      //Object[] args = {};
      //Object newobj = null;
      StringBuffer sql = new StringBuffer("update ");

      sql.append(table);
      sql.append(" set ");

      try
      {
         PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(type, mapping);

         if (pds == null || pds.length == 0)
            throw new DataLayerException("Error creating Insert SQL. Bean Discriptors are missing.");

         boolean first = true;

         for (int i = 0; i < pds.length; i++)
         {
        	String name = pds[i].getName();
        	Field fielddef = (Field)mapping.get(name);
        	if (! fielddef.getType().equals("key")) {
                if (! first)
                    sql.append(", ");
                 else
                    first = false;

                 sql.append(pds[i].getName());
                 sql.append("=?");
        	}
         }
         sql.append(" ");
         sql.append(whereClause);
      }
      catch (Exception e)
      {
         throw new DataLayerException("Failed in getInsertSqlGeneric()", e);
      }
      log.debug("Update sql: " + sql.toString());
      return sql.toString();
   }

   /**
    * Generate the SQL statement used to load beans of the specified
    * type from the database.
    *
    * @param table                Description of Parameter
    * @param type                 Description of Parameter
    * @param mapping              Description of Parameter
    * @param whereClause
    * @return                     The value of the LoadSql property.
    * @throws DataLayerException
    */
   protected String getLoadSql(String table, Class type, OrderedMap mapping, String whereClause) throws DataLayerException
   {
      StringBuffer sql = new StringBuffer("select ");

      try
      {
         PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(type, mapping);

         if (pds == null || pds.length == 0)
         {
            if (type == null)
               throw new DataLayerException("Error creating Insert SQL. Bean Discriptors are missing.  Type is NULL.");
            else
               throw new DataLayerException("Error creating Insert SQL. Bean Discriptors are missing.  Type = " + type.getName());
         }

         boolean first = true;

         for (int i = 0; i < pds.length; i++)
         {
            if (! first)
               sql.append(", ");
            else
               first = false;

            sql.append(pds[i].getName());
         }

         sql.append(" from ");
         sql.append(table);
         sql.append(" ");
         sql.append(whereClause);
         return sql.toString();
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Unable to generate SQL to load data from the database.", e);
      }
   }

   /**
    * Generate the SQL statement used to count rows in the specified
    * table using the specified where clause.
    *
    * @param table                String
    * @param whereClause          String
    * @return                     The value of the CountSql property.
    * @throws DataLayerException
    */
   protected String getCountSql(String table, String whereClause)
   {
      StringBuffer sql = new StringBuffer("select count(*) from ");

      sql.append(table);
      sql.append(" ");
      sql.append(whereClause);
      return sql.toString();
   }

   /**
    * The driver to use to access the data.
    *
    * @return   mjs.core.database.AbstractDatabaseDriver
    */
   public DatabaseDriver getDriver()
   {
      return driver;
   }

   /**
    * The driver to use to access the data.
    *
    * @param newDriver  mjs.core.database.AbstractDatabaseDriver
    */
   public void setDriver(DatabaseDriver newDriver)
   {
      driver = newDriver;
   }

   /**
    * Wrap the provided Object with single quotes. Single quotes that

    * are present before the conversion are escaped with another
    * single quote.
    *
    * @param obj  the Object to wrap.
    * @return     the quoted Object or null if the initial Object was
    * null.
    */
   public final static Object quoteStr(Object obj)
   {
      if (obj == null)
      {
         return null;
      }

      return "'" + quoteTicks((String)obj) + "'";
   }

   /**
    * Wraps an Object with single quotes and % signs to use in partial
    * query requests.Single quotes that are present before the
    * conversion are escaped with another single quote.
    *
    * @param obj  Object to be wrapped
    * @return     the wrapped Object or null if the initial Object was
    * null.
    */
   public final static Object quoteStrLike(Object obj)
   {
      if (obj == null)
      {
         return null;
      }

      return "'%" + quoteTicks((String)obj) + "%'";
   }

   /**
    * Wrap the provided Object with single quotes. A percent siqn is
    * also appended to allow partial search queries. Single quotes
    * that are present before the conversion are removed.
    *
    * @param obj  the Object to wrap.
    * @return     the quoted Object or null if the initial Object was
    * null.
    */
   public final static Object quoteStrLikeStart(Object obj)
   {
      if (obj == null)
      {
         return null;
      }

      return "'" + quoteTicks((String)obj) + "%'";
   }

   /**
    * Searches an object for single quotes and escapes them with
    * another single quote.
    *
    * @param st  Description of Parameter
    * @return    the escaped Object.
    */
   public final static String quoteTicks(String st)
   {

      if (st == null || st.indexOf("'") == -1)
      {
         return st;
      }

      StringBuffer see = new StringBuffer(st);
      int i = 0;

      while (i < see.length())
      {
         char c = see.charAt(i);

         if (c != '\'')
         {
            i++;
            continue;
         }

         see.insert(i, '\'');
         i += 2;
      }
      return see.toString();
   }

   /**
    * The database connection to use when talking to the database.
    *
    * @return   The value of the Connection property.
    */
   public Connection getConnection()
   {
      return connection;
   }

   /**
    * The database connection to use when talking to the database.
    *
    * @param value  The new Connection value.
    */
   public void setConnection(Connection value)
   {
      connection = value;
   }

   /**
    * Loads the mapping file at the specified location and stores the
    * data in a Hashtable where the key is the field name and the
    * value .
    *
    * @param mappingFile             String
    * @return                        Hashtable
    * @exception DataLayerException  Description of Exception
    */
   public OrderedMap loadMapping(String mappingFile) throws DataLayerException
   {
      try
      {
         if (mappingFile == null)
            throw new DataLayerException("Error loading mapping file.  Mapping file path is null.");

         log.debug("Loading mapping file...");
         return driver.loadMapping(mappingFile);
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Error loading mapping file.", e);
      }
   }

   /**
    * Convert an attribute to it's string representation to be used
    * with an insert or update SQL statement.
    *
    * @param prop                 PropertyDescriptor
    * @param value                Object
    * @param fieldDef             FieldDefinition
    * @return                     String
    * @throws DataLayerException
    */
   protected String attributeToString(PropertyDescriptor prop, Object value, Field fieldDef) throws DataLayerException
   {
      try
      {
         // Statement param indexes start with 1, not 0.
         if (logCore.isDebugEnabled())
         {
            logCore.debug("Property:");
            logCore.debug("           Property Name: " + prop.getDisplayName());
            logCore.debug("  prop.getPropertyType(): " + prop.getPropertyType().getName());
            logCore.debug("                   Value: " + value.toString());
            logCore.debug("        value.getClass(): " + value.getClass().getName());
            logCore.debug("           FieldDef Name: " + fieldDef.getName());
            logCore.debug("           FieldDef Type: " + fieldDef.getType());
            logCore.debug("         FieldDef Format: " + fieldDef.getFormat());
         }

         String fieldName = prop.getName().toLowerCase();
         String fieldType = fieldDef.getType();
         String result = null;

         if (fieldType.equals("key") && value == null)
            return "";

         if (fieldType.equals("string"))
         {
            // value is a String.
            result = "'" + FormatUtils.parseString(value.toString(), fieldDef) + "'";
            logCore.debug("   " + fieldName + " (" + fieldType + ") = '" + value.toString() + "'  -->  " + result);
            return result;
         }
         else if (fieldType.equals("key"))
         {
            // value is a primary key.
            logCore.debug("   " + fieldName + " (" + fieldType + ") = '" + value.toString() + "'  -->  " + value.toString());
            return value.toString();
         }
         else if (fieldType.equals("date"))
         {
            if (value != null)
            {
               java.util.Date date = null;

               if (String.class.isAssignableFrom(prop.getPropertyType()))
                  date = FormatUtils.parseDate(value.toString(), fieldDef);
               else if (java.util.Date.class.isAssignableFrom(prop.getPropertyType()))
                  date = (java.util.Date)value;
               else
                  throw new DataLayerException("Error parsing value for field " + fieldName + ".  Type expected to be Date or String.");
               String out = DateUtils.dateToString(date, "yyyy-MM-dd HH:mm:ss");

               result = "TO_DATE('" + out + "', 'YYYY-MM-DD HH24:MI:SS')";
            }
            else
               result = "''";
            logCore.debug("   " + fieldName + " (" + fieldType + ") = '" + value.toString() + "'  -->  " + result);
            return result;
         }
         else if (fieldType.equals("long"))
         {
            Long num = null;

            if (String.class.isAssignableFrom(prop.getPropertyType()))
               num = FormatUtils.parseLong(value.toString());
            else if (Long.class.isAssignableFrom(prop.getPropertyType()))
               num = (Long)value;
            else
               throw new DataLayerException("Error parsing value for field " + fieldName + ".  Type expected to be Long or String.");
            logCore.debug("   " + fieldName + " (" + fieldType + ") = '" + value.toString() + "'  -->  " + num.toString());
            return num.toString();
         }
         else if (fieldType.equals("int"))
         {
            Integer num = null;

            if (String.class.isAssignableFrom(prop.getPropertyType()))
               num = FormatUtils.parseInteger(value.toString());
            else if (value instanceof Integer)
               num = (Integer)value;
            else
            {
               log.debug("value.getClass(): " + value.getClass().getName());
               throw new DataLayerException("Error parsing value for field " + fieldName + ".  Type expected to be Integer or String.");
            }
            logCore.debug("   " + fieldName + " (" + fieldType + ") = '" + value.toString() + "'  -->  " + num.toString());
            return num.toString();
         }
         else if (fieldType.equals("boolean"))
         {
            Boolean item = null;

            if (String.class.isAssignableFrom(prop.getPropertyType()))
               item = FormatUtils.parseBoolean(value.toString(), fieldDef);
            else if (value instanceof Boolean)
               item = (Boolean)value;
            else
               throw new DataLayerException("Error parsing value for field " + fieldName + ".  Type expected to be Boolean or String.");

            if (item.booleanValue())
               result = "'Y'";
            else
               result = "'N'";
            logCore.debug("   " + fieldName + " (" + fieldType + ") = '" + value.toString() + "'  -->  " + result);
            return result;
         }
         else if (fieldType.equals("double") || fieldType.equals("float"))
         {
            BigDecimal item = null;

            if (String.class.isAssignableFrom(prop.getPropertyType()))
               item = FormatUtils.parseBigDecimal(value.toString(), fieldDef);
            else if (BigDecimal.class.isAssignableFrom(prop.getPropertyType()))
               item = (BigDecimal)value;
            else
               throw new DataLayerException("Error parsing value for field " + fieldName + ".  Type expected to be BigDecimal or String.");
            logCore.debug("   " + fieldName + " (" + fieldType + ") = '" + value.toString() + "'  -->  '" + item.toString() + "'");
            return item.toString();
         }
         else
         {
            throw new DataLayerException("Error converting attribute to string.  Unrecognized data type (" + value.getClass() + ").");
         }
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Error converting attribute to string.", e);
      }
   }

   /**
    * Generate the SQL to use to store this bean in the database.
    *
    * @param table                String
    * @param mapping              Description of Parameter
    * @param bean                 Object
    * @param keyAlreadyPopulated  Description of Parameter
    * @return                     String
    * @throws DataLayerException
    */
/*   
   protected String getInsertSql(String table, Hashtable mapping, Object bean, boolean keyAlreadyPopulated) throws DataLayerException
   {
      Method method = null;
      Object[] args = {};
      Object newobj = null;
      StringBuffer sql = new StringBuffer("insert into ");

      sql.append(table);
      sql.append(" (");

      try
      {
         PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());

         if (pds == null || pds.length == 0)
            throw new DataLayerException("Error creating Insert SQL. Bean Discriptors are missing.");

         boolean first = true;

         // Append field list.
         for (int i = 0; i < pds.length; i++)
         {
            if (mapping.containsKey(pds[i].getName().toLowerCase()))
            {
               String fieldName = pds[i].getName().toLowerCase();
               Field fieldDef = (Field)(mapping.get(fieldName));


               if (fieldDef.getType().equalsIgnoreCase("key") && ! keyAlreadyPopulated)
               {
                  // Key is not already populated.  Get the next value in
                  // the insert statement.
                  if (! first)
                     sql.append(", ");
                  else
                     first = false;
                  sql.append(pds[i].getName());
               }
               else
               {
                  // Do NOT put in field name if field value is NULL...
                  method = pds[i].getReadMethod();
                  newobj = method.invoke(bean, args);
                  if (newobj != null)
                  {
                     if (! first)
                        sql.append(", ");
                     else
                        first = false;
                     sql.append(pds[i].getName());
                  }
               }
            }
         }
         sql.append(") values (");

         first = true;
         // Append values.
         for (int i = 0; i < pds.length; i++)
         {
            if (mapping.containsKey(pds[i].getName().toLowerCase()))
            {
               String fieldName = pds[i].getName().toLowerCase();
               Field fieldDef = (Field)(mapping.get(fieldName));


               if (fieldDef.getType().equalsIgnoreCase("key") && ! keyAlreadyPopulated)
               {
                  // Key is not already populated.  Get the next value in
                  // the insert statement.
                  if (! first)
                     sql.append(", ");
                  else
                     first = false;

                  String sequence = fieldDef.getSequence();

                  sql.append(sequence);
                  sql.append(".NEXTVAL");
               }
               else
               {
                  // Process the non-key fields.
                  method = pds[i].getReadMethod();
                  newobj = method.invoke(bean, args);
                  if (newobj != null)
                  {
                     if (! first)
                        sql.append(", ");
                     else
                        first = false;
                     sql.append(attributeToString(pds[i], newobj, fieldDef));
                  }
               }
            }
         }

         sql.append(") ");

      }
      catch (Exception e)
      {
         throw new DataLayerException("Failed in getInsertSql().", e);
      }

      return sql.toString();
   }
*/
}
