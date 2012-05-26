//
// file: WSLeafTreeNode.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/trees/LeafTreeNode.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.trees;

// Witness imports
import mjs.core.factories.ListItemFactory;


/**
 * WSFolderTreeNode:  This is the base class for all non-folder treenodes (leaf nodes).
 */
public abstract class LeafTreeNode extends TreeNode
{
   /**
    * Constructor.
    * @param   factory     The tree node factory.
    */
   public LeafTreeNode(ListItemFactory factory)
   {
      super(factory);
   }

   /**
    * Constructor.
    * @param   factory     The tree node factory.
    * @param   pObjectID   The numeric ID.
    */
   public LeafTreeNode(ListItemFactory factory, long pObjectID)
   {
      super(factory, pObjectID);
   }

   /**
    * The display text of this tree node.
    */
   public abstract String getObjectName();

   /**
    * The display text of this tree node.
    */
   public abstract void setObjectName(String sNewName);
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/trees/LeafTreeNode.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:46:26   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:29:16   mshoemake
//  Initial revision.
//
//     Rev 1.1   May 21 2002 16:09:38   mshoemake
//  Relocated ListItem factories to baseclasses.factories.
//
//     Rev 1.0   Dec 17 2001 11:13:50   mshoemake
//  Initial revision.


