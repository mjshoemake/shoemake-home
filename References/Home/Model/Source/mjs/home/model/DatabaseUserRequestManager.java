/* File name:     DatabaseUserRequestManager.java
 * Package name:  mjs.home.commands
 * Project name:  CmdServer
 * Date Created:  Mar 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.model;

// Java imports
import java.lang.StringBuffer;
import java.sql.ResultSet;

//	MJS imports
import mjs.core.communication.LoggingManager;
import mjs.core.database.DatabaseConnection;


/**
 * The request manager is a facade for the database access.  
 * It contains a single public method for each request, 
 * abstracting the details of the implementation from the
 * calling class.   
 * 
 * @author mshoemake
 */
public class DatabaseUserRequestManager implements UserRequestManager
{
   /**
    * Connection to the database.
    */
   private DatabaseConnection connection = null;

   /**
    * The logging manager for this servlet.
    */
   private LoggingManager logManager = null;

   // SORT ORDERS
   public static final int SORT_LASTNAME = 0;
   public static final int SORT_LASTNAME_DESC = 1;
   public static final int SORT_FIRSTNAME = 2;
   public static final int SORT_FIRSTNAME_DESC = 3;
   public static final int SORT_USERNAME = 4;
   public static final int SORT_USERNAME_DESC = 5;
   

   /**
    * Constructor.
    * 
    * @param urlDSN  The url of the DSN to use to connect to
    *                the database. 
    */
   public DatabaseUserRequestManager(String urlDSN) throws java.lang.Exception
   {
      try
      {
         connection = new DatabaseConnection();
         connection.openConnection(urlDSN);
      }
      catch (java.lang.Exception e)
      {
         throw e;
      }
   }

   /**
    * Constructor.
    * 
    * @param urlDSN  The url of the DSN to use to connect to
    *                the database.
    * @param logManager  The log manager used to write messages
    *                    to a trace file. 
    */
   public DatabaseUserRequestManager(String urlDSN, LoggingManager logManager)
   {
      this.logManager = logManager;
      try
      {
         connection = new DatabaseConnection();
         writeToLog("DatabaseUserRequestManager  Constructor()  BEGIN", LoggingManager.PRIORITY_DEBUG);
         writeToLog("DatabaseUserRequestManager  Constructor()  Database connection created.", LoggingManager.PRIORITY_DEBUG);
         writeToLog("DatabaseUserRequestManager  Constructor()  Attempting to open connection... ("+urlDSN+")", LoggingManager.PRIORITY_DEBUG);
         connection.openConnection(urlDSN);
         writeToLog("DatabaseUserRequestManager  Constructor()  Database connection opened.", LoggingManager.PRIORITY_DEBUG);
         writeToLog("DatabaseUserRequestManager  Constructor()  END", LoggingManager.PRIORITY_DEBUG);
      }
      catch (java.lang.Exception e)
      {
         writeToLog("DatabaseUserRequestManager  Constructor()  EXCEPTION THROWN.", LoggingManager.PRIORITY_DEBUG);
         writeToLog(e);         
      }
   }

   /**
    * Get the list of users from the database.
    * 
    * @return XML file as a String.
    */
   public UserList getUserList() throws java.lang.Exception  
   {
      return getUserList(SORT_LASTNAME);
   }   

   /**
    * Get the list of users from the database.
    * 
    * @return XML file as a String.
    */
   public UserList getUserList(int sortorder) throws java.lang.Exception  
   {
      writeToLog("DatabaseUserRequestManager  getUserList()  BEGIN", LoggingManager.PRIORITY_DEBUG);
      String query = "select user_pk, username, password, fname, lname from users";
      String orderby = null;      
      switch (sortorder)
      {
         case(SORT_FIRSTNAME):  
            writeToLog("DatabaseUserRequestManager  getUserList()  Sort by First Name.", LoggingManager.PRIORITY_DEBUG);
            orderby = " order by fname, lname";
            break;
         case(SORT_FIRSTNAME_DESC):  
            writeToLog("DatabaseUserRequestManager  getUserList()  Sort by First Name (descending).", LoggingManager.PRIORITY_DEBUG);
            orderby = " order by fname desc, lname desc";
            break;
         case(SORT_LASTNAME):  
            writeToLog("DatabaseUserRequestManager  getUserList()  Sort by Last Name.", LoggingManager.PRIORITY_DEBUG);
            orderby = " order by lname, fname";
            break;
         case(SORT_LASTNAME_DESC):  
            writeToLog("DatabaseUserRequestManager  getUserList()  Sort by Last Name (descending).", LoggingManager.PRIORITY_DEBUG);
            orderby = " order by lname desc, fname desc";
            break;
         case(SORT_USERNAME):  
            writeToLog("DatabaseUserRequestManager  getUserList()  Sort by Username.", LoggingManager.PRIORITY_DEBUG);
            orderby = " order by username, fname";
            break;
         case(SORT_USERNAME_DESC):  
            writeToLog("DatabaseUserRequestManager  getUserList()  Sort by Username (descending).", LoggingManager.PRIORITY_DEBUG);
            orderby = " order by username desc, fname desc";
            break;
         default:   
            writeToLog("DatabaseUserRequestManager  getUserList()  DEFAULT: Sort by Last Name.", LoggingManager.PRIORITY_DEBUG);
            orderby = " order by lname, fname";
            break;
      }      

      query += orderby;      
      ResultSet results = connection.executeQuery(query);
      writeToLog("DatabaseUserRequestManager  getUserList()  Query executed: "+query, LoggingManager.PRIORITY_DEBUG);
      User nextUser = null;
      try
      {
         UserList list = new UserList();
         while (results.next())
         {
            int pk = results.getInt(1);
            String userName = results.getString(2); 
            String password = results.getString(3); 
            String firstName = results.getString(4); 
            String lastName = results.getString(5); 
            nextUser = new User(pk, userName, password, firstName, lastName);
            list.asVector().add(nextUser);               
         }
         writeToLog("DatabaseUserRequestManager  getUserList()  # rows returned: "+list.asVector().size(), LoggingManager.PRIORITY_DEBUG);
         writeToLog("DatabaseUserRequestManager  getUserList()  END", LoggingManager.PRIORITY_DEBUG);
         return list;
      }
      catch (java.lang.Exception e)
      {
         // Throw AbstractServletException.
         writeToLog("DatabaseUserRequestManager  getUserList()  EXCEPTION THROWN.", LoggingManager.PRIORITY_DEBUG);
         throw e;         
      }
   }
   
   /**
    * Get the list of users from the database.  This command uses the 
    * user ID to specify the user.
    * 
    * @return object representing a User in the system.
    */
   public User getUser(int userID) throws java.lang.Exception  
   {
      writeToLog("DatabaseUserRequestManager  getUser("+userID+")  BEGIN", LoggingManager.PRIORITY_DEBUG);
      String query = "select user_pk, username, password, fname, lname from users where user_pk="+userID;
      ResultSet results = connection.executeQuery(query);
      writeToLog("DatabaseUserRequestManager  getUser("+userID+")  Query executed: "+query, LoggingManager.PRIORITY_DEBUG);

      try
      {
         if (results.next())
         {
            int pk = results.getInt(1);
            String userName = results.getString(2); 
            String password = results.getString(3); 
            String firstName = results.getString(4); 
            String lastName = results.getString(5); 
            writeToLog("DatabaseUserRequestManager  getUser("+userID+")  UserName="+userName+", Password="+password+", FirstName="+firstName+", LastName="+lastName+", PK="+pk, LoggingManager.PRIORITY_DEBUG);
            writeToLog("DatabaseUserRequestManager  getUser"+userID+")  END", LoggingManager.PRIORITY_DEBUG);
            return new User(pk, userName, password, firstName, lastName);
         }
         
         // Otherwise, return null (empty result set).
         writeToLog("DatabaseUserRequestManager  getUser("+userID+")  NO DATA FOUND.", LoggingManager.PRIORITY_DEBUG);
         writeToLog("DatabaseUserRequestManager  getUser("+userID+")  END", LoggingManager.PRIORITY_DEBUG);
         return null;
      }
      catch (java.lang.Exception e)
      {
         // Throw AbstractServletException.
         writeToLog("DatabaseUserRequestManager  getUser("+userID+")  EXCEPTION THROWN.", LoggingManager.PRIORITY_DEBUG);
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
      writeToLog("DatabaseUserRequestManager  addUser("+user.getUserName()+")  BEGIN", LoggingManager.PRIORITY_DEBUG);
      String query = "insert into users (username, password, fname, lname) values ('"+user.getUserName()+"', '"+user.getPassword()+"', '"+user.getFirstName()+"', '"+user.getLastName()+"')";
      writeToLog("DatabaseUserRequestManager  addUser()  Query: "+query, LoggingManager.PRIORITY_DEBUG);

      try
      {
         connection.executeStatement(query);
         writeToLog("DatabaseUserRequestManager  addUser()  User added successfully.", LoggingManager.PRIORITY_DEBUG);
      }
      catch (java.lang.Exception e)
      {
         // Unsuccessful.
         writeToLog("DatabaseUserRequestManager  addUser()  EXCEPTION THROWN.", LoggingManager.PRIORITY_DEBUG);
         throw e;
      }
   }
   
   /**
    * Delete all specified users from the database.
    * 
    * @param userList  The user IDs to delete.
    * @throws java.lang.AbstractServletException
    */   
   public void deleteUsers(String[] userList) throws java.lang.Exception  
   {
      writeToLog("DatabaseUserRequestManager  deleteUsers()  BEGIN", LoggingManager.PRIORITY_DEBUG);

      // The list of IDs to delete.      
      StringBuffer idList = new StringBuffer();
      
      // Start with the first one in the list.
      for (int C=0; C <= userList.length-1; C++)
      {
         if (C == 0)
         {
            // First one in the list.  No comma...
            idList.append(userList[C]); 
         }
         else
         {
            // Prepend comma and space...
            idList.append(", "+userList[C]);
         }
      }
      
      String query = "delete from users where user_pk IN ("+idList.toString()+")";

      try
      {
         writeToLog("DatabaseUserRequestManager  deleteUsers()  Query: "+query, LoggingManager.PRIORITY_DEBUG);
         connection.executeStatement(query);
         writeToLog("DatabaseUserRequestManager  deleteUsers()  Users deleted successfully.", LoggingManager.PRIORITY_DEBUG);
      }
      catch (java.lang.Exception e)
      {
         // Unsuccessful.
         writeToLog("DatabaseUserRequestManager  deleteUsers()  EXCEPTION THROWN.", LoggingManager.PRIORITY_DEBUG);
         throw e;
      }
      writeToLog("DatabaseUserRequestManager  deleteUsers()  END", LoggingManager.PRIORITY_DEBUG);
   }
   
   /**
    * Delete all users from the database.
    * 
    * @throws java.lang.AbstractServletException
    */   
   public void deleteAllUsers() throws java.lang.Exception
   {  
      writeToLog("DatabaseUserRequestManager  deleteAllUsers()  BEGIN", LoggingManager.PRIORITY_DEBUG);
      
      String query = "delete from users where user_pk > -1";

      try
      {
         writeToLog("DatabaseUserRequestManager  deleteAllUsers()  Query: "+query, LoggingManager.PRIORITY_DEBUG);
         connection.executeStatement(query);
         writeToLog("DatabaseUserRequestManager  deleteAllUsers()  Users deleted successfully.", LoggingManager.PRIORITY_DEBUG);
      }
      catch (java.lang.Exception e)
      {
         // Unsuccessful.
         writeToLog("DatabaseUserRequestManager  deleteAllUsers()  EXCEPTION THROWN.", LoggingManager.PRIORITY_DEBUG);
         throw e;
      }
      writeToLog("DatabaseUserRequestManager  deleteAllUsers()  END", LoggingManager.PRIORITY_DEBUG);
   }

   /**
    * Delete a user from the database.  This command users the 
    * user ID to specify the user.
    * 
    * @param userID  The user to delete.
    * @throws java.lang.AbstractServletException
    */   
   public void deleteUser(int userID) throws java.lang.Exception  
   {
      writeToLog("DatabaseUserRequestManager  deleteUser("+userID+")  BEGIN", LoggingManager.PRIORITY_DEBUG);
      String query = "delete from users where user_pk="+userID;

      try
      {
         writeToLog("DatabaseUserRequestManager  deleteUser("+userID+")  Query: "+query, LoggingManager.PRIORITY_DEBUG);
         connection.executeStatement(query);
         writeToLog("DatabaseUserRequestManager  deleteUser("+userID+")  User deleted successfully.", LoggingManager.PRIORITY_DEBUG);
      }
      catch (java.lang.Exception e)
      {
         // Unsuccessful.
         writeToLog("DatabaseUserRequestManager  deleteUser("+userID+")  EXCEPTION THROWN.", LoggingManager.PRIORITY_DEBUG);
         throw e;
      }
      writeToLog("DatabaseUserRequestManager  deleteUser("+userID+")  END", LoggingManager.PRIORITY_DEBUG);
   }
   
   /**
    * Update a user's information in the database.
    * 
    * @param userID    The user to update.
    * @param user      The user's updated information.
    * @throws java.lang.AbstractServletException
    */   
   public void updateUser(int userID, User user) throws java.lang.Exception  
   {
      writeToLog("DatabaseUserRequestManager  updateUser("+user.getUserName()+")  BEGIN", LoggingManager.PRIORITY_DEBUG);
      String query = "update users set password='"+user.getPassword()+"', fname='"+user.getFirstName()+"', username='"+user.getUserName()+"', lname='"+user.getLastName()+"' where user_pk="+userID;
      writeToLog("DatabaseUserRequestManager  updateUser()  Query: "+query, LoggingManager.PRIORITY_DEBUG);

      try
      {
         connection.executeStatement(query);
         writeToLog("DatabaseUserRequestManager  updateUser()  User updated successfully.", LoggingManager.PRIORITY_DEBUG);
      }
      catch (java.lang.Exception e)
      {
         // Unsuccessful.
         writeToLog("DatabaseUserRequestManager  updateUser()  EXCEPTION THROWN", LoggingManager.PRIORITY_DEBUG);
         throw e;
      }
      writeToLog("DatabaseUserRequestManager  updateUser()  END", LoggingManager.PRIORITY_DEBUG);
   }

   /**
    * Set the logging priority for the LoggingManager.
    */
   public void setLoggingPriority(int priority)
   {
      if (logManager != null)
         logManager.setLoggingPriority(priority);
   }

   /**
    * Write message to log file.
    */
   public void writeToLog(String message, int priority)
   {
      if (logManager != null)
         logManager.writeToLog(message, priority);
   }

   /**
    * Write message to log file.
    */
   public void writeToLog(java.lang.Exception e)
   {
      if (logManager != null)
         logManager.writeToLog(e);
   }
   
}
