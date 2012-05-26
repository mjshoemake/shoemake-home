/* File name:     LocalHtmlAction.java
 * Package name:  mjs.home.controller.action
 * Project name:  Home - Controller
 * Date Created:  May 8, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.controller.action;

// Java imports
import javax.servlet.http.HttpServletRequest;

// MJS imports
import mjs.core.communication.FileManager;
import mjs.core.communication.LoggingManager;
import mjs.core.servlet.ServletControllerResponse;
import mjs.core.servlet.ServletControllerResponseType;
import mjs.core.servlet.ServletLoggableObject;
import mjs.home.controller.HomeServletConfig;

/**
 * This Action class loads an HTML file from the
 * disk and returns it back to the caller.
 * 
 * @author mshoemake
 */
public class LocalHtmlAction extends ServletLoggableObject
                             implements Action
{
   /**
    * The path to the html file to return to the user.
    */
   String htmlPath = null;
   
   /**
    * Constructor.
    */
   public LocalHtmlAction(HomeServletConfig config, String htmlPath)
   {
      super(config);
      writeToLog("LocalHtmlAction  Constructor()  BEGIN", LoggingManager.PRIORITY_DEBUG);
      this.htmlPath = htmlPath;
      writeToLog("LocalHtmlAction  Constructor()  HtmlPath = "+htmlPath, LoggingManager.PRIORITY_DEBUG);
      writeToLog("LocalHtmlAction  Constructor()  END", LoggingManager.PRIORITY_DEBUG);
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
         writeToLog("LocalHtmlAction  execute()  BEGIN", LoggingManager.PRIORITY_DEBUG);
         String path = request.getRealPath(htmlPath);
         String html = FileManager.readXMLFile(path);

         writeToLog("LocalHtmlAction  execute()  Loaded html from... "+path, LoggingManager.PRIORITY_DEBUG);
         // Response type is HTML.
         ServletControllerResponseType type = new ServletControllerResponseType(ServletControllerResponseType.RESPONSE_HTML);
         ServletControllerResponse response = new ServletControllerResponse(getServletConfig(), html, type);
         writeToLog("LocalHtmlAction  execute()  END  response type is HTML.", LoggingManager.PRIORITY_DEBUG);
         return response;
      }
      catch (java.lang.Exception e)
      {
         writeToLog(e);
      }
      return null;
   }

}
