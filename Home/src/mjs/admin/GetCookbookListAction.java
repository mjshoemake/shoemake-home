package mjs.admin;

//import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.database.DatabaseDriver;
import mjs.database.PaginatedList;
import mjs.exceptions.ActionException;
//import mjs.recipes.RecipeForm;
import mjs.admin.CookbookManager;
import mjs.utils.Constants;
import mjs.utils.SingletonInstanceManager;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

//@SuppressWarnings("rawtypes")
public class GetCookbookListAction extends AbstractAction {

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
      metrics.startEvent("GetCookbookList", "action");
      CookbookManager dbMgr = null;

      try {
         SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
         DatabaseDriver driver = (DatabaseDriver) mgr.getInstance("mjs.database.DatabaseDriver");
         addBreadcrumbs(req, "Manage Cookbooks", "../GetCookbookList.do");

         if (driver == null)
            throw new ActionException("Unable to create database managers.  Driver is null.");
         // Create the instance of SampleDataManager
         dbMgr = new CookbookManager(driver);
         log.debug("CookbookManager created.");

         // Validate form data.
         // String mappingFile = "mjs/recipes/RecipeMapping.xml";
         // Hashtable dataMapping = driver.loadMapping(mappingFile);
         log.debug("DataMapping file loaded.");

         // Get list.
         try {
            dbMgr.open();
            PaginatedList list = dbMgr.getCookbookList(30, 450, "/cookbookListJsp");
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
         ActionException ex = new ActionException("Error trying to get the list of cookbooks.", e);
         throw ex;
      }
      finally {
         metrics.endEvent("GetCookbookList", "action");
         metrics.writeMetricsToLog();
      }
   }

}
