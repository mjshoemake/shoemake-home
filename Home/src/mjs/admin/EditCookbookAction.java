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
import mjs.utils.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditCookbookAction extends AbstractAction {

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
      metrics.startEvent("EditCookbook", "action");
      Form myForm = (Form)form;

      try {
         String pk = req.getParameter(Constants.PARAM_ID);
         addBreadcrumbs(req, "Edit Cookbook", "../EditCookbook.do?id=" + pk);

         TableDataManager dbMgr = getTable("CookbooksMapping.xml");
         log.debug("DataMapping file loaded.");
         if (pk != null) {
            log.debug("Form validated successfully.");
            // Insert recipe.
            try {
               dbMgr.open();
               dbMgr.loadBean(myForm, " where cookbooks_pk = " + pk);
            } finally {
               dbMgr.close();
            }

            return (mapping.findForward("success"));
         } else {
            // Validation failed.
            log.debug("Primary key is missing or invalid.");
            return (mapping.findForward("invalid"));
         }
      }
      catch (java.lang.Exception e) {
         ActionException ex = new ActionException("Error trying to save a new recipe.", e);
         throw ex;
      }
      finally {
         metrics.endEvent("EditCookbook", "action");
         metrics.writeMetricsToLog();
      }
   }

}
