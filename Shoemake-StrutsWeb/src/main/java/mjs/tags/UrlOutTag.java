/*
 * $Id: TopNavTag.java 22365 2010-07-20 16:34:01Z mshoemake $
 *
 ***************************************************************************
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, Cisco Systems, Inc.
 ***************************************************************************
 */
package mjs.tags;

import javax.servlet.jsp.JspWriter;

import mjs.tags.TagExpressionHandler;

import org.apache.log4j.Logger;

/**
 * Tag used to write out a URL that comes from 
 * a request attribute, session attribute, etc.  
 * This tag uses the expression handler to 
 * find the URL.  It then encodes the URL 
 * leveraging JSTL's url tag, and writes the URL
 * to the JSP output.
 */
public class UrlOutTag extends AbstractNavTag {
    
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
    public UrlOutTag() {
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
            log = Logger.getLogger("UI_JSP");
            Object obj = null;

            if (expr != null) {
                // Get the object whose properties should be traced.
                obj = TagExpressionHandler.evaluateExpression(pageContext, expr);
            }

            if (obj != null) {
                String url = obj.toString();
                String context = req.getContextPath();
                String encoded = encodeUrl(url, context);
                log.info("URL: " + encoded);
                out.print(encoded);
            }
        } catch (java.lang.Exception e) {
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

}
