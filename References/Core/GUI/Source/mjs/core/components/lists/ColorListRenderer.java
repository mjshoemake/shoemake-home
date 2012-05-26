//
// file: WSColorListRenderer.java
// desc:
// proj: eQuality 6.3
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/lists/ColorListRenderer.java-arc  $
// $Author:Mike Shoemake$
// $Revision:7$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.lists;

// Core Java Imports
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
// Witness Systems Imports
import mjs.core.components.FlexibleBorder;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;
import mjs.core.utils.ColorIcon;
import mjs.core.utils.ColorFactory;


/**
 * The list renderer used to display a color picklist for Witness Systems combobox and list controls.
 */
public class ColorListRenderer extends JLabel implements ListCellRenderer
{
   /**
    * Outline border.
    */
   private Border colBlackBorder = BorderFactory.createLineBorder(ColorFactory.black, 2);

   /**
    * Empty border.
    */
   private Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);

   /**
    * The icon used to display the color.
    */
   private static ColorIcon icon = new ColorIcon();

   /**
    * The name of the color.
    */
   private static String sColor;

   /**
    * Constructor.
    */
   public ColorListRenderer()
   {
   }

   /**
    * Called by the JList component when a new cell needs to be rendered.
    * @param   list          The parent list control.
    * @param   value         The value to display (Color object).
    * @param   index         The index of the list.
    * @param   isSelected    Is this item selected?
    * @param   cellHasFocus  Is the current cell focused?
    */
   public Component getListCellRendererComponent
   (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
   {
      // Text color is always black.
      setForeground(ColorFactory.black);
      if (value instanceof ColorListItem)
      {
         // Get the list item.
         ColorListItem item = (ColorListItem)value;
         // Get the values from the list item.
         icon.setColor(item.getColor());
         sColor = item.toString();
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
         MessageDialogHandler.showMessage( message, null, "WSColorListRenderer" );
         // Create a new list item.
         ColorListItem item = new ColorListItem(ColorFactory.black);
         // Get the values from the list item.
         icon.setColor(item.getColor());
         sColor = item.toString();
      }
      // Set the values to display.
      setIcon(icon);
      setText(sColor);
      setForeground(ColorFactory.black);
      return this;
   }

   public void rendererRepaint()
   {
      setIcon(icon);
      setText(sColor);
      repaint();
   }
}
// $Log:
//  7    Balance   1.6         6/6/2003 8:40:20 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  6    Balance   1.5         3/7/2003 9:28:22 AM    Mike Shoemake   Removed
//       reference to forms.utils.ComponentUtils.  Replaced with
//       core.utils.ComponentUtils.  Not sure yet why the same method is in
//       both classes.
//  5    Balance   1.4         1/29/2003 4:47:14 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  4    Balance   1.3         1/17/2003 8:50:27 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  3    Balance   1.2         12/20/2002 10:05:05 AM Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  2    Balance   1.1         10/11/2002 8:54:24 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:45:44 PM   Mike Shoemake
// $

//
//     Rev 1.1   Oct 11 2002 08:54:24   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:45:44   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:28:22   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:12:14   mshoemake
//  Initial revision.


