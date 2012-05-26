/* File name:     MainFrame.java
 * Package name:  mjs.jdbctest
 * Project name:  CmdServer
 * Date Created:  Mar 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.jdbctest;

// Java imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.home.commands.users.UserWebService;
import mjs.home.commands.users.UserWebServiceImpl;
import mjs.home.commands.users.client.SoapUserRequestManager;
import mjs.home.model.User;
import mjs.home.model.UserList;
import mjs.home.struts.Constants;


public class MainFrame extends JFrame implements ActionListener
{
   private JPanel contentPane;
   private JMenuBar menuBar = new JMenuBar();
   // Menus
   private JMenu menuLocal = new JMenu();
   private JMenu menuSoap = new JMenu();
   private JMenu menuStruts = new JMenu();
   // Menu Items
   private JMenuItem itemAddUser = new JMenuItem();
   private JMenuItem itemDeleteUser = new JMenuItem();
   private JMenuItem itemUpdateUser = new JMenuItem();
   private JMenuItem itemGetUserList = new JMenuItem();
   private JMenuItem itemGetUser = new JMenuItem();
   private JMenuItem itemExit = new JMenuItem();
   private JMenuItem itemSoapAddUser = new JMenuItem();
   private JMenuItem itemSoapDeleteUser = new JMenuItem();
   private JMenuItem itemSoapUpdateUser = new JMenuItem();
   private JMenuItem itemSoapGetUserList = new JMenuItem();
   private JMenuItem itemSoapGetUser = new JMenuItem();
   private JMenuItem itemStrutsGetUserList = new JMenuItem();
   // Other
   private JLabel statusBar = new JLabel();
   private BorderLayout borderLayout1 = new BorderLayout();
   private JTextArea resultsView = new JTextArea();
   private JScrollPane resultsScroll = new JScrollPane();
   // Settings   
   private int numUpdates = 0;

   
   /**
    * The logging manager.
    */
   private LoggingManager logManager = new LoggingManager("C:\\LogFiles\\CmdServer.log", "CmdServer", LoggingManager.PRIORITY_DEBUG);
   
   /**
    * The request manager.
    * <p>
    * This request manager requires that the database is local
    * and does NOT use SOAP calls.
    */
   private UserWebService webService = new UserWebServiceImpl();
   
   /**
    * The SOAP client request manager.
    * <p>
    * This request manager actually makes a SOAP call to 
    * interact with the database.
    */
   private SoapUserRequestManager soapReqManager = new SoapUserRequestManager(); 

   /**
    * Constructor.
    */
   public MainFrame()
   {
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      try
      {
         jbInit();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }

   /**Component initialization*/
   private void jbInit() throws Exception
   {
      contentPane = (JPanel) this.getContentPane();
      contentPane.setLayout(borderLayout1);
      this.setSize(new Dimension(400, 300));
      this.setTitle("JDBC Test Application");
      statusBar.setText(" ");

      // QUERIES MENU
      menuLocal.setText("Local");
      menuSoap.setText("Soap");
      menuStruts.setText("Struts");

      // Items
      itemGetUserList.setText("GetUserList");
      itemGetUserList.addActionListener(this);
      itemAddUser.setText("AddUser");
      itemAddUser.addActionListener(this);
      itemDeleteUser.setText("DeleteUser");
      itemDeleteUser.addActionListener(this);
      itemGetUser.setText("GetUser");
      itemGetUser.addActionListener(this);
      itemUpdateUser.setText("UpdateUser");
      itemUpdateUser.addActionListener(this);
      itemExit.setText("Exit");
      itemExit.addActionListener(this);
      itemSoapGetUserList.setText("GetUserList");
      itemSoapGetUserList.addActionListener(this);
      itemSoapAddUser.setText("AddUser");
      itemSoapAddUser.addActionListener(this);
      itemSoapDeleteUser.setText("DeleteUser");
      itemSoapDeleteUser.addActionListener(this);
      itemSoapGetUser.setText("GetUser");
      itemSoapGetUser.addActionListener(this);
      itemSoapUpdateUser.setText("UpdateUser");
      itemSoapUpdateUser.addActionListener(this);
      itemStrutsGetUserList.setText("GetUserList");
      itemStrutsGetUserList.addActionListener(this);

      menuBar.add(menuLocal);
      menuBar.add(menuSoap);
      menuBar.add(menuStruts);
      menuLocal.add(itemAddUser);
      menuLocal.add(itemDeleteUser);
      menuLocal.add(itemGetUser);
      menuLocal.add(itemGetUserList);
      menuLocal.add(itemUpdateUser);
      menuLocal.add(itemExit);
      menuSoap.add(itemSoapAddUser);
      menuSoap.add(itemSoapDeleteUser);
      menuSoap.add(itemSoapGetUser);
      menuSoap.add(itemSoapGetUserList);
      menuSoap.add(itemSoapUpdateUser);
      menuStruts.add(itemStrutsGetUserList);
      this.setJMenuBar(menuBar);
      resultsScroll.add(resultsView);
      resultsScroll.setViewportView(resultsView);
      contentPane.add(resultsScroll, BorderLayout.CENTER);
      contentPane.add(statusBar, BorderLayout.SOUTH);
   }

   public void actionPerformed(ActionEvent event)
   {
      try
      {
         String xml = null;  
         if (event.getSource().equals(itemExit))
         {
            // Close the application.
            closeApplication();
         }
         else if (event.getSource().equals(itemGetUserList))
         {
            getUserList(webService);             
            statusBar.setText("Query executed.");
         }
         else if (event.getSource().equals(itemGetUser))
         {
            getUser(webService, "mshoemake");
            getUser(webService, "fflintstone");
            getUser(webService, "ted");
            statusBar.setText("Query executed.");
         }
         else if (event.getSource().equals(itemAddUser))
         {
            User user = new User(0, "testuser1", "1234", "Test", "User1");
            addUser(webService, user);
            statusBar.setText("Query executed.");
         }
         else if (event.getSource().equals(itemDeleteUser))
         {
            deleteUser(webService, "testuser1");
            statusBar.setText("Query executed.");
         }
         else if (event.getSource().equals(itemUpdateUser))
         {
            User user = getUser(webService, "testuser1");
            ++numUpdates;
            user.setLastName("User"+numUpdates);
            updateUser(webService, user, user.getUserID());
            statusBar.setText("Query executed.");
         }
         else if (event.getSource().equals(itemSoapGetUserList))
         {
            getSoapUserList(soapReqManager);             
            statusBar.setText("Query executed.");
         }
         else if (event.getSource().equals(itemSoapAddUser))
         {
            User user = new User(0, "testuser1", "1234", "Test", "User1");
            addSoapUser(soapReqManager, user);
            statusBar.setText("Query executed.");
         }
         else if (event.getSource().equals(itemSoapDeleteUser))
         {
            deleteSoapUser(soapReqManager, "testuser1");
            statusBar.setText("Query executed.");
         }
         else if (event.getSource().equals(itemSoapGetUser))
         {
            getSoapUser(soapReqManager, "mshoemake");
            getSoapUser(soapReqManager, "fflintstone");
            getSoapUser(soapReqManager, "ted");
            statusBar.setText("Query executed.");
         }
         else if (event.getSource().equals(itemSoapUpdateUser))
         {
            User user = getSoapUser(soapReqManager, "testuser1");
            ++numUpdates;
            user.setLastName("User"+numUpdates);
            updateSoapUser(soapReqManager, user, user.getUserID());
            statusBar.setText("Query executed.");
         }
         else if (event.getSource().equals(itemStrutsGetUserList))
         {
            // Get the user list.
            User[] list = Constants.userReqManager.getUserList().toUserArray();
         
            // Display the results.
            for (int C=0; C <= list.length-1; C++)
            {
               User user = list[C];
               writeLog("User:");
               writeLog("   ID: "+user.getUserID());
               writeLog("   UserName: "+user.getUserName());
               writeLog("   Password: "+user.getPassword());
               writeLog("   First Name: "+user.getFirstName());
               writeLog("   Last Name: "+user.getLastName());
               writeLog("");
            }
         }
      }
      catch (java.lang.Exception e)
      {
         // Log exception.
         if (logManager != null)  
            logManager.writeToLog(e);
      }
   }

   public void closeApplication()
   {
      contentPane.remove(statusBar);
      this.setJMenuBar(null);
      contentPane = null;
      menuBar.remove(menuLocal);
      menuLocal.remove(itemExit);
      menuLocal.remove(itemGetUserList);
      menuLocal.remove(itemGetUser);
      menuLocal = null;
      itemExit = null;
      itemGetUserList = null;
      itemGetUser = null;
      statusBar = null;
      borderLayout1 = null;
      System.exit(0);
   }

   /**Overridden so we can exit when window is closed*/
   protected void processWindowEvent(WindowEvent e)
   {
      super.processWindowEvent(e);
      if (e.getID() == WindowEvent.WINDOW_CLOSING)
      {
         closeApplication();
      }
   }

   /**
    * Get the userlist and display the output.
    * 
    * @param webService  The web service component to use.
    */   
   private void getUserList(UserWebService webService)
   {
      try
      {
         // Get the user list.
         User[] list = webService.getUserList();
         
         // Display the results.
         for (int C=0; C <= list.length-1; C++)
         {
            User user = list[C];
            writeLog("User:");
            writeLog("   ID: "+user.getUserID());
            writeLog("   UserName: "+user.getUserName());
            writeLog("   Password: "+user.getPassword());
            writeLog("   First Name: "+user.getFirstName());
            writeLog("   Last Name: "+user.getLastName());
            writeLog("");
         }
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Get the userlist and display the output.
    * 
    * @param reqManager  The request manager to use as a proxy.
    */   
   private void getSoapUserList(SoapUserRequestManager reqManager)
   {
      try
      {
         // Get the user list.
         UserList list = reqManager.getUserList();
         
         // Display the results.
         for (int C=0; C <= list.getUserList().size()-1; C++)
         {
            User user = (User)(list.getUserList().get(C));
            writeLog("User:");
            writeLog("   ID: "+user.getUserID());
            writeLog("   UserName: "+user.getUserName());
            writeLog("   Password: "+user.getPassword());
            writeLog("   First Name: "+user.getFirstName());
            writeLog("   Last Name: "+user.getLastName());
            writeLog("");
         }
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Add this user to the database.
    * 
    * @param webService  The web service component to use.
    * @param user        The user to add.
    */   
   private void addUser(UserWebService webService, User user)
   {
      try
      {
         // Get the user list.
         webService.addUser(user);
         writeLog("ADD USER:");
         writeLog("   UserName: "+user.getUserName());
         writeLog("   Password: "+user.getPassword());
         writeLog("   First Name: "+user.getFirstName());
         writeLog("   Last Name: "+user.getLastName());
         writeLog("");
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Add this user to the database.
    * 
    * @param reqManager  The request manager to use as a proxy.
    * @param user        The user to add.
    */   
   private void addSoapUser(SoapUserRequestManager reqManager, User user)
   {
      try
      {
         // Add the user.
         reqManager.addUser(user);
         writeLog("ADD USER:");
         writeLog("   UserName: "+user.getUserName());
         writeLog("   Password: "+user.getPassword());
         writeLog("   First Name: "+user.getFirstName());
         writeLog("   Last Name: "+user.getLastName());
         writeLog("");
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Add this user to the database.
    * 
    * @param webService  The web service component to use.
    * @param user        The updated user information.
    * @param userName    The username of the user to update. 
    */   
   private void updateUser(UserWebService webService, User user, String username)
   {
      try
      {
         // Get the user list.
         webService.updateUser(username, user);
         writeLog("UPDATE USER:  "+username);
         writeLog("   UserName: "+user.getUserName());
         writeLog("   Password: "+user.getPassword());
         writeLog("   First Name: "+user.getFirstName());
         writeLog("   Last Name: "+user.getLastName());
         writeLog("");
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Add this user to the database.
    * 
    * @param reqManager  The request manager to use as a proxy.
    * @param user        The updated user information.
    * @param userName    The username of the user to update. 
    */   
   private void updateSoapUser(SoapUserRequestManager reqManager, User user, String username)
   {
      try
      {
         // Get the user list.
         reqManager.updateUser(username, user);
         writeLog("UPDATE USER:  "+username);
         writeLog("   UserName: "+user.getUserName());
         writeLog("   Password: "+user.getPassword());
         writeLog("   First Name: "+user.getFirstName());
         writeLog("   Last Name: "+user.getLastName());
         writeLog("");
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Add this user to the database.
    * 
    * @param webService  The web service component to use.
    * @param user        The updated user information.
    * @param userID      The user pk of the user to update. 
    */   
   private void updateUser(UserWebService webService, User user, int userID)
   {
      try
      {
         // Get the user list.
         webService.updateUser(userID, user);
         writeLog("UPDATE USER:  "+userID);
         writeLog("   UserName: "+user.getUserName());
         writeLog("   Password: "+user.getPassword());
         writeLog("   First Name: "+user.getFirstName());
         writeLog("   Last Name: "+user.getLastName());
         writeLog("");
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Add this user to the database.
    * 
    * @param reqManager  The request manager to use as a proxy.
    * @param user        The updated user information.
    * @param userID      The user pk of the user to update. 
    */   
   private void updateSoapUser(SoapUserRequestManager reqManager, User user, int userID)
   {
      try
      {
         // Get the user list.
         reqManager.updateUser(userID, user);
         writeLog("UPDATE USER:  "+userID);
         writeLog("   UserName: "+user.getUserName());
         writeLog("   Password: "+user.getPassword());
         writeLog("   First Name: "+user.getFirstName());
         writeLog("   Last Name: "+user.getLastName());
         writeLog("");
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Delete this user from this database.
    * 
    * @param webService  The web service component to use.
    */   
   private void deleteUser(UserWebService webService, String username)
   {
      try
      {
         // Get the user list.
         webService.deleteUser(username);
         writeLog("DELETE USER:");
         writeLog("   UserName: "+username);
         writeLog("");
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Delete this user from this database.
    * 
    * @param webService  The web service component to use.
    */   
   private void deleteSoapUser(SoapUserRequestManager reqManager, String username)
   {
      try
      {
         // Get the user list.
         reqManager.deleteUser(username);
         writeLog("DELETE USER:");
         writeLog("   UserName: "+username);
         writeLog("");
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Get the userlist and display the output.
    * 
    * @param webService  The web service component to use.
    */   
   private User getUser(UserWebService webService, String username)
   {
      try
      {
         // Get the specified user.
         User user = webService.getUser(username);
         
         if (user != null)
         {
            // Display the results.
            writeLog("Requested User:  "+username);
            writeLog("   ID: "+user.getUserID());
            writeLog("   UserName: "+user.getUserName());
            writeLog("   Password: "+user.getPassword());
            writeLog("   First Name: "+user.getFirstName());
            writeLog("   Last Name: "+user.getLastName());
            writeLog("");
         }
         else
         {
            writeLog("Requested User:  "+username);
            writeLog("   USER NOT FOUND");
            writeLog("");
         }
         return user;
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
         return null;
      }
   }
   
   /**
    * Get the userlist and display the output.
    * 
    * @param webService  The web service component to use.
    */   
   private User getSoapUser(SoapUserRequestManager reqManager, String username)
   {
      try
      {
         // Get the specified user.
         User user = reqManager.getUser(username);
         
         if (user != null)
         {
            // Display the results.
            writeLog("Requested User:  "+username);
            writeLog("   ID: "+user.getUserID());
            writeLog("   UserName: "+user.getUserName());
            writeLog("   Password: "+user.getPassword());
            writeLog("   First Name: "+user.getFirstName());
            writeLog("   Last Name: "+user.getLastName());
            writeLog("");
         }
         else
         {
            writeLog("Requested User:  "+username);
            writeLog("   USER NOT FOUND");
            writeLog("");
         }
         return user;
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
         return null;
      }
   }
   
   private void writeLog(String message)
   {
      resultsView.setLineWrap(true);
      resultsView.append(message+"\n");
   }
}