package mjs.recipes;

import java.sql.Connection;
import org.apache.log4j.Logger;
import mjs.database.DataLayerException;
import mjs.database.DatabaseDriver;
import mjs.database.TableDataManager;
import mjs.model.RecipeForm;
import mjs.utils.Loggable;

/**
 * The data manager class for Tax Account Codes.
 */
public class RecipeManager extends Loggable {

   /**
    * The data manager instance to use to talk to the database.
    */
   protected TableDataManager manager = null;

   /**
    * The default mapping file.
    */
   protected String mappingFile = "mjs/recipes/RecipeMapping.xml";

   /**
    * The table name for this data manager.
    */
   protected String table = "recipes";

   /**
    * The log4j logger to use when writing log messages to the "Core"
    * category.
    */
   protected Logger logResultSet = Logger.getLogger("ResultSet");

   
   /**
    * Constructor.
    * 
    * @param newDriver com.accenture.core.model.AbstractDatabaseDriver
    * @exception DataLayerException Description of Exception
    */
   public RecipeManager(DatabaseDriver newDriver) throws DataLayerException {
      manager = new TableDataManager(newDriver, "recipes", mappingFile, RecipeForm.class);
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
    * @param applicationPK Description of Parameter
    * @return
    * @throws DataLayerException
    */
   /*
    * public ArrayList getUserListAsSelectOptions(String applicationPK) throws
    * DataLayerException { ArrayList listOfUserIds = null;
    * 
    * try { StringBuffer sql = new StringBuffer("select ");
    * 
    * sql.append("u.user_id, u.fname, u.mname, u.lname ");
    * sql.append("from users u, user_permissions p ");
    * sql.append("where p.user_id = u.user_id and p.application_pk = ");
    * sql.append(applicationPK);
    * sql.append(" order by u.lname, u.fname, u.mname");
    * 
    * ResultSet resultSet = manager.executeSQL(sql.toString());
    * 
    * if (resultSet != null) {
    * 
    * listOfUserIds = new ArrayList(); while (resultSet.next()) { String userID
    * = resultSet.getString(1); String fName = resultSet.getString(2); String
    * mName = resultSet.getString(3); String lName = resultSet.getString(4);
    * StringBuffer value = new StringBuffer(fName);
    * 
    * if (mName != null && ! mName.equals("")) { value.append(" ");
    * value.append(mName); value.append(" "); } if (lName != null && !
    * lName.equals("")) { value.append(" "); value.append(lName); }
    * listOfUserIds.add(new SelectOption(userID, value.toString())); } }
    * 
    * } catch (Exception e) { throw new
    * DataLayerException("Error while retrieving list of users." +
    * e.getMessage()); } return listOfUserIds; } //end of getUserList()
    */

   /**
    * Loads the list of objects from the database.
    * 
    * @param pageSize Description of Parameter
    * @param maxRows Description of Parameter
    * @return The value of the UserList property.
    * @throws DataLayerException
    */
/*   
   public PaginatedList getRecipeList(int pageSize, int maxRows, String globalForward) throws DataLayerException {
      try {
         PaginatedList list = manager.loadList("", pageSize, maxRows, globalForward);

         return list;
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error loading the sample bean list.", ex);
      }
   }
   /**
    * Loads the list of objects from the database.
    * 
    * @param pageSize Description of Parameter
    * @param maxRows Description of Parameter
    * @return The value of the UserList property.
    * @throws DataLayerException
    */
   /*
   public PaginatedList getRecipeList(String whereClause, int pageSize, int maxRows, String globalForward) throws DataLayerException {
      try {
         PaginatedList list = manager.loadList(whereClause, pageSize, maxRows, globalForward);

         return list;
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error loading the sample bean list.", ex);
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
   public void updateRecipe(RecipeForm bean) throws DataLayerException {
      try {
         String whereClause = "where recipes_pk = " + bean.getRecipes_pk();
         log.debug("Calling Update Bean...");
         manager.updateBean(table, mappingFile, bean, whereClause);
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error updating the recipe " + bean.getName() + ".", ex);
      }
   }

   /**
    * Delete this user in the database.
    * 
    * @param bean UserForm
    * @param applicationPK String
    * @throws DataLayerException
    */
   /*
   public void deleteRecipe(RecipeForm bean) throws DataLayerException {
      try {
         String whereClause = "where recipes_pk = " + bean.getRecipes_pk();
         log.debug("Calling Delete Bean...");
         manager.delete(whereClause);
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error deleting the recipe " + bean.getName() + ".", ex);
      }
   }

   /**
    * Delete this user in the database.
    * 
    * @param bean UserForm
    * @param applicationPK String
    * @throws DataLayerException
    */
   /*
   public void deleteRecipe(int recipes_pk) throws DataLayerException {
      try {
         String whereClause = "where recipes_pk = " + recipes_pk;
         log.debug("Calling Delete Bean...");
         manager.delete(whereClause);
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error deleting the recipe with primary key " + recipes_pk + ".", ex);
      }
   }

   /**
    * Insert this user in the database.
    * 
    * @param bean UserForm
    * @throws DataLayerException
    */
   /*
   public void insertRecipe(RecipeForm bean) throws DataLayerException {
      try {
         String table = "recipes";
         log.debug("Calling Insert Bean...");
         manager.insertBean(table, mappingFile, bean);
      }
      catch (DataLayerException ex) {
         throw new DataLayerException("Error inserting the recipe " + bean.getName() + ".", ex);
      }
   }

   /**
    * Returns the number of rows in the specified table that match the specified
    * where clause.
    * 
    * @param table String
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
   public void getRecipe(int recipes_pk, RecipeForm form) throws DataLayerException {
      try {
         if (form == null)
            throw new DataLayerException("Unable to get the recipe properties.  Form is null.");

         manager.loadBean(form, " where recipes_pk = " + recipes_pk);
      }
      catch (DataLayerException e) {
         throw e;
      }
      catch (Exception e) {
         throw new DataLayerException("Error loading the user for user ID " + recipes_pk + ".", e);
      }
   }

   /**
    * Get the recipe data for the view recipe page.
    * @param recipes_pk
    * @param form
    * @throws DataLayerException
    */
   /*
   public void getRecipeForView(int recipes_pk, RecipeForm form) throws DataLayerException {
      ResultSet rs = null; 
      try {
         if (form == null)
            throw new DataLayerException("Unable to search for recipes.  RecipeSearchForm is null.");
         StringBuilder builder = new StringBuilder();
         builder.append("select r.name, r.directions, r.ingredients, r.servings, r.serving_size, ");
         builder.append("       r.nutrition, r.calories_per_serving, r.notes, r.picture_filename, ");
         builder.append("       c.name as cname, m.name as mname, mc.name as mcname, r.favorite ");
         builder.append("from recipes r, cookbooks c, meals m, meal_categories mc ");
         builder.append("where r.cookbook_pk = c.cookbooks_pk ");
         builder.append("  and r.meals_pk = m.meals_pk ");
         builder.append("  and r.meal_categories_pk = mc.meal_categories_pk ");
         builder.append("  and r.recipes_pk = " + recipes_pk);
         rs = manager.executeSQL(builder.toString());
         if (! rs.next()) {
            throw new DataLayerException("Error trying to load recipe for primary key " + recipes_pk + ".");
         }
         
         form.setRecipes_pk(recipes_pk+"");
         form.setName(rs.getString(1));
         form.setDirections(rs.getString(2));
         form.setIngredients(rs.getString(3));
         form.setServings(rs.getString(4));
         form.setServing_size(rs.getString(5));
         form.setNutrition(rs.getString(6));
         form.setCalories_per_serving(rs.getString(7));
         form.setNotes(rs.getString(8));
         form.setPicture_filename(rs.getString(9));
         form.setCookbook_pk(rs.getString(10));
         form.setMeals_pk(rs.getString(11));
         form.setMeal_categories_pk(rs.getString(12));
         form.setFavorite(rs.getString(13));
      }
      catch (DataLayerException e) {
         throw e;
      }
      catch (Exception e) {
         throw new DataLayerException("Error loading the user for user ID " + recipes_pk + ".", e);
      } finally {
         try {
             if (rs != null) {
                rs.close();
             }
         } catch (Exception e) {
             log.error("Failed to close result set.", e);  
         }
      }
   }

   /**
    * Loads the list of objects from the database based on the specified filter.
    * 
    * @param pageSize Description of Parameter
    * @param maxRows Description of Parameter
    * @return The value of the UserList property.
    * @throws DataLayerException
    */
   /*
   public PaginatedList searchRecipes(HashMap<String,String> searchCriteria, int pageSize, int maxRows, String globalForward) throws DataLayerException {
      try {
    	 HashMap<String,String> formData = null; 
         if (searchCriteria == null) {
            formData = new HashMap<String,String>(); 
         } else {
            formData = removeAtSignFromKeys(searchCriteria);
         }
         
         PaginatedList list = new PaginatedList(RecipeSearchForm.class, pageSize, maxRows, globalForward);
         list.setPageLength(10);
         StringBuilder builder = new StringBuilder();
         builder.append("select r.recipes_pk, r.name, r.serving_size, ");
         builder.append("       r.calories_per_serving, c.name as cname, ");
         builder.append("       m.name as mname, mc.name as mcname, r.favorite ");
         builder.append("from recipes r, cookbooks c, meals m, meal_categories mc ");
         builder.append("where r.cookbook_pk = c.cookbooks_pk ");
         builder.append("  and r.meals_pk = m.meals_pk ");
         builder.append("  and r.meal_categories_pk = mc.meal_categories_pk ");
         
         // Add filters.
         likeFilter("r.name", formData.get("name"), builder);
         likeFilter("c.name", formData.get("cookbook"), builder);
         likeFilter("m.name", formData.get("meal"), builder);
         likeFilter("mc.name", formData.get("meal_category"), builder);
         likeFilter("r.favorite", formData.get("favorite"), builder);
         likeFilter("r.serving_size", formData.get("serving_size"), builder);
         intFilter("r.calories_per_serving", formData.get("calories_per_serving"), builder);
         builder.append("order by r.name ");
         log.info("SQL: " + builder.toString());

         ResultSet rs = manager.executeSQL(builder.toString());
         while (rs.next()) {
            RecipeSearchForm item = new RecipeSearchForm();
            item.setRecipes_pk(rs.getInt(1) + "");
            item.setName(rs.getString(2));
            item.setServing_size(rs.getString(3));
            item.setCalories_per_serving(rs.getString(4));
            item.setCookbook(rs.getString(5));
            item.setMeal(rs.getString(6));
            item.setMeal_category(rs.getString(7));
            item.setFavorite(rs.getString(8));
            list.add(item);
            logResultSet.debug("Next Item:");
            logResultSet.debug("   Name: " + item.getName());
            logResultSet.debug("   Cookbook: " + item.getCookbook());
            logResultSet.debug("   Meal: " + item.getMeal());
            logResultSet.debug("   Meal_category: " + item.getMeal_category());
            logResultSet.debug("   Favorite: " + item.getFavorite());
         }

         logResultSet.info("ResultSet size: " + list.size());
         rs.close();
         return list; 
      }
      catch (DataLayerException e) {
         throw e;
      }
      catch (Exception e) {
         throw new DataLayerException("Error loading the recipe list for the specified filter.", e);
      }
   }

   /**
    * Returns a the input Map with leading "@" signs removed from the names
    * of the keys.  Ex. The key "@name" becomes "name". 
    * @param map HashMap<String,String>
    * @return HashMap<String,String>
    */
   /*
   private HashMap<String,String> removeAtSignFromKeys(HashMap<String,String> map) {
	   HashMap<String,String> result = new HashMap<String,String>();
	   
	   Iterator<String> iterator = map.keySet().iterator();
	   while (iterator.hasNext()) {
		   String key = iterator.next();
		   if (key.startsWith("@")) {
			   result.put(key.substring(1), map.get(key));
		   } else {
			   result.put(key, map.get(key));
		   }
	   }
	   
	   return result;
   }
   
   
/*
   private void likeFilter(String name, String value, StringBuilder sql) {
      if (value != null && value.length() > 0) {
         String newValue = StringUtils.replaceAll(value, '*', '%');
         log.info(name + " filter: " + value + "  -> " + newValue);
         if (newValue.length() > 0 && (! newValue.contains("%"))) {
            newValue += "%";
         }
         sql.append("  and " + name + " LIKE \"");
         sql.append(newValue);
         sql.append("\" ");
      }
   }
   
   private void intFilter(String name, String value, StringBuilder sql) {
      int numberPart = -1;
      String operator = null;
      if (value != null && value.length() > 0) {
         // Find the start of the int value.
         int i=0; 
         char ch = value.charAt(i);
         while (i <= value.length()-1 && (! Character.isDigit(ch))) {
            i++;
            ch = value.charAt(i);
         }
         log.info("IntFilter: " + value + "  Numeric found at pos: " + i);
         if (i <= value.length()-1) {
            // Found.
            numberPart = Integer.parseInt(value.substring(i));
            if (i > 0) {
                operator = value.substring(0,i).trim();
            } else {
               operator = "=";
            }
            log.info("IntFilter: " + value + " -> num=" + numberPart + " operator=" + operator);
         }

         if (operator.equals("<") ||
             operator.equals(">") ||
             operator.equals("=") ||
             operator.equals("<=") ||
             operator.equals(">=") ||
             operator.equals("<>")) {
            sql.append("  and ");
            sql.append(name);
            sql.append(" ");
            sql.append(operator);
            sql.append(" \"");
            sql.append(numberPart);
            sql.append("\" ");
         }
      }
   }
   
   /**
    * Generate the SQL for the user search.
    * 
    * @param form UserSearchForm
    * @param applicationPK Description of Parameter
    * @return String
    */
   /*
    * protected String generateSearchSQL(UserSearchForm form, String
    * applicationPK) { StringBuffer sql = new StringBuffer();
    * 
    * sql.append(
    * "select u.user_id, u.fname, u.mname, u.lname, u.phone, u.email, u.ipager, p.permission_level "
    * ); sql.append("from users u, user_permissions p "); sql.append(
    * "where upper(p.user_id) = upper(u.user_id) and upper(p.application_pk )= "
    * ); sql.append(applicationPK.toUpperCase());
    * //sql.append(" and u.deleted=''N''");
    * 
    * if (! form.getSearchUser_ID().trim().equals("")) {
    * sql.append(" and upper(u.user_id) like '");
    * sql.append(AbstractDataManager.
    * quoteTicks(form.getSearchUser_ID().trim().toUpperCase().replace('*',
    * '%'))); sql.append("%'"); } if (! form.getSearchFname().trim().equals(""))
    * { sql.append(" and upper(u.fname) like '");
    * sql.append(AbstractDataManager.
    * quoteTicks(form.getSearchFname().trim().toUpperCase().replace('*', '%')));
    * sql.append("%'"); } if (! form.getSearchMname().trim().equals("")) {
    * sql.append(" and upper(u.mname) like '");
    * sql.append(AbstractDataManager.quoteTicks
    * (form.getSearchMname().trim().toUpperCase().replace('*', '%')));
    * sql.append("%'"); } if (! form.getSearchLname().trim().equals("")) {
    * sql.append(" and upper(u.lname) like '");
    * sql.append(AbstractDataManager.quoteTicks
    * (form.getSearchLname().trim().toUpperCase().replace('*', '%')));
    * sql.append("%'"); } if (! form.getSearchPhone().trim().equals("")) {
    * sql.append(" and upper(u.phone) like '");
    * sql.append(AbstractDataManager.quoteTicks
    * (form.getSearchPhone().trim().toUpperCase().replace('*', '%')));
    * sql.append("%'"); } if (!
    * form.getSearchEmail().trim().toUpperCase().equals("")) {
    * sql.append(" and upper(u.email) like '");
    * sql.append(AbstractDataManager.quoteTicks
    * (form.getSearchEmail().trim().toUpperCase().replace('*', '%')));
    * sql.append("%'"); } if (!
    * form.getSearchPermissionLevel().trim().equals("-1")) {
    * sql.append(" and p.permission_level = ");
    * sql.append(form.getSearchPermissionLevel().trim()); } if (!
    * form.getSearchIpager().trim().toUpperCase().equals("")) {
    * sql.append(" and upper(u.ipager) like '");
    * sql.append(AbstractDataManager.quoteTicks
    * (form.getSearchIpager().trim().toUpperCase().replace('*', '%')));
    * sql.append("%'"); } sql.append(" order by u.lname, u.fname, u.mname");
    * return sql.toString(); }
    */

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
