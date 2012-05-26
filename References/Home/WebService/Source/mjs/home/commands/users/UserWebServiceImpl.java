package mjs.home.commands.users;

// Java imports
import java.rmi.RemoteException;

import mjs.home.model.*;


/*
 * This is the web service for user management.
 * 
 * @author mshoemake
 */
public class UserWebServiceImpl implements UserWebService 
{
   UserWebServiceConfiguration config = new UserWebServiceConfiguration();
   UserRequestManagerImpl reqManager = null; 

   /**
    * Constructor.
    */
   public UserWebServiceImpl()
   {
      try
      {
         reqManager = new UserRequestManagerImpl(config.getDatabaseDSN()); 
      }
      catch (java.lang.Exception e)
      {
         // @TODO Handle exception.
      }
   }
   
   /**
    * Get the specified user from the database.
    * 
    * @return User object.
    */
   public User getUser(String userName) throws RemoteException
   {
      try
      {
         return reqManager.getUser(userName);
      }
      catch (java.lang.Exception e)
      {
         throw createRemoteException(e);
      }
   }

   /**
    * Get the specified user from the database.
    * 
    * @return User object.
    */
   public User getUser(int userID) throws RemoteException
   {
      try
      {
         return reqManager.getUser(userID);
      }
      catch (java.lang.Exception e)
      {
         throw createRemoteException(e);
      }
   }


   /**
    * Get the list of users from the database.
    * 
    * @return UserList object.
    */
   public User[] getUserList() throws RemoteException
   {
      try
      {
         return reqManager.getUserList().toUserArray();   
      }
      catch (java.lang.Exception e)
      {
         throw createRemoteException(e);
      }
   }

   /**
    * Add a user to the database.
    * 
    * @param user  The user to add.
    */
   public void addUser(User user) throws RemoteException
   {  
      try
      {
         reqManager.addUser(user);   
      }
      catch (java.lang.Exception e)
      {
         throw createRemoteException(e);
      }
   }

   /**
    * Delete the user with this userName from the database.
    * 
    * @param userName
    * @throws RemoteException
    */
   public void deleteUser(String userName) throws RemoteException
   {  
      try
      {
         reqManager.deleteUser(userName);   
      }
      catch (java.lang.Exception e)
      {
         throw createRemoteException(e);
      }
   }

   /**
    * Delete the user with this userID from the database.
    * 
    * @param userID
    * @throws RemoteException
    */
   public void deleteUser(int userID) throws RemoteException
   {  
      try
      {
         reqManager.deleteUser(userID);   
      }
      catch (java.lang.Exception e)
      {
         throw createRemoteException(e);
      }
   }

   /**
    * Update a user's information in the database.
    * 
    * @param userName  The user to update.
    * @param user      The updated user information.
    * @throws RemoteException
    */   
   public void updateUser(String userName, User user) throws RemoteException
   {
      try
      {
         reqManager.updateUser(userName, user);   
      }
      catch (java.lang.Exception e)
      {
         throw createRemoteException(e);
      }
   }

   /**
    * Update a user's information in the database.
    * 
    * @param userID    The user to update.
    * @param user      The updated user information.
    * @throws RemoteException
    */   
   public void updateUser(int userID, User user) throws RemoteException  
   {
      try
      {
         reqManager.updateUser(userID, user);   
      }
      catch (java.lang.Exception e)
      {
         throw createRemoteException(e);
      }
   }


     
   // **************  END OF WEB SERVICE  **************

   /**
    * Creates a RemoteException object from a java.lang.AbstractServletException.
    * 
    * @param e  The java.lang.AbstractServletException to model.
    * @return   The remote exception.
    */
   private RemoteException createRemoteException(java.lang.Exception e)
   {
      RemoteException ex = new RemoteException(e.getMessage());
      ex.setStackTrace(e.getStackTrace());
      return ex;
   }
}
