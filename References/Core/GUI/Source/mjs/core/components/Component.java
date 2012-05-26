//
// file: Component.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/Component.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.1  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components;

// Java imports
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
// Witness imports
import mjs.core.administration.SystemDefaults;

/**
 * This is the interface to be implemented for all Witness Systems baseclasses.
 */
public interface Component
{
   // ****** DEFAULTS ******

   /**
    * Default Left property for implementing controls.
    */
   public static final int DEFAULT_LEFT = 0;

   /**
    * Default Top property for implementing controls.
    */
   public static final int DEFAULT_TOP = 0;

   /**
    * Default Height property for implementing controls.
    */
   public static final int DEFAULT_HEIGHT = 25;

   /**
    * Default Width property for implementing controls.
    */
   public static final int DEFAULT_WIDTH = 125;


   // ****** Associated Label Defaults ******
   /**
    * Default Left property for implementing controls.
    */
   public static final int DEFAULT_LABEL_LEFT = 0;

   /**
    * Default Top property for implementing controls.
    */
   public static final int DEFAULT_LABEL_TOP = 0;

   /**
    * Default Height property for implementing controls.
    */
   public static final int DEFAULT_LABEL_HEIGHT = 25;

   /**
    * Default Width property for implementing controls.
    */
   public static final int DEFAULT_LABEL_WIDTH = 125;

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

   // Colors
   public Color getBackground();

   public Color getForeground();

   public void setBackground(Color color);

   public void setForeground(Color color);
   // Additional functionality

   /**
    * Delete this component.
    */
   public void delete();

   /**
    * Event triggered when this component is deleted.
    */
   public void event_OnDelete();

   /**
    * The index of this component in the create order.  The create order of
    * components determines the overlap hierarchy and the tab order.
    */
   public int getCreateOrder();

   /**
    * The index of this component in the create order.  The create order of
    * components determines the overlap hierarchy and the tab order.
    */
   public void setCreateOrder(int value);

   public void remove(MenuComponent popup);

   public void remove(java.awt.Component comp);

   // Dimensions
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
   public boolean contains(int x, int y);

   public float getAlignmentX();

   public float getAlignmentY();

   public Border getBorder();

   public Font getFont();

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

   public void addListener(EventListener listener);

   public void removeListener(EventListener listener);

   public void removeAllListeners();
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/Component.java-arc  $
//
//     Rev 1.1   Sep 18 2002 17:06:14   mshoemake
//  Set background to default non-editable background in mjs.core.administration.SystemDefaults.
//
//     Rev 1.0   Aug 23 2002 14:44:42   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:04   mshoemake
//  Initial revision.
//
//   Rev 1.01   Feb 07 2002 16:11:54   sputtagunta
//changed the default font size to 12


