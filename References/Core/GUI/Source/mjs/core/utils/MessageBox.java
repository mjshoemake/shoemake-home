//
// file: WSMessageBox.java
// desc: Window to display confirmation messages
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/MessageBox.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.utils;

// Import java classes
import java.awt.*;
import javax.swing.*;
// Witness imports
// **************************************************************************
// IF THIS IMPORT STATEMENT CHANGES PLEASE NOTIFY THE TEAM LEAD SO THE CLASS
// DIAGRAMS CAN BE UPDATED.
// **************************************************************************
import mjs.core.strings.InternationalizationStrings;


/**
 * This class is used for popup confirmation, error, or information messages in the client app or applet. <p>
 * This code was actually swiped from the WSOptionPaneWindow in 6.0
 * and the class name and method names were changed to make the code a little bit more readable. <p>
 * It extends from the JOptionPane swing class.
 */
public class MessageBox extends JOptionPane
{
   /**
    * This is a Yes or No confirmation message.  If the user selects "Yes",
    * the method returns true.  Otherwise, it returns false.
    */
   public static boolean yesNoMsg(Component parent, String title, String message)
   {
      // If no title specified, use "Confirmation".
      if (title == null)
      { title = InternationalizationStrings.szConfirmation; }
      Object[] obj = new Object[]
      {
         InternationalizationStrings.szYes, InternationalizationStrings.szNo
      };
      // Create the dialog.
      JOptionPane jop = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, obj,
      InternationalizationStrings.szNo);
      JDialog dialog = jop.createDialog(parent, title);
      //  Seems option pane doesn't get sized correctly so increase
      // the width and height by 10 percent.
      Dimension dim = dialog.getSize();
      Dimension newdim = new Dimension((int)(dim.width + (dim.width / 10)), (int)(dim.height + (dim.height / 10)));
      dialog.setSize(newdim);
      // Display the message.
      dialog.show();
      // Return true or false.
      Object selectedValue = jop.getValue();
      // Yes = true, No = false.
      if (selectedValue != null && selectedValue.equals(obj[0]))
      { return true; }
      else { return false; }
   }

   /**
    * This is a standard information message (OK button only).
    */
   public static void informationMsg(Component parent, String title, String message)
   {
      JOptionPane jop = new JOptionPane(message, INFORMATION_MESSAGE);
      JDialog dialog = jop.createDialog(parent, title);
      // If no title specified, use "Information"
      if (title == null)
      { title = InternationalizationStrings.szInformation; }
      //  Seems option pane doesn't get sized correctly so increase
      // the width and height by 10 percent.
      Dimension dim = dialog.getSize();
      Dimension newdim = new Dimension((int)(dim.width + (dim.width / 10)), (int)(dim.height + (dim.height / 10)));
      dialog.setSize(newdim);
      dialog.show();
   }

   /**
    * This is a standard error message (OK button only).
    */
   public static void errorMsg(Component parent, String title, String message)
   {
      JOptionPane jop = new JOptionPane(message, ERROR_MESSAGE);
      JDialog dialog = jop.createDialog(parent, title);
      //  Seems option pane doesn't get sized correctly so increase
      // the width and height by 10 percent.
      Dimension dim = dialog.getSize();
      // If no title specified, use "Error"
      if (title == null)
      { title = InternationalizationStrings.szError; }
      // TODO JDK 1.2 1.X does not have getWidth or getHeight()
      //Dimension newdim =  new Dimension( (int) (dim.getWidth() + (dim.getWidth() / 10) ),
      //                                   (int) (dim.getHeight() + (dim.getHeight() / 10)) );
      Dimension newdim = new Dimension((int)(dim.width + (dim.width / 10)), (int)(dim.height + (dim.height / 10)));
      dialog.setSize(newdim);
      dialog.show();
   }

   private MessageBox()
   {
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/MessageBox.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:48:28   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:26   mshoemake
//  Initial revision.
//
//   Rev 1.5   Apr 22 2002 16:44:20   mshoemake
//Update to comments.
//
//   Rev 1.4   Apr 16 2002 16:32:50   mshoemake
//Updated the import statement.


