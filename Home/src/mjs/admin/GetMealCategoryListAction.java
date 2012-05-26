package mjs.admin;

//import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.database.DatabaseDriver;
import mjs.database.PaginatedList;
import mjs.exceptions.ActionException;
import mjs.admin.MealCategoriesManager;
import mjs.utils.Constants;
import mjs.utils.SingletonInstanceManager;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

//@SuppressWarnings("rawtypes")
public class GetMealCategoryListAction extends AbstractAction {

   /**
    * Execute this action.
    * 
    * @param mapping ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @param res HttpServletResponse
    * @return ActionForward
    * @exception Exception Description of Exception
    */
   public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
      metrics.startEvent("GetMealCategoryList", "action");
      MealCategoriesManager dbMgr = null;

      try {
         SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
         DatabaseDriver driver = (DatabaseDriver) mgr.getInstance("mjs.database.DatabaseDriver");
         addBreadcrumbs(req, "Manage Meal Categories", "../GetMealCategoryList.do");

         if (driver == null)
            throw new ActionException("Unable to create database managers.  Driver is null.");
         // Create the instance of SampleDataManager
         dbMgr = new MealCategoriesManager(driver);
         log.debug("MealCategoriesManager created.");

         // Validate form data.
         // String mappingFile = "mjs/recipes/RecipeMapping.xml";
         // Hashtable dataMapping = driver.loadMapping(mappingFile);
         log.debug("DataMapping file loaded.");

         // Get list.
         try {
            dbMgr.open();
            PaginatedList list = dbMgr.getMealCategoryList(30, 450, "/mealCategoryListJsp");
            req.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_CACHE, list);
         }
         catch (Exception e) {
            throw e;
         } finally {
            dbMgr.close();
         }

         return (mapping.findForward("success"));
      }
      catch (java.lang.Exception e) {
         ActionException ex = new ActionException("Error trying to get the list of meal categories.", e);
         throw ex;
      }
      finally {
         metrics.endEvent("GetMealCategoryList", "action");
         metrics.writeMetricsToLog();
      }
   }

}
