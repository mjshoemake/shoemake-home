//
// file: WSEnumerationTypeOwner.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/EnumerationTypeOwner.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////
// Witness imports
// **************************************************************************
// IF THIS IMPORT STATEMENT CHANGES PLEASE NOTIFY THE TEAM LEAD SO THE CLASS
// DIAGRAMS CAN BE UPDATED.
// **************************************************************************

package mjs.core.types;


/**
 * This is the interface to be implemented for any object that: <p> <pre>
* 1. contains a descendent of WSEnumerationType
* 2. needs to be notified when the value of that enumerated object changes
* </pre>
 */
public interface EnumerationTypeOwner
{
   /**
    * Called by an enumeration type when the value changes.
    * @param   newValue    An object of type WSEnumerationType that contains the new value.
    */
   public void event_OnEnumerationValueChanged(EnumerationType newValue);
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/EnumerationTypeOwner.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:48:04   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:04   mshoemake
//  Initial revision.
//
//   Rev 1.1   Apr 22 2002 16:44:20   mshoemake
//Update to comments.


