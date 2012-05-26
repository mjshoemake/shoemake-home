//
// file: ButtonListener.java
// proj: ER 6.4 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/events/ChildButtonMouseListener.java-arc  $
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
 * This is the interface to be implemented when a button owned by a container
 * component receives a mouse event that needs to be propegated beyond the parent.
 * This makes it clear to the listener that the mouse event occured on the button
 * and not on the parent itself.
 *
 * @author    mshoemake
 * @created   November 22, 2002
 */
public interface ChildButtonMouseListener extends EventListener
{
   /**
    * A child button received a MouseClicked event.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseClicked(ChildButtonMouseEvent e);

   /**
    * A child button owned by the listener received a MouseEntered event.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseEntered(ChildButtonMouseEvent e);

   /**
    * A child button owned by the listener received a MouseExited event.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseExited(ChildButtonMouseEvent e);

   /**
    * A child button received a MouseReleased event.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMousePressed(ChildButtonMouseEvent e);

   /**
    * A child button owned by the listener received a MouseReleased event.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseReleased(ChildButtonMouseEvent e);

   /**
    * @link dependency
    */
   /*# ChildButtonMouseEvent lnkChildButtonMouseEvent; */
}

// $Log:
//  2    Balance   1.1         3/28/2003 9:01:21 AM   Mike Shoemake   Added
//       Togethersoft diagram dependencies.
//  1    Balance   1.0         11/25/2002 11:30:40 AM Mike Shoemake
// $
//
//     Rev 1.0   Nov 25 2002 11:30:40   mshoemake
//  Initial revision.
//
//     Rev 1.0   Nov 25 2002 10:02:02   mshoemake
//  Initial revision.
//
//     Rev 1.0   Nov 22 2002 08:56:26   mshoemake
//  Creating initial revision of class.
//
