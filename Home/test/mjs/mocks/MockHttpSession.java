package mjs.mocks;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import mjs.mocks.MockServletContext;

/**
 * The mocked up version of the HttpSession interface.  This is 
 * used to simulate a real world environment without an actual 
 * servlet container (for use with JUnit).
 */
public class MockHttpSession implements HttpSession
{
   long createdTime = new Date().getTime();
   Hashtable attributes = new Hashtable();
   ServletContext context = null;
   
   /**
    * Constructor.
    */
   public MockHttpSession(ServletContext obj)
   {
      context = obj;
   }
   
   /**
    * Constructor.
    */
   public MockHttpSession()
   {
      context = new MockServletContext("application", new Hashtable());
   }
   
   public long getCreationTime()
   {
      return createdTime;
   }

   public String getId()
   {
      return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   }

   public long getLastAccessedTime()
   {
      return (new Date().getTime() - 10000);
   }

   public ServletContext getServletContext()
   {
      return context;
   }

   public void setMaxInactiveInterval(int arg0)
   {
   }

   public int getMaxInactiveInterval()
   {
      return 10000;
   }

   public HttpSessionContext getSessionContext()
   {
      return null;
   }

   public Object getAttribute(String arg0)
   {
      return attributes.get(arg0);
   }

   public Object getValue(String arg0)
   {
      return attributes.get(arg0);
   }

   public Enumeration getAttributeNames()
   {
      return attributes.keys();
   }

   public String[] getValueNames()
   {
      return null;
   }

   public void setAttribute(String arg0, Object arg1)
   {
      attributes.put(arg0, arg1);
   }

   public void putValue(String arg0, Object arg1)
   {
      attributes.put(arg0, arg1);
   }

   public void removeAttribute(String arg0)
   {
      attributes.remove(arg0);
   }

   public void removeValue(String arg0)
   {
      attributes.remove(arg0);
   }

   public void invalidate()
   {
      attributes = new Hashtable();
   }

   public boolean isNew()
   {
      return false;
   }

}
