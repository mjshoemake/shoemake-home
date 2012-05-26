//
// file: WSBorderType.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/BorderType.java-arc  $
// $Author:Mike Shoemake$
// $Revision:7$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.types;

// Java imports
import java.awt.*;
import java.util.*;
// Witness imports
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ColorFactory;


/**
 * WSBorderType:  The type of border to use when displaying a component.
 * All components should have two border types and an external border.
 * The external border is set using the setBorder() method of the
 * component.  The external border defaults to being always displayed outside the WSBorder.
 */
public class BorderType extends EnumerationType
{
   /**
    * No bevel.
    */
   public static final int BORDER_NONE = 0;

   /**
    * Lowered bevel, making the component appear as if it has a sunken in or depressed surface.
    */
   public static final int BORDER_LOWERED_BEVEL = 1;

   /**
    * Raised bevel, making the component appear as if it has a raised surface.
    */
   public static final int BORDER_RAISED_BEVEL = 2;

   /**
    * Titled border, displaying a string.  The font of the titled border will be the
    * font of the component.  The display text of the titled border will be determined by the getTitleText() method.
    */
   public static final int BORDER_TITLED = 3;

   /**
    * A line border.  The color of the line border will be determined by the getLineColor() method.
    */
   public static final int BORDER_LINE = 4;

   /**
    * The color to use if the border is a line border.
    */
   private Color color = ColorFactory.black;

   /**
    * The line width to use if the border is a line border.
    */
   private int width = 1;

   /**
    * The text to use if the border is a titled border.
    */
   private String title = "";

   /**
    * Inner bevel color.  Default is white.
    */
   private Color innerBevelColor = ColorFactory.white;

   /**
    * Inner bevel color.  Default is dark gray.
    */
   private Color outerBevelColor = ColorFactory.darkGray;

   /**
    * Constructor. <p> Default Summary Type is FORM_TOTAL.
    */
   public BorderType()
   {
      super(BORDER_NONE);
   }

   /**
    * Constructor.
    * @param  nDefaultValue   The default int value.
    */
   public BorderType(int nDefaultValue)
   {
      super(nDefaultValue);
   }

   /**
    * The minimum value allowed.
    */
   public int getMinimumValue()
   {
      return 0;
   }

   /**
    * The maximum value allowed.
    */
   public int getMaximumValue()
   {
      return 2;
   }

   /**
    * Return the string representation of the current type.
    */
   public String getValueAsText()
   {
      return getValueAsText(getValue());
   }

   /**
    * Return the string representation of this type.
    */
   public String getValueAsText(int value)
   {
      switch (getValue())
      {
         case(BORDER_NONE):
            return InternationalizationStrings.szNone;
         case(BORDER_LOWERED_BEVEL):
            return InternationalizationStrings.szLoweredBevel;
         case(BORDER_RAISED_BEVEL):
            return InternationalizationStrings.szRaisedBevel;
         case(BORDER_TITLED):
            return InternationalizationStrings.szTitled;
         case(BORDER_LINE):
            return InternationalizationStrings.szLine;
      }

      /* Don't worry about range checking here.  That is handled in the
         setValue() method of the baseclass.  */

      return "";
   }

   /**
    * Set the value of this border by the specified text.
    */
   public void setValueAsText(String text)
   {
      if (text.equals(InternationalizationStrings.szNone))
      {
         // No border.
         setValue(BORDER_NONE);
      }
      else if (text.equals(InternationalizationStrings.szLoweredBevel))
      {
         // Lowered bevel border.
         setValue(BORDER_LOWERED_BEVEL);
      }
      else if (text.equals(InternationalizationStrings.szRaisedBevel))
      {
         // Raised bevel border.
         setValue(BORDER_RAISED_BEVEL);
      }
      else if (text.equals(InternationalizationStrings.szTitled))
      {
         // Titled border.
         setValue(BORDER_TITLED);
      }
      else if (text.equals(InternationalizationStrings.szLine))
      {
         // Line border.
         setValue(BORDER_LINE);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10416,
                                                       "Balance",
                                                       "Evaluations",
                                                       "",
                                                       Message.INTERNAL,
                                                       InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                       mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSBorderType" );
      }
   }

   /**
    * A list of picklist options for this type.
    */
   public static Vector getTextPickList()
   {
      Vector vctPicklist = new Vector();
      vctPicklist.add(InternationalizationStrings.szNone);
      vctPicklist.add(InternationalizationStrings.szLoweredBevel);
      vctPicklist.add(InternationalizationStrings.szRaisedBevel);
      vctPicklist.add(InternationalizationStrings.szTitled);
      vctPicklist.add(InternationalizationStrings.szLine);
      return vctPicklist;
   }

   /**
    * A list of picklist options for this type.  This list contains bevel options only (None, Raised, Lowered).
    */
   public static Vector getBevelStylePickList()
   {
      Vector vctPicklist = new Vector();
      vctPicklist.add(InternationalizationStrings.szNone);
      vctPicklist.add(InternationalizationStrings.szLowered);
      vctPicklist.add(InternationalizationStrings.szRaised);
      return vctPicklist;
   }

   /**
    * The color to use if the border is a line border.
    */
   public Color getColor()
   {
      return color;
   }

   /**
    * Inner bevel color.  Default is white.
    */
   public Color getInnerBevelColor()
   {
      return innerBevelColor;
   }

   /**
    * Inner bevel color.  Default is dark gray.
    */
   public Color getOuterBevelColor()
   {
      return outerBevelColor;
   }

   /**
    * The line width to use if the border is a line border.
    */
   public int getLineThickness()
   {
      return width;
   }

   /**
    * The text to use if the border is a titled border.
    */
   public String getTitleText()
   {
      return title;
   }

   /**
    * The color to use if the border is a line border.
    */
   public void setColor(Color c)
   {
      color = c;
   }

   /**
    * Inner bevel color.  Default is white.
    */
   public void setInnerBevelColor(Color c)
   {
      innerBevelColor = c;
   }

   /**
    * Inner bevel color.  Default is dark gray.
    */
   public void setOuterBevelColor(Color c)
   {
      outerBevelColor = c;
   }

   /**
    * The line width to use if the border is a line border.
    */
   public void setLineThickness(int nwidth)
   {
      width = nwidth;
   }

   /**
    * The text to use if the border is a titled border.
    */
   public void setTitleText(String text)
   {
      title = text;
   }
}
// $Log:
//  7    Balance   1.6         6/6/2003 8:40:21 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  6    Balance   1.5         3/7/2003 9:15:57 AM    Jeff Giesler    Fixed
//       rerference problem.
//  5    Balance   1.4         1/29/2003 4:47:16 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  4    Balance   1.3         1/17/2003 8:50:26 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  3    Balance   1.2         12/20/2002 10:05:07 AM Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  2    Balance   1.1         10/11/2002 8:54:42 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:48:02 PM   Mike Shoemake
// $

//
//     Rev 1.1   Oct 11 2002 08:54:42   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:48:02   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:02   mshoemake
//  Initial revision.
//
//   Rev 1.2   Dec 07 2001 09:21:58   mshoemake
//Update to WSExceptionFactory class.  Changed throwException() to handleException().


