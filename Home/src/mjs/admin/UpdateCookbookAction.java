
package mjs.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.admin.CookbookForm;
import mjs.aggregation.OrderedMap;
import mjs.core.AbstractAction;
import mjs.database.DatabaseDriver;
import mjs.exceptions.ActionException;
import mjs.utils.SingletonInstanceManager;
import mjs.view.ValidationErrorList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class UpdateCookbookAction extends AbstractAction
{
   /**
    * Execute this action.
    *
    * @param mapping
    * @param form
    * @param req                Description of Parameter
    * @param res                Description of Parameter
    * @return                   ActionForward
    * @exception Exception      Description of Exception
    */
   public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception
   {
      metrics.startEvent("UpdateCookbook", "action");
      CookbookForm myForm = (CookbookForm)form;
      CookbookManager dbMgr = null;

      try
      {
          SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
          DatabaseDriver driver = (DatabaseDriver)mgr.getInstance("mjs.database.DatabaseDriver");

         if (driver == null)
            throw new ActionException("Unable to create database managers.  Driver is null.");
         dbMgr = new CookbookManager(driver);
         log.debug("CookbookManager created.");

         // Validate form data.
         String mappingFile = "mjs/admin/CookbooksMapping.xml";
         OrderedMap dataMapping = driver.loadMapping(mappingFile);
         log.debug("DataMapping file loaded.");
         ValidationErrorList errors = myForm.validate(dataMapping);
         if (errors.isEmpty())
         {
        	 log.debug("Form validated successfully.");
             // Insert recipe.
             try {
                 dbMgr.open();
                 dbMgr.updateCookbook(myForm);
                 dbMgr.close(true);
             } catch (Exception e) {
                 dbMgr.close(false);
                 throw e;
             }
            return (mapping.findForward("success"));
         }
         else
         {
        	// Validation failed. 
            log.debug("Form validation failed.");
            return (mapping.findForward("invalid"));
         }
      }
      catch (java.lang.Exception e)
      {
         ActionException ex = new ActionException("Error trying to update a cookbook.", e);
         throw ex;
      } finally {
          metrics.endEvent("UpdateCookbook", "action");
          metrics.writeMetricsToLog();
      }
   }

}
