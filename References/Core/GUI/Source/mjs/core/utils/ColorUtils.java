//
// file: WSColorUtils.java
// desc:
// proj: ER 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/ColorUtils.java-arc  $
// $Author:Mike Shoemake$
// $Revision:3$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.utils;

// Java imports
import java.awt.*;
import java.util.*;
// Witness imports
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ColorFactory;
import mjs.core.messages.MessageDialogHandler;


/**
 * A utility class used to do various color-based tasks, including converting Strings to Color objects.
 * @author   Mike Shoemake
 * @version  1.0
 * @date     5/27/2001
 */
public class ColorUtils
{
   /**
    * The string representation of the class.
    */
   private static String sClassTitle = "WSColorUtils";

   /**
    * Utility method.  Get the String representation of this Color.
    */
   public static String getColorAsString(Color color)
   {
      if (color != null)
      {
         if (color.equals(ColorFactory.black))
         {
            return InternationalizationStrings.szBlack;
         }
         else if (color.equals(ColorFactory.blue))
         {
            return InternationalizationStrings.szBlue;
         }
         else if (color.equals(ColorFactory.cyan))
         {
            return InternationalizationStrings.szCyan;
         }
         else if (color.equals(ColorFactory.darkGray))
         {
            return InternationalizationStrings.szDarkgray;
         }
         else if (color.equals(ColorFactory.gray))
         {
            return InternationalizationStrings.szGray;
         }
         else if (color.equals(ColorFactory.lightGray))
         {
            return InternationalizationStrings.szLightGray;
         }
         else if (color.equals(ColorFactory.green))
         {
            return InternationalizationStrings.szGreen;
         }
         else if (color.equals(ColorFactory.magenta))
         {
            return InternationalizationStrings.szMagenta;
         }
         else if (color.equals(ColorFactory.orange))
         {
            return InternationalizationStrings.szOrange;
         }
         else if (color.equals(ColorFactory.pink))
         {
            return InternationalizationStrings.szPink;
         }
         else if (color.equals(ColorFactory.red))
         {
            return InternationalizationStrings.szRed;
         }
         else if (color.equals(ColorFactory.white))
         {
            return InternationalizationStrings.szWhite;
         }
         else if (color.equals(ColorFactory.yellow))
         {
            return InternationalizationStrings.szYellow;
         }
      }
      return InternationalizationStrings.szBlack;
   }

   /**
    * Utility method.  Get the Color that corresponds to this String.
    */
   public static Color getColor(String text)
   {
      if (text != null)
      {
         if (text.equals(InternationalizationStrings.szBlack))
         {
            return ColorFactory.black;
         }
         else if (text.equals(InternationalizationStrings.szBlue))
         {
            return ColorFactory.blue;
         }
         else if (text.equals(InternationalizationStrings.szCyan))
         {
            return ColorFactory.cyan;
         }
         else if (text.equals(InternationalizationStrings.szDarkgray))
         {
            return ColorFactory.darkGray;
         }
         else if (text.equals(InternationalizationStrings.szGray))
         {
            return ColorFactory.gray;
         }
         else if (text.equals(InternationalizationStrings.szLightGray))
         {
            return ColorFactory.lightGray;
         }
         else if (text.equals(InternationalizationStrings.szGreen))
         {
            return ColorFactory.green;
         }
         else if (text.equals(InternationalizationStrings.szMagenta))
         {
            return ColorFactory.magenta;
         }
         else if (text.equals(InternationalizationStrings.szOrange))
         {
            return ColorFactory.orange;
         }
         else if (text.equals(InternationalizationStrings.szPink))
         {
            return ColorFactory.pink;
         }
         else if (text.equals(InternationalizationStrings.szRed))
         {
            return ColorFactory.red;
         }
         else if (text.equals(InternationalizationStrings.szWhite))
         {
            return ColorFactory.white;
         }
         else if (text.equals(InternationalizationStrings.szYellow))
         {
            return ColorFactory.yellow;
         }
      }
      // Default to black.
      return ColorFactory.black;
   }

   // TOGETHER DIAGRAM DEPENDENCIES.
   // PLEASE DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# ColorFactory lnkColorFactory; */

   /**
    * @link dependency
    */
   /*# MessageDialogHandler lnkMessageDialogHandler; */

   /**
    * @link dependency
    */
   /*# InternationalizationStrings lnkInternationalizationStrings; */


}
// $Log:
//  3    Balance   1.2         3/18/2003 1:24:13 PM   Mike Shoemake   Added
//       Together diagram links.
//  2    Balance   1.1         12/20/2002 10:05:07 AM Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  1    Balance   1.0         8/23/2002 2:48:26 PM   Mike Shoemake
// $
//
//     Rev 1.0   Aug 23 2002 14:48:26   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:24   mshoemake
//  Initial revision.
//
//   Rev 1.20   Mar 10 2000 14:41:46   RSrinivasan
//tags added


