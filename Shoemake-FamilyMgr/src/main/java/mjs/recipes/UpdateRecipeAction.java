package mjs.recipes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.exceptions.ActionException;
import mjs.database.PaginatedList;
import mjs.database.TableDataManager;
import mjs.model.Form;
import mjs.model.RecipeForm;
import mjs.utils.Constants;
import mjs.view.ValidationErrorList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UpdateRecipeAction extends AbstractAction {
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
        metrics.startEvent("UpdateRecipe", "action");
        Form myForm = (Form) form;
        RecipeForm recipeForm = (RecipeForm) form;

        try {
            TableDataManager dbMgr = getTable("RecipeMapping.xml");
            ValidationErrorList errors = dbMgr.validateForm(myForm);
            if (errors.isEmpty()) {
                log.debug("Form validated successfully.");
                // Update recipe.
                try {
                    dbMgr.open();
                    String whereClause = "where recipes_pk = " + recipeForm.getRecipes_pk();
                    dbMgr.updateBean(recipeForm, whereClause);
                    dbMgr.close(true);
                } catch (Exception e) {
                    dbMgr.close(false);
                    throw e;
                }

                // PaginatedList is now dirty.
                req.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_DIRTY, "true");

                // Get the paginated list.
                PaginatedList list = (PaginatedList) req.getSession()
                    .getAttribute(Constants.ATT_PAGINATED_LIST_CACHE);
                return (mapping.findForward(list.getGlobalForward()));
            } else {
                // Validation failed.
                log.debug("Form validation failed.");
                return (mapping.findForward("invalid"));
            }
        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException("Error trying to save a new recipe.", e);
            throw ex;
        } finally {
            metrics.endEvent("UpdateRecipe", "action");
            metrics.writeMetricsToLog();
        }
    }

}
