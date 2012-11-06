package mjs.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.exceptions.ActionException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NewFamilyMemberAction extends AbstractAction {
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
        metrics.startEvent("NewFamilyMember", "action");
        try {
            addBreadcrumbs(req, "New Family Member", "../NewFamilyMember.do");
            return (mapping.findForward("success"));
        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException("Error trying to create a new family member.", e);
            throw ex;
        } finally {
            metrics.endEvent("NewFamilyMember", "action");
            metrics.writeMetricsToLog();
        }
    }

}
