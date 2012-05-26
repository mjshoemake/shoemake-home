/* File name:     StringListItemFactory.java
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
public class StringListItemFactory implements ListItemFactory
{
   /**
    * Create list item with int sort key value.
    */
   public ListItem createItem(String sDisplayText, int nIntSortValue, Object obj)
   {
      return new StringListItem(sDisplayText, nIntSortValue, obj);
   }

   /**
    * Create list item with long sort key value.
    */
   public ListItem createItem(String sDisplayText, long lLongSortValue, Object obj)
   {
      return new StringListItem(sDisplayText, lLongSortValue, obj);
   }

   /**
    * Create list item with String sort key value.
    */
   public ListItem createItem(String sDisplayText, String sStringSortValue, Object obj)
   {
      return new StringListItem(sDisplayText, sStringSortValue, obj);
   }
}
