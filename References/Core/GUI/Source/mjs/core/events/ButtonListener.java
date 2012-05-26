//
// file: ButtonListener.java
// proj: ER 6.4 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/events/ButtonListener.java-arc  $
// $Author:Mike Shoemake$
// $Revision:2$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.events;

// Java imports
import java.util.EventListener;

/**
 * This is the interface to be implemented when receiving button click events
 * from a base button, button panel, dialog, etc.  The ButtonListener is tailored
 * specifically for these types of components.
 * <p>
 * Any component (panel, dialog, etc.) that contains and received ActionPerformed
 * events from buttons can accept ButtonListeners.  This allows button events to
 * be received without having to add the listener to each button individually.
 * It allows abstraction by treating a dialog or button panel as a black box.
 *
 * @author    mshoemake
 * @created   November 22, 2002
 */
public interface ButtonListener extends EventListener
{
   /**
    * An event method to implement to receive messages when a button is clicked.
    *
    * @param e  The ButtonEvent object that describes the action that occured.
    */
   void buttonClicked(ButtonEvent e);

   /**
    * @link dependency
    */
   /*# ButtonEvent lnkButtonEvent; */
}

// $Log:
//  2    Balance   1.1         3/28/2003 9:01:19 AM   Mike Shoemake   Added
//       Togethersoft diagram dependencies.
//  1    Balance   1.0         11/25/2002 10:02:02 AM Mike Shoemake
// $
//
//     Rev 1.0   Nov 25 2002 10:02:02   mshoemake
//  Initial revision.
//
//     Rev 1.0   Nov 22 2002 08:56:26   mshoemake
//  Creating initial revision of class.
//
