//
// file: WSOwner.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/ComponentOwner.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components;


/**
 * This is the interface to be implemented for an object that needs to be notified when something inside a child object
 * changes or an action has either taken place or needs to take place. <p>
 * It is very generic in nature.  For most implementations, this design should be sufficient since the owner of the object
 * can then check to see what type of object it was that changed. <p> For some implementations, such as a toolbar, additional
 * information will be required (which button was clicked, etc.).
 * This interface can be used as a template for those types of needs.
 */
public interface ComponentOwner
{
   /**
    * Notify this owner object of a change that occured.
    * @param   source         The source object.
    * @param   nActionType    The type of action that occured or needs to occur.  This is totally dependent
    * upon the implementation.
    */
   public void notifyOfAction(Object source, int nActionType);
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/ComponentOwner.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:44:42   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:06   mshoemake
//  Initial revision.


