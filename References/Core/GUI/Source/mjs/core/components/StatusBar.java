//
// file: WSStatusBar.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/StatusBar.java-arc  $
// $Author:Mike Shoemake$
// $Revision:7$
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
import java.lang.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
// Witness Imports
import mjs.core.administration.SystemDefaults;
import mjs.core.aggregation.ComponentHashtable;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.types.EnumerationTypeOwner;
import mjs.core.types.EnumerationType;
import mjs.core.types.AnchorType;
import mjs.core.utils.ColorFactory;


/**
 * WSStatusBar:  This is the base StatusBar class for Witness Systems java applets and applications. <p>
 * The layout of the status bar is controlled by the anchor type. <pre>
 * The options are:
 * 1.  Anchor Left
 * 2.  Anchor Right
 * 3.  Evenly Distribute Left To Right
 * </pre> <p> "Anchor Left" means that as the status bar width expands or contracts, the
 * sections (labels) farthest to the right take up the remainder of the space (the
 * labels to the left remain fixed or anchored). <p> "Anchor Right" is exactly the opposite of "Anchor Left".  The label
 * farthest to the left expands and contracts as the panel expands and contracts. <p>
 * "Evenly distribute" means that all labels will expand and contract evenly. <p>
 * To add status bar sections (labels), use the addSection method. <p> Sample usage: <p> <pre>
 * <code>
 * WSStatusBar sbStatus = new WSStatusBar();
 *
 * // This needs to be followed by addSection calls for the change
 * // to take affect.
 * sbStatus.getAnchorType().setValue(WSAnchorType.ANCHOR_RIGHT);
 *
 * sbStatus.addSection(0, "General Status", 300);
 * sbStatus.addSection(1, "File Currently Opened", 150);
 * sbStatus.addSection(2, "Read Only", 80);
 *
 * // Set the status text.
 * sbStatus.setStatus("Working...", 0);
 * sbStatus.setStatus("C:\checking.xml", 1);
 * sbStatus.setStatus("Read Only", 2);
 * </code>
 * </pre>
 */
public class StatusBar extends JPanel implements Component, EnumerationTypeOwner
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
    * The index of this component in the create order.
    */
   private int nCreateOrder = 0;

   /**
    * Is this component active and ready to display messages or not?
    */
   private boolean bActive = false;

   /**
    * The list of listeners that have been added to the control (maintained internally).
    * It is used to automatically remove all added listeners from the control when the delete() method is called.
    */
   private Vector vctListeners = null;

   /**
    * The list of labels (sections) for the status bar.
    */
   private ComponentHashtable htLabelList = new ComponentHashtable();

   /**
    * The type of anchoring applied to the sections (labels). <p> <pre>
    * The options are:
    * 1.  Anchor Left
    * 2.  Anchor Right
    * 3.  Evenly Distribute Left To Right
    * </pre>
    */
   private AnchorType wsAnchorType = new AnchorType();
   // Member variables
   private Border bdrLowered = BorderFactory.createLoweredBevelBorder();
   private Border bdrLine = BorderFactory.createLineBorder(ColorFactory.black, 1);
   // Layout
   private GridBagLayout gridbag = new GridBagLayout();

   /**
    * Constructor.
    */
   public StatusBar()
   {
      try
      {
         nObjectID = generateID();
         sbBaseInit();
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
   public StatusBar(long objectID)
   {
      try
      {
         nObjectID = objectID;
         sbBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   // Initialization (called by constructor).
   private void sbBaseInit() throws Exception
   {
      setPreferredSize(new Dimension(100, 25));
      setLayout(gridbag);
      setAutoscrolls(false);
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
    * Is this component active and ready to display messages or not?
    */
   public boolean isActive()
   {
      return bActive;
   }

   /**
    * Is this component active and ready to display messages or not?
    */
   public void setActive(boolean value)
   {
      bActive = value;
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
   // *****  Non-Interface Methods  *****

   /**
    * Creates a new section (label) for the toolbar. A toolbar section is a label that displays some type of
    * information (status, modified flag, etc.).
    * @param   sHint   The hint (tooltip) text for this section of the toolbar.
    * @param   nWidth  The width of the section.
    */
   public long addSection(String sHint, int nWidth)
   {
      // Generate ObjectID.
      int nLabelCount = this.htLabelList.size();
      Integer iLabelCount = new Integer(nLabelCount);
      long lLabelCount = iLabelCount.longValue();
      // Call primary method.
      return addSection(lLabelCount, sHint, nWidth);
   }

   /**
    * Creates a new section (label) for the toolbar. A toolbar section is a label that displays some type of
    * information (status, modified flag, etc.).
    * @param   nSectionID  A numeric id value that will be associated with the section.
    * @param   sHint       The hint (tooltip) text for this section of the toolbar.
    * @param   nWidth      The width of the section.
    */
   private long addSection(long nSectionID, String sHint, int nWidth)
   {
      // Use specified object ID.
      StandardLabel lblNew = new StandardLabel(nSectionID);
      // Setup the label.
      lblNew.setBounds(3, 3, nWidth, 21);
      lblNew.setPreferredSize(nWidth, 21);
      lblNew.setToolTipText(sHint);
      lblNew.setForeground(SystemDefaults.getDefaultForeground());
      lblNew.setBackground(SystemDefaults.getDefaultNonEditBackground());
      lblNew.setBorder(bdrLowered);
      lblNew.setFont(SystemDefaults.getDefaultFont());
      // Add the new label to the status bar.
      this.add(lblNew);
      // Add new label to hashtable.
      htLabelList.add(nSectionID, lblNew);
      // Redraw the status bar.
      adjustSections();
      return lblNew.getObjectID();
   }

   /**
    * The width of the section that corresponds to the section ID.
    * @param  nWidth      The new width of the section.
    * @param  nSectionID  The numeric ID associated with the section.
    */
   public void setSectionWidth(long nSectionID, int nWidth)
   {
      int nHeight = getHeight();
      // Get the desired section label.
      StandardLabel lblSection = (StandardLabel)(htLabelList.get(nSectionID));
      if (lblSection != null)
      {
         lblSection.setPreferredSize(nWidth, nHeight);
         updateUI();
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
         MessageDialogHandler.showMessage( message, null, "WSStatusBar" );
      }
   }

   /**
    * The text to be displayed by the section that corresponds to the section ID.
    * @param  nWidth      The new width of the section.
    * @param  nSectionID  The numeric ID associated with the section.
    */
   public void setStatus(long nSectionID, String sMessage)
   {
      // Get the desired section label.
      StandardLabel lblSection = (StandardLabel)(htLabelList.get(nSectionID));
      if (lblSection != null)
      {
         lblSection.setText(sMessage);
         if (this.isShowing())
         {
            lblSection.repaint();
            if (isActive())
            {
               // This is required because repaint doesn't always actually display
               // the desired text if called in the middle of an operation.
               //
               // The isActive() method is used to determine whether or not the
               // status bar has been displayed to the screen.  isActive is set
               // from the outside when the applet or application becomes active.  If
               // active is false it's fine because repaint() will still be called.
               //
               // This just makes sure that all messages will be displayed properly
               // to the user.  Defaulting isActive() to true causes side-effects
               // when running inside a browser.
               lblSection.paintImmediately(lblSection.getBounds());
            }
         }
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
         MessageDialogHandler.showMessage( message, null, "WSStatusBar" );
      }
   }

   /**
    * The text to be displayed by the section that corresponds to the section ID.
    * @param  nSectionID  The numeric ID associated with the section.
    */
   public String getStatus(long nSectionID)
   {
      // Get the desired section label.
      StandardLabel lblSection = (StandardLabel)(htLabelList.get(nSectionID));
      if (lblSection != null)
      {
         return lblSection.getText();
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
         MessageDialogHandler.showMessage( message, null, "WSStatusBar" );
         return new String();
      }
   }

   /**
    * Get the Anchor Type object.
    */
   public AnchorType getAnchorType()
   {
      return this.wsAnchorType;
   }

   /**
    * Use the gridbag layout to properly display the components of the status bar.
    */
   private void adjustSections()
   {
      switch (wsAnchorType.getValue())
      {
         case AnchorType.ANCHOR_LEFT:
            applyAnchorLeft();
            break;
         case AnchorType.ANCHOR_RIGHT:
            applyAnchorRight();
            break;
         case AnchorType.DISTRIBUTE_LEFT_TO_RIGHT:
            applyAnchorEvenDistribution();
            break;
         default:
            // Create message object
            Message message = MessageFactory.createMessage( 10416,
                                                           "Balance",
                                                           "Evaluations",
                                                           "",
                                                           Message.INTERNAL,
                                                           InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                           getLocale() );

            // Display message
            MessageDialogHandler.showMessage( message, null, "WSStatusBar" );
            // Continue with Even Distribution.
            applyAnchorEvenDistribution();
            break;
      }
   }

   /**
    * Adjust the widths of the status bar sections using an "Anchor Left"
    * methodology.  This means that the label farthest to the right will
    * take up the remaining space.  The remaining sections will have a fixed width.
    */
   private void applyAnchorLeft()
   {
      GridBagConstraints constr = new GridBagConstraints();
      for (long C = 0; C <= htLabelList.size() - 1; C++)
      {
         // Get the desired section label.
         StandardLabel lblSection = (StandardLabel)(htLabelList.get(C));
         if (C == 0)
         {
            // First section.
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = 1;
            constr.weightx = 0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         else if (C == htLabelList.size() - 1)
         {
            // Last section
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = GridBagConstraints.REMAINDER; //end row
            constr.weightx = 1.0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         else if (C == htLabelList.size() - 2)
         {
            // Next to last section
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = GridBagConstraints.RELATIVE; //next-to-last in row
            constr.weightx = 0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         else
         {
            // Inbetween.
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = 1;
            constr.weightx = 0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         // Set the constraints for this section.
         gridbag.setConstraints(lblSection, constr);
      }
      // Display the changes.
      updateUI();
   }

   /**
    * Adjust the widths of the status bar sections using an "Anchor Right"
    * methodology.  This means that the label farthest to the left will
    * take up the remaining space.  The remaining sections will have a fixed width.
    */
   private void applyAnchorRight()
   {
      GridBagConstraints constr = new GridBagConstraints();
      for (long C = 0; C <= htLabelList.size() - 1; C++)
      {
         // Get the desired section label.
         StandardLabel lblSection = (StandardLabel)(htLabelList.get(C));
         if (C == 0)
         {
            // First section.
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = 1;
            constr.weightx = 1.0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         else if (C == htLabelList.size() - 1)
         {
            // Last section
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = GridBagConstraints.REMAINDER; //end row
            constr.weightx = 0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         else if (C == htLabelList.size() - 2)
         {
            // Next to last section
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = GridBagConstraints.RELATIVE; //next-to-last in row
            constr.weightx = 0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         else
         {
            // Inbetween.
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = 1;
            constr.weightx = 0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         // Set the constraints for this section.
         gridbag.setConstraints(lblSection, constr);
      }
      // Display the changes.
      updateUI();
   }

   /**
    * Adjust the widths of the status bar sections using an "Evenly Distributed"
    * methodology.  This means that the widths of all labels will increase or
    * decrease proportionately as the status bar width changes.
    */
   private void applyAnchorEvenDistribution()
   {
      GridBagConstraints constr = new GridBagConstraints();
      for (long C = 0; C <= htLabelList.size() - 1; C++)
      {
         // Get the desired section label.
         StandardLabel lblSection = (StandardLabel)(htLabelList.get(C));
         if (C == 0)
         {
            // First section.
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = 1;
            constr.weightx = 1.0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         else if (C == htLabelList.size() - 1)
         {
            // Last section
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = GridBagConstraints.REMAINDER; //end row
            constr.weightx = 1.0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         else if (C == htLabelList.size() - 2)
         {
            // Next to last section
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = GridBagConstraints.RELATIVE; //next-to-last in row
            constr.weightx = 1.0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         else
         {
            // Inbetween.
            constr.gridx = new Long(C).intValue();
            constr.anchor = GridBagConstraints.WEST;
            constr.fill = GridBagConstraints.BOTH;
            constr.gridwidth = 1;
            constr.weightx = 1.0;
            constr.insets = new Insets(0, 1, 0, 1);
         }
         // Set the constraints for this section.
         gridbag.setConstraints(lblSection, constr);
      }
      // Display the changes.
      updateUI();
   }

   /**
    * Called by an enumeration type when the value changes.  This is a WSEnumerationTypeOwner interface-related event.
    * @param   newValue    An object of type WSEnumerationType that contains the new value.
    */
   public void event_OnEnumerationValueChanged(EnumerationType newValue) { }

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
      Enumeration enum = htLabelList.elements();
      while (enum.hasMoreElements())
      {
         Component wsObj = (Component)(enum.nextElement());
         wsObj.delete();
      }
      // Clear the child list.
      htLabelList.clear();
      // Remove from parent.
      java.awt.Container contParent = this.getParent();
      contParent.remove(this);
   }

   /**
    * Get the font name.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public String getFontName()
   {
      if (getFont() != null)
      {
         return getFont().getFamily();
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
         MessageDialogHandler.showMessage( message, null, "WSStatusBar" );
         return new String();
      }
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
// $Log:
//  7    Balance   1.6         6/6/2003 8:40:20 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  6    Balance   1.5         1/17/2003 8:51:15 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  5    Balance   1.4         12/20/2002 10:05:03 AM Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  4    Balance   1.3         12/19/2002 2:01:40 PM  Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  3    Balance   1.2         11/25/2002 3:20:46 PM  Mike Shoemake   Update to
//       the listener model.
//  2    Balance   1.1         10/11/2002 8:54:30 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:44:52 PM   Mike Shoemake
// $
//
//     Rev 1.2   Nov 25 2002 15:20:46   mshoemake
//  Update to the listener model.
//
//     Rev 1.1   Oct 11 2002 08:54:30   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:44:52   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:14   mshoemake
//  Initial revision.
//
//     Rev 1.2   Mar 12 2002 08:13:06   mshoemake
//  Fixed refresh problem with the status bar.  Not all of the messages were getting displayed to the user.
//
//     Rev 1.1   Feb 11 2002 14:21:16   hfaynzilberg
//  added NullPointerException handling
//
//     Rev 1.0   Dec 17 2001 11:05:34   mshoemake
//  Initial revision.


