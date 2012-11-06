package mjs.mocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Hashtable;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * The mocked up version of the HttpServletRequest interface.  
 * This is used to simulate a real world environment without 
 * an actual servlet container (for use with JUnit).
 */
public class MockHttpServletRequest implements HttpServletRequest
{
   /**
    * The log4j logger to use when writing log
    * messages.  This is populated by extracting
    * the logger using the Logger category.  The
    * default Logger category is "Test".  See
    * the public methods debug(), info(), etc.
    */
   private Logger log = Logger.getLogger("Test");

   private Hashtable attributes = new Hashtable();
   private Hashtable parameters = new Hashtable();
   private HttpSession session = null;
   private String forwardTo = null;

   /**
    * Constructor.
    */
   public MockHttpServletRequest(HttpSession session)
   {
      this.session = session;
   }

   /**
    * Constructor.
    */
   public MockHttpServletRequest()
   {
      this.session = new MockHttpSession();
   }

   /**
    * Added so the test class can see what the forward target
    * was. 
    */
   public String getForwardTarget()
   {
      return forwardTo;
   }
   
   public String getAuthType()
   {
      return "/authType";
   }

   public Cookie[] getCookies()
   {
      return null;
   }

   public long getDateHeader(String arg0)
   {
      return 0;
   }

   public String getHeader(String arg0)
   {
      return null;
   }

   public String getLocalAddr() {
       return null;
   }
   
   public String getLocalName() {
       return null;
   }
   
   public int getRemotePort() {
       return 0;
   }
   
   public int getLocalPort() {
       return 0;
   }
   
   public Enumeration getHeaders(String arg0)
   {
      return null;
   }

   public Enumeration getHeaderNames()
   {
      return null;
   }

   public int getIntHeader(String arg0)
   {
      return 0;
   }

   public String getMethod()
   {
      return "/method";
   }

   public String getPathInfo()
   {
      return "/pathInfo";
   }

   public String getPathTranslated()
   {
      return "/pathTranslated";
   }

   public String getContextPath()
   {
      return "/contextPath";
   }

   public String getQueryString()
   {
      return "/queryString";
   }

   public String getRemoteUser()
   {
      return "/remoteUser";
   }

   public boolean isUserInRole(String arg0)
   {
      return false;
   }

   public Principal getUserPrincipal()
   {
      return null;
   }

   public String getRequestedSessionId()
   {
      return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   }

   public String getRequestURI()
   {
      return "/requestURI";
   }

   public StringBuffer getRequestURL()
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("/website");
      return buffer;
   }

   public String getServletPath()
   {
      return "/servletPath";
   }

   public HttpSession getSession(boolean arg0)
   {
      if (session != null)
      {
         // Return the current session.
         return session;
      }
      else if (arg0)
      {
         session = new MockHttpSession(null);
         return session;
      }
      else
      {
         // Null session.
         return null;
      }   
   }

   public HttpSession getSession()
   {
      return session;
   }

   public boolean isRequestedSessionIdValid()
   {
      return true;
   }

   public boolean isRequestedSessionIdFromCookie()
   {
      return false;
   }

   public boolean isRequestedSessionIdFromURL()
   {
      return false;
   }

   public boolean isRequestedSessionIdFromUrl()
   {
      return false;
   }

   public Object getAttribute(String arg0)
   {
      return attributes.get(arg0);
   }

   public Enumeration getAttributeNames()
   {
      return attributes.keys();
   }

   public String getCharacterEncoding()
   {
      return null;
   }

   public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException
   {
   }

   public int getContentLength()
   {
      return 0;
   }

   public String getContentType()
   {
      return null;
   }

   public ServletInputStream getInputStream() throws IOException
   {
      return null;
   }

   public String getParameter(String arg0)
   {
      Object value = parameters.get(arg0);
      
      if (value != null)
      {
         return value.toString();
      }
      else
      {
         return null;
      }
   }

   public Enumeration getParameterNames()
   {
      return parameters.keys();
   }

   public String[] getParameterValues(String arg0)
   {
	  String value = (String)parameters.get(arg0);
	  if (value != null) {
		  String[] values = new String[1];
		  values[0] = value;
		  return values;
	  } else {
		  return new String[0];
	  }
   }

   public Map getParameterMap()
   {
      return null;
   }

   public String getProtocol()
   {
      return null;
   }

   public String getScheme()
   {
      return null;
   }

   public String getServerName()
   {
      return "thisserver";
   }

   public int getServerPort()
   {
      return 8866;
   }

   public BufferedReader getReader() throws IOException
   {
      return null;
   }

   public String getRemoteAddr()
   {
      return null;
   }

   public String getRemoteHost()
   {
      return null;
   }

   public void setAttribute(String arg0, Object arg1)
   {
      attributes.put(arg0, arg1);
   }

   public void setParameter(String arg0, Object arg1)
   {
      parameters.put(arg0, arg1);
   }

   public void removeAttribute(String arg0)
   {
      attributes.remove(arg0);
   }

   public Locale getLocale()
   {
      return Locale.US;
   }

   public Enumeration getLocales()
   {
      return null;
   }

   public boolean isSecure()
   {
      return false;
   }

   public RequestDispatcher getRequestDispatcher(String arg0)
   {
      forwardTo = arg0;
      return new MockRequestDispatcher();
   }

   public String getRealPath(String arg0)
   {
      return null;
   }
   
}
