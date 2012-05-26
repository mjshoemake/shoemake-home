//
// file: ServletUtils.java
// desc:
// proj: ER 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/ArrayUtils.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.servlet;

// Java imports
import java.io.*;
import javax.servlet.http.*;

// MJS imports
import mjs.core.communication.FileManager;


/**
 * ServletUtils is a utility class used to do various servlet-specific tasks.
 * @author   Mike Shoemake
 * @version  1.0
 * @date     1/10/2004
 */
public class ServletUtils
{
   /**
    * The string representation of the class.
    */
   private static String sClassTitle = "ServletUtils";

   /**
    * Utility method.  Send this HTML file to the client.
    */
   public static void sendHTMLToClient(HttpServletResponse response, String html)
   {
      try
      {
         // Only send content type if not multi-part.
         response.setContentType("text/html");
         if (html.length() == 0)
         {
            // HTML is empty.  Return error page.
            html = "<html><head><title>Login</title></head><body><h1>Invalid Login.  Access Denied.</h1></body></html>";
         }
         PrintWriter out = response.getWriter();
         out.println(html);
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Utility method.  Send this HTML file to the client.
    */
   public static void sendHTMLFileToClient(HttpServletResponse response, String source)
   {
      try
      {
         String html = FileManager.readXMLFile(source);
         sendHTMLToClient(response, html);
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }

}
