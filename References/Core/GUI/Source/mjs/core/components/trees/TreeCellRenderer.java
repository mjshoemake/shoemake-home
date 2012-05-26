//
// file: WSTreeCellRenderer.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/trees/TreeCellRenderer.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.trees;

// Java imports
import java.awt.*;
import javax.swing.tree.DefaultTreeCellRenderer;


/**
 * WSTreeCellRenderer:  The cell renderer for the WSTree component.
 */
public class TreeCellRenderer extends DefaultTreeCellRenderer
{
   /**
    * Constructor.
    */
   public TreeCellRenderer()
   {
      try
      {
         jbInit();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Initialization.
    */
   private void jbInit() throws Exception
   {
      this.setLayout(null);
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/trees/TreeCellRenderer.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:46:26   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:29:18   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:13:52   mshoemake
//  Initial revision.


