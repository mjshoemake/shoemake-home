package mjs.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.exceptions.ActionException;
import mjs.database.PaginatedList;
import mjs.database.TableDataManager;
import mjs.utils.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class DeleteFamilyMemberAction extends AbstractAction {
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
        metrics.startEvent("DeleteFamilyMember", "action");
        DynaActionForm actionForm = (DynaActionForm)form;

        try {
            TableDataManager dbMgr = getTable("FamilyMemberMapping.xml");
            try {
                dbMgr.open();
                String whereClause = "where family_member_pk = " + actionForm.getString("family_member_pk");
                dbMgr.delete(whereClause);
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
        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException("Error trying to delete a family member.", e);
            throw ex;
        } finally {
            metrics.endEvent("DeleteFamilyMember", "action");
            metrics.writeMetricsToLog();
        }
    }

}
