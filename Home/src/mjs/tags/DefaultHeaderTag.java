package mjs.tags;

import javax.servlet.jsp.JspWriter;

public class DefaultHeaderTag extends AbstractTag {

    static final long serialVersionUID = -4174504602386548113L;

    /**
     * Constructor.
     */
    public DefaultHeaderTag() {
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
            out.println("<!--BEGIN DEFAULTERROR TAG-->");
            out.println("<meta name=\"keywords\" content=\"\">"); 
            out.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">"); 
            out.println("<title>Shoemake Management and Chronicles</title>"); 
            out.println("<link href=\"../css/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\">"); 
            out.println("<script type=\"text/javascript\" src=\"../js/jquery-1.4.2.min.js\"></script>"); 
            out.println("<script type=\"text/javascript\" src=\"../js/jquery.gallerax-0.2.js\"></script>"); 
            out.println("<style type=\"text/css\">");
            out.println("@import \"../css/gallery.css\";");
            out.println("</style>");
        } catch (java.lang.Exception e) {
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }
}
