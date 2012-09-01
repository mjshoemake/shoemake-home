package mjs.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.admin.CookbookForm;
import mjs.core.AbstractAction;
import mjs.core.Form;
import mjs.database.TableDataManager;
import mjs.exceptions.ActionException;
import mjs.view.ValidationErrorList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UpdateCookbookAction extends AbstractAction {
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
        metrics.startEvent("UpdateCookbook", "action");
        Form myForm = (Form) form;
        CookbookForm cbForm = (CookbookForm)form;

        try {
            TableDataManager dbMgr = getTable("CookbooksMapping.xml");

            // Validate form data.
            String mappingFile = "mjs/admin/CookbooksMapping.xml";
            ValidationErrorList errors = dbMgr.validateForm(myForm);
            if (errors.isEmpty()) {
                log.debug("Form validated successfully.");
                // Insert recipe.
                try {
                    dbMgr.open();
                    dbMgr.updateBean(myForm, "where cookbooks_pk = " + cbForm.getCookbooks_pk());
                    dbMgr.close(true);
                } catch (Exception e) {
                    dbMgr.close(false);
                    throw e;
                }
                return (mapping.findForward("success"));
            } else {
                // Validation failed.
                log.debug("Form validation failed.");
                return (mapping.findForward("invalid"));
            }
        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException("Error trying to update a cookbook.", e);
            throw ex;
        } finally {
            metrics.endEvent("UpdateCookbook", "action");
            metrics.writeMetricsToLog();
        }
    }

}
