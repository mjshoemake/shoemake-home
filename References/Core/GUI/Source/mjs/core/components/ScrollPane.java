//
// file: WSScrollPane.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/ScrollPane.java-arc  $
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
import java.lang.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
// Witness imports
import mjs.core.utils.ArrayUtils;
import mjs.core.administration.SystemDefaults;
import mjs.core.types.EnumerationTypeOwner;
import mjs.core.types.EnumerationType;
import mjs.core.types.FontPropegationStyle;
import mjs.core.types.ScrollBarType;
import mjs.core.types.BorderType;


/**
 * WSScrollPane:  This is the base class for Witness Systems selection panels.
 * This panel does not have built in scroll functionality.  If that is needed use the WSScrollPanel instead. <p>
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
public class ScrollPane extends JScrollPane implements Component, Container, EnumerationTypeOwner
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
    * Family name of associated font.
    */
   private String sFontFamilyName = "";

   /**
    * The list of listeners that have been added to the control (maintained internally).
    * It is used to automatically remove all added listeners from the control when the delete() method is called.
    */
   protected Vector vctListeners = new Vector();

   /**
    * Should the popup menu display to the user when the component is right clicked?
    */
   private boolean bPopupMenuEnabled = true;

   /**
    * The popup menu to display when the component is right clicked.
    */
   private PopupMenu wsMenu = null;

   /**
    * The layout to use for the button.
    */
   private BorderLayout borderLayout1 = new BorderLayout();

   /**
    * The border object for this component.
    */
   private FlexibleBorder wsBorder = new FlexibleBorder(this);

   /**
    * The border object for this component.
    */
   private Border bdrExternal = null;

   /**
    * Whether or not scrollbars will appear in a WSScrollPane.
    */
   private ScrollBarType wsScrollBarStyle = new ScrollBarType ();

   /**
    * Whether or not to propegate the new font down to the child components.
    */
   private FontPropegationStyle wsFontPropStyle = new FontPropegationStyle();

   /**
    * The index of this component in the create order.
    */
   private int nCreateOrder = 0;

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
   public ScrollPane()
   {
      this(generateID());
      setBorder(null);
   }

   /**
    * Constructor.
    * @param   pButtonId   The unique, numeric ID used to distinguish the component from other components.
    */
   public ScrollPane(long pObjectId)
   {
      try
      {
         setObjectID(pObjectId);
         // Inialize component.
         pnlBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Initialize the component.
    */
   private void pnlBaseInit() throws Exception
   {
      wsScrollBarStyle.setOwner(this);
      setWidth(100);
      setHeight(100);
      setFont(SystemDefaults.getDefaultFont());
      setBackground(SystemDefaults.getDefaultNonEditBackground());
      setForeground(SystemDefaults.getDefaultForeground());
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
      // Remove from parent.
      java.awt.Container contParent = this.getParent();
      if (contParent != null)
      { contParent.remove(this); }
   }

   /**
    * The text to be displayed in a titled border if TitleBorderVisible is turned on.
    */
   public void setBorderText(String text)
   {
      wsBorder.setTitleText(text);
      super.setBorder(wsBorder.createBorder(this, bdrExternal));
   }

   /**
    * The text to be displayed in a titled border if TitleBorderVisible is turned on.
    */
   public String getBorderText()
   {
      return wsBorder.getTitleText();
   }

   /**
    * The border for the component.
    */
   public void setBorder(Border border)
   {
      bdrExternal = border;
      if (wsBorder != null)
      { super.setBorder(wsBorder.createBorder(this, bdrExternal)); }
      else { super.setBorder(border); }
   }

   /**
    * The border for the component.
    */
   public Border getExternalBorder()
   {
      return bdrExternal;
   }

   /**
    * The internal border for the component.
    */
   public FlexibleBorder getBorderManager()
   {
      return wsBorder;
   }

   /**
    * Update the border when the font or foreground changes.  Also handles font propegation.
    */
   public void setFont(Font font)
   {
      // set the font of this section for future reference
      super.setFont(font);
      // Propegate this font down to the child components.
      if (wsFontPropStyle != null)
      { propegateFont(font); }
      if (wsBorder != null)
      { super.setBorder(wsBorder.createBorder(this, bdrExternal)); }
   }

   /**
    * Propegate the new font of this component down to the child components.
    */
   public void propegateFont(Font f)
   {
      Font currFont = this.getFont();
      Vector children = getComponentsList();
      // If no children or propegation set to never, just exit.
      if ((children == null) || (getFontPropegationStyle().getValue() == FontPropegationStyle.FONT_PROP_NEVER))
      { return; }
      // now set the font of all the section's controls
      for (int C = 0; C <= children.size() - 1; C++)
      {
         // get the next scheme
         Component comp = (Component)(children.get(C));
         // If child's font equals my original font, set new font....
         if (((currFont.equals(comp.getFont())) && (getFontPropegationStyle().getValue() == FontPropegationStyle.FONT_PROP_CONDITIONAL)) ||
         (getFontPropegationStyle().getValue() == FontPropegationStyle.FONT_PROP_ALWAYS))
         {
            // Component's current font is the same as the old font.  Ok to change.
            comp.setFont(f);
         }
      }
   }

   /**
    * Whether or not to propegate the new font down to the child components.
    */
   public FontPropegationStyle getFontPropegationStyle()
   {
      return wsFontPropStyle;
   }

   /**
    * Whether or not to propegate the new font down to the child components.
    */
   public Vector getComponentsList()
   {
      return ArrayUtils.arrayToVector(getComponents());
   }

   /**
    * Update the border when the font or foreground changes.
    */
   public void setForeground(Color fg)
   {
      super.setForeground(fg);
      if (wsBorder != null)
      { super.setBorder(wsBorder.createBorder(this, bdrExternal)); }
   }

   /**
    * Whether or not scrollbars will appear in a WSScrollPane.
    */
   public ScrollBarType getScrollBarStyle()
   {
      return wsScrollBarStyle;
   }

   /**
    * Called by an enumeration type when the value changes.
    * @param   newValue    An object of type WSEnumerationType that contains the new value.
    */
   public void event_OnEnumerationValueChanged(EnumerationType newValue)
   {
      if (newValue instanceof ScrollBarType)
      {
         int nValue = Integer.parseInt(Long.toString(newValue.getValue()));
         switch (nValue)
         {
            case ScrollBarType.SCROLL_BARS_AS_NEEDED:
               {
                  setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                  setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                  updateUI();
                  break;
               }
            case ScrollBarType.SCROLL_BARS_NEVER:
               {
                  setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                  setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                  updateUI();
                  break;
               }
            case ScrollBarType.SCROLL_BARS_ALWAYS:
               {
                  setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                  setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                  updateUI();
                  break;
               }
         }
      }
      if (newValue instanceof BorderType)
      {
         // Border has changed.  Update the physical border of the component.
         super.setBorder(wsBorder.createBorder(this, bdrExternal));
      }
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
         // HierarchyBoundsListener
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
         // HierarchyBoundsListener
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
    * The name associated with the object if applicable.  If the parent
    * of this component is a WSTabbedPane, the tab text changes to match the new name.
    */
   public void setObjectName(String value)
   {
      sObjectName = value;
      // If parent is TabbedPane, set tab captions.
      if (getParent() instanceof TabbedPane)
      {
         TabbedPane tabs = (TabbedPane)(getParent());
         int nIndex = tabs.indexOfComponent((java.awt.Component) this);
         tabs.setTitleAt(nIndex, value);
      }
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
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/ScrollPane.java-arc  $
//
//     Rev 1.3   Nov 25 2002 15:20:46   mshoemake
//  Update to the listener model.
//
//     Rev 1.2   Oct 11 2002 08:54:30   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.1   Sep 18 2002 17:06:14   mshoemake
//  Set background to default non-editable background in mjs.core.administration.SystemDefaults.
//
//     Rev 1.0   Aug 23 2002 14:44:50   mshoemake
//  Initial revision.
//
//     Rev 1.1   Jun 24 2002 13:23:32   mshoemake
//  Renamed some classes.  This is the end of the package and classname refactoring effort for build 20.
//
//     Rev 1.0   Jun 20 2002 17:27:14   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:05:34   mshoemake
//  Initial revision.


