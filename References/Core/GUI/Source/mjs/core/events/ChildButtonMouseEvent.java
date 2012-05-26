//
// file: ButtonEvent.java
// proj: ER 6.4 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/events/ChildButtonMouseEvent.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.events;

// Java imports
import java.awt.Component;
import java.awt.event.MouseEvent;


/**
 * When a buttonClicked() event is triggered on the ButtonListener, a ButtonEvent
 * is created.  The ButtonEvent contains information about the button that was
 * clicked, including the reference to the button itself.
 * <p>
 * Any component (panel, dialog, etc.) that contains and received ActionPerformed
 * events from buttons can accept ButtonListeners.  This allows button events to
 * be received without having to add the listener to each button individually.
 * It allows abstraction by treating a dialog or button panel as a black box.
 *
 * @author    mshoemake
 * @created   November 22, 2002
 */
public class ChildButtonMouseEvent extends MouseEvent
{
   /**
    * The ID for the button.
    */
   long                  buttonID = -1;

   /**
    * The button caption text.
    */
   String                buttonCaption = "<Undefined>";


   /**
    * Constructor.
    *
    * @param source         The source object for this event.
    * @param buttonID       The ID for the button.
    * @param buttonCaption  The caption text for the button.
    */
   public ChildButtonMouseEvent(MouseEvent e, long buttonID, String buttonCaption)
   {
      super((Component)(e.getSource()), e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
      this.buttonID = buttonID;
      this.buttonCaption = buttonCaption;
   }


   /**
    * The ID for the button.
    */
   public long getButtonID()
   {
      return buttonID;
   }


   /**
    * The button caption text.
    */
   public String getButtonCaption()
   {
      return buttonCaption;
   }
}

// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/events/ChildButtonMouseEvent.java-arc  $
//
//     Rev 1.0   Nov 25 2002 11:30:38   mshoemake
//  Initial revision.
//
//     Rev 1.0   Nov 25 2002 10:02:02   mshoemake
//  Initial revision.
//
//     Rev 1.0   Nov 22 2002 08:56:26   mshoemake
//  Creating initial revision of class.
//
