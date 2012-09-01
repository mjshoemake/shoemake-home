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
public class GetRecipesByLetterAction extends AbstractAction {

    public static final String GLOBAL_FORWARD = "/GetRecipesByLetter";

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
    public ActionForward processRequest(ActionMapping mapping,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res) throws Exception {
        metrics.startEvent("GetRecipesByLetter", "action");

        try {
            TableDataManager dbMgr = getTable("RecipeMapping.xml");
            if (dbMgr == null) {
                throw new ActionException("Unable to access database. TableDataManager not found in cache.");
            }
            clearBreadcrumbs(req);
            addBreadcrumbs(req, "Recipe List By Letter", "../GetRecipesByLetter.do");

            boolean listFound = false;
            PaginatedList list = (PaginatedList) req.getSession()
                .getAttribute(Constants.ATT_PAGINATED_LIST_CACHE);
            if (list != null && list.getGlobalForward().equals(GLOBAL_FORWARD)) {
                listFound = true;
            }
            log.debug("Matching PaginatedList retrieved from cache: " + listFound);

            boolean letterChanged = false;
            String letter = null;
            String paramLetter = req.getParameter(PARAM_LETTER);
            String savedLetter = (String) req.getSession().getAttribute(PARAM_LETTER);
            if (paramLetter == null) {
                // Check session.
                if (savedLetter != null) {
                    letter = savedLetter;
                    letterChanged = false;
                } else {
                    letter = "A";
                    letterChanged = true;
                }
            } else if (paramLetter.equals(savedLetter)) {
                letter = paramLetter;
                letterChanged = false;
            } else {
                letter = paramLetter;
                letterChanged = true;
            }

            // Have changes occured in the data (updates, adds, etc.) that
            // require
            // a reload of the data in the list?
            String listDirty = (String) req.getSession()
                .getAttribute(Constants.ATT_PAGINATED_LIST_DIRTY);
            boolean paginatedListDirty = false;
            if (listDirty != null) {
                paginatedListDirty = Boolean.parseBoolean(listDirty);
            }

            // Validate form data.
            // String mappingFile = "mjs/recipes/RecipeMapping.xml";
            // Hashtable dataMapping = driver.loadMapping(mappingFile);
            log.debug("DataMapping file loaded.");

            if (!listFound || letterChanged || paginatedListDirty) {

                // Get recipes for letter.
                try {
                    if (letter.length() > 1)
                        throw new ActionException("Request parameter " + PARAM_LETTER
                            + " must be a single character.  Found: " + letter);
                    String whereClause = " where substring(name,1,1)='" + letter + "'";
                    dbMgr.open();
                    list = dbMgr.loadList(whereClause, 30,450, GLOBAL_FORWARD);
                    dbMgr.close(true);
                    req.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_CACHE, list);
                    req.getSession().removeAttribute(Constants.ATT_PAGINATED_LIST_DIRTY);
                    req.getSession().setAttribute(PARAM_LETTER, letter);
                } catch (Exception e) {
                    if (dbMgr != null) {
                        dbMgr.close(false);
                    }
                    throw e;
                }
            }

            return (mapping.findForward("success"));
        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException("Error trying to save a new recipe.", e);
            throw ex;
        } finally {
            metrics.endEvent("GetRecipesByLetter", "action");
            metrics.writeMetricsToLog();
        }
    }

}
