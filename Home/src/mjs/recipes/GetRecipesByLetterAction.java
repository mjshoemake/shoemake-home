package mjs.recipes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.database.DatabaseDriver;
import mjs.database.PaginatedList;
import mjs.exceptions.ActionException;
import mjs.recipes.RecipeManager;
import mjs.utils.Constants;
import mjs.utils.SingletonInstanceManager;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

//@SuppressWarnings("rawtypes")
public class GetRecipesByLetterAction extends AbstractAction {

   public static String PARAM_LETTER = "letter";

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
      metrics.startEvent("GetRecipesByLetter", "action");
      RecipeManager dbMgr = null;

      try {
         SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
         DatabaseDriver driver = (DatabaseDriver) mgr.getInstance("mjs.database.DatabaseDriver");

         clearBreadcrumbs(req);
         addBreadcrumbs(req, "Recipe List By Letter", "../GetRecipesByLetter.do");

         if (driver == null)
            throw new ActionException("Unable to create database managers.  Driver is null.");
         // Create the instance of SampleDataManager
         dbMgr = new RecipeManager(driver);
         log.debug("RecipeManager created.");

         // Validate form data.
         // String mappingFile = "mjs/recipes/RecipeMapping.xml";
         // Hashtable dataMapping = driver.loadMapping(mappingFile);
         log.debug("DataMapping file loaded.");

         // Get recipes for letter.
         try {
            String letter = req.getParameter(PARAM_LETTER);
            if (letter == null) {
               // Check session.
               Object obj = req.getSession().getAttribute(PARAM_LETTER);
               if (obj != null && obj instanceof String) {
                  letter = (String) obj;
               } else {
                  letter = "A";
               }
            }

            if (letter.length() > 1)
               throw new ActionException("Request parameter " + PARAM_LETTER + " must be a single character.  Found: " + letter);
            String whereClause = " where substring(name,1,1)='" + letter + "'";
            dbMgr.open();
            PaginatedList list = dbMgr.getRecipeList(whereClause, 30, 450, "/recipesByLetterJsp");
            dbMgr.close(true);
            req.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_CACHE, list);
            req.getSession().setAttribute(PARAM_LETTER, letter);
         }
         catch (Exception e) {
            dbMgr.close(false);
            throw e;
         }

         return (mapping.findForward("success"));
      }
      catch (java.lang.Exception e) {
         ActionException ex = new ActionException("Error trying to save a new recipe.", e);
         throw ex;
      }
      finally {
         metrics.endEvent("GetRecipesByLetter", "action");
         metrics.writeMetricsToLog();
      }
   }

}
