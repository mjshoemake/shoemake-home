//
// file: WSEnterValueDialog.java
// desc:
// proj: ER 6.3
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/dialogs/EnterValueDialog.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.2  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.dialogs;

// Java imports
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.event.*;
// Witness imports
import mjs.core.administration.SystemDefaults;
import mjs.core.components.Panel;
import mjs.core.components.TextField;
import mjs.core.components.ButtonPanel;
import mjs.core.components.Button;
import mjs.core.events.ButtonEvent;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.types.HorizontalLocationType;


/**
 * This dialog allows the user to enter a text or numeric value, such as
 * a name, caption text, width, or height.  OK and Cancel buttons are provided. <p> Usage: <pre>
 * <code>
 * Frame frame = JOptionPane.getFrameForComponent (applet_reference);
 * String text = WSEnterValueDialog.execute (frame, "This is the frame", true, "The label caption:", false);
 * </code>
 * </pre>
 */
public class EnterValueDialog extends BaseWindowDialog
{
   /**
    * The edit value entered by the user.
    */
   private TextField edtValue = new TextField();

   /**
    * The main panel for this dialog.
    */
   private Panel pnlMain = null;

   /**
    * Constructor.
    * @param   frame          The frame to use when displaying the dialog.
    * @param   title          The title for the frame.
    * @param   modal          Is this dialog a modal dialog?
    * @param   caption        The label caption to display above the edit box.
    * @param   defaultValue   The default value of the edit box.
    */
   public EnterValueDialog(Frame frame, String title, boolean modal, String caption, String defaultValue)
   {
      super(frame, title, modal, true, 5, 5, true);
      try
      {
         // Description
         super.setDescriptionText(caption);
         // Default value of the edit box.
         if (defaultValue != null)
         { edtValue.setText(defaultValue); }
         else { edtValue.setText(""); }
         // Complete initialization.
         fdBaseInit();
         pack();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Initializes buttons, labels and text fields.
    */
   private void fdBaseInit() throws Exception
   {
      pnlMain = getWindowPanel();
      ButtonPanel pnlButtons = getButtonPanel();
      // Add the buttons.
      pnlButtons.addButton(InternationalizationStrings.szCancel, HorizontalLocationType.CENTER);
      pnlButtons.addButton(InternationalizationStrings.szOK, HorizontalLocationType.CENTER);
      // Set the dialog and panel properties.
      pnlMain.setLayout(null);
      pnlMain.setPreferredSize(250, 25);
      // Bounds
      edtValue.setBounds(new Rectangle(1, 1, 265, 21));
      // Font
      edtValue.setFont(SystemDefaults.getDefaultFont());
      // Tab Order
      Button btnOK = pnlButtons.getButtonByText(InternationalizationStrings.szOK);
      Button btnCancel = pnlButtons.getButtonByText(InternationalizationStrings.szCancel);
      // Next focusable component
      edtValue.setNextFocusableComponent(btnOK);
      btnOK.setNextFocusableComponent(btnCancel);
      btnCancel.setNextFocusableComponent(edtValue);
      // Add components
      getContentPane().add(pnlMain);
      pnlMain.add(edtValue, null);
   }

   /**
    * Displays dialog and returns the text entered by the user.
    * @param   readonly   Is this dialog read only or not?
    */
   public String showDialog(boolean readOnly)
   {
      setReadOnly(readOnly);
      super.showDialog();
      System.out.println("DIALOG CLOSING");
      return edtValue.getText();
   }

   /**
    * Displays dialog and returns the text entered by the user.
    */
   public static String execute(Frame frame, String title, boolean modal, String sMessage,
   String sDefaultValue, boolean bReadOnly)
   {
      EnterValueDialog dialog = new EnterValueDialog(frame, title, modal, sMessage, sDefaultValue);
      return dialog.showDialog(bReadOnly);
   }

   /**
    * This sets the components in the dialog to be readonly or not readonly.
    * @param   readonly   Is this dialog read only or not?
    */
   public void setReadOnly(boolean readonly)
   {
      getButtonPanel().getButtonByText(InternationalizationStrings.szOK).setEnabled(!readonly);
      // Edit boxes
      edtValue.setEditable(!readonly);
   }

   /**
    * The components in the dialog are either readonly or not readonly.
    */
   public boolean isReadOnly()
   {
      return !getButtonPanel().getButtonByText(InternationalizationStrings.szOK).isEnabled();
   }
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * The user clicked the ok button.
    */
   public void event_OkButtonClicked() { }

   /**
    * Event triggered when the user clicks the cancel button.
    */
   public void event_CancelButtonClicked() { }

   /**
    * Called by a button on the dialog when it is clicked by the user.
    * @param   e              The action event associated with the button click.
    * @param   sDisplayText   The display text of the button.
    * @param   nObjectID      The unique numeric ID associated with the button.
    */
   public void buttonClicked(ButtonEvent e)
   {
      // EVENT.
      event_OnButtonClicked(e);
      if (e.getButtonCaption().equals(InternationalizationStrings.szOK))
      {
         // OK Button clicked.
         okButtonClicked();
      }
      else
      {
         // Cancel Button clicked.
         cancelButtonClicked();
      }
   }

   /**
    * The user clicked the ok button.
    */
   private void okButtonClicked()
   {
      // EVENT.
      event_OkButtonClicked();
      this.setVisible(false);
   }

   /**
    * The user clicked the cancel button.
    */
   private void cancelButtonClicked()
   {
      // EVENT.
      event_CancelButtonClicked();
      edtValue.setText(null);
      this.setVisible(false);
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/dialogs/EnterValueDialog.java-arc  $
//
//     Rev 1.2   Nov 25 2002 15:19:46   mshoemake
//  Update to the listener model.
//
//     Rev 1.1   Oct 11 2002 08:54:20   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:45:14   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:52   mshoemake
//  Initial revision.
//
//     Rev 1.1   Dec 18 2001 08:49:58   mshoemake
//  Integrated the dialogs with the new buttonClicked() method.  Reserving the event_OnButtonClicked() method for aplication specific functionality.
//
//     Rev 1.0   Dec 17 2001 11:13:02   mshoemake
//  Initial revision.


