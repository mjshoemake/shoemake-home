//
// file: WSCheckBox.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/CheckBox.java-arc  $
// $Author:Mike Shoemake$
// $Revision:7$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import mjs.core.administration.SystemDefaults;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.strings.SystemStrings;
import mjs.core.utils.ColorFactory;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;


/**
 * This is the base class for Witness Systems CheckBox. <p> Several empty event methods have been made public to allow the
 * developer to perform some action when the event occurs without having to add their own
 * listeners to the component.  To handle the events simply override the event
 * handler methods and implement the desired functionality.  Not all of the events
 * are triggered by listeners.  Some, like OnDelete, are called when the delete() method is called. <code>
 * <p>   public void event_OnDelete()                           {}
 * <p>   public void event_OnItemStateChange (ItemEvent e)      {}
 * <p>   public void event_OnSelectionChanged (ActionEvent e)   {}
 * <p>   public void event_OnMouseClicked (MouseEvent e)        {}
 * <p>   public void event_OnMousePressed (MouseEvent e)        {}
 * <p>   public void event_OnMouseReleased (MouseEvent e)       {}
 * <p>   public void event_OnMouseEntered (MouseEvent e)        {}
 * <p>   public void event_OnMouseExited (MouseEvent e)         {} </code>
 */
public class CheckBox extends JCheckBox implements Component
{
   /**
    * The text associated with a true selected value in the checkbox ("Checked").
    */
   public static final String TEXT_CHECKED = InternationalizationStrings.szChecked;

   /**
    * The text associated with a false selected value in the checkbox ("Unchecked").
    */
   public static final String TEXT_UNCHECKED = InternationalizationStrings.szUnchecked;
   // ****** WSComponent variables ******

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
   private Vector vctListeners = null;
   // Other member variables

   /**
    * If readonly is true then the selected value cannot be changed.
    */
   private boolean bReadOnly = true;

   /**
    * The default value for the checkbox (checked or unchecked).  This is used to allow
    * the developer to reset the checkbox to the original value if necessary.
    */
   private boolean bDefaultValue = true;

   /**
    * The current value for the checkbox (checked or unchecked).  This is REQUIRED to handle
    * the readonly funtionality of the checkbox.
    */
   //   Removed.  This should be replaced by setSelected() and isSelected() methods.
   //   private boolean   bCurrentValue = true;

   /**
    * Should the popup menu display to the user when the component is right clicked?
    */
   private boolean bPopupMenuEnabled = true;

   /**
    * The index of this component in the create order.
    */
   private int nCreateOrder = 0;

   /**
    * The popup menu to display when the component is right clicked.
    */
   private PopupMenu wsMenu = null;

   /**
    * The component or object that owns this object.  This is used to notify the owner when something changes.
    */
   private ComponentOwner wsOwner = null;
   private boolean bExecutingActionPerformed = false;
   // ****** Default listeners ******

   /**
    * The default action listener for this component.  This listener triggers the event_OnSelectionChanged() event.
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
         event_OnMousePressed(e);
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
    * The next ID to use when a new object of this type is created.
    */
   private static int nIDGenerator = 10000;

   /**
    * Constructor.
    */
   public CheckBox()
   {
      try
      {
         // Generate an ID.
         nObjectID = generateID();
         chkBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Constructor.
    * @param  pButtonId     The ID to use for this object (rather than allowing the class to autogenerate an ID).
    */
   public CheckBox(long pObjectId)
   {
      try
      {
         setObjectID(pObjectId);
         // Inialize component.
         chkBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   // Initialization (called by constructor).
   private void chkBaseInit() throws Exception
   {
      // Set Default Values.
      setWidth(Component.DEFAULT_WIDTH);
      setHeight(Component.DEFAULT_HEIGHT);
      setFont(SystemDefaults.getDefaultFont());
      setBackground(SystemDefaults.getDefaultNonEditBackground());
      setForeground(SystemDefaults.getDefaultForeground());
      setSelected(getDefaultValue());
      setBorderPainted(true);
      // Add default listeners.
      addActionListener(lAction);
      addItemListener(lItem);
      addMouseListener(lMouse);
      setBorder(BorderFactory.createLineBorder(ColorFactory.black, 1));
   }

   /**
    * Generate a unique ID with respect to this class for this component.
    * ie.  Each object of this type will have an ID that sets it apart from the other objects.
    */
   private static long generateID()
   {
      return IDGenerator.generateID();
   }
   // ****** Font ******

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
    * Called by the delete() method before any processing takes place.
    */
   public void event_OnDelete() { }

   /**
    * Called by the showPopupMenu() method before any processing takes place.
    * It passes the list of menu items as a parameter to allow the event handler to enable
    * @param  vctMenuItemList    The list of menu items for this popup menu.
    */
   public void event_BeforeShowPopupMenu(Vector vctMenuItemList) { }

   /**
    * Triggered by ItemListener.
    */
   public void event_OnItemStateChange(ItemEvent e) { }

   /**
    * Triggered by ActionPerformed listener.
    */
   public void event_OnSelectionChanged(ActionEvent e) { }

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
   // ****** Functionality methods ******

   /**
    * Delete this component.
    */
   public void delete()
   {
      // EVENT
      event_OnDelete();
      removeAllListeners();
      // Remove from parent.
      java.awt.Container contParent = this.getParent();
      if (contParent != null)
      { contParent.remove(this); }
   }

   /**
    * This is a compatibility method to give a similar interface to WSComboBox and WSRadioButton. <p>
    * If toString() of the object returns either "checked", "true", "yes", or "on" then
    * the checkbox is set to TRUE.  Otherwise it is FALSE.  This check is case insensitive.
    */
   public void setSelectedItem(Object anObject)
   {
      String sText = anObject.toString();
      if (sText.equalsIgnoreCase("true") || sText.equalsIgnoreCase(TEXT_CHECKED) || sText.equalsIgnoreCase("yes") ||
      sText.equalsIgnoreCase("on"))
      {
         this.setSelected(true);
      }
      else
      {
         this.setSelected(false);
      }
   }

   /**
    * This updates the picklist when changes occur.  This is a compatibility method only. For the WSCheckBox, it does nothing.
    */
   public void refreshItemList()
   {
      // This is a compatibility method only.
   }

   /**
    * Set whether or not this checkbox is read-only to the user.
    */
   public void setReadOnly(boolean value)
   {
      bReadOnly = value;
      setDefaultValue(isSelected());
   }

   /**
    * Is this checkbox read-only to the user?
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
    * The default value for the checkbox (checked or unchecked).  This is used to allow
    * the developer to reset the checkbox to the original value if necessary.
    */
   public void setDefaultValue(boolean value)
   {
      bDefaultValue = value;
      if (isSelected() != bDefaultValue)
      { setSelected(value); }
   }

   /**
    * The default value for the checkbox (checked or unchecked).  This is used to allow
    * the developer to reset the checkbox to the original value if necessary.
    */
   public boolean getDefaultValue()
   {
      return bDefaultValue;
   }

   /**
    * The default value for the checkbox (checked or unchecked).  This is used to allow
    * the developer to reset the checkbox to the original value if necessary.
    */
   //   Removed.  This should be replaced by setSelected() and isSelected() methods.
   //   public void setCurrentValue (boolean value)
   //   {
   //      bCurrentValue = value;
   //      setSelected(value);
   //   }

   /**
    * Reset the current value of the checkbox to the default value.
    */
   public void resetToDefaultValue()
   {
      if (isSelected() != bDefaultValue)
      { setSelected(bDefaultValue); }
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
      if (wsMenu.getMenuItemList() != null)
      {
         return wsMenu.getMenuItemList();
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10420,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSCheckBox" );
         return new Vector();
      }
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
   // LISTENER EVENT - actionPerformed

   /**
    * Handles ReadOnly property.
    */
   private void default_actionPerformed(ActionEvent e)
   {
      java.awt.Container parent = getParent();
      if (parent != null)
      {
         bExecutingActionPerformed = true;
         if (isReadOnly())
         {
            // Change the value back to what it was.
            resetToDefaultValue();
         }
         else
         {
            // Remember the new value.
            //bCurrentValue = isSelected();
            // EVENT.   Value just changed.
            event_OnSelectionChanged(e);
            if (wsOwner != null)
            {
               wsOwner.notifyOfAction(this, 0);
            }
         }
         bExecutingActionPerformed = false;
      }
   }
   // LISTENER EVENT - itemStateChanged

   /**
    * Handles ReadOnly property (alternate event).
    */
   public void default_itemStateChanged(ItemEvent e)
   {
      java.awt.Container parent = getParent();
      if (parent != null)
      {
         // EVENT
         event_OnItemStateChange(e);
         if (!bExecutingActionPerformed)
         {
            // Automatically call the action performed method.
            default_actionPerformed(new ActionEvent(this, 0, SystemStrings.szEVE_StateChange));
         }
      }
   }
   // LISTENER EVENT - MousePressed

   /**
    * Handles PopupMenu.
    */
   public void default_mouseReleased(MouseEvent e)
   {
      java.awt.Container parent = getParent();
      if (parent != null)
      {
         // EVENT
         event_OnMouseReleased(e);
         if (SwingUtilities.isRightMouseButton(e))
         {
            if ((wsMenu != null) && isPopupMenuEnabled())
            {
               // EVENT
               event_BeforeShowPopupMenu(getPopupMenuItems());
               // Display popup menu.
               wsMenu.show((java.awt.Component) this, e);
            }
         }
      }
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
      vctListeners.add(l);
      return true;
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

   public void addChangeListener(ChangeListener l)
   {
      if (addToListenerList(l))
         super.addChangeListener(l);
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
         super.addFocusListener(l);
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
         super.addKeyListener(l);
   }

   public void addMouseListener(MouseListener l)
   {
      if (addToListenerList(l))
         super.addMouseListener(l);
   }

   public void addMouseMotionListener(MouseMotionListener l)
   {
      if (addToListenerList(l))
         super.addMouseMotionListener(l);
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

   public void removeChangeListener(ChangeListener l)
   {
      super.removeChangeListener(l);
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
      else if (listener instanceof ChangeListener)
      {
         // ChangeListener
         addChangeListener((ChangeListener)listener);
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
      if (listener instanceof ActionListener)
      {
         // ActionListener
         removeActionListener((ActionListener)listener);
      }
      else if (listener instanceof AncestorListener)
      {
         // AncestorListener
         removeAncestorListener((AncestorListener)listener);
      }
      else if (listener instanceof ChangeListener)
      {
         // ChangeListener
         removeChangeListener((ChangeListener)listener);
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
      else if (listener instanceof ItemListener)
      {
         // ItemListener
         removeItemListener((ItemListener)listener);
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
// $Log:
//  7    Balance   1.6         6/6/2003 8:40:19 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  6    Balance   1.5         1/17/2003 8:50:26 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  5    Balance   1.4         12/20/2002 10:05:03 AM Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  4    Balance   1.3         12/19/2002 1:56:22 PM  Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  3    Balance   1.2         11/25/2002 3:20:42 PM  Mike Shoemake   Update to
//       the listener model.
//  2    Balance   1.1         10/11/2002 8:54:18 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:44:40 PM   Mike Shoemake
// $
//
//     Rev 1.2   Nov 25 2002 15:20:42   mshoemake
//  Update to the listener model.
//
//     Rev 1.1   Oct 11 2002 08:54:18   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:44:40   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:04   mshoemake
//  Initial revision.
//
//     Rev 1.5   May 14 2002 13:34:50   mshoemake
//  Fixed #13042.  Read-Only users see different answers than other users.
//  Added setDefaultValue() call to setReadOnly() method.
//
//     Rev 1.4   Apr 17 2002 08:51:20   mshoemake
//  Updated import statements.
//
//     Rev 1.3   Apr 12 2002 10:17:32   mshoemake
//  Replaced getCurrentValue() with isSelected().
//
//     Rev 1.2   Apr 11 2002 14:41:06   mshoemake
//  Changed setCurrentValue() method from being private to public.
//
//     Rev 1.1   Feb 08 2002 15:41:26   hfaynzilberg
//  added NullPointerException handling
//
//     Rev 1.0   Dec 17 2001 11:05:30   mshoemake
//  Initial revision.


