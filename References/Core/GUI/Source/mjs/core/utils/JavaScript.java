//
// file: WSJavaScriptAccessor.java
// proj: eQuality 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/JavaScript.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.utils;

// Java imports
import java.applet.*;
import javax.swing.*;
//import netscape.javascript.JSObject;

// Witness imports
// **************************************************************************
// IF THIS IMPORT STATEMENT CHANGES PLEASE NOTIFY THE TEAM LEAD SO THE CLASS
// DIAGRAMS CAN BE UPDATED.
// **************************************************************************
//JavaDoc comments


/**
 * This class is an interface to the JavaScript functions.  The methods are
 * static, since they don't need to know anything about the context around them.
 * @author Mike Shoemake
 * @version 6.1
 * @date 6/14/2001
 */
public class JavaScript
{
//   JSObject m_hWindow = null;

   /**
    * JavaScript function call. <p> This assumes that no parameters will be passed to the JavaScript function.
    * @param   applet    The current applet.
    * @param   sCommand  The JavaScript function to call.
    */
   public void callJavaScript(Applet applet, String sCommand)
   {
      Object[] oEmptyParamList = {};
      callJavaScript(applet, sCommand, oEmptyParamList);
   }

   /**
    * JavaScript function call. <p> This assumes that no parameters will be passed to the JavaScript function.
    * @param   applet        The current applet.
    * @param   sCommand      The JavaScript function to call.
    * @param   sParamValue   The value to be passed into the JavaScript function as a parameter.
    */
   public void callJavaScript(Applet applet, String sCommand, String sParamValue)
   {
      Object[] oParamList = {sParamValue};
      callJavaScript(applet, sCommand, oParamList);
   }

   /**
    * JavaScript function call. <p> This assumes that no parameters will be passed to the JavaScript function.
    * @param   applet        The current applet.
    * @param   sCommand      The JavaScript function to call.
    * @param   oParamArray   An array of parameter objects.
    */
   public void callJavaScript(Applet applet, String sCommand, Object[] oParamArray)
   {
//      JSObject jsWindow = this.getWindow(applet);

      /* If the window is null then we are not in a browser.  The applet
         was probably called from JBuilder. */

//      if (jsWindow != null)
//      {
//         try
//         {
            // Call the JavaScript function.
            // I do not know why this works, but it does, so leave the eval call in
            //    this code. Otherwise, the call command will lock up the web browser...
//            jsWindow.eval("");
//            jsWindow.call(sCommand, oParamArray);
//         }
//         catch (Exception e)
//         {
            /* Unable to call JavaScript.  The applet is probably loaded from a
               test HTML that does not have the javascript info in it.  */
//         }
//      }
   }

   /**
    * Returns the name of the Browser.
    * @param   jsWindow    The window object used by the applet.
    */
//   private static String getBrowserName(JSObject jsWindow)
//   {
//      String sBrowser = null;
//      try
//      {
         // Is it Netscape?
//         JSObject nav = (JSObject)jsWindow.getMember("navigator");
//         sBrowser = (String)nav.getMember("appName");
//      }
//      catch (Exception e)
//      {
          // Must be IE.
//         sBrowser = "Microsoft";
//      }
//      return sBrowser;
//   }

   /**
    * Returns the window object used by this applet.
    * @param   applet        The current applet.
    */
//   private JSObject getWindow(Applet applet)
//   {
//      JSObject hWindow = null;
//      MessageLog.Debug("  Getting Window");
//      if (this.m_hWindow == null)
//      {
//         MessageLog.Debug("Window is not known, getting window.");
//         try
//         {
//            // Can I get the window?
//            this.m_hWindow = this.m_hWindow.getWindow(applet);
//         }
//         catch (netscape.javascript.JSException e)
//         {
//            // Must not be in a browser.
//         }
//         MessageLog.Debug("Got Window.");
//      }
//      MessageLog.Debug("  Returning Window.");
//      return hWindow;
//   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/JavaScript.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:48:28   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 24 2002 13:19:02   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:26   mshoemake
//  Initial revision.
//
//   Rev 1.4   Apr 22 2002 16:44:20   mshoemake
//Update to comments.
//
//   Rev 1.3   Apr 16 2002 16:04:22   mshoemake
//Update to import statement.


