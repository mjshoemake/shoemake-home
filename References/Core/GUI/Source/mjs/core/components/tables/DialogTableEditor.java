//
// file: WSDialogTableCellRenderer.java
// desc:
// proj: ER 6.3
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/tables/DialogTableEditor.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.tables;

// Java Imports
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
// Witness Imports
import mjs.core.strings.SystemStrings;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.components.dialogs.BaseDialog;


/**
 * The table cell renderer used to display Witness Systems dialogs.
 * This renderer includes an ellipses button which the user clicks to trigger the dialog.
 */
public abstract class DialogTableEditor extends TableEditor
{
   /**
    * The TextField placed to the left of the ellipses button.
    */
   private JTextField txtDisplay = new JTextField();

   /**
    * The ellipses button used to trigger the dialog.
    */
   private JButton btnTrigger = new JButton(InternationalizationStrings.szEllipses);

   /**
    * The list of parameters to pass to the dialog.
    */
   private Vector vctParameters = null;

   /**
    * The text to display in the TextField component.
    */
   private String sDisplayText = null;

   /**
    * Constructor.
    * @param   parentEditor             The parent editor.
    * @param   stopEditingOnFocusLost   Stop editing when the component loses focus?
    * @param   sDisplayText             The display text of the renderer.
    */
   public DialogTableEditor(TableCellEditor parentEditor, boolean stopEditingOnFocusLost, String text)
   {
      setParentEditor(parentEditor);
      setStopEditingOnFocusLostEnabled(stopEditingOnFocusLost);
      // Save references.
      sDisplayText = text;
      // Setup edit box.
      txtDisplay.setPreferredSize(new Dimension(70, 20));
      txtDisplay.setEditable(false);
      txtDisplay.setFont(new java.awt.Font(SystemStrings.szDialog, 0, 11));
      // Setup button.
      btnTrigger.setPreferredSize(new Dimension(20, 20));
      btnTrigger.setMaximumSize(new Dimension(20, 20));
      btnTrigger.setMinimumSize(new Dimension(20, 20));
      btnTrigger.addActionListener(createButtonActionListener());
      // Setup Layout.
      GridBagLayout gridbag = new GridBagLayout();
      GridBagConstraints c = new GridBagConstraints();
      setLayout(gridbag);
      // Place the textfield and buttons on the form.
      c.anchor = GridBagConstraints.CENTER;
      c.fill = GridBagConstraints.BOTH;
      c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last in row
      c.weightx = 1.0;
      gridbag.setConstraints(txtDisplay, c);
      add(txtDisplay);
      c.anchor = GridBagConstraints.EAST;
      c.fill = GridBagConstraints.BOTH;
      c.gridwidth = GridBagConstraints.REMAINDER; //end row
      c.weightx = 0.0;
      gridbag.setConstraints(btnTrigger, c);
      add(btnTrigger);
   }
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * Called when the ellipses button is clicked.
    */
   public abstract BaseDialog event_CreateDialog();

   /**
    * Called when the ellipses button is clicked.
    */
   public abstract Vector event_GetDialogParameters();

   // ****** Functionality methods ******
   private ActionListener createButtonActionListener()
   {
      return new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            BaseDialog wsDialog = event_CreateDialog();
            String sText = sDisplayText;
            int row = getRow();
            int col = getColumn();
            // EVENT.
            Vector vctParam = event_GetDialogParameters();
            if (vctParam != null)
            {
               // Parameter is not null.  Pass to dialog.
               wsDialog.setParameter(vctParam);
            }
            // Center the dialog.
            centerDialog(wsDialog);
            // Show the dialog.
            wsDialog.showDialog();
            TableModel tm = getTable().getModel();
            tm.setValueAt(sText, row, col);
            // Update the property sheet.
            txtDisplay.setText(sText);
         }
      };
   }

   /**
    * Position the dialog in the center of the screen.
    */
   private void centerDialog(BaseDialog dialog)
   {
/*
      Toolkit tk = Toolkit.getDefaultToolkit();
      Dimension sSize = tk.getScreenSize();
      Dimension dSize = dialog.getSize();
      if ((sSize.getWidth() > 0) && (sSize.getHeight() > 0))
      {
         // Center the dialog.
         Point position = new Point((int)(sSize.getWidth() - (int)dSize.getWidth())/2,
                                    (int)(sSize.getHeight() - (int)dSize.getHeight())/2);
         dialog.setLocation (position);
      }
      else
      {
         // Throw exception.
         WSExceptionFactory.throwException(WSBusinessLogicException.ERR_DIVIDE_BY_ZERO, "WSDialogTableCellRenderer", null);
      }
*/

      dialog.centerDialog();
   }

   /**
    * Event called before the renderer is displayed to the user.
    */
   public void event_BeforeRendererDisplayed(Object value, boolean isSelected, boolean hasFocus)
   {
      // Set the editor value.
      if (sDisplayText != null)
      { txtDisplay.setText(sDisplayText); }
      btnTrigger.setEnabled(isSelected);
   }

   /**
    * The actual control to use to edit the value.
    */
   public Component getEditorComponent()
   {
      return this;
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/tables/DialogTableEditor.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:46:08   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 24 2002 13:18:00   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:28:54   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:12:14   mshoemake
//  Initial revision.


