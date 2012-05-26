/* File name:     ListItemFactory.java
 * Package name:  mjs.core.aggregation
 * Project name:  Core
 * Date Created:  Aug 15, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.core.aggregation;


/**
 * This is the interface to be implemented when creating a factory used to produce 
 * list items or sort items.
 */
public interface ListItemFactory
{
   /**
    * Create list item with int sort key value.
    */
   public ListItem createItem(String sDisplayText, int nIntSortValue, Object obj);

   /**
    * Create list item with long sort key value.
    */
   public ListItem createItem(String sDisplayText, long lLongSortValue, Object obj);

   /**
    * Create list item with String sort key value.
    */
   public ListItem createItem(String sDisplayText, String sStringSortValue, Object obj);
}
