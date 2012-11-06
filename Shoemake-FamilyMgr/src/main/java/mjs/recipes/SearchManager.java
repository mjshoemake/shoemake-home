package mjs.recipes;

import java.sql.ResultSet;
import java.util.HashMap;

import mjs.database.DataLayerException;
import mjs.database.PaginatedList;
import mjs.database.SearchDataManager;
import mjs.recipes.RecipeForm;
import mjs.recipes.RecipeSearchForm;
import mjs.users.UserForm;

/**
 * The data manager class for Tax Account Codes.
 */
public class SearchManager extends SearchDataManager {

   /**
    * Constructor.
    * 
    * @param newDriver com.accenture.core.model.AbstractDatabaseDriver
    * @exception DataLayerException Description of Exception
    */
   public SearchManager() throws DataLayerException {
       super();
   }

   /**
    * Get the recipe data for the view recipe page.
    * @param recipes_pk
    * @param form
    * @throws DataLayerException
    */
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
    * Loads the list of objects from the database based on the specified filter.
    * 
    * @param pageSize Description of Parameter
    * @param maxRows Description of Parameter
    * @return The value of the UserList property.
    * @throws DataLayerException
    */
   public PaginatedList searchUsers(HashMap<String,String> searchCriteria, int pageSize, int maxRows, String globalForward) throws DataLayerException {
      try {
         HashMap<String,String> formData = null; 
         if (searchCriteria == null) {
            formData = new HashMap<String,String>(); 
         } else {
            formData = removeAtSignFromKeys(searchCriteria);
         }
         
         PaginatedList list = new PaginatedList(UserForm.class, pageSize, maxRows, globalForward);
         list.setPageLength(10);
         StringBuilder builder = new StringBuilder();
         builder.append("select u.user_pk, u.username, u.fname, u.lname, ");
         builder.append("       u.login_enabled ");
         builder.append("from users u ");
         builder.append("where 1=1 ");
         
         // Add filters.
         likeFilter("u.username", formData.get("username"), builder);
         likeFilter("u.fname", formData.get("fname"), builder);
         likeFilter("u.lname", formData.get("lname"), builder);
         likeFilter("u.login_enabled", formData.get("login_enabled"), builder);
         builder.append("order by u.lname, u.fname ");
         log.info("SQL: " + builder.toString());

         ResultSet rs = manager.executeSQL(builder.toString());
         while (rs.next()) {
            UserForm item = new UserForm();
            item.setUser_pk(rs.getInt(1) + "");
            item.setUsername(rs.getString(2));
            item.setFname(rs.getString(3));
            item.setLname(rs.getString(4));
            item.setLogin_enabled(rs.getString(5));
            list.add(item);
            logResultSet.debug("Next Item:");
            logResultSet.debug("   User Name: " + item.getUsername());
            logResultSet.debug("   First Name: " + item.getFname());
            logResultSet.debug("   Last Name: " + item.getLname());
            logResultSet.debug("   Login Enabled: " + item.getLogin_enabled());
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

}
