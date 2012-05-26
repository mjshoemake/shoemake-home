//
// file: WSContainer.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/Container.java-arc  $
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
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
// Witness imports
import mjs.core.types.FontPropegationStyle;


/**
 * This is the interface to be implemented for all Witness Systems containers (panels, scroll panes, etc.).
 */
public interface Container
{
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

   /**
    * Whether or not to propegate the new font down to the child components.
    */
   public FontPropegationStyle getFontPropegationStyle();

   /**
    * The list of child components for this container.
    */
   public Vector getComponentsList();

   /**
    * The text to be displayed in a titled border if TitleBorderVisible is turned on.
    */
   public void setBorderText(String text);

   /**
    * The text to be displayed in a titled border if TitleBorderVisible is turned on.
    */
   public String getBorderText();

   /**
    * Add a component to this container.
    */
   public java.awt.Component add(java.awt.Component component);

   /**
    * Remove a component from this container.
    */
   public void remove(java.awt.Component component);

   /**
    * The border for the component.
    */
   public void setBorder(Border border);

   /**
    * The border for the component.
    */
   public Border getExternalBorder();

   /**
    * The internal border for the component.
    */
   public FlexibleBorder getBorderManager();

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

   public void remove(MenuComponent popup);

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
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/Container.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:44:42   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:06   mshoemake
//  Initial revision.


