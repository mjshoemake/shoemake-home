//
// file: WSColorIcon.java
// desc:
// proj: eQuality 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/ColorIcon.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.utils;

// Java imports
import java.awt.*;
import javax.swing.*;
// Witness imports
import mjs.core.utils.ColorFactory;


/**
 * An icon used to display color options for witness systems & combobox and listbox color chooser renderers.
 */
public class ColorIcon implements Icon
{
   /**
    * The color to display.
    */
   private Color color = ColorFactory.gray;

   /**
    * The display width of the icon.
    */
   private int width = 15;

   /**
    * The display height of the icon.
    */
   private int height = 15;

   /**
    * Constructor.
    */
   public ColorIcon()
   {
   }

   /**
    * Constructor.
    * @param    color    The color to display.
    * @param    width    The width of the icon.
    * @param    height   The height of the icon.
    */
   public ColorIcon(Color color, int width, int height)
   {
      this.color = color;
      this.width = width;
      this.height = height;
   }

   /**
    * Draw the new color icon at this position.
    */
   public void paintIcon(Component c, Graphics g, int x, int y)
   {
      g.setColor(ColorFactory.black);
      g.drawRect(x, y, width - 1, height - 1);
      g.setColor(color);
      g.fillRect(x + 1, y + 1, width - 2, height - 2);
   }

   /**
    * The color to display.
    */
   public final Color getColor()
   {
      return color;
   }

   /**
    * The color to display.
    */
   public final void setColor(Color color)
   {
      this.color = color;
   }

   /**
    * The display width of the icon.
    */
   public int getIconWidth()
   {
      return width;
   }

   /**
    * The display width of the icon.
    */
   public void setIconWidth(int width)
   {
      this.width = width;
   }

   /**
    * The display height of the icon.
    */
   public int getIconHeight()
   {
      return height;
   }

   /**
    * The display height of the icon.
    */
   public void setIconHeight(int height)
   {
      this.height = height;
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/ColorIcon.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:48:26   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:24   mshoemake
//  Initial revision.
//
//   Rev 1.1   Mar 10 2000 14:49:30   RSrinivasan
//tags added


