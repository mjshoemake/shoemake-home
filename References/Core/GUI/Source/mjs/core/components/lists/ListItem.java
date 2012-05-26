//
// file: WSListItem.java
// proj: eQuality 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/ListItem.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.lists;

import java.util.*;


/**
 * This is the object used by WSComboBox, WSRadioButton, WSList, and other classes when displaying a list of
 * items.  The sort functionality is built into the object, so the the list could be sorted simply by setting a property.
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
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/ListItem.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:45:46   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:28:24   mshoemake
//  Initial revision.


