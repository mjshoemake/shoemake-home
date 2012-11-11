package mjs.recipes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.exceptions.ActionException;
import mjs.database.TableDataManager;
import mjs.model.RecipeForm;
import mjs.utils.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MakeRecipeFavoriteAction extends AbstractAction {
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
        metrics.startEvent("MakeRecipeFavorite", "action");

        try {
            String recipes_pk = req.getParameter(Constants.PARAM_ID);
            TableDataManager dbMgr = getTable("RecipeMapping.xml");

            RecipeForm recipeForm = (RecipeForm) form;
            // Insert recipe.
            try {
                dbMgr.open();
                dbMgr.loadBean(recipeForm, " where recipes_pk = " + recipes_pk);
                dbMgr.close();
            } catch (Exception e) {
                dbMgr.close();
                throw e;
            }

            // Insert recipe.
            recipeForm.setFavorite("Yes");
            try {
                dbMgr.open();
                String whereClause = "where recipes_pk = " + recipeForm.getRecipes_pk();
                dbMgr.updateBean(recipeForm, whereClause);
                dbMgr.close(true);
            } catch (Exception e) {
                dbMgr.close(false);
                throw e;
            }

            return (mapping.findForward("success"));

        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException("Error make recipe a favorite.", e);
            throw ex;
        } finally {
            metrics.endEvent("MakeRecipeFavorite", "action");
            metrics.writeMetricsToLog();
        }
    }

}
