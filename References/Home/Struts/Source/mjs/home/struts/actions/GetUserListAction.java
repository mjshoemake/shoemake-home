/* File name:     GetUserListAction.java
 * Package name:  mjs.home.struts.actions
 * Project name:  Home - Struts
 * Date Created:  Jun 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.struts.actions;

// Java imports
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.home.struts.Constants;
import mjs.home.model.User;
import mjs.home.model.DatabaseUserRequestManager;

/**
 * Struts action class used to display the user list.  
 * This is invoked by calling GetUserList.do.
 * 
 * @author mshoemake
 */
public class GetUserListAction extends DispatchAction
{
   /**
    * Execute this Struts action, returning a list of users sorted
    * by last name.  This is invoked by calling GetUserList.do?method=sortLastName
    * 
    * @param mapping     The ActionMapping used to select this instance
    * @param form        The optional ActionForm bean for this request (if any)
    * @param request     The HTTP request we are processing
    * @param response    The HTTP response we are creating
    * throws Exception
    */
   public ActionForward sortLastName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      // @TODO I18N: hardcoded strings.
      request.setAttribute("status", "Sorted user list by last name.");
      // Sort by last name.
      return retrieveList(DatabaseUserRequestManager.SORT_LASTNAME, mapping, form, request, response);
   }

   /**
    * Execute this Struts action, returning a list of users sorted
    * by last name in descending order.  This is invoked by calling 
    * GetUserList.do?method=sortLastNameDesc
    * 
    * @param mapping     The ActionMapping used to select this instance
    * @param form        The optional ActionForm bean for this request (if any)
    * @param request     The HTTP request we are processing
    * @param response    The HTTP response we are creating
    * throws Exception
    */
   public ActionForward sortLastNameDesc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      // @TODO I18N: hardcoded strings.
      request.setAttribute("status", "Sorted user list by last name in descending order.");
      // Sort by last name in descending order.
      return retrieveList(DatabaseUserRequestManager.SORT_LASTNAME_DESC, mapping, form, request, response);
   }

   /**
    * Execute this Struts action, returning a list of users sorted
    * by first name.  This is invoked by calling GetUserList.do?method=sortFirstName
    * 
    * @param mapping     The ActionMapping used to select this instance
    * @param form        The optional ActionForm bean for this request (if any)
    * @param request     The HTTP request we are processing
    * @param response    The HTTP response we are creating
    * throws Exception
    */
   public ActionForward sortFirstName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      // @TODO I18N: hardcoded strings.
      request.setAttribute("status", "Sorted user list by first name.");
      // Sort by first name.
      return retrieveList(DatabaseUserRequestManager.SORT_FIRSTNAME, mapping, form, request, response);
   }

   /**
    * Execute this Struts action, returning a list of users sorted
    * by first name in descending order.  This is invoked by calling 
    * GetUserList.do?method=sortFirstNameDesc
    * 
    * @param mapping     The ActionMapping used to select this instance
    * @param form        The optional ActionForm bean for this request (if any)
    * @param request     The HTTP request we are processing
    * @param response    The HTTP response we are creating
    * throws Exception
    */
   public ActionForward sortFirstNameDesc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      // @TODO I18N: hardcoded strings.
      request.setAttribute("status", "Sorted user list by first name in descending order.");
      // Sort by first name in descending order.
      return retrieveList(DatabaseUserRequestManager.SORT_FIRSTNAME_DESC, mapping, form, request, response);
   }

   /**
    * Execute this Struts action, returning a list of users sorted
    * by username.  This is invoked by calling GetUserList.do?method=sortFirstName
    * 
    * @param mapping     The ActionMapping used to select this instance
    * @param form        The optional ActionForm bean for this request (if any)
    * @param request     The HTTP request we are processing
    * @param response    The HTTP response we are creating
    * throws Exception
    */
   public ActionForward sortUserName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      // @TODO I18N: hardcoded strings.
      request.setAttribute("status", "Sorted user list by user name.");
      // Sort by username.
      return retrieveList(DatabaseUserRequestManager.SORT_USERNAME, mapping, form, request, response);
   }

   /**
    * Execute this Struts action, returning a list of users sorted
    * by username in descending order.  This is invoked by calling 
    * GetUserList.do?method=sortUserNameDesc
    * 
    * @param mapping     The ActionMapping used to select this instance
    * @param form        The optional ActionForm bean for this request (if any)
    * @param request     The HTTP request we are processing
    * @param response    The HTTP response we are creating
    * throws Exception
    */
   public ActionForward sortUserNameDesc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      // @TODO I18N: hardcoded strings.
      request.setAttribute("status", "Sorted user list by user name in descending order.");
      // Sort by username in descending order.
      return retrieveList(DatabaseUserRequestManager.SORT_USERNAME_DESC, mapping, form, request, response);
   }

   /**
    * Execute this Struts action, returning a list of users sorted
    * by username in descending order.  This is invoked by calling 
    * GetUserList.do?method=sortUserNameDesc
    * 
    * @param mapping     The ActionMapping used to select this instance
    * @param form        The optional ActionForm bean for this request (if any)
    * @param request     The HTTP request we are processing
    * @param response    The HTTP response we are creating
    * throws Exception
    */
   public ActionForward sortMostRecent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      // Sort using most recent order.
      String sortorder = (String)(request.getSession().getAttribute("sortorder"));
      if (sortorder == null)
      {
         // If not set, default to last name sortation.
         // This value will get stored in the session by retrieveList();         
         sortorder = DatabaseUserRequestManager.SORT_LASTNAME+"";
      }
             
      return retrieveList(Integer.parseInt(sortorder), mapping, form, request, response);
   }

   /**
    * Execute this Struts action, using the specified sort order.  This is invoked by calling
    * /GetUserList.do.
    * 
    * @param sortorder   The sort order to use when retrieving the list.
    * @param mapping     The ActionMapping used to select this instance
    * @param form        The optional ActionForm bean for this request (if any)
    * @param request     The HTTP request we are processing
    * @param response    The HTTP response we are creating
    * throws Exception
    */
   private ActionForward retrieveList(int sortorder, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      Constants.logManager.writeToLog("GetUserListAction  execute()  BEGIN", LoggingManager.PRIORITY_DEBUG);

      Constants.logManager.writeToLog("GetUserListAction  execute()  Retrieving list of users...  Sort Order="+sortorder, LoggingManager.PRIORITY_DEBUG);
      Vector userlist = Constants.userReqManager.getUserList(sortorder).asVector();
      Constants.logManager.writeToLog("GetUserListAction  execute()  Retrieving list of users successful.", LoggingManager.PRIORITY_DEBUG);

      // @TODO I18N: hardcoded strings.
      if (request.getAttribute("status") == null)
      {
         // If null, set the attribute with a blank.
         request.setAttribute("status", "");
      }

      // Save the sort order for later.
      request.getSession().setAttribute("sortorder", sortorder+"");
      // Trace out the contents of the userlist.
      for (int C=0; C <= userlist.size()-1; C++)
      {
         User user = (User)(userlist.get(C));
         Constants.logManager.writeToLog("GetUserListAction  execute()  User #"+C+": "+user.getUserName()+", "+user.getFirstName()+" "+user.getLastName()+"  ID="+user.getUserID(), LoggingManager.PRIORITY_DEBUG);
      }

      // Return data back to the JSP page.
      request.setAttribute(Constants.RETURN_VALUE_KEY, userlist);

      // Give the list of users to the form.  This is used for the 
      // checkboxes when the user clicks "Delete Users" button.
      Constants.logManager.writeToLog("GetUserListAction  execute()  Unselecting all checkboxes...", LoggingManager.PRIORITY_DEBUG);
      String[] selection = {};
      
      GetUserListForm getUserListForm = (GetUserListForm)form;
      getUserListForm.setUserList(userlist);
      getUserListForm.setSelectedItems(selection);

      // Forward control to the specified success URI
      Constants.logManager.writeToLog("GetUserListAction  execute()  END", LoggingManager.PRIORITY_DEBUG);
      return (mapping.findForward("success"));
   }

}
