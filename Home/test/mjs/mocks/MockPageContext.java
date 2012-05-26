package mjs.mocks;

import java.util.Hashtable;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import org.apache.log4j.Logger;


/**
 * The mocked up version of the HttpServletRequest interface. This is
 * used to simulate a real world environment without an actual servlet
 * container (for use with JUnit).
 */
public class MockPageContext extends PageContext
{
   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Test". See the public methods
    * debug(), info(), etc.
    */
   private Logger log = Logger.getLogger("Test");

   private Hashtable attributes = new Hashtable();
   private HttpSession session = null;
   private ServletRequest req = null;
   private ServletResponse res = null;
   private ServletConfig config = null;
   private ServletContext context = null;

   public MockPageContext(HttpSession session, ServletRequest req, ServletResponse res, ServletConfig config, ServletContext context)
   {
      this.session = session;
      this.req = req;
      this.res = res;
      this.config = config;
      this.context = context;
   }

   /**
    * Return the object associated with the name in the page scope or
    * null if not found.
    *
    * @param arg0  Description of Parameter
    * @return      The value of the Attribute property.
    */
   public Object getAttribute(String arg0)
   {
      return attributes.get(arg0);
   }

   /**
    * Remove the object reference associated with the given name, look
    * in all scopes in the scope order.
    *
    * @param arg0  Description of Parameter
    */
   public void removeAttribute(String arg0)
   {
      log.debug("NOT IMPLEMENTED");
   }

   /**
    * Included because can't compile without it.
    *
    * @param arg0  The new Attribute value.
    * @param arg1  The new Attribute value.
    */
   public void setAttribute(String arg0, int arg1)
   {
      log.debug("NOT IMPLEMENTED");
   }

   /**
    * Searches for the named attribute in page, request, session (if
    * valid), and application scope(s) in order and returns the value
    * associated or null.
    *
    * @param name  Description of Parameter
    * @return      Description of Return Value
    */
   public java.lang.Object findAttribute(java.lang.String name)
   {
      HttpServletRequest req = (HttpServletRequest)this.req;

      Object result = this.getAttribute(name);

      if (result == null)
         result = req.getAttribute(name);
      if (result == null)
         result = req.getSession().getAttribute(name);
      if (result == null)
         result = req.getSession().getServletContext().getAttribute(name);

      return result;
   }

   /**
    * This method is used to re-direct, or "forward" the current
    * ServletRequest and ServletResponse to another active component
    * in the application.
    *
    * @param relativeUrlPath  Description of Parameter
    */
   public void forward(java.lang.String relativeUrlPath)
   {
      log.debug("NOT IMPLEMENTED");
   }

   /**
    * Return the object associated with the name in the specified
    * scope or null if not found.
    *
    * @param name   Description of Parameter
    * @param scope  Description of Parameter
    * @return       The value of the Attribute property.
    */
   public java.lang.Object getAttribute(java.lang.String name, int scope)
   {
      log.debug("NOT IMPLEMENTED");
      return null;
   }

   /**
    * Enumerate all the attributes in a given scope.
    *
    * @param scope  Description of Parameter
    * @return       The value of the AttributeNamesInScope property.
    */
   public java.util.Enumeration getAttributeNamesInScope(int scope)
   {
      log.debug("NOT IMPLEMENTED");
      return null;
   }

   /**
    * Get the scope where a given attribute is defined.
    *
    * @param name  Description of Parameter
    * @return      The value of the AttributesScope property.
    */
   public int getAttributesScope(java.lang.String name)
   {
      log.debug("NOT IMPLEMENTED");
      return 0;
   }

   /**
    * The current value of the exception object (an Exception).
    *
    * @return   The value of the Exception property.
    */
   public java.lang.Exception getException()
   {
      log.debug("NOT IMPLEMENTED");
      return null;
   }

   /**
    * The current value of the out object (a JspWriter).
    *
    * @return   The value of the Out property.
    */
   public JspWriter getOut()
   {
      return (JspWriter)new MockJspWriter();
   }

   /**
    * The current value of the page object (a Servlet).
    *
    * @return   The value of the Page property.
    */
   public java.lang.Object getPage()
   {
      log.debug("NOT IMPLEMENTED");
      return null;
   }

   /**
    * The current value of the request object (a ServletRequest).
    *
    * @return   The value of the Request property.
    */
   public ServletRequest getRequest()
   {
      return req;
   }

   /**
    * The current value of the response object (a ServletResponse).
    *
    * @return   The value of the Response property.
    */
   public ServletResponse getResponse()
   {
      return res;
   }

   /**
    * The ServletConfig instance.
    *
    * @return   The value of the ServletConfig property.
    */
   public ServletConfig getServletConfig()
   {
      return config;
   }

   /**
    * The ServletContext instance.
    *
    * @return   The value of the ServletContext property.
    */
   public ServletContext getServletContext()
   {
      return context;
   }

   /**
    * The current value of the session object (an HttpSession).
    *
    * @return   The value of the Session property.
    */
   public HttpSession getSession()
   {
      return session;
   }

   /**
    * This method is intended to process an unhandled "page" level
    * exception by redirecting the exception to either the specified
    * error page for this JSP, or if none was specified, to perform
    * some implementation dependent action.
    *
    * @param e  Description of Parameter
    */
   public void handlePageException(java.lang.Exception e)
   {
      log.debug("NOT IMPLEMENTED");
   }

   /**
    * This method is identical to the handlePageException(Exception),
    * except that it accepts a Throwable.
    *
    * @param t  Description of Parameter
    */
   public void handlePageException(java.lang.Throwable t)
   {
      log.debug("NOT IMPLEMENTED");
   }

   /**
    * Causes the resource specified to be processed as part of the
    * current ServletRequest and ServletResponse being processed by
    * the calling Thread.
    *
    * @param relativeUrlPath  Description of Parameter
    */
   public void include(java.lang.String relativeUrlPath)
   {
      log.debug("NOT IMPLEMENTED");
   }

   /**
    * The initialize method is called to initialize an uninitialized
    * PageContext so that it may be used by a JSP Implementation class
    * to service an incoming request and response within it's
    * _jspService() method.
    *
    * @param servlet       Description of Parameter
    * @param request       Description of Parameter
    * @param response      Description of Parameter
    * @param errorPageURL  Description of Parameter
    * @param needsSession  Description of Parameter
    * @param bufferSize    Description of Parameter
    * @param autoFlush     Description of Parameter
    */
   public void initialize(Servlet servlet, ServletRequest request, ServletResponse response, java.lang.String errorPageURL, boolean needsSession, int bufferSize, boolean autoFlush)
   {
      log.debug("NOT IMPLEMENTED");
   }

   /**
    * This method shall "reset" the internal state of a PageContext,
    * releasing all internal references, and preparing the PageContext
    * for potential reuse by a later invocation of initialize().
    */
   public void release()
   {
      log.debug("NOT IMPLEMENTED");
   }

   /**
    * Remove the object reference associated with the specified name
    * in the given scope.
    *
    * @param name   Description of Parameter
    * @param scope  Description of Parameter
    */
   public void removeAttribute(java.lang.String name, int scope)
   {
      if (scope == PAGE_SCOPE)
         attributes.remove(name);
      else if (scope == REQUEST_SCOPE)
         req.removeAttribute(name);
      else if (scope == SESSION_SCOPE)
         session.removeAttribute(name);
      else if (scope == APPLICATION_SCOPE)
         context.removeAttribute(name);
   }

   /**
    * Register the name and object specified with page scope
    * semantics.
    *
    * @param name       The new Attribute value.
    * @param attribute  The new Attribute value.
    */
   public void setAttribute(java.lang.String name, java.lang.Object attribute)
   {
      attributes.put(name, attribute);
   }

   /**
    * Register the name and object specified with appropriate scope
    * semantics.
    *
    * @param name   The new Attribute value.
    * @param o      The new Attribute value.
    * @param scope  The new Attribute value.
    */
   public void setAttribute(java.lang.String name, java.lang.Object o, int scope)
   {
      if (scope == PAGE_SCOPE)
         attributes.put(name, o);
      else if (scope == REQUEST_SCOPE)
         req.setAttribute(name, o);
      else if (scope == SESSION_SCOPE)
         session.setAttribute(name, o);
      else if (scope == APPLICATION_SCOPE)
         context.setAttribute(name, o);
   }
}
