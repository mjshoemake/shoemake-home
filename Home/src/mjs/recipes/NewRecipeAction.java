
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


public class NewRecipeAction extends AbstractAction
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
      metrics.startEvent("NewRecipe", "action");
      CookbookManager dbMgr = null;
      MealsManager meMgr = null;
      MealCategoriesManager mcMgr = null;
      try
      {
         SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
         DatabaseDriver driver = (DatabaseDriver) mgr.getInstance("mjs.database.DatabaseDriver");
         
         addBreadcrumbs(req, "New Recipe", "../NewRecipe.do");
         
         if (driver == null)
            throw new ActionException("Unable to create database managers.  Driver is null.");
         // Create the instance of SampleDataManager
         dbMgr = new CookbookManager(driver);
         log.debug("CookbookManager created.");
         meMgr = new MealsManager(driver);
         log.debug("MealsManager created.");
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
            meMgr.open();
            ArrayList<SelectOption> options = meMgr.getMealsAsSelectOptions();
            req.setAttribute("meals", options);
         } finally {
            meMgr.close();
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
          metrics.endEvent("NewRecipe", "action");
          metrics.writeMetricsToLog();
      }
   }

}
