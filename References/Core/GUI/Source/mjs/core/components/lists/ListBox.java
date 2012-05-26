//
// file: WSList.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/ListBox.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.1  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.lists;

// Java imports
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
// Witness imports
import mjs.core.factories.ListItemFactory;
import mjs.core.factories.StringListItemFactory;
import mjs.core.administration.SystemDefaults;
import mjs.core.components.ScrollPane;
import mjs.core.components.ComponentOwner;
import mjs.core.components.Component;
import mjs.core.types.BorderType;
import mjs.core.utils.ColorFactory;


/**
 * WSList:  This is the base class for Witness Systems selection list objects.
 * This listbox accepts and displays any object that implements the WSListItem
 * interface.  It extends a JScrollPane, so all scroll functionality is imbedded in the component. <p>
 * Several methods were added to the JComponent functionality (setLeft, setTop,
 * etc.) to make the interface more consistent (JComponent had a getWidth but no setWidth, etc.). <p>
 * Several empty event methods have been made public to allow the developer to
 * perform some action when the event occurs without having to add their own
 * listeners to the component.  To handle the events simply override the event
 * handler methods and implement the desired functionality.  Not all of the events
 * are triggered by listeners.  Some, like OnDelete, are called when the delete() method is called. <code>
 * <p>   public void event_OnDelete() <p>   public void event_OnSelectionChanged (ListSelectionEvent e)
 * <p>   public void event_OnMouseClicked (MouseEvent e) <p>   public void event_OnMousePressed (MouseEvent e)
 * <p>   public void event_OnMouseReleased (MouseEvent e) <p>   public void event_OnMouseEntered (MouseEvent e)
 * <p>   public void event_OnMouseExited (MouseEvent e)
 * <p>   public void event_BeforeShowPopupMenu (Vector vctMenuItemList) </code>
 */
public class ListBox extends ScrollPane implements Component
{
   /**
    * If ReadOnly is true then the selected value cannot be changed.  This is
    * different from being disabled in that a non-ReadOnly component can still respond to events (drag, mouse click, etc.).
    */
   private boolean bReadOnly = false;

   /**
    * The default selected value.  This is used to make the component
    * ReadOnly.  If bReadOnly is turned on, the combo box automatically
    * reverts to the default value when the user attempts to make a change, causing the combobox to be read only.
    */
   private ListItem wsDefaultValue = null;

   /**
    * The currently selected value.  This is used to make the component ReadOnly.  If bReadOnly is turned on, the combo box
    * automatically reverts to the default value when the user attempts
    * to make a change, causing the combobox to be read only.
    */
   private ListItem wsCurrentValue = null;

   /**
    * Should the popup menu display to the user when the component is right clicked?
    */
   private boolean bResettingDefault = false;

   /**
    * The List Item Factory for this WSList component.  This factory creates the items
    * to be displayed in a way that the underlying sort functionality can understand and manipulate.
    */
   private StringListItemFactory itemFactory = new StringListItemFactory();

   /**
    * This is the background set using the setBackground() method.  If this is null then
    * the background will depend on whether it's read only or not.
    */
   private Color updatedBackground = null;

   /**
    * The index of this component in the create order.
    */
   private int nCreateOrder = 0;

   /**
    * The list of items.  All sort functionality is embedded in the WSSortedVector.
    */
   private SortedList itemList = new SortedList(false, (StringListItemFactory)itemFactory);

   /**
    * The ListModel for the JList component.
    */
   private DefaultListModel listmodel = new DefaultListModel();

   /**
    * The actual JList component (this class wraps the JList rather than extending it so it can extend from the JScrollPane).
    */
   private JList listbox = new JList(listmodel);

   /**
    * A string representation of the class title.
    */
   private String sClassTitle = "WSList";

   /**
    * The component or object that owns this object.  This is used to notify the owner when something changes.
    */
   private ComponentOwner wsOwner = null;

   /**
    * Default the selected value to the first item in the list. This is the option used when no saved default value is found.
    */
   private static final int CMD_DEFAULT_TO_FIRST_ITEM = 0;

   /**
    * Default the selected value to the saved default value.
    */
   private static final int CMD_DEFAULT_TO_SAVED_ITEM = 1;

   /**
    * The default selection listener for this component.  This listener triggers the event_OnSelectionChanged() event.
    */
   private ListSelectionListener lSelection = new javax.swing.event.ListSelectionListener()
   {
      public void valueChanged(ListSelectionEvent e)
      {
         default_valueChanged(e);
      }
   };

   /**
    * The default item listener for this component.  This listener triggers the event_OnItemStateChanged() event.
    */
   private ItemListener lItem = new java.awt.event.ItemListener()
   {
      public void itemStateChanged(ItemEvent e)
      {
         default_itemStateChanged(e);
      }
   };

   /**
    * Constructor.
    */
   public ListBox()
   {
      super();
      // Inialize component.
      lstBaseInit();
   }

   /**
    * Constructor.
    * @param   pButtonId   The unique, numeric ID used to distinguish the component from other components.
    */
   public ListBox(long pObjectId)
   {
      super(pObjectId);
      // Inialize component.
      lstBaseInit();
   }

   /**
    * Initialize the component.
    */
   private void lstBaseInit()
   {
      setSize(100, 100);
      setFont(SystemDefaults.getDefaultFont());
      setReadOnly(false);
      // Background set in setReadOnly() method.
      setForeground(SystemDefaults.getDefaultForeground());
      getViewport().add(listbox);
      addListSelectionListener(lSelection);
      getBorderManager().getInnerBorder().setValue(BorderType.BORDER_LINE);
      getBorderManager().getInnerBorder().setLineThickness(1);
      getBorderManager().getInnerBorder().setColor(ColorFactory.black);
   }

   /**
    * The index of this component in the create order.  The create order of
    * components determines the overlap hierarchy and the tab order.
    */
   public int getCreateOrder()
   {
      return nCreateOrder;
   }

   /**
    * The index of this component in the create order.  The create order of
    * components determines the overlap hierarchy and the tab order.
    */
   public void setCreateOrder(int value)
   {
      nCreateOrder = value;
   }

   /**
    * Set whether or not this component is Read Only to the user.
    */
   public void setReadOnly(boolean value)
   {
      bReadOnly = value;
      setDefaultValue(getSelectedItem());

      // Update background color if necessary.
      if (updatedBackground == null)
      {
         if (! value)
         {
            // Editable.
            listbox.setBackground(SystemDefaults.getDefaultEditBackground());
         }
         else
         {
            // Not editable.
            listbox.setBackground(SystemDefaults.getDefaultNonEditBackground());
         }
      }
   }


   /**
    * Is this component Read Only to the user?
    */
   public boolean isReadOnly()
   {
      return bReadOnly;
   }

   /**
    * The background color of this component.
    */
   public void setBackground(Color bg)
   {
      super.setBackground(bg);
      updatedBackground = bg;
      if (listbox != null)
      {
         // Update the child component (listbox).
         listbox.setBackground(bg);
      }
   }

   /**
    * The component or object that owns this object.  This is used to notify the owner when something changes.
    */
   public void setOwner(ComponentOwner owner)
   {
      wsOwner = owner;
   }

   /**
    * The component or object that owns this object.  This is used to notify the owner when something changes.
    */
   public ComponentOwner getOwner()
   {
      return wsOwner;
   }

   /**
    * The default selected value.  This is used to make the component
    * ReadOnly.  If bReadOnly is turned on, the combo box automatically
    * reverts to the default value when the user attempts to make a change, causing the combobox to be read only.
    */
   public void setDefaultValue(ListItem value)
   {
      wsDefaultValue = value;
      setCurrentValue(value);
   }

   /**
    * The default value for the component.  This is used to allow
    * the developer to reset the component to the original value if necessary.
    */
   public ListItem getDefaultValue()
   {
      return wsDefaultValue;
   }

   /**
    * The currently selected value.  This is used to make the component ReadOnly.  If bReadOnly is turned on, the combo box
    * automatically reverts to the default value when the user attempts
    * to make a change, causing the combobox to be read only.
    */
   private void setCurrentValue(ListItem value)
   {
      wsCurrentValue = value;
      this.setSelectedItem((Object)value);
   }

   /**
    * Reset the current value of the combobox to the default value.
    */
   public void resetToDefaultValue()
   {
      if (!bResettingDefault)
      {
         bResettingDefault = true;
         preventChanges();
         bResettingDefault = false;
      }
   }

   /**
    * Reset the current value of the combobox to the default value.
    * Make sure the previously selected value is still the selected value.
    */
   private void preventChanges()
   {
      wsCurrentValue = getSelectedItem();
      // What do I need to do?
      int nCommand = 0;
      if (wsDefaultValue == null)
      {
         if (getSelectedIndex() == 0)
         {
            // The default value is already selected.  No change.
            return;
         }
         else
         {
            // No stored default value.  Reset to first item.
            nCommand = CMD_DEFAULT_TO_FIRST_ITEM;
         }
      }
      else if (wsCurrentValue == null)
      {
         // Default found.  Make the default current.
         nCommand = CMD_DEFAULT_TO_SAVED_ITEM;
      }
      else if (wsDefaultValue.toString().equals(wsCurrentValue.toString()))
      {
         // The default value is already selected.  No change.
         return;
      }
      else
      {
         // Default found.  Make the default current.
         nCommand = CMD_DEFAULT_TO_SAVED_ITEM;
      }
      // Handle the command.
      switch (nCommand)
      {
         case CMD_DEFAULT_TO_FIRST_ITEM:
            {
               if (getItemCount() > 0)
               { setSelectedIndex(0); }
               return;
            }
         case CMD_DEFAULT_TO_SAVED_ITEM:
            {
               setCurrentValue(wsDefaultValue);
               return;
            }
      }
   }
   // ****** Font ******

   /**
    * Get the font name.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public String getFontName()
   {
      return getFont().getFamily();
   }

   /**
    * Get the font style.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public int getFontStyle()
   {
      return getFont().getStyle();
   }

   /**
    * Get the font size.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public int getFontSize()
   {
      return getFont().getSize();
   }

   /**
    * Set the font name.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public void setFontName(String sfontName)
   {
      setFont(new Font(sfontName, getFont().getStyle(), getFont().getSize()));
   }

   /**
    * Set the font style.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public void setFontStyle(int nfontStyle)
   {
      setFont(new Font(getFont().getFamily(), nfontStyle, getFont().getSize()));
   }

   /**
    * Set the font name.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public void setFontSize(int nfontSize)
   {
      setFont(new Font(getFont().getFamily(), getFont().getStyle(), nfontSize));
   }
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * Triggered by ActionPerformed listener.
    */
   public void event_OnSelectionChanged(ListSelectionEvent e) { }

   /**
    * Triggered by ItemStateChanged event of Item listener.
    */
   public void event_OnItemStateChanged(ItemEvent e) { }
   // ****** Functionality methods ******

   /**
    * Delete this component.
    */
   public void delete()
   {
      super.delete();
      itemList.removeAllElements();
      removeAllElements();
   }
   // ****** Listener methods ******
   // LISTENER EVENT - ActionPerformed

   /**
    * Handles selection change notification, Read Only, etc.
    */
   public void default_valueChanged(ListSelectionEvent e)
   {
      if (!bResettingDefault)
      {
         bResettingDefault = true;
         if (isReadOnly())
         {
            preventChanges();
         }
         else
         {
            if (getSelectedItem() instanceof ListItem)
            {
               wsCurrentValue = (ListItem)getSelectedItem();
            }
            if (!e.getValueIsAdjusting())
            {
               // Selection changed.
               event_OnSelectionChanged(e);
               if (wsOwner != null)
               {
                  wsOwner.notifyOfAction(this, 0);
               }
            }
         }
         bResettingDefault = false;
      }
   }

   /**
    * Handles selection change notification, Read Only, etc.
    */
   public void default_itemStateChanged(ItemEvent e)
   {
      if (!bResettingDefault)
      {
         bResettingDefault = true;
         if (isReadOnly())
         {
            preventChanges();
         }
         else
         {
            if (getSelectedItem() instanceof ListItem)
            {
               wsCurrentValue = (ListItem)getSelectedItem();
            }
            // Selection changed.
            event_OnItemStateChanged(e);
         }
         bResettingDefault = false;
      }
   }

   /**
    * Remove all items in the list.
    */
   public void removeAllElements()
   {
      itemList.removeAllElements();
      updateListModel();
   }

   /**
    * Return the item at this position in the list.
    */
   public ListItem get(int index)
   {
      return itemList.get(index);
   }

   /**
    * Is the list sorted?
    */
   public void setSorted(boolean sorted)
   {
      itemList.setSorted(sorted);
      updateListModel();
   }

   /**
    * Is the list sorted?
    */
   public boolean isSorted()
   {
      return itemList.isSorted();
   }

   /**
    * Update the list model.  This should be called when something changes in the list.
    */
   private void updateListModel()
   {
      if ((listmodel != null) && (itemList != null))
      {
         listmodel.removeAllElements();
         for (int C = 0; C <= itemList.size() - 1; C++)
         {
            ListItem item = (ListItem)(itemList.get(C));
            // Add the items in sorted order.
            listmodel.addElement((Object)(itemList.get(C)));
         }
      }
      if (isReadOnly())
      {
         // ReadOnly.  Select the default item.
         preventChanges();
      }
   }

   /**
    * This updates the picklist when changes occur.
    */
   public void refreshItemList()
   {
      updateListModel();
   }

   /**
    * Populate with a Vector of WSListItem objects.  WSListItem is an interface
    * that can be implemented by any object that needs to be used in the WSList. <p>
    * NOTE:  If the Vector contains anything other than WSListItem objects an
    * exception will be thrown and the method will exit.
    */
   public void setItemList(Vector items)
   {
      itemList.setItemList(items);
      updateListModel();
   }

   /**
    * Get the selected item.  Returns the first selected value or null, if the selection is empty.
    */
   public int getItemCount()
   {
      return listmodel.size();
   }

   /**
    * Get the selected item.  Returns the first selected value or null, if the selection is empty.
    */
   public ListItem getSelectedItem()
   {
      return (ListItem)(listbox.getSelectedValue());
   }

   /**
    * The selected list item for this component.
    */
   public void setSelectedItem(Object obj)
   {
      listbox.setSelectedValue(obj, true);
   }

   /**
    * Select the list item for this component based on the specified text.  If an exact match is found, select that item.
    */
   public void setSelectedText(String text)
   {
      Vector items = getItemList();
      for (int C = 0; C <= items.size() - 1; C++)
      {
         Object obj = items.get(C);
         if (obj.toString().equals(text))
         {
            listbox.setSelectedValue(obj, true);
            return;
         }
      }
   }

   /**
    * Select the list item for this component based on the specified text.
    * If an item is found whose text begins with the specified text, select that item.
    */
   public void setSelectedStartsWith(String text)
   {
      Vector items = getItemList();
      for (int C = 0; C <= items.size() - 1; C++)
      {
         Object obj = items.get(C);
         if (obj.toString().startsWith(text))
         {
            listbox.setSelectedValue(obj, true);
            return;
         }
      }
   }

   /**
    * The selected list item for this component.
    */
   public void setSelectedIndex(int index)
   {
      Object obj = listbox.getModel().getElementAt(index);
      listbox.setSelectedValue(obj, true);
   }

   /**
    * The index of the selected list item for this component.
    */
   public int getSelectedIndex()
   {
      return listbox.getSelectedIndex();
   }

   /**
    * The indexes of the selected list items for this component.
    */
   public int[] getSelectedIndices()
   {
      return listbox.getSelectedIndices();
   }

   /**
    * Get the selected items.  This returns an array and allows for multiple items to be selected.
    */
   public Object[] getSelectedItems()
   {
      return listbox.getSelectedValues();
   }

   /**
    * Clears the selection - after calling this method isSelectionEmpty() will return true.
    * This is a convenience method that just delegates to the selectionModel.
    */
   public void clearSelection()
   {
      listbox.clearSelection();
   }

   /**
    * Add item to the list.
    * @param     item    The WSListItem to add.
    */
   public void add(ListItem item)
   {
      itemList.add(item);
      updateListModel();
   }

   /**
    * Remove item from the list.
    * @param     item    The WSListItem to remove.
    */
   public void remove(ListItem item)
   {
      itemList.remove(item);
      updateListModel();
   }

   /**
    * Add item to the list.
    * @param     sDisplayText      The text to be displayed to the user for this item.
    * @param     nIntSortValue     The numeric sort value (int).
    * @param     obj               An object associated with this item.  This could be
    * set to null.  It's simply a way to associate an object with this item if necessary.
    */
   public void add(String sDisplayText, int nIntSortValue, Object obj)
   {
      itemList.add(sDisplayText, nIntSortValue, obj);
      updateListModel();
   }

   /**
    * Add item to the list.
    * @param     sDisplayText      The text to be displayed to the user for this item.
    * @param     lLongSortValue    The numeric sort value (long).
    * @param     obj               An object associated with this item.  This could be
    * set to null.  It's simply a way to associate an object with this item if necessary.
    */
   public void add(String sDisplayText, long lLongSortValue, Object obj)
   {
      itemList.add(sDisplayText, lLongSortValue, obj);
      updateListModel();
   }

   /**
    * Add item to the list.
    * @param     sDisplayText      The text to be displayed to the user for this item.
    * @param     sStringSortValue  The String sort value.
    * @param     obj               An object associated with this item.  This could be
    * set to null.  It's simply a way to associate an object with this item if necessary.
    */
   public void add(String sDisplayText, String sStringSortValue, Object obj)
   {
      itemList.add(sDisplayText, sStringSortValue, obj);
      updateListModel();
   }

   /**
    * Add item to the list.
    * @param     sDisplayText      The text to be displayed to the user for this item.  This
    * is also the sort value when sorted is turned on.
    * @param     obj               An object associated with this item.  This could be
    * set to null.  It's simply a way to associate an object with this item if necessary.
    */
   public void add(String sDisplayText, Object obj)
   {
      itemList.add(sDisplayText, sDisplayText, obj);
      updateListModel();
   }

   /**
    * Add item to the list.
    * @param     sDisplayText      The text to be displayed to the user for this item.  This
    * is also the sort value when sorted is turned on.
    */
   public void add(String sDisplayText)
   {
      itemList.add(sDisplayText, sDisplayText, null);
      updateListModel();
   }

   /**
    * Remove the list item associated with this String sort value. <p> NOTE:  If multiple matches are found, all are removed.
    * @param    sStringSortValue    The sort value (String) of the item to remove.
    */
   public void remove(String sStringSortValue)
   {
      itemList.remove(sStringSortValue);
      updateListModel();
   }

   /**
    * Remove the list item associated with this int sort value. <p> NOTE:  If multiple matches are found, all are removed.
    * @param    nIntSortValue    The sort value (int) of the item to remove.
    */
   public void remove(int nIntSortValue)
   {
      itemList.remove(nIntSortValue);
      updateListModel();
   }

   /**
    * Remove the list item associated with this long sort value. <p> NOTE:  If multiple matches are found, all are removed.
    * @param    lLongSortValue    The sort value (long) of the item to remove.
    */
   public void remove(long lLongSortValue)
   {
      itemList.remove(lLongSortValue);
      updateListModel();
   }

   /**
    * Remove the list item associated with this object reference. <p> NOTE:  If multiple matches are found, all are removed.
    * @param    oObjectReference    The object reference of the item to remove.
    */
   public void remove(Object oObjectReference)
   {
      itemList.remove(oObjectReference);
      updateListModel();
   }

   /**
    * Returns a copy of the item Vector. <p> Changes to the returned Vector do not affect the actual Vector found in
    * the WSSortedVector class.
    */
   public Vector getItemList()
   {
      return itemList.cloneAsVector();
   }

   // ****** Listener methods ******
   public void addListSelectionListener(ListSelectionListener l)
   {
      if (addToListenerList(l))
      {
         if (listbox != null)
         {
            // Add the listener.
            listbox.addListSelectionListener(l);
         }
      }
   }

   public void addFocusListener(FocusListener l)
   {
      super.addFocusListener(l);
      if (listbox != null)
      { listbox.addFocusListener(l); }
   }

   public void addInputMethodListener(InputMethodListener l)
   {
      super.addInputMethodListener(l);
      if (listbox != null)
      { listbox.addInputMethodListener(l); }
   }

   public void addKeyListener(KeyListener l)
   {
      super.addKeyListener(l);
      if (listbox != null)
      { listbox.addKeyListener(l); }
   }

   public void addMouseListener(MouseListener l)
   {
      super.addMouseListener(l);
      if (listbox != null)
      { listbox.addMouseListener(l); }
   }

   public void addMouseMotionListener(MouseMotionListener l)
   {
      super.addMouseMotionListener(l);
      if (listbox != null)
      { listbox.addMouseMotionListener(l); }
   }

   public void addPropertyChangeListener(PropertyChangeListener l)
   {
      super.addPropertyChangeListener(l);
      if (listbox != null)
      { listbox.addPropertyChangeListener(l); }
   }

   public void addVetoableChangeListener(VetoableChangeListener l)
   {
      super.addVetoableChangeListener(l);
      if (listbox != null)
      { listbox.addVetoableChangeListener(l); }
   }

   public void removeFocusListener(FocusListener l)
   {
      super.removeFocusListener(l);
      listbox.removeFocusListener(l);
   }

   public void removeInputMethodListener(InputMethodListener l)
   {
      super.removeInputMethodListener(l);
      listbox.removeInputMethodListener(l);
   }

   public void removeKeyListener(KeyListener l)
   {
      super.removeKeyListener(l);
      listbox.removeKeyListener(l);
   }

   public void removeMouseListener(MouseListener l)
   {
      super.removeMouseListener(l);
      listbox.removeMouseListener(l);
   }

   public void removeMouseMotionListener(MouseMotionListener l)
   {
      super.removeMouseMotionListener(l);
      listbox.removeMouseMotionListener(l);
   }

   public void removePropertyChangeListener(PropertyChangeListener l)
   {
      super.removePropertyChangeListener(l);
      listbox.removePropertyChangeListener(l);
   }

   public void removeVetoableChangeListener(VetoableChangeListener l)
   {
      super.removeVetoableChangeListener(l);
      listbox.removeVetoableChangeListener(l);
   }

   public void setEnabled(boolean value)
   {
      listbox.setEnabled(value);
   }

   public boolean isEnabled()
   {
      return listbox.isEnabled();
   }

   public void setSelectionMode(int selectionMode)
   {
      listbox.setSelectionMode(selectionMode);
   }

   public int getSelectionMode()
   {
      return listbox.getSelectionMode();
   }

   public void ensureIndexIsVisible(int index)
   {
      listbox.ensureIndexIsVisible(index);
   }

   public int getAnchorSelectionIndex()
   {
      return listbox.getAnchorSelectionIndex();
   }

   public ListCellRenderer getCellRenderer()
   {
      return listbox.getCellRenderer();
   }

   public int getFirstVisibleIndex()
   {
      return listbox.getFirstVisibleIndex();
   }

   public ListModel getModel()
   {
      return listbox.getModel();
   }

   public int getLastVisibleIndex()
   {
      return listbox.getLastVisibleIndex();
   }

   public int getMinSelectionIndex()
   {
      return listbox.getMinSelectionIndex();
   }

   public Object getPrototypeCellValue()
   {
      return listbox.getPrototypeCellValue();
   }

   public boolean isSelectionEmpty()
   {
      return listbox.isSelectionEmpty();
   }

   public void setCellRenderer(ListCellRenderer cellRenderer)
   {
      listbox.setCellRenderer(cellRenderer);
   }

   public void setPrototypeCellValue(Object prototypeCellValue)
   {
      listbox.setPrototypeCellValue(prototypeCellValue);
   }

   public void setForeground(Color fg)
   {
      super.setForeground(fg);
      if (listbox != null)
      { listbox.setForeground(fg); }
   }

   public void setFont(Font f)
   {
      super.setFont(f);
      if (listbox != null)
      { listbox.setFont(f); }
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/ListBox.java-arc  $
//
//     Rev 1.1   Nov 25 2002 15:20:20   mshoemake
//  Update to the listener model.
//
//     Rev 1.0   Aug 23 2002 14:45:46   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:28:24   mshoemake
//  Initial revision.
//
//     Rev 1.4   May 21 2002 16:10:18   mshoemake
//  Relocated ListItem factories to baseclasses.factories.
//
//     Rev 1.3   Apr 22 2002 15:37:30   mshoemake
//  Update to setReadOnly method.
//
//     Rev 1.0   Dec 17 2001 11:05:32   mshoemake
//  Initial revision.


