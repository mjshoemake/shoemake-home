package mjs.mocks;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;

/**
 * The mocked up version of the ServletConttext interface.  This 
 * is used to simulate a real world environment without an actual 
 * servlet container (for use with JUnit).
 */
public class MockServletContext implements ServletContext
{
   /**
    * The log4j logger to use when writing log
    * messages.  This is populated by extracting
    * the logger using the Logger category.  The
    * default Logger category is "Test".  See
    * the public methods debug(), info(), etc.
    */
   private Logger log = Logger.getLogger("Test");
   
   Hashtable initParams = new Hashtable();
   Hashtable attributes = new Hashtable();
   //Servlet servlet = null;
   String contextname = null;
   
   /**
    * Constructor.
    */
//   public MockServletContext(Servlet servlet, String contextname, Hashtable initParams)
   public MockServletContext(String contextname, Hashtable initParams)
   {
      //this.servlet = servlet;
      this.contextname = contextname;
      this.initParams = initParams;
   }
      
   public ServletContext getContext(String arg0)
   {
      return null;
   }

   public int getMajorVersion()
   {
      return 2;
   }

   public int getMinorVersion()
   {
      return 3;
   }

   public String getMimeType(String arg0)
   {
      return "text/html";
   }

   public String getContextPath()
   {
      return "";
   }

   public Set getResourcePaths(String arg0)
   {
      log.debug("BEGIN");
      return null;
   }

   public URL getResource(String arg0) throws MalformedURLException
   {
      log.debug("BEGIN");
     return null;
   }

   public InputStream getResourceAsStream(String arg0)
   {
      log.debug("BEGIN");
      return null;
   }

   public RequestDispatcher getRequestDispatcher(String arg0)
   {
      log.debug("BEGIN");
      return null;
   }

   public RequestDispatcher getNamedDispatcher(String arg0)
   {
      log.debug("BEGIN");
      return null;
   }

   public Servlet getServlet(String arg0) throws ServletException
   {
      log.debug("BEGIN");
      return null;
   }

   public Enumeration getServlets()
   {
      log.debug("BEGIN");
      return null;
   }

   public Enumeration getServletNames()
   {
      log.debug("BEGIN");
    return null;
   }

   public void log(String arg0)
   {
   }

   public void log(Exception arg0, String arg1)
   {
   }

   public void log(String arg0, Throwable arg1)
   {
   }

   public String getRealPath(String arg0)
   {
      return null;
   }

   public String getServerInfo()
   {
      log.debug("BEGIN");
      return null;
   }

   public String getInitParameter(String arg0)
   {
	  Object value = initParams.get(arg0);
	  if (value == null)
		  throw new NullPointerException("MockServletContext: InitParam " + arg0 + " not found.");
      return value.toString();
   }

   public Enumeration getInitParameterNames()
   {
      log.debug("Init Parameter count: "+initParams.size());
      return initParams.keys();
   }

   public Object getAttribute(String arg0)
   {
      return attributes.get(arg0);
   }

   public Enumeration getAttributeNames()
   {
      return attributes.keys();
   }

   public void setAttribute(String arg0, Object arg1)
   {
      attributes.put(arg0, arg1);
   }

   public void removeAttribute(String arg0)
   {
      attributes.remove(arg0);
   }

   public String getServletContextName()
   {
      return contextname;
   }

}
