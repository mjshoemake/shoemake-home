/* File name:     Servlet.java
 * Package name:  mjs.core.servlet
 * Project name:  Core
 * Date Created:  Apr 23, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.core.servlet;

// Java imports
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;


/**
 * The base servlet for my applications.
 * 
 * @author mshoemake
 */
public abstract class Servlet extends HttpServlet
{
   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Servlet".
    */
   protected Logger log = null;

   /**
    * Constructor.
    */
   public Servlet()
   {
      super();
   }

   /**
    * The servlet configuration object that contains general
    * servlet information and objects such as locale for this
    * session, message resource bundle, logging manager, etc.
    * <p>
    * This abstract method should be implemented to return the
    * required servlet config object, creating a new one if 
    * none exists.
    */
   public abstract ServletConfig getServletConfiguration();


   /**
    * Initialize global variables for this servlet.
    */
   public void init() throws ServletException
   {
   }

   /**
    * Process the HTTP Post request.
    */
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      doGet(request, response);
   }

   /**
    * Process the HTTP Get request.
    */
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      // Get the servlet config object.  Create a new one if none exists.
      ServletConfig config = getServletConfiguration();
      config.setLocale(request.getLocale());
      
      ServletController controller = config.getServletController();

      if (request.getSession() == null)
      {
         // Initialize the user session.
         request.getSession(true);
         log.info("User session initialized.");
         // Set the default state.
         request.getSession().setAttribute("state", ""+config.getDefaultState());
         log.info("Default state = "+config.getDefaultState());
      }
      else if (request.getSession().getAttribute("state") == null)
      {
         // Set the default state.  Session already exists.
         request.getSession().setAttribute("state", ""+config.getDefaultState());
         log.info("Default state = "+config.getDefaultState());
      }

      // Call the controller to determine the action to take. 
      ServletControllerResponse controllerResponse = controller.processRequest(request);
      handleControllerResponse(controllerResponse, request, response);
   }

   /**
    * Perform some action based on the response from the action object. 
    */
   public abstract void handleControllerResponse(ServletControllerResponse controllerResponse, HttpServletRequest request, HttpServletResponse response);

   /**
    * Clean up resources.
    */
   public abstract void destroy();

}
