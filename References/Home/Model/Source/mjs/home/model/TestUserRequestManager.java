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
import java.util.Enumeration;
import java.util.Vector;

//	MJS imports
import mjs.core.aggregation.Hashtable;
import mjs.core.aggregation.SortedVector;
import mjs.core.aggregation.StringListItemFactory;
import mjs.core.aggregation.ListItem;
import mjs.core.communication.LoggingManager;


/**
 * The request manager is a facade for the database access.  
 * It contains a single public method for each request, 
 * abstracting the details of the implementation from the
 * calling class.
 * <p>
 * This request manager keeps an in-memory list of all users in 
 * the system and allows the system to be tested without a 
 * live database. 
 * 
 * @author mshoemake
 */
public class TestUserRequestManager implements UserRequestManager
{
   /**
    * The logging manager for this servlet.
    */
   private LoggingManager logManager = null;
   
   /**
    * The in memory storage 
    */
   private Hashtable hashList = new Hashtable(); 

   /**
    * The in memory storage 
    */
   private int nextPK = 15; 


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
   public TestUserRequestManager(String urlDSN) throws java.lang.Exception
   {
      populateUsers();      
   }
   
   /**
    * Constructor.
    * 
    * @param urlDSN  The url of the DSN to use to connect to
    *                the database.
    * @param logManager  The log manager used to write messages
    *                    to a trace file. 
    */
   public TestUserRequestManager(String urlDSN, LoggingManager logManager)
   {
      this.logManager = logManager;
      try
      {
         writeToLog("TestUserRequestManager  Constructor()  BEGIN", LoggingManager.PRIORITY_DEBUG);
         populateUsers();      
         writeToLog("TestUserRequestManager  Constructor()  Test database created.", LoggingManager.PRIORITY_DEBUG);
         writeToLog("TestUserRequestManager  Constructor()  END", LoggingManager.PRIORITY_DEBUG);
      }
      catch (java.lang.Exception e)
      {
         writeToLog("TestUserRequestManager  Constructor()  EXCEPTION THROWN.", LoggingManager.PRIORITY_DEBUG);
         writeToLog(e);         
      }
   }

   /**
    * This method autopopulates the list of users so we don't
    * start with a clean slate.
    * 
    * @throws java.lang.Exception
    */
   private void populateUsers() throws java.lang.Exception
   {
      try
      {
         // Prepopulate the list of users.
         User user = null;
         user = new User(1, "mshoemake", "password", "Mike", "Shoemake");
         hashList.add(user.getUserID(), user);
         user = new User(2, "gjetson", "password", "George", "Jetson");
         hashList.add(user.getUserID(), user);
         user = new User(3, "jjetson", "password", "Jane", "Jetson");
         hashList.add(user.getUserID(), user);
         user = new User(4, "ejetson", "password", "Elroy", "Jetson");
         hashList.add(user.getUserID(), user);
         user = new User(5, "fflintstone", "password", "Fred", "Flintstone");
         hashList.add(user.getUserID(), user);
         user = new User(6, "wflintstone", "password", "Wilma", "Flintstone");
         hashList.add(user.getUserID(), user);
         user = new User(7, "pflintstone", "password", "Pebbles", "Flintstone");
         hashList.add(user.getUserID(), user);
         user = new User(8, "barubble", "password", "Barney", "Rubble");
         hashList.add(user.getUserID(), user);
         user = new User(9, "berubble", "password", "Betty", "Rubble");
         hashList.add(user.getUserID(), user);
         user = new User(10, "jquest", "password", "Johnny", "Quest");
         hashList.add(user.getUserID(), user);
         user = new User(11, "bspacely", "password", "Bob", "Spacely");
         hashList.add(user.getUserID(), user);
      }
      catch (java.lang.Exception e)
      {
         throw e;
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
      String sortBy = null;
      String sortValue = null;
      int sortField = sortorder;
      boolean ascending = true;
      
      writeToLog("TestUserRequestManager  getUserList()  BEGIN", LoggingManager.PRIORITY_DEBUG);
      try
      {
         // Set the sort field.
         if (sortField == SORT_FIRSTNAME_DESC)
         {
            sortField = SORT_FIRSTNAME;
            ascending = false;
         }
         if (sortField == SORT_LASTNAME_DESC)
         {
            sortField = SORT_LASTNAME;
            ascending = false;
         }
         if (sortField == SORT_USERNAME_DESC)
         {
            sortField = SORT_USERNAME;
            ascending = false;
         }
         
         // Prepare to sort the list.
         SortedVector sortlist = new SortedVector(ascending, false, new StringListItemFactory(), logManager);
         writeToLog("TestUserRequestManager  getUserList()  Created SortedVector...", LoggingManager.PRIORITY_DEBUG);
                  
         Enumeration enum = hashList.elements();
         
         while (enum.hasMoreElements())
         {
            User user = (User)(enum.nextElement());
            writeToLog("TestUserRequestManager  getUserList()  Processing "+user.getUserName()+"...", LoggingManager.PRIORITY_DEBUG);
            switch (sortField)
            {
               case(SORT_FIRSTNAME):
                  if (sortBy == null)
                  {
                     // Populate sortBy for use by logging.
                     sortBy = "First Name";                  
                  }
                  sortValue = user.getFirstName()+" "+user.getLastName();
                  break;
                  
               case(SORT_USERNAME):  
                  if (sortBy == null)
                  {
                     // Populate sortBy for use by logging.
                     sortBy = "Login Name";                  
                  }
                  sortValue = user.getUserName()+" "+user.getFirstName();
                  break;

               // DEFAULT TO LAST NAME
               default:   
                  if (sortBy == null)
                  {
                     // Populate sortBy for use by logging.
                     sortBy = "Last Name";                  
                  }
                  sortValue = user.getLastName()+" "+user.getFirstName();
                  break;
            }      

            // Add the user to the list.
            sortlist.add(sortValue.trim(), sortValue.trim(), user);                  
         }
         
         // Sort the list.
         writeToLog("TestUserRequestManager  getUserList()  Sorting the list...", LoggingManager.PRIORITY_DEBUG);
         sortlist.setSorted(true);         
         writeToLog("TestUserRequestManager  getUserList()  The list is sorted by "+sortBy+"...", LoggingManager.PRIORITY_DEBUG);
         
         // Convert the SortedVector to a Vector;
         UserList userList = new UserList();
         Vector vector = new Vector();
         if (ascending)
         {
            // Ascending Sort.
            for (int C=0; C <= sortlist.size()-1; C++)
            {
               ListItem next = (ListItem)(sortlist.get(C));
               vector.add(next.getObjectReference());
            }
         }
         else
         {
            // Descending Sort.
            for (int C=sortlist.size()-1; C >= 0; C--)
            {
               ListItem next = (ListItem)(sortlist.get(C));
               vector.add(next.getObjectReference());
            }
         }
         
         userList.setUserList(vector);
         writeToLog("TestUserRequestManager  getUserList()  # rows returned: "+userList.asVector().size(), LoggingManager.PRIORITY_DEBUG);
         writeToLog("TestUserRequestManager  getUserList()  END", LoggingManager.PRIORITY_DEBUG);
         return userList;
      }
      catch (java.lang.Exception e)
      {
         // Throw AbstractServletException.
         writeToLog("TestUserRequestManager  getUserList()  EXCEPTION THROWN.", LoggingManager.PRIORITY_DEBUG);
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
      writeToLog("TestUserRequestManager  getUser("+userID+")  BEGIN", LoggingManager.PRIORITY_DEBUG);
      User user = (User)(hashList.get(userID));    
      writeToLog("TestUserRequestManager  getUser("+userID+")  UserName="+user.getUserName()+", Password="+user.getPassword()+", FirstName="+user.getFirstName()+", LastName="+user.getLastName()+", PK="+user.getUserID(), LoggingManager.PRIORITY_DEBUG);
      writeToLog("TestUserRequestManager  getUser"+userID+")  END", LoggingManager.PRIORITY_DEBUG);
      return user;
   }

   /**
    * Add a user to the database.
    * 
    * @param user  The user to add.
    * @throws java.lang.AbstractServletException
    */   
   public void addUser(User user) throws java.lang.Exception  
   {
      writeToLog("TestUserRequestManager  addUser("+user.getUserName()+")  BEGIN", LoggingManager.PRIORITY_DEBUG);
      nextPK++; 
      hashList.add(nextPK, user);
      user.setUserID(nextPK);  
      writeToLog("TestUserRequestManager  addUser("+user.getUserName()+")  END", LoggingManager.PRIORITY_DEBUG);
   }
   
   /**
    * Delete all specified users from the database.
    * 
    * @param userList  The user IDs to delete.
    * @throws java.lang.AbstractServletException
    */   
   public void deleteUsers(String[] userList) throws java.lang.Exception  
   {
      writeToLog("TestUserRequestManager  deleteUsers()  BEGIN", LoggingManager.PRIORITY_DEBUG);

      // The list of IDs to delete.      
      StringBuffer idList = new StringBuffer();
      
      // Start with the first one in the list.
      for (int C=0; C <= userList.length-1; C++)
      {
         // Remove this user from the list.
         hashList.remove(Integer.parseInt(userList[C]));
      }
      writeToLog("TestUserRequestManager  deleteUsers()  END", LoggingManager.PRIORITY_DEBUG);
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
      writeToLog("TestUserRequestManager  deleteUser("+userID+")  BEGIN", LoggingManager.PRIORITY_DEBUG);
      // Remove this user from the list.
      hashList.remove(userID);
      writeToLog("TestUserRequestManager  deleteUser("+userID+")  END", LoggingManager.PRIORITY_DEBUG);
   }
   
   /**
    * Delete all users from the database.
    * 
    * @throws java.lang.AbstractServletException
    */   
   public void deleteAllUsers() throws java.lang.Exception
   {
      writeToLog("TestUserRequestManager  deleteAllUsers()  BEGIN", LoggingManager.PRIORITY_DEBUG);
      // Remove all users form this list.
      hashList.clear();
      writeToLog("TestUserRequestManager  deleteAllUsers()  END", LoggingManager.PRIORITY_DEBUG);
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
      writeToLog("TestUserRequestManager  updateUser("+user.getUserName()+")  BEGIN", LoggingManager.PRIORITY_DEBUG);
      // Remove this user from the list.
      hashList.remove(userID);
      // Re-add with new information.
      hashList.add(userID, user);

      writeToLog("TestUserRequestManager  updateUser()  END", LoggingManager.PRIORITY_DEBUG);
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
