//
// file: WSTableCellRenderer.java
// desc:
// proj: ER 6.3
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/tables/TableEditor.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.tables;

// Java imports
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
// Witness imports
import mjs.core.components.ComponentOwner;


/**
 * WSTableCellRenderer:  The baseclass for all Witness Systems table cell renderers.
 */
public abstract class TableEditor extends JPanel implements javax.swing.table.TableCellRenderer
{
   /**
    * The parent table.
    */
   private JTable table = null;

   /**
    * The row of the table in which the renderer will be displayed.
    */
   private int row = 0;

   /**
    * The column of the table in which the renderer will be displayed.
    */
   private int col = 0;

   /**
    * Object to notify when a change occurs.
    */
   private ComponentOwner owner = null;

   /**
    * The parent table cell editor if applicable.
    */
   private TableCellEditor parentEditor = null;

   /**
    * Should the editor stop editing when focus is lost? This would be true for most editors (combobox, textfield,
    * etc.).  Renderers that have "..." buttons to display dialogs should probably have this set to false, since
    * the property would still be active while a dialog is displayed.
    */
   private boolean bStopEditingOnFocusLost = false;

   private FocusListener lFocus = new java.awt.event.FocusAdapter()
   {
      public void focusLost(FocusEvent e)
      {
         focusLost(e);
      }
   };

   /**
    * Constructor.
    */
   public TableEditor()
   {
   }

   /**
    * Called by the JTable.  Retrieves the actual editing component so the table can begin editing.
    */
   public Component getTableCellRendererComponent
   (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
   {
      this.table = table;
      this.row = row;
      this.col = col;
      // EVENT.
      event_BeforeRendererDisplayed(value, isSelected, hasFocus);
      // Get the component from child class if event method is
      // overridden.
      Component comp = event_GetRendererComponent();
      if (comp != null)
      {
         // Return specified renderer.
         return comp;
      }
      else
      {
         // Return this renderer.
         return this;
      }
   }
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * Called before the new value is reported to the table.
    * If the return value if false, the new value is replaced with the previous value.
    */
   public void event_BeforeRendererDisplayed(Object value, boolean isSelected, boolean hasFocus) { }

   /**
    * Get a renderer component if the renderer to be displayed is not this renderer.  The return value is null if the method
    * is not overridden.  This will cause this object to be returned & as the renderer component to display in the table.
    */
   public Component event_GetRendererComponent() { return null; };

   /**
    * Called when the FocusLost listener method is called.
    */
   public void event_OnFocusLost(FocusEvent e) { };

   /**
    * The actual control to use to edit the value.
    */
   public abstract Component getEditorComponent();

   /**
    * The parent table.
    */
   public void setTable(JTable table)
   {
      this.table = table;
   }

   /**
    * The parent table.
    */
   public JTable getTable()
   {
      return this.table;
   }

   /**
    * The row of the table in which the renderer will be displayed.
    */
   public void setRow(int row)
   {
      this.row = row;
   }

   /**
    * The row of the table in which the renderer will be displayed.
    */
   public int getRow()
   {
      return this.row;
   }

   /**
    * The column of the table in which the renderer will be displayed.
    */
   public void setColumn(int col)
   {
      this.col = col;
   }

   /**
    * The column of the table in which the renderer will be displayed.
    */
   public int getColumn()
   {
      return this.col;
   }

   /**
    * Object to notify when a change occurs.
    */
   public void setOwner(ComponentOwner newOwner)
   {
      this.owner = newOwner;
   }

   /**
    * Object to notify when a change occurs.
    */
   public ComponentOwner getOwner()
   {
      return this.owner;
   }

   /**
    * The parent table cell editor if applicable.
    */
   public void setParentEditor(TableCellEditor editor)
   {
      parentEditor = editor;
   }

   /**
    * The parent table cell editor if applicable.
    */
   public TableCellEditor getParentEditor()
   {
      return parentEditor;
   }

   /**
    * Should the editor stop editing when focus is lost? This would be true for most editors (combobox, textfield,
    * etc.).  Renderers that have "..." buttons to display dialogs should probably have this set to false, since
    * the property would still be active while a dialog is displayed.
    */
   public void setStopEditingOnFocusLostEnabled(boolean value)
   {
      bStopEditingOnFocusLost = value;
      if (bStopEditingOnFocusLost)
      {
         // Add focus listener
         this.addFocusListener(lFocus);
      }
      else
      {
         // Remove focus listener
         this.removeFocusListener(lFocus);
      }
   }

   /**
    * Should the editor stop editing when focus is lost? This would be true for most editors (combobox, textfield,
    * etc.).  Renderers that have "..." buttons to display dialogs should probably have this set to false, since
    * the property would still be active while a dialog is displayed.
    */
   public boolean isStopEditingOnFocusLostEnabled()
   {
      return bStopEditingOnFocusLost;
   }

   /**
    * Object to notify when a change occurs.
    */
   public abstract String getText();

   /**
    * This method calls stopCellEditing() for the
    */
   private void focusLost(FocusEvent e)
   {
      event_OnFocusLost(e);
      // Stop editing.  Focus was lost.
      if (getParentEditor() != null)
      {
         getParentEditor().stopCellEditing();
      }
      else if (this instanceof TableCellEditor)
      {
         TableCellEditor editor = (TableCellEditor)this;
         editor.stopCellEditing();
      }
   }

   /**
    * Add the listener to the editor control.
    */
   public void addFocusListener(FocusListener l)
   {
      if (getEditorComponent() != null)
      { getEditorComponent().addFocusListener(l); }
   }

   /**
    * Add the listener to the editor control.
    */
   public void removeFocusListener(FocusListener l)
   {
      if (getEditorComponent() != null)
      { getEditorComponent().removeFocusListener(l); }
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/tables/TableEditor.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:46:08   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 24 2002 13:18:00   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:28:56   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:12:14   mshoemake
//  Initial revision.


