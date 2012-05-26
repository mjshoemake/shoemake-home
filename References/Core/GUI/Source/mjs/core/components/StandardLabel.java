//
// file: WSStandardLabel.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/StandardLabel.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.2  $
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


/**
 * WSStandardLabel:  This is the base class for Witness Systems standard labels.  It extends JLabel.
 */
public class StandardLabel extends JLabel implements Component, Label
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
   private Vector vctListeners = new Vector();

   /**
    * The string representation of the name of the class.  This is used in debug messages not intended for the customer. <p>
    * NOTE:  The string returned by this method is not internationalized and should only be used in conjunction with:
    * 1.  WSMessageLog 2.  WSExceptionFactory
    */
   private String sClassTitle = "WSERLabel";

   /**
    * Allow a border to be displayed or not?
    */
   private boolean bShowBorder = true;

   /**
    * The index of this component in the create order.
    */
   private int nCreateOrder = 0;

   /**
    * The component to which this label belongs.  This is used to establish a connection between an editable control and
    * the label that modifies it.
    */
   private Component wsComp = null;

   /**
    * The border layout used for this component.
    */
   private BorderLayout borderLayout1 = new BorderLayout();

   /**
    * Constructor.
    */
   public StandardLabel()
   {
      try
      {
         // Generate an ID.
         nObjectID = generateID();
         lblBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Constructor.
    * @param  pOjectId      The ID to use for this object (rather than allowing the class to autogenerate an ID).
    */
   public StandardLabel(long pObjectId)
   {
      try
      {
         // Set ObjectID.
         setObjectID(pObjectId);
         lblBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   // Initialization (called by constructor).
   private void lblBaseInit() throws Exception
   {
      this.setLayout(borderLayout1);
//      LookAndFeel.installBorder(this, "Label.border");
//      LookAndFeel.installColorsAndFont(this, "Label.background", "Label.foreground", "Label.font");
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
    * The string representation of the name of the class.  This is used in debug messages not intended for the customer. <p>
    * NOTE:  The string returned by this method is not internationalized and should only be used in conjunction with: <pre>
   * 1.  WSMessageLog
   * 2.  WSExceptionFactory
   * </pre>
    */
   public String getClassName()
   {
      return sClassTitle;
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
    * Delete this component.
    */
   public void delete()
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
      // Remove from parent.
      java.awt.Container contParent = this.getParent();
      if (contParent != null)
      { contParent.remove(this); }
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
    * Allow the label to have it's own border? (Default is yes)
    */
   public boolean isBorderVisible()
   {
      return bShowBorder;
   }

   /**
    * Allow the label to have it's own border? (Default is yes)
    */
   public void setBorderVisible(boolean value)
   {
      bShowBorder = value;
      if (!value)
      {
         // Clear the border.
         super.setBorder(null);
      }
   }

   /**
    * The border for this component. <p> This method overrides the setBorder() method to only set the border when we are in
    * FormGen mode.  Labels in eval mode should not have line borders.
    */
   public void setBorder(Border border)
   {
      if (isBorderVisible())
      {
         super.setBorder(border);
      }
      else
      {
         super.setBorder(null);
      }
   }

   /**
    * The component to which this label belongs.  This is used to establish a connection between an editable control and
    * the label that modifies it.
    */
   public Component getRelatedComponent()
   {
      return wsComp;
   }

   /**
    * The component to which this label belongs.  This is used to establish a connection between an editable control and
    * the label that modifies it.
    */
   public void setRelatedComponent(Component component)
   {
      wsComp = component;
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
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/StandardLabel.java-arc  $
//
//     Rev 1.2   Nov 25 2002 15:20:46   mshoemake
//  Update to the listener model.
//
//     Rev 1.1   Oct 11 2002 08:54:30   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:44:50   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:14   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:05:34   mshoemake
//  Initial revision.


