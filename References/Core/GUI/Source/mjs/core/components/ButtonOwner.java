//
// file: WSButtonOwner.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/ButtonOwner.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.1  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components;

// Java imports
import java.awt.event.*;


/**
 * This is the interface to be implemented for any object that contains
 * WSButton objects and wants to be notified when the button is clicked.
 * Upon the button click by the user, the WSButton will call the
 * "event_OnButtonClicked()" event method of it's parent IF the parent has implemented this interface.
 */
public interface ButtonOwner
{
   /**
    * Called by a button on the panel when it is clicked by the user.  This method
    * should not be overridden.  Developers should use the event_OnButtonClicked event instead.
    * @param   e              The action event associated with the button click.
    * @param   sDisplayText   The display text of the button.
    * @param   nObjectID      The unique numeric ID associated with the button.
    */
   public void buttonClicked(ActionEvent e, String sDisplayText, long nObjectID);

   /**
    * The overrideable event triggered by a button click.
    * @param   e              The action event associated with the button click.
    * @param   sDisplayText   The display text of the button.
    * @param   nObjectID      The unique numeric ID associated with the button.
    */
   public void event_OnButtonClicked(ActionEvent e, String sDisplayText, long nObjectID);

   /**
    * The overrideable event triggered by a mouse pressed.
    * @param   e              The action event associated with the mouse pressed.
    */
   public void mousePressed(MouseEvent e);
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/ButtonOwner.java-arc  $
//
//     Rev 1.1   Nov 25 2002 10:00:00   mshoemake
//  PHASED OUT.  DO NOT USE.  Replaced by listener model in mjs.core.events
//
//     Rev 1.0   Aug 23 2002 14:44:40   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:04   mshoemake
//  Initial revision.
//
//     Rev 1.1   Jan 28 2002 13:44:50   hfaynzilberg
//  Added multi-drop functionality when "Shift" Key's pressed.
//
//     Rev 1.0   Dec 17 2001 09:13:56   mshoemake
//  Initial revision.


