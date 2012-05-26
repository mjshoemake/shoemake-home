//
// file: WSRadioButton.java
// desc:
// proj: eQuality 6.3
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/RadioButton.java-arc  $
// $Author:Mike Shoemake$
// $Revision:8$
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
import mjs.core.factories.ListItemFactory;
import mjs.core.factories.RadioButtonListItemFactory;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;
import mjs.core.administration.SystemDefaults;
import mjs.core.types.EnumerationType;
import mjs.core.components.Panel;
import mjs.core.components.Container;
import mjs.core.components.Component;
import mjs.core.types.BorderType;


/**
 * WSRadioButton:  This is the base class for Witness Systems radio buttons.
 * This control inherits from WSPanel.  The radio buttons actually reside on
 * the control.  Whichever button is selected will be accessible by the SelectedItem methods. <p>
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
public class RadioButton extends Panel implements Component
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
   private RadioButtonListItemFactory itemFactory = new RadioButtonListItemFactory();

   /**
    * The list of items.  All sort functionality is embedded in the WSSortedVector.
    */
   private SortedList itemList = new SortedList(false, (ListItemFactory)itemFactory);

   /**
    * The radiobutton group object.
    */
   private ButtonGroup bgradioGroup = new ButtonGroup();

   /**
    * The index of this component in the create order.
    */
   private int nCreateOrder = 0;

   /**
    * The layout to use when displaying the radio buttons.
    */
   private GridLayout gridLayout = new GridLayout();

   /**
    * Is the component currently updating the UI for the item list?
    */
   private boolean bUpdatingUI = false;

   /**
    * Is the radiobutton panel currently resizing?
    */
   private boolean bResizing = false;

   /**
    * The alignment for the radio buttons.
    */
   private int nradioButtonsAlignment = 0;

   /**
    * Default the selected value to the first item in the list. This is the option used when no saved default value is found.
    */
   private static final int CMD_DEFAULT_TO_FIRST_ITEM = 0;

   /**
    * Default the selected value to the saved default value.
    */
   private static final int CMD_DEFAULT_TO_SAVED_ITEM = 1;

   /**
    * A string representation of the class title.
    */
   private String sClassTitle = "WSRadioButton";

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
    * Constructor.
    */
   public RadioButton()
   {
      super();
      try
      {
         // Inialize component.
         rbBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Constructor.
    * @param   pObjectId   The unique, numeric ID used to distinguish the component from other components.
    */
   public RadioButton(long pObjectId)
   {
      super(pObjectId);
      try
      {
         // Inialize component.
         rbBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Initialize the component.
    */
   private void rbBaseInit() throws Exception
   {
      // Set the default values for this component.
      setLayout(gridLayout);
      setRadioButtonAlignment(0);
      setComponentDefaults();
      setFont(SystemDefaults.getDefaultFont());
   }

   /**
    * The string representation of the name of the class.  This is used in debug messages not intended for the customer. <p>
    * NOTE:  The string returned by this method is not internationalized and should only be used in conjunction with: <p> <pre>
   * 1.  WSMessageLog
   * 2.  WSExceptionFactory
   * </pre>
    */
   public String getClassName()
   {
      return sClassTitle;
   }

   /**
    * Set the default dimensions, font, and color of this component.  This method is called by the constructor.
    */
   private void setComponentDefaults()
   {
      setLeft(Component.DEFAULT_LEFT);
      setTop(Component.DEFAULT_TOP);
      setHeight(35);
      setWidth(87);
      setFont(SystemDefaults.getDefaultFont());
      setBackground(SystemDefaults.getDefaultNonEditBackground());
      setForeground(SystemDefaults.getDefaultForeground());
      getBorderManager().getOuterBorder().setValue(BorderType.BORDER_TITLED);
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
      setDefaultValue((ListItem)(getSelectedItem()));
   }

   /**
    * Is this component Read Only to the user?
    */
   public boolean isReadOnly()
   {
      return bReadOnly;
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
      wsCurrentValue = value;
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
      else if (getSelectedItem() != null)
      {
         if (wsDefaultValue.toString().equals(getSelectedItem().toString()))
         {
            // The default value is already selected.  No change.
            wsCurrentValue = wsDefaultValue;
            return;
         }
         else
         {
            nCommand = CMD_DEFAULT_TO_SAVED_ITEM;
         }
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
      super.delete();
      // Component specific information.
      itemList.removeAllElements();
      itemList = null;
   }
   // ****** Listener methods ******
   // LISTENER EVENT - ActionPerformed

   /**
    * Handles selection change notification, Read Only, etc.
    */
   public void default_actionPerformed(ActionEvent e)
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
            event_OnSelectionChanged(e);
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
      for (int C = 0; C <= itemList.size() - 1; C++)
      {
         RadioButtonListItem item = (RadioButtonListItem)(itemList.get(C));
         cleanButtonItem(item);
      }
      itemList.removeAllElements();
      if (!bUpdatingUI)
      { updateListModel(); }
   }
   // ****** Display ******

   /**
    * The radiobutton group object.
    */
   private ButtonGroup getRadioButtonGroup()
   {
      return bgradioGroup;
   }

   /**
    * Add to the radiobutton group object.
    */
   private void addButtonToGroup(RadioButtonListItem rbButton)
   {
      bgradioGroup.add((AbstractButton)rbButton);
   }

   /**
    * Remove from the radiobutton group object.
    */
   private void removeButtonFromGroup(RadioButtonListItem rbButton)
   {
      bgradioGroup.remove((AbstractButton)rbButton);
   }

   /**
    * The alignment for the radio buttons.
    */

   /*
   private int getRadioButtonAlignment()
   {
      return nradioButtonsAlignment;
   }
   */

   /**
    * The alignment for the radio buttons.
    */
   private void setRadioButtonAlignment(int nradioButtonsAlignment)
   {
      this.nradioButtonsAlignment = nradioButtonsAlignment;
      if (this.nradioButtonsAlignment == 0) //vertical alignment
      {
         gridLayout.setColumns(1);
         gridLayout.setRows(itemList.size());
      }
      else
      {
         gridLayout.setRows(1);
         gridLayout.setColumns(itemList.size());
      }
   }

   public Dimension getAutoSizeDimensions()
   {
      FontMetrics metrics = this.getFontMetrics(getFont());
      if (metrics != null)
      {
         // Set initial values.
         int numberOfButtons = 0;
         int nDefaultHeight = 10;
         int nDefaultWidth = 10;
         int nPreferredHeight = getHeight();
         int nPreferredWidth = getWidth();
         if (itemList != null)
         {
            numberOfButtons = itemList.size();
         }
         int nLongestTextWidth = 0;
         int nNextWidth = 0;
         int nFontHeight = 0;
         // Find longest text width.
         if (itemList != null)
         {
            for (int C = 0; C <= itemList.size() - 1; C++)
            {
               // Determine the width of each button.
               String sButtonText = ((JRadioButton)itemList.get(C)).getText();
               nNextWidth = metrics.stringWidth(sButtonText);
               // Remember the width of the longest button.
               if (nNextWidth > nLongestTextWidth)
               { nLongestTextWidth = nNextWidth; }
            }
         }
         // Determine the width of the title border text.
         nNextWidth = metrics.stringWidth(this.getBorderText());

         /* If the title border text is longer than the longest button width, use
            it instead.  */

         if (nNextWidth > nLongestTextWidth)
         { nLongestTextWidth = nNextWidth; }
         // Determine the height of the text for this font.
         nFontHeight = metrics.getHeight();
         if (numberOfButtons != 0)
         {
            // Calculate the preferred height and width based on the width of the text.
            if ((((nFontHeight + 10) * numberOfButtons) + 10) > nDefaultHeight)
            { nPreferredHeight = ((numberOfButtons * (nFontHeight + 10)) + 10); }
            if (nLongestTextWidth + 60 > nDefaultWidth)
            { nPreferredWidth = nLongestTextWidth + 60; }
            if (nLongestTextWidth + 60 < nDefaultWidth)
            { nPreferredWidth = nDefaultWidth; }
         }
         if (gridLayout != null)
         {
            gridLayout.setColumns(1);
            if (itemList != null)
            {
               gridLayout.setRows(itemList.size());
            }
            setLayout(gridLayout);
         }
         return new Dimension(nPreferredWidth, nPreferredHeight);
      }
      else
      {
         // Metrics is null.
         return new Dimension(getWidth(), getHeight());
      }
   }

   public void setHeight(int height)
   {
      if (!(height < getAutoSizeDimensions().getHeight()))
      {
         //set height only if greater than minimum height.
         super.setBounds(super.getX(), super.getY(), super.getWidth(), height);
      }
      else
      {
         //set height as the minimum height
         super.setBounds(super.getX(), super.getY(), super.getWidth(),
         new Double(getAutoSizeDimensions().getHeight()).intValue());
      }
   }

   public void setWidth(int width)
   {
      if (!(width < getAutoSizeDimensions().getWidth()))
      {
         //set width only if greater than minimum width.
         super.setBounds(super.getX(), super.getY(), width, super.getHeight());
      }
      else
      {
         //set width as the minimum width.
         super.setBounds(super.getX(), super.getY(),
         new Double(getAutoSizeDimensions().getWidth()).intValue(), super.getHeight());
      }
   }

   /**
    * The text to be displayed in a titled border if TitleBorderVisible is turned on. <p>
    * This is overridden so this method can call autoSize() when the text changes. <p>
    * NOTE:  Eventually this part of the autoSize funtionality should go into the WSPanel where the border functionality is.
    */
   public void setBorderText(String text)
   {
      super.setBorderText(text);
      autoSize();
   }

   /**
    * Autosize the control based on the text and number of child radiobuttons in the radiobutton group.
    */
   public void autoSize()
   {
      boolean bDimChanged = false;
      if (!bResizing)
      {
         bResizing = true;
         Dimension dim = getAutoSizeDimensions();
         int nWidth = (int)(dim.getWidth());
         int nHeight = (int)(dim.getHeight());
         if ((int)(dim.getWidth()) <= getWidth())
         { nWidth = getWidth(); }
         if ((int)(dim.getHeight()) <= getHeight())
         { nHeight = getHeight(); }
         setPreferredSize(nWidth, nHeight);
         setMinimumSize(nWidth, nHeight);
         setMaximumSize(nWidth, nHeight);
         setSize(nWidth, nHeight);
         updateUI();
         bResizing = false;
      }
   }

   /**
    * Update the list model.  This should be called when something changes in the list.
    */
   private void updateListModel()
   {
      Font currentFont = getFont();
      java.awt.Component[] comps = this.getComponents();
      boolean bAnswerFound = false;
      String sDefaultAnswer = "";
      // TEMPORARY
      if (!bUpdatingUI)
      {
         bUpdatingUI = true;
         // Remove all current radiobuttons.
         for (int C = 0; C <= comps.length - 1; C++)
         {
            if (comps[C] instanceof RadioButtonListItem)
            {
               removeButtonFromGroup((RadioButtonListItem)comps[C]);
               super.remove((java.awt.Component) comps[C]);
            }
            else
            {
               // Create message object
               Message message = MessageFactory.createMessage( 10422,
                                                              "Balance",
                                                              "Evaluations",
                                                              "",
                                                              Message.INTERNAL,
                                                              InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                              mjs.core.utils.ComponentUtils.getLocale() );

               // Display message
               MessageDialogHandler.showMessage( message, null, sClassTitle );
               return;
            }
         }
         gridLayout.setColumns(1);
         gridLayout.setRows(itemList.size());
         // Add the new radiobuttons.
         for (int C = 0; C <= itemList.size() - 1; C++)
         {
            RadioButtonListItem nextItem = (RadioButtonListItem)(itemList.get(C));
            addButtonToGroup(nextItem);
            super.add((java.awt.Component) nextItem);
         }
         // Make the default radiobutton the selected radiobutton.
         resetToDefaultValue();
         autoSize();
         bUpdatingUI = false;
      }
   }

   /**
    * The selected list item for this component.
    */
   public void setSelectedItem(Object obj)
   {
      RadioButtonListItem item = (RadioButtonListItem)obj;
      item.setSelected(true);
   }

   /**
    * The selected list item for this component.
    */
   public Object getSelectedItem()
   {
      int index = getSelectedIndex();
      if (index != -1)
      { return itemList.get(index); }
      else { return null; }
   }

   /**
    * The index of the selected list item for this component.
    */
   public int getSelectedIndex()
   {
      for (int C = 0; C <= itemList.size() - 1; C++)
      {
         RadioButtonListItem item = (RadioButtonListItem)(itemList.get(C));
         if (item.isSelected())
         { return C; }
      }
      return -1;
   }

   /**
    * The index of the selected list item for this component.
    */
   public void setSelectedIndex(int index)
   {
      RadioButtonListItem item = (RadioButtonListItem)(itemList.get(index));
      item.setSelected(true);
   }

   /**
    * The number of items in the selection list.
    */
   public int getItemCount()
   {
      return itemList.size();
   }
   // ****** Business Logic ******

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
    * This updates the picklist when changes occur.
    */
   public void refreshItemList()
   {
      updateListModel();
   }

   /**
    * Clears the selection - after calling this method isSelectionEmpty() will return true.
    * This is a convenience method that just delegates to the selectionModel.
    */

/*
   public void clearSelection()
   {
      clearSelection();
   }
*/

   /**
    * Populate with a Vector of WSListItem objects.  WSListItem is an interface
    * that can be implemented by any object that needs to be used in the WSList. <p>
    * NOTE:  If the Vector contains anything other than WSListItem objects an
    * exception will be thrown and the method will exit.
    */
   public void setItemList(Vector items)
   {
      for (int C = 0; C <= itemList.size() - 1; C++)
      {
         RadioButtonListItem item = (RadioButtonListItem)(itemList.get(C));
         cleanButtonItem(item);
      }
      // ConfigureButtonItem for each button in the list.
      for (int C = 0; C <= items.size() - 1; C++)
      {
         RadioButtonListItem item = (RadioButtonListItem)(items.get(C));
         configureButtonItem(item);
      }
      // Fill the item list.
      itemList.setItemList(items);
      updateListModel();
   }

   /**
    * Configure RadioButton item.  This will add necessary listeners, default certain properties, etc.
    * @param     item    The WSRadioButtonListItem to update.
    */
   private void configureButtonItem(RadioButtonListItem item)
   {
      item.setFont(getFont());
      item.setBackground(getBackground());
      item.setForeground(getForeground());
      item.addActionListener(lAction);
      // Add all necessary listeners to this component.
      Vector vctListeners = this.getListenerList();
      for (int C = 0; C <= vctListeners.size() - 1; C++)
      {
         EventListener l = (EventListener)(vctListeners.get(C));
         if (l instanceof ActionListener)
         {
            // Action Listener.
            item.addActionListener((ActionListener)l);
         }
         else if (l instanceof ChangeListener)
         {
            // Change Listener.
            item.addChangeListener((ChangeListener)l);
         }
         else if (l instanceof FocusListener)
         {
            // Focus Listener.
            item.addFocusListener((FocusListener)l);
         }
         else if (l instanceof KeyListener)
         {
            // Key Listener.
            item.addKeyListener((KeyListener)l);
         }
         else if (l instanceof MouseListener)
         {
            // Mouse Listener.
            item.addMouseListener((MouseListener)l);
         }
         else if (l instanceof MouseMotionListener)
         {
            // MouseMotion Listener.
            item.addMouseMotionListener((MouseMotionListener)l);
         }
      }
   }

   /**
    * Remove added listeners from the RadioButton item.
    * @param     item    The WSRadioButtonListItem to update.
    */
   private void cleanButtonItem(RadioButtonListItem item)
   {
      // Remove all necessary listeners from this component.
      Vector vctListeners = this.getListenerList();
      item.removeActionListener(lAction);
      for (int C = 0; C <= vctListeners.size() - 1; C++)
      {
         EventListener l = (EventListener)(vctListeners.get(C));
         if (l instanceof ActionListener)
         {
            // Action Listener.
            item.removeActionListener((ActionListener)l);
         }
         else if (l instanceof ChangeListener)
         {
            // Change Listener.
            item.removeChangeListener((ChangeListener)l);
         }
         else if (l instanceof FocusListener)
         {
            // Focus Listener.
            item.removeFocusListener((FocusListener)l);
         }
         else if (l instanceof KeyListener)
         {
            // Key Listener.
            item.removeKeyListener((KeyListener)l);
         }
         else if (l instanceof MouseListener)
         {
            // Mouse Listener.
            item.removeMouseListener((MouseListener)l);
         }
         else if (l instanceof MouseMotionListener)
         {
            // MouseMotion Listener.
            item.removeMouseMotionListener((MouseMotionListener)l);
         }
      }
   }

   /**
    * Add item to the list.
    * @param     item    The WSListItem to add.  Only WSRadioButtonListItem objects are acceptable.
    */
   public void add(ListItem item)
   {
      add((RadioButtonListItem)item);
   }

   /**
    * Add item to the list.
    * @param     item    The WSRadioButtonListItem to add.  Only WSRadioButtonListItem objects are acceptable.
    */
   public void add(RadioButtonListItem item)
   {
      int nIndex = itemList.add((ListItem)item);
      configureButtonItem(item);
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
      int nIndex = itemList.add(sDisplayText, nIntSortValue, obj);
      RadioButtonListItem item = (RadioButtonListItem)itemList.get(nIndex);
      configureButtonItem(item);
      item.setSize(30, 30);
      add((java.awt.Component) item);
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
      int nIndex = itemList.add(sDisplayText, lLongSortValue, obj);
      RadioButtonListItem item = (RadioButtonListItem)itemList.get(nIndex);
      configureButtonItem(item);
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
      int nIndex = itemList.add(sDisplayText, sStringSortValue, obj);
      RadioButtonListItem item = (RadioButtonListItem)itemList.get(nIndex);
      configureButtonItem(item);
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
      int nIndex = itemList.add(sDisplayText, sDisplayText, obj);
      RadioButtonListItem item = (RadioButtonListItem)itemList.get(nIndex);
      configureButtonItem(item);
      updateListModel();
   }

   /**
    * Add item to the list.
    * @param     sDisplayText      The text to be displayed to the user for this item.  This
    * is also the sort value when sorted is turned on.
    */
   public void add(String sDisplayText)
   {
      int nIndex = itemList.add(sDisplayText, sDisplayText, null);
      RadioButtonListItem item = (RadioButtonListItem)itemList.get(nIndex);
      configureButtonItem(item);
      updateListModel();
   }

   /**
    * Remove the list item associated with this String sort value. <p> NOTE:  If multiple matches are found, all are removed.
    * @param    sStringSortValue    The sort value (String) of the item to remove.
    */
   public void remove(String sStringSortValue)
   {
      RadioButtonListItem item = (RadioButtonListItem)(itemList.remove(sStringSortValue));
      cleanButtonItem(item);
      updateListModel();
   }

   /**
    * Remove the list item associated with this long sort value. <p> NOTE:  If multiple matches are found, all are removed.
    * @param    lLongSortValue    The sort value (long) of the item to remove.
    */
   public void remove(long lLongSortValue)
   {
      RadioButtonListItem item = (RadioButtonListItem)(itemList.remove(lLongSortValue));
      cleanButtonItem(item);
      updateListModel();
   }

   /**
    * Remove this list item.
    * @param    item    An object that has implemented the WSListItem interface.
    */
   public void remove(ListItem item)
   {
      itemList.remove(item);
      cleanButtonItem((RadioButtonListItem)item);
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
      if (listener instanceof ChangeListener)
      {
         // ChangeListener
         addChangeListener((ChangeListener)listener);
      }
      else
      {
         // Call addListener() of baseclass.
         super.addListener(listener);
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
      if (listener instanceof ChangeListener)
      {
         // ChangeListener
         removeChangeListener((ChangeListener)listener);
      }
      else
      {
         // Call removeListener() of baseclass.
         super.removeListener(listener);
      }
   }

   public void addActionListener(ActionListener l)
   {
      if (itemList != null)
      {
         // Add listener to all child components.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.addActionListener(l);
         }
      }
   }

   public void addChangeListener(ChangeListener l)
   {
      // Add listener to all child components.
      Vector vctButtons = itemList.cloneAsVector();
      for (int C = 0; C <= vctButtons.size() - 1; C++)
      {
         RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
         item.addChangeListener(l);
      }
   }

   public void addFocusListener(FocusListener l)
   {
      super.addFocusListener(l);
      if (itemList != null)
      {
         // Add listener to all child components.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.addFocusListener(l);
         }
      }
   }

   public void addKeyListener(KeyListener l)
   {
      super.addKeyListener(l);
      if (itemList != null)
      {
         // Add listener to all child components.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.addKeyListener(l);
         }
      }
   }

   public void addMouseListener(MouseListener l)
   {
      super.addMouseListener(l);
      if (itemList != null)
      {
         // Add listener to all child components.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.addMouseListener(l);
         }
      }
   }

   public void addMouseMotionListener(MouseMotionListener l)
   {
      super.addMouseMotionListener(l);
      if (itemList != null)
      {
         // Add listener to all child components.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.addMouseMotionListener(l);
         }
      }
   }

   public void removeActionListener(ActionListener l)
   {
      if (itemList != null)
      {
         // Remove listener from all child components.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.removeActionListener(l);
         }
      }
   }

   public void removeChangeListener(ChangeListener l)
   {
      if (itemList != null)
      {
         // Remove listener from all child components.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.removeChangeListener(l);
         }
      }
   }

   public void removeFocusListener(FocusListener l)
   {
      super.removeFocusListener(l);
      if (itemList != null)
      {
         // Remove listener from all child components.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.removeFocusListener(l);
         }
      }
   }

   public void removeKeyListener(KeyListener l)
   {
      super.removeKeyListener(l);
      if (itemList != null)
      {
         // Remove listener from all child components.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.removeKeyListener(l);
         }
      }
   }

   public void removeMouseListener(MouseListener l)
   {
      super.removeMouseListener(l);
      if (itemList != null)
      {
         // Remove listener from all child components.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.removeMouseListener(l);
         }
      }
   }

   public void removeMouseMotionListener(MouseMotionListener l)
   {
      super.removeMouseMotionListener(l);
      if (itemList != null)
      {
         // Remove listener from all child components.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.removeMouseMotionListener(l);
         }
      }
   }

   public void setFont(Font font)
   {
      if (font.equals(getFont()))
      { return; }
      super.setFont(font);
      if (itemList != null)
      {
         // Propegate down to all list items.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.setFont(font);
         }
      }
      if (itemList != null)
      { autoSize(); }
   }

   public void setBackground(Color bg)
   {
      super.setBackground(bg);
      if (itemList != null)
      {
         // Propegate down to all list items.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.setBackground(bg);
         }
      }
   }

   public void setForeground(Color fg)
   {
      super.setForeground(fg);
      if (itemList != null)
      {
         // Propegate down to all list items.
         Vector vctButtons = itemList.cloneAsVector();
         for (int C = 0; C <= vctButtons.size() - 1; C++)
         {
            RadioButtonListItem item = (RadioButtonListItem)(vctButtons.get(C));
            item.setForeground(fg);
         }
      }
   }
}
// $Log:
//  8    Balance   1.7         6/6/2003 8:40:20 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  7    Balance   1.6         3/7/2003 9:28:24 AM    Mike Shoemake   Removed
//       reference to forms.utils.ComponentUtils.  Replaced with
//       core.utils.ComponentUtils.  Not sure yet why the same method is in
//       both classes.
//  6    Balance   1.5         1/29/2003 4:47:15 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  5    Balance   1.4         1/17/2003 8:51:06 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  4    Balance   1.3         12/20/2002 10:05:06 AM Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  3    Balance   1.2         11/25/2002 10:27:04 AM Mike Shoemake   Cleaned
//       up the listener implementation.
//  2    Balance   1.1         10/11/2002 8:54:24 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:45:46 PM   Mike Shoemake
// $

//
//     Rev 1.2   Nov 25 2002 10:27:04   mshoemake
//  Cleaned up the listener implementation.
//
//     Rev 1.1   Oct 11 2002 08:54:24   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:45:46   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:28:26   mshoemake
//  Initial revision.
//
//     Rev 1.13   May 21 2002 16:10:18   mshoemake
//  Relocated ListItem factories to baseclasses.factories.
//
//     Rev 1.12   Apr 30 2002 16:40:30   mshoemake
//  Added call to autoSize() from setBorderText() method.
//
//     Rev 1.11   Apr 22 2002 15:42:32   mshoemake
//  Update to setReadOnly method.
//
//     Rev 1.6   Mar 27 2002 15:01:14   hfaynzilberg
//  fixed # 12323
//  Unable to choose Default Answer of Global Answer scheme for radio buttons.
//
//     Rev 1.5   Mar 25 2002 12:55:06   mshoemake
//  Rolling back to 1.3.  This is the way it needs to be.  The radiobutton should only resize to be bigger (never smaller).
// It should automatically expand but never automatically shrink.
//
//     Rev 1.3   Feb 27 2002 14:25:52   sputtagunta
//  Fixed 12010 :
//  Exception when clicking ok from question's Answer Scheme dialog.
//
//     Rev 1.2   Feb 26 2002 16:55:56   sputtagunta
//  Fixed #12004 : Default Answer property doesn't work when opening an existing form.
//
//     Rev 1.1   Feb 20 2002 09:43:06   sputtagunta
//  Fixed 11621 : Property Sheet should not allow radiobutton width and height to be too small.
//
//     Rev 1.0   Dec 17 2001 11:05:34   mshoemake
//  Initial revision.


