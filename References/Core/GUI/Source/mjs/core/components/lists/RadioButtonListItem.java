//
// file: WSRadioButtonListItem.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/RadioButtonListItem.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.lists;

import java.util.*;
import javax.swing.*;
// ********** SortItem used to sort the information **********


/**
 * This is an object that is compatible with the WSSort class that extends a JRadioButton.  This allows the WSRadioButton
 * class to simply display the buttons in the order given by the WSList object. <p>
 * WSSort allows the user to sort on ints, longs, or strings, and allows the user to receive back a sorted
 * list of either ints, longs, strings, or objects sorted by any of the three.  (See WSSort.java) <p>
 * This is also used by WSRadioButton when displaying a list of items.  The sort functionality is built into the
 * object, so the the list could be sorted simply by setting a property.
 */
public class RadioButtonListItem extends JRadioButton implements ListItem
{
   /**
    * Sort Value (int).
    */
   private int nSortInt = 0;

   /**
    * Sort Value (long).
    */
   private long lSortLong = 0;

   /**
    * Sort Value (String).
    */
   private String sSortString = null;

   /**
    * Text to display for this list item.
    */
   private String sText = "";

   /**
    * Object reference to be associated with this list item (optional).
    */
   private Object oData = null;

   /**
    * Type of sort.  See WSSort class.
    */
   private int nSortType = -1;

   /**
    * Constructor.
    */
   public RadioButtonListItem(String sDisplayText, int nIntSortValue, Object obj)
   {
      nSortInt = nIntSortValue;
      oData = obj;
      sText = sDisplayText;
      nSortType = SortEngine.INT_SORT;
   }

   /**
    * Constructor.
    */
   public RadioButtonListItem(String sDisplayText, long lLongSortValue, Object obj)
   {
      lSortLong = lLongSortValue;
      oData = obj;
      sText = sDisplayText;
      nSortType = SortEngine.LONG_SORT;
   }

   /**
    * Constructor.
    */
   public RadioButtonListItem(String sDisplayText, String sStringSortValue, Object obj)
   {
      sSortString = sStringSortValue;
      oData = obj;
      sText = sDisplayText;
      nSortType = SortEngine.STRING_SORT;
   }

   // ****** get methods ******
   public int getSortType()
   {
      return nSortType;
   }

   /**
    * Sort Value (int).
    */
   public int getIntSortValue()
   {
      return nSortInt;
   }

   /**
    * Sort Value (long).
    */
   public long getLongSortValue()
   {
      return lSortLong;
   }

   /**
    * Sort Value (String).
    */
   public String getStringSortValue()
   {
      return sSortString;
   }

   /**
    * Object reference to be associated with this list item (optional).
    */
   public Object getObjectReference()
   {
      return oData;
   }

   /**
    * Display text.
    */
   public String getText()
   {
      return sText;
   }

   /**
    * Method used by comboboxes and lists to get the display text.
    */
   public String toString()
   {
      return sText;
   }

   // ****** set methods ******
   public void setSortType(int value)
   {
      nSortType = value;
   }

   public void setText(String value)
   {
      sText = value;
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/RadioButtonListItem.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:45:48   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:28:26   mshoemake
//  Initial revision.


