/* File name:     NoAction.java
 * Package name:  mjs.home.controller
 * Project name:  Home - Controller
 * Date Created:  Apr 25, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.controller.action;

// Java imports
import javax.servlet.http.HttpServletRequest;

// MJS imports
import mjs.core.servlet.ServletControllerResponse;

/**
 * This actions does nothing.
 * 
 * @author mshoemake
 */
public class NoAction implements Action
{
   /**
    * Constructor. 
    */
   public NoAction()
   {
      super();
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
      return null;
   }

}
