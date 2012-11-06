package mjs.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * The main servlet for Femto Struts applications.
 */
public class Servlet extends org.apache.struts.action.ActionServlet {

    static final long serialVersionUID = -4174504602386548113L;

    /**
     * The log4j logger to use when writing log messages. The Logger category for this component is "Servlet".
     */
    protected Logger logger = Logger.getLogger("Servlet");

    /**
     * The log4j logger to use when writing log messages. The Logger category for this component is "Servlet".
     */
    protected Logger auditLog = Logger.getLogger("Audit");

    /**
     * Constructor.
     */
    public Servlet() {
        super();
        logger.debug("Created servlet.");
    }

    /**
     * Initialize the action mappings for this servlet.
     */
    public void initServlet() throws javax.servlet.ServletException {
        super.initServlet();
        logger.info("Action mappings initialized.");
    }

    /**
     * Process an HTTP "GET" request.
     * 
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     */
    public void doGet(HttpServletRequest req,
                      HttpServletResponse res) throws IOException, ServletException {
        logger.info("");
        logger.info("");
        logger.info("Request received: " + req.getServletPath());
        logger.debug("Processing HTTP \"Get\" request...");
        auditLog.info("");
        auditLog.info("");
        auditLog.info("REQUEST: " + req.getServletPath());
        super.doGet(req, res);
    }

    /**
     * Process an HTTP "POST" request.
     * 
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     */
    public void doPost(HttpServletRequest req,
                       HttpServletResponse res) throws IOException, ServletException {
        logger.info("");
        logger.info("");
        logger.info("REQUEST: " + req.getServletPath());
        logger.debug("Processing HTTP \"Post\" request...");
        auditLog.info("");
        auditLog.info("");
        auditLog.info("REQUEST: " + req.getServletPath());
        try {
            super.doPost(req, res);
            
            //logger.info("HttpServletRequest:");
            //String[] lines = LogUtils.dataToStrings(req);
            //for (int i=0; i <= lines.length-1; i++) {
            // 	logger.info("   " + lines[i]);
            //}
        } catch (Exception e) {
        	logger.error("Error processing POST request.", e); 
         }
         
    }
}
