/* File name:     UpdateUserAction.java
 * Package name:  mjs.home.struts.actions
 * Project name:  Home - Struts
 * Date Created:  Jun 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.struts.actions;

// Java imports
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.home.struts.Constants;
import mjs.home.model.User;

/**
 * Struts action class used to update a user's attributes.
 * This is invoked by calling UpdateUserPage.do.
 * 
 * @author mshoemake
 */
public class UpdateUserAction extends Action
{

   /**
    * Execute this Struts action.  This is invoked by calling
    * /UpdateUserPage.do.
    * 
    * @param mapping     The ActionMapping used to select this instance
    * @param actionForm  The optional ActionForm bean for this request (if any)
    * @param request     The HTTP request we are processing
    * @param response    The HTTP response we are creating
    * throws Exception
    */
   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      Constants.logManager.writeToLog("UpdateUserAction  execute()  BEGIN", LoggingManager.PRIORITY_DEBUG);

      try
      {
         // Prepare to return any errors to the user.
         ActionErrors errors = new ActionErrors();

         String id = request.getParameter("id");
         Constants.logManager.writeToLog("UpdateUserAction  execute()  Loading User Data ("+id+")...", LoggingManager.PRIORITY_DEBUG);
         User user = Constants.userReqManager.getUser(Integer.parseInt(id));
         Constants.logManager.writeToLog("UpdateUserAction  execute()  Form data: username="+user.getUserName()+", first="+user.getFirstName()+", last="+user.getLastName()+", pass="+user.getPassword()+", "+user.getUserID(), LoggingManager.PRIORITY_DEBUG);

         Constants.logManager.writeToLog("UpdateUserAction  execute()  Setting form attributes...", LoggingManager.PRIORITY_DEBUG);

         // Populate the form data so this user can be edited.          
         AddUserForm addUserForm = (AddUserForm)form;
         addUserForm.setUserID(user.getUserID());
         addUserForm.setFirstName(user.getFirstName());
         addUserForm.setLastName(user.getLastName());
         addUserForm.setUserName(user.getUserName());
         addUserForm.setPassword(user.getPassword());

         // Populate the user's name for the breadcrumbs.          
         String name = user.getFirstName()+" "+user.getLastName();
         request.setAttribute("editname", name.trim());
      }
      catch (java.lang.Exception e)
      {
         Constants.logManager.writeToLog(e);
      }

      Constants.logManager.writeToLog("UpdateUserAction  execute()  END", LoggingManager.PRIORITY_DEBUG);
      return (mapping.findForward("success"));
   }

}
