package mjs.database;

import java.beans.PropertyDescriptor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import mjs.aggregation.OrderedMap;
import mjs.utils.BeanUtils;
import mjs.utils.LogUtils;
import mjs.utils.StringUtils;

/**
 * The DataManager class is the class that retrieves from 
 * and sends data to the database.  It sends commands to
 * the driver which then handles the actual communication 
 * with JDBC and the database.
 */
@SuppressWarnings("rawtypes")
public class DataManager extends AbstractDataManager
{

   /**
    * The SQL Statement used to generate the ResultSet.  Hanging on to this
    * so we can close it when the close() method executes.  When the statement
    * is closed, the result set is closed automatically.  Hanging on to the
    * statement allows the result set to be processed externally from the
    * method in which it was created.
    */
   Statement stmt = null;
   
   /**
    * Constructor.
    */
   public DataManager(DatabaseDriver driver) 
   {
      super(driver);
   }

   /**
    * Closes the transaction. If unsuccessful, the transaction
    * performs a rollback.
    * @throws DataLayerException
    */
   public void close(boolean successful) throws DataLayerException
   {
      try
      {
         super.close(successful);
         if (stmt != null)
            stmt.close();
      }
      catch (Exception e)
      {
         throw new DataLayerException("Error closing SQL statement.", e);
      }
   }

   /**
    * Load this list from the database.
    * @param table          The table to load from.
    * @param pageSize       The size of the page (default is 10).
    * @param maxRecords     The max # of records (default is 500).
    * @param whereClause    The where clause to use for the query.
    * @param globalForward  The global forward to use for to get back to the current JSP page.
    *                       This is used by the pagination actions.
    * @return PaginatedList
    * @throws DataLayerException
    */
   public PaginatedList loadList(String sql, Class type, String mappingFile, int pageSize, int maxRecords, String globalForward) throws DataLayerException
   {
      Statement stmt = null;
      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         log.debug("");
         log.debug("LOADING list from the database:");
         log.debug("   sql="+sql);
         log.debug("   bean type="+StringUtils.removePackage(type.getName()));
         log.debug("   mappingFile="+mappingFile);
         DatabaseDriver driver = getDriver();

         if (getConnection() == null)
            throw new DataLayerException("Error loading data using SQL statement.  The open() method was never called.");

         // Load the mapping file.
         OrderedMap mapping = driver.loadMapping(mappingFile);  
         stmt = getConnection().createStatement(); 
         log.info("LoadList SQL: "+sql);
         ResultSet resultSet = stmt.executeQuery(sql);
         PaginatedList list = new PaginatedList(type, pageSize, maxRecords, globalForward);
         
         // Populate the list from the result set.
         driver.populateBeanList(resultSet, mapping, list);
         return list;
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Unable to load data using SQL statement.", e);
      }
      finally
      {
         try
         {
            if (stmt != null)
               stmt.close();
         }
         catch (Exception e)
         {
            throw new DataLayerException("Unable to load data using SQL statement.", e);
         }
      }
   }

   /**
    * Load this list from the database.
    * @param table          The table to load from.
    * @param pageSize       The size of the page (default is 10).
    * @param maxRecords     The max # of records (default is 500).
    * @param whereClause    The where clause to use for the query.
    * @param globalForward  The global forward to use for to get back to the current JSP page.
    *                       This is used by the pagination actions.
    * @return PaginatedList
    * @throws DataLayerException
    */
   public PaginatedList loadList(String table, Class type, String mappingFile, String whereClause, int pageSize, int maxRecords, String globalForward) throws DataLayerException
   {
      Statement stmt = null;
      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         log.debug("");
         log.debug("LOADING list from the database:");
         log.debug("   table="+table);
         log.debug("   bean type="+StringUtils.removePackage(type.getName()));
         log.debug("   mappingFile="+mappingFile);
         log.debug("   whereClause="+whereClause);
         DatabaseDriver driver = getDriver();

         if (getConnection() == null)
            throw new DataLayerException("Error loading data from the table "+table+".  The open() method was never called.");

         // Load the mapping file.
         OrderedMap mapping = driver.loadMapping(mappingFile);  
            
         stmt = getConnection().createStatement(); 
         String sql = getLoadSql(table, type, mapping, whereClause);
         log.info("LoadList SQL: "+sql);
         ResultSet resultSet = stmt.executeQuery(sql);
         PaginatedList list = new PaginatedList(type, pageSize, maxRecords, globalForward);
         
         // Populate the list from the result set.
         driver.populateBeanList(resultSet, mapping, list);
         return list;
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Unable to load data from the table "+table+".", e);
      }
      finally
      {
         try
         {
            if (stmt != null)
               stmt.close();
         }
         catch (Exception e)
         {
            throw new DataLayerException("Unable to load data from the table "+table+".", e);
         }
      }
   }

   /**
    * Executes the specified SQL query and returns a ResultSet object.
    * @param sql String
    * @return ResultSet
    * @throws DataLayerException
    */
   public ResultSet executeSQL(String sql) throws DataLayerException
   {
      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         log.debug("");
         log.debug("LOADING list from the database:");
         log.info("   ExecuteSQL SQL="+sql);
         //DatabaseDriver driver = getDriver();

         if (getConnection() == null)
            throw new DataLayerException("Error loading data using SQL.  The open() method was never called.");

         // NOTE:  The statement will be closed when the DataManager.close() 
         //        method is called.
         stmt = getConnection().createStatement(); 
         return stmt.executeQuery(sql);
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Unable to load data using SQL.", e);
      }
   }

   /**
    * Load this bean from the database.
    * @param whereClause    The search criteria.
    * @return Object
    * @throws DataLayerException
    */
   public Object loadBean(String table, Class type, String mappingFile, String whereClause) throws DataLayerException
   {
      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         return loadBean(type.newInstance(), table, type, mappingFile, whereClause);
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (Exception e)
      {
         throw new DataLayerException("Error populating bean (probably error during instantiation of "+type.getName()+".", e);
      }
   }   
   
   /**
    * Load this bean from the database.
    * @param whereClause    The search criteria.
    * @return Object
    * @throws DataLayerException
    */
   public Object loadBean(Object bean, String table, Class type, String mappingFile, String whereClause) throws DataLayerException
   {
      Statement stmt = null;
      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         log.debug("");
         log.debug("LOADING bean from the database:");
         log.debug("   table="+table);
         log.debug("   bean type="+StringUtils.removePackage(type.getName()));
         log.debug("   mappingFile="+mappingFile);
         log.debug("   whereClause="+whereClause);
         DatabaseDriver driver = getDriver();

         if (getConnection() == null)
            throw new DataLayerException("Error loading data from the table "+table+".  The open() method was never called.");

         // Load the mapping file.
         OrderedMap mapping = driver.loadMapping(mappingFile);  
         
         stmt = getConnection().createStatement(); 
         String sql = getLoadSql(table, type, mapping, whereClause);
         log.info("LoadBean SQL: "+sql);
         ResultSet rs = stmt.executeQuery(sql);

         // Populate the bean from the result set.
         driver.populateBean(rs, mapping, type, bean);
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Unable to load object from database table "+table+".", e);
      }
      finally
      {
         try
         {
            if (stmt != null)
               stmt.close();
         }
         catch (Exception e)
         {
            throw new DataLayerException("Unable to load data from the table "+table+".", e);
         }
      }
      
      return bean;
   }

   /**
    * Count the number of rows in the result set.
    * @param table          The table to load from.
    * @param whereClause    The where clause to use for the query.
    * @return int
    * @throws DataLayerException
    */
   public int countRows(String table, String whereClause) throws DataLayerException
   {
      Statement stmt = null;
      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         log.debug("");
         log.debug("Counting rows from the database:");
         log.debug("   table="+table);
         log.debug("   whereClause="+whereClause);
         //DatabaseDriver driver = getDriver();

         if (getConnection() == null)
            throw new DataLayerException("Error counting rows from the table "+table+".  The open() method was never called.");

         stmt = getConnection().createStatement(); 
         String sql = getCountSql(table, whereClause);
         log.info("CountRows SQL: "+sql);
         ResultSet resultSet = stmt.executeQuery(sql);
         resultSet.next();
         int result = resultSet.getInt(1);
         return result;
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Unable to count rows from the table "+table+".", e);
      }
      finally
      {
         try
         {
            if (stmt != null)
               stmt.close();
         }
         catch (Exception e)
         {
            throw new DataLayerException("Unable to count rows from the table "+table+".", e);
         }
      }
   }

   /**
    * Delete all rows in the table that was specified in the class 
    * constructor that match the specified where clause.
    * @param whereClause
    * @throws DataLayerException
    */
   public void delete(String table, String whereClause) throws DataLayerException
   {
      String deleteSQL = null;
      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         log.debug("");
         log.debug("DELETING rows from the database:");
         log.debug("   table="+table);
         log.debug("   whereClause="+whereClause);

         if (getConnection() == null)
            throw new DataLayerException("Error deleting data from the table "+table+".  The open() method was never called.");
         
         if (whereClause == null)
            throw new DataLayerException("Unable to delete from database.  Where clause is null.");

         deleteSQL = "delete from " + table + " " + whereClause; 
         log.info("Delete SQL: "+deleteSQL);
         getDriver().executeStatement(deleteSQL, getConnection());
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Error deleting rows from table "+table+".", e);
      }
   }

   /**
    * Save this bean to the database table specified in the
    * constructor.
    * @param table String
    * @param mappingFile String
    * @param bean Object
    * @return String  The primary key value for the inserted row.
    * @throws DataLayerException
    */
   public String insertBean(String table, String mappingFile, Object bean) throws DataLayerException
   {
      return insertBean(table, mappingFile, bean, false);    
   }
   
   /**
    * Save this bean to the database table specified in the
    * constructor.
    * @param table String
    * @param mappingFile String
    * @param bean Object
    * @return String  The primary key value for the inserted row.
    * @throws DataLayerException
    */
   public String insertBean(String table, String mappingFile, Object bean, boolean keyIncrementedDuringInsert) throws DataLayerException
   {
      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         return insertBeanUsingPreparedStatement(table, mappingFile, bean);
      }
      catch (DataLayerException e)
      {
         throw e;
      }
   }       
   
   /**
    * Save this bean to the database table specified in the
    * constructor.
    * @param table String
    * @param mappingFile String
    * @param bean Object
    * @return String  The primary key value for the inserted row.
    * @throws DataLayerException
    */
/*   
   private String insertBeanUsingSQL(String table, String mappingFile, Object bean, boolean keyIncrementedDuringInsert) throws DataLayerException
   {
      String insertSQL = null;
      String primaryKeyValue = null;
      
      if (bean == null)
         throw new DataLayerException("Unable to save bean to the database.  Bean is null.");

      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         log.debug("");
         log.debug("INSERTING bean to the database:");
         log.debug("   table="+table);
         log.debug("   bean type="+StringUtils.removePackage(bean.getClass().getName()));
         log.debug("   mappingFile="+mappingFile);
         DatabaseDriver driver = getDriver();

         if (getConnection() == null)
            throw new DataLayerException("Error inserting data into the table "+table+".  The open() method was never called.");
         
         // Load the mapping file.
         Hashtable mapping = driver.loadMapping(mappingFile);  
         PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());

         if (! keyIncrementedDuringInsert)
         {
            // Get the next primary key value.  Also updates the bean with the primary
            // key value so it can be used in the insert statement.
            primaryKeyValue = getPrimaryKeyForInsertStatement(table, mapping, bean, pds);
         }   

         // Create prepared statement.
         insertSQL = getInsertSql(table, mapping, bean, ! keyIncrementedDuringInsert);
         log.info("InsertBean SQL: "+insertSQL);
         getDriver().executeStatement(insertSQL, getConnection());

         if (logResultSet.isDebugEnabled())
         {
            String[] lines = LogUtils.dataToStrings(bean);
            for (int C=0; C <= lines.length-1; C++)
               logResultSet.debug("   "+lines[C]);
         }

         return primaryKeyValue;
      }
      catch (java.lang.Exception e)
      {
         if (e.getMessage().startsWith("ORA-01747: invalid user.table.column,"))
            throw new DataLayerException(e.getMessage()+"  Make sure the column names are correct and NOT Oracle reserved words.");
         else
            throw new DataLayerException("Unable to save bean to the database.", e);
      }
   }       
*/
   
   /**
    * Save this bean to the database table specified in the
    * constructor.
    * @param table String
    * @param mappingFile String
    * @param bean Object
    * @return String  The primary key value for the inserted row.
    * @throws DataLayerException
    */
   private String insertBeanUsingPreparedStatement(String table, String mappingFile, Object bean) throws DataLayerException
   {
      String insertSQL = null;
      String primaryKeyValue = null;
      
      if (bean == null)
         throw new DataLayerException("Unable to save bean to the database.  Bean is null.");

      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         log.debug("");
         log.debug("INSERTING bean to the database:");
         log.debug("   table="+table);
         log.debug("   bean type="+StringUtils.removePackage(bean.getClass().getName()));
         log.debug("   mappingFile="+mappingFile);
         DatabaseDriver driver = getDriver();

         if (getConnection() == null)
            throw new DataLayerException("Error deleting data from the table "+table+".  The open() method was never called.");
         
         // Load the mapping file.
         OrderedMap mapping = driver.loadMapping(mappingFile);  
         PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());

         // Create prepared statement.
         insertSQL = getInsertSqlForPreparedStatement(table, mapping, bean.getClass());
         log.info("InsertBean SQL: "+insertSQL);
         PreparedStatement statement = getConnection().prepareStatement(insertSQL);

         // Bind the values of the insert statement.
         populatePreparedStatementValues(bean, statement, mapping, pds);

         // Execute the statement.
         statement.executeUpdate();
         
         log.debug("Inserted Bean:");
         String[] lines = LogUtils.dataToStrings(bean);
         for (int C=0; C <= lines.length-1; C++)
            log.debug("   "+lines[C]);
         
         return primaryKeyValue;
      }
      catch (java.lang.Exception e)
      {
         log.error("SQL: "+insertSQL);
         throw new DataLayerException("Unable to save bean to the database.", e);
      }
   }       
   
   /**
    * Save each bean in this list to the specified database table.
    * @param table String
    * @param mappingFile String
    * @param list PaginatedList
    * @throws DataLayerException
    */
/*
   public void insertBeanList(String table, String mappingFile, PaginatedList list) throws DataLayerException
   {
      insertBeanList(table, mappingFile, list.getEntireList());   
   }
*/
   /**
    * Save each bean in this list to the specified database table.
    * @param table String
    * @param mappingFile String
    * @param list ArrayList
    * @throws DataLayerException
    */
/*   
   public void insertBeanList(String table, String mappingFile, ArrayList list) throws DataLayerException
   {
      insertBeanListUsingPreparedStatement(table, mappingFile, list, false);
   }   
*/
   
   /**
    * Save each bean in this list to the specified database table.
    * @param table String
    * @param mappingFile String
    * @param list ArrayList
    * @throws DataLayerException
    */
/*   
   private void insertBeanListUsingPreparedStatement(String table, String mappingFile, ArrayList list, boolean keyIncrementedDuringInsert) throws DataLayerException
   {
      String insertSQL = null;
      //String primaryKeyValue = null;
      Object bean = null;
      
      if (list == null)
         throw new DataLayerException("Unable to save bean to the database.  List is null.");

      if (list.size() == 0)
      {
         log.debug("List is empty.  Nothing to insert.  Aborting process.");
         return;
      }   
      
      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         Class type = list.get(0).getClass();
         log.debug("");
         log.debug("INSERTING bean list to the database:");
         log.debug("   table="+table);
         log.debug("   bean type="+StringUtils.removePackage(type.getName()));
         log.debug("   mappingFile="+mappingFile);
         DatabaseDriver driver = getDriver();

         if (getConnection() == null)
            throw new DataLayerException("Error deleting data from the table "+table+".  The open() method was never called.");
         
         // Load the mapping file.
         Hashtable mapping = driver.loadMapping(mappingFile);  
         PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(type);

         // Create prepared statement.
         insertSQL = getInsertSqlForPreparedStatement(table, mapping, type, ! keyIncrementedDuringInsert).toLowerCase();
         log.info("InsertBeanList SQL: "+insertSQL);
         PreparedStatement statement = getConnection().prepareStatement(insertSQL);

         for (int C=0; C <= list.size()-1; C++)
         {
            bean = list.get(C);            
            
            if (! bean.getClass().getName().equals(type.getName()))
               throw new DataLayerException("Unable to save bean list to the database.  All objects in the list must be the same type ("+type.getName()+" <> "+bean.getClass().getName()+").");

            if (! keyIncrementedDuringInsert)
            {
               // Get the next primary key value.  Also updates the bean with the primary
               // key value so it can be used in the insert statement.
               getPrimaryKeyForInsertStatement(table, mapping, bean, pds);
               //primaryKeyValue = getPrimaryKeyForInsertStatement(table, mapping, bean, pds);
            }
            
            // Bind the values of the insert statement.
            populatePreparedStatementValues(bean, statement, mapping, pds);

            // Execute the statement.
            statement.executeUpdate();
            statement.clearParameters();
         }
      }
      catch (java.lang.Exception e)
      {
    	 try { 
             log.error("Error trying to insert Bean:");
             String[] lines = LogUtils.dataToStrings(bean);
             for (int D=0; D <= lines.length-1; D++)
                log.error("   "+lines[D]);
    	 } catch (Exception ex) {
             log.error("Error trying to insert Bean.");
    	 }
         log.error("SQL: "+insertSQL);
         throw new DataLayerException("Unable to save bean to the database.", e);
      }
   }       
*/
   
   /**
    * Save each bean in this list to the specified database table.
    * @param table String
    * @param mappingFile String
    * @param list ArrayList
    * @throws DataLayerException
    */
/*   
   private void insertBeanListUsingSQL(String table, String mappingFile, ArrayList list) throws DataLayerException
   {
      String insertSQL = null;
      String primaryKeyValue = null;
      
      if (list == null)
         throw new DataLayerException("Unable to save bean to the database.  List is null.");

      if (list.size() == 0)
      {
         log.debug("List is empty.  Nothing to insert.  Aborting process.");
         return;
      }   
      
      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         Class type = list.get(0).getClass();
         log.debug("");
         log.debug("INSERTING bean list to the database:");
         log.debug("   table="+table);
         log.debug("   bean type="+StringUtils.removePackage(type.getName()));
         log.debug("   mappingFile="+mappingFile);
         DatabaseDriver driver = getDriver();

         if (getConnection() == null)
            throw new DataLayerException("Error deleting data from the table "+table+".  The open() method was never called.");
         
         // Load the mapping file.
         Hashtable mapping = driver.loadMapping(mappingFile);  
         PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(type);

         // Create prepared statement.
         insertSQL = getInsertSql(table, mapping, type, true);
         log.info("InsertBeanList SQL: "+insertSQL);
         PreparedStatement statement = getConnection().prepareStatement(insertSQL);

         for (int C=0; C <= list.size()-1; C++)
         {
            Object bean = list.get(C);
            
            if (! bean.getClass().getName().equals(type.getName()))
               throw new DataLayerException("Unable to save bean list to the database.  All objects in the list must be the same type ("+type.getName()+" <> "+bean.getClass().getName()+").");

            insertBeanUsingSQL(table, mappingFile, bean, true);
            
            if (log.isDebugEnabled())
            {
               // Trace out contents of each bean.
               log.debug("Inserted Bean ["+C+"]:");
               String[] lines = LogUtils.dataToStrings(bean);
               for (int D=0; D <= lines.length-1; D++)
                  log.debug("   "+lines[D]);
            }
         }
      }
      catch (java.lang.Exception e)
      {
         log.error("SQL: "+insertSQL);
         throw new DataLayerException("Unable to save bean to the database.", e);
      }
   }       
*/      
   /**
    * Update this bean in the database table specified in the constructor.
    * @param table String
    * @param mappingFile String
    * @param bean Object
    * @param whereClause String
    * @throws DataLayerException
    */
   public void updateBean(String table, String mappingFile, Object bean, String whereClause) throws DataLayerException
   {
      String updateSQL = null;
      
      if (bean == null)
         throw new DataLayerException("Unable to save bean to the database.  Bean is null.");

      try
      {
         // Method should not call open(), as this will be called already
         // before this method is called.
         log.debug("");
         log.debug("UPDATING bean in the database:");
         log.debug("   table="+table);
         log.debug("   bean type="+StringUtils.removePackage(bean.getClass().getName()));
         log.debug("   mappingFile="+mappingFile);
         log.debug("   whereClause="+whereClause);
         DatabaseDriver driver = getDriver();

         if (getConnection() == null)
            throw new DataLayerException("Error deleting data from the table "+table+".  The open() method was never called.");
         
         // Load the mapping file.
         OrderedMap mapping = driver.loadMapping(mappingFile);  

         // Create prepared statement.
         updateSQL = getUpdateSqlForPreparedStatement(table, mapping, bean.getClass(), whereClause);
         log.info("UpdateBean SQL: "+updateSQL);
         PreparedStatement statement = getConnection().prepareStatement(updateSQL);

         // Bind the values of the insert statement.
         PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());
         populatePreparedStatementValues(bean, statement, mapping, pds);

         // Execute the statement.
         statement.executeUpdate();
      }
      catch (java.lang.Exception e)
      {
         log.error("SQL: "+updateSQL);
         throw new DataLayerException("Unable to save bean to the database.", e);
      }
   }       
      
   /**
    * Save all beans in this list to the database.  This method
    * will delete all rows in the database that match the
    * deleteWhereClause first before saving the beans in the
    * list.  This is incase items were removed from the list
    *  beforehand.
    * @param list
    * @param deleteWhereClause
    * @throws DataLayerException
    */
/*   
   public void saveBeanList(String table, PaginatedList list, String mappingFile, String deleteWhereClause) throws DataLayerException
   {
      try
      {
          if (list == null)
              throw new DataLayerException("Unable to save list to the database.  List is null.");

          PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(list.getDataType());

          if (pds == null || pds.length == 0)
             throw new DataLayerException("Unable to save list to the database.  Bean discriptors are missing.");

         // Method should not call open(), as this will be called already
         // before this method is called.
         log.debug("");
         log.debug("REPLACING list of beans in the database:");
         log.debug("   table="+table);
         log.debug("   bean type="+StringUtils.removePackage(list.getDataType().getName()));
         log.debug("   mappingFile="+mappingFile);
         log.debug("   whereClause="+deleteWhereClause);         
         delete(table, deleteWhereClause);

         if (getConnection() == null)
            throw new DataLayerException("Error deleting data from the table "+table+".  The open() method was never called.");
         
         // Create prepared statement.
         Hashtable mapping = getDriver().loadMapping(mappingFile);
         String insertSQL = getInsertSqlForPreparedStatement(table, mapping, list.getDataType(), false);
         log.info("SaveBeanList SQL: "+insertSQL);
         PreparedStatement statement = getConnection().prepareStatement(insertSQL);

         // Iterate through the list.
         for (int C=0; C <= list.size()-1; C++)
         {
            // Insert each bean using prepared statements.
            Object bean = list.get(C);
            
            // Bind the values of the insert statement.
            populatePreparedStatementValues(bean, statement, mapping, pds);

            // Execute the statement.
            statement.executeUpdate();
         }
         
         statement.close();
      }
      catch (DataLayerException ex)
      {
         throw ex;   
      }
      catch (java.lang.Exception e)
      {
         throw new DataLayerException("Unable to save list to the database.", e);
      }
   }
*/
   
   /**
    * Gets the next value for the primary key represented by the field of 
    * type "key" in the mapping file.  This is used for insert statements.
    * @param table String
    * @param mapping Hashtable
    */
/*   
   protected String getPrimaryKeyForInsertStatement(String table, Hashtable mapping, Object bean, PropertyDescriptor[] pds) throws DataLayerException
   {
      Method method = null;
      PropertyDescriptor property = null;
      Object[] writeArgs = new Object[1];
      Object[] readArgs = {};
      try
      {
         // Find the key field.
         Enumeration e = mapping.elements();
         int keyCount = 0;
         FieldDefinition keyField = null;
         while (e.hasMoreElements())
         {
            FieldDefinition field = (FieldDefinition)e.nextElement();
            if (field.getType().toLowerCase().equals("key"))
            {
               keyCount++;
               keyField = field;
            }
         }
         
         // If no keys, return null and don't worry about it.  This will only
         // be an issue if there are key fields.
         if (keyCount == 0)
            return null;
         
         // If multiple keys in the mapping file, this is a problem.  
         // Throw exception.
         if (keyCount > 1)
            throw new DataLayerException("Error getting next primary key value for table "+table+".  Multiple fields of type key found in mapping file.  Only one is allowed.");

         // If no sequence specified for the key field, this is also a problem.
         // Throw exception.
         if (keyField.getSequence() == null || keyField.getSequence().trim().equals(""))
            throw new DataLayerException("Error getting next primary key value for table "+table+".  No sequence specified in mapping file for key field "+keyField.getName()+".");

         // Generate SQL statement.
         StringBuffer sql = new StringBuffer("select ");
         sql.append(keyField.getSequence()+".nextval ");
         sql.append("from dual");
         log.info("GetPrimaryKey SQL: "+sql.toString());

         // Get the next value in the primary key sequence.
         ResultSet resultSet = executeSQL(sql.toString());
         resultSet.next();
         String primaryKeyValue = resultSet.getString(1);
         resultSet.getStatement().close();

         // Update the bean with the new primary key.
         property = findPropertyDescriptor(keyField.getName(), pds); 
         method = property.getWriteMethod();
         writeArgs[0] = primaryKeyValue;
         method.invoke(bean, writeArgs);

         // Verify that the value was updated correctly.
         method = property.getReadMethod();
         Object value = method.invoke(bean, readArgs);
         if (! value.toString().equals(primaryKeyValue))
            throw new DataLayerException("Error updating "+bean.getClass()+" "+keyField.getName()+" property with new primary key value "+primaryKeyValue+".  Verification after set failed.");
         
         return primaryKeyValue;
      }
      catch (Exception e)
      {
         throw new DataLayerException("Error getting next primary key value for table "+table+".", e);
      }
   }
*/
   
   /**
    * Find the property descriptor for the specified field name in the 
    * specified PropertyDescriptor array.
    * @param fieldName String
    * @param pds PropertyDescriptor[]
    * @return PropertyDescriptor
    */
/*   
   private PropertyDescriptor findPropertyDescriptor(String fieldName, PropertyDescriptor[] pds)
   {
      for (int C=0; C <= pds.length-1; C++)
      {
         // Does this property descriptor match the field name?
         if (pds[C].getName().trim().equalsIgnoreCase(fieldName.trim()))
            return pds[C];
      }

      return null;
   }
*/   
}
