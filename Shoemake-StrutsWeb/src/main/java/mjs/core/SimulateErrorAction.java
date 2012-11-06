package mjs.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.exceptions.CoreException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This is a test action used to generate an Exception and throw it 
 * back to the AbstractAction to test the error handling and the error
 * page. 
 */
public class SimulateErrorAction extends AbstractAction {

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
                                        HttpServletRequest req,
                                        HttpServletResponse res) throws Exception {

        CoreException ex = new CoreException("Simulating a root cause exception.");
        CoreException e = new CoreException("Simulating a top level exception.", ex);
        throw e;
    }

}
