//
// file: WSWindowDialog.java
// proj: eQuality Build 18 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/dialogs/BaseWindowDialog.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.2  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.dialogs;

// Java imports
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;
// Witness imports
import mjs.core.administration.SystemDefaults;
import mjs.core.components.Panel;
import mjs.core.components.ButtonPanel;
import mjs.core.components.Label;
import mjs.core.components.Component;
import mjs.core.events.ButtonEvent;
import mjs.core.events.ButtonListener;
import mjs.core.events.ButtonListenerList;
import mjs.core.factories.MultiLineLabelFactory;
import mjs.core.utils.ColorFactory;


/**
 * Class WSWindowDialog is a baseclass to be used for most custom dialogs.
 * It has a built in, flexible button panel across the bottom and it allows
 * the developer to add a panel or a similar component as the primary "window".
 * The window component is automatically framed in such a way that the component
 * has the standard margin for Witness dialogs.
 * <p> All components should be added to the panel returned by the getWindowPanel()
 * method instead of adding them directly to the dialog.
 * @author Mike Shoemake
 */
public abstract class BaseWindowDialog extends BaseDialog implements ButtonListener
{
   /**
    * Filler panel for the margins (far north).
    */
   private JPanel pnlFarNorth = new JPanel();

   /**
    * Filler panel for the margins (north).
    */
   private JPanel pnlNorth = new JPanel();

   /**
    * Filler panel for the margins (east).
    */
   private JPanel pnlEast = new JPanel();

   /**
    * Filler panel for the margins (north east).
    */
   private JPanel pnlNorthEast = new JPanel();

   /**
    * Filler panel for the margins (west).
    */
   private JPanel pnlWest = new JPanel();

   /**
    * Filler panel for the margins (north east).
    */
   private JPanel pnlNorthWest = new JPanel();

   /**
    * This panel will contain the window component to be displayed.
    */
   private Panel pnlWindow = new Panel();

   /**
    * The description label for this dialog.
    */
   private MultiLineLabelFactory factory = new MultiLineLabelFactory();

   /**
    * The description label for this dialog.
    */
   private Label lblDesc = factory.createLabel();

   /**
    * The current window component to display.
    */
   private Component wsComp = null;

   /**
    * The button panel that contains OK, Cancel, Help, and other buttons.
    */
   private ButtonPanel pnlButtons = new ButtonPanel();

   /**
    * The list of button listeners to notify when a button click occurs.
    */
   private ButtonListenerList buttonListenerList = new ButtonListenerList();

   /**
    * Should the description label be displayed to the user?
    */
   private boolean bDescVisible = true;

   /**
    * If the user is in design mode, the various pieces of the dialog show up in different colors to make it easy to setup.
    */
   private boolean bDesignMode = false;

   /**
    * The description label height.
    */
   private int nDescLblHeight = 16;

   /**
    * Constructor.
    * @param    title        The text to display in the title bar.
    * @param    bModal       Is this dialog a modal dialog?
    * @param    bResizable   Can the user resize this dialog manually?
    * @param    nWidth       The width of the dialog.
    * @param    nHeight      The height of the dialog.
    */
   public BaseWindowDialog(Frame frame, String title, boolean bModal, boolean bResizable, int nWidth, int nHeight,
   boolean showDescription)
   {
      super(frame, title, bModal, bResizable, nWidth, nHeight);
      setDescriptionVisible(showDescription);
      wdwBaseInit();
      pnlWindow.setPreferredSize(new Dimension(nWidth, nHeight));
   }

   /**
    * Initialization.
    */
   private void wdwBaseInit()
   {
      pnlNorth.setLayout(new BorderLayout());
      lblDesc.setText("<Description>");
      lblDesc.setBackground(pnlWindow.getBackground());
      lblDesc.setFont(SystemDefaults.getDefaultFont());
      this.getContentPane().setLayout(new BorderLayout());
      pnlButtons.addButtonListener(this);
      //Set up filler panels
      setLeftMargin(15);
      setRightMargin(15);
      setUpperMargin(15);
      showDescriptionLabel();
      //Place filler panels
      getContentPane().add(pnlButtons, BorderLayout.SOUTH);
      getContentPane().add(pnlEast, BorderLayout.EAST);
      getContentPane().add(pnlWest, BorderLayout.WEST);
      getContentPane().add(pnlNorth, BorderLayout.NORTH);
      setBackground(SystemDefaults.getDefaultNonEditBackground());
      // Initialize the main window panel
      pnlWindow.setLayout(new BorderLayout());
      pnlWindow.setBorder(null);
      //Place window panel on the dialog
      getContentPane().add(pnlWindow, BorderLayout.CENTER);
      centerDialog();
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
    * The current window component to display.
    */
   public Panel getWindowPanel()
   {
      return pnlWindow;
   }

   /**
    * The background of this dialog.
    */
   public void setBackground(Color c)
   {
      super.setBackground(c);
      if (pnlEast != null)
      {
         // Panels
         pnlEast.setBackground(c);
         pnlWest.setBackground(c);
         pnlNorth.setBackground(c);
         pnlFarNorth.setBackground(c);
         pnlNorthEast.setBackground(c);
         pnlNorthWest.setBackground(c);
         pnlWindow.setBackground(c);
         pnlWindow.setBackground(c);
         lblDesc.setBackground(c);
      }
   }

   /**
    * The button panel that contains OK, Cancel, Help, and other buttons.
    */
   public ButtonPanel getButtonPanel()
   {
      return pnlButtons;
   }

   /**
    * The margins to use around the outside of the main panel.
    */
   public void setUpperMargin(int value)
   {
      int height = value;
      // Allow for label height.
      if (bDescVisible)
      { height = height + getDescriptionHeight(); }
      pnlNorth.setPreferredSize(new Dimension(20, height));
      pnlFarNorth.setPreferredSize(new Dimension(pnlNorth.getWidth(), value));
   }

   /**
    * The margins to use around the outside of the main panel.
    */
   public void setLeftMargin(int value)
   {
      pnlWest.setPreferredSize(new Dimension(value, 20));
      pnlNorthWest.setPreferredSize(new Dimension(value, pnlNorth.getHeight()));
   }

   /**
    * The margins to use around the outside of the main panel.
    */
   public void setRightMargin(int value)
   {
      pnlEast.setPreferredSize(new Dimension(value, 20));
      pnlNorthEast.setPreferredSize(new Dimension(value, pnlNorth.getHeight()));
   }

   /**
    * The description label text that gets displayed above the main window.
    */
   public void setDescriptionText(String description)
   {
      lblDesc.setText(description);
   }

   /**
    * The description label height.
    */
   public void setDescriptionHeight(int height)
   {
      nDescLblHeight = height;
      if (lblDesc != null)
      { lblDesc.setHeight(height); }
   }

   /**
    * The description label height.
    */
   public int getDescriptionHeight()
   {
      return nDescLblHeight;
   }

   /**
    * Should the description label be displayed to the user?
    */
   public void setDescriptionVisible(boolean visible)
   {
      bDescVisible = visible;
   }

   /**
    * Should the description label be displayed to the user?
    */
   public boolean isDescriptionVisible()
   {
      return bDescVisible;
   }

   /**
    * If the user is in design mode, the various pieces of the dialog show up in different colors to make it easy to setup.
    */
   public void setDesignMode(boolean designing)
   {
      if (designing)
      {
         getButtonPanel().setBackground(ColorFactory.red);
         setBackground(ColorFactory.red);
         pnlNorth.setBackground(ColorFactory.black);
         pnlNorthWest.setBackground(ColorFactory.gray);
         pnlNorthEast.setBackground(ColorFactory.gray);
         pnlEast.setBackground(ColorFactory.blue);
         pnlWest.setBackground(ColorFactory.blue);
         pnlFarNorth.setBackground(ColorFactory.blue);
      }
   }

   /**
    * Should the description label be displayed?  If not, hide it.
    */
   private void showDescriptionLabel()
   {
      boolean bShowIt = isDescriptionVisible();
      if (bShowIt)
      {
         // Setup North Panel
         pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
         pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
         pnlNorth.add(pnlFarNorth, BorderLayout.NORTH);
         pnlNorth.add((java.awt.Component) lblDesc, BorderLayout.CENTER);
      }
   }

   /**
    * Add this button listener to the component.
    * @param l  The button listener.
    */
   public void addButtonListener(ButtonListener l)
   {
      buttonListenerList.add(l);
   }

   /**
    * Remove this button listener from the component.
    * @param l  The button listener.
    */
   public void removeButtonListener(ButtonListener l)
   {
      buttonListenerList.remove(l);
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/dialogs/BaseWindowDialog.java-arc  $
//
//     Rev 1.2   Nov 25 2002 10:00:32   mshoemake
//  Modified the listener model to make it more consistent with what Java does and to make it easier to use.  Also phased out ButtonOwner interface.
//
//     Rev 1.1   Sep 18 2002 17:07:06   mshoemake
//  Set background to default non-editable background in mjs.core.administration.SystemDefaults.
//
//     Rev 1.0   Aug 23 2002 14:45:14   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:52   mshoemake
//  Initial revision.
//
//     Rev 1.5   May 21 2002 10:53:38   mshoemake
//  Relocated the label factories from baseclasses.components to baseclasses.factories.
//
//     Rev 1.4   Feb 25 2002 10:32:26   mshoemake
//  Integrated the default background color with the WSDefaults class.
//
//     Rev 1.3   Feb 22 2002 16:30:46   mshoemake
//  Fixed #11799.  The border color of the Font Dialog doesn't match the rest of the dialog.
//
//  Still need to setup a default dialog background in the WSDefaults class and use that instead of hardcoding the RGB value.
//
//     Rev 1.2   Feb 07 2002 17:15:16   SEpperson
//  Fixed issues with global questions and answer schemes.
//
//     Rev 1.1   Dec 18 2001 08:17:26   mshoemake
//  Adjusted the dialog so that a small version of the dialog could be allowed.
//
//     Rev 1.0   Dec 17 2001 11:13:04   mshoemake
//  Initial revision.


