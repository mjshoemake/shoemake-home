//
// file: WSToolBar.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/ToolBar.java-arc  $
// $Author:Mike Shoemake$
// $Revision:9$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components;

// Java Imports
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
// Witness Imports
import mjs.core.administration.SystemDefaults;
import mjs.core.aggregation.ComponentHashtable;
import mjs.core.events.ButtonEvent;
import mjs.core.events.ButtonListener;
import mjs.core.events.ButtonListenerList;
import mjs.core.events.ChildButtonMouseEvent;
import mjs.core.events.ChildButtonMouseListener;
import mjs.core.events.ChildButtonMouseListenerList;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.types.EnumerationTypeOwner;
import mjs.core.types.EnumerationType;
import mjs.core.utils.ColorFactory;


/**
 * WSToolBar:  This is a JToolBar descendent that is a baseclass for all Witness Systems toolbars.
 */
public class ToolBar extends JToolBar implements Component, ButtonListener, MouseListener, EnumerationTypeOwner
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
    * The list of button listeners to notify when a button click occurs.
    */
   private ButtonListenerList buttonListenerList = new ButtonListenerList();

   /**
    * The list of button listeners to notify when a button click occurs.
    */
   private ChildButtonMouseListenerList childButtonMouseListenerList = new ChildButtonMouseListenerList();

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
    * The list of buttons on the toolbar.
    */
   private ComponentHashtable htButtonList = new ComponentHashtable();

   /**
    * The size (width and height) of the separator.
    */
   private Dimension dSeparator = new Dimension(6, 35);

   /**
    * The size (width and height) of the button.
    */
   private Dimension dButton = new Dimension(35, 35);

   /**
    * Is the shift key held down?
    */
   private boolean bShiftPressed = false;

   /**
    * The border to use when a toggle button is depressed.
    */
   private static Border loweredBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED, ColorFactory.white, ColorFactory.white,
   ColorFactory.darkGray, ColorFactory.darkGray);

   /**
    * The border to use when a toggle button is raised.
    */
   private static Border raisedBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, ColorFactory.white, ColorFactory.darkGray,
   ColorFactory.white, ColorFactory.darkGray);

   /**
    * Constructor.
    */
   public ToolBar()
   {
      this(generateID());
   }

   /**
    * Constructor.
    * @param  pOjectId      The ID to use for this object (rather than allowing the class to autogenerate an ID).
    */
   public ToolBar(long objectID)
   {
      try
      {
         nObjectID = objectID;
         pnlBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Initialization for this object.
    */
   private void pnlBaseInit() throws Exception
   {
      setPreferredSize(new Dimension(300, 50));
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
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * Called by the delete() method before any processing takes place.
    */
   public void event_OnDelete() { }

   /**
    * Called when a button is clicked by the user.  This is a WSButtonOwner interface-related event.  The buttons actually
    * call the buttonClicked() event in order to keep this event method
    * available for developers to use for custom event handlers.
    * @param   e              The action event associated with the button click.
    * @param   sDisplayText   The display text of the button.  For WSToggleButton objects this is the tooltip text.
    * @param   nObjectID      The unique numeric ID associated with the button.
    */
   public void event_OnButtonClicked(ButtonEvent e)
   {
   }

   /**
    * Called by an enumeration type when the value changes.  This is a WSEnumerationTypeOwner interface-related event.
    * @param   newValue    An object of type WSEnumerationType that contains the new value.
    */
   public void event_OnEnumerationValueChanged(EnumerationType newValue) { }

   /**
    * Called by the button when it is clicked by the user.  This method
    * notifies the parent WSTabbedToolBar (if there is one) about the
    * button click event, so the developer could trap the events in the
    * parent WSTabbedToolBar component if desired. <p>
    * It then calls the overridable event method (event_OnButtonClicked).
    * @param   e              The button event associated with the button click.
    */
   public void buttonClicked(ButtonEvent e)
   {
      Object eventSource = e.getSource();
      if (eventSource instanceof ToggleButton)
      {
         ToggleButton button = (ToggleButton)eventSource;
         if (button.isSelected())
         {
            button.setBorder(raisedBorder);
            setAllButtonsDeselected();
            button.setSelected(true);
            event_onButtonSelected(button);
         }
         else
         {

            // Raised border.
            button.setBorder(raisedBorder);
            event_onButtonDeselected(button);
         }
      }
      // EVENT.
      event_OnButtonClicked(e);
      // Notify the listeners.
      buttonListenerList.buttonClicked(e);
   }

   // MOUSE EVENT METHODS
   /**
    * The event method that gets triggered when the mouse is released on a child
    * button.
    * <p>
    * This event is only triggered for ToggleButton objects.
    */
   public void mouseReleased(MouseEvent e)
   {
      setShiftPressed(e.isShiftDown());
      // Notify any listeners.
      ToggleButton button = (ToggleButton)(e.getSource());
      ChildButtonMouseEvent event = new ChildButtonMouseEvent(e, button.getObjectID(), button.getText());
      childButtonMouseListenerList.buttonMouseReleased(event);
   }

   /**
    * The event method that gets triggered when the mouse is pressed on a child
    * button.
    * <p>
    * This event is only triggered for ToggleButton objects.
    */
   public void mousePressed(MouseEvent e)
   {
      setShiftPressed(e.isShiftDown());
      // Notify any listeners.
      ToggleButton button = (ToggleButton)(e.getSource());
      ChildButtonMouseEvent event = new ChildButtonMouseEvent(e, button.getObjectID(), button.getText());
      childButtonMouseListenerList.buttonMousePressed(event);
   }

   /**
    * The event method that gets triggered when the mouse is pressed on a child
    * button.
    * <p>
    * This event is only triggered for ToggleButton objects.
    */
   public void mouseClicked(MouseEvent e)
   {
      // Notify any listeners.
      ToggleButton button = (ToggleButton)(e.getSource());
      ChildButtonMouseEvent event = new ChildButtonMouseEvent(e, button.getObjectID(), button.getText());
      childButtonMouseListenerList.buttonMouseClicked(event);
   }

   /**
    * The event method that gets triggered when the mouse is pressed on a child
    * button.
    * <p>
    * This event is only triggered for ToggleButton objects.
    */
   public void mouseEntered(MouseEvent e)
   {
      // Notify any listeners.
      ToggleButton button = (ToggleButton)(e.getSource());
      ChildButtonMouseEvent event = new ChildButtonMouseEvent(e, button.getObjectID(), button.getText());
      childButtonMouseListenerList.buttonMouseEntered(event);
   }

   /**
    * The event method that gets triggered when the mouse is pressed on a child
    * button.
    * <p>
    * This event is only triggered for ToggleButton objects.
    */
   public void mouseExited(MouseEvent e)
   {
      // Notify any listeners.
      ToggleButton button = (ToggleButton)(e.getSource());
      ChildButtonMouseEvent event = new ChildButtonMouseEvent(e, button.getObjectID(), button.getText());
      childButtonMouseListenerList.buttonMouseExited(event);
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
    * Is the shift key held down?
    */
   public boolean isShiftPressed()
   {
      return bShiftPressed;
   }

   /**
    * Is the shift key held down?
    */
   public void setShiftPressed(boolean value)
   {
      bShiftPressed = value;
   }

   /**
    * Enable or disable all buttons on the toolbar.
    */
   public void setEnabled(boolean enabled)
   {
      super.setEnabled(enabled);
      Enumeration enum = htButtonList.elements();
      while (enum.hasMoreElements())
      {
         Component wsObj = (Component)(enum.nextElement());
         wsObj.setEnabled(enabled);
      }
   }

   /**
    * Deselect all toggle buttons on the toolbar. <p> If a button is not a toggle button then it is ignored.
    */
   public void setAllButtonsDeselected()
   {
      Enumeration enum = htButtonList.elements();
      while (enum.hasMoreElements())
      {
         Component wsObj = (Component)(enum.nextElement());
         if (wsObj instanceof ToggleButton)
         {
            ToggleButton button = (ToggleButton)wsObj;
            button.setSelected(false);
            button.setBorder(raisedBorder);
         }
      }
   }

   /**
    * Deselect this toggle button on the toolbar. <p> If the button is not a toggle button then it is ignored.
    */
   public void setButtonDeselected(Component object)
   {
      if (object instanceof ToggleButton)
      {
         ToggleButton button = (ToggleButton)object;
         button.setSelected(false);
         button.setBorder(raisedBorder);
      }
   }

   /**
    * Enable or disable a specific button.
    */
   public void setEnabled(long buttonID, boolean enabled)
   {
      Component wsObj = (Component)(htButtonList.get(buttonID));
      wsObj.setEnabled(enabled);
   }

   /**
    * Set the border for all toolbar buttons.
    */
   public void setButtonBorder(Border border)
   {
      Enumeration enum = htButtonList.elements();
      while (enum.hasMoreElements())
      {
         Component wsObj = (Component)(enum.nextElement());
         wsObj.setBorder(border);
      }
   }

   /**
    * Set the border for the specified toolbar button.
    */
   public void setButtonBorder(long buttonID, Border border)
   {
      Component wsObj = (Component)(htButtonList.get(buttonID));
      wsObj.setBorder(border);
   }

   /**
    * Set the specified button to be either selected or unselected.
    */
   public void setToggleButtonSelected(long buttonID, boolean selected)
   {
      if (htButtonList.get(buttonID)instanceof ToggleButton)
      {
         ToggleButton wsButton = (ToggleButton)(htButtonList.get(buttonID));
         wsButton.setSelected(selected);
         if (selected)
         {
            setAllButtonsDeselected();
            // Lowered border.
            wsButton.setSelected(true);
            wsButton.setBorder(loweredBorder);
         }
         else
         {
            // Raised border.
            wsButton.setBorder(raisedBorder);
         }
      }
   }

   /**
    * Change the tab color for this component if the parent is a WSTabbedPane.
    */
   public void setTabColor(Color color)
   {
      if (this.getParent() instanceof TabbedPane)
      {
         TabbedPane parent = (TabbedPane)(this.getParent());
         int nIndex = parent.indexOfComponent(this);
         parent.setBackgroundAt(nIndex, color);
      }
   }
   // *****  Non-Interface Methods  *****

   /**
    * Creates a new button and adds it to the panel.  Returns the object ID for the new button.
    * @param   image                The image to display in the button.
    * @param   sToolTip             The tooltip of the button.
    * @param   nObjectID            The unique numeric ID associated with the button.
    */
   public long addButton(ImageIcon image, String sToolTip, long nObjectID)
   {
      // Create and initialize the button.
      Button button = new Button(nObjectID);
      if (image != null)
      { button.setIcon(image); }
      if (sToolTip != null)
      { button.setToolTipText(sToolTip); }
      // Add the new button to the panel.
      button.setMargin(new Insets(0, 0, 0, 0));
      button.setPreferredSize(dButton);
      button.addButtonListener(this);
      add(button);
      // Add new button to hashtable.
      htButtonList.add(nObjectID, button);
      // Redraw the panel.
      updateUI();
      return button.getObjectID();
   }

   /**
    * Creates a new button and adds it to the panel.  Returns the object ID for the new button.
    * @param   image                The image to display in the button.
    * @param   sToolTip             The tooltip of the button.
    * @param   nObjectID            The unique numeric ID associated with the button.
    */
   public long addToggleButton(ImageIcon image, String sToolTip, long nObjectID)
   {
      // Create and initialize the button.
      ToggleButton button = new ToggleButton(nObjectID);
      if (image != null)
      { button.setIcon(image); }
      if (sToolTip != null)
      { button.setToolTipText(sToolTip); }
      // Add the new button to the panel.
      button.setPreferredSize(dButton);
      button.setMargin(new Insets(0, 0, 0, 0));
      button.addButtonListener(this);
      button.addMouseListener(this);
      button.setFocusPainted(false);
      button.setRequestFocusEnabled(false);
      button.setOpaque(true);
      add(button);
      setButtonDeselected(button);
      // Add new button to hashtable.
      htButtonList.add(nObjectID, button);
      // Redraw the panel.
      updateUI();
      return button.getObjectID();
   }

   /**
    * Creates an invisible button and adds it to the panel. <p>
    * This button is intended to allow the all toggle buttons to deselect at once.
    * Without it, one toggle button must be selected at all times.
    */
   public void addInvisibleToggleButton()
   {
      // Create and initialize the button.
      ToggleButton button = new ToggleButton(nObjectID);
      // Add the new button to the panel.
      button.addButtonListener(this);
      button.addMouseListener(this);
      button.setVisible(false);
      add(button);
      // Redraw the panel.
      updateUI();
   }

   /**
    * Add the separator using the default separator width.
    */
   public void addSeparator()
   {
      super.addSeparator(dSeparator);
   }

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
      // Delete all labels.
      Enumeration enum = htButtonList.elements();
      while (enum.hasMoreElements())
      {
         Component wsObj = (Component)(enum.nextElement());
         wsObj.delete();
      }
      // Clear the child list.
      htButtonList.clear();
      // Remove from parent.
      java.awt.Container contParent = this.getParent();
      contParent.remove(this);
   }

   /**
    * Enables or disables the button with the specified object ID.
    */
   public void setEnabled(int nButtonID, boolean enabled)
   {
      Button button = (Button)(htButtonList.get(nButtonID));
      if (button != null)
      {
         button.setEnabled(enabled);
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
         MessageDialogHandler.showMessage( message, null, "ToolBar" );
      }
   }

   /**
    * Enables or disables the button with the specified tooltip text.
    */
   public void setEnabled(String tooltip, boolean enabled)
   {
      Button button = getButtonByToolTipText(tooltip);
      if (button != null)
      {
         button.setEnabled(enabled);
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
         MessageDialogHandler.showMessage( message, null, "ToolBar" );
      }
   }

   /**
    * Retrieve the button with the specified text.
    */
   public Button getButtonByToolTipText(String text)
   {
      Enumeration enum = htButtonList.elements();
      while (enum.hasMoreElements())
      {
         Button button = (Button)(enum.nextElement());
         if (button.getToolTipText().equals(text))
         { return button; }
      }
      return null;
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

   public void addButtonListener(ButtonListener l)
   {
      if (addToListenerList(l))
         buttonListenerList.add(l);
   }

   public void addChildButtonMouseListener(ChildButtonMouseListener l)
   {
      if (addToListenerList(l))
         childButtonMouseListenerList.add(l);
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

   public void removeButtonListener(ButtonListener l)
   {
      buttonListenerList.remove(l);
      removeFromListenerList(l);
   }

   public void removeChildButtonMouseListener(ChildButtonMouseListener l)
   {
      childButtonMouseListenerList.remove(l);
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
      else if (listener instanceof ButtonListener)
      {
         // ButtonListener
         addButtonListener((ButtonListener)listener);
      }
      else if (listener instanceof ChildButtonMouseListener)
      {
         // ChildButtonMouseListener
         addChildButtonMouseListener((ChildButtonMouseListener)listener);
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
      else if (listener instanceof ButtonListener)
      {
         // ButtonListener
         removeButtonListener((ButtonListener)listener);
      }
      else if (listener instanceof ChildButtonMouseListener)
      {
         // ChildButtonMouseListener
         removeChildButtonMouseListener((ChildButtonMouseListener)listener);
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

   /**
    * Called when button is selected.  This method should be overwritten in
    * order to provide specific functionality when toolbar button is selected.
    * @param   button         The WSToggleButton selected by the user
    */
   public void event_onButtonSelected(ToggleButton button) { }

   /**
    * Called when button is deselected.  This method should be overwritten in
    * order to provide specific functionality when toolbar button is deselected.
    * @param   button         The WSToggleButton deselected by the user
    */
   public void event_onButtonDeselected(ToggleButton button) { }


   // TOGETHERSOFT DIAGRAM DEPENDENCIES.  DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# Button lnkButton; */

   /**
    * @link dependency
    */
   /*# ToggleButton lnkToggleButton; */

}
// $Log:
//  9    Balance   1.8         6/6/2003 8:40:20 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  8    Balance   1.7         3/28/2003 2:48:12 PM   Mike Shoemake   Added
//       Together diagram dependencies.
//  7    Balance   1.6         1/17/2003 8:51:19 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  6    Balance   1.5         12/20/2002 10:05:01 AM Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  5    Balance   1.4         12/19/2002 2:58:30 PM  Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  4    Balance   1.3         11/25/2002 3:21:04 PM  Mike Shoemake   Update to
//       the listener model.
//  3    Balance   1.2         11/25/2002 9:57:26 AM  Mike Shoemake   Modified
//       the listener model to make it more consistent with what Java does and
//       to make it easier to use.  Also phased out ButtonOwner interface.
//  2    Balance   1.1         10/11/2002 8:54:36 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:44:56 PM   Mike Shoemake
// $

//
//     Rev 1.3   Nov 25 2002 15:21:04   mshoemake
//  Update to the listener model.
//
//     Rev 1.2   Nov 25 2002 09:57:26   mshoemake
//  Modified the listener model to make it more consistent with what Java does and to make it easier to use.  Also phased out ButtonOwner interface.
//
//     Rev 1.1   Oct 11 2002 08:54:36   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:44:56   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:18   mshoemake
//  Initial revision.
//
//     Rev 1.5   Feb 25 2002 14:58:28   hfaynzilberg
//  Moved Fix for  # 11807 to WSERComponentToolBar.java
//
//     Rev 1.4   Feb 15 2002 16:00:52   hfaynzilberg
//  Fixed #11807 Question Pool toolbar button should raise when the dialog is activated.
//
//     Rev 1.3   Feb 11 2002 16:35:12   hfaynzilberg
//  added NullPointerException handling
//
//     Rev 1.2   Jan 30 2002 08:46:16   hfaynzilberg
//  added some comments and removed unneeded method
//
//     Rev 1.1   Jan 28 2002 13:37:24   hfaynzilberg
//  Added multi-drop functionality when "Shift" Key's pressed.
//
//     Rev 1.0   Dec 17 2001 11:05:36   mshoemake
//  Initial revision.


