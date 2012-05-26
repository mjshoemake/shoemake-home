//
// file: EnumerationTypeListener.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/events/EnumerationValueListener.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.events;

import mjs.core.types.EnumerationType;


/**
 * This listener should be implemented by any object that wants to listen for the change in value of an enumeration object.
 * <p> This is the replacement for the EnumerationTypeOwner interface.
 */
public interface EnumerationValueListener
{
   /**
    * Called by an enumeration type when the value changes.
    * @param   newValue    The new int value of the enumeration type.
    * @param   object      An object of type WSEnumerationType that contains the new value.
    */
   public void enumerationValueChanged(int newValue, EnumerationType object);

   /**
    * Called by an enumeration type before the value changes.  This method returns
    * a boolean value.  If the result is true, the change operation will continue.  If not,
    * the change operation will be aborted.
    * @param   previousValue  The original value before the change request.
    * @param   newValue       The new int value of the enumeration type.
    * @param   object         An object of type WSEnumerationType that contains the new value.
    */
   public boolean beforeEnumerationValueChanged(int previousValue, int newValue, EnumerationType object);
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/events/EnumerationValueListener.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:46:40   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:29:34   mshoemake
//  Initial revision.
//
//     Rev 1.1   May 22 2002 15:42:38   mshoemake
//  Updated the method names.
//
//   Rev 1.01   Feb 07 2002 16:11:54   mshoemake
//Initial revision.


