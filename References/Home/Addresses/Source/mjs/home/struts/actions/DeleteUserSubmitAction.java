/* File name:     DeleteUserSubmitAction.java
 * Package name:  mjs.home.struts.actions
 * Project name:  Home - Struts
 * Date Created:  Jul 31, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.struts.actions;

//Java imports
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.home.struts.Constants;

/**
 * Struts action class used to respond to user activity
 * in the User List page.
 * 
 * @author mshoemake
 */
public class DeleteUserSubmitAction extends Action
{

   /**
    * Execute this Struts action.  This is invoked by calling
    * /GetUserListSubmit.do.
    * <p>
    * This method will process the specified HTTP request, and 
    * create the corresponding HTTP response (or forward to another 
    * web component that will create it).
    * <p>
    * Returns an <code>ActionForward</code> instance describing 
    * where and how control should be forwarded, or <code>null</code> 
    * if the response has already been completed.
    * 
    * @param mapping     The ActionMapping used to select this instance
    * @param actionForm  The optional ActionForm bean for this request (if any)
    * @param request     The HTTP request we are processing
    * @param response    The HTTP response we are creating
    * throws Exception
    */
   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      Constants.logManager.writeToLog("DeleteUserSubmitAction  execute()  BEGIN", LoggingManager.PRIORITY_DEBUG);

      try
      {
         // Prepare to return any errors to the user.
         ActionErrors errors = new ActionErrors();

         // Validation complete.  Save the new user to the database.
         GetUserListForm thisForm = (GetUserListForm)form;
         String[] selection = thisForm.getSelectedItems();
         
         if (selection.length == 0)
         {
            // UserName required.
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.delete.selection.empty"));
            Constants.logManager.writeToLog("DeleteUserSubmitAction  execute()  User Error: No users were selected to delete.", LoggingManager.PRIORITY_DEBUG);
         }

         // Report any errors we have discovered back to the original form
         if (! errors.empty()) 
         {
            Constants.logManager.writeToLog("DeleteUserSubmitAction  execute()  Showing errors to user.  Try again.", LoggingManager.PRIORITY_DEBUG);
            saveErrors(request, errors);
            // Send to GetUserList action.
            return (mapping.findForward("success"));
         }
         
         // Delete the users.
         for (int C=0; C <= selection.length-1; C++)
         {
            // Trace out the list of users to delete.
            Constants.logManager.writeToLog("DeleteUserSubmitAction  execute()  "+C+": "+selection[C], LoggingManager.PRIORITY_DEBUG);
         }
         Constants.userReqManager.deleteUsers(selection);
         // @TODO I18N: hardcoded strings.
         request.setAttribute("status", "User(s) deleted successfully.");
         
      }   
      catch (java.lang.Exception e)
      {
         Constants.logManager.writeToLog(e);
         // @TODO I18N: hardcoded strings.
         request.setAttribute("status", "Error occured trying to delete the users.  The users were not deleted.");
      }

      // If a form was used, remove the Form Bean.  mapping.getAttribute()
      // returns the attribute name for the form bean.
      if (mapping.getAttribute() != null) 
      {
         if ("request".equals(mapping.getScope()))
             request.removeAttribute(mapping.getAttribute());
         else
             request.getSession().removeAttribute(mapping.getAttribute());
      }       

      // Forward control to the specified success URI
      Constants.logManager.writeToLog("DeleteUserSubmitAction  execute()  END", LoggingManager.PRIORITY_DEBUG);
      return (mapping.findForward("success"));
   }

}
