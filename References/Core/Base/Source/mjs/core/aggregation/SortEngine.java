/* File name:     SortEngine.java
 * Package name:  mjs.core.aggregation
 * Project name:  Core
 * Date Created:  Aug 15, 2004
 * Created by:    mshoemake
 * Year:          2004
 */

package mjs.core.aggregation;

// Java imports
import java.util.*;

/**
 * SortEngine allows the developer to sort on ints, longs, or
 * strings, and allows the developer to receive back a sorted list 
 * of either ints, longs, strings, or objects sorted by any of the three.
 * <p>  
 * This class should not be used directly.  Use the SortedVector class instead 
 * (SortedVector extends SortEngine). 
 */
public abstract class SortEngine
{
   // Sort Types
   public static final int INT_SORT    = 0;
   public static final int LONG_SORT   = 1;
   public static final int STRING_SORT = 2;
   // ****** Default Values ******

   /**
    * This variable is set by the add methods.
    */
   private int nSortType = -1;

   /**
    * Set by the constructor.
    */
   private boolean bAscending = true;

   /**
    * Sort on add?  In other words, should we automatically sort the new items as they are added to the list rather
    * than sorting after all items have been added? <p> This will be slower than sorting them all at once.  The only
    * time you would want AutoSort turned on is when all of the items
    * in the list have already been sorted and you want to integrate
    * NEW items into the already sorted list as they are added. <p> This variable is set by the constructor.
    */
   private boolean bAutoSort = false;

   /**
    * Flag used by the execute method.
    */
   private boolean bError = false;

   /**
    * Is this sort a case sensitive sort (default is NO).
    */
   private boolean bCaseSensitive = false;

   /**
    * List of items to sort.
    */
   private Vector vctSortList = new Vector();

   /**
    * Reference to an item.
    */
   private ListItem item = null;

   /**
    * Constructor.
    */
   public SortEngine(boolean bAscending, boolean bAutoSort)
   {
      // Ascending or Descending?
      this.bAscending = bAscending;
      // Sort when a new item is added?
      this.bAutoSort = bAutoSort;
   }

   /**
    * Constructor. <pre>
    * Defaults:
    *    Ascending:  true
    *    AutoSort:  false
    * </pre>
    */
   public SortEngine()
   {
      // Ascending or Descending?
      this.bAscending = true;

      /* Do not autosort.  This will automatically be turned on after
         the sort of the initial values, so the new values can be added
         to the list in their proper place.  */

      this.bAutoSort = false;
   }
   // ****** Functionality Methods ******

   /**
    * Add accepts a WSListItem object to add to the list. Throws an exception and returns -1 upon error.
    */
   public int add(ListItem item)
   {
      // Add failure returns -1.
      int nIndex = -1;
      if (nSortType == -1)
      { nSortType = item.getSortType(); }
      // All items need to be the same type.
      if (nSortType == item.getSortType())
      {
         // Int Sort.  Continue processing
         nIndex = addToList(item);
      }
      else
      {
         // @TODO Throw exception if out of bounds.
      }
      return nIndex;
   }

   /**
    * Returns the index of the inserted item in the list.
    */
   private int addToList(ListItem item)
   {
      int nIndex = -1;
      // Add the new item.
      if (bAutoSort)
      {
         // List is already sorted.  Insert where appropriate.
         nIndex = addToSortedList(item);
      }
      else
      {
         /* List is not sorted.  Just add to the list--we will sort
            later.  */

         vctSortList.add(item);
         nIndex = vctSortList.size() - 1;
      }
      // Return the index of the new item.
      return nIndex;
   }

   /**
    * List is already sorted.  Insert where appropriate.
    */
   private int addToSortedList(ListItem value)
   {
      // Find a place for this object to go.
      boolean bKeepSearching = true;
      int nTemp = 0;
      while ((bKeepSearching) && (nTemp <= vctSortList.size() - 1))
      {
         ListItem sTemp = (ListItem)(vctSortList.get(nTemp));
         // Is this it?
         if (isCorrectOrder(value, sTemp))
         {
            vctSortList.insertElementAt(value, nTemp);
            bKeepSearching = false;
         }
         else
         {
            // Nope.  Keep going.
            nTemp++;
         }
      }
      // Go ahead and append to end of list.
      if (bKeepSearching)
      { vctSortList.addElement(value); }
      // Return the index of the inserted item.
      return nTemp;
   }

   /**
    * Is this list sorted?
    */
   public void setSorted(boolean value)
   {
      if (value)
      {
         execute();
         bAutoSort = true;
      }
      else
      {
         /* From now on, do NOT sort when new items
            are added.  */

         bAutoSort = false;
      }
   }

   /**
    * Is this list sorted?
    */
   public boolean isSorted()
   {
      return bAutoSort;
   }

   /**
    * Is this sort a case sensitive sort (default is NO).
    */
   public void setCaseSensitive(boolean value)
   {
      bCaseSensitive = value;
   }

   /**
    * Is this sort a case sensitive sort (default is NO).
    */
   public boolean isCaseSensitive()
   {
      return bCaseSensitive;
   }

   /**
    * Execute sort based on the sort types specified above.
    */
   private boolean execute()
   {
      ListItem itemCurrent;
      ListItem itemNext;
      int nCurrent = 0;
      boolean bContinue = true;
      if (nSortType == -1) { return false; }
      // Clear the error flag.
      bError = false;
      int iterations = 0;

      // Begin sorting.
      while ((nCurrent <= vctSortList.size() - 2) && (bContinue))
      {
         itemCurrent = (ListItem)(vctSortList.get(nCurrent));
         itemNext = (ListItem)(vctSortList.get(nCurrent + 1));

         // Trace progress.
         traceList(vctSortList);

         if (!isCorrectOrder(itemCurrent, itemNext))
         {
            // Not correct order.  Need to relocate the current.
            if (nCurrent == 0)
            {
               // Just stick it at the top of the list and start
               // over.
               vctSortList.removeElementAt(nCurrent + 1);
               vctSortList.insertElementAt(itemNext, 0);
               //writeToLog("SortEngine  execute()  Incorrect order.  Current = 0.  Moving '"+itemNext.getStringSortValue()+"' to the top of the list.", LoggingManager.PRIORITY_DEBUG);
            }
            else
            {
               // Find a spot for next and insert it using binary search...
               int newLocation = findNewLocation(vctSortList, itemNext, nCurrent+1);
               vctSortList.removeElementAt(nCurrent + 1);
               vctSortList.insertElementAt(itemNext, newLocation);
               //writeToLog("SortEngine  execute()  Incorrect order.  Moving '"+itemNext.getStringSortValue()+"' to position "+newLocation+"...", LoggingManager.PRIORITY_DEBUG);
            }
         }
         else 
         { 
            nCurrent++; 
            //writeToLog("SortEngine  execute()  Correct order.  Incrementing counter.", LoggingManager.PRIORITY_DEBUG);
         }
         
         // Stop if taking too long.  May be in continuous loop...
         iterations++;
         if (iterations > 200)
            bContinue = false;
      }
      if (!bError)
      {
         /* Turn on auto-sort for any new items that are added after
            this sort process.  */

         bAutoSort = true;
      }
      return (!bError);
   }

   /**
    * Find the new location for the item specified in the sorted list
    * using binary search to boost performance.
    * 
    * @param list
    * @param item
    * @param numSorted
    */
   private int findNewLocation(Vector list, ListItem item, int numSorted)
   { 
      // Test list item.
      ListItem itemTest = null;
      // low point of binary search.
      int low = 0;
      // high point of binary search.
      int high = numSorted-1;
      //writeToLog("SortEngine  findNewLocation()  low="+low+"("+list.get(low).toString()+"), high="+high+"("+list.get(low).toString()+")", LoggingManager.PRIORITY_DEBUG);

      while (high - low > 3)
      {
         int middle = (((high - low) / 2) + low);         
         itemTest = (ListItem)(list.get(middle));
         //writeToLog("SortEngine  findNewLocation()  low="+low+"("+list.get(low).toString()+"), middle="+middle+"("+itemTest.toString()+"), high="+high+"("+list.get(high).toString()+")", LoggingManager.PRIORITY_DEBUG);
         if (! isCorrectOrder(item, itemTest))
         {
            // Move the low.
            low = middle;   
            //writeToLog("SortEngine  findNewLocation()  NEW LOW:  low="+low+"("+list.get(low).toString()+")...", LoggingManager.PRIORITY_DEBUG);
         }
         else
         {
            // Move the high.
            high = middle;   
            //writeToLog("SortEngine  findNewLocation()  NEW HIGH:  high="+high+"("+list.get(high).toString()+")...", LoggingManager.PRIORITY_DEBUG);
         }
      }
      
      // Compare remaining options.
      int C = low;
      // Boolean flag to let us know that we have a match.
      boolean found = false; 
      while ((! found) && (C <= high))
      {
         // Scroll through each of the remaining items starting with the first one.
         itemTest = (ListItem)(list.get(C));
         //writeToLog("SortEngine  findNewLocation()  Checking #"+C+", "+itemTest.toString()+"...", LoggingManager.PRIORITY_DEBUG);
         if (! isCorrectOrder(item, itemTest))
         {
            // This isn't it.  Try the next one.  
            C++;                          
         }
         else
         {
            // Found the match.  We can stop now.
            found = true; 
         }
      }
      
      if (found)
      {
         return C;
      }
      else
      {
         return -1;   
      }
   }

   /**
    * Trace out the current order of the list.
    */
   private void traceList(Vector list)
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("Current list:");
      for (int C=0; C <= list.size()-1; C++)
      {
         ListItem item = (ListItem)(list.get(C));
         if (C == 0)
         {
            buffer.append(" "+item.getStringSortValue());
         }
         else
         {
            buffer.append(", "+item.getStringSortValue());
         }
      }
   }

   // Comparison methods.
   private boolean isCorrectOrder(ListItem item1, ListItem item2)
   {
      boolean bCorrect = false;
      switch (nSortType)
      {
         case INT_SORT:
            {
               bCorrect = isCorrectOrder(item1.getIntSortValue(), item2.getIntSortValue());
               break;
            }
         case LONG_SORT:
            {
               bCorrect = isCorrectOrder(item1.getLongSortValue(), item2.getLongSortValue());
               break;
            }
         case STRING_SORT:
            {
               bCorrect = isCorrectOrder(item1.getStringSortValue(), item2.getStringSortValue(), isCaseSensitive());
               break;
            }
         default:
            {
               return true;
            }
      }
      // If "descending" then sort in reverse order.
      if (!bAscending)
      { bCorrect = !bCorrect; }
      return bCorrect;
   }

   public static boolean isCorrectOrder(int nLeft, int nRight)
   {
      return (nRight >= nLeft);
   }

   public static boolean isCorrectOrder(long lLeft, long lRight)
   {
      return (lRight >= lLeft);
   }

   public static boolean isCorrectOrder(String sLeft, String sRight, boolean bCaseSensitive)
   {
      if ((sLeft != null) && (sRight != null))
      {
         if (bCaseSensitive)
         {
            // Case sensitive.
            return (!(sLeft.compareTo(sRight) > 0));
         }
         else
         {
            // Case insensitive.
            return (!(sLeft.compareToIgnoreCase(sRight) > 0));
         }
      }
      else
      {
         return true;
      }
   }

   // ****** Return value methods ******
   // Get the sorted list of ints.
   public int[] getResult_Ints()
   {
      ListItem item = null;
      int nSize = vctSortList.size();
      // Return in an array.
      int[] aInts = new int[nSize];
      for (int C = 0; C <= vctSortList.size() - 1; C++)
      {
         item = (ListItem)(vctSortList.get(C));
         aInts[C] = item.getIntSortValue();
      }
      return aInts;
   }

   // Get the sorted list of ints.
   public long[] getResult_Longs()
   {
      ListItem item = null;
      int nSize = vctSortList.size();
      // Return in an array.
      long[] aValues = new long[nSize];
      for (int C = 0; C <= vctSortList.size() - 1; C++)
      {
         item = (ListItem)(vctSortList.get(C));
         aValues[C] = item.getLongSortValue();
      }
      return aValues;
   }

   // Get the sorted list of ints.
   public String[] getResult_Strings()
   {
      ListItem item = null;
      int nSize = vctSortList.size();
      // Return in an array.
      String[] aValues = new String[nSize];
      for (int C = 0; C <= vctSortList.size() - 1; C++)
      {
         item = (ListItem)(vctSortList.get(C));
         aValues[C] = item.getStringSortValue();
      }
      return aValues;
   }

   // Get the sorted list of ints.
   public Object[] getResult_Objects()
   {
      ListItem item = null;
      int nSize = vctSortList.size();
      // Return in an array.
      Object[] aValues = new Object[nSize];
      for (int C = 0; C <= vctSortList.size() - 1; C++)
      {
         item = (ListItem)(vctSortList.get(C));
         aValues[C] = item.getObjectReference();
      }
      return aValues;
   }

   public int size()
   {
      return vctSortList.size();
   }

   // Get the sorted Vector of WSListItem objects.
   public ListItem get(int index)
   {
      if (vctSortList.get(index) == null)
      {
         // @TODO  Throw exception for item not found.
      }
      return (ListItem)(vctSortList.get(index));
   }

   /**
    * Remove the list item associated with this String sort value. <p> NOTE:  If multiple matches are found, all are removed.
    * @param    sStringSortValue    The sort value (String) of the item to remove.
    */
   public ListItem remove(String sStringSortValue)
   {
      for (int C = 0; C <= vctSortList.size() - 1; C++)
      {
         ListItem item = (ListItem)(vctSortList.get(C));
         if (item.getStringSortValue().equals(sStringSortValue))
         {
            // Match found.  Remove from the list.
            vctSortList.remove((Object)item);
            return item;
         }
      }
      return null;
   }

   /**
    * Remove the list item associated with this int sort value. <p> NOTE:  If multiple matches are found, all are removed.
    * @param    nIntSortValue    The sort value (int) of the item to remove.
    */
   public ListItem remove(int nIntSortValue)
   {
      for (int C = 0; C <= vctSortList.size() - 1; C++)
      {
         ListItem item = (ListItem)(vctSortList.get(C));
         if (item.getIntSortValue() == nIntSortValue)
         {
            // Match found.  Remove from the list.
            vctSortList.remove((Object)item);
            return item;
         }
      }
      return null;
   }

   /**
    * Remove the list item associated with this long sort value. <p> NOTE:  If multiple matches are found, all are removed.
    * @param    lLongSortValue    The sort value (long) of the item to remove.
    */
   public ListItem remove(long lLongSortValue)
   {
      for (int C = 0; C <= vctSortList.size() - 1; C++)
      {
         ListItem item = (ListItem)(vctSortList.get(C));
         if (item.getLongSortValue() == lLongSortValue)
         {
            // Match found.  Remove from the list.
            vctSortList.remove((Object)item);
            return item;
         }
      }
      return null;
   }

   /**
    * Remove the list item associated with this object reference. <p> NOTE:  If multiple matches are found, all are removed.
    * @param    oObjectReference    The object reference of the item to remove.
    */
   public ListItem remove(Object oObjectReference)
   {
      for (int C = 0; C <= vctSortList.size() - 1; C++)
      {
         ListItem item = (ListItem)(vctSortList.get(C));
         if (item.getObjectReference() == oObjectReference)
         {
            // Match found.  Remove from the list.
            vctSortList.remove((Object)item);
            return item;
         }
      }
      return null;
   }

   /**
    * Remove this list item.
    * @param    item    An object that has implemented the WSListItem interface.
    */
   public ListItem remove(ListItem item)
   {
      vctSortList.remove((Object)item);
      return item;
   }

   /**
    * Remove all list items.
    */
   public void removeAllElements()
   {
      vctSortList.removeAllElements();
   }

   /**
    * Make a new copy of the Vector and return to the calling method.
    */
   public Object clone()
   {
      return vctSortList.clone();
   }

   /**
    * Make a new copy of the Vector and return to the calling method.
    */
   public Vector cloneAsVector()
   {
      return (Vector)(vctSortList.clone());
   }
   
}
