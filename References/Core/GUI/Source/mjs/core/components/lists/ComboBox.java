//
// file: WSComboBox.java
// desc:
// proj: eQuality 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/ComboBox.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.1  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
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
import mjs.core.administration.SystemDefaults;
import mjs.core.factories.ListItemFactory;
import mjs.core.factories.StringListItemFactory;
import mjs.core.components.Component;
import mjs.core.components.ComponentOwner;
import mjs.core.components.IDGenerator;
import mjs.core.components.PopupMenu;


/**
 * WSComboBox:  This is the base class for Witness Systems comboboxes. <p>
 * Several methods were added to the JComponent functionality (setLeft, setTop,
 * etc.) to make the interface more consistent (JComponent had a getWidth but no setWidth, etc.). <p>
 * Several empty event methods have been made public to allow the developer to
 * perform some action when the event occurs without having to add their own
 * listeners to the component.  To handle the events simply override the event
 * handler methods and implement the desired functionality.  Not all of the events
 * are triggered by listeners.  Some, like OnDelete, are called when the delete() method is called. <code>
 * <p>   public void event_OnDelete() <p>   public void event_OnSelectionChanged (ActionEvent e)
 * <p>   public void event_OnMouseClicked (MouseEvent e) <p>   public void event_OnMousePressed (MouseEvent e)
 * <p>   public void event_OnMouseReleased (MouseEvent e) <p>   public void event_OnMouseEntered (MouseEvent e)
 * <p>   public void event_OnMouseExited (MouseEvent e)
 * <p>   public void event_BeforeShowPopupMenu (Vector vctMenuItemList) </code>
 */
public class ComboBox extends JComboBox implements Component
{
   /**
    * An integer object ID.  This can be either specified in the constructor, set
    * using the set method, or the baseclass will auto generate a number that is unique for the class.
    */
   private long nObjectID = 0;

   /**
    * A user defined object type.  This can be used in conjunction with integer
    * constants defined by the developer.  It is not used internally for anything and is very flexible.
    */
   private int nObjectType = 0;

   /**
    * The name associated with the object if applicable.
    */
   private String sObjectName = "";

   /**
    * The list of listeners that have been added to the control (maintained internally).
    * It is used to automatically remove all added listeners from the control when the delete() method is called.
    */
   private Vector vctListeners = new Vector();

   /**
    * The index of this component in the create order.
    */
   private int nCreateOrder = 0;

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
   //   Removed.  This should be replaced by setSelectedItem() and getSelectedItem() methods.
   //   private WSListItem wsCurrentValue = null;

   /**
    * Should the popup menu display to the user when the component is right clicked?
    */
   private boolean bPopupMenuEnabled = true;

   /**
    * Should the dropdown picklist be accessible by the user (default is yes)?
    * This was mainly implemented for FormGen so the dropdown picklist could be suppressed under certain circumstances.
    */
   private boolean bAllowPicklist = true;

   /**
    * Should the popup menu display to the user when the component is right clicked?
    */
   private boolean bResettingDefault = false;

   /**
    * Is the picklist currently being displayed?
    */
   //   private boolean   bShowingPicklist = false;

   /**
    * The popup menu to display when the component is right clicked.
    */
   private PopupMenu wsMenu = null;

   /**
    * The layout to use for the combobox.
    */
   private BorderLayout borderLayout1 = new BorderLayout();

   /**
    * The model for the JComboBox component.
    */
   private DefaultComboBoxModel combomodel = new DefaultComboBoxModel();

   /**
    * A string representation of the class title.
    */
   private String sClassTitle = "WSComboBox";

   /**
    * The List Item Factory for this WSList component.  This factory creates the items
    * to be displayed in a way that the underlying sort functionality can understand and manipulate.
    */
   private StringListItemFactory itemFactory = new StringListItemFactory();

   /**
    * The list of items.  All sort functionality is embedded in the WSSortedVector.
    */
   private SortedList itemList = new SortedList(false, (ListItemFactory)itemFactory);

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
    * The default action listener for this component.  This listener triggers the event_OnButtonClicked() event.
    */
   private ActionListener lAction = new java.awt.event.ActionListener()
   {
      public void actionPerformed(ActionEvent e)
      {
         default_actionPerformed(e);
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
    * The default item listener for this component.  This listener triggers the event_OnMousePressed() event, etc.
    */
   private MouseListener lMouse = new java.awt.event.MouseListener()
   {
      public void mousePressed(MouseEvent e)
      {
         default_mousePressed(e);
      }
      public void mouseReleased(MouseEvent e)
      {
         default_mouseReleased(e);
      }
      public void mouseClicked(MouseEvent e)
      {
         event_OnMouseClicked(e);
      }
      public void mouseEntered(MouseEvent e)
      {
         event_OnMouseEntered(e);
      }
      public void mouseExited(MouseEvent e)
      {
         event_OnMouseExited(e);
      }
   };

   /**
    * Constructor.
    */
   public ComboBox()
   {
      this(generateID());
   }

   /**
    * Constructor.
    * @param   pObjectId   The unique, numeric ID used to distinguish the component from other components.
    */
   public ComboBox(long pObjectId)
   {
      try
      {
         setObjectID(pObjectId);
         // Inialize component.
         cbBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Initialize the component.
    */
   private void cbBaseInit() throws Exception
   {
      // Set the default values for this component.
      setComponentDefaults();
      super.setModel(combomodel);
      setPopupVisible(false);
      // Add default listeners
      addActionListener(lAction);
      addMouseListener(lMouse);
   }

   /**
    * Set the default dimensions, font, and color of this component.  This method is called by the constructor.
    */
   private void setComponentDefaults()
   {
      setLeft(Component.DEFAULT_LEFT);
      setTop(Component.DEFAULT_TOP);
      setHeight(Component.DEFAULT_HEIGHT);
      setWidth(Component.DEFAULT_WIDTH);
      setReadOnly(false);
      setFont(SystemDefaults.getDefaultFont());
      setBackground(SystemDefaults.getDefaultNonEditBackground());
      setForeground(SystemDefaults.getDefaultForeground());
   }

   /**
    * This method does nothing but return a reference to itself.  It's useless.  Unfortunately,
    * someone is using it somewhere so we can't just remove it.  Need to phase it out ASAP.
    */
   public ComboBox getInput()
   {
      return this;
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
    * Should the dropdown picklist be accessible by the user (default is yes)?
    * This was mainly implemented for FormGen so the dropdown picklist could be suppressed under certain circumstances.
    */
   public void setPicklistAllowed(boolean value)
   {
      bAllowPicklist = value;
   }

   /**
    * Should the dropdown picklist be accessible by the user (default is yes)?
    * This was mainly implemented for FormGen so the dropdown picklist could be suppressed under certain circumstances.
    */
   public boolean isPicklistAllowed()
   {
      return bAllowPicklist;
   }

   /**
    * Set whether or not this component is Read Only to the user.
    */
   public void setReadOnly(boolean value)
   {
      bReadOnly = value;
      if (getSelectedItem() != null)
      {
         // Default to the currently selected value.
         setDefaultValue((ListItem)(getSelectedItem()));
      }
      else if (getItemList().size() > 0)
      {
         // Default to the first item in the list.
         setDefaultValue((ListItem)getItemAt(0));
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
      // Change the selection.
      //   Removed.  This should be replaced by setSelectedItem() and getSelectedItem() methods.
      //      wsCurrentValue = value;
      setSelectedItem((Object)value);
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
   //   Removed.  This should be replaced by setSelectedItem() and getSelectedItem() methods.
   //   private void setCurrentValue (WSListItem value)
   //   {
   //      wsCurrentValue = value;
   //      this.setSelectedItem((Object)value);
   //   }

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
      else if (getSelectedItem() == null)
      {
         // Default found.  Make the default current.
         nCommand = CMD_DEFAULT_TO_SAVED_ITEM;
      }
      else if (wsDefaultValue.toString().equals(getSelectedItem().toString()))
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
               // Do nothing.
               setSelectedItem(getDefaultValue());
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

   /**
    * Generate a unique ID with respect to this class for this component.
    * ie.  Each object of this type will have an ID that sets it apart from the other objects.
    */
   private static long generateID()
   {
      return IDGenerator.generateID();
   }
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * Triggered by ActionPerformed listener.
    */
   public void event_OnSelectionChanged(ActionEvent e) { }

   /**
    * Triggered by ItemStateChanged event of Item listener.
    */
   public void event_OnItemStateChanged(ItemEvent e) { }

   /**
    * Triggered by MouseListener.
    */
   public void event_OnMouseClicked(MouseEvent e) { }

   /**
    * Triggered by MouseListener.
    */
   public void event_OnMousePressed(MouseEvent e) { }

   /**
    * Triggered by MouseListener.
    */
   public void event_OnMouseReleased(MouseEvent e) { }

   /**
    * Triggered by MouseListener.
    */
   public void event_OnMouseEntered(MouseEvent e) { }

   /**
    * Triggered by MouseListener.
    */
   public void event_OnMouseExited(MouseEvent e) { }

   /**
    * Called by the delete() method before any processing takes place.
    */
   public void event_OnDelete() { }

   /**
    * Called by the showPopupMenu() method before any processing takes place.
    * It passes the list of menu items as a parameter to allow the event handler to enable or disable the buttons as needed.
    * @param  vctMenuItemList    The list of menu items for this popup menu.
    */
   public void event_BeforeShowPopupMenu(Vector vctMenuItemList) { }
   // ****** Functionality methods ******

   /**
    * Delete this component.
    */
   public void delete()
   {
      // EVENT
      event_OnDelete();
      // Remove all listeners.
      for (int C = 0; C <= vctListeners.size() - 1; C++)
      {
         // Remove the next listener.
         EventListener lNext = (EventListener)(vctListeners.get(C));
         this.removeListener(lNext);
      }
      // Clear the listener list.
      vctListeners.removeAllElements();
      itemList.removeAllElements();
      removeAllElements();
      // Remove from parent.
      Container contParent = this.getParent();
      if (contParent != null)
      { contParent.remove(this); }
   }

   /**
    * The popup menu to display when the component is right clicked.
    */
   public void setPopupMenu(PopupMenu menu)
   {
      wsMenu = menu;
   }

   /**
    * Get the list of menu items from the popup menu.  This can be used to enable/disable or hide/show menu items.
    */
   public Vector getPopupMenuItems()
   {
      return wsMenu.getMenuItemList();
   }

   /**
    * Display the current popup menu.
    */
   public void showPopupMenu()
   {
      if (isPopupMenuEnabled())
      {
         // EVENT.
         event_BeforeShowPopupMenu(getPopupMenuItems());
         wsMenu.show(this, 0, 0);
      }
   }

   /**
    * Is it ok to display the popup menu?
    */
   public boolean isPopupMenuEnabled()
   {
      return bPopupMenuEnabled;
   }

   /**
    * Is it ok to display the popup menu?
    */
   public void setPopupMenuEnabled(boolean value)
   {
      bPopupMenuEnabled = value;
   }
   // ****** Listener methods ******
   // LISTENER EVENT - ActionPerformed

   /**
    * Handles selection change notification, Read Only, etc.
    */
   public void default_actionPerformed(ActionEvent e)
   {
      //      hidePopup();
      //      bShowingPicklist = false;
      if (!bResettingDefault)
      {
         bResettingDefault = true;
         if (isReadOnly())
         {
            if (getDefaultValue() != getSelectedItem())
            {
               // Change the answer back to the default.
               preventChanges();
            }
         }
         else
         {
            // Selection changed.
            event_OnSelectionChanged(e);
            if (wsOwner != null)
            {
               wsOwner.notifyOfAction(this, 0);
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
            // Selection changed.
            event_OnItemStateChanged(e);
         }
         bResettingDefault = false;
      }
   }

   /**
    * Toggles the visibility of the picklist.  Normally this is handled automatically by the
    * JComboBox but the built-in listeners have forced manual maintenance of the picklist.
    */

/*
   private void togglePicklist()
   {
      // Show or suppress picklist.
      if (bAllowPicklist)
      {
         bShowingPicklist = ! bShowingPicklist;
         setPopupVisible(bShowingPicklist);
      }
      else
      {
         hidePopup();
      }
   }
*/

   /**
    * Hides popup if necessary.
    */
   public void default_mousePressed(MouseEvent e)
   {
      if (!isPicklistAllowed())
      { hidePopup(); }
      event_OnMousePressed(e);
      if (!isPicklistAllowed())
      { hidePopup(); }
   }

   /**
    * Handles PopupMenu.
    */
   public void default_mouseReleased(MouseEvent e)
   {
      Container parent = getParent();
      if (!isPicklistAllowed())
      { hidePopup(); }
      if (parent != null)
      {
         // EVENT
         event_OnMouseReleased(e);
         if (SwingUtilities.isRightMouseButton(e))
         {
            if ((wsMenu != null) && isPopupMenuEnabled())
            {
               // Display popup menu.
               wsMenu.show((java.awt.Component) (e.getSource()), e);
            }
         }
      }
      if (!isPicklistAllowed())
      { hidePopup(); }
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
      if ((combomodel != null) && (itemList != null))
      {
         combomodel.removeAllElements();
         for (int C = 0; C <= itemList.size() - 1; C++)
         {
            ListItem item = (ListItem)(itemList.get(C));
            // Add the items in sorted order.
            combomodel.addElement((Object)(itemList.get(C)));
         }
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
    * Add item to the list.
    * @param     item    The WSListItem to add.  An object of any type that implements the WSListItem interface is acceptable.
    */
   public void add(ListItem item)
   {
      itemList.add(item);
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
    * Remove this list item.
    * @param    item    An object that has implemented the WSListItem interface.
    */
   public void remove(ListItem item)
   {
      itemList.remove(item);
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

   /**
    * A Vector containing all of the listeners currently added to this component.
    */
   public Vector getListenerList()
   {
      return (Vector)(vctListeners.clone());
   }

   /**
    * Remove all listeners that have been added to this component.
    */
   public void removeAllListeners()
   {
      /* Remove all listeners.  Count backwards instead of forwards because we are
         removing from the Vector as we go.  */

      for (int C = vctListeners.size() - 1; C >= 0; C--)
      {
         // Remove the next listener.
         EventListener lNext = (EventListener)(vctListeners.get(C));
         this.removeListener(lNext);
      }
      // Clear the listener list.
      vctListeners.removeAllElements();
   }

   /**
    * Add this listener to the listener list.  This allows easy removal of all
    * listeners by using the removeAllListeners() method, which also gets called
    * when inside the delete() method.
    * <p>
    * This method is published so extending classes are able to add their own
    * custom listeners to the list.  These methods (add and remove) must be used
    * for the addListener() and removeListener() generic methods to work properly.
    */
   public boolean addToListenerList(EventListener l)
   {
      if (vctListeners == null)
      {
         // Create the listener list if it doesn't exist.
         vctListeners = new Vector();
      }
      if (! vctListeners.contains(l))
      {
         // Add the listener to the list if it's not already there.
         vctListeners.add(l);
         return true;
      }
      return false;
   }

   /**
    * Removes this listener from the listener list.
    * <p>
    * This method is published so extending classes are able to add and remove
    * their own custom listeners.  These methods (add and remove) must be used
    * for the addListener() and removeListener() generic methods to work properly.
    */
   public void removeFromListenerList(EventListener l)
   {
      vctListeners.remove(l);
   }

   // LISTENERS
   public void addActionListener(ActionListener l)
   {
      if (addToListenerList(l))
         super.addActionListener(l);
   }

   public void addAncestorListener(AncestorListener l)
   {
      if (addToListenerList(l))
         super.addAncestorListener(l);
   }

   public void addComponentListener(ComponentListener l)
   {
      if (addToListenerList(l))
         super.addComponentListener(l);
   }

   public void addContainerListener(ContainerListener l)
   {
      if (addToListenerList(l))
         super.addContainerListener(l);
   }

   public void addFocusListener(FocusListener l)
   {
      if (addToListenerList(l))
      {
         // Add listener to child components
         java.awt.Component[] complist = getComponents();
         super.addFocusListener(l);
         for (int C = 0; C <= complist.length - 1; C++)
         {
            java.awt.Component jComp = complist[C];
            jComp.addFocusListener(l);
         }
      }
   }

   public void addHierarchyBoundsListener(HierarchyBoundsListener l)
   {
      if (addToListenerList(l))
         super.addHierarchyBoundsListener(l);
   }

   public void addHierarchyListener(HierarchyListener l)
   {
      if (addToListenerList(l))
         super.addHierarchyListener(l);
   }

   public void addInputMethodListener(InputMethodListener l)
   {
      if (addToListenerList(l))
         super.addInputMethodListener(l);
   }

   public void addItemListener(ItemListener l)
   {
      if (addToListenerList(l))
         super.addItemListener(l);
   }

   public void addKeyListener(KeyListener l)
   {
      if (addToListenerList(l))
      {
         // Add listener to child components
         java.awt.Component[] complist = getComponents();
         super.addKeyListener(l);
         for (int C = 0; C <= complist.length - 1; C++)
         {
            java.awt.Component jComp = complist[C];
            jComp.addKeyListener(l);
         }
      }
   }

   public void addMouseListener(MouseListener l)
   {
      if (addToListenerList(l))
      {
         java.awt.Component[] complist = getComponents();
         super.addMouseListener(l);
         super.removeMouseListener(lMouse);
         super.addMouseListener(lMouse);
         for (int C = 0; C <= complist.length - 1; C++)
         {
            java.awt.Component jComp = complist[C];

            /* Only add to the CellRendererPane.  That will automatically add
               to the button.  */

            if (jComp instanceof CellRendererPane)
            {
               jComp.addMouseListener(l);
            }
            // Remove and readd the built-in mouse listener.
            jComp.removeMouseListener(lMouse);
            jComp.addMouseListener(lMouse);
         }
      }
   }

   public void setEditor(ComboBoxEditor anEditor)
   {
      super.setEditor(anEditor);
      // Add all mouse listeners to the new editor.
      for (int C = 0; C <= vctListeners.size() - 1; C++)
      {
         if (vctListeners.get(C)instanceof MouseListener)
         {
            MouseListener l = (MouseListener)(vctListeners.get(C));
            anEditor.getEditorComponent().addMouseListener(l);
         }
      }
   }

   public void addMouseMotionListener(MouseMotionListener l)
   {
      if (addToListenerList(l))
      {
         java.awt.Component[] complist = getComponents();
         super.addMouseMotionListener(l);
         for (int C = 0; C <= complist.length - 1; C++)
         {
            java.awt.Component jComp = complist[C];
            jComp.addMouseMotionListener(l);
         }
      }
   }

   public void addPropertyChangeListener(PropertyChangeListener l)
   {
      if (addToListenerList(l))
         super.addPropertyChangeListener(l);
   }

   public void addVetoableChangeListener(VetoableChangeListener l)
   {
      if (addToListenerList(l))
         super.addVetoableChangeListener(l);
   }

   public void removeActionListener(ActionListener l)
   {
      super.removeActionListener(l);
      removeFromListenerList(l);
   }

   public void removeAncestorListener(AncestorListener l)
   {
      super.removeAncestorListener(l);
      removeFromListenerList(l);
   }

   public void removeComponentListener(ComponentListener l)
   {
      super.removeComponentListener(l);
      removeFromListenerList(l);
   }

   public void removeContainerListener(ContainerListener l)
   {
      super.removeContainerListener(l);
      removeFromListenerList(l);
   }

   public void removeFocusListener(FocusListener l)
   {
      super.removeFocusListener(l);
      removeFromListenerList(l);
   }

   public void removeHierarchyBoundsListener(HierarchyBoundsListener l)
   {
      super.removeHierarchyBoundsListener(l);
      removeFromListenerList(l);
   }

   public void removeHierarchyListener(HierarchyListener l)
   {
      super.removeHierarchyListener(l);
      removeFromListenerList(l);
   }

   public void removeInputMethodListener(InputMethodListener l)
   {
      super.removeInputMethodListener(l);
      removeFromListenerList(l);
   }

   public void removeItemListener(ItemListener l)
   {
      super.removeItemListener(l);
      removeFromListenerList(l);
   }

   public void removeKeyListener(KeyListener l)
   {
      super.removeKeyListener(l);
      removeFromListenerList(l);
   }

   public void removeMouseListener(MouseListener l)
   {
      super.removeMouseListener(l);
      removeFromListenerList(l);
   }

   public void removeMouseMotionListener(MouseMotionListener l)
   {
      super.removeMouseMotionListener(l);
      removeFromListenerList(l);
   }

   public void removePropertyChangeListener(PropertyChangeListener l)
   {
      super.removePropertyChangeListener(l);
      removeFromListenerList(l);
   }

   public void removeVetoableChangeListener(VetoableChangeListener l)
   {
      super.removeVetoableChangeListener(l);
      removeFromListenerList(l);
   }


   // *****  Methods from WSIObject interface.  *****

   /**
    * Adds the event listener to the component.  This method is very generic
    * and allows listeners to be added without having to find out the
    * listener type.  The addListener method calls the correct method for the listener type.
    */
   public void addListener(EventListener listener)
   {
      if (listener instanceof ActionListener)
      {
         // ActionListener
         addActionListener((ActionListener)listener);
      }
      else if (listener instanceof AncestorListener)
      {
         // AncestorListener
         addAncestorListener((AncestorListener)listener);
      }
      else if (listener instanceof ComponentListener)
      {
         // ComponentListener
         addComponentListener((ComponentListener)listener);
      }
      else if (listener instanceof ContainerListener)
      {
         // ContainerListener
         addContainerListener((ContainerListener)listener);
      }
      else if (listener instanceof FocusListener)
      {
         // FocusListener
         addFocusListener((FocusListener)listener);
      }
      else if (listener instanceof HierarchyBoundsListener)
      {
         // HierarchyListener
         addHierarchyBoundsListener((HierarchyBoundsListener)listener);
      }
      else if (listener instanceof HierarchyListener)
      {
         // HierarchyListener
         addHierarchyListener((HierarchyListener)listener);
      }
      else if (listener instanceof InputMethodListener)
      {
         // InputMethodListener
         addInputMethodListener((InputMethodListener)listener);
      }
      else if (listener instanceof ItemListener)
      {
         // ItemListener
         addItemListener((ItemListener)listener);
      }
      else if (listener instanceof KeyListener)
      {
         // KeyListener
         addKeyListener((KeyListener)listener);
      }
      else if (listener instanceof MouseListener)
      {
         // MouseListener
         addMouseListener((MouseListener)listener);
      }
      else if (listener instanceof MouseMotionListener)
      {
         // MouseMotionListener
         addMouseMotionListener((MouseMotionListener)listener);
      }
      else if (listener instanceof PropertyChangeListener)
      {
         // PropertyChangeListener
         addPropertyChangeListener((PropertyChangeListener)listener);
      }
      else if (listener instanceof VetoableChangeListener)
      {
         // VetoableChangeListener
         addVetoableChangeListener((VetoableChangeListener)listener);
      }
   }

   /**
    * Removes the event listener to the component.  This method is very generic
    * and allows listeners to be removed without having to find out the
    * listener type.  The removeListener method calls the correct method for the listener type.
    */
   public void removeListener(EventListener listener)
   {
      if (listener instanceof AncestorListener)
      {
         // AncestorListener
         removeAncestorListener((AncestorListener)listener);
      }
      else if (listener instanceof ComponentListener)
      {
         // ComponentListener
         removeComponentListener((ComponentListener)listener);
      }
      else if (listener instanceof ContainerListener)
      {
         // ContainerListener
         removeContainerListener((ContainerListener)listener);
      }
      else if (listener instanceof FocusListener)
      {
         // FocusListener
         removeFocusListener((FocusListener)listener);
      }
      else if (listener instanceof HierarchyBoundsListener)
      {
         // HierarchyListener
         removeHierarchyBoundsListener((HierarchyBoundsListener)listener);
      }
      else if (listener instanceof HierarchyListener)
      {
         // HierarchyListener
         removeHierarchyListener((HierarchyListener)listener);
      }
      else if (listener instanceof InputMethodListener)
      {
         // InputMethodListener
         removeInputMethodListener((InputMethodListener)listener);
      }
      else if (listener instanceof KeyListener)
      {
         // KeyListener
         removeKeyListener((KeyListener)listener);
      }
      else if (listener instanceof MouseListener)
      {
         // MouseListener
         removeMouseListener((MouseListener)listener);
      }
      else if (listener instanceof MouseMotionListener)
      {
         // MouseMotionListener
         removeMouseMotionListener((MouseMotionListener)listener);
      }
      else if (listener instanceof PropertyChangeListener)
      {
         // PropertyChangeListener
         removePropertyChangeListener((PropertyChangeListener)listener);
      }
      else if (listener instanceof VetoableChangeListener)
      {
         // VetoableChangeListener
         removeVetoableChangeListener((VetoableChangeListener)listener);
      }
   }

   /**
    * The integer object ID.  This can be either specified in the constructor, set
    * using the set method, or the baseclass will auto generate a number that is unique for the class.
    */
   public long getObjectID()
   {
      return nObjectID;
   }

   /**
    * The integer object ID.  This can be either specified in the constructor, set
    * using the set method, or the baseclass will auto generate a number that is unique for the class.
    */
   public void setObjectID(long value)
   {
      nObjectID = value;
   }

   /**
    * A user defined object type.  This can be used in conjunction with integer
    * constants defined by the developer.  It is not used internally for anything and is very flexible.
    */
   public int getObjectType()
   {
      return nObjectType;
   }

   /**
    * A user defined object type.  This can be used in conjunction with integer
    * constants defined by the developer.  It is not used internally for anything and is very flexible.
    */
   public void setObjectType(int value)
   {
      nObjectType = value;
   }

   /**
    * The name associated with the object if applicable.
    */
   public String getObjectName()
   {
      return sObjectName;
   }

   /**
    * The name associated with the object if applicable.
    */
   public void setObjectName(String value)
   {
      sObjectName = value;
   }

   public int getLeft()
   {
      return super.getX();
   }

   public int getTop()
   {
      return super.getY();
   }

   public void setLeft(int left)
   {
      super.setBounds(left, super.getY(), super.getWidth(), super.getHeight());
   }

   public void setTop(int top)
   {
      super.setBounds(super.getX(), top, super.getWidth(), super.getHeight());
   }

   public void setWidth(int width)
   {
      super.setBounds(super.getX(), super.getY(), width, super.getHeight());
   }

   public void setHeight(int height)
   {
      super.setBounds(super.getX(), super.getY(), super.getWidth(), height);
   }

   // Miscellaneous Component methods
   public void setMaximumSize(int width, int height)
   {
      super.setMaximumSize(new Dimension(width, height));
   }

   public void setMinimumSize(int width, int height)
   {
      super.setMinimumSize(new Dimension(width, height));
   }

   public void setPreferredSize(int width, int height)
   {
      super.setPreferredSize(new Dimension(width, height));
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/ComboBox.java-arc  $
//
//     Rev 1.1   Nov 25 2002 15:20:18   mshoemake
//  Update to the listener model.
//
//     Rev 1.0   Aug 23 2002 14:45:44   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:28:24   mshoemake
//  Initial revision.
//
//     Rev 1.9   May 21 2002 16:10:18   mshoemake
//  Relocated ListItem factories to baseclasses.factories.
//
//     Rev 1.8   Apr 25 2002 09:06:52   mshoemake
//  Fixed default and current value stuff.
//
//     Rev 1.7   Apr 22 2002 15:51:00   mshoemake
//  Update to setReadOnly method.
//
//     Rev 1.5   Apr 17 2002 09:11:22   mshoemake
//  Added WSListItemFactory interface to import statement.
//
//     Rev 1.3   Apr 16 2002 13:55:50   hfaynzilberg
//  Fixed #12669
//  Contact Display popup menu doesn't work initially for new evaluations.
//
//     Rev 1.2   Apr 16 2002 13:29:36   mshoemake
//  Added ReadOnly toggle button to the baseclass test applet.
//
//     Rev 1.1   Apr 12 2002 14:10:10   mshoemake
//  Added addActionListener and addItemListener to the addListener() method.
//
//     Rev 1.0   Dec 17 2001 11:05:30   mshoemake
//  Initial revision.


