//
// file: WSListItemFactory.java
// proj: eQuality 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/factories/ListItemFactory.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.factories;

// Witness imports
import mjs.core.components.lists.ListItem;


/**
 * This is the interface to be implemented when creating a factory used to produce list items or sort items.
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
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/factories/ListItemFactory.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:47:20   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:59:34   mshoemake
//  Initial revision.
//
//     Rev 1.1   May 22 2002 08:44:36   mshoemake
//  Fixed the package statements.
//
//     Rev 1.0   May 21 2002 16:12:56   mshoemake
//  Initial revision.


