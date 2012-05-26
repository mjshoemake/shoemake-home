/* File name:     AddUserAction.java
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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.home.struts.Constants;
import mjs.home.model.User;

/**
 * Struts action class used to add a user to the user list.  
 * This is invoked by calling AddUser.do.
 * 
 * @author mshoemake
 */
public class AddUserSubmitAction extends Action
{

   /**
    * Execute this Struts action.  This is invoked by calling
    * /AddUser.do.
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
      Constants.logManager.writeToLog("AddUserSubmitAction  execute()  BEGIN", LoggingManager.PRIORITY_DEBUG);

      if (! isCancelled(request))
      {
         // Prepare to return any errors to the user.
         ActionErrors errors = new ActionErrors();

         String username = (String)PropertyUtils.getSimpleProperty(form, "userName");
         String firstName = (String)PropertyUtils.getSimpleProperty(form, "firstName");
         String lastName = (String)PropertyUtils.getSimpleProperty(form, "lastName");
         // NOTE:  Password defaults to "password", and will be changed 
         //        at initial login.
         Constants.logManager.writeToLog("AddUserSubmitAction  execute()  Form data: username="+username+", first="+firstName+", last="+lastName, LoggingManager.PRIORITY_DEBUG);

         if (username.length() == 0)
         {
            // UserName required.
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.username.required"));
            Constants.logManager.writeToLog("AddUserSubmitAction  execute()  User Error: Username is required", LoggingManager.PRIORITY_DEBUG);
         }

         if (firstName.length() == 0)
         {
            // FirstName required.
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.firstname.required"));
            Constants.logManager.writeToLog("AddUserSubmitAction  execute()  User Error: First Name is required", LoggingManager.PRIORITY_DEBUG);
         }

         if (lastName.length() == 0)
         {
            // LastName required.
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.lastname.required"));
            Constants.logManager.writeToLog("AddUserSubmitAction  execute()  User Error: Last Name is required", LoggingManager.PRIORITY_DEBUG);
         }

         // Report any errors we have discovered back to the original form
         if (! errors.empty()) 
         {
            Constants.logManager.writeToLog("AddUserSubmitAction  execute()  Showing errors to user.  Try again.", LoggingManager.PRIORITY_DEBUG);
            saveErrors(request, errors);
            return (mapping.getInputForward());
         }

         try
         {
            // Validation complete.  Save the new user to the database.
            AddUserForm addUserForm = (AddUserForm)form;
            User user = addUserForm.getUser();
            Constants.userReqManager.addUser(user);
            // @TODO I18N: hardcoded strings, concantenation.
            String name = user.getFirstName()+" "+user.getLastName();
            request.setAttribute("status", "New user saved ("+name.trim()+").");
         }
         catch (java.lang.Exception e)
         {
            // @TODO I18N: hardcoded strings.
            request.setAttribute("status", "Error occured trying to save to the database.  The user was not added.");
            Constants.logManager.writeToLog(e);
         }
      }
      else
      {
         // Operation canceled. 
         Constants.logManager.writeToLog("AddUserSubmitAction  execute()  Add operation CANCELED.", LoggingManager.PRIORITY_DEBUG);
         // @TODO I18N: hardcoded strings.
         request.setAttribute("status", "Add new user canceled.");
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
      Constants.logManager.writeToLog("AddUserSubmitAction  execute()  END", LoggingManager.PRIORITY_DEBUG);
      return (mapping.findForward("success"));
   }

}
