//
// file: WSFontPropegationStyle.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/FontPropegationStyle.java-arc  $
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
//import mjs.baseclasses.components.containers.panels.*;
import mjs.core.strings.InternationalizationStrings;


/**
 * WSFontPropegationStyle:  Whether or not scrollbars will appear in a WSScrollPane.
 */
public class FontPropegationStyle extends EnumerationType
{
   /**
    * Do not propegate the font for this component down to it's child components.
    */
   public static final int FONT_PROP_NEVER = 0;

   /**
    * ONLY propegate the font for this component down to child components whose
    * current font matches the original font of this component before the change.
    */
   public static final int FONT_PROP_CONDITIONAL = 1;

   /**
    * Always propegate the font for this component down to it's child components.
    */
   public static final int FONT_PROP_ALWAYS = 2;

   /**
    * Constructor. <p> The default value is FONT_PROP_NONE.
    */
   public FontPropegationStyle()
   {
      super(FONT_PROP_NEVER);
   }

   /**
    * Constructor.
    * @param  nDefaultValue   The default int value.
    */
   public FontPropegationStyle(int nDefaultValue)
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
         case(FONT_PROP_NEVER):
            return InternationalizationStrings.szNever;
         case(FONT_PROP_CONDITIONAL):
            return InternationalizationStrings.szConditionally;
         case(FONT_PROP_ALWAYS):
            return InternationalizationStrings.szAlways;
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
      vctPicklist.add(InternationalizationStrings.szNever);
      vctPicklist.add(InternationalizationStrings.szConditionally);
      vctPicklist.add(InternationalizationStrings.szAlways);
      return vctPicklist;
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/FontPropegationStyle.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:48:04   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:04   mshoemake
//  Initial revision.
//
//   Rev 1.1   Dec 18 2001 08:10:14   mshoemake
//Modified package structure for baseclasses and formgen.


