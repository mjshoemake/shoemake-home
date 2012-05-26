
package mjs.recipes;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.admin.CookbookManager;
import mjs.admin.MealCategoriesManager;
import mjs.admin.MealsManager;
import mjs.core.AbstractAction;
import mjs.database.DatabaseDriver;
import mjs.exceptions.ActionException;
import mjs.utils.SingletonInstanceManager;
import mjs.view.SelectOption;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class NewTestRecipeAction extends AbstractAction
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
      metrics.startEvent("NewTestRecipe", "action");
      CookbookManager dbMgr = null;
      MealsManager meMgr = null;
      MealCategoriesManager mcMgr = null;
      try
      {
         SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
         DatabaseDriver driver = (DatabaseDriver) mgr.getInstance("mjs.database.DatabaseDriver");
         
         addBreadcrumbs(req, "New Test Recipe", "../NewTestRecipe.do");
         
         if (driver == null)
            throw new ActionException("Unable to create database managers.  Driver is null.");
         // Create the instance of SampleDataManager
         dbMgr = new CookbookManager(driver);
         log.debug("CookbookManager created.");
         mcMgr = new MealCategoriesManager(driver);
         log.debug("MealCategoriesManager created.");
         try {
            dbMgr.open();
            ArrayList<SelectOption> options = dbMgr.getCookbooksAsSelectOptions();
            req.setAttribute("cookbooks", options);
         } finally {
            dbMgr.close();
         }

         try {
            mcMgr.open();
            ArrayList<SelectOption> options = mcMgr.getMealCategoriesAsSelectOptions();
            req.setAttribute("categories", options);
         } finally {
            mcMgr.close();
         }
         
         return (mapping.findForward("success"));
      } catch (java.lang.Exception e) {
         ActionException ex = new ActionException("Error trying to create a new recipe.", e);
         throw ex;
      } finally {
          metrics.endEvent("NewTestRecipe", "action");
          metrics.writeMetricsToLog();
      }
   }

}
