/*
 * $Id: AbstractTag.java 22365 2010-07-20 16:34:01Z mshoemake $
 *
 ***************************************************************************
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, Cisco Systems, Inc.
 ***************************************************************************
 */
package mjs.tags;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import mjs.exceptions.CoreException;
import org.apache.log4j.Logger;

/**
 * We extend Java's TagSupport class with the AbstractTag class to provide 
 * default implementations for the doStartTag() and doEndTag() methods. 
 * The abstract method output() is called by doStartTag() to generate HTML. 
 * Each derived Tag class must provide its own attributes and output method. 
 * This class should only be extended by tags that do not process the body of 
 * a tag (singleton tags). Derived classes should implement output() instead 
 * of doStartTag().
 */
public abstract class AbstractTag extends TagSupport {

    static final long serialVersionUID = -4174504602386548113L;
    
    /**
     * Used to detect browser type.
     */
    protected HttpServletRequest req = null;

    /**
     * Used to encode urls.
     */
    protected HttpServletResponse res = null;

    /**
     * Used to write the tag output.
     */
    protected JspWriter writer = null;

    /**
     * The log4j logger to use when writing log messages. This is populated by extracting the logger using the Logger category.
     * The default Logger category is "Tag".
     */
    protected Logger log = Logger.getLogger("Tags");

    /**
     * The constructor used when tag is instantiated by the JSP compiler. All tag fields are initialized dynamically from the tag
     * parameters in the JSP. This constructor defaults the log category to "Tags".
     */
    public AbstractTag() {
    }

    /**
     * Continues evaluation of the page.
     * 
     * @return int (EVAL_PAGE)
     * @exception JspException Description of Exception
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    /**
     * Starts the evaluation of the tag. Only singleton tags should be derived from this class. Output is generated by
     * implementing the output() method.
     * 
     * @return int (SKIP_BODY)
     * @exception JspException Description of Exception
     */
    public int doStartTag() throws JspException {
        // This abstract routine will be implemented by each custom tag. We separate
        // it from doStartTag() so that tags can generate output independent of the jsp
        // engine (i.e. as subcomponents of other tags).
        int result = SKIP_BODY;

        try {
            log.debug("");
            log.debug("TAG: " + this.getClass().getName());
            // Initialize the controller, imgpath, output writer, request and response
            // from the pageContext.
            init(pageContext);
            result = output(this);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new JspException(ex.toString());
        }

        return result;
    }

    /**
     * Makes sure a url is not javascript and then encodes the url using the response object. Javascript is passed back unchanged.
     * 
     * @param url an unencoded url
     * @return an encoded url
     */
    public String encode(String url) {
        if (res == null)
            return "encoding failed! no response object!";

        if (url.indexOf("javascript:") != -1)
            return url;

        return res.encodeURL(url);
    }

    /**
     * Used to detect browser type.
     * 
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return req;
    }

    /**
     * Used to encode urls.
     * 
     * @return HttpServletResponse
     */
    public HttpServletResponse getResponse() {
        return res;
    }

    /**
     * Used to write the tag output.
     * 
     * @return javax.servlet.jsp.JspWriter
     */
    public javax.servlet.jsp.JspWriter getWriter() {
        return writer;
    }

    /**
     * If the imgpath and controller are not assigned by tag parameters, they are set from the page context (project config file).
     * The response, request and output writer are also assigned from the page context for convenient reference.
     * 
     * @param page Description of Parameter
     */
    public void init(PageContext page) {
        if (page == null) {
            log.debug("page is NULL.  Returning.");
            return;
        }

        // We use the response object to encode urls whenever a url is included
        // as part of the tag output. Url encoding allows us to work without cookies.
        res = (HttpServletResponse) page.getResponse();
        req = (HttpServletRequest) page.getRequest();
        writer = page.getOut();
    }

    /**
     * Provided since primitive types such as boolean are not understood in JSP tag parameters. Converts the string "true" or
     * "false" to its boolean value. Assumes anything except "true" is false.
     * 
     * @param val should be true or false
     * @return boolean
     */
    public static boolean is(String val) {
        if (val.toUpperCase().equals("TRUE"))
            return true;
        return false;
    }

    /**
     * Determines if the browser is Explorer or Netscape.
     * 
     * @return boolean
     */
    public boolean isNetscape() {
        if (req == null)
            return false;
        String agent = req.getHeader("User-Agent");

        if (agent.toUpperCase().indexOf("MSIE") >= 0)
            return false;
        return true;
    }

    /**
     * Determines if the browser is Explorer or Netscape.
     * 
     * @param request_ Description of Parameter
     * @return boolean
     */
    public static boolean isNetscape(HttpServletRequest request_) {
        if (request_ == null)
            return false;
        String agent = request_.getHeader("User-Agent");

        if (agent.toUpperCase().indexOf("MSIE") >= 0)
            return false;
        return true;
    }

    /**
     * Converts null Strings to an empty String. If the String is not null, trims any leading or trailing spaces.
     * 
     * @param tmp the candidate String
     * @return String
     */
    public String nonull(String tmp) {
        if (tmp == null)
            return "";
        return tmp.trim();
    }

    /**
     * Implemented instead of doStartTag to generate the tag's output.
     * 
     * @param tag Description of Parameter
     * @return Description of Return Value
     * @exception IOException Description of Exception
     * @exception CoreException Description of Exception
     */
    public abstract int output(AbstractTag tag) throws CoreException, IOException;

    /**
     * Used to detect browser type.
     * 
     * @param newRequest the request
     */
    public void setRequest(HttpServletRequest newRequest) {
        req = newRequest;
    }

    /**
     * Used to encode urls.
     * 
     * @param newResponse the response
     */
    public void setResponse(HttpServletResponse newResponse) {
        res = newResponse;
    }

    /**
     * Used to write the tag output.
     * 
     * @param newWriter javax.servlet.jsp.JspWriter
     */
    public void setWriter(javax.servlet.jsp.JspWriter newWriter) {
        writer = newWriter;
    }

}

