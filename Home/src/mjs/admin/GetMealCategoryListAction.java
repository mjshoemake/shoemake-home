package mjs.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.database.PaginatedList;
import mjs.database.TableDataManager;
import mjs.exceptions.ActionException;
import mjs.utils.Constants;
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
    public ActionForward processRequest(ActionMapping mapping,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res) throws Exception {
        metrics.startEvent("GetMealCategoryList", "action");

        try {
            TableDataManager dbMgr = getTable("MealCategoriesMapping.xml");
            addBreadcrumbs(req, "Manage Meal Categories", "../GetMealCategoryList.do");

            // Get list.
            try {
                dbMgr.open();
                PaginatedList list = dbMgr.loadList("order by name", 30, 450, "/mealCategoryListJsp");
                req.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_CACHE, list);
            } catch (Exception e) {
                throw e;
            } finally {
                dbMgr.close();
            }

            return (mapping.findForward("success"));
        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException(
                "Error trying to get the list of meal categories.", e);
            throw ex;
        } finally {
            metrics.endEvent("GetMealCategoryList", "action");
            metrics.writeMetricsToLog();
        }
    }

}
