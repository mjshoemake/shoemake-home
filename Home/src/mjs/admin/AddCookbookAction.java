package mjs.admin;

//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.core.Form;
import mjs.exceptions.ActionException;
import mjs.database.TableDataManager;
import mjs.view.ValidationErrorList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddCookbookAction extends AbstractAction {

   /**
    * Execute this action.
    * 
    * @param mapping
    * @param form
    * @param req Description of Parameter
    * @param res Description of Parameter
    * @return ActionForward
    * @exception Exception Description of Exception
    */
   public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
      metrics.startEvent("AddCookbook", "action");
      Form myForm = (Form)form;

      try {
          // Validate form data.
          TableDataManager dbMgr = getTable("CookbooksMapping.xml");
          ValidationErrorList errors = dbMgr.validateForm(myForm);
          if (errors.isEmpty()) {
             log.debug("Form validated successfully.");
             // Insert recipe.
             try {
               dbMgr.open();
               dbMgr.insertBean(myForm);
               dbMgr.close(true);
            }
            catch (Exception e) {
               dbMgr.close(false);
               throw e;
            }
            return (mapping.findForward("success"));
         } else {
            // Validation failed.
            log.debug("Form validation failed.");
            return (mapping.findForward("invalid"));
         }
      }
      catch (java.lang.Exception e) {
         ActionException ex = new ActionException("Error trying to save a new cookbook.", e);
         throw ex;
      }
      finally {
         metrics.endEvent("AddCookbook", "action");
         metrics.writeMetricsToLog();
      }
   }

}
