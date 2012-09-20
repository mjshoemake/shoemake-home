package mjs.tags;

import javax.servlet.jsp.JspWriter;

import mjs.utils.Constants;
import mjs.view.ValidationErrorList;

public class ShowErrorTag extends AbstractTag {

    static final long serialVersionUID = -4174504602386548113L;

    /**
     * The field name for which to display errors.
     */
    private String property = null;

    /**
     * Constructor.
     */
    public ShowErrorTag() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param var_ Description of Parameter
     */
    public ShowErrorTag(String property_) {
        super();
        setProperty(property_);
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
            out.println("<!--BEGIN LOGERROR TAG-->");
            // Look in page, request, and session scope for the exception.
            ValidationErrorList errors = null;
            errors = (ValidationErrorList)pageContext.getAttribute(Constants.ATT_VALIDATION_ERRORS);
            if (errors == null)
                errors = (ValidationErrorList)req.getAttribute(Constants.ATT_VALIDATION_ERRORS);
            if (errors == null)
                errors = (ValidationErrorList)req.getSession().getAttribute(Constants.ATT_VALIDATION_ERRORS);

            if (errors != null && ! errors.isEmpty()) {
                errors.logContents();
                out.println("<!--ValidationErrorList found. -->");
                String error = errors.getError(property);
                if (errors.contains(property)) {
                    out.println("<table><tr><td style=\"color: red\">");
                    out.println(error);    
                    out.println("</td></tr></table>");
                }    
            } else if (errors == null) {
                // Do nothing.
                out.println("<!--ValidationErrorList not found.-->");
            } else {
                errors.logContents();
                out.println("<!--ValidationErrorList is empty.-->");
            }
            out.println("<!--END LOGERROR TAG-->");
            
        } catch (java.lang.Exception e) {
            try {
                out.println("<!--EXCEPTION THROWN: " + e.getClass().getName() + " - " + e.getMessage() + " -->");
            } catch (Exception ex) {
                // Ignore.
            }
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    /**
     * The variable name that contains the exception.
     * 
     * @return The value of the Var property.
     */
    public String getProperty() {
        return property;
    }
    
    /**
     * The variable name that contains the exception.
     * 
     * @param value The new Var value.
     */
    public void setProperty(String value) {
        property = value;
    }

}
