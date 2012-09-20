package mjs.tags;

import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;

/**
 * Tag used to display the top navigation bar in the 
 * Femto UI project.  This tag finds the navigation bar
 * details in the specified "var" attribute, which must
 * be of type ArrayList<NavigationConfigItem>.
 */
public class TopNavTag extends AbstractNavTag {
    
    static final long serialVersionUID = -4174504602386548113L;

    /**
     * Constructor.
     */
    public TopNavTag() {
        super();
    }

    /**
     * Execute.
     * 
     * @param tag
     * @return Description of Return Value
     */
    public int output(AbstractTag tag) {
        Logger log = null;
        JspWriter out = tag.getWriter();
        //String context = req.getContextPath();

        try {
            log = Logger.getLogger("JSP");
            out.println("         <div id=\"header\">");
            out.println("         <a href=\"/home/Logout.do\">Logout</a>");
            out.println("    		<div id=\"logo\">");
            out.println("             <h1><a href=\"main.html\">Shoemake Management and Chronicles</a></h1>");
            out.println("    		</div>");
            out.println("         </div>");
            out.println("         <!-- end #header -->");
            out.println("         <div id=\"menu\" width=\"100%\">");
            out.println("    		<ul width=\"100%\"><li class=\"current_page_item\"><a href=\"/home/GetFamilyMemberList.do\">Family</a></li>");
            out.println("             <li><a href=\"#\">House</a></li>");
            out.println("             <li><a href=\"/home/GetRecipesByLetter.do\">Recipes</a></li>");
            out.println("             <li><a href=\"#\">Finances</a></li>");
            out.println("             <li><a href=\"#\">Cars</a></li>");
            out.println("             <li><a href=\"#\">Spiritual</a></li>");
            out.println("             <li><a href=\"#\">Fitness</a></li>");
            out.println("             <li><a href=\"#\">Entertainment</a></li>");
            out.println("             <li><a href=\"#\">Vacation</a></li>");
            out.println("             <li><a href=\"#\">Pictures</a></li>");
            out.println("             <li><a href=\"#\">Technology</a></li>");
            out.println("             <li><a href=\"#\">Extras</a></li>");
            out.println("             <li><a href=\"/home/ShowAdminPage.do\">Admin</a></li>");
            out.println("           </ul>");
            out.println("         </div>");
            out.println("         <!-- end #menu -->");
            
        } catch (java.lang.Exception e) {
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }
}
