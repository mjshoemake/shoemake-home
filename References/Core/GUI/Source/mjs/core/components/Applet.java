//
// file: WSApplet.java
// proj: ER 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/Applet.java-arc  $
// $Author:Mike Shoemake$
// $Revision:7$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components;

// Import java classes
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
//import netscape.javascript.*;
import mjs.core.administration.AppletParameters;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;


/*
WSApplet:  This is the base applet class.  It is generic and
   reusable.  This class should be written such that it is not
   tied to a release.  Release or project specific information
   should be kept in the inheriting class.

   Constructor Parameters:
      None
*/

public abstract class Applet extends JApplet
{
   // Abstract methods to be implemented by child class.

   /* Returns vector of parameter NAMES for the WSAppletParams to use
      to get the parameter list.  This method is called by the
      WSAppletParams class. */

   public abstract Vector getParamList();

   // Applet reference.
   private static Applet appSingleton = null;
   // Bounds/Dimensions of the applet
   private Rectangle rAppletBounds = null;

   // Constructor
   public Applet(int xpos, int ypos, int width, int height)
   {
      getContentPane().setLayout(new BorderLayout());
      // Create the applet bounds rectangle.
      rAppletBounds = new Rectangle(xpos, ypos, width, height);
      // Set the singleton reference.
      if (appSingleton != null)
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10417,
                                                       "Balance",
                                                       "Evaluations",
                                                       "",
                                                       Message.INTERNAL,
                                                       InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                       mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSApplet" );
      }
      else
      {
         // Set the reference to this applet.
         appSingleton = this;
      }
   }

   // Get the applet parameters
   public void init()
   {
      /* This class automatically gets the parameter information
         when it is created. */

      AppletParameters params = new AppletParameters(this);
   }

   // Get the name of the current browser.
/*
   public String getBrowserName()
   {
      String sBrowser = "";
      try
      {
         JSObject win = JSObject.getWindow(this);
         JSObject nav = (JSObject)win.getMember("navigator");
         sBrowser = (String)nav.getMember("appName");
      }
      catch (netscape.javascript.JSException e)
      {
         sBrowser = "Microsoft";
      }
      catch (Exception e)
      {
         System.out.println("NO BROWSER");
      }
      return sBrowser;
   }
*/

   public static Applet getApplet()
   {
      return appSingleton;
   }

   // Get Preset Applet Dimensions
   public int getAppletLeft()
   {
      return (int)(rAppletBounds.getX());
   }

   public int getAppletTop()
   {
      return (int)(rAppletBounds.getY());
   }

   public int getAppletWidth()
   {
      return (int)(rAppletBounds.getWidth());
   }

   public int getAppletHeight()
   {
      return (int)(rAppletBounds.getHeight());
   }

   // TOGETHER DIAGRAM DEPENDENCIES.  DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# AppletParameters lnkAppletParameters; */

   /**
    * @link dependency
    */
   /*# MessageDialogHandler lnkMessageDialogHandler; */

   /**
    * @link dependency
    */
   /*# ComponentUtils lnkComponentUtils; */
}
// $Log:
//  7    Balance   1.6         6/6/2003 8:40:19 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  6    Balance   1.5         3/28/2003 2:48:10 PM   Mike Shoemake   Added
//       Together diagram dependencies.
//  5    Balance   1.4         3/7/2003 9:28:11 AM    Mike Shoemake   Removed
//       reference to forms.utils.ComponentUtils.  Replaced with
//       core.utils.ComponentUtils.  Not sure yet why the same method is in
//       both classes.
//  4    Balance   1.3         1/29/2003 4:47:12 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  3    Balance   1.2         1/17/2003 8:50:23 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  2    Balance   1.1         10/11/2002 8:54:16 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:44:38 PM   Mike Shoemake
// $

//
//     Rev 1.1   Oct 11 2002 08:54:16   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:44:38   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:02   mshoemake
//  Initial revision.
//
//   Rev 1.2   Dec 07 2001 09:27:04   mshoemake
//Update to WSExceptionFactory class.  Changed throwException() to handleException().


