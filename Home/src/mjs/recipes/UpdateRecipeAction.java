
package mjs.recipes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.aggregation.OrderedMap;
import mjs.core.AbstractAction;
import mjs.exceptions.ActionException;
import mjs.database.DatabaseDriver;
import mjs.database.PaginatedList;
import mjs.recipes.RecipeForm;
import mjs.recipes.RecipeManager;
import mjs.utils.Constants;
import mjs.utils.SingletonInstanceManager;
import mjs.view.ValidationErrorList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class UpdateRecipeAction extends AbstractAction
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
      metrics.startEvent("UpdateRecipe", "action");
      RecipeForm recipeForm = (RecipeForm)form;
      RecipeManager dbMgr = null;

      try
      {
          SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
          DatabaseDriver driver = (DatabaseDriver)mgr.getInstance("mjs.database.DatabaseDriver");

         if (driver == null)
            throw new ActionException("Unable to create database managers.  Driver is null.");
         //Create the instance of SampleDataManager
         dbMgr = new RecipeManager(driver);
         log.debug("RecipeManager created.");

         // Validate form data.
         String mappingFile = "mjs/recipes/RecipeMapping.xml";
         OrderedMap dataMapping = driver.loadMapping(mappingFile);
         log.debug("DataMapping file loaded.");
         ValidationErrorList errors = recipeForm.validate(dataMapping);
         if (errors.isEmpty())
         {
        	 log.debug("Form validated successfully.");
             // Insert recipe.
             try {
                 dbMgr.open();
                 dbMgr.updateRecipe(recipeForm);
                 dbMgr.close(true);
             } catch (Exception e) {
                 dbMgr.close(false);
                 throw e;
             }
             // Get the paginated list.
             PaginatedList list = (PaginatedList)req.getSession().getAttribute(Constants.ATT_PAGINATED_LIST_CACHE);
             return (mapping.findForward(list.getGlobalForward()));
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
         ActionException ex = new ActionException("Error trying to save a new recipe.", e);
         throw ex;
      } finally {
          metrics.endEvent("UpdateRecipe", "action");
          metrics.writeMetricsToLog();
      }
   }

}