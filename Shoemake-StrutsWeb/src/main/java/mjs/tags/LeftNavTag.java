package mjs.tags;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.jsp.JspWriter;

import mjs.tags.NavigationConfigItem;
import mjs.tags.TagExpressionHandler;

import org.apache.log4j.Logger;

/**
 * Tag used to display the top navigation bar in the 
 * Femto UI project.  This tag finds the navigation bar
 * details in the specified "var" attribute, which must
 * be of type ArrayList<NavigationConfigItem>.
 */
@SuppressWarnings("rawtypes")
public class LeftNavTag extends AbstractNavTag {
    
    static final long serialVersionUID = -4174504602386548113L;

    /**
     * The expression that resolves to the top nav details.
     * This should resolve to an object of type 
     * ArrayList<NavigationConfigItem>.
     */
    private String expr = null;

    /**
     * Constructor.
     */
    public LeftNavTag() {
        super();
    }

    /**
     * The expression that resolves to the top nav details.
     * This should resolve to an object of type 
     * ArrayList<NavigationConfigItem>.
     */
    public String getExpr() {
        return expr;
    }

    /**
     * The expression that resolves to the top nav details.
     * This should resolve to an object of type 
     * ArrayList<NavigationConfigItem>.
     */
    public void setExpr(String value) {
        expr = value;
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

        try {
            log = Logger.getLogger("JSP");
            Object obj = null;

            log.info("Request attribute names: (JSP)");
            Enumeration names = this.req.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement().toString();    
                log.info("   " + name + "=" + req.getAttribute(name));
            }    
            String context = req.getContextPath();
            log.info("   context=" + context);
            
            if (expr != null) {
                // Get the object whose properties should be traced.
                obj = TagExpressionHandler.evaluateExpression(pageContext, expr);
            }

            if (obj != null && obj instanceof ArrayList) {
                ArrayList navList = (ArrayList)obj;
                
                log.info("Left Nav Items: count=" + navList.size());

                String firstNavID = null;
                for (int i=0; i <= navList.size()-1; i++) {
                    if (navList.get(i) instanceof NavigationConfigItem) {
                        NavigationConfigItem nextItem = (NavigationConfigItem)navList.get(i);
                        
                        // For right now we are assuming that the first item will be the selected
                        StringBuffer classes = new StringBuffer();
                        classes.append("dijitAccordionTitle");
                        if (firstNavID == null) {
                            firstNavID = nextItem.getID();
                            classes.append(" dijitAccordionTitle-selected");
                        }
                        out.println("<div id='" + nextItem.getID() + "' TABINDEX='"+(i+1)+"' ");
                        out.println("     class='" + classes.toString() + "'");
                        out.println("     onMouseOver='leftNavOver(this);' onFocus='leftNavOver(this);'");
                        out.println("     onMouseOut='leftNavOut(this);' onblur='leftNavOut(this);'");
                        out.println("     onKeyDown='if(event.keyCode==13){this.onclick();}'");
                        
                        StringBuffer onClick = new StringBuffer();
                        String url = encodeUrl(nextItem.getUrl(), context);
                        onClick.append("openPage({contentHref: '");
                        onClick.append(url);
                        onClick.append("', primaryNavId: '");
                        onClick.append(nextItem.getTopNavID());
                        onClick.append("', secondaryNavId: '");
                        onClick.append(nextItem.getSubNavID());
                        onClick.append("'});");
                        out.println("onClick=\"" + onClick.toString() + "\">");
                        
                        out.println("    <table class='panelTable'><tbody><tr>");
                        out.println("        <td><span class='dijitAccordionText'");
                        out.println("                  id='phasedsai2_button_title'>");
                        out.println("                " + nextItem.getCaption());
                        out.println("        </span></td>");
                        out.println("    </tr></tbody></table>");
                        out.println("</div>");
                        
                        log.info("   " + nextItem.getID() +
                                 ": " + nextItem.getCaption() + 
                                 " url=" + url +
                                 " topNav=" + nextItem.getTopNavID() + 
                                 " subNav=" + nextItem.getSubNavID());
                    
                    }
                }
            } else if (obj == null) {
                log.info("Left Navigation list is null.");
            } else {
                log.info("Left Navigation is not an ArrayList (" + obj.getClass().getName() + ").");
            }
        } catch (java.lang.Exception e) {
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

}
