package mjs.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.aggregation.OrderedMap;
import mjs.core.AbstractAction;
import mjs.database.DatabaseConfig;
import mjs.database.TableDataManager;
import mjs.exceptions.ActionException;
import mjs.utils.Constants;
import mjs.utils.SingletonInstanceManager;
import mjs.view.ValidationErrorList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoginAction extends AbstractAction {

   /**
    * Execute this action.
    * 
    * @param mapping
    * @param form
    * @param req
    *           Description of Parameter
    * @param res
    *           Description of Parameter
    * @return ActionForward
    * @exception Exception
    *               Description of Exception
    */
   public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
      metrics.startEvent("Login", "action");
      LoginForm loginForm = (LoginForm) form;
      User user = new User();
      TableDataManager dbMgr = null;

      try {
         SingletonInstanceManager imgr = SingletonInstanceManager.getInstance();
         DatabaseConfig config = (DatabaseConfig)imgr.getInstance(DatabaseConfig.class.getName());
         dbMgr = config.getTable("LoginMapping.xml");
         
         //DatabaseDriver driver = (DatabaseDriver) mgr.getInstance("mjs.database.DatabaseDriver");
         //if (driver == null)
         //   throw new ActionException("Unable to create database managers.  Driver is null.");
         //String mappingFile = "/mjs/users/LoginMapping.xml";
         //String tableName = "users";
         //dbMgr = new TableDataManager(driver, "users", mappingFile, User.class);
         
         // Create the instance of SampleDataManager
         log.debug("UserManager created.");

         // Validate form data.
         OrderedMap dataMapping = dbMgr.loadMapping();
         log.debug("DataMapping file loaded.");
         ValidationErrorList errors = loginForm.validate(dataMapping);
         if (errors.isEmpty()) {
            log.debug("Form validated successfully.");
            // Validate the password.
            try {
               dbMgr.open();
               dbMgr.loadBean(user, " where username = '" + loginForm.getUsername() + "'");
               dbMgr.close();
               log.debug("User object retrieve for username " + loginForm.getUsername());
            }
            catch (Exception e) {
               dbMgr.close();
               throw e;
            }

            // Verify password.
            String pwEntered = loginForm.getPassword();
            log.debug("Entered: " + pwEntered + "  From DB: " + user.getPassword());
            if (user.getPassword().equals(pwEntered)) {
               // Successful login.
               log.debug("Login successful.");
               SessionManager.getInstance().invalidatePrevSession(loginForm.getUsername(), req.getSession());
               req.getSession().setAttribute(Constants.ATT_USER_ID, loginForm.getUsername());
               return (mapping.findForward("success"));
            } else {
               // Login failed.
               log.debug("Login failed.  Password doesn't match.");
               return (mapping.findForward("invalid"));
            }
         } else {
            // Validation failed.
            log.debug("Form validation failed.");
            return (mapping.findForward("invalid"));
         }
      }
      catch (java.lang.Exception e) {
         ActionException ex = new ActionException("Error trying to login.", e);
         throw ex;
      }
      finally {
         metrics.endEvent("Login", "action");
         metrics.writeMetricsToLog();
      }
   }

   /**
    * Is this action allowed to bypass authentication 
    * check?  If true, the action will be allowed to 
    * proceed even if no user attribute exists in the
    * session.  If false, the action will redirect to
    * the login page.
    * 
    * @return boolean
    */
   public boolean bypassAuthenticationCheck() {
      return true;
   }
   
}
