package mjs.core;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import mjs.exceptions.InvalidSessionException;
import mjs.setup.MainProperties;
import mjs.utils.Constants;
import mjs.utils.PerformanceMetrics;
import mjs.view.Breadcrumbs;

/**
 * Struts action class which is the base class for Femto Struts applications. It
 * includes a Log4J integration, so log messages can be sent without
 * instantiating the Logger object.
 * <p>
 * Ex. <code>log.debug("Hello");</code>
 */
public abstract class AbstractAction extends Action {
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
	 * Used to track and monitor performance statistics.
	 */
	protected PerformanceMetrics metrics = new PerformanceMetrics();
    
    /**
     * Constructor. Because no category is specified in this constructor, the
     * category defaults to "Action".
     */
    public AbstractAction() {
        this(null);
    }

    /**
     * Constructor. If the category is null, it will default to "Action".
     * 
     * @param category
     */
    public AbstractAction(String category) {
        try {
            if (category != null)
                log = Logger.getLogger(category);
        } catch (java.lang.Exception e) {
            handleException(e);
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

        log.info("ACTION: " + this.getClass().getName());
        String userID = (String) req.getSession().getAttribute(Constants.ATT_USER_ID);
        log.info("User: " + userID);
        auditLog.info("User: " + userID);
        if (userID == null && ! bypassAuthenticationCheck()) {
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
            if(!name.equals("password")){
               auditLog.info("   parameter[" + x + "]: " + name + " -> "
                  + getParameterValuesAsString(name, req));
            }
            else{
            	auditLog.info("   parameter[" + x + "]: " + name + " -> "
                  +"password is hidden for security reasons");
            }
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
    public ActionForward execute(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        try {
            // Write out the request params to the log.
            traceAttributes(request);
    
            // Process the request.
            return processRequest(mapping, form, request, response);
        } catch (InvalidSessionException e) {
            log.info(e.getMessage() + "  Invalid session.  Redirecting to login page...");
            return (mapping.findForward(Constants.FWD_SESSION_TIMEOUT));
        } catch (Exception e) {
            // Handle the error.
            request.setAttribute("exception", e);
            log.error("ERROR: " + e.getMessage(), e);
            return error(mapping, request, e);
        }
    }

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
    public abstract ActionForward processRequest(ActionMapping mapping,
                                                 ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception;

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
        String testEnvironment = MainProperties.getInstance().getProperty(
                Constants.PROP_TEST_ENVIRONMENT);
        if (testEnvironment == null || testEnvironment.equalsIgnoreCase("true")) {
        	return true;
        } else {
            return false;
        }    
    }
    
    /**
     * Set the status message to be displayed to the UI.
     * @param request HttpServletRequest
     */
    public void setStatusMsg(HttpServletRequest req, String msg) {
    	req.setAttribute(Constants.ATT_STATUS_MSG, msg);
    }
    
    /**
     * Get the breadcrumbs list.
     * @param req HttpServletRequest
     * @return Breadcrumbs
     */
    private Breadcrumbs getBreadcrumbs(HttpServletRequest req) {
       Breadcrumbs result = null;
       HttpSession session = req.getSession();
       Object obj = session.getAttribute(Constants.ATT_BREADCRUMBS);
       if (obj != null && obj instanceof Breadcrumbs) {
           result = ( Breadcrumbs)obj;
       } else {
          result = new Breadcrumbs();
          session.setAttribute(Constants.ATT_BREADCRUMBS, result);
       }
       
       return result;
    }
    
    /**
     * Add to the breadcrumbs list.
     * @param req HttpServletRequest
     * @param caption String
     * @param action String
     */
    protected void addBreadcrumbs(HttpServletRequest req,
                                  String caption,
                                  String action) {
        Breadcrumbs breadcrumbs = getBreadcrumbs(req);
        breadcrumbs.add(caption, action);
    }
    
    /**
     * Add to the breadcrumbs list.
     * @param req HttpServletRequest
     * @param caption String
     * @param action String
     */
    protected void clearBreadcrumbs(HttpServletRequest req) {
        Breadcrumbs breadcrumbs = getBreadcrumbs(req);
        breadcrumbs.clear();
    }
    
}
