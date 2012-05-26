package mjs.mocks;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * The mocked up version of the HttpServletResponse interface.  
 * This is used to simulate a real world environment without 
 * an actual servlet container (for use with JUnit).
 */
public class MockHttpServletResponse implements HttpServletResponse
{
   Vector cookies = new Vector();

   public static final int RETURN_HTML = 0;
   public static final int RETURN_REDIRECT = 1;
   public static final int RETURN_ERROR = 2;
   
   int returnType = RETURN_HTML;
   String redirectTo = null;
   
   /**
    * Constructor.
    */
   public MockHttpServletResponse()
   {
   }
   

   /**
    * The return type for this action (HTML, REDIRECT, or ERROR).
    */
   public int getReturnType()
   {
      return returnType;
   }

   /**
    * The return type for this action (HTML, REDIRECT, or ERROR).
    */
   public String getContentType()
   {
      return "";
   }

   /**
    * The redirect target specified by the action.
    */
   public String getRedirectTarget()
   {
      return redirectTo;
   }

   public void addCookie(Cookie arg0)
   {
      cookies.add(arg0);
   }

   public boolean containsHeader(String arg0)
   {
      return false;
   }

   public String encodeURL(String arg0)
   {
      return arg0;
   }

   public String encodeRedirectURL(String arg0)
   {
      return "redirect:"+arg0;
   }

   public String encodeUrl(String arg0)
   {
      return arg0;
   }

   public String encodeRedirectUrl(String arg0)
   {
      return "redirect:"+arg0;
   }

   public void sendError(int arg0, String arg1) throws IOException
   {
      returnType = RETURN_ERROR;
   }

   public void sendError(int arg0) throws IOException
   {
      returnType = RETURN_ERROR;
   }

   public void sendRedirect(String arg0) throws IOException
   {
      returnType = RETURN_REDIRECT;
      redirectTo = arg0;
   }

   public void setDateHeader(String arg0, long arg1)
   {
   }

   public void addDateHeader(String arg0, long arg1)
   {
   }

   public void setHeader(String arg0, String arg1)
   {
   }

   public void addHeader(String arg0, String arg1)
   {
   }

   public void setIntHeader(String arg0, int arg1)
   {
   }

   public void addIntHeader(String arg0, int arg1)
   {
   }

   public void setStatus(int arg0)
   {
   }

   public void setStatus(int arg0, String arg1)
   {
   }

   public String getCharacterEncoding()
   {
      return null;
   }

   public ServletOutputStream getOutputStream() throws IOException
   {
      return null;
   }

   public PrintWriter getWriter() throws IOException
   {
      return new PrintWriter(new ByteArrayOutputStream());
   }

   public void setContentLength(int arg0)
   {
   }

   public void setCharacterEncoding(String arg0)
   {
   }

   public void setContentType(String arg0)
   {
   }

   public void setBufferSize(int arg0)
   {
   }

   public int getBufferSize()
   {
      return 10000;
   }

   public void flushBuffer() throws IOException
   {
   }

   public void resetBuffer()
   {
   }

   public boolean isCommitted()
   {
      return false;
   }

   public void reset()
   {
   }

   public void setLocale(Locale arg0)
   {
   }

   public Locale getLocale()
   {
      return null;
   }

}
