//
// file: WSButton.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/Button.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.3  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components;

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
import mjs.core.events.ButtonEvent;
import mjs.core.events.ButtonListener;
import mjs.core.events.ButtonListenerList;
import mjs.core.types.EnumerationType;
import mjs.core.types.EnumerationTypeOwner;
import mjs.core.types.HorizontalLocationType;


/**
 * WSButton:  This is the base class for Witness Systems Buttons. <p>
 * In order for the parent of the button to be notified when the button is
 * clicked the parent must implement the WSButtonOwner interface. <p>
 * Several empty event methods have been made public to allow the developer to
 * perform some action when the event occurs without having to add their own
 * listeners to the component.  To handle the events simply override the event
 * handler methods and implement the desired functionality.  Not all of the events
 * are triggered by listeners.  Some, like OnDelete, are called when the delete() method is called. <code>
 * <p>   public void event_OnDelete() <p>   public void event_OnButtonClicked (ActionEvent e)
 * <p>   public void event_OnMouseClicked (MouseEvent e) <p>   public void event_OnMousePressed (MouseEvent e)
 * <p>   public void event_OnMouseReleased (MouseEvent e) <p>   public void event_OnMouseEntered (MouseEvent e)
 * <p>   public void event_OnMouseExited (MouseEvent e) <p>   public void event_BeforeShowPopupMenu (Vector vctMenuItemList)
 * <p>   public void event_OnEnumerationValueChanged (WSEnumerationType newValue) </code>
 */
public class Button extends JButton implements Component, EnumerationTypeOwner
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
    * Should the popup menu display to the user when the component is right clicked?
    */
   private boolean bPopupMenuEnabled = true;

   /**
    * The popup menu to display when the component is right clicked.
    */
   private PopupMenu wsMenu = null;

   /**
    * The next ID to use when a new object of this type is created.
    */
   private static int nIDGenerator = 10000;

   /**
    * The list of button listeners to notify when a button click occurs.
    */
   private ButtonListenerList buttonListenerList = new ButtonListenerList();

   /**
    * The index of this component in the create order.
    */
   private int nCreateOrder = 0;

   /**
    * The layout to use for the button.
    */
   private BorderLayout borderLayout1 = new BorderLayout();

   /**
    * The area of the panel (Left, Right, Center) in which to place a component.
    */
   private HorizontalLocationType wsLocationType = new HorizontalLocationType(HorizontalLocationType.CENTER, this);

   /**
    * The default width of the component.
    */
   public static final int DEFAULT_WIDTH = 67;

   /**
    * The default height of the component.
    */
   public static final int DEFAULT_HEIGHT = 27;

   /**
    * The size of the button to use when performing horizontal or vertical button alignment.  See WSDialogButtonPanel.  If
    * value is less than 1, it is ignored and the default width and height is used.
    */
   private int nAlignButtonSize = -1;

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
    * Constructor.
    */
   public Button()
   {
      this(generateID());
   }

   /**
    * Constructor.
    * @param   pButtonId   The unique, numeric ID used to distinguish the component from other components.
    */
   public Button(long pObjectId)
   {
      try
      {
         setObjectID(pObjectId);
         // Inialize component.
         btnBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Initialize the component.
    */
   private void btnBaseInit() throws Exception
   {
      setLayout(borderLayout1);
      setFont(SystemDefaults.getDefaultFont());
      setBackground(SystemDefaults.getDefaultNonEditBackground());
      setForeground(SystemDefaults.getDefaultForeground());
      setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
      setMargin(new Insets(2, 5, 2, 5));
      addActionListener(lAction);
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
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * Triggered by ActionPerformed listener.
    */
   public void event_OnButtonClicked(ActionEvent e) { }

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
    * It passes the list of menu items as a parameter to allow the event handler to enable
    * @param  vctMenuItemList    The list of menu items for this popup menu.
    */
   public void event_BeforeShowPopupMenu(Vector vctMenuItemList) { }

   /**
    * Called by an enumeration type when the value changes.  This is a WSEnumerationTypeOwner interface-related event.
    * @param   newValue    An object of type WSEnumerationType that contains the new value.
    */
   public void event_OnEnumerationValueChanged(EnumerationType newValue) { }
   // ****** Functionality methods ******

   /**
    * Delete this component.
    */
   public void delete()
   {
      // EVENT
      event_OnDelete();
      // Remove all listeners.
      if (vctListeners.size() != 0)
      {
         for (int C = 0; C <= vctListeners.size() - 1; C++)
         {
            // Remove the next listener.
            EventListener lNext = (EventListener)(vctListeners.get(C));
            this.removeListener(lNext);
         }
      }
      // Clear the listener list.
      vctListeners.removeAllElements();
      // Remove from parent.
      java.awt.Container contParent = this.getParent();
      contParent.remove(this);
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
    * Get the Location Type object.
    */
   public HorizontalLocationType getHorizontalLocationType()
   {
      return this.wsLocationType;
   }

   /**
    * The size of the button to use when performing horizontal or vertical button alignment.  See WSDialogButtonPanel.  If
    * value is less than 1, it is ignored and the default width and height is used.
    * @param   nSize   The width or height of the button.  The user may need to force a width and override the default.  If
    * alignment is horizontal, nSize is width, otherwise it is height.
    */
   public void setAlignmentButtonSize(int nButtonSize)
   {
      nAlignButtonSize = nButtonSize;
   }

   /**
    * The size of the button to use when performing horizontal or vertical button alignment.  See WSDialogButtonPanel.  If
    * value is less than 1, it is ignored and the default width and height is used.
    */
   public int getAlignmentButtonSize()
   {
      return nAlignButtonSize;
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
      if (wsMenu != null)
      {
         return wsMenu.getMenuItemList();
      }
      else
      {
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
         if (wsMenu != null)
         {
            wsMenu.show(this, 0, 0);
         }
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
   // ****** Listener methods ******
   // LISTENER EVENT - ActionPerformed

   /**
    * The order of events in this method are as follows: <p> <pre>
   * 1.  OnButtonClicked event of this button.
   * 2.  OnButtonClicked of all ButtonListener objects.
   * </pre>
    */
   private void default_actionPerformed(ActionEvent e)
   {
      // EVENT.
      event_OnButtonClicked(e);
      // Notification.
      ButtonEvent event = new ButtonEvent(this, getObjectID(), getText());
      buttonListenerList.buttonClicked(event);
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

      if (vctListeners.size() != 0)
      {
         for (int C = vctListeners.size() - 1; C >= 0; C--)
         {
            // Remove the next listener.
            EventListener lNext = (EventListener)(vctListeners.get(C));
            this.removeListener(lNext);
         }
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
   public void addButtonListener(ButtonListener l)
   {
      if (addToListenerList(l))
         buttonListenerList.add(l);
   }

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

   public void removeButtonListener(ButtonListener l)
   {
      buttonListenerList.remove(l);
      removeFromListenerList(l);
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
      vctListeners.remove(l);
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
      if (listener instanceof ButtonListener)
      {
         // ButtonListener (Witness custom listener)
         addButtonListener((ButtonListener)listener);
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
      else if (listener instanceof ButtonListener)
      {
         // ButtonListener (Witness custom listener)
         removeButtonListener((ButtonListener)listener);
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
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/Button.java-arc  $
//
//     Rev 1.3   Nov 25 2002 15:20:40   mshoemake
//  Update to the listener model.
//
//     Rev 1.2   Nov 25 2002 09:57:20   mshoemake
//  Modified the listener model to make it more consistent with what Java does and to make it easier to use.  Also phased out ButtonOwner interface.
//
//     Rev 1.1   Sep 18 2002 17:06:12   mshoemake
//  Set background to default non-editable background in mjs.core.administration.SystemDefaults.
//
//     Rev 1.0   Aug 23 2002 14:44:38   mshoemake
//  Initial revision.
//
//     Rev 1.2   Jul 29 2002 09:05:46   mshoemake
//  Rolling back to revision 1.0.
//
//     Rev 1.0   Jun 20 2002 17:27:02   mshoemake
//  Initial revision.
//
//     Rev 1.4   Apr 16 2002 17:32:02   mshoemake
//  Update to import statements.
//
//     Rev 1.3   Apr 11 2002 19:44:28   mshoemake
//  Added getListenerList() method.
//
//     Rev 1.2   Mar 05 2002 15:03:56   sputtagunta
//  added  check for null pointer exceptions
//
//     Rev 1.1   Dec 17 2001 09:15:44   mshoemake
//  Updated PVCS tags.


