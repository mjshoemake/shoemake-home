package mjs.core;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import mjs.exceptions.InvalidSessionException;
import mjs.utils.Constants;
import mjs.setup.MainProperties;

/**
 * Struts dispatch action class which is the base class for Femto 
 * DispatchAction classes.  It includes a Log4J integration, so log 
 * messages can be sent without instantiating the Logger object.
 * <p>
 * Example action handler method:
 * <p>
 * <pre>
 *  public final ActionForward addUser(ActionMapping mapping,
 *                                     ActionForm form,
 *                                     HttpServletRequest request,
 *                                     HttpServletResponse response) throws Exception {
 *      try {
 *          // Write out the request params to the log.
 *          traceAttributes(request);
 *          
 *          // CODE TO PROCESS THE REQUEST GOES HERE.
 *          
 *      } catch (java.lang.Exception e) {
 *          // Handle the error.
 *          request.setAttribute("exception", e);
 *          log.error(e.getMessage(), e);
 *          return error(mapping, request, e);
 *      }
 *  }
 * </pre>
 */
public abstract class AbstractMappingDispatchAction extends MappingDispatchAction {
    /**
     * The log4j logger to use when writing log messages.
     */
    protected Logger log = Logger.getLogger("Actions");

    /**
     * The log4j logger to use when writing audit log 
     * messages. 
     */
    protected Logger auditLog = Logger.getLogger("Audit");

    /**
     * Constructor. Because no category is specified in this constructor, the
     * category defaults to "Action".
     */
    public AbstractMappingDispatchAction() {
        this(null);
    }

    /**
     * Constructor. If the category is null, it will default to "Action".
     * 
     * @param category
     */
    public AbstractMappingDispatchAction(String category) {
        try {
            if (category != null)
                log = Logger.getLogger(category);
        } catch (java.lang.Exception e) {
            handleException(e);
        }
    }

    /**
     * Execute this action.  Normally in Struts, the execute() method
     * is implemented in the application-specific classes.  Instead, 
     * we are declaring the execute method as final and are providing
     * an abstract processRequest() method that must be implemented 
     * within the child action classes.  This gives the base action 
     * more control to consistently perform certain actions (consistent
     * error handling, tracing out the request information, etc.).   
     * 
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @exception Exception Description of Exception
     */
    public final ActionForward execute(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        try {
            // Write out the request params to the log.
            traceAttributes(request);
    
            // Process the request.
            return super.execute(mapping, form, request, response);
        } catch (InvalidSessionException e) {
            log.info(e.getMessage() + "  Invalid session.  Redirecting to login page...");
            return (mapping.findForward(Constants.FWD_SESSION_TIMEOUT));
        } catch (Exception e) {
            // Handle the error.
            request.setAttribute("exception", e);
            log.error(e.getMessage(), e);
            return error(mapping, request, e);
        }
    }

    /**
     * This method should be overridden to provide consistent functionality for
     * what should occur when an exception is caught in an action class. The
     * default version simply writes the exception to the log.
     * 
     * @param e
     */
    public void handleException(java.lang.Exception e) {
        log.error(e.getMessage(), e);
    }

    /**
     * Forward to the error page, publishing the exception as a request
     * parameter if the test.environment is set to true.
     * 
     * @param mapping
     * @param e
     * @return ActionForward
     */
    public ActionForward error(ActionMapping mapping,
                               HttpServletRequest req,
                               java.lang.Exception e) {

        String testEnvironment = MainProperties.getInstance().getProperty(
            Constants.PROP_TEST_ENVIRONMENT);
        log.debug("testEnvironment=" + testEnvironment);
        
        if (testEnvironment == null || testEnvironment.equalsIgnoreCase("true")) {
            req.setAttribute("exception", e);
        }

        req.setAttribute("pageTitle", "System Error");
        req.setAttribute("pageDescription",
            "A system error has occurred.  Please notify your system administrator.");
        return mapping.findForward("/Error");
    }

    /**
     * Trace out the parameters and attributes received in the request.
     */
    @SuppressWarnings("rawtypes")
    public void traceAttributes(HttpServletRequest req) throws Exception {
        Enumeration enumeration = null;
        int x = 0;
        if (! bypassAuthenticationCheck() && req.getSession() == null) {
            req.getSession().invalidate();
            throw new InvalidSessionException("Session expired.  Please login again.");
        }    

        String userID = (String) req.getSession().getAttribute(Constants.ATT_USER_ID);
        if (userID != null) {
            log.info("User: " + userID);
            auditLog.info("User: " + userID);
        } else if (! bypassAuthenticationCheck()) {
            req.getSession().invalidate();
            throw new InvalidSessionException("Session expired.  Please login again.");
        }    

        // Log request parameters.
        enumeration = req.getParameterNames();
        x = 0;
        log.info("Request Parameters:");
        auditLog.info("Request Parameters:");
        while (enumeration.hasMoreElements()) {
            x++;
            String name = enumeration.nextElement().toString();
            log.info("   parameter[" + x + "]: " + name + " -> "
                + getParameterValuesAsString(name, req));
            auditLog.info("   parameter[" + x + "]: " + name + " -> "
                + getParameterValuesAsString(name, req));
        }

        log.info("Request Attributes:");
        enumeration = req.getAttributeNames();
        x = 0;
        while (enumeration.hasMoreElements()) {
            x++;
            String name = enumeration.nextElement().toString();
            log.info("   attribute[" + x + "]: " + name + " -> "
                + req.getAttribute(name).toString());
        }

        log.debug("Session Attributes:");
        HttpSession session = req.getSession();
        enumeration = session.getAttributeNames();
        x = 0;
        while (enumeration.hasMoreElements()) {
            x++;
            String name = enumeration.nextElement().toString();
            log.debug("   attribute[" + x + "]: " + name + " -> "
                + session.getAttribute(name).toString());
        }
    }

    /**
     * Get the list of parameter values as a String (comma separated).
     * 
     * @param name String
     * @param req HttpServletRequest
     * @return String
     */
    private String getParameterValuesAsString(String name,
                                              HttpServletRequest req) {
        StringBuffer result = new StringBuffer();
        String[] items = req.getParameterValues(name);

        for (int C = 0; C <= items.length - 1; C++) {
            if (C > 0)
                result.append(", ");
            result.append(items[C]);
        }
        return result.toString();
    }

    /**
     * Is this action allowed to bypass authentication 
     * check?  If true, the action will be allowed to 
     * proceed even if no user attribute exists in the
     * session.  If false, the action will redirect to
     * the login page.
     * 
     * @return boolean
     */
    public boolean bypassAuthenticationCheck() {
        return false;
    }
}
