package mjs.recipes;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.database.DataManager;
import mjs.exceptions.ActionException;
import mjs.view.SelectOption;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NewTestRecipeAction extends AbstractAction {
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
        metrics.startEvent("NewTestRecipe", "action");
        try {
            DataManager dbMgr = new DataManager(getDriver());
            addBreadcrumbs(req, "New Test Recipe", "../NewTestRecipe.do");

            try {
                dbMgr.open();
                ArrayList<SelectOption> options = dbMgr.getSelectOptions("cookbooks",
                                                                         "cookbooks_pk",
                                                                         "name");
                req.setAttribute("cookbooks", options);
            } finally {
                dbMgr.close();
            }

            try {
                dbMgr.open();
                ArrayList<SelectOption> options = dbMgr.getSelectOptions("meals",
                                                                         "meals_pk",
                                                                         "name");
                req.setAttribute("meals", options);
            } finally {
                dbMgr.close();
            }

            try {
                dbMgr.open();
                ArrayList<SelectOption> options = dbMgr.getSelectOptions("meal_categories",
                                                                         "meal_categories_pk",
                                                                         "name");
                req.setAttribute("categories", options);
            } finally {
                dbMgr.close();
            }

            return (mapping.findForward("success"));
        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException("Error trying to create a new recipe.", e);
            throw ex;
        } finally {
            metrics.endEvent("NewTestRecipe", "action");
            metrics.writeMetricsToLog();
        }
    }

}
