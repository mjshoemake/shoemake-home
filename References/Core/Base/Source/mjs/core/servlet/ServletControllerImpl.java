/* File name:     ServletControllerImpl.java
 * Package name:  mjs.core.servlet
 * Project name:  Core - Base
 * Date Created:  Apr 29, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.core.servlet;

// Java imports
import javax.servlet.http.HttpServletRequest;


/**
 * This class is used for the controller object for the servlet.  
 * This relates to the Model/View/Controller architecture.  
 * The Controller contains the business logic, retrieves the 
 * data, and sends the required data to the View component.
 * 
 * @author mshoemake
 */
public abstract class ServletControllerImpl implements ServletController
{
   /**
    * Constructor. 
    */
   public ServletControllerImpl(ServletConfig config)
   {
      super();
   }

   /**
    * Process the request from the servlet.
    * 
    * @see mjs.core.servlet.ServletController#processRequest(javax.servlet.http.HttpServletRequest)
    */
   public abstract ServletControllerResponse processRequest(HttpServletRequest request);

}
