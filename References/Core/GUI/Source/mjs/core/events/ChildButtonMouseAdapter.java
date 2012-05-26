//
// file: AbstractEvent.java
// proj: ER 6.4 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/events/ChildButtonMouseAdapter.java-arc  $
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
public class ChildButtonMouseAdapter implements ChildButtonMouseListener
{
   /**
    * A child button received a MouseClicked event.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseClicked(ChildButtonMouseEvent e) {}

   /**
    * A child button owned by the listener received a MouseEntered event.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseEntered(ChildButtonMouseEvent e) {}

   /**
    * A child button owned by the listener received a MouseExited event.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseExited(ChildButtonMouseEvent e) {}

   /**
    * A child button received a MouseReleased event.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMousePressed(ChildButtonMouseEvent e) {}

   /**
    * A child button owned by the listener received a MouseReleased event.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseReleased(ChildButtonMouseEvent e) {}

   /**
    * @link dependency
    */
   /*# ChildButtonMouseEvent lnkChildButtonMouseEvent; */
}

// $Log:
//  2    Balance   1.1         3/28/2003 9:01:19 AM   Mike Shoemake   Added
//       Togethersoft diagram dependencies.
//  1    Balance   1.0         11/25/2002 11:30:38 AM Mike Shoemake
// $
//
//     Rev 1.0   Nov 25 2002 11:30:38   mshoemake
//  Initial revision.
//
//     Rev 1.0   Nov 25 2002 10:02:00   mshoemake
//  Initial revision.
//
//     Rev 1.0   Nov 22 2002 08:56:26   mshoemake
//  Creating initial revision of class.
//

