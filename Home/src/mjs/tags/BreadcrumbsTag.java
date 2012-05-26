/*
 * $Id: TopNavTag.java 22365 2010-07-20 16:34:01Z mshoemake $
 *
 ***************************************************************************
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, Cisco Systems, Inc.
 ***************************************************************************
 */
package mjs.tags;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.jsp.JspWriter;

import mjs.view.ActionLink;
import mjs.view.Breadcrumbs;
import mjs.tags.TagExpressionHandler;
import mjs.utils.LogUtils;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.rt.core.UrlTag;

/**
 * Tag used to display the breadcrumbs in the 
 * Femto UI project.  This tag finds the breadcrumbs
 * object created by the Struts Action class and
 * displays the links using the Cues Dojo breadcrumbs
 * components.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class BreadcrumbsTag extends AbstractNavTag {
    
    static final long serialVersionUID = -4174504602386548113L;

    /**
     * Constructor.
     */
    public BreadcrumbsTag() {
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

        try {
            log = Logger.getLogger("JSP");
            Object obj = null;
            String expr = "${sessionScope.breadcrumbs}";

            log.info("Request attribute names: (JSP)");
            Enumeration names = this.req.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement().toString();    
                log.info("   " + name + "=" + req.getAttribute(name));
            }    
            log.info("Session attribute names: (JSP)");
            names = this.req.getSession().getAttributeNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement().toString();    
                log.info("   " + name + "=" + req.getSession().getAttribute(name));
            }    
            String context = req.getContextPath();
            log.info("   context=" + context);
            log.info("Param expr=" + expr);
            if (expr != null) {
                // Get the object whose properties should be traced.
                obj = TagExpressionHandler.evaluateExpression(pageContext, expr);
            }

            if (obj != null && obj instanceof Breadcrumbs) {
                 Breadcrumbs breadcrumbs = (Breadcrumbs)obj;
                
                log.info("Breadcrumbs: count=" + breadcrumbs.getLinks().size());
                
                String[] lines = LogUtils.dataToStrings(breadcrumbs);
                for (int i=0; i <= lines.length-1; i++)
                    log.info("   " + lines[i]);
                
                out.println("<p><div id=\"breadcrumb\" class=\"breadcrumb\"/>"); 
                ArrayList<ActionLink> links = breadcrumbs.getLinks();
                StringBuilder builder = new StringBuilder();
                for (int i=0; i <= links.size()-1; i++) {
                   boolean isLink = true;
                    if (i == links.size()-1) {
                        isLink = false;
                    } 
                    if (i > 0) {
                        builder.append("&nbsp;&nbsp;&gt;&nbsp;&nbsp;");
                    }
                    ActionLink next = links.get(i);
                    if (isLink) {
                       // Link
                       builder.append("<a href=\"" + next.getAction() + "\">" + next.getCaption() + "</a>");
                    } else {
                       // Label
                       builder.append("<a href=\"" + next.getAction() + "\">" + next.getCaption() + "</a>");
                    }
                }
                out.println(builder.toString());
                out.println("</div>");
                
            } else if (obj == null) {
                log.info("Breadcrumbs is null.");
            } else {
                log.info("Specified Breadcrumbs object is not of type Breadcrumbs (" + obj.getClass().getName() + ").");
            }
        } catch (java.lang.Exception e) {
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    /**
     * Encode the URL using JSTL UrlTag.
     * @param url String
     * @return String
     */
    protected String encodeUrl(String url, String context) {
        String result = null;
        try {
            if (url != null && ! url.equals(""))
                result = UrlTag.resolveUrl(url, context, this.pageContext);
            else
                result = url;           
        } catch (Exception e) {
            log.error("Error encoding URL " + url + " for ActionLink.  Returning initial URL.", e);
            result = url;
        }
        
        return result;
    }

}
