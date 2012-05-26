//
// file: WSScrollBarStyle.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/ScrollBarType.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.types;

// Java imports
import java.util.*;
// Witness imports
//import mjs.baseclasses.components.panels.*;
import mjs.core.strings.InternationalizationStrings;


/**
 * WSScrollBarStyle:  Whether or not scrollbars will appear in a WSScrollPane.
 */
public class ScrollBarType extends EnumerationType
{
   /**
    * Only display scroll bars when they are needed.
    */
   public static final int SCROLL_BARS_AS_NEEDED = 0;

   /**
    * Never display scroll bars.
    */
   public static final int SCROLL_BARS_NEVER = 1;

   /**
    * Always display scroll bars.
    */
   public static final int SCROLL_BARS_ALWAYS = 2;

   /**
    * Constructor. <p> The default value is SCROLL_BARS_AS_NEEDED.
    */
   public ScrollBarType()
   {
      super(SCROLL_BARS_AS_NEEDED);
   }

   /**
    * Constructor.
    * @param  nDefaultValue   The default int value.
    */
   public ScrollBarType(int nDefaultValue)
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
    * Sets the value based on the string representation.
    */
   public void setValue(String value)
   {
      if (value.equals(InternationalizationStrings.szAlways))
      { setValue(SCROLL_BARS_ALWAYS); }
      else if (value.equals(InternationalizationStrings.szAsNeeded))
      { setValue(SCROLL_BARS_AS_NEEDED); }
      else if (value.equals(InternationalizationStrings.szNever))
      { setValue(SCROLL_BARS_NEVER); }
      else { setValue(SCROLL_BARS_AS_NEEDED); }
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
         case(SCROLL_BARS_ALWAYS):
            return InternationalizationStrings.szAlways;
         case(SCROLL_BARS_AS_NEEDED):
            return InternationalizationStrings.szAsNeeded;
         case(SCROLL_BARS_NEVER):
            return InternationalizationStrings.szNever;
      }

      /* Don't worry about range checking here.  That is handled in the
         setValue() method of the baseclass.  */

      return "";
   }

   /**
    * A list of picklist options for this type.
    */
   public static Vector getTextPickList()
   {
      Vector vctPicklist = new Vector();
      vctPicklist.add(InternationalizationStrings.szAsNeeded);
      vctPicklist.add(InternationalizationStrings.szNever);
      vctPicklist.add(InternationalizationStrings.szAlways);
      return vctPicklist;
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/ScrollBarType.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:48:06   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 24 2002 13:18:38   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:06   mshoemake
//  Initial revision.
//
//   Rev 1.3   Dec 18 2001 08:10:16   mshoemake
//Modified package structure for baseclasses and formgen.


