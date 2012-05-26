//
// file: WSSwitchPane.java
// desc:
// proj: eQuality 6.1 or later
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/SwitchViewPane.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
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


/**
 * WSSwitchPane is a WSScrollPane descendent that allows the user to switch
 * between multiple panels, displaying one at a time.  This can be used for wizards, dynamic layouts, etc. New events:
 * event_BeforeShowComponent (Component comp)
 * @author   Mike Shoemake
 * @version  1.0
 * @date     2/1/2000
 */
public class SwitchViewPane extends ScrollPane
{
   /**
    * The current component to display.
    */
   private Component wsCurrent = null;

   /**
    * The list of components available for display. Only one can be displayed at a time.
    */
   private Vector vctComponents = new Vector();

   /**
    * The string representation of the name of the class.  This is used in debug messages not intended for the customer. <p>
    * NOTE:  The string returned by this method is not internationalized and should only be used in conjunction with:
    * 1.  WSMessageLog 2.  WSExceptionFactory
    */
   private String sClassTitle = "WSSwitchPane";

   /**
    * The minimum number of components in the list.  The remove() method
    * will only remove a component if there are at least this number of
    * components in the list.  The default is 0, allowing all components to be removed if requested. <p>
    * The removeAllElements() method overrides this functionality.
    */
   private int nMinComponents = 0;

   /**
    * Constructor.
    */
   public SwitchViewPane()
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
   public SwitchViewPane(long pObjectId)
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
      for (int C = 0; C <= vctComponents.size() - 1; C++)
      {
         Component comp = (Component)(vctComponents.get(C));
         vctComponents.remove((Object)comp);
         // Remove the component from this container.
         super.remove((java.awt.Component) comp);
         // Delete the component.
         comp.delete();
      }
   }

   /**
    * Overriding setBackground method.
    */
   public void setBackground(Color bg)
   {
      super.setBackground(bg);
      if (vctComponents != null)
      {
         for (int C = 0; C <= vctComponents.size() - 1; C++)
         {
            Component comp = (Component)(vctComponents.get(C));
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
      if (vctComponents != null)
      {
         for (int C = 0; C <= vctComponents.size() - 1; C++)
         {
            Component comp = (Component)(vctComponents.get(C));
            comp.setForeground(fg);
         }
      }
   }

   /**
    * Change the tab color for this component if the parent is a WSTabbedPane.
    */
   public void setTabColor(Color color)
   {
      if (this.getParent() instanceof TabbedPane)
      {
         TabbedPane parent = (TabbedPane)(this.getParent());
         int nIndex = parent.indexOfComponent(this);
         parent.setBackgroundAt(nIndex, color);
      }
   }

   /**
    * The number of child components.
    */
   public int getChildCount()
   {
      return vctComponents.size();
   }

   /**
    * Add this component to the list and make it the currently displayed component.
    */
   public java.awt.Component add(java.awt.Component comp)
   {
      Component wsComp = (Component)comp;
      vctComponents.add(wsComp);
      wsComp.setBackground(getBackground());
      wsComp.setForeground(getForeground());
      showComponent(wsComp);
      return (java.awt.Component) comp;
   }

   /**
    * The currently displayed component.
    */
   public void setDisplayedComponent(Component comp)
   {
      wsCurrent = comp;
   }

   /**
    * The currently displayed component.
    */
   public Component getDisplayedComponent()
   {
      return wsCurrent;
   }

   /**
    * Show this component as the currently displayed component.
    */
   public void showComponent(Component comp)
   {
      // Does this scheme belong to this section?
      if (vctComponents.contains(comp))
      {
         if (getDisplayedComponent() != null)
         { getViewport().remove((java.awt.Component) getDisplayedComponent()); }
         setViewportView((java.awt.Component) comp);
         setDisplayedComponent(comp);
         // EVENT.
         event_BeforeShowComponent(comp);
         this.updateUI();
      }
   }

   /**
    * EVENT. The component is about to be displayed.  Handle any actions that
    * need to happen before the component is displayed to the user.
    */
   public void event_BeforeShowComponent(Component comp) { }

   /**
    * If a component in this Vector is owned by this SwitchPane, show it as the currently displayed component.
    */
   public void showComponent(Vector componentsToShow)
   {
      for (int C = 0; C <= componentsToShow.size() - 1; C++)
      {
         Object obj = componentsToShow.get(C);
         if ((vctComponents.contains(obj)) && (obj instanceof Component))
         {
            Component comp = (Component)obj;
            showComponent(comp);
            return;
         }
      }
   }

   /**
    * Does this component belong to this SwitchPane?
    */
   public boolean contains(Component comp)
   {
      return vctComponents.contains(comp);
   }

   /**
    * Remove this component from the list. <p> NOTE:  This method may not remove the component if the MinimumComponents
    * property is set to a value greater than 0.
    */
   public void remove(Component comp)
   {
      // Don't reduce past the minumum number of components.
      if (vctComponents.size() > getMinimumComponents())
      {
         int nIndex = vctComponents.indexOf((Object)comp);
         if (nIndex == 0)
         {
            vctComponents.removeElement((Object)comp);
            setViewportView((java.awt.Component) (vctComponents.elementAt(nIndex)));
         }
         else
         {
            setViewportView((java.awt.Component) (vctComponents.elementAt(nIndex - 1)));
            vctComponents.removeElement((Object)comp);
         }
         updateUI();
      }
   }

   /**
    * Delete the child component.  If the component should be garbage collected, this is the preferred method of removal. <p>
    * NOTE:  This method may not remove the component if the MinimumComponents property is set to a value greater than 0.
    */
   public void deleteChild(Component comp)
   {
      // Don't reduce past the minumum number of components.
      if (vctComponents.size() > getMinimumComponents())
      {
         int nIndex = vctComponents.indexOf((Object)comp);
         if (nIndex == 0)
         {
            vctComponents.removeElement((Object)comp);
            setViewportView((java.awt.Component) (vctComponents.elementAt(nIndex)));
         }
         else
         {
            setViewportView((java.awt.Component) (vctComponents.elementAt(nIndex - 1)));
            vctComponents.removeElement((Object)comp);
         }
         comp.delete();
         updateUI();
      }
   }

   /**
    * Remove this component from the list. <p> NOTE:  This method may not remove the component if the MinimumComponents
    * property is set to a value greater than 0.
    */
   public void removeCurrentComponent()
   {
      Component comp = wsCurrent;
      remove(comp);
   }

   /**
    * The current component to display.
    */
   public Component getCurrentComponent()
   {
      if (wsCurrent != null)
      { return wsCurrent; }
      else { return (Component)(vctComponents.get(0)); }
   }

   /**
    * The list of components available for display. Only one can be displayed at a time.
    */
   public Vector getComponentsList()
   {
      if (vctComponents != null)
      { return (Vector)(vctComponents.clone()); }
      else { return new Vector(); }
   }

   /**
    * Remove all components.  This physically removes the currently
    * displayed component from the SwitchPane and removes all components
    * from the component list.  It does NOT call the delete method on each component. <p>
    * NOTE:  This method should NOT call the super class's version of
    * removeAll().  That method physically removes the viewport.
    */
   public void removeAll()
   {
      if (getDisplayedComponent() != null)
      { getViewport().remove((java.awt.Component) getDisplayedComponent()); }
      vctComponents.removeAllElements();
      wsCurrent = null;
   }

   /**
    * The minimum number of components in the list.  The remove() method
    * will only remove a component if there are at least this number of
    * components in the list.  The default is 0, allowing all components to be removed if requested. <p>
    * The removeAllElements() method overrides this functionality.
    */
   public int getMinimumComponents()
   {
      return nMinComponents;
   }

   /**
    * The minimum number of components in the list.  The remove() method
    * will only remove a component if there are at least this number of
    * components in the list.  The default is 0, allowing all components to be removed if requested. <p>
    * The removeAllElements() method overrides this functionality.
    */
   public void setMinimumComponents(int value)
   {
      nMinComponents = value;
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/SwitchViewPane.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:44:52   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:16   mshoemake
//  Initial revision.
//
//     Rev 1.1   Jan 17 2002 17:09:46   mshoemake
//  Fixed null pointer exception when creating and opening evaluations.
//
//     Rev 1.0   Dec 17 2001 11:05:34   mshoemake
//  Initial revision.


