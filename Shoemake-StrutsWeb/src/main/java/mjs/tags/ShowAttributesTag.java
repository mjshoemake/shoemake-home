package mjs.tags;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;

import mjs.utils.Constants;

public class ShowAttributesTag extends AbstractTag {

    static final long serialVersionUID = -4174504602386548113L;

    /**
     * The log4j logger to use when writing log messages.
     */
    protected Logger jspLog = Logger.getLogger("JSP");
    
    /**
     * Constructor.
     */
    public ShowAttributesTag() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param var_ Description of Parameter
     */
    public ShowAttributesTag(String var_) {
        super();
    }

    /**
     * Execute.
     * 
     * @param tag
     * @return Description of Return Value
     */
    public int output(AbstractTag tag) {
        JspWriter out = tag.getWriter();

        try {
            traceAttributes(req, out);
            
        } catch (java.lang.Exception e) {
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    /**
     * Trace out the parameters and attributes received in the request.
     */
    @SuppressWarnings("rawtypes")
    public void traceAttributes(HttpServletRequest req,
                                JspWriter out) throws Exception {
        Enumeration enumeration = null;
        int x = 0;

        out.println("<br/><h3>JSP TAG traceAttributes</h3>");
        String userID = (String) req.getSession().getAttribute(Constants.ATT_USER_ID);
        if (userID != null) {
            out.println("<br/>&nbsp;&nbsp;&nbsp;User: " + userID);
        }
        // Log request parameters.
        enumeration = req.getParameterNames();
        x = 0;
        out.println("<br/>&nbsp;&nbsp;&nbsp;Request Parameters:");
        while (enumeration.hasMoreElements()) {
            x++;
            String name = enumeration.nextElement().toString();
            out.println("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;parameter[" + x + "]: " + name + " -> "
                + getParameterValuesAsString(name, req));
        }

        out.println("<br/>&nbsp;&nbsp;&nbsp;Request Attributes:");
        enumeration = req.getAttributeNames();
        x = 0;
        while (enumeration.hasMoreElements()) {
            x++;
            String name = enumeration.nextElement().toString();
            out.println("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;attribute[" + x + "]: " + name + " -> "
                + req.getAttribute(name).toString());
        }

        out.println("<br/>&nbsp;&nbsp;&nbsp;Session Attributes:");
        HttpSession session = req.getSession();
        enumeration = session.getAttributeNames();
        x = 0;
        while (enumeration.hasMoreElements()) {
            x++;
            String name = enumeration.nextElement().toString();
            out.println("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;attribute[" + x + "]: " + name + " -> "
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
    
}
