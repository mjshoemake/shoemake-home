/* File name:     Action.java
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
import mjs.core.servlet.ServletControllerResponse;

/**
 * The action interface for the controller part of
 * the home application.
 * 
 * @author mshoemake
 */
public interface Action
{
   /**
    * The execute method for an action. 
    * 
    * @param request  The servlet request object.
    * @param config   The servlet config object.
    */ 
   public ServletControllerResponse execute(HttpServletRequest request);     
}
