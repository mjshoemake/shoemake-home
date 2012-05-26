package mjs.admin;

//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.admin.CookbookManager;
import mjs.core.AbstractAction;
import mjs.exceptions.ActionException;
import mjs.database.DatabaseDriver;
import mjs.utils.Constants;
import mjs.utils.SingletonInstanceManager;
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
      CookbookForm myForm = (CookbookForm) form;
      CookbookManager dbMgr = null;

      try {
         SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
         DatabaseDriver driver = (DatabaseDriver) mgr.getInstance("mjs.database.DatabaseDriver");

         if (driver == null)
            throw new ActionException("Unable to create database managers.  Driver is null.");
         // Create the instance of SampleDataManager
         dbMgr = new CookbookManager(driver);
         log.debug("CookbookManager created.");

         String pk = req.getParameter(Constants.PARAM_ID);
         addBreadcrumbs(req, "Edit Cookbook", "../EditCookbook.do?id=" + pk);

         // Validate form data.
         //String mappingFile = "mjs/admin/CookbooksMapping.xml";
         //Hashtable dataMapping = driver.loadMapping(mappingFile);
         log.debug("DataMapping file loaded.");
         if (pk != null) {
            log.debug("Form validated successfully.");
            // Insert recipe.
            try {
               dbMgr.open();
               dbMgr.getCookbook(Integer.parseInt(pk), myForm);
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
