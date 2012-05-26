//
// file: WSPopupMenu.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/PopupMenu.java-arc  $
// $Author:Mike Shoemake$
// $Revision:8$
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
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;


/**
 * This is the base class for Witness Systems Popup Menu.  This class is designed to
 * minimize the use of frequently needed listeners (ActionListener, etc.) as well
 * as add commonly needed flexibility to minimize reinventing the wheel. <p> Sample usage: <p> <pre>
* <code>
* WSPopupMenu pmMenu = new WSPopupMenu();
*
* // *** In the Constructor of the derived WSPopupMenu class...
* // Add menu items.
* pmMenu.add("Add",      0);
* pmMenu.add("Delete",   1);
* pmMenu.add("Rename",   2);
*
* // *** Further down in the derived WSPopupMenu class...
* public void  event_OnMenuItemClicked (ActionEvent  e,
*                                       String       sDisplayText,
*                                       int          nObjectID,
*                                       Component    invoker)
* {
*    // Perform some action when the menu item is clicked.
*    // NOTE:  If "Delete" was clicked:
*    //           sDisplayText  =  "Delete"
*    //           nObjectID     =  "1"
*    //           invoker       =  the component that invoked the popup menu.
* }
* </code>
* </pre>
 */
public abstract class PopupMenu extends JPopupMenu implements Component
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
    * The component that invoked the menu.
    */
   private java.awt.Component comp = null;

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
    * Constructor.
    */
   public PopupMenu()
   {
      this(generateID());
   }

   /**
    * Constructor.
    * @param  pObjectId     The ID to use for this object (rather than allowing the class to autogenerate an ID).
    */
   public PopupMenu(long pObjectID)
   {
      try
      {
         // Generate an ID.
         nObjectID = pObjectID;
         pmBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   private void pmBaseInit() throws Exception
   {
      this.setLayout(null);
      setFont(SystemDefaults.getDefaultFont());
      setBackground(SystemDefaults.getDefaultNonEditBackground());
      setForeground(SystemDefaults.getDefaultForeground());
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
    * Delete this component.
    */
   public void delete()
   {
      // EVENT
      event_OnDelete();
      removeAllListeners();
      // Remove from parent.
      getParent().remove(this);
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
    * Called before the menu is displayed.
    */
   public void event_OnShow(java.awt.Component invoker) { }

   /**
    * Called by the selected menu item.
    */
   public abstract void event_OnMenuItemClicked
   (ActionEvent e, String sDisplayText, long nObjectID, java.awt.Component invoker);

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
   // ****** Add method (Overloaded). ******

   /**
    * Add this menuitem to the menu.
    * @param   sDisplayText   The display text of the menu item.
    */
   public JMenuItem add(String sDisplayText)
   {
      MenuItem item = new MenuItem(this, sDisplayText);
      super.add((JMenuItem)item);
      return (JMenuItem)item;
   }

   /**
    * Add this menuitem to the menu.
    * @param   sDisplayText   The display text of the menu item.
    * @param   nObjectID      The int ID associated with the menu item.
    */
   public MenuItem add(String sDisplayText, int nObjectID)
   {
      MenuItem item = new MenuItem(this, sDisplayText, nObjectID);
      super.add((JMenuItem)item);
      return item;
   }

   /**
    * Add this menuitem to the menu.
    * @param  menuitem    The menu item to add.
    */
   public MenuItem add(MenuItem menuitem)
   {
      super.add((JMenuItem)menuitem);
      return menuitem;
   }

   /**
    * Add this menuitem to the menu.
    * @param  menuitem    The menu item to add.  Only WSMenuItem is allowed.
    */
   public JMenuItem add(JMenuItem menuitem)
   {
      if (menuitem instanceof MenuItem)
      {
         // Ok to add.
         super.add(menuitem);
         return menuitem;
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10430,
                                                       "Balance",
                                                       "Evaluations",
                                                       "",
                                                       Message.INTERNAL,
                                                       InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                       mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSPopupMenu" );
         return null;
      }
   }

   /**
    * Add this menuitem to the menu.
    * @param  comp    The menu item to add.  Only WSMenuItem is allowed.
    */
   public Component add(Component comp)
   {
      if (comp instanceof MenuItem)
      {
         // Ok to add.
         super.add((JMenuItem)comp);
         return comp;
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10430,
                                                       "Balance",
                                                       "Evaluations",
                                                       "",
                                                       Message.INTERNAL,
                                                       InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                       mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSPopupMenu" );
         return null;
      }
   }

   /**
    * Add this menuitem to the menu.
    * @param  comp    The menu item to add.  Only WSMenuItem is allowed.
    * @param  index   The location to insert the menu item.
    */
   public java.awt.Component add(java.awt.Component comp, int index)
   {
      if (comp instanceof MenuItem)
      {
         // Ok to add.
         super.add(comp, index);
         return comp;
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10430,
                                                       "Balance",
                                                       "Evaluations",
                                                       "",
                                                       Message.INTERNAL,
                                                       InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                       mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSPopupMenu" );
         return null;
      }
   }

   /**
    * Add this menuitem to the menu.
    * @param  comp          The menu item to add.  Only WSMenuItem is allowed.
    * @param  constraints   Constraints object (see JMenuItem).
    */
   public void add(java.awt.Component comp, Object constraints)
   {
      if (comp instanceof MenuItem)
      {
         // Ok to add.
         super.add(comp, constraints);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10430,
                                                       "Balance",
                                                       "Evaluations",
                                                       "",
                                                       Message.INTERNAL,
                                                       InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                       mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSPopupMenu" );
      }
   }

   /**
    * Add this menuitem to the menu.
    * @param  comp          The menu item to add.  Only WSMenuItem is allowed.
    * @param  constraints   Constraints object (see JMenuItem).
    * @param  index         The location to insert the menu item.
    */
   public void add(java.awt.Component comp, Object constraints, int index)
   {
      if (comp instanceof MenuItem)
      {
         // Ok to add.
         super.add(comp, constraints, index);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10430,
                                                       "Balance",
                                                       "Evaluations",
                                                       "",
                                                       Message.INTERNAL,
                                                       InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                       mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSPopupMenu" );
      }
   }

   /**
    * Add this menuitem to the menu. NOTE:  This version of the add method is currently NOT supported.
    */
   public Component add(String name, Component comp)
   {
      // Create message object
      Message message = MessageFactory.createMessage( 10430,
                                                    "Balance",
                                                    "Evaluations",
                                                    "",
                                                    Message.INTERNAL,
                                                    InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                    mjs.core.utils.ComponentUtils.getLocale() );

      // Display message
      MessageDialogHandler.showMessage( message, null, "WSPopupMenu" );
      return null;
   }

   /**
    * Add this menuitem to the menu. NOTE:  This version of the add method is currently NOT supported.
    */
   public JMenuItem add(Action a)
   {
      // Create message object
      Message message = MessageFactory.createMessage( 10430,
                                                    "Balance",
                                                    "Evaluations",
                                                    "",
                                                    Message.INTERNAL,
                                                    InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                    mjs.core.utils.ComponentUtils.getLocale() );

      // Display message
      MessageDialogHandler.showMessage( message, null, "WSPopupMenu" );
      return null;
   }

   /**
    * Return the list of menu items.
    */
   public Vector getMenuItemList()
   {
      java.awt.Component[] complist = super.getComponents();
      Vector vctList = new Vector();

      /* getComponents returns an array.  Convert to a Vector and
         return to calling method.  */

      for (int C = 0; C <= complist.length - 1; C++)
      {
         java.awt.Component comp = complist[C];
         vctList.add((Object)comp);
      }
      return vctList;
   }

   /**
    * Display the menu.
    */
   public void show(java.awt.Component invoker, MouseEvent e)
   {
      comp = invoker;
      event_OnShow(invoker);
      show(invoker, e.getX(), e.getY());
      this.setVisible(true);
   }

   /**
    * Display the menu.
    */
   public void show(java.awt.Component invoker, int x, int y)
   {
      comp = invoker;
      event_OnShow(invoker);
      super.show(invoker, x, y);
      this.setVisible(true);
   }

   /**
    * Display the menu.
    */
   public void show(java.awt.Component invoker)
   {
      comp = invoker;
      event_OnShow(invoker);
      super.show(invoker, 0, 0);
      this.setVisible(true);
   }

   /**
    * The most recent invoking component for this popup menu.  This gets populated when
    * the show() method is called.  It can be null depending on which version of the show() method is used.
    */
   public java.awt.Component getInvokingComponent()
   {
      return comp;
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
   // ****** Listeners *******

   /**
    * Remove all listeners that have been added to this component.
    */
   public void removeAllListeners()
   {
      // Remove all listeners.
      for (int C = 0; C <= vctListeners.size() - 1; C++)
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
    * <p>
    * This method returns true if the listener was not already in the list, otherwise
    * it returns false.
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
   // ****** Additional WSComponent methods ******

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
//  8    Balance   1.7         6/6/2003 8:40:19 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  7    Balance   1.6         3/7/2003 9:28:15 AM    Mike Shoemake   Removed
//       reference to forms.utils.ComponentUtils.  Replaced with
//       core.utils.ComponentUtils.  Not sure yet why the same method is in
//       both classes.
//  6    Balance   1.5         1/29/2003 4:47:13 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  5    Balance   1.4         1/17/2003 8:51:03 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  4    Balance   1.3         12/19/2002 2:01:39 PM  Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  3    Balance   1.2         11/25/2002 3:20:44 PM  Mike Shoemake   Update to
//       the listener model.
//  2    Balance   1.1         10/11/2002 8:54:28 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:44:50 PM   Mike Shoemake
// $

//
//     Rev 1.2   Nov 25 2002 15:20:44   mshoemake
//  Update to the listener model.
//
//     Rev 1.1   Oct 11 2002 08:54:28   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:44:50   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:12   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:05:32   mshoemake
//  Initial revision.


