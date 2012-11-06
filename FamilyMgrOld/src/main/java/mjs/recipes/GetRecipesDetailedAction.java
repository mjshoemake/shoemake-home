package mjs.recipes;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.aggregation.OrderedMap;
import mjs.core.AbstractAction;
import mjs.database.PaginatedList;
import mjs.database.home.SearchManager;
import mjs.exceptions.ActionException;
import mjs.utils.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetRecipesDetailedAction extends AbstractAction {

    public static final String GLOBAL_FORWARD = "/GetRecipesDetailed";

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
        metrics.startEvent("GetRecipesDetailed", "action");

        try {
            SearchManager dbMgr = new SearchManager();
            clearBreadcrumbs(req);
            addBreadcrumbs(req, "Recipe Details", "../GetRecipesDetailed.do");

            boolean listFound = false;
            PaginatedList list = (PaginatedList) req.getSession()
                .getAttribute(Constants.ATT_PAGINATED_LIST_CACHE);
            if (list != null && list.getGlobalForward().equals(GLOBAL_FORWARD)) {
                listFound = true;
            }
            log.debug("Matching PaginatedList retrieved from cache: " + listFound);

            // Have changes occured in the data (updates, adds, etc.) that
            // require
            // a reload of the data in the list?
            String listDirty = (String) req.getSession()
                .getAttribute(Constants.ATT_PAGINATED_LIST_DIRTY);
            boolean paginatedListDirty = false;
            if (listDirty != null) {
                paginatedListDirty = Boolean.parseBoolean(listDirty);
            }
            log.debug("PaginatedList dirty: " + paginatedListDirty);

            String mappingFile = "/mjs/recipes/RecipeSearchMapping.xml";
            OrderedMap dataMapping = getDriver().loadMapping(mappingFile);

            // Remember filter options.
            int searchParamsChanged = 0;
            HashMap<String, String> params = (HashMap<String, String>) req.getSession()
                .getAttribute(Constants.ATT_SEARCH_CRITERIA);
            if (params == null) {
                params = new HashMap<String, String>();
            }
            if (req.getParameterMap().size() > 0) {
                Enumeration en = req.getParameterNames();
                while (en.hasMoreElements()) {
                    String name = en.nextElement().toString();
                    if (dataMapping.containsKey(name)) {
                        String newValue = req.getParameter(name);
                        String oldValue = params.get(name);
                        if (!newValue.equals(oldValue)) {
                            searchParamsChanged++;
                            params.put(name, newValue);
                        }
                    }
                }
                req.getSession().setAttribute(Constants.ATT_SEARCH_CRITERIA, params);
            }
            log.debug("Searched params changed from last used criteria: " + searchParamsChanged);

            //
            if (!listFound || searchParamsChanged > 0 || paginatedListDirty) {
                // Get recipes for letter.
                try {
                    dbMgr.open();
                    list = dbMgr.searchRecipes(params, 30, 450, GLOBAL_FORWARD);
                    list.setFieldDefs(dataMapping);
                    list.setFilterAction("/home/GetRecipesDetailed.do");
                    dbMgr.close(true);
                    req.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_CACHE, list);
                    req.getSession().removeAttribute(Constants.ATT_PAGINATED_LIST_DIRTY);
                    log.debug("Search data retrieved and persisted in the session.");
                } catch (Exception e) {
                    dbMgr.close(false);
                    throw e;
                }

            } else {
                log.debug("No search data retrieved.  To retrieve data, either: list must be null, params changed must be greater than 0, or paginated list must be dirty.");
            }

            return (mapping.findForward("success"));
        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException("Error trying to save a new recipe.", e);
            throw ex;
        } finally {
            metrics.endEvent("GetRecipesDetailed", "action");
            metrics.writeMetricsToLog();
        }
    }

}
