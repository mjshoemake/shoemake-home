/* File name:     ServletController.java
 * Package name:  mjs.core.servlet
 * Project name:  Core
 * Date Created:  Apr 23, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.core.servlet;

// Java imports
import javax.servlet.http.HttpServletRequest;

/**
 * This interface is used for the controller object
 * for the servlet.  This relates to the Model/View/Controller
 * architecture.  The Controller contains the business logic,
 * retrieves the data, and sends the required data to the
 * View component.
 * 
 * @author mshoemake
 */
public interface ServletController
{
   /**
    * Process the request and return the information 
    * about the next page to display.  It accepts a
    * servlet configuration object that specifies the
    * application name, module name, locale, and
    * resource bundle to use. 
    * 
    * @param request   The servlet request object.
    */
   public ServletControllerResponse processRequest(HttpServletRequest request);

}
