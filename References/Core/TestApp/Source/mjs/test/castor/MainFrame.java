/* File name:     MainFrame.java
 * Package name:  mjs.jdbctest
 * Project name:  CmdServer
 * Date Created:  Mar 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.test.castor;


// Java imports
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Locale;
import javax.swing.*;

// MJS imports
import mjs.core.communication.JarFileExtractor;
import mjs.core.communication.LoggingManager;
import mjs.core.xml.resourceBundle.MessageResourceBundle;
import mjs.core.xml.resourceBundle.MessageResource;
import mjs.core.xml.resourceBundle.Localization;
import mjs.core.xml.CastorObjectConverter;
import mjs.home.model.UserList;
import mjs.home.model.User;
import mjs.test.xalan.XSLTest;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class MainFrame extends JFrame implements ActionListener
{
   private JPanel contentPane;
   private JMenuBar menuBar = new JMenuBar();

   // Menus
   private JMenu menuActions = new JMenu();
   private JMenu menuGeneral = new JMenu();

   // Menu Items
   private JMenuItem itemUnmarshal = new JMenuItem();
   private JMenuItem itemUnmarshalMapping = new JMenuItem();
   private JMenuItem itemMarshalUserList = new JMenuItem();
   private JMenuItem itemUserListXSLT = new JMenuItem();
   private JMenuItem itemExit = new JMenuItem();
   private JMenuItem itemLocaleTest = new JMenuItem();
   private JMenuItem itemLoggingTest = new JMenuItem();

   // Other
   private JLabel statusBar = new JLabel();
   private BorderLayout borderLayout1 = new BorderLayout();
   private JTextArea resultsView = new JTextArea();
   private JScrollPane resultsScroll = new JScrollPane();

   // Settings   
   private int numUpdates = 0;
   private int numLogs = 0;

   /**
    * The logging manager.
    */
   private LoggingManager logManager = new LoggingManager("C:\\LogFiles\\TestApp.log", "Core",
         LoggingManager.PRIORITY_DEBUG);

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
      catch (Exception e)
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
      this.setTitle("Test Application");
      statusBar.setText(" ");

      // Main Menu
      menuActions.setText("Actions");
      menuGeneral.setText("General");

      // Items
      itemUnmarshal.setText("Unmarshal Resource Bundle");
      itemUnmarshal.addActionListener(this);
      itemUnmarshalMapping.setText("Unmarshal User (mapped)");
      itemUnmarshalMapping.addActionListener(this);
      itemMarshalUserList.setText("Marshal UserList (mapped)");
      itemMarshalUserList.addActionListener(this);
      itemUserListXSLT.setText("UserList XSLT Transformation");
      itemUserListXSLT.addActionListener(this);
      itemExit.setText("Exit");
      itemExit.addActionListener(this);
      itemLocaleTest.setText("Locale Test");
      itemLocaleTest.addActionListener(this);
      itemLoggingTest.setText("Logging Test");
      itemLoggingTest.addActionListener(this);

      menuBar.add(menuActions);
      menuBar.add(menuGeneral);
      menuActions.add(itemUnmarshal);
      menuActions.add(itemUnmarshalMapping);
      menuActions.add(itemMarshalUserList);
      menuActions.add(itemUserListXSLT);
      menuActions.add(itemExit);
      menuGeneral.add(itemLocaleTest);
      menuGeneral.add(itemLoggingTest);
      this.setJMenuBar(menuBar);
      resultsScroll.add(resultsView);
      resultsScroll.setViewportView(resultsView);
      contentPane.add(resultsScroll, BorderLayout.CENTER);
      contentPane.add(statusBar, BorderLayout.SOUTH);
   }

   /**
    * DOCUMENT ME!
    *
    * @param event DOCUMENT ME!
    */
   public void actionPerformed(ActionEvent event)
   {
      MessageResource message = null;
      Localization localMessage = null;

      try
      {
         String xml = null;

         if (event.getSource().equals(itemExit))
         {
            // Close the application.
            closeApplication();
         }
         else if (event.getSource().equals(itemUnmarshal))
         {
            File file = new File("M:\\work\\java\\core\\testapp\\resources.xml");

            //            File file = new File("/resources.xml");
            FileReader reader = new FileReader(file);

            // Unmarshal the resource bundle.  
            MessageResourceBundle bundle = (MessageResourceBundle) (MessageResourceBundle.unmarshal(reader));
            writeLog("Processing unmarshaled object:");
            writeLog("  Resource Bundle");

            // Process the list of messages.
            java.util.Enumeration enum = bundle.enumerateMessageResource();

            while (enum.hasMoreElements())
            {
               message = (MessageResource) (enum.nextElement());
               writeLog("    Message:  ID = " + message.getMessageID());
               localMessage = message.getLocalization("en_US");
               writeLog("       Localization:  Locale = " + localMessage.getLocale());
               writeLog("         Text = " + localMessage.getMessageText());
               writeLog("         Category = " + localMessage.getCategory());
               writeLog("         Component = " + localMessage.getComponent());
               writeLog("         SubComponent = " + localMessage.getSubComponent());
            }

            writeLog("Checking hashtable functionality:");
            message = (MessageResource) (bundle.getMessageResource("10001"));
            writeLog("    Message:  ID = " + message.getMessageID());
            message = (MessageResource) (bundle.getMessageResource("10002"));
            writeLog("    Message:  ID = " + message.getMessageID());

            statusBar.setText("XML File marshaled.");
         }
         else if (event.getSource().equals(itemUnmarshalMapping))
         {
            xml = "<User><UserID>12</UserID><UserName>mshoemake</UserName><Password>1234</Password><FirstName>Mike</FirstName><LastName>Shoemake</LastName></User>";

            UserSuitCase user = null;

            // Create Class object.
            Class userClass = Class.forName("mjs.test.castor.UserSuitCase");

            // Create URL for mapping file.
            //            String mappingFile = "UserMapping.xml";
            String mappingFile = "/mjs/test/castor/UserMapping.xml";
            java.net.URL url = getClass().getResource(mappingFile);

            //convert xml to Global Question Pool object
            user = (UserSuitCase) (CastorObjectConverter.convertXMLToObject(xml, userClass, url));
            writeLog("XML Mapping Test");
            writeLog("   User");
            writeLog("      UserID:      " + user.getUserID());
            writeLog("      UserName:    " + user.getUserName());
            writeLog("      Password:    " + user.getPassword());
            writeLog("      FirstName:   " + user.getFirstName());
            writeLog("      LastName:    " + user.getLastName());
            writeLog(" ");
            statusBar.setText("XML File unmarshaled.");
         }
         else if (event.getSource().equals(itemMarshalUserList))
         {
            UserList userlist = new UserList();
            userlist.asVector().add(new User(1, "mshoemake", "1234", "Mike", "Shoemake"));             
            userlist.asVector().add(new User(2, "sshoemake", "4mikey", "Susan", "Shoemake"));             
            userlist.asVector().add(new User(3, "fflintstone", "fred", "Fred", "Flintstone"));             

            // Create Class object.
            Class userClass = userlist.getClass();

            // Create URL for mapping file.
            //            String mappingFile = "UserMapping.xml";
            String mappingFile = "/mjs/home/model/UserListMapping.xml";
            java.net.URL url = userClass.getResource(mappingFile);

            //convert xml to Global Question Pool object
            xml = CastorObjectConverter.convertObjectToXML(userlist, userClass, url);
            writeLog("Marshal UserList");
            writeLog("   XML: "+xml);
            writeLog(" ");
            statusBar.setText("Object marshaled to XML.");
         }
         else if (event.getSource().equals(itemUserListXSLT))
         {
            xml = "<UserList><User><UserID>1</UserID><UserName>mshoemake</UserName><Password>090972</Password><FirstName>Mike</FirstName><LastName>Shoemake</LastName></User><User><UserID>2</UserID><UserName>sshoemake</UserName><Password>4mikey</Password><FirstName>Susan</FirstName><LastName>Shoemake</LastName></User><User><UserID>3</UserID><UserName>fflintstone</UserName><Password>1234</Password><FirstName>Fred</FirstName><LastName>Flintstone</LastName></User></UserList>";
            String xslPath = "/mjs/test/xalan/userlist.xsl";
            String xsl = JarFileExtractor.getXMLText(xslPath, false);
            String html = XSLTest.executeXSL(xml, xsl);
            writeLog("UserList XSLT Transformation");
            writeLog("   HTML: "+html);
            writeLog(" ");
            statusBar.setText("XML File unmarshaled.");
         }
         else if (event.getSource().equals(itemLocaleTest))
         {
            Locale locale = Locale.UK;
            writeLog("Locale Test:");
            writeLog("              Variant: " + locale.getVariant());
            writeLog("              Country: " + locale.getCountry());
            writeLog("             Language: " + locale.getLanguage());
            writeLog("       DisplayVariant: " + locale.getDisplayVariant());
            writeLog("       DisplayCountry: " + locale.getDisplayCountry());
            writeLog("          DisplayName: " + locale.getDisplayName());
            writeLog(" ");
            locale = Locale.US;
            writeLog("Locale Test:");
            writeLog("              Variant: " + locale.getVariant());
            writeLog("              Country: " + locale.getCountry());
            writeLog("             Language: " + locale.getLanguage());
            writeLog("       DisplayVariant: " + locale.getDisplayVariant());
            writeLog("       DisplayCountry: " + locale.getDisplayCountry());
            writeLog("          DisplayName: " + locale.getDisplayName());
            writeLog(" ");

            statusBar.setText("Locale Test complete.");
         }
         else if (event.getSource().equals(itemLoggingTest))
         {
            numLogs++;
            logManager.writeToLog("Log Test Debug   #" + numLogs, LoggingManager.PRIORITY_DEBUG);
            logManager.writeToLog("Log Test Error   #" + numLogs, LoggingManager.PRIORITY_ERROR);
            logManager.writeToLog("Log Test Fatal   #" + numLogs,
               LoggingManager.PRIORITY_FATAL_ERROR);
            logManager.writeToLog("Log Test Info    #" + numLogs, LoggingManager.PRIORITY_INFO);
            logManager.writeToLog("Log Test None    #" + numLogs, LoggingManager.PRIORITY_NONE);
            logManager.writeToLog("Log Test Warning #" + numLogs, LoggingManager.PRIORITY_WARNING);
            writeLog("Log Test #" + numLogs);
         }
      }
      catch (java.lang.Exception e)
      {
         // Log exception.
         e.printStackTrace();
      }
   }

   /**
    * DOCUMENT ME!
    */
   public void closeApplication()
   {
      contentPane.remove(statusBar);
      this.setJMenuBar(null);
      contentPane = null;
      menuBar.remove(menuActions);
      menuActions.remove(itemExit);
      menuActions.remove(itemUnmarshal);
      menuActions.remove(itemUnmarshalMapping);
      menuActions.remove(itemMarshalUserList);
      menuActions.remove(itemUserListXSLT);
      menuActions = null;
      itemExit = null;
      itemUnmarshal = null;
      itemUnmarshalMapping = null;
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

   private void writeLog(String message)
   {
      resultsView.setLineWrap(true);
      resultsView.append(message + "\n");
   }
}
