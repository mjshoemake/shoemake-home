package mjs.admin;

//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.aggregation.OrderedMap;
import mjs.core.AbstractAction;
import mjs.database.DatabaseDriver;
import mjs.exceptions.ActionException;
import mjs.utils.SingletonInstanceManager;
import mjs.view.ValidationErrorList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddMealAction extends AbstractAction {

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
      metrics.startEvent("AddMeal", "action");
      MealForm myForm = (MealForm)form;
      MealsManager dbMgr = null;

      try {
         SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
         DatabaseDriver driver = (DatabaseDriver) mgr.getInstance("mjs.database.DatabaseDriver");

         if (driver == null)
            throw new ActionException("Unable to create database managers.  Driver is null.");
         // Create the instance of SampleDataManager
         dbMgr = new MealsManager(driver);
         log.debug("MealsManager created.");

         // Validate form data.
         String mappingFile = "mjs/admin/MealsMapping.xml";
         OrderedMap dataMapping = driver.loadMapping(mappingFile);
         log.debug("DataMapping file loaded.");
         ValidationErrorList errors = myForm.validate(dataMapping);
         if (errors.isEmpty()) {
            log.debug("Form validated successfully.");
            // Insert recipe.
            try {
               dbMgr.open();
               dbMgr.insertMeal(myForm);
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
         metrics.endEvent("AddMeal", "action");
         metrics.writeMetricsToLog();
      }
   }

}
