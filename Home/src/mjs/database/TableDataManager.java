package mjs.database;

import java.util.ArrayList;

import mjs.aggregation.OrderedMap;
import mjs.core.Form;
import mjs.view.SelectOption;
import mjs.view.ValidationErrorList;

//import java.util.ArrayList;


/**
 * The TableDataManager class is the class that retrieves from and
 * sends data to a specific database table. This table simplifies the
 * interface because the table name, mapping file, and bean datatype
 * can all be specified in the constructor.
 */
@SuppressWarnings("rawtypes")
public class TableDataManager extends DataManager
{
   /**
    * The table name to update.
    */
   String table = null;

   /**
    * The mapping file name used to specify the datatypes of the bean
    * properties.
    */
   String mappingFile = null;

   /**
    * The Class associated with the bean to instantiate when
    * retrieving data from the table (loadBeanList() and loadBean()
    * methods).
    */
   Class type = null;


   /**
    * Constructor.
    *
    * @param driver       Description of Parameter
    * @param tableName    Description of Parameter
    * @param mappingFile  Description of Parameter
    * @param datatype     Description of Parameter
    */
   public TableDataManager(DatabaseDriver driver, String tableName, String mappingFile, Class datatype) throws DataLayerException {
      super(driver);
      this.table = tableName;
      this.mappingFile = mappingFile;
      this.type = datatype;
   }


   /**
    * Load this list from the database.
    *
    * @param whereClause    Description of Parameter
    * @param pageSize       The size of the page (default is 10).
    * @param maxRecords     The max # of records (default is 500).
    * @param globalForward  The global forward to use for to get back to the current JSP page.
    *                       This is used by the pagination actions.
    * @return                     PaginatedList
    * @throws DataLayerException
    */
   public PaginatedList loadList(String whereClause, int pageSize, int maxRecords, String globalForward) throws DataLayerException
   {
      return loadList(table, type, mappingFile, whereClause, pageSize, maxRecords, globalForward);
   }

   
   /**
    * Load this bean from the database.
    *
    * @param whereClause          The search criteria.
    * @return                     Object
    * @throws DataLayerException
    */
   public Object loadBean(String whereClause) throws DataLayerException
   {
      return loadBean(table, type, mappingFile, whereClause);
   }

   /**
    * Load this bean from the database.
    *
    * @param bean                 Description of Parameter
    * @param whereClause          The search criteria.
    * @return                     Object
    * @throws DataLayerException
    */
   public Object loadBean(Object bean, String whereClause) throws DataLayerException
   {
      return loadBean(bean, table, type, mappingFile, whereClause);
   }

   /**
    * Delete all rows in the table that was specified in the class
    * constructor that match the specified where clause.
    *
    * @param whereClause
    * @throws DataLayerException
    */
   public void delete(String whereClause) throws DataLayerException
   {
      delete(table, whereClause);
   }

   /**
    * Save this bean to the database table specified in the
    * constructor.
    *
    * @param bean
    * @return                     String The primary key value for the
    * inserted row.
    * @throws DataLayerException
    */
   public String insertBean(Object bean) throws DataLayerException
   {
      return insertBean(table, mappingFile, bean);
   }

   /**
    * Save each bean in this list to the database table specified in
    * the constructor.
    *
    * @param list                 ArrayList
    * @throws DataLayerException
    */
/*
   public void insertBeanList(ArrayList list) throws DataLayerException
   {
      insertBeanList(table, mappingFile, list);
   }
*/
   
   /**
    * Update this bean in the database table specified in the
    * constructor.
    *
    * @param bean
    * @param whereClause          Description of Parameter
    * @throws DataLayerException
    */
   public void updateBean(Object bean, String whereClause) throws DataLayerException
   {
      updateBean(table, mappingFile, bean, whereClause);
   }

   /**
    * Save all beans in this list to the database. This method will
    * delete all rows in the database that match the deleteWhereClause
    * first before saving the beans in the list. This is incase items
    * were removed from the list beforehand.
    *
    * @param list
    * @param deleteWhereClause
    * @throws DataLayerException
    */
/*   
   public void saveBeanList(PaginatedList list, String deleteWhereClause) throws DataLayerException
   {
      saveBeanList(table, list, mappingFile, deleteWhereClause);
   }
*/
   /**
    * The table name to update.
    *
    * @return   The value of the TableName property.
    */
   public String getTableName()
   {
      return table;
   }

   /**
    * The mapping file name used to specify the datatypes of the bean
    * properties.
    *
    * @return   The value of the MappingFile property.
    */
   public String getMappingFile()
   {
      return mappingFile;
   }

   /**
    * The Class associated with the bean to instantiate when
    * retrieving data from the table (loadBeanList() and loadBean()
    * methods).
    *
    * @return   The value of the Datatype property.
    */
   public Class getDatatype()
   {
      return type;
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
   public OrderedMap loadMapping() throws DataLayerException
   {
      try
      {
         if (mappingFile == null)
            throw new DataLayerException("Error loading mapping file.  Mapping file path is null.");

         log.debug("Loading mapping file...");
         return getDriver().loadMapping(mappingFile);
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
    * Validate the specified form object.
    * @param form
    * @throws DataLayerException
    */
   public ValidationErrorList validateForm(Form form) throws DataLayerException {
       DatabaseDriver driver = getDriver();
       if (driver == null)
          throw new DataLayerException("Unable to validate the specified form. The database driver is null.");

       try {
           OrderedMap dataMapping = driver.loadMapping(mappingFile);
           return form.validate(dataMapping);
       } catch (Exception e) {
           throw new DataLayerException("Unable to validate the specified form. " + e.getMessage(), e);
       }
   }
   
   /**
    * Get a list of select options from the specified table.
    * 
    * @param table String
    * @param pkField String
    * @param captionField String
    * @return ArrayList<SelectOption>
    * @throws DataLayerException
    */
   public ArrayList<SelectOption> getSelectOptions(String pkField, 
		                                           String captionField) throws DataLayerException {
	   
	  return getSelectOptions(table, pkField, captionField);
   }
   
}
