/* File name:     MainFrame.java
 * Package name:  mjs.jdbctest
 * Project name:  CmdServer
 * Date Created:  Mar 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.commands.users;

// Java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

// MJS imports
import mjs.home.model.*;


/*
 * This is the web service interface for user management.
 * 
 * @author mshoemake
 */
public interface UserWebService extends Remote 
{
   /**
    * Get the specified user from the database.
    * 
    * @return User object.
    */
   public User getUser(String userName) throws RemoteException;

   /**
    * Get the specified user from the database.
    * 
    * @return User object.
    */
   public User getUser(int userID) throws RemoteException;

   /**
    * Get the list of users from the database.
    * 
    * @return UserList object.
    */
   public User[] getUserList() throws RemoteException;
   
   /**
    * Add a user to the database.
    * 
    * @param user  The user to add.
    */
   public void addUser(User user) throws RemoteException;
   
   /**
    * Delete the user with this userName from the database.
    * 
    * @param userName
    * @throws RemoteException
    */
   public void deleteUser(String userName) throws RemoteException;  

   /**
    * Delete the user with this userID from the database.
    * 
    * @param userID
    * @throws RemoteException
    */
   public void deleteUser(int userID) throws RemoteException;
   
   /**
    * Update a user's information in the database.
    * 
    * @param userName  The user to update.
    * @param user      The updated user information.
    * @throws RemoteException
    */   
   public void updateUser(String userName, User user) throws RemoteException;  
     
   /**
    * Update a user's information in the database.
    * 
    * @param userID    The user to update.
    * @param user      The updated user information.
    * @throws RemoteException
    */   
   public void updateUser(int userID, User user) throws RemoteException;  
     
}
