//
// file: WSDialog
// desc: Standard Witness dialog baseclass.
// proj: eQuality 6.3 and later
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/dialogs/BaseDialog.java-arc  $
// $Author:Mike Shoemake$
// $Revision:9$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.dialogs;

// Java imports
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;
// Witness imports
import mjs.core.events.ButtonEvent;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;


/**
 * WSDialog:  The base dialog class for Witness Systems applets and applications.
 */
public abstract class BaseDialog extends JDialog
{
   /**
    * Is this dialog read only?
    */
   private boolean bReadOnly = false;

   /**
    * This allows the developer to pass in parameter objects to the dialog and then use the objects in the BeforeShow() event.
    */
   private Vector vctParameters = null;

   /**
    * Constructor.
    * @param    frame        The frame to use when displaying the dialog.
    * @param    title        The text to display in the title bar.
    * @param    bModal       Is this dialog a modal dialog?
    * @param    bResizable   Can the user resize this dialog manually?
    * @param    nWidth       The width of the dialog.
    * @param    nHeight      The height of the dialog.
    */
   public BaseDialog(Frame frame, String title, boolean bModal, boolean bResizable, int nWidth, int nHeight)
   {
      super(frame, title, bModal);
      try
      {
         setResizable(bResizable);
         setSize(nWidth, nHeight);
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
      // add a window listener to this DIALOG to handle user closes.
      this.addWindowListener(
      new java.awt.event.WindowAdapter()
      {
         public void windowClosed(WindowEvent e)
         {
            this_windowClosed(e);
         }
         public void windowClosing(WindowEvent e)
         {
            this_windowClosing(e);
         }
      });
   }

   /**
    * Called by a button on the dialog when it is clicked by the user.
    * @param   e              The action event associated with the button click.
    * @param   sDisplayText   The display text of the button.
    * @param   nObjectID      The unique numeric ID associated with the button.
    */
   public abstract void buttonClicked(ButtonEvent e);
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * Called when a button on the dialog is clicked by the user.  This is an
    * overridable method for the developer of a child class to use in order to
    * implement button click functionality without having to setup listeners.
    * @param   e              The action event associated with the button click.
    * @param   sDisplayText   The display text of the button.
    * @param   nObjectID      The unique numeric ID associated with the button.
    */
   public void event_OnButtonClicked(ButtonEvent e) { }

   /**
    * Called after the dialog has closed.
    */
   public void event_OnWindowClosed(WindowEvent e) { }

   /**
    * Called before the dialog closes.
    */
   public void event_OnWindowClosing(WindowEvent e) { }

   /**
    * Called right before the dialog is displayed to the user.
    */
   public void event_BeforeShow() { }

   /**
    * This allows the developer to pass in parameter objects to the dialog and then use the objects in the BeforeShow() event.
    */
   public void setParameter(Vector paramlist)
   {
      /* Allow the user to pass in parameter objects (could also be a Vector
         of objects.  */

      vctParameters = paramlist;
   }

   /**
    * This allows the developer to pass in parameter objects to the dialog and then use the objects in the BeforeShow() event.
    */
   public Vector getParameter()
   {
      return vctParameters;
   }

   /**
    * Is this dialog read only?
    */
   public abstract void setReadOnly(boolean value);

   /**
    * Is this dialog read only?
    */
   public abstract boolean isReadOnly();

   /**
    * Execute (run) the dialog.
    */
   public final void showDialog()
   {
      // EVENT.
      centerDialog();
      event_BeforeShow();
      this.setVisible(true);
   }

   // Window Closing methods.
   private void this_windowClosed(WindowEvent e)
   {
      // EVENT.
      event_OnWindowClosed(e);
   }

   private void this_windowClosing(WindowEvent e)
   {
      // EVENT.
      event_OnWindowClosing(e);
   }

   /**
    * Position the dialog in the center of the screen.
    */
   public void centerDialog()
   {
      Toolkit tk = Toolkit.getDefaultToolkit();
      Dimension sSize = tk.getScreenSize();
      Dimension dSize = getSize();
      if ((sSize.getWidth() > 0) && (sSize.getHeight() > 0))
      {
         // Center the dialog.
         Point position = new Point((int)(sSize.getWidth() - (int)dSize.getWidth()) / 2,
         (int)(sSize.getHeight() - (int)dSize.getHeight()) / 2);
         setLocation(position);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10426,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSDialog" );
      }
   }
}
// $Log:
//  9    Balance   1.8         6/6/2003 8:40:20 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  8    Balance   1.7         3/7/2003 9:28:18 AM    Mike Shoemake   Removed
//       reference to forms.utils.ComponentUtils.  Replaced with
//       core.utils.ComponentUtils.  Not sure yet why the same method is in
//       both classes.
//  7    Balance   1.6         1/29/2003 4:47:14 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  6    Balance   1.5         1/17/2003 8:50:24 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  5    Balance   1.4         11/25/2002 3:19:44 PM  Mike Shoemake   Update to
//       the listener model.
//  4    Balance   1.3         10/11/2002 8:54:18 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  3    Balance   1.2         9/17/2002 12:57:46 PM  Mike Shoemake   Rolling
//       back previous changes.
//  2    Balance   1.1         9/17/2002 10:51:48 AM  Mike Shoemake
//       showDialog() method is no longer FINAL.  It doesn't need to be.
//  1    Balance   1.0         8/23/2002 2:45:14 PM   Mike Shoemake
// $

//
//     Rev 1.4   Nov 25 2002 15:19:44   mshoemake
//  Update to the listener model.
//
//     Rev 1.3   Oct 11 2002 08:54:18   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.2   Sep 17 2002 12:57:46   mshoemake
//  Rolling back previous changes.
//
//     Rev 1.1   Sep 17 2002 10:51:48   mshoemake
//  showDialog() method is no longer FINAL.  It doesn't need to be.
//
//     Rev 1.0   Aug 23 2002 14:45:14   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:50   mshoemake
//  Initial revision.
//
//     Rev 1.2   Feb 07 2002 17:15:16   SEpperson
//  Fixed issues with global questions and answer schemes.
//
//     Rev 1.1   Dec 18 2001 08:49:58   mshoemake
//  Integrated the dialogs with the new buttonClicked() method.  Reserving the event_OnButtonClicked() method for aplication specific functionality.
//
//     Rev 1.0   Dec 17 2001 11:13:02   mshoemake
//  Initial revision.


