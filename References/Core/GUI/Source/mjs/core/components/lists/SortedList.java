//
// file: WSSortedVector.java
// proj: eQuality 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/SortedList.java-arc  $
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
import mjs.core.factories.ListItemFactory;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;


/**
 * This is the list class used by WSComboBox, WSRadioButton, WSList, and other classes to store a list of
 * items.  The sort functionality is built into the object, so the list could be sorted simply by
 * setting the "Sorted" property. <p> This class uses WSListItem as the encapsulator for the item to display.
 */
public class SortedList extends SortEngine
{
   private ListItemFactory itemFactory = null;

   /**
    * Constructor.
    */
   public SortedList(boolean bSorted, ListItemFactory factory)
   {
      super();
      // Sort when a new item is added?
      setSorted(bSorted);
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
            // Wrong type.
            // Create message object
            Message message = MessageFactory.createMessage( 10414,
                                                           "Balance",
                                                           "Evaluations",
                                                           "",
                                                           Message.INTERNAL,
                                                           InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                           mjs.core.utils.ComponentUtils.getLocale() );

            // Display message
            MessageDialogHandler.showMessage( message, null, "WSSortedVector" );
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
         // Create message object
         Message message = MessageFactory.createMessage( 10410,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSSortedVector" );
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
         // Create message object
         Message message = MessageFactory.createMessage( 10410,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSSortedVector" );
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
         // Create message object
         Message message = MessageFactory.createMessage( 10410,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSSortedVector" );
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
// $Log:
//  6    Balance   1.5         6/6/2003 8:40:20 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  5    Balance   1.4         3/7/2003 9:28:27 AM    Mike Shoemake   Removed
//       reference to forms.utils.ComponentUtils.  Replaced with
//       core.utils.ComponentUtils.  Not sure yet why the same method is in
//       both classes.
//  4    Balance   1.3         1/29/2003 4:47:15 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  3    Balance   1.2         1/17/2003 8:51:13 AM   Peter Lichtenwalner ONYX
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
//   Rev 1.6   May 21 2002 16:11:04   mshoemake
//Relocated ListItem factories to baseclasses.factories.
//
//   Rev 1.5   Dec 07 2001 08:48:24   mshoemake
//Update to WSExceptionFactory class.  Changed throwException() to handleException().


