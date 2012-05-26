/* File name:     SoapUserRequestManager.java
 * Package name:  mjs.home.commands.users.client
 * Project name:  Home - WebServiceClient
 * Date Created:  Apr 18, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.commands.users.client;

// MJS imports
import mjs.home.model.User;
import mjs.home.model.UserList;
import mjs.home.model.UserRequestManager;

/**
 * The request manager is a facade for the database access.  
 * It contains a single public method for each request, 
 * abstracting the details of the implementation from the
 * calling class.   
 * 
 * @author mshoemake
 */
public class SoapUserRequestManager implements UserRequestManager
{
   /**
    * The stub to the web service.
    */
   UserWebService_Stub stub = null;
   
   /**
    * Configuration object for the SoapClient.
    *
    */
   SoapClientConfiguration config = new SoapClientConfiguration();

   /**
    * Constructor. 
    */
   public SoapUserRequestManager()
   {
      // Instantiate the stub/proxy
      System.out.println("SoapUserRequestManager   constructor()  Begin");
      UserService_Impl serviceproxy = new UserService_Impl();
      stub = (UserWebService_Stub) (serviceproxy.getUserWebServicePort());
      
      // Set the endpoint URL
      String Url = config.getUserWebServiceURL();
      System.out.println("SoapUserRequestManager   constructor()  URL = "+Url);
      stub._setProperty(javax.xml.rpc.Stub.ENDPOINT_ADDRESS_PROPERTY, Url);
      System.out.println("SoapUserRequestManager   constructor()  End");
   }
    
   /**
    * Get the list of users from the database.
    * 
    * @return the list of users.
    */
   public UserList getUserList() throws java.lang.Exception  
   {
      try
      {
         // Call the getInfo web service menthod
         GetUserListResponse response = stub.getUserList(new GetUserList());
         // Process response 
         mjs.home.commands.users.client.User[] users = response.getResult();
         UserList list = new UserList();
         for (int C=0; C <= users.length-1; C++)
         {
            mjs.home.commands.users.client.User tempUser = users[C];   
            User nextUser = new User(tempUser.getUserID(), 
                                     tempUser.getUserName(), 
                                     tempUser.getPassword(),
                                     tempUser.getFirstName(),
                                     tempUser.getLastName());
            list.getUserList().add(nextUser);                    
         }
         return list;
      }
      catch (java.lang.Exception e)
      {
         throw e;
      }
   }

   /**
    * Get the list of users from the database.
    * 
    * @param username  The specified user to retrieve. 
    * @return the specified user.
    */
   public User getUser(String username) throws java.lang.Exception  
   {
      try
      {
         // Call the getInfo web service menthod
         GetUser2Response response = stub.getUser2(new GetUser2(username));
         // Process response 
         mjs.home.commands.users.client.User tempUser = response.getResult();
         User user = new User(tempUser.getUserID(), 
                              tempUser.getUserName(), 
                              tempUser.getPassword(),
                              tempUser.getFirstName(),
                              tempUser.getLastName());
         return user;
      }
      catch (java.lang.Exception e)
      {
         // Throw AbstractServletException.
         throw e;         
      }
   }

   /**
    * Get the list of users from the database.
    * @param userID  The specified user to retrieve. 
    * @return the specified user.
    */
   public User getUser(int userID) throws java.lang.Exception  
   {
      try
      {
         // Call the getInfo web service menthod
         GetUserResponse response = stub.getUser(new GetUser(userID));
         // Process response 
         mjs.home.commands.users.client.User tempUser = response.getResult();
         User user = new User(tempUser.getUserID(), 
                              tempUser.getUserName(), 
                              tempUser.getPassword(),
                              tempUser.getFirstName(),
                              tempUser.getLastName());
         return user;
      }
      catch (java.lang.Exception e)
      {
         // Throw AbstractServletException.
         throw e;         
      }
   }
   
   /**
    * Add a user to the database.
    * 
    * @param user  The user to add.
    * @throws java.lang.AbstractServletException
    */   
   public void addUser(User user) throws java.lang.Exception  
   {
      try
      {
         mjs.home.commands.users.client.User tempUser = 
            new mjs.home.commands.users.client.User(user.getFirstName(),
                                                    user.getLastName(),
                                                    user.getPassword(),
                                                    user.getUserID(),
                                                    user.getUserName());
         // Call the getInfo web service menthod
         stub.addUser(new AddUser(tempUser));
      }
      catch (java.lang.Exception e)
      {
         // Unsuccessful.
         throw e;
      }
   }

   /**
    * Delete a user from the database.
    * 
    * @param userName  The user to delete.
    * @throws java.lang.AbstractServletException
    */   
   public void deleteUser(String userName) throws java.lang.Exception  
   {
      try
      {
         // Call the getInfo web service menthod
         stub.deleteUser2(new DeleteUser2(userName));
      }
      catch (java.lang.Exception e)
      {
         // Unsuccessful.
         throw e;
      }
   }
   
   /**
    * Delete a user from the database.
    * 
    * @param userID  The specified user to delete. 
    * @throws java.lang.AbstractServletException
    */   
   public void deleteUser(int userID) throws java.lang.Exception  
   {
      try
      {
         // Call the getInfo web service menthod
         stub.deleteUser(new DeleteUser(userID));
      }
      catch (java.lang.Exception e)
      {
         // Unsuccessful.
         throw e;
      }
   }

   /**
    * Update a user's information in the database.
    * 
    * @param userName  The user to update.
    * @param user      The user's updated information.
    * @throws java.lang.AbstractServletException
    */   
   public void updateUser(String userName, User user) throws java.lang.Exception  
   {
      try
      {
         mjs.home.commands.users.client.User tempUser = 
            new mjs.home.commands.users.client.User(user.getFirstName(),
                                                    user.getLastName(),
                                                    user.getPassword(),
                                                    user.getUserID(),
                                                    user.getUserName());
         // Call the getInfo web service menthod
         stub.updateUser2(new UpdateUser2(userName, tempUser));
      }
      catch (java.lang.Exception e)
      {
         // Unsuccessful.
         throw e;
      }
   }
   
   /**
    * Update a user's information in the database.
    * 
    * @param userName  The user to update.
    * @param user      The user's updated information.
    * @throws java.lang.AbstractServletException
    */   
   public void updateUser(int userID, User user) throws java.lang.Exception  
   {
      try
      {
         mjs.home.commands.users.client.User tempUser = 
            new mjs.home.commands.users.client.User(user.getFirstName(),
                                                    user.getLastName(),
                                                    user.getPassword(),
                                                    user.getUserID(),
                                                    user.getUserName());
         // Call the getInfo web service menthod
         stub.updateUser(new UpdateUser(userID, tempUser));
      }
      catch (java.lang.Exception e)
      {
         // Unsuccessful.
         throw e;
      }
   }
   
}
