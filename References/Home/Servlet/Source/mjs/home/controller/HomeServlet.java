/* File name:     HomeServlet.java
 * Package name:  mjs.home.controller
 * Project name:  Home - Controller
 * Date Created:  Apr 25, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.controller;

// Java imports
import java.io.File;
import java.io.FileReader;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.core.servlet.Servlet;
import mjs.core.servlet.ServletConfig;
import mjs.core.servlet.ServletControllerResponse;
import mjs.core.xml.resourceBundle.MessageResourceBundle;
import mjs.home.controller.HomeServletConfig;
import mjs.home.view.HomeViewManager;

/**
 * This is the servlet for the Shoemake home application.
 * It extends the mjs.core.servlet.Servlet class.
 * 
 * @author mshoemake
 */
public class HomeServlet extends Servlet
{
   /**
    * The servlet configuration object that contains general
    * servlet information and objects such as locale for this
    * session, message resource bundle, logging manager, etc.
    */
   private ServletConfig config = null;

   /**
    * The servlet controller object that processes the 
    * requests.
    */
   HomeController controller = null;
   
   /**
    * The path and file location of the message resource xml.
    * <p>
    * NOTE:  This should eventually end up in an external 
    * configuration file.
    */
   private static final String MESSAGE_BUNDLE_PATH = "M:\\work\\java\\core\\testapp\\resources.xml";    

   /**
    * The path and file location of output log file.
    * <p>
    * NOTE:  This should eventually end up in an external 
    * configuration file.
    */
   private static final String LOG_FILE_PATH = "C:\\LogFiles\\ShoemakeHome.log";    


   /**
    * Constructor.
    */
   public HomeServlet()
   {
      super();

      // Create logging manager.
      LoggingManager logManager = new LoggingManager(LOG_FILE_PATH, "HomeServlet", LoggingManager.PRIORITY_DEBUG);
      MessageResourceBundle bundle = null;
      String databaseDSN = "jdbc:odbc:AccessTasks";

      // Unmarshal the message resource bundle.
      try 
      {  
         File file = new File(MESSAGE_BUNDLE_PATH);
         FileReader reader = new FileReader(file);
         bundle = (MessageResourceBundle)(MessageResourceBundle.unmarshal(reader));
      }
      catch (java.lang.Exception e)
      {
         // Write log message.
         logManager.writeToLog(e);
      }

      // Create config object.
      HomeServletConfig config = null;
      config = new HomeServletConfig("Shoemake HomeSite",
                                     bundle,
                                     null, // null controller initially
                                     logManager,
                                     null, // null locale initially
                                     PageType.STATE_INITIAL,
                                     databaseDSN);
      this.config = (ServletConfig)config;
                                             
      // Create controller.                                
      controller = new HomeController(config);
      config.setServletController(controller);                                          
   }

   /**
    * The servlet configuration object that contains general
    * servlet information and objects such as locale for this
    * session, message resource bundle, logging manager, etc.
    */
   public ServletConfig getServletConfiguration()
   {
      return config;
   }

   /**
    * Perform some action based on the response from the action object. 
    */
   public void handleControllerResponse(ServletControllerResponse controllerResponse, HttpServletRequest request, HttpServletResponse response)
   {
      try
      {
         HomeViewManager viewManager = new HomeViewManager(config);
         viewManager.sendHttpResponse(controllerResponse, request, response);         
      }
      catch (java.lang.Exception e)
      {
         config.getLoggingManager().writeToLog(e);      
      }
   }

   /**
    * Clean up resources.
    */
   public void destroy()
   {
      config = null;
      controller = null;
   }

}
