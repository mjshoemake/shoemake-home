/* File name:     ListItem.java
 * Package name:  mjs.core.aggregation
 * Project name:  Core
 * Date Created:  Aug 15, 2004
 * Created by:    mshoemake
 * Year:          2004
 */

package mjs.core.aggregation;


/**
 * This is an object that is compatible with the SortedVector class. 
 * <p> SortedVector allows the developer to sort on ints, longs, or
 * strings, and allows the developer to receive back a sorted list 
 * of either ints, longs, strings, or objects sorted by any of the three.  
 */
public class StringListItem implements ListItem
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
   public StringListItem(String sDisplayText, int nIntSortValue, Object obj)
   {
      nSortInt = nIntSortValue;
      oData = obj;
      sText = sDisplayText;
      nSortType = SortEngine.INT_SORT;
   }

   /**
    * Constructor.
    */
   public StringListItem(String sDisplayText, long lLongSortValue, Object obj)
   {
      lSortLong = lLongSortValue;
      oData = obj;
      sText = sDisplayText;
      nSortType = SortEngine.LONG_SORT;
   }

   /**
    * Constructor.
    */
   public StringListItem(String sDisplayText, String sStringSortValue, Object obj)
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


