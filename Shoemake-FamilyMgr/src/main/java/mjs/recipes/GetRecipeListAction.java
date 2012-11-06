package mjs.recipes;

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
public class GetRecipeListAction extends AbstractAction {

    public static final String GLOBAL_FORWARD = "/GetRecipeList";

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
    public ActionForward processRequest(ActionMapping mapping,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res) throws Exception {
        metrics.startEvent("GetRecipeList", "action");

        try {
            TableDataManager dbMgr = getTable("RecipeMapping.xml");
            clearBreadcrumbs(req);
            addBreadcrumbs(req, "Recipe List", "../GetRecipeList.do");

            boolean listFound = false;
            PaginatedList list = (PaginatedList) req.getSession()
                .getAttribute(Constants.ATT_PAGINATED_LIST_CACHE);
            if (list != null && list.getGlobalForward().equals(GLOBAL_FORWARD)) {
                listFound = true;
            }
            log.debug("Matching PaginatedList retrieved from cache: " + listFound);

            // Have changes occured in the data (updates, adds, etc.) that
            // require a reload of the data in the list?
            String listDirty = (String) req.getSession()
                .getAttribute(Constants.ATT_PAGINATED_LIST_DIRTY);
            boolean paginatedListDirty = false;
            if (listDirty != null) {
                paginatedListDirty = Boolean.parseBoolean(listDirty);
            }

            if (!listFound || paginatedListDirty) {
                // Get all recipes.
                try {
                    String whereClause = " order by name";
                    dbMgr.open();
                    list = dbMgr.loadList(whereClause, 30,450, GLOBAL_FORWARD);
                    dbMgr.close(true);
                    req.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_CACHE, list);
                    req.getSession().removeAttribute(Constants.ATT_PAGINATED_LIST_DIRTY);
                } catch (Exception e) {
                    dbMgr.close(false);
                    throw e;
                }
            }

            return (mapping.findForward("success"));
        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException("Error trying to save a new recipe.", e);
            throw ex;
        } finally {
            metrics.endEvent("GetRecipeList", "action");
            metrics.writeMetricsToLog();
        }
    }

}
