//
// file: WSStringListItemFactory.java
// proj: eQuality 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/factories/StringListItemFactory.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.factories;

// Witness imports
import mjs.core.components.lists.StringListItem;
import mjs.core.components.lists.ListItem;


/**
 * This is the interface to be implemented when creating a factory used to produce list items or sort items. <p>
 * This is a utility class for use by baseclasses only.
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
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/factories/StringListItemFactory.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:47:22   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:59:36   mshoemake
//  Initial revision.
//
//     Rev 1.1   May 22 2002 08:44:40   mshoemake
//  Fixed the package statements.
//
//     Rev 1.0   May 21 2002 16:12:56   mshoemake
//  Initial revision.


