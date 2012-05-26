//
// file: ButtonPanel.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/ButtonPanel.java-arc  $
// $Author:Mike Shoemake$
// $Revision:12$
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
import mjs.core.types.ButtonAlignmentType;
import mjs.core.types.HorizontalLocationType;
import mjs.core.types.EnumerationType;
import mjs.core.types.EnumerationTypeOwner;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;


/**
 * ButtonPanel:  This is a button panel used to display buttons like
 * OK, Cancel, Add, etc.  This is designed for use in a dialog or wizard,
 * but it can also be useful under other circumstances.  Each button that
 * is added is set to reside either WEST/NORTH, CENTER, or EAST/SOUTH on the panel. <p>
 * The layout of the status bar is controlled by the Alignment Type. <pre>
* Valid options are:
* 1.  Horizontal (East to West)
* 2.  Vertical (North to South)
* </pre> <p> NOTE:  Currently only HORIZONTAL alignment is supported. <p> To add buttons use the addButton method.
 * <p> Sample usage: <p> <pre>
* <code>
* ButtonPanel pnlButtons = new ButtonPanel();
*
* pnlButtons.getButtonAlignmentType().setValue(WSButtonAlignmentType.HORIZONTAL);
*
* // Add the buttons
* pnlButtons.addButton("Add", WSHorizontalLocationType.LEFT);
* pnlButtons.addButton("Delete", WSHorizontalLocationType.LEFT);
*
* pnlButtons.addButton("OK", WSHorizontalLocationType.CENTER);
* pnlButtons.addButton("Cancel", WSHorizontalLocationType.CENTER);
*
* pnlButtons.addButton("Menu", WSHorizontalLocationType.RIGHT);
* pnlButtons.addButton("Help", WSHorizontalLocationType.RIGHT);
* </code>
* </pre>
 */
public class ButtonPanel extends Panel implements Component, ButtonListener, EnumerationTypeOwner
{
   // ****** WSComponent variables ******

   /**
    * The list of buttons on the panel.
    */
   private ComponentHashtable htButtonList = new ComponentHashtable();

   /**
    * The list of button listeners to notify when a button click occurs.
    */
   private ButtonListenerList buttonListenerList = new ButtonListenerList();

   /**
    * The layout of buttons on the panel. <p> <pre>
   * Valid options are:
   * 1.  Horizontal (East to West)
   * 2.  Vertical (North to South)
   * </pre>
    */
   private ButtonAlignmentType wsAlignType = new ButtonAlignmentType(ButtonAlignmentType.HORIZONTAL, this);
   // Spacer panels.
   private JPanel pnlSpacerLeft = new JPanel();
   private JPanel pnlSpacerRight = new JPanel();
   // Layout
   private GridBagLayout gridbag = new GridBagLayout();

   /**
    * Constructor.
    */
   public ButtonPanel()
   {
      super();
      try
      {
         // Initialize the component.
         pnlBaseInit();
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
   public ButtonPanel(long objectID)
   {
      super(objectID);
      try
      {
         // Initialize the component.
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
      setFont(SystemDefaults.getDefaultFont());
      setBackground(SystemDefaults.getDefaultNonEditBackground());
      setForeground(SystemDefaults.getDefaultForeground());
      setPreferredSize(new Dimension(300, 50));
      setLayout(gridbag);
      setAutoscrolls(false);
      add(pnlSpacerLeft);
      add(pnlSpacerRight);
   }

   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * Called when a button is clicked by the user.  This is a WSButtonOwner
    * interface-related event.  The buttons actually call the buttonClicked()
    * event in order to keep this event method available for developers to use for custom event handlers.
    * @param   e              The action event associated with the button click.
    * @param   sDisplayText   The display text of the button.  For WSToggleButton objects this is the tooltip text.
    * @param   nObjectID      The unique numeric ID associated with the button.
    */
   public void event_OnButtonClicked(ButtonEvent e)
   {
   }

   /**
    * An event method to implement to receive messages when a button is clicked.
    *
    * @param e  The ButtonEvent object that describes the action that occured.
    */
   public void buttonClicked(ButtonEvent e)
   {
      // EVENT.  Notify extending class.
      event_OnButtonClicked(e);
      // Notify listeners.
      buttonListenerList.buttonClicked(e);
   }

   /**
    * Called by an enumeration type when the value changes.  This is a WSEnumerationTypeOwner interface-related event.
    * @param   newValue    An object of type WSEnumerationType that contains the new value.
    */
   public void event_OnEnumerationValueChanged(EnumerationType newValue) { }


   // *****  Non-Interface Methods  *****
   /**
    * Creates a new button and adds it to the panel.  Returns the object ID for the new button.
    * @param   sText                The display text for the button.
    * @param   image                The image to display in the button.
    * @param   nObjectID            The unique numeric ID associated with the button.
    * @param   nHorizLocationType   Left, Right, or Center.  See WSHorizontalLocationType for details.  If the alignment is
    * vertical, Left is TOP, Right is BOTTOM.
    * @param   nSize                The width or height of the button.
    * The user may need to force a width and override the default.
    * If alignment is horizontal, nSize is width, otherwise it is height.
    */
   public long addButton(String sText, ImageIcon image, long nObjectID, int nHorizLocationType, int nSize)
   {
      // Create and initialize the button.
      Button button = new Button(nObjectID);
      button.setObjectID(nObjectID);
      button.addButtonListener(this);
      button.setAlignmentButtonSize(nSize);
      button.getHorizontalLocationType().setValue(nHorizLocationType);
      if (image != null)
      { button.setIcon(image); }
      if (!(sText.equals("") || (sText == null)))
      { button.setText(sText); }
      // Add the new button to the panel.
      this.add(button);
      // Add new button to hashtable.
      htButtonList.add(nObjectID, button);
      // Redraw the panel.
      updateUI();
      return button.getObjectID();
   }

   /**
    * Creates a new button and adds it to the panel.  Returns the object ID for the new button.
    * @param   sText                The display text for the button.
    * @param   image                The image to display in the button.
    * @param   nHorizLocationType   Left, Right, or Center.  See WSHorizontalLocationType for details.  If the alignment is
    * vertical, Left is TOP, Right is BOTTOM.
    * @param   nSize                The width or height of the button.
    * The user may need to force a width and override the default.
    * If alignment is horizontal, nSize is width, otherwise it is height.
    */
   public long addButton(String sText, ImageIcon image, int nHorizLocationType, int nSize)
   {
      // Create a unique object ID and call the main method.
      return addButton(sText, image, htButtonList.size(), nHorizLocationType, nSize);
   }

   /**
    * Creates a new button and adds it to the panel.  Returns the object ID for the new button.
    * @param   sText                The display text for the button.
    * @param   nHorizLocationType   Left, Right, or Center.  See WSHorizontalLocationType for details.  If the alignment is
    * vertical, Left is TOP, Right is BOTTOM.
    * @param   nSize                The width or height of the button.
    * The user may need to force a width and override the default.
    * If alignment is horizontal, nSize is width, otherwise it is height.
    */
   public long addButton(String sText, int nHorizLocationType, int nSize)
   {
      // Create a unique object ID and call the main method.
      return addButton(sText, null, htButtonList.size(), nHorizLocationType, nSize);
   }

   /**
    * Creates a new button and adds it to the panel.  Returns the object ID for the new button.
    * @param   image                The image to display in the button.
    * @param   nHorizLocationType   Left, Right, or Center.  See WSHorizontalLocationType for details.  If the alignment is
    * vertical, Left is TOP, Right is BOTTOM.
    * @param   nSize                The width or height of the button.
    * The user may need to force a width and override the default.
    * If alignment is horizontal, nSize is width, otherwise it is height.
    */
   public long addButton(ImageIcon image, int nHorizLocationType, int nSize)
   {
      // Create a unique object ID and call the main method.
      return addButton("", image, htButtonList.size(), nHorizLocationType, nSize);
   }

   /**
    * Creates a new button and adds it to the panel.  Returns the object ID for the new button.
    * @param   sText                The display text for the button.
    * @param   image                The image to display in the button.
    * @param   nHorizLocationType   Left, Right, or Center.  See WSHorizontalLocationType for details.  If the alignment is
    * vertical, Left is TOP, Right is BOTTOM.
    */
   public long addButton(String sText, ImageIcon image, int nHorizLocationType)
   {
      // Create a unique object ID and call the main method.
      return addButton(sText, image, htButtonList.size(), nHorizLocationType, -1);
   }

   /**
    * Creates a new button and adds it to the panel.  Returns the object ID for the new button.
    * @param   sText                The display text for the button.
    * @param   nHorizLocationType   Left, Right, or Center.  See WSHorizontalLocationType for details.  If the alignment is
    * vertical, Left is TOP, Right is BOTTOM.
    */
   public long addButton(String sText, int nHorizLocationType)
   {
      // Create a unique object ID and call the main method.
      return addButton(sText, null, htButtonList.size(), nHorizLocationType, -1);
   }

   /**
    * Creates a new button and adds it to the panel.  Returns the object ID for the new button.
    * @param   image                The image to display in the button.
    * @param   nHorizLocationType   Left, Right, or Center.  See WSHorizontalLocationType for details.  If the alignment is
    * vertical, Left is TOP, Right is BOTTOM.
    */
   public long addButton(ImageIcon image, int nHorizLocationType)
   {
      // Create a unique object ID and call the main method.
      return addButton("", image, htButtonList.size(), nHorizLocationType, -1);
   }

   /**
    * Get the Button Alignment Type object.
    */
   public ButtonAlignmentType getButtonAlignmentType()
   {
      return this.wsAlignType;
   }

   /**
    * Use the gridbag layout to properly display the components on the panel. <p>
    * Currently only HORIZONTAL alignment is supported.
    */
   private void adjustLayout()
   {
      alignHorizontal();

/*
      switch (wsAlignType.getValue())
      {
         case WSButtonAlignmentType.HORIZONTAL:
            alignHorizontal();
            break;

         case WSButtonAlignmentType.VERTICAL:
            alignVertical();
            break;

         default:
            WSExceptionFactory.throwException(WSBusinessLogicException.ERR_UNMATCHED_VALUE, "ButtonPanel", null);

            // Continue with Horizontal Alignment.
            alignHorizontal();
            break;
      }
*/
   }

   /**
    * Reposition the components on the panel using horizontal alignment.
    */
   private void alignHorizontal()
   {
      GridBagConstraints constr = new GridBagConstraints();
      int nLeft = 0;
      int nRight = 0;
      int nCenter = 0;
      int nButtonCount = 0;
      pnlSpacerLeft.setBackground(getBackground());
      pnlSpacerRight.setBackground(getBackground());
      // Initial constraints
      constr.anchor = GridBagConstraints.WEST;
      constr.gridx = GridBagConstraints.RELATIVE;
      constr.gridwidth = 1;
      constr.fill = GridBagConstraints.NONE;
      constr.weightx = 0;
      constr.insets = new Insets(10, 10, 10, 10);
      for (int C = 0; C <= 2; C++)
      {
         // Do for all components.
         Enumeration enum = htButtonList.elements();
         while (enum.hasMoreElements())
         {
            // Get the desired button.
            Button button = (Button)(enum.nextElement());
            if (button.getHorizontalLocationType().getValue() == C)
            {
               // Initial constraints
               constr.fill = GridBagConstraints.NONE;
               constr.weightx = 0;
               constr.insets = new Insets(10, 10, 10, 10);
               switch (button.getHorizontalLocationType().getValue())
               {
                  case HorizontalLocationType.LEFT:
                     nButtonCount = nLeft;
                     nLeft++;
                     constr.anchor = GridBagConstraints.WEST;
                     break;
                  case HorizontalLocationType.RIGHT:
                     nButtonCount = nRight;
                     nRight++;
                     constr.anchor = GridBagConstraints.EAST;
                     break;
                  case HorizontalLocationType.CENTER:
                     nButtonCount = nCenter;
                     nCenter++;
                     constr.anchor = GridBagConstraints.CENTER;
                     break;
                  default:
                     // Create message object
                     Message message = MessageFactory.createMessage( 10416,
                                                                    "Balance",
                                                                    "Evaluations",
                                                                    "",
                                                                    Message.INTERNAL,
                                                                    InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                                    mjs.core.utils.ComponentUtils.getLocale() );

                     // Display message
                     MessageDialogHandler.showMessage( message, null, "ButtonPanel" );
                     constr.anchor = GridBagConstraints.WEST;
                     break;
               }
               // Set the insets.
               if (nButtonCount == 0)
               { constr.insets = new Insets(10, 10, 10, 10); }
               else { constr.insets = new Insets(10, 0, 10, 10); }
               // Set the constraints for this section.
               remove(button);
               add(button);
               gridbag.setConstraints(button, constr);
            }
         }
         // Draw the spacers.
         if (C == 0)
         {
            // Set the constraints for this section.
            constr.fill = GridBagConstraints.BOTH;
            constr.anchor = GridBagConstraints.WEST;
            constr.weightx = 1;
            remove(pnlSpacerLeft);
            add(pnlSpacerLeft);
            gridbag.setConstraints(pnlSpacerLeft, constr);
         }
         else if (C == 1)
         {
            // Set the constraints for this section.
            constr.fill = GridBagConstraints.BOTH;
            constr.anchor = GridBagConstraints.EAST;
            constr.weightx = 1;
            remove(pnlSpacerRight);
            add(pnlSpacerRight);
            gridbag.setConstraints(pnlSpacerRight, constr);
         }
      }
   }

   public void updateUI()
   {
      if (htButtonList != null)
      {
         if (!htButtonList.isEmpty())
         { adjustLayout(); }
      }
      // Display the changes.
      super.updateUI();
   }

   /**
    * Delete this component.
    */
   public void delete()
   {
      // EVENT
      event_OnDelete();
      super.delete();
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
         Message message = MessageFactory.createMessage( 10410,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "ButtonPanel" );
      }
   }

   /**
    * Enables or disables the button with the specified caption.
    */
   public void setEnabled(String text, boolean enabled)
   {
      Button button = getButtonByText(text);
      if (button != null)
      {
         button.setEnabled(enabled);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10410,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "ButtonPanel" );
      }
   }

   /**
    * Retrieve the button with the specified text.
    */
   public Button getButtonByText(String text)
   {
      Enumeration enum = htButtonList.elements();
      while (enum.hasMoreElements())
      {
         Button button = (Button)(enum.nextElement());
         if (button.getText().equals(text))
         { return button; }
      }
      return null;
   }

   public void addButtonListener(ButtonListener l)
   {
      addToListenerList(l);
      buttonListenerList.add(l);
   }

   public void removeButtonListener(ButtonListener l)
   {
      buttonListenerList.remove(l);
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
      if (listener instanceof ButtonListener)
      {
         // ButtonListener
         addButtonListener((ButtonListener)listener);
      }

      // Call method in baseclass.
      super.addListener(listener);
   }

   /**
    * Removes the event listener to the component.  This method is very generic
    * and allows listeners to be removed without having to find out the
    * listener type.  The removeListener method calls the correct method for the listener type.
    */
   public void removeListener(EventListener listener)
   {
      if (listener instanceof ButtonListener)
      {
         // ButtonListener
         removeButtonListener((ButtonListener)listener);
      }

      // Call method in baseclass.
      super.removeListener(listener);
   }

   public void setBackground(Color bg)
   {
      super.setBackground(bg);
      if (pnlSpacerLeft != null)
      { pnlSpacerLeft.setBackground(bg); }
      if (pnlSpacerRight != null)
      { pnlSpacerRight.setBackground(bg); }
   }

   /**
    * Called when a mouse is pressed by the user.  This is a WSButtonOwner interface-related event.
    * @param   e              The action event associated with the mouse press.
    */
//   public void mousePressed(MouseEvent e) { }


   // TOGETHERSOFT DIAGRAM DEPENDENCIES.  DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# Button lnkButton; */

}
// $Log:
//  12   Balance   1.11        6/6/2003 8:40:19 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  11   Balance   1.10        3/28/2003 2:48:11 PM   Mike Shoemake   Added
//       Together diagram dependencies.
//  10   Balance   1.9         3/7/2003 9:28:12 AM    Mike Shoemake   Removed
//       reference to forms.utils.ComponentUtils.  Replaced with
//       core.utils.ComponentUtils.  Not sure yet why the same method is in
//       both classes.
//  9    Balance   1.8         1/29/2003 4:47:12 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  8    Balance   1.7         1/17/2003 8:50:26 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  7    Balance   1.6         12/19/2002 1:56:22 PM  Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  6    Balance   1.5         12/18/2002 8:02:27 AM  Mike Shoemake   Minor
//       changes to the listener implementation.
//  5    Balance   1.4         11/25/2002 9:57:22 AM  Mike Shoemake   Modified
//       the listener model to make it more consistent with what Java does and
//       to make it easier to use.  Also phased out ButtonOwner interface.
//  4    Balance   1.3         10/11/2002 8:54:16 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  3    Balance   1.2         9/20/2002 7:52:24 AM   Mike Shoemake   Removed
//       system.out messages.
//  2    Balance   1.1         9/18/2002 5:06:14 PM   Mike Shoemake   Set
//       background to default non-editable background in
//       mjs.core.administration.SystemDefaults.
//  1    Balance   1.0         8/23/2002 2:44:40 PM   Mike Shoemake
// $


//
//     Rev 1.4   Nov 25 2002 09:57:22   mshoemake
//  Modified the listener model to make it more consistent with what Java does and to make it easier to use.  Also phased out ButtonOwner interface.
//
//     Rev 1.3   Oct 11 2002 08:54:16   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.2   Sep 20 2002 07:52:24   mshoemake
//  Removed system.out messages.
//
//     Rev 1.1   Sep 18 2002 17:06:14   mshoemake
//  Set background to default non-editable background in mjs.core.administration.SystemDefaults.
//
//     Rev 1.0   Aug 23 2002 14:44:40   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:04   mshoemake
//  Initial revision.
//
//     Rev 1.5   Apr 17 2002 09:29:40   mshoemake
//  Update to import statements.
//
//     Rev 1.4   Feb 11 2002 13:03:34   hfaynzilberg
//  added NullPointerException handling
//
//     Rev 1.3   Feb 08 2002 16:34:54   hfaynzilberg
//  declared 'pnlSpacerLeft' and 'pnlSpacerRight' fields private
//
//     Rev 1.2   Jan 28 2002 13:40:04   hfaynzilberg
//  Added multi-drop functionality when "Shift" Key's pressed.
//
//     Rev 1.1   Dec 18 2001 08:51:40   mshoemake
//  This panel now calls buttonClicked() method of the dialog when a button click occurs rather than calling the event_OnButtonClicked() method directly.
//
//     Rev 1.0   Dec 17 2001 11:05:30   mshoemake
//  Initial revision.


