//
// file: WSSortEngine.java
// proj: eQuality 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/SortEngine.java-arc  $
// $Author:Mike Shoemake$
// $Revision:6$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.lists;

// Java imports
import java.util.*;
// Witness imports
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;


/**
 * This sort allows the user to sort on ints, longs, or strings, and allows the user to receive back a sorted list of either
 * ints, longs, strings, or objects sorted by any of the three. <p>
 * This class should not be used directly.  Use the WSSortedVector class instead (WSSortedVector extends WSSortEngine).
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
         // Create message object
         Message message = MessageFactory.createMessage( 10412,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSSort" );
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
      if (nSortType == -1)
      { return false; }
      // Clear the error flag.
      bError = false;
      // Begin sorting.
      while ((nCurrent <= vctSortList.size() - 2) && (bContinue))
      {
         itemCurrent = (ListItem)(vctSortList.get(nCurrent));
         itemNext = (ListItem)(vctSortList.get(nCurrent + 1));
         if (!isCorrectOrder(itemCurrent, itemNext))
         {
            // Not correct order.  Need to relocate the current.
            vctSortList.removeElementAt(nCurrent + 1);
            if (nCurrent == 0)
            {
               // Just stick it at the top of the list.
               vctSortList.insertElementAt(itemNext, 0);
            }
            else
            {
               addToList(itemNext);
            }
         }
         else { nCurrent++; }
      }
      if (!bError)
      {
         /* Turn on auto-sort for any new items that are added after
            this sort process.  */

         bAutoSort = true;
      }
      return (!bError);
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
         // Create message object
         Message message = MessageFactory.createMessage( 10415,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSSort" );
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
// $Log:
//  6    Balance   1.5         6/6/2003 8:40:21 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  5    Balance   1.4         3/7/2003 9:13:35 AM    Jeff Giesler    Fixed
//       rerference problem.
//  4    Balance   1.3         1/29/2003 4:47:15 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  3    Balance   1.2         1/17/2003 8:51:15 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  2    Balance   1.1         10/11/2002 8:54:26 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:45:48 PM   Mike Shoemake
// $

//
//     Rev 1.1   Oct 11 2002 08:54:26   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:45:48   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:28:26   mshoemake
//  Initial revision.
//
//   Rev 1.12   Mar 22 2002 08:43:42   mshoemake
//Performed a little cleanup.
//
//   Rev 1.11   Feb 12 2002 10:12:56   hfaynzilberg
//added  ArrayIndexOutOfBoundsException handling
//
//   Rev 1.10   Dec 07 2001 08:48:24   mshoemake
//Update to WSExceptionFactory class.  Changed throwException() to handleException().


