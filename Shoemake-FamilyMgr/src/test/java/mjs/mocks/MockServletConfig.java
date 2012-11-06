package mjs.mocks;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;

/**
 * A mock version of ServletConfig interface.  This is used to
 * simulate a servlet container for unit test purposes.
 */
public class MockServletConfig implements ServletConfig
{
   /**
    * The log4j logger to use when writing log
    * messages.  This is populated by extracting
    * the logger using the Logger category.  The
    * default Logger category is "Test".  See
    * the public methods debug(), info(), etc.
    */
   private Logger log = Logger.getLogger("Test");
   
   Servlet servlet = null;
   ServletContext context = null; 
   
   /**
    * Constructor.
    */
   //public MockServletConfig(Servlet servlet, Hashtable initParams)
   public MockServletConfig(Hashtable initParams)
   {
      //this.servlet = servlet;
      //context = new MockServletContext(servlet, "application", initParams);
      context = new MockServletContext("application", initParams);
   }
   
   /**
    * Constructor.
    */
   public MockServletConfig() {
      context = new MockServletContext("application", new Hashtable());
   }
   
   public String getServletName()
   {
      return "epsap";
   }

   public ServletContext getServletContext()
   {
      return context;
   }

   public String getInitParameter(String arg0)
   {
      return context.getInitParameter(arg0);
   }

   public Enumeration getInitParameterNames()
   {
      return new Hashtable().keys();
   }

}
