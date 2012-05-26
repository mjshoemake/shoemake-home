//
// file: WSTextFieldTableCellRenderer.java
// desc:
// proj: ER 6.3
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/tables/TextFieldTableEditor.java-arc  $
// $Author:Mike Shoemake$
// $Revision:3$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.tables;

// Java imports
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
// Witness import
import mjs.core.strings.InternationalizationStrings;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.administration.SystemDefaults;
import mjs.core.components.TextField;
import mjs.core.components.ComponentOwner;
import mjs.core.components.Component;


/**
 * The base class for Witness Systems combobox table cell editors.
 */
public class TextFieldTableEditor extends TableEditor
{
   /**
    * The actual control to use to edit the value.
    */
   private TextField control = new TextField();

   /**
    * The most recent value.
    */
   private Object oSavedValue = null;

   /**
    * The message to display if the user input is invalid.
    */
   private String sUserInputErrorMsg = InternationalizationStrings.szInvalidInputValue;

   /**
    * Display error messages?
    */
   private boolean bDisplayErrorMsg = true;

   /**
    * Constructor.
    */
   public TextFieldTableEditor()
   {
      control.setPreferredSize(new Dimension(75, 20));
      control.setBorder(BorderFactory.createEmptyBorder());
      control.setFont(SystemDefaults.getDefaultFont());
      control.setEditable(true);
      setStopEditingOnFocusLostEnabled(true);
      // Action Listener that updates the table.
      control.addActionListener(
      new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            postChanges();
         }
      });
      // Add the control.
      this.setLayout(new BorderLayout());
      add(control, BorderLayout.CENTER);
   }
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * Called before the new value is reported to the table.
    * If the return value if false, the new value is replaced with the previous value.
    */
   public boolean event_ValidateNewValue(String newText) { return true; }

   /**
    * Event called before the renderer is displayed to the user.
    */
   public void event_BeforeRendererDisplayed(Object value, boolean isSelected, boolean hasFocus)
   {
      if (value != null)
      {
         oSavedValue = value;
         control.setText(oSavedValue.toString());
      }
      // If not selected, disable the control.
      control.setEnabled(isSelected);
   }

   /**
    * The actual control to use to edit the value.
    */
   public java.awt.Component getEditorComponent()
   {
      return (java.awt.Component) control;
   }

   /**
    * Update the table with the new changes.
    */
   public void postChanges()
   {
      if (event_ValidateNewValue(control.getText()))
      {
         TableModel tm = getTable().getModel();
         tm.setValueAt(control.getText(), getRow(), getColumn());
         oSavedValue = (Object)(control.getText());
         if (getOwner() != null)
         { getOwner().notifyOfAction(this, 0); }
      }
      else if (isErrorMessagesEnabled())
      {
         // Notify the user.
         // Create message object
         Message message = MessageFactory.createMessage( 10540,
                                                        "Balance",
                                                        "eQuality",
                                                        "",
                                                        Message.ERROR,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, this, InternationalizationStrings.szError );
         control.setText(oSavedValue.toString());
      }
   }

   /**
    * Called when the FocusLost listener method is called.
    */
   public void event_OnFocusLost(FocusEvent e)
   {
      postChanges();
   }

   /**
    * Display error messages?
    */
   public void setErrorMessagesEnabled(boolean value)
   {
      bDisplayErrorMsg = value;
   }

   /**
    * Display error messages?
    */
   public boolean isErrorMessagesEnabled()
   {
      return bDisplayErrorMsg;
   }

   /**
    * Is the editbox readonly?
    */
   public void setReadOnly(boolean value)
   {
      control.setEditable(!value);
   }

   /**
    * Is the editbox readonly?
    */
   public boolean isReadOnly()
   {
      return !control.isEditable();
   }

   /**
    * Object to notify when a change occurs.
    */
   public String getText()
   {
      return control.getText();
   }
}
// $Log:
//  3    Balance   1.2         6/6/2003 8:40:21 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  2    Balance   1.1         1/17/2003 8:51:18 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  1    Balance   1.0         8/23/2002 2:46:08 PM   Mike Shoemake
// $
//
//     Rev 1.0   Aug 23 2002 14:46:08   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 24 2002 13:18:02   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:28:56   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:12:14   mshoemake
//  Initial revision.


