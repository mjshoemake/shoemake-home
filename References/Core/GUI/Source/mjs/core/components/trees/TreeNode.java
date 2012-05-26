//
// file: WSTreeNode.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/trees/TreeNode.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.1  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.trees;

// Java imports
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.*;
// Witness imports
import mjs.core.factories.ListItemFactory;
import mjs.core.components.IDGenerator;
import mjs.core.components.lists.ListItem;
import mjs.core.components.lists.SortedList;
import mjs.core.components.lists.SortEngine;


/**
 * WSTreeNode:  This is the base class for Witness Systems treenodes.
 */
public abstract class TreeNode extends DefaultMutableTreeNode
{
   /**
    * An integer object ID.  This can be either specified in the constructor, set
    * using the set method, or the baseclass will auto generate a number that is unique for the class.
    */
   private long nObjectID = 0;

   /**
    * A user defined object type.  This can be used in conjunction with integer
    * constants defined by the developer.  It is not used internally for anything and is very flexible.
    */
   private int nObjectType = 0;

   /**
    * The name associated with the object if applicable.
    */
   private String sObjectName = "";

   /**
    * Text representation of the node type.
    */
   private String sNodeType = "<Use setNodeType()>";

   /**
    * Should the popup menu display to the user when the component is right clicked?
    */
   private boolean bPopupMenuEnabled = true;

   /**
    * A string representation of the class title.
    */
   private String sClassTitle = "WSTreeNode";

   /**
    * The list of tree nodes.  All sort functionality is embedded in the WSSortedVector.
    */
   private SortedList itemList = null;

   /**
    * Constructor.
    * @param   factory     The tree node factory.
    */
   public TreeNode(ListItemFactory factory)
   {
      this(factory, generateID());
   }

   /**
    * Constructor.
    * @param   factory     The tree node factory.
    * @param   pObjectID   The numeric ID.
    */
   public TreeNode(ListItemFactory factory, long pObjectID)
   {
      try
      {
         itemList = new SortedList(false, factory);
         setObjectID(pObjectID);
         tnBaseInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Initialization (called by constructor).
    */
   private void tnBaseInit() throws Exception
   {
   }

   /**
    * Generate a unique ID with respect to this class for this component.
    * ie.  Each object of this type will have an ID that sets it apart from the other objects.
    */
   private static long generateID()
   {
      return IDGenerator.generateID();
   }
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * Called by the delete() method before any processing takes place.
    */
   public void event_OnDelete() { }
   // ****** Functionality methods ******

   /**
    * Delete this component.
    */
   public void delete()
   {
      // EVENT
      event_OnDelete();
      for (int C = getChildCount() - 1; C >= 0; C--)
      {
         MutableTreeNode child = (MutableTreeNode)(getChildAt(C));
         if (child instanceof TreeNode)
         {
            TreeNode wsChild = (TreeNode)child;
            wsChild.delete();
         }
      }
      removeAllChildren();
      if (this.getParent() instanceof MutableTreeNode)
      {
         MutableTreeNode parent = (MutableTreeNode)(getParent());
         parent.remove(this);
      }
   }

   /**
    * Is the list sorted?
    */
   public void setSorted(boolean sorted)
   {
      itemList.setSorted(sorted);
      updateListModel();
   }

   /**
    * Is the list sorted?
    */
   public boolean isSorted()
   {
      return itemList.isSorted();
   }

   /**
    * Update the list model.  This should be called when something changes in the list.
    */
   private void updateListModel()
   {
      removeAllChildren();
      for (int C = 0; C <= itemList.size() - 1; C++)
      {
         MutableTreeNode node = (MutableTreeNode)(itemList.get(C));
         // Add the items in sorted order.
         super.add(node);
      }
   }

   /**
    * This updates the picklist when changes occur.
    */
   public void refreshItemList()
   {
      updateListModel();
   }

   /**
    * Populate with a Vector of WSTreeNode (WSListItem) objects. <p>
    * NOTE:  If the Vector contains anything other than WSListItem objects an
    * exception will be thrown and the method will exit.
    */
   public void setItemList(Vector items)
   {
      itemList.setItemList(items);
      updateListModel();
   }

   /**
    * Get the selected item.  Returns the first selected value or null, if the selection is empty.
    */
   public int getItemCount()
   {
      return getChildCount();
   }

   /**
    * Add a child tree node to this tree node.
    * @param     newChild    The WSTreeNode to add.
    */
   public void add(MutableTreeNode newChild)
   {
      if (newChild instanceof ListItem)
      {
         ListItem item = (ListItem)newChild;
         itemList.add(item);
      }
      updateListModel();
   }

   /**
    * Add a child tree node to this tree node.
    * @param     newChild    The WSTreeNode to add.
    */
   public void remove(MutableTreeNode aChild)
   {
      if (aChild instanceof ListItem)
      {
         ListItem item = (ListItem)aChild;
         itemList.remove(item);
      }
      updateListModel();
   }

   /**
    * Returns a copy of the item Vector. <p> Changes to the returned Vector do not affect the actual Vector found in
    * the WSSortedVector class.
    */
   public Vector getItemList()
   {
      return itemList.cloneAsVector();
   }

   /**
    * Is it ok to display the popup menu?
    */
   public boolean isPopupMenuEnabled()
   {
      return bPopupMenuEnabled;
   }

   /**
    * Is it ok to display the popup menu?
    */
   public void setPopupMenuEnabled(boolean value)
   {
      bPopupMenuEnabled = value;
   }

   /**
    * The display text of this tree node. <p> This method actually calls getObjectName().
    */
   public String getText()
   {
      return getObjectName();
   }

   /**
    * The display text of this tree node. <p> This method actually calls setObjectName().
    */
   public void setText(String sText)
   {
      setObjectName(sText);
   }

   /**
    * The integer object ID.  This can be either specified in the constructor, set
    * using the set method, or the baseclass will auto generate a number that is unique for the class.
    */
   public long getObjectID()
   {
      return nObjectID;
   }

   /**
    * The integer object ID.  This can be either specified in the constructor, set
    * using the set method, or the baseclass will auto generate a number that is unique for the class.
    */
   public void setObjectID(long value)
   {
      nObjectID = value;
   }

   /**
    * A user defined object type.  This can be used in conjunction with integer
    * constants defined by the developer.  It is not used internally for anything and is very flexible.
    */
   public int getObjectType()
   {
      return nObjectType;
   }

   /**
    * A user defined object type.  This can be used in conjunction with integer
    * constants defined by the developer.  It is not used internally for anything and is very flexible.
    */
   public void setObjectType(int value)
   {
      nObjectType = value;
   }

   /**
    * The name associated with the object if applicable.
    */
   public String getObjectName()
   {
      return sObjectName;
   }

   /**
    * The name associated with the object if applicable.
    */
   public void setObjectName(String value)
   {
      sObjectName = value;
      setUserObject(value);
   }

   /**
    * The display text of this tree node. <p> This method actually calls setObjectName().
    */
   public String toString()
   {
      return getObjectName();
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/trees/TreeNode.java-arc  $
//
//     Rev 1.1   Oct 11 2002 08:54:38   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:46:28   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:29:18   mshoemake
//  Initial revision.
//
//     Rev 1.1   May 21 2002 16:09:38   mshoemake
//  Relocated ListItem factories to baseclasses.factories.
//
//     Rev 1.0   Dec 17 2001 11:13:52   mshoemake
//  Initial revision.


