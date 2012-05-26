/* File name:     ActionFactory.java
 * Package name:  mjs.home.controller
 * Project name:  Home - Controller
 * Date Created:  Apr 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.controller.action;

// Java imports
import javax.servlet.http.HttpServletRequest;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.core.servlet.ServletConfig;
import mjs.core.servlet.ServletLoggableObject;
import mjs.home.controller.PageType;
import mjs.home.controller.HomeServletConfig;
import mjs.home.model.UserRequestManager;

/**
 * The factory that returns a controller object for
 * a specific page that needs to be displayed.
 * 
 * @author mshoemake
 */
public class ActionFactory extends ServletLoggableObject
{
   /**
    * The SOAP client request manager.
    * <p>
    * This request manager actually makes a SOAP call to 
    * interact with the database.
    */
   private UserRequestManager reqManager = null; 


   /**
    * Constructor.
    */
   public ActionFactory(ServletConfig config, UserRequestManager userReqManager)
   {
      super(config);
      writeToLog("ActionFactory  Constructor()  BEGIN", LoggingManager.PRIORITY_DEBUG);
      reqManager = userReqManager;
      writeToLog("ActionFactory  Constructor()    Received UserRequestManager object.", LoggingManager.PRIORITY_DEBUG);
      writeToLog("ActionFactory  Constructor()  END", LoggingManager.PRIORITY_DEBUG);
   }
   
   /**
    * Create an action object based on the information supplied.
    * 
    * @param request  The servlet request object.
    * @param config   The servlet config object.
    */
   public Action createAction(HttpServletRequest request, ServletConfig config) 
   {
      writeToLog("ActionFactory  createAction()  BEGIN", LoggingManager.PRIORITY_DEBUG);
      writeToLog("ActionFactory  createAction()    Calling getNextPage()...", LoggingManager.PRIORITY_DEBUG);
      int page = getNextPage(request, config);
      
      // Update state attribute of session request.
      request.getSession().setAttribute("state", ""+page);
      
      // Creat action object.
      writeToLog("ActionFactory  createAction()    Calling createActionForPage()...  page = "+page, LoggingManager.PRIORITY_DEBUG);
      Action action = createActionForPage(page, request, config);
      writeToLog("ActionFactory  createAction()  END", LoggingManager.PRIORITY_DEBUG);
      return action;      
   }
   
   /**
    * Figure out what the next page should be based on the 
    * information supplied.
    * 
    * @param request  The servlet request object.
    * @param config   The servlet config object.
    */
   private int getNextPage(HttpServletRequest request, ServletConfig config)
   {   
      String temp = request.getParameter("state");
      int state = -1;
      if (temp == null)
      {
         // State is null.  Default to initial state. 
         state = PageType.STATE_INITIAL;
      }
      else
      {   
         // Parse state value. 
         state = Integer.parseInt(temp);
      }

      
      switch (state)
      {
         // Display the initial page.
         // FOR NOW, INITIAL PAGE IS USER LIST.
         case PageType.STATE_INITIAL:
            return PageType.STATE_MAIN;

         // Display User List
         case PageType.STATE_USER_LIST:
            return PageType.STATE_USER_LIST;

         // Edit User
         case PageType.STATE_EDIT_USER:
            return PageType.STATE_USER_LIST;

         default:   
            return PageType.STATE_USER_LIST;  
      }     
   }
   
   /**
    * Create an action object based on the information supplied.
    *
    * @param page     The next page to display.  
    * @param request  The servlet request object.
    * @param config   The servlet config object.
    */
   private Action createActionForPage(int page, HttpServletRequest request, ServletConfig config) 
   {
      switch (page)
      {
         // Display the user list.
         case PageType.STATE_MAIN:
            return new LocalHtmlAction((HomeServletConfig)config, "html/Main.xhtml");

         // Display the user list.
         case PageType.STATE_USER_LIST:
            return new GetUserListAction((HomeServletConfig)config, reqManager);

         default:   
            return new NoAction();  
      }     
   }
   
}
