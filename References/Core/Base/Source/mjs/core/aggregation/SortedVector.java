/* File name:     SortedVector.java
 * Package name:  mjs.core.aggregation
 * Project name:  Core
 * Date Created:  Aug 15, 2004
 * Created by:    mshoemake
 * Year:          2004
 */

package mjs.core.aggregation;

// Java imports
import java.util.Vector;


/**
 * This is the sorted Vector class used to store a list of items.
 * <p> 
 * This class uses the ListItem interface as the encapsulator for 
 * the item to display.
 */
public class SortedVector extends SortEngine
{
   private ListItemFactory itemFactory = null;

   /**
    * Constructor.
    */
   public SortedVector(boolean bSorted, ListItemFactory factory)
   {
      super();
      // Sort when a new item is added?
      setSorted(bSorted);
      // The factory to use when creating the list items.
      setItemFactory(factory);
   }

   /**
    * Constructor.
    */
   public SortedVector(boolean ascending, boolean bSorted, ListItemFactory factory)
   {
      super(ascending, bSorted);
      // The factory to use when creating the list items.
      setItemFactory(factory);
   }

   /**
    * Populate with a Vector of WSListItem objects.  WSListItem is an interface
    * that can be implemented by any object that needs to be used in the WSSortedVector. <p>
    * NOTE:  If the Vector contains anything other than WSListItem objects an
    * exception will be thrown and the method will exit.
    */
   public void setItemList(Vector items)
   {
      removeAllElements();
      for (int C = 0; C <= items.size() - 1; C++)
      {
         Object obj = items.get(C);
         if (obj instanceof ListItem)
         {
            // WSListItem found.  Add to the list.
            ListItem newItem = (ListItem)obj;
            add(newItem);
         }
         else
         {
            // @TODO  Throw exception for wrong type.
            // Exception thrown.  Exit gracefully.
            return;
         }
      }
   }

   /**
    * Add item with integer key value.
    */
   public int add(String sDisplayText, int nIntSortValue, Object obj)
   {
      if (itemFactory != null)
      {
         // Create item.
         ListItem newItem = itemFactory.createItem(sDisplayText, nIntSortValue, obj);
         int nIndex = this.add(newItem);
         return nIndex;
      }
      else
      {
         // @TODO  Throw exception because ItemFactory object has not been instantiated.
         return -1;
      }
   }

   /**
    * Add item with long key value.
    */
   public int add(String sDisplayText, long lLongSortValue, Object obj)
   {
      if (itemFactory != null)
      {
         // Create item.
         ListItem newItem = itemFactory.createItem(sDisplayText, lLongSortValue, obj);
         int nIndex = this.add(newItem);
         return nIndex;
      }
      else
      {
         // @TODO  Throw exception because ItemFactory object has not been instantiated.
         return -1;
      }
   }

   /**
    * Add item with string key value.
    */
   public int add(String sDisplayText, String sStringSortValue, Object obj)
   {
      if (itemFactory != null)
      {
         // Create item.
         ListItem newItem = itemFactory.createItem(sDisplayText, sStringSortValue, obj);
         int nIndex = this.add(newItem);
         return nIndex;
      }
      else
      {
         // @TODO  Throw exception because ItemFactory object has not been instantiated.
         return -1;
      }
   }

   /**
    * The factory used to create the list items.
    */
   public void setItemFactory(ListItemFactory factory)
   {
      itemFactory = factory;
   }
}
