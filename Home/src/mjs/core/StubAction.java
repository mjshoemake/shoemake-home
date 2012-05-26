package mjs.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This is a stubbed action, used to send the application to a valid action
 * without a lot of fluff. This can be used to stub out areas that aren't ready
 * yet.
 */
public class StubAction extends AbstractAction {

    /**
     * This is the method that should be implemented to process the 
     * Http request (normally this is handled by the execute() method
     * but femto UI does this a little differently).  Exception handling
     * and rerouting the user to the error page is done by the base class
     * in the execute() method, so only errors NOT intended to trigger
     * an error page should be caught (ie. errors that should be handled
     * in other ways that do not constitute a failure).  
     * 
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @exception Exception Description of Exception
     */
    public ActionForward processRequest(ActionMapping mapping,
                                        ActionForm form,
                                        HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {

        log.debug("No action.  This action is stubbed out for testing purposes.");
        return mapping.findForward("success");
    }

}
