package mjs.recipes;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.aggregation.OrderedMap;
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


@SuppressWarnings("rawtypes")
public class GetRecipesDetailedAction extends AbstractAction {

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
      metrics.startEvent("GetRecipesDetailed", "action");
      RecipeManager dbMgr = null;

      try {
         SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
         DatabaseDriver driver = (DatabaseDriver) mgr.getInstance("mjs.database.DatabaseDriver");

         clearBreadcrumbs(req);
         addBreadcrumbs(req, "Recipe Details", "../GetRecipesDetailed.do");

         if (driver == null)
            throw new ActionException("Unable to create database managers.  Driver is null.");
         // Create the instance of SampleDataManager
         dbMgr = new RecipeManager(driver);
         log.debug("RecipeManager created.");

         String mappingFile = "mjs/recipes/RecipeSearchMapping.xml";
         OrderedMap dataMapping = driver.loadMapping(mappingFile);
         
         // Remember filter options.
         HashMap<String,String> params = (HashMap<String,String>)req.getSession().getAttribute(Constants.ATT_SEARCH_CRITERIA);
         if (params == null) {
            params = new HashMap<String,String>();
         }
         if (req.getParameterMap().size() > 0) {
            Enumeration en = req.getParameterNames();
            while (en.hasMoreElements()) {
               String name = en.nextElement().toString();
               String value = req.getParameter(name);
               params.put(name, value);
            }
            req.getSession().setAttribute(Constants.ATT_SEARCH_CRITERIA, params);
         } 
         
         // Get recipes for letter.
         try {
            dbMgr.open();
            PaginatedList list = dbMgr.searchRecipes(params, 30, 450, "/recipesDetailedJsp");
            list.setFieldDefs(dataMapping);
            list.setFilterAction("/home/GetRecipesDetailed.do");
            dbMgr.close(true);
            req.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_CACHE, list);
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
         metrics.endEvent("GetRecipesDetailed", "action");
         metrics.writeMetricsToLog();
      }
   }

}
