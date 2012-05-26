//
// file: WSLabeledComboBox.java
// desc:
// proj: ER 6.0
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/LabeledComboBox.java-arc  $
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
import javax.swing.*;
// Witness imports
import mjs.core.administration.SystemDefaults;
import mjs.core.factories.LabelFactory;
import mjs.core.components.lists.ComboBox;


/**
 * WSLabeledComboBox is the base class for Witness Systems labeled JComboBox descendents.
 * It comes with a label associated with it which can be used or ignored.
 * The label was added to the implementation for the Evaluations project.  If the developer
 * doesn't want to use the associated label they can simply use "deleteAssociatedLabel()" to
 * clear it from memory or inherit from WSComboBox instead.
 */
public class LabeledComboBox extends ComboBox
{
   /**
    * The associated label for this component.
    */
   private Label lblAssociatedLabel = null;

   /**
    * Constructor.
    */
   public LabeledComboBox(LabelFactory factory)
   {
      super();
      lblAssociatedLabel = factory.createLabel();
      lblAssociatedLabel.setRelatedComponent(this);
      // Set the default values for the label.
      setAssociatedLabelDefaults();
   }

   /**
    * Set the default dimensions, font, and color of the label that is associated with a labeled
    * component such as WSCQuestionCombo, WSCLinkItem, etc.  This method is called by the constructor.
    */
   private void setAssociatedLabelDefaults()
   {
      setAssociatedLabelLeft(Component.DEFAULT_LABEL_LEFT);
      setAssociatedLabelTop(Component.DEFAULT_LABEL_TOP);
      setAssociatedLabelHeight(Component.DEFAULT_LABEL_HEIGHT);
      setAssociatedLabelWidth(Component.DEFAULT_LABEL_WIDTH);
      lblAssociatedLabel.setFont(SystemDefaults.getDefaultFont());
      setAssociatedLabelBackground(SystemDefaults.getDefaultNonEditBackground());
      setAssociatedLabelForeground(SystemDefaults.getDefaultForeground());
   }

   /**
    * Delete this component.
    */
   public void delete()
   {
      super.delete();
      deleteAssociatedLabel();
   }
   // ****** Associated Label ******

   /**
    * Delete the label that is associated with a labeled component such as WSCQuestionCombo,
    * WSCLinkItem, etc.  This method also removes the label from it's parent if there is a parent.
    */
   public void deleteAssociatedLabel()
   {
      if (lblAssociatedLabel != null)
      {
         //Check to see if the label has been added to a container...
         java.awt.Container parent = lblAssociatedLabel.getParent();
         if (parent != null)
         {
            // Remove from parent.
            parent.remove((java.awt.Component) lblAssociatedLabel);
         }
      }
      lblAssociatedLabel = null;
   }

   /**
    * The label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public Label getAssociatedLabel()
   {
      return lblAssociatedLabel;
   }

   /**
    * The display text of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public String getAssociatedLabelText()
   {
      return lblAssociatedLabel.getText();
   }

   /**
    * The display text of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public void setAssociatedLabelText(String sText)
   {
      lblAssociatedLabel.setText(sText);
   }

   /**
    * The left position of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public int getAssociatedLabelLeft()
   {
      return lblAssociatedLabel.getLeft();
   }

   /**
    * The left position of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public void setAssociatedLabelLeft(int nLeft)
   {
      lblAssociatedLabel.setBounds(nLeft, lblAssociatedLabel.getTop(), lblAssociatedLabel.getWidth(),
      lblAssociatedLabel.getHeight());
   }

   /**
    * The top position of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public int getAssociatedLabelTop()
   {
      return lblAssociatedLabel.getTop();
   }

   /**
    * The top position of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public void setAssociatedLabelTop(int ntop)
   {
      lblAssociatedLabel.setBounds(lblAssociatedLabel.getLeft(), ntop, lblAssociatedLabel.getWidth(),
      lblAssociatedLabel.getHeight());
   }

   /**
    * The width of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public int getAssociatedLabelWidth()
   {
      return lblAssociatedLabel.getWidth();
   }

   /**
    * The width of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public void setAssociatedLabelWidth(int nwidth)
   {
      lblAssociatedLabel.setBounds(lblAssociatedLabel.getLeft(), lblAssociatedLabel.getTop(),
      nwidth, lblAssociatedLabel.getHeight());
   }

   /**
    * The height of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public int getAssociatedLabelHeight()
   {
      return lblAssociatedLabel.getHeight();
   }

   /**
    * The height of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public void setAssociatedLabelHeight(int nheight)
   {
      lblAssociatedLabel.setBounds(lblAssociatedLabel.getLeft(), lblAssociatedLabel.getTop(), lblAssociatedLabel.getWidth(), nheight);
   }

   /**
    * The font name of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public String getAssociatedLabelFontName()
   {
      return lblAssociatedLabel.getFont().getFamily();
   }

   /**
    * The font name of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public void setAssociatedLabelFontName(String sfontName)
   {
      lblAssociatedLabel.setFont(
      new Font(sfontName, lblAssociatedLabel.getFont().getStyle(), lblAssociatedLabel.getFont().getSize()));
   }

   /**
    * The font style of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public int getAssociatedLabelFontStyle()
   {
      return lblAssociatedLabel.getFont().getStyle();
   }

   /**
    * The font style of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public void setAssociatedLabelFontStyle(int nfontStyle)
   {
      lblAssociatedLabel.setFont(
      new Font(lblAssociatedLabel.getFont().getFamily(), nfontStyle, lblAssociatedLabel.getFont().getSize()));
   }

   /**
    * The font size of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public int getAssociatedLabelFontSize()
   {
      return lblAssociatedLabel.getFont().getSize();
   }

   /**
    * The font size of the label that is associated with a labeled component such as WSCQuestionCombo, WSCLinkItem, etc.
    */
   public void setAssociatedLabelFontSize(int nFontSize)
   {
      lblAssociatedLabel.setFont(
      new Font(lblAssociatedLabel.getFont().getFamily(), lblAssociatedLabel.getFont().getStyle(), nFontSize));
   }

   /**
    * The foreground color of the label that is associated with a labeled component such as
    * WSCQuestionCombo, WSCLinkItem, etc.
    */
   public Color getAssociatedLabelForeground()
   {
      return lblAssociatedLabel.getForeground();
   }

   /**
    * The foreground color of the label that is associated with a labeled component such as
    * WSCQuestionCombo, WSCLinkItem, etc.
    */
   public void setAssociatedLabelForeground(Color CcaptionForegroundColor)
   {
      lblAssociatedLabel.setForeground(CcaptionForegroundColor);
   }

   /**
    * The background color of the label that is associated with a labeled component such as
    * WSCQuestionCombo, WSCLinkItem, etc.
    */
   public Color getAssociatedLabelBackground()
   {
      return lblAssociatedLabel.getBackground();
   }

   /**
    * The background color of the label that is associated with a labeled component such as
    * WSCQuestionCombo, WSCLinkItem, etc.
    */
   public void setAssociatedLabelBackground(Color CcaptionBackgroundColor)
   {
      lblAssociatedLabel.setBackground(CcaptionBackgroundColor);
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/LabeledComboBox.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:44:44   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:08   mshoemake
//  Initial revision.
//
//     Rev 1.3   May 21 2002 10:55:16   mshoemake
//  Relocated the label factories from baseclasses.components to baseclasses.factories.
//
//     Rev 1.2   Apr 17 2002 10:10:48   mshoemake
//  Updated import statement.
//
//     Rev 1.1   Feb 13 2002 16:24:42   sputtagunta
//  Fixed 11782 : For labels, font Arial Black Italic 12 comes back as just Arial Italic 12.
//
//     Rev 1.0   Dec 17 2001 11:05:30   mshoemake
//  Initial revision.


