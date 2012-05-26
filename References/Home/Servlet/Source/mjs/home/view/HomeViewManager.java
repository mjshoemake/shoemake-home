/* File name:     HomeViewManager.java
 * Package name:  mjs.home.view
 * Project name:  Home - Controller
 * Date Created:  May 1, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.view;

// Java imports
import java.io.PrintWriter;
import java.lang.Integer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.core.communication.FileManager;
import mjs.core.servlet.ServletConfig;
import mjs.core.servlet.ServletControllerResponse;
import mjs.core.servlet.ServletControllerResponseType;
import mjs.core.servlet.ServletLoggableObject;
import mjs.core.xsl.XSLTransformer;
import mjs.home.controller.PageType;

/**
 * The view component of the servlet.  This component
 * converts the data to a displayable format using XSLT
 * transformation.
 * 
 * @author mshoemake
 */
public class HomeViewManager extends ServletLoggableObject 
{

   /**
    * Constructor.
    * 
    * @param  config  The servlet config object. 
    */
   public HomeViewManager(ServletConfig config)
   {
      super(config);
      writeToLog("HomeViewManager  Constructor()  View Manager created.", LoggingManager.PRIORITY_DEBUG);
   }

   /**
    * Send the response to the user. 
    */
   public void sendHttpResponse(ServletControllerResponse controllerResponse, HttpServletRequest request, HttpServletResponse response)
   {
      int type = controllerResponse.getResponseType().getValue(); 
      
      switch (type)
      {
         case ServletControllerResponseType.RESPONSE_HTML:
            // HTML response
            handleHtmlResponse(controllerResponse, request, response);
            break;
            
         case ServletControllerResponseType.RESPONSE_XML_DATA:
            // XML/XSL response
            handleXmlResponse(controllerResponse, request, response);
            break;
            
         default:
            // MJS: ERROR
            break;   
      }     
   }

   /**
    * The response contains a displayable HTML file.  Send it
    * directly to the client browser.
    * 
    * @param controllerResponse
    * @param request
    * @param response
    */
   private void handleHtmlResponse(ServletControllerResponse controllerResponse, HttpServletRequest request, HttpServletResponse response)
   {
      try
      {
         String html = controllerResponse.getXMLData();
         PrintWriter out = response.getWriter();
         out.println(html);
      }   
      catch (java.lang.Exception e)
      {
         writeToLog(e);      
      }
   }

   /**
    * The response contains XML data to be use in conjuction
    * with an XSL stylesheet to generate an HTML file.  
    * 
    * @param controllerResponse
    * @param request
    * @param response
    */      
   private void handleXmlResponse(ServletControllerResponse controllerResponse, HttpServletRequest request, HttpServletResponse response)
   {
      try
      {
         writeToLog("HomeViewManager  handleXmlResponse()  BEGIN", LoggingManager.PRIORITY_DEBUG);
         String xml = controllerResponse.getXMLData();
         writeToLog("HomeViewManager  handleXmlResponse()  xml = "+xml, LoggingManager.PRIORITY_DEBUG);
         String xslPath = getXSLPath(request);
         writeToLog("HomeViewManager  handleXmlResponse()  xslPath = "+xslPath, LoggingManager.PRIORITY_DEBUG);
         String xsl = FileManager.readXMLFile(xslPath);
         writeToLog("HomeViewManager  handleXmlResponse()  Extracted xsl.  xsl = "+xsl, LoggingManager.PRIORITY_DEBUG);
         String html = XSLTransformer.executeXSL(xml, xsl);
         writeToLog("HomeViewManager  handleXmlResponse()  XSLT complete.  html = "+html, LoggingManager.PRIORITY_DEBUG);
         
         // Send response to user.
         response.setContentType("text/html");
         writeToLog("HomeViewManager  handleXmlResponse()  Preparing to send html response...", LoggingManager.PRIORITY_DEBUG);
         if (html.length() == 0)
         {
            // HTML is empty.  Return error page.
            writeToLog("Empty html response.  Nothing to send.", LoggingManager.PRIORITY_DEBUG);
            // TODO  Throw Exception.
         }
         PrintWriter out = response.getWriter();
         out.println(html);
         writeToLog("HomeViewManager  handleXmlResponse()  Response sent.", LoggingManager.PRIORITY_DEBUG);
      }
      catch (java.lang.Exception e)
      {
         writeToLog(e);      
      }
   }

   /**
    * Return the XSL path for the current page. 
    * 
    * @param request
    */
   private String getXSLPath(HttpServletRequest request)
   {
      writeToLog("HomeViewManager  getXSLPath()  BEGIN", LoggingManager.PRIORITY_DEBUG);
      int page = Integer.parseInt(request.getSession().getAttribute("state").toString());
      
      writeToLog("HomeViewManager  getXSLPath()  request.session.state = "+page, LoggingManager.PRIORITY_DEBUG);
      switch (page)
      {
         case PageType.STATE_USER_LIST:
            // User List
            writeToLog("HomeViewManager  getXSLPath()  END", LoggingManager.PRIORITY_DEBUG);
            return request.getRealPath("xsl/UserList.xsl");
            
         default:
            // Default to UserList   
            writeToLog("HomeViewManager  getXSLPath()  Page not found.  Using default xsl path.", LoggingManager.PRIORITY_DEBUG);
            writeToLog("HomeViewManager  getXSLPath()  END", LoggingManager.PRIORITY_DEBUG);
            return request.getRealPath("xsl/UserList.xsl");
      }     
   }

}
