//
// file: AbstractEvent.java
// proj: ER 6.4 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/events/ButtonAdapter.java-arc  $
// $Author:Mike Shoemake$
// $Revision:2$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.events;


/**
 * This is an adapter class that implements the ButtonListener interface.
 * <p>
 * Any component (panel, dialog, etc.) that contains and received ActionPerformed
 * events from buttons can accept ButtonListeners.  This allows button events to
 * be received without having to add the listener to each button individually.
 * It allows abstraction by treating a dialog or button panel as a black box.
 *
 * @author    mshoemake
 * @created   November 22, 2002
 */
public class ButtonAdapter implements ButtonListener
{
   /**
    * An event method to implement to receive messages when a button is clicked.
    *
    * @param e  The ButtonEvent object that describes the action that occured.
    */
   public void buttonClicked(ButtonEvent e) { }

   /**
    * @link dependency
    */
   /*# ButtonEvent lnkButtonEvent; */
}

// $Log:
//  2    Balance   1.1         3/28/2003 9:01:06 AM   Mike Shoemake   Added
//       Togethersoft diagram dependencies.
//  1    Balance   1.0         11/25/2002 10:02:00 AM Mike Shoemake
// $
//
//     Rev 1.0   Nov 25 2002 10:02:00   mshoemake
//  Initial revision.
//
//     Rev 1.0   Nov 22 2002 08:56:26   mshoemake
//  Creating initial revision of class.
//

