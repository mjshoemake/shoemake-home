package mjs.recipes;

//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.admin.CookbookManager;
import mjs.admin.MealsManager;
import mjs.admin.MealCategoriesManager;
import mjs.core.AbstractAction;
import mjs.database.DatabaseDriver;
import mjs.exceptions.ActionException;
import mjs.recipes.RecipeForm;
import mjs.recipes.RecipeManager;
import mjs.utils.Constants;
import mjs.utils.SingletonInstanceManager;
import mjs.view.SelectOption;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditRecipeAction extends AbstractAction {

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
      metrics.startEvent("EditRecipe", "action");
      RecipeForm recipeForm = (RecipeForm) form;
      RecipeManager dbMgr = null;
      CookbookManager cbMgr = null;
      MealsManager meMgr = null;
      MealCategoriesManager mcMgr = null;

      try {
         SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
         DatabaseDriver driver = (DatabaseDriver) mgr.getInstance("mjs.database.DatabaseDriver");

         if (driver == null)
            throw new ActionException("Unable to create database managers.  Driver is null.");
         // Create the instance of SampleDataManager
         dbMgr = new RecipeManager(driver);
         log.debug("RecipeManager created.");
         cbMgr = new CookbookManager(driver);
         log.debug("CookbookManager created.");
         meMgr = new MealsManager(driver);
         log.debug("MealsManager created.");
         mcMgr = new MealCategoriesManager(driver);
         log.debug("MealCategoriesManager created.");

         String pk = req.getParameter(Constants.PARAM_ID);
         addBreadcrumbs(req, "Edit Recipe", "../EditRecipe.do?id=" + pk);

         // Validate form data.
         log.debug("DataMapping file loaded.");
         if (pk != null) {
            log.debug("Form validated successfully.");
            // Insert recipe.
            try {
               dbMgr.open();
               dbMgr.getRecipe(Integer.parseInt(pk), recipeForm);
               dbMgr.close(true);
            }
            catch (Exception e) {
               dbMgr.close(false);
               throw e;
            }

            try {
               cbMgr.open();
               ArrayList<SelectOption> options = cbMgr.getCookbooksAsSelectOptions();
               req.setAttribute("cookbooks", options);
            } finally {
               cbMgr.close();
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
         metrics.endEvent("EditRecipe", "action");
         metrics.writeMetricsToLog();
      }
   }

}
