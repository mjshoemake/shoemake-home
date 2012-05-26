/* File name:     HomeController.java
 * Package name:  mjs.home.controller
 * Project name:  Home - Controller
 * Date Created:  Apr 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.controller;

// Java imports
import javax.servlet.http.HttpServletRequest;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.core.servlet.ServletConfig;
import mjs.core.servlet.ServletController;
import mjs.core.servlet.ServletControllerImpl;
import mjs.core.servlet.ServletControllerResponse;
import mjs.home.controller.action.Action;
import mjs.home.controller.action.ActionFactory;
import mjs.home.model.UserRequestManager;
import mjs.home.model.DatabaseUserRequestManager;

/**
 * The main controller facade for the Shoemake home
 * web site.  This class takes the request and response
 * objects from the servlet, takes the appropriate action,
 * and returns the NextPageInfo object to the servlet 
 * to be passed on to the view layer.  
 * 
 * @author mshoemake
 */
public class HomeController extends ServletControllerImpl 
                            implements ServletController
{

   /**
    * The factory used to create action objects.
    */
   ActionFactory factory = null;
   
   /**
    * Constructor.
    * 
    * @param config  The servlet configuration object. 
    */
   public HomeController(ServletConfig config) 
   {
      super(config);
      try
      {
         writeToLog("HomeController  Constructor()  BEGIN", LoggingManager.PRIORITY_DEBUG);
         HomeServletConfig homeConfig = (HomeServletConfig)config;
         UserRequestManager userReqManager = new DatabaseUserRequestManager(homeConfig.getDatabaseDSN()); 
         writeToLog("HomeController  Constructor()  Created RequestManager.", LoggingManager.PRIORITY_DEBUG);
         factory = new ActionFactory(homeConfig, userReqManager);
         writeToLog("HomeController  Constructor()  Created ActionFactory.", LoggingManager.PRIORITY_DEBUG);
         writeToLog("HomeController  Constructor()  END", LoggingManager.PRIORITY_DEBUG);
      }
      catch (java.lang.Exception e)
      {
         writeToLog(e);   
      }
   }

   /**
    * Process the request and return the information 
    * about the next page to display.
    * 
    * @param request   The servlet request object.
    * @return
    */
   public ServletControllerResponse processRequest(HttpServletRequest request)
   {
      writeToLog("HomeController  processRequest()  BEGIN", LoggingManager.PRIORITY_DEBUG);
      writeToLog("HomeController  processRequest()  Creating action object...", LoggingManager.PRIORITY_DEBUG);
      Action action = factory.createAction(request, getServletConfig());      
      writeToLog("HomeController  processRequest()  Executing action object.", LoggingManager.PRIORITY_DEBUG);
      ServletControllerResponse response = action.execute(request);   
      writeToLog("HomeController  processRequest()  END  response = "+response.getXMLData(), LoggingManager.PRIORITY_DEBUG);
      return response;
   }
}
