//
// file: WSComboBoxTableCellRenderer.java
// desc:
// proj: ER 6.3
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/tables/ComboBoxTableEditor.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.tables;

// Java imports
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// Witness imports
import mjs.core.components.lists.ComboBox;
import mjs.core.components.Component;


/**
 * The base class for Witness Systems combobox table cell editors.
 */
public class ComboBoxTableEditor extends TableEditor
{
   /**
    * The actual control to use to edit the value.
    */
   private ComboBox control = new ComboBox();

   /**
    * Constructor.
    */
   public ComboBoxTableEditor()
   {
      control.setPreferredSize(new Dimension(100, 20));
      setStopEditingOnFocusLostEnabled(true);
      // Action Listener that updates the table.
      control.addActionListener(
      new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            TableModel tm = getTable().getModel();
            tm.setValueAt(control.getSelectedItem(), getRow(), getColumn());
         }
      });
      // Add the combobox.
      this.setLayout(new BorderLayout());
      add(control, BorderLayout.CENTER);
   }

   /**
    * Event called before the renderer is displayed to the user.
    */
   public void event_BeforeRendererDisplayed(Object value, boolean isSelected, boolean hasFocus)
   {
      // Select the current value.
      if (value != null)
      { control.setSelectedItem(value); }
      // If not selected, disable the control.
      control.setEnabled(isSelected);
   }

   /**
    * The item list of the combobox.  The Vector must contain objects that implement the WSListItem interface.
    */
   public void setItemList(Vector items)
   {
      control.setItemList(items);
   }

   /**
    * The renderer to use for this combobox.
    */
   public void setRenderer(ListCellRenderer aRenderer)
   {
      control.setRenderer(aRenderer);
   }

   /**
    * The selected item of the combobox.
    */
   public Object getSelectedItem()
   {
      return control.getSelectedItem();
   }

   /**
    * The actual control to use to edit the value.
    */
   public java.awt.Component getEditorComponent()
   {
      return (java.awt.Component) control;
   }

   /**
    * Object to notify when a change occurs.
    */
   public String getText()
   {
      return control.getSelectedItem().toString();
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/tables/ComboBoxTableEditor.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:46:06   mshoemake
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


