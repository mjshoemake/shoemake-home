/* File name:     ListItem.java
 * Package name:  mjs.core.aggregation
 * Project name:  Core
 * Date Created:  Aug 15, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.core.aggregation;

/**
 * This is the class used sorting a list of items.  
 * The sort functionality is built into the SortedVector class 
 * which could be instantiated and used by any object.
 */
public interface ListItem
{
   // WSSortItem interface compatibility methods.
   public int getIntSortValue();

   public long getLongSortValue();

   public String getStringSortValue();

   public Object getObjectReference();

   public int getSortType();

   public void setSortType(int value);

   // WSListItem methods.
   public String getText();

   public String toString();
}
