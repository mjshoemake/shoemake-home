package mjs.users;

import java.sql.ResultSet;
import java.util.HashMap;

import mjs.database.DataLayerException;
import mjs.database.PaginatedList;
import mjs.database.SearchDataManager;
import mjs.model.UserForm;

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
