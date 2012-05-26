//
// file: WSTabbedToolBar.java
// desc:
// proj: eQuality 6.3 or later
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/TabbedToolBar.java-arc  $
// $Author:Mike Shoemake$
// $Revision:8$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components;

// Java imports
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
// Witness imports
import mjs.core.events.ButtonEvent;
import mjs.core.events.ButtonListener;
import mjs.core.events.ButtonListenerList;
import mjs.core.events.ChildButtonMouseEvent;
import mjs.core.events.ChildButtonMouseListener;
import mjs.core.aggregation.ComponentHashtable;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;


/**
 * WSTabbedToolBar is a WSTabbedPane descendent that encapsulates the tabbed toolbar functionality.
 */
public class TabbedToolBar extends TabbedPane implements ButtonListener, ChildButtonMouseListener
{
   /**
    * The list of toolbars on this tabbed pane.
    */
   private ComponentHashtable htCompList = new ComponentHashtable();

   /**
    * The string representation of the name of the class.  This is used in debug messages not intended for the customer. <p>
    * NOTE:  The string returned by this method is not internationalized and should only be used in conjunction with:
    * 1.  WSMessageLog 2.  WSExceptionFactory
    */
   private String sClassTitle = "WSTabbedToolBar";

   /**
    * The list of button listeners to notify when a button click occurs.
    */
   private ButtonListenerList buttonListenerList = new ButtonListenerList();

   /**
    * Is the shift key held down?
    */
   private boolean bShiftPressed = false;

   /**
    * Constructor.
    */
   public TabbedToolBar()
   {
      try
      {
         spBaseInit();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Constructor.
    * @param   pButtonId   The unique, numeric ID used to distinguish the component from other components.
    */
   public TabbedToolBar(long pObjectId)
   {
      super(pObjectId);
      try
      {
         // Inialize component.
         spBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Initialize the component.
    */
   private void spBaseInit()
   {
      setTabPlacement(JTabbedPane.BOTTOM);
   }

   /**
    * The string representation of the name of the class.  This is used in debug messages not intended for the customer. <p>
    * NOTE:  The string returned by this method is not internationalized and should only be used in conjunction with: <pre>
   * 1.  WSMessageLog
   * 2.  WSExceptionFactory
   * </pre>
    */
   public String getClassName()
   {
      return sClassTitle;
   }


   // MOUSE EVENT METHODS
   /**
    * The event method that gets triggered when the mouse is released on a child
    * button.
    * <p>
    * This event is only triggered for ToggleButton objects.
    */
   public void buttonMouseReleased(ChildButtonMouseEvent e)
   {
      setShiftPressed(e.isShiftDown());
   }

   /**
    * The event method that gets triggered when the mouse is pressed on a child
    * button.
    * <p>
    * This event is only triggered for ToggleButton objects.
    */
   public void buttonMousePressed(ChildButtonMouseEvent e)
   {
      setShiftPressed(e.isShiftDown());
   }

   /**
    * The event method that gets triggered when the mouse is pressed on a child
    * button.
    * <p>
    * This event is only triggered for ToggleButton objects.
    */
   public void buttonMouseClicked(ChildButtonMouseEvent e)   {}

   /**
    * The event method that gets triggered when the mouse is pressed on a child
    * button.
    * <p>
    * This event is only triggered for ToggleButton objects.
    */
   public void buttonMouseEntered(ChildButtonMouseEvent e)  {}

   /**
    * The event method that gets triggered when the mouse is pressed on a child
    * button.
    * <p>
    * This event is only triggered for ToggleButton objects.
    */
   public void buttonMouseExited(ChildButtonMouseEvent e)   {}


   /**
    * Overriding the delete method in order to delete the child components.
    */
   public void delete()
   {
      super.delete();
      deleteAllChildren();
   }

   /**
    * Delete the children of this component.  If the components should be
    * garbage collected, this is the preferred method of removal.
    */
   public void deleteAllChildren()
   {
      // Delete the children
      Enumeration enum = (Enumeration)(htCompList.elements());
      while (enum.hasMoreElements())
      {
         java.awt.Component comp = (java.awt.Component) (enum.nextElement());
         super.remove(comp);
         if (comp instanceof Component)
         {
            Component wscomp = (Component)comp;
            htCompList.remove(wscomp.getObjectID());
            wscomp.delete();
         }
      }
   }

   /**
    * Overriding setBackground method.
    */
   public void setBackground(Color bg)
   {
      super.setBackground(bg);
      if (htCompList != null)
      {
         Enumeration enum = (Enumeration)(htCompList.elements());
         while (enum.hasMoreElements())
         {
            Component comp = (Component)(enum.nextElement());
            comp.setBackground(bg);
         }
      }
   }

   /**
    * Overriding setForeground method.
    */
   public void setForeground(Color fg)
   {
      super.setForeground(fg);
      if (htCompList != null)
      {
         Enumeration enum = (Enumeration)(htCompList.elements());
         while (enum.hasMoreElements())
         {
            Component comp = (Component)(enum.nextElement());
            comp.setForeground(fg);
         }
      }
   }

   /**
    * Called when a button is clicked by the user.  This is a WSButtonOwner interface-related event.  The toolbars actually
    * call the buttonClicked() event in order to keep this event method
    * available for developers to use for custom event handlers.
    * @param   e              The action event associated with the button click.
    * @param   sDisplayText   The display text of the button.  For WSToggleButton objects this is the tooltip text.
    * @param   nObjectID      The unique numeric ID associated with the button.
    */
   public void event_OnButtonClicked(ButtonEvent e)  {}

   /**
    * Called by the toolbar when a button is clicked by the user.
    */
   public void buttonClicked(ButtonEvent e)
   {
      // EVENT.
      event_OnButtonClicked(e);
      // Notify the listeners.
      buttonListenerList.buttonClicked(e);
   }

   /**
    * Is the shift key held down?
    */
   public boolean isShiftPressed()
   {
      return bShiftPressed;
   }

   /**
    * Is the shift key held down?
    */
   public void setShiftPressed(boolean value)
   {
      bShiftPressed = value;
      // Update the child toolbar components.
      Enumeration enum = (Enumeration)(htCompList.elements());
      while (enum.hasMoreElements())
      {
         ToolBar toolbar = (ToolBar)(enum.nextElement());
         toolbar.setShiftPressed(bShiftPressed);
      }
   }

   /**
    * The number of child components.
    */
   public int getChildCount()
   {
      return htCompList.size();
   }

   /**
    * Add this toolbar component to the tabbed pane.
    */
   public Component add(Component comp)
   {
      if (comp instanceof ToolBar)
      {
         ToolBar toolbar = (ToolBar)comp;
         toolbar.addButtonListener(this);
         add(toolbar.getObjectName(), toolbar, toolbar.getObjectName());
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10422,
                                                       "Balance",
                                                       "Evaluations",
                                                       "",
                                                       Message.INTERNAL,
                                                       InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                       mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, sClassTitle );
      }
      return comp;
   }

   /**
    * Add a new toolbar component and return the object ID.
    */
   public long add(String tabtext)
   {
      ToolBar toolbar = new ToolBar();
      htCompList.add(toolbar.getObjectID(), toolbar);
      toolbar.setObjectName(tabtext);
      toolbar.addButtonListener(this);
      add(toolbar.getObjectName(), toolbar);
      return toolbar.getObjectID();
   }

   /**
    * Add this toolbar component to the tabbed pane.
    */
   public Component add(String tabtext, ToolBar toolbar, String hint)
   {
      htCompList.add(toolbar.getObjectID(), toolbar);
      toolbar.addButtonListener(this);
      addTab(tabtext, null, toolbar, hint);
      return (Component)toolbar;
   }

   /**
    * Add this toolbar component to the tabbed pane.
    */
   public Component add(String tabtext, ToolBar toolbar)
   {
      htCompList.add(toolbar.getObjectID(), toolbar);
      toolbar.addButtonListener(this);
      addTab(tabtext, null, toolbar, tabtext);
      return (Component)toolbar;
   }

   /**
    * Add a new toolbar component and return the object ID.
    */
   public long add(String tabtext, long objectID)
   {
      ToolBar toolbar = new ToolBar(objectID);
      htCompList.add(toolbar.getObjectID(), toolbar);
      toolbar.addButtonListener(this);
      toolbar.setObjectName(tabtext);
      addTab(toolbar.getObjectName(), null, toolbar, toolbar.getObjectName());
      return toolbar.getObjectID();
   }

   /**
    * Remove this component from the list. <p> NOTE:  This method may not remove the component if the MinimumComponents
    * property is set to a value greater than 0.
    */
   public void remove(java.awt.Component component)
   {
      super.remove(component);
      // Remove from the hashtable.
      if (component instanceof mjs.core.components.Component)
      {
         Component wsComp = (Component)component;
         wsComp.removeListener(this);
         htCompList.remove(wsComp.getObjectID());
      }
   }

   /**
    * Adds the button listener to the component.
    * @param l  The button listener.
    */
   public void addButtonListener(ButtonListener l)
   {
      addToListenerList(l);
      buttonListenerList.add(l);
   }

   public void removeButtonListener(ButtonListener l)
   {
      buttonListenerList.remove(l);
      removeFromListenerList(l);
   }

   /**
    * Adds the event listener to the component.  This method is very generic
    * and allows listeners to be added without having to find out the
    * listener type.  The addListener method calls the correct method for the listener type.
    */
   public void addListener(EventListener listener)
   {
      if (listener instanceof ButtonListener)
      {
         // ButtonListener
         addButtonListener((ButtonListener)listener);
      }
      else
      {
         // Call addListener() of baseclass.
         super.addListener(listener);
      }
   }

   /**
    * Removes the event listener to the component.  This method is very generic
    * and allows listeners to be removed without having to find out the
    * listener type.  The removeListener method calls the correct method for the listener type.
    */
   public void removeListener(EventListener listener)
   {
      if (listener instanceof ButtonListener)
      {
         // ButtonListener
         removeButtonListener((ButtonListener)listener);
      }
      else
      {
         // Call removeListener() of baseclass.
         super.removeListener(listener);
      }
   }

   /**
    * The list of components that have been added to this container.
    */
   public Vector getComponentsList()
   {
      Vector vctReturn = new Vector();
      if (htCompList != null)
      {
         Enumeration enum = htCompList.elements();
         // Populate the return Vector.
         while (enum.hasMoreElements())
         {
            Object obj = enum.nextElement();
            vctReturn.add(obj);
         }
      }
      return vctReturn;
   }

   /**
    * Remove all components.  This physically removes the currently
    * displayed component from the SwitchPane and removes all components
    * from the component list.  It does NOT call the delete method on each component.
    */
   public void removeAll()
   {
      super.removeAll();
      htCompList.clear();
   }
}
// $Log:
//  8    Balance   1.7         6/6/2003 8:40:20 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  7    Balance   1.6         3/7/2003 9:28:17 AM    Mike Shoemake   Removed
//       reference to forms.utils.ComponentUtils.  Replaced with
//       core.utils.ComponentUtils.  Not sure yet why the same method is in
//       both classes.
//  6    Balance   1.5         1/29/2003 4:47:13 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  5    Balance   1.4         1/17/2003 8:51:18 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  4    Balance   1.3         11/25/2002 3:21:02 PM  Mike Shoemake   Update to
//       the listener model.
//  3    Balance   1.2         11/25/2002 9:57:24 AM  Mike Shoemake   Modified
//       the listener model to make it more consistent with what Java does and
//       to make it easier to use.  Also phased out ButtonOwner interface.
//  2    Balance   1.1         10/11/2002 8:54:32 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:44:54 PM   Mike Shoemake
// $

//
//     Rev 1.3   Nov 25 2002 15:21:02   mshoemake
//  Update to the listener model.
//
//     Rev 1.2   Nov 25 2002 09:57:24   mshoemake
//  Modified the listener model to make it more consistent with what Java does and to make it easier to use.  Also phased out ButtonOwner interface.
//
//     Rev 1.1   Oct 11 2002 08:54:32   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:44:54   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:16   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:05:34   mshoemake
//  Initial revision.


