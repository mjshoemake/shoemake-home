package mjs.admin;

import java.sql.Connection;
import mjs.database.DataLayerException;
import mjs.database.DatabaseDriver;
import mjs.database.TableDataManager;
import mjs.model.MealForm;
import mjs.utils.Loggable;

/**
 * The data manager class for Meal Categories.
 */
public class MealsManager extends Loggable {

   /**
    * The data manager instance to use to talk to the database.
    */
   protected TableDataManager manager = null;

   /**
    * The default mapping file.
    */
   protected String mappingFile = "mjs/admin/MealsMapping.xml";

   /**
    * The table name for this data manager.
    */
   protected String table = "meals";

   /**
    * Constructor.
    * 
    * @param newDriver com.accenture.core.model.AbstractDatabaseDriver
    * @exception DataLayerException Description of Exception
    */
   public MealsManager(DatabaseDriver newDriver) throws DataLayerException {
      manager = new TableDataManager(newDriver, table, mappingFile, MealForm.class);
   }

   /**
    * Creates and opens a DatabaseTransaction to use to talk to the database.
    * 
    * @return Description of Return Value
    * @throws DataLayerException
    */
   public Connection open() throws DataLayerException {
      return manager.open();
   }

   /**
    * Closes the transaction. If unsuccessful, the transaction performs a
    * rollback.
    * 
    * @param successful Description of Parameter
    * @throws DataLayerException
    */
   public void close(boolean successful) throws DataLayerException {
      manager.close(successful);
   }

   /**
    * Closes the current transaction and releases the connection back to the
    * connection pool.
    * 
    * @throws DataLayerException
    */
   public void close() throws DataLayerException {
      manager.close();
   }

   /**
    * Get the Meals as HTML select options.
    * 
    * @return ArrayList
    * @throws DataLayerException
    */
/*   
   public ArrayList<SelectOption> getMealsAsSelectOptions() throws DataLayerException {
      ArrayList<SelectOption> result = new ArrayList<SelectOption>();

      try {
         StringBuffer sql = new StringBuffer();
         sql.append("select meals_pk, name ");
         sql.append("from meals ");
         sql.append("order by name");

         ResultSet resultSet = manager.executeSQL(sql.toString());
         if (resultSet != null) {
            while (resultSet.next()) {
               String pk = resultSet.getString(1);
               String name = resultSet.getString(2);
               result.add(new SelectOption(pk, name));
            }
         }
      }
      catch (Exception e) {
         throw new DataLayerException("Error while retrieving list of users." + e.getMessage());
      }
      return result;
   }

   /**
    * Loads the list of objects from the database.
    * 
    * @param pageSize int
    * @param maxRows int
    * @return PaginatedList
    * @throws DataLayerException
    */
/*   
   public PaginatedList getMealList(int pageSize, int maxRows, String globalForward) throws DataLayerException {
      try {
         PaginatedList list = manager.loadList("order by name", pageSize, maxRows, globalForward);
         return list;
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error loading the meal category list.", ex);
      }
   }

   /**
    * Loads the list of objects from the database.
    * 
    * @param pageSize int
    * @param maxRows int
    * @return PaginatedList
    * @throws DataLayerException
    */
   /*
   public PaginatedList getMealList(String whereClause, int pageSize, int maxRows, String globalForward) throws DataLayerException {
      try {
         PaginatedList list = manager.loadList(whereClause, pageSize, maxRows, globalForward);
         return list;
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error loading the meal category list.", ex);
      }
   }

   /**
    * Update this user in the database.
    * 
    * @param bean UserForm
    * @param applicationPK String
    * @throws DataLayerException
    */
/*   
   public void updateMeal(MealForm bean) throws DataLayerException {
      try {
         String whereClause = "where meals_pk = " + bean.getPK();
         log.debug("Calling Update Bean...");
         manager.updateBean(table, mappingFile, bean, whereClause);
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error updating the meal category " + bean.getName() + ".", ex);
      }
   }

   /**
    * Delete this item in the database.
    * 
    * @param bean
    * @throws DataLayerException
    */
/*
   public void deleteMeal(MealForm bean) throws DataLayerException {
      try {
         String whereClause = "where meals_pk = " + bean.getPK();
         log.debug("Calling Delete Bean...");
         manager.delete(whereClause);
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error deleting the meal category " + bean.getName() + ".", ex);
      }
   }

   /**
    * Delete this item in the database.
    * 
    * @param pk
    * @throws DataLayerException
    */
/*
   public void deleteMeal(int pk) throws DataLayerException {
      try {
         String whereClause = "where meals_pk = " + pk;
         log.debug("Calling Delete Bean...");
         manager.delete(whereClause);
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error deleting the meal category with primary key " + pk + ".", ex);
      }
   }

   /**
    * Insert this item in the database.
    * 
    * @param bean 
    * @throws DataLayerException
    */
/*   public void insertMeal(MealForm bean) throws DataLayerException {
      try {
         log.debug("Calling Insert Bean...");
         manager.insertBean(table, mappingFile, bean);
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error inserting the meal " + bean.getName() + ".", ex);
      }
   }

   /**
    * Returns the number of rows in the specified table that match the specified
    * where clause.
    * 
    * @param whereClause String
    * @return int
    * @throws DataLayerException
    */
/*
   public int countRows(String whereClause) throws DataLayerException {
      try {
         int results = manager.countRows(table, whereClause);
         return results;
      }
      catch (DataLayerException e) {
         throw e;
      }
      catch (Exception e) {
         throw new DataLayerException("Error counting rows in table " + table + ".", e);
      }
   }

   /**
    * Loads the specified user based on the UID.
    * 
    * @throws DataLayerException
    */
/*
   public void getMeal(int pk, MealForm form) throws DataLayerException {
      try {
         if (form == null)
            throw new DataLayerException("Unable to get the meal category properties.  Form is null.");
         manager.loadBean(form, " where meals_pk = " + pk);
      }
      catch (DataLayerException e) {
         throw e;
      }
      catch (Exception e) {
         throw new DataLayerException("Error loading the meal for PK " + pk + ".", e);
      }
   }

   /**
    * Loads the specified user based on the UID.
    * 
    * @throws DataLayerException
    */
/*   
   public void getMealByName(String name, MealForm form) throws DataLayerException {
      try {
         if (form == null)
            throw new DataLayerException("Unable to get the meal properties for name '" + name + "'.  Form is null.");
         manager.loadBean(form, " where name = '" + name + "'");
      }
      catch (DataLayerException e) {
         throw e;
      }
      catch (Exception e) {
         throw new DataLayerException("Error loading the meal property for name '" + name + "'.", e);
      }
   }

   /**
    * Get the mapping file location.
    * 
    * @return String
    */
/*   
   public String getMappingFile() {
      return mappingFile;
   }
*/
}
