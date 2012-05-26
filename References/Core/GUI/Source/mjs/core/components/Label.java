//
// file: WSLabel.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/Label.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
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


/**
 * WSLabel:  This is the interface implemented by all Witness Systems Labels.
 */
public interface Label
{
   /**
    * The string representation of the name of the class.  This is used in debug messages not intended for the customer. <p>
    * NOTE:  The string returned by this method is not internationalized and should only be used in conjunction with: <pre>
   * 1.  WSMessageLog
   * 2.  WSExceptionFactory
   * </pre>
    */
   public String getClassName();
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * Called by the delete() method before any processing takes place.
    */
   public void event_OnDelete();

   /**
    * Delete this component.
    */
   public void delete();

   /**
    * Allow the label to have it's own border? (Default is yes)
    */
   public boolean isBorderVisible();

   /**
    * Allow the label to have it's own border? (Default is yes)
    */
   public void setBorderVisible(boolean value);

   /**
    * Get the font name.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public String getFontName();

   /**
    * Get the font style.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public int getFontStyle();

   /**
    * Get the font size.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public int getFontSize();

   /**
    * Set the font name.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public void setFontName(String sfontName);

   /**
    * Set the font style.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public void setFontStyle(int nfontStyle);

   /**
    * Set the font name.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public void setFontSize(int nfontSize);

   /**
    * The component to which this label belongs.  This is used to establish a connection between an editable control and
    * the label that modifies it.
    */
   public void setRelatedComponent(Component component);

   /**
    * The component to which this label belongs.  This is used to establish a connection between an editable control and
    * the label that modifies it.
    */
   public Component getRelatedComponent();

   /**
    * Remove all listeners that have been added to this component.
    */
   public void removeAllListeners();

   public void addAncestorListener(AncestorListener l);

   public void addComponentListener(ComponentListener l);

   public void addContainerListener(ContainerListener l);

   public void addFocusListener(FocusListener l);

   public void addHierarchyBoundsListener(HierarchyBoundsListener l);

   public void addInputMethodListener(InputMethodListener l);

   public void addKeyListener(KeyListener l);

   public void addMouseListener(MouseListener l);

   public void addMouseMotionListener(MouseMotionListener l);

   public void addPropertyChangeListener(PropertyChangeListener l);

   public void addVetoableChangeListener(VetoableChangeListener l);

   public void removeAncestorListener(AncestorListener l);

   public void removeComponentListener(ComponentListener l);

   public void removeContainerListener(ContainerListener l);

   public void removeFocusListener(FocusListener l);

   public void removeHierarchyBoundsListener(HierarchyBoundsListener l);

   public void removeInputMethodListener(InputMethodListener l);

   public void removeKeyListener(KeyListener l);

   public void removeMouseListener(MouseListener l);

   public void removeMouseMotionListener(MouseMotionListener l);

   public void removePropertyChangeListener(PropertyChangeListener l);

   public void removeVetoableChangeListener(VetoableChangeListener l);
   // *****  Methods from WSIObject interface.  *****

   /**
    * Adds the event listener to the component.  This method is very generic
    * and allows listeners to be added without having to find out the
    * listener type.  The addListener method calls the correct method for the listener type.
    */
   public void addListener(EventListener listener);

   /**
    * Removes the event listener to the component.  This method is very generic
    * and allows listeners to be removed without having to find out the
    * listener type.  The removeListener method calls the correct method for the listener type.
    */
   public void removeListener(EventListener listener);

   /**
    * The integer object ID.  This can be either specified in the constructor, set
    * using the set method, or the baseclass will auto generate a number that is unique for the class.
    */
   public long getObjectID();

   /**
    * The integer object ID.  This can be either specified in the constructor, set
    * using the set method, or the baseclass will auto generate a number that is unique for the class.
    */
   public void setObjectID(long value);

   /**
    * A user defined object type.  This can be used in conjunction with integer
    * constants defined by the developer.  It is not used internally for anything and is very flexible.
    */
   public int getObjectType();

   /**
    * A user defined object type.  This can be used in conjunction with integer
    * constants defined by the developer.  It is not used internally for anything and is very flexible.
    */
   public void setObjectType(int value);

   /**
    * The name associated with the object if applicable.
    */
   public String getObjectName();

   /**
    * The name associated with the object if applicable.
    */
   public void setObjectName(String value);

   public void remove(MenuComponent popup);

   public void remove(java.awt.Component comp);

   public void setText(String t);

   public String getText();

   public int getLeft();

   public int getTop();

   public int getWidth();

   public int getHeight();

   public Rectangle getBounds();

   public void setBounds(int left, int top, int width, int height);

   public void setBounds(Rectangle r);

   public void setLeft(int left);

   public void setTop(int top);

   public void setWidth(int width);

   public void setHeight(int height);

   // Miscellaneous Component methods
   public Color getBackground();

   public Color getForeground();

   public void setBackground(Color color);

   public void setForeground(Color color);

   public boolean contains(int x, int y);

   public float getAlignmentX();

   public float getAlignmentY();

   public Border getBorder();

   public Font getFont();

   public java.awt.Container getParent();

   public Dimension getMaximumSize();

   public Dimension getMinimumSize();

   public java.awt.Component getNextFocusableComponent();

   public Dimension getPreferredSize();

   public Dimension getSize();

   public Point getToolTipLocation(MouseEvent event);

   public String getToolTipText();

   public boolean hasFocus();

   public boolean isEnabled();

   public boolean isRequestFocusEnabled();

   public boolean isVisible();

   public void paintImmediately(int left, int top, int width, int height);

   public void repaint();

   public boolean requestDefaultFocus();

   public void requestFocus();

   public void setAlignmentX(float AlignmentX);

   public void setAlignmentY(float AlignmentY);

   public void setBorder(Border border);

   public void setEnabled(boolean enabled);

   public void setFont(Font font);

   public void setLocation(int x, int y);

   public void setMaximumSize(int width, int height);

   public void setMaximumSize(Dimension dimension);

   public void setMinimumSize(int width, int height);

   public void setMinimumSize(Dimension dimension);

   public void setPreferredSize(int width, int height);

   public void setPreferredSize(Dimension dimension);

   public void setNextFocusableComponent(java.awt.Component acomponent);

   public void setRequestFocusEnabled(boolean aFlag);

   public void setToolTipText(String text);

   public void setVisible(boolean aFlag);

   public void updateUI();
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/Label.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:44:44   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:08   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:05:30   mshoemake
//  Initial revision.


