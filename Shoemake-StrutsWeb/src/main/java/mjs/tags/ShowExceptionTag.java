package mjs.tags;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import mjs.utils.LogUtils;

public class ShowExceptionTag extends AbstractTag {

    static final long serialVersionUID = -4174504602386548113L;

    /**
     * The variable name that contains the exception.
     */
    private String var = null;

    /**
     * Constructor.
     */
    public ShowExceptionTag() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param var_ Description of Parameter
     */
    public ShowExceptionTag(String var_) {
        super();
        setVar(var_);
    }

    /**
     * Execute.
     * 
     * @param tag
     * @return Description of Return Value
     */
    @SuppressWarnings("rawtypes")
    public int output(AbstractTag tag) {
        JspWriter out = tag.getWriter();

        try {
            out.println("<!--BEGIN LOGERROR TAG-->");

            // Look in page, request, and session scope for the exception.
            Exception e = (Exception) pageContext.getAttribute(var);
            if (e == null)
                e = (Exception) req.getAttribute(var);
            if (e == null)
                e = (Exception) req.getSession().getAttribute(var);

            if (e != null) {
                // Parse error.
                String next = LogUtils.getStackTraceAsString(e);
                next = next.replaceAll("\\tat ", "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;at ");
                next = next.replaceAll("Caused by:", "<br/>Caused by:");
                
                out.println("<table>");
                out.println("<tr>");
                out.println("<td colspan='2'>");
                out.println("<h3>" + e.getMessage() + "</h3>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>&nbsp;&nbsp;&nbsp;</td>");
                out.println("<td>");
                out.println(next);
                out.println("</td>");
                out.println("</tr>");
                
                if (e.getCause() != null) {
                    next = LogUtils.getStackTraceAsString(e.getCause());
                    next = next.replaceAll("\\tat ", "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;at ");
                    next = next.replaceAll("Caused by:", "<br/>Caused by:");

                    out.println("<tr>");
                    out.println("<td colspan='2'>");
                    out.println("<h3>ROOT CAUSE: " + e.getCause().getMessage() + "</h3>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td>&nbsp;&nbsp;&nbsp;</td>");
                    out.println("<td>");
                    out.println(next);
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");

                out.println("<p/>");
                out.println("<h3>Request:</h3>&nbsp;&nbsp;&nbsp;" + req.getServletPath());
                out.println("<p/>");
                out.println("<h3>Request parameters:</h3>");
                Enumeration enumeration = req.getParameterNames();
                int x = 0;
                while (enumeration.hasMoreElements()) {
                    x++;
                    String name = enumeration.nextElement().toString();
                    out.println("&nbsp;&nbsp;&nbsp;&nbsp;[" + x + "]: " + name + " -> "
                        + getParameterValuesAsString(name, req) + "<br/>");
                }
                out.println("<p/>");
                out.println("<h3>Request attributes:</h3>");
                enumeration = req.getAttributeNames();
                x = 0;
                while (enumeration.hasMoreElements()) {
                    x++;
                    String name = enumeration.nextElement().toString();
                    out.println("&nbsp;&nbsp;&nbsp;&nbsp;[" + x + "]: " + name + " -> "
                        + req.getAttribute(name).toString() + "<br/>");
                }
                out.println("<p/>");
            } else {
                out.println("EXCEPTION IS NULL.");
            }
            
        } catch (java.lang.Exception e) {
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
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
     * The variable name that contains the exception.
     * 
     * @return The value of the Var property.
     */
    public String getVar() {
        return var;
    }
    
    /**
     * The variable name that contains the exception.
     * 
     * @param value The new Var value.
     */
    public void setVar(String value) {
        var = value;
    }

}
