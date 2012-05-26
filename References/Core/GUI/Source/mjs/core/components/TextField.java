//
// file: WSTextField.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/TextField.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.2  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
////////////////////////////////////////////////////////////////////////////

package mjs.core.components;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import mjs.core.administration.SystemDefaults;


/**
 * This is the base class for Witness Systems EditBox. <p> Several empty event methods have been made public to allow the
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
public class TextField extends JTextField implements Component
{
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

   /**
    * The index of this component in the create order.
    */
   private int nCreateOrder = 0;

   /**
    * Should the popup menu display to the user when the component is right clicked?
    */
   private boolean bPopupMenuEnabled = true;

   /**
    * This is the background set using the setBackground() method.  If this is null then
    * the background will depend on whether it's read only or not.
    */
   private Color updatedBackground = null;

   /**
    * The popup menu to display when the component is right clicked.
    */
   private PopupMenu wsMenu = null;
   // ****** Default listeners ******

   /**
    * The default mouse listener for this component.  This listener triggers the event_OnMousePressed() event, etc.
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
    * Constructor.
    */
   public TextField()
   {
      try
      {
         // Generate an ID.
         nObjectID = generateID();
         tfBaseInit();
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
   public TextField(long pObjectId)
   {
      try
      {
         setObjectID(pObjectId);
         // Inialize component.
         tfBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   // Initialization (called by constructor).
   private void tfBaseInit() throws Exception
   {
      // Set Default Values.
      setWidth(Component.DEFAULT_WIDTH);
      setHeight(Component.DEFAULT_HEIGHT);
      setReadOnly(false);
      setFont(SystemDefaults.getDefaultFont());
      // Background set in setReadOnly() method.
      setForeground(SystemDefaults.getDefaultForeground());
      // Add default listeners.
      addMouseListener(lMouse);
   }

   /**
    * Generate a unique ID with respect to this class for this component.
    * ie.  Each object of this type will have an ID that sets it apart from the other objects.
    */
   private static long generateID()
   {
      return IDGenerator.generateID();
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
    * Set whether or not this checkbox is read-only to the user.
    */
   public void setReadOnly(boolean value)
   {
      setEditable(!value);
   }

   /**
    * If editable then show edit background.  Otherwise show non-edit background.
    * If the background property has been explicitly set after the creation of
    * the component then that background will be used.  Otherwise is uses the
    * defaults specified in the SystemDefaults class.
    */
   public void setEditable(boolean b)
   {
      super.setEditable(b);
      if (updatedBackground == null)
      {
         if (b)
         {
            // Editable.
            super.setBackground(SystemDefaults.getDefaultEditBackground());
         }
         else
         {
            // Not editable.
            super.setBackground(SystemDefaults.getDefaultNonEditBackground());
         }
      }
   }

   /**
    * The background color of the component.
    * @param c
    */
   public void setBackground(Color bg)
   {
      updatedBackground = bg;
      super.setBackground(bg);
   }

   /**
    * Is this checkbox read-only to the user?
    */
   public boolean isReadOnly()
   {
      return !isEditable();
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
   // LISTENER EVENT - MouseReleased

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
      if (listener instanceof AncestorListener)
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
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/TextField.java-arc  $
//
//     Rev 1.2   Nov 25 2002 15:21:02   mshoemake
//  Update to the listener model.
//
//     Rev 1.1   Oct 11 2002 08:54:34   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:44:54   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:18   mshoemake
//  Initial revision.
//
//     Rev 1.1   Mar 25 2002 09:51:36   mshoemake
//  Fix to javadoc comments.
//
//     Rev 1.0   Dec 17 2001 11:05:36   mshoemake
//  Initial revision.


