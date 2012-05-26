//
// file: WSColorTableCellRenderer.java
// desc:
// proj: ER 6.3
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/tables/ColorTableEditor.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.tables;

// Java imports
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// Witness imports
import mjs.core.components.lists.ColorListRenderer;
import mjs.core.components.lists.ColorListItem;
import mjs.core.utils.ColorFactory;


/**
 * The base class for Witness Systems combobox table cell editors.
 */
public class ColorTableEditor extends ComboBoxTableEditor
{
   /**
    * Constructor.
    */
   public ColorTableEditor()
   {
      // Prepare the list renderer.
      ColorListRenderer renderer = new ColorListRenderer();
      setRenderer(renderer);
      // Prepare the picklist.
      Vector vctColorList = new Vector();
      vctColorList.add(new ColorListItem(ColorFactory.black));
      vctColorList.add(new ColorListItem(ColorFactory.blue));
      vctColorList.add(new ColorListItem(ColorFactory.cyan));
      vctColorList.add(new ColorListItem(ColorFactory.darkGray));
      vctColorList.add(new ColorListItem(ColorFactory.gray));
      vctColorList.add(new ColorListItem(ColorFactory.green));
      vctColorList.add(new ColorListItem(ColorFactory.lightGray));
      vctColorList.add(new ColorListItem(ColorFactory.magenta));
      vctColorList.add(new ColorListItem(ColorFactory.orange));
      vctColorList.add(new ColorListItem(ColorFactory.pink));
      vctColorList.add(new ColorListItem(ColorFactory.red));
      vctColorList.add(new ColorListItem(ColorFactory.white));
      vctColorList.add(new ColorListItem(ColorFactory.yellow));
      setItemList(vctColorList);
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/tables/ColorTableEditor.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:46:06   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 24 2002 13:18:00   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:28:54   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:12:14   mshoemake
//  Initial revision.


