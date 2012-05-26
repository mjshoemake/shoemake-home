//
// file: WSHorizontalLocationType.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/HorizontalLocationType.java-arc  $
// $Author:Mike Shoemake$
// $Revision:6$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.types;

// Java imports
import java.util.*;
// Witness imports
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;


/**
 * WSHorizontalLocationType:  The area of the panel (East, West, Center) in which to place a component.
 */
public class HorizontalLocationType extends EnumerationType
{
   /**
    * Left:  The component is going on the left side of the panel.
    */
   public static final int LEFT = 0;

   /**
    * Center:  The component is going in the center of the panel.
    */
   public static final int CENTER = 1;

   /**
    * Right:  The component is going on the right side of the panel.
    */
   public static final int RIGHT = 2;

   /**
    * Constructor. <p> Default value is CENTER.
    */
   public HorizontalLocationType()
   {
      super(CENTER);
   }

   /**
    * Constructor. <p> Default value is CENTER.
    * @param  owner           The owner of this object.
    */
   public HorizontalLocationType(EnumerationTypeOwner owner)
   {
      super(CENTER);
      this.setOwner(owner);
   }

   /**
    * Constructor.
    * @param  nDefaultValue   The default int value.
    */
   public HorizontalLocationType(int nDefaultValue)
   {
      super(nDefaultValue);
   }

   /**
    * Constructor.
    * @param  nDefaultValue   The default int value.
    * @param  owner           The owner of this object.
    */
   public HorizontalLocationType(int nDefaultValue, EnumerationTypeOwner owner)
   {
      super(nDefaultValue);
      this.setOwner(owner);
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
         case(LEFT):
            return InternationalizationStrings.szLeft;
         case(CENTER):
            return InternationalizationStrings.szCenter;
         case(RIGHT):
            return InternationalizationStrings.szRight;
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
      if (text.equals(InternationalizationStrings.szLeft))
      {
         // Left
         setValue(LEFT);
      }
      else if (text.equals(InternationalizationStrings.szCenter))
      {
         // Center
         setValue(CENTER);
      }
      else if (text.equals(InternationalizationStrings.szRight))
      {
         // Right
         setValue(RIGHT);
      }
      else
      {
         // Throw exception.
         // Create message object
         Message message = MessageFactory.createMessage( 10416,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSHorizontalLocationType" );
      }
   }

   /**
    * A list of picklist options for this type.
    */
   public static Vector getTextPickList()
   {
      Vector vctPicklist = new Vector();
      vctPicklist.add(InternationalizationStrings.szLeft);
      vctPicklist.add(InternationalizationStrings.szCenter);
      vctPicklist.add(InternationalizationStrings.szRight);
      return vctPicklist;
   }
}
// $Log:
//  6    Balance   1.5         6/6/2003 8:40:21 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  5    Balance   1.4         3/7/2003 8:59:02 AM    Mike Shoemake   Removed
//       reference to forms.utils.ComponentUtils class.  Should have been to
//       core.utils.ComponentUtils.class.  Not sure yet why those two classes
//       have the same method.
//  4    Balance   1.3         1/29/2003 4:47:16 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  3    Balance   1.2         1/17/2003 8:50:54 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  2    Balance   1.1         10/11/2002 8:54:44 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:48:04 PM   Mike Shoemake
// $

//
//     Rev 1.1   Oct 11 2002 08:54:44   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:48:04   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:06   mshoemake
//  Initial revision.
//
//   Rev 1.2   Dec 07 2001 09:21:58   mshoemake
//Update to WSExceptionFactory class.  Changed throwException() to handleException().


