//
// file: WSDefaults.java
// proj: eQuality 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/administration/SystemDefaults.java-arc  $
// $Author:Mike Shoemake$
// $Revision:5$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.administration;

// Java imports
import java.awt.Color;
import java.awt.Font;
//import java.util.*;
// **************************************************************************
// IF THIS IMPORT STATEMENT CHANGES PLEASE NOTIFY THE TEAM LEAD SO THE CLASS
// DIAGRAMS CAN BE UPDATED.
// **************************************************************************
// Witness imports
import mjs.core.utils.ColorFactory;


/**
 * This class stores and gives static read and protected write access to the default
 * values of the system (font, etc.).
 * @author Mike Shoemake
 * @version 1.1
 * @date 5/14/2001
 */
public class SystemDefaults
{
   /**
    * The default font for visual components.
    */
   private static Font defaultFont = new Font("Dialog", 0, 12);

   /**
    * The default background color for non-editable components.
    */
   private static Color defaultNonEditBackground = ColorFactory.lightGray;

   /**
    * The default background color for editable components.
    */
   private static Color defaultEditBackground = ColorFactory.white;

   /**
    * The default foreground for all components.
    */
   private static Color defaultForeground = ColorFactory.black;

   /**
    * The default font for visual components.
    */
   public static void setDefaultFont(Font newFont)
   {
      defaultFont = newFont;
   }

   /**
    * The default font for visual components.
    */
   public static Font getDefaultFont()
   {
      return new Font(defaultFont.getFamily(), defaultFont.getStyle(), defaultFont.getSize());
   }

   /**
    * The default non-editable background color for Witness components.
    */
   public static void setDefaultNonEditBackground(Color color)
   {
      defaultNonEditBackground = color;
   }

   /**
    * The default non-editable background color for Witness components.
    */
   public static Color getDefaultNonEditBackground()
   {
      return defaultNonEditBackground;
   }

   /**
    * The default editable background color for Witness components.
    */
   public static void setDefaultEditBackground(Color color)
   {
      defaultEditBackground = color;
   }

   /**
    * The default editable background color for Witness components.
    */
   public static Color getDefaultEditBackground()
   {
      return defaultEditBackground;
   }

   /**
    * The default foreground color for Witness components.
    */
   public static void setDefaultForeground(Color color)
   {
      defaultForeground = color;
   }

   /**
    * The default foreground color for Witness components.
    */
   public static Color getDefaultForeground()
   {
      return defaultForeground;
   }

   // TOGETHERSOFT DIAGRAM DEPENDENCIES.  DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# ColorFactory lnkColorFactory; */

}
