/* File name:     GetUserListAction.java
 * Package name:  mjs.home.controller.action
 * Project name:  Home - Controller
 * Date Created:  Apr 25, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.controller.action;

// Java imports
import javax.servlet.http.HttpServletRequest;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.core.servlet.ServletControllerResponse;
import mjs.core.servlet.ServletControllerResponseType;
import mjs.core.servlet.ServletLoggableObject;
import mjs.home.controller.HomeServletConfig;
import mjs.home.model.UserList;
import mjs.home.model.UserRequestManager;

/**
 * Get the user list and return it in XML 
 * format to the servlet controller.
 * 
 * @author mshoemake
 */
public class GetUserListAction extends ServletLoggableObject 
                               implements Action
{
   /**
    * The user request manager that retrieves, adds, and updates
    * data in the database.
    * <p>
    * This could be a SOAP client or it could retrieve the 
    * data locally.
    */
   private UserRequestManager reqManager = null; 

   /**
    * Constructor.
    * <p>
    * @param reqManager  The request manager to use to retrieve
    *                    the data.
    */
   public GetUserListAction(HomeServletConfig config, UserRequestManager reqManager)
   {
      super(config);
      writeToLog("GetUserListAction  Constructor()  BEGIN", LoggingManager.PRIORITY_DEBUG);
      this.reqManager = reqManager;
      if (reqManager != null)  
         writeToLog("GetUserListAction  Constructor()    Received UserRequestManager object.", LoggingManager.PRIORITY_DEBUG);
      else   
         writeToLog("GetUserListAction  Constructor()    ERROR: UserRequestManager object is NULL!!!", LoggingManager.PRIORITY_DEBUG);
      writeToLog("GetUserListAction  Constructor()  END", LoggingManager.PRIORITY_DEBUG);
   }

   /**
    * The execute method for an action. 
    * 
    * @param request  The servlet request object.
    * @param config   The servlet config object.
    * @see mjs.home.controller.Action#execute(javax.servlet.http.HttpServletRequest, mjs.core.servlet.ServletConfig)
    */ 
   public ServletControllerResponse execute(HttpServletRequest request)
   {
      try
      {
         writeToLog("GetUserListAction  execute()  BEGIN", LoggingManager.PRIORITY_DEBUG);
         UserList list = reqManager.getUserList();
         writeToLog("GetUserListAction  execute()  Received user list data...", LoggingManager.PRIORITY_DEBUG);
         XMLToObjectConverter converter = new XMLToObjectConverter(getServletConfig());
         String xml = converter.convertUserListToXML(list);
         writeToLog("GetUserListAction  execute()  Converted user list to xml...", LoggingManager.PRIORITY_DEBUG);
         
         // Response type is XML DATA.
         ServletControllerResponseType type = new ServletControllerResponseType(ServletControllerResponseType.RESPONSE_XML_DATA);
         ServletControllerResponse response = new ServletControllerResponse(getServletConfig(), xml, type);
         writeToLog("GetUserListAction  execute()  END  response = "+response.getXMLData(), LoggingManager.PRIORITY_DEBUG);
         return response;
      }
      catch (java.lang.Exception e)
      {
         writeToLog(e);
      }
      return null;
   }

}
