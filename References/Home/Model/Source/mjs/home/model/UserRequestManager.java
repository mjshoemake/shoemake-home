/* File name:     UserRequestManager.java
 * Package name:  mjs.home.controller
 * Project name:  Home - Controller
 * Date Created:  Apr 28, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.model;

// Java imports

/**
 * The request manager is a facade for the database access.  
 * It contains a single public method for each request, 
 * abstracting the details of the implementation from the
 * calling class.   
 * 
 * @author mshoemake
 */
public interface UserRequestManager
{
   /**
    * Get the list of users from the database sorted by
    * first name within last name.
    * 
    * @return the list of users.
    */
   public UserList getUserList() throws java.lang.Exception;  

   /**
    * Get the list of users from the database sorted however
    * the user specifies (constants defined in 
    * DatabaseUserRequestManager.java.
    * 
    * @param sortorder  The specified order in which to sort. 
    * @return the list of users.
    */
   public UserList getUserList(int sortorder) throws java.lang.Exception;  

   /**
    * Get the list of users from the database.
    * @param userID  The specified user to retrieve. 
    * @return the specified user.
    */
   public User getUser(int userID) throws java.lang.Exception;  
   
   /**
    * Add a user to the database.
    * 
    * @param user  The user to add.
    * @throws java.lang.AbstractServletException
    */   
   public void addUser(User user) throws java.lang.Exception;  

   /**
    * Delete all specified users from the database.
    * 
    * @param userList  The user IDs to delete.
    * @throws java.lang.AbstractServletException
    */   
   public void deleteUsers(String[] userList) throws java.lang.Exception;  

   /**
    * Delete all users from the database.
    * 
    * @throws java.lang.AbstractServletException
    */   
   public void deleteAllUsers() throws java.lang.Exception;  

   /**
    * Delete a user from the database.
    * 
    * @param userID  The specified user to delete. 
    * @throws java.lang.AbstractServletException
    */   
   public void deleteUser(int userID) throws java.lang.Exception;  

   /**
    * Update a user's information in the database.
    * 
    * @param userName  The user to update.
    * @param user      The user's updated information.
    * @throws java.lang.AbstractServletException
    */   
   public void updateUser(int userID, User user) throws java.lang.Exception;  

}
