//
// file: WSBorder.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/FlexibleBorder.java-arc  $
// $Author:Mike Shoemake$
// $Revision:2$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components;

// Java imports
import java.awt.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
// Witness imports
import mjs.core.types.EnumerationTypeOwner;
import mjs.core.types.EnumerationType;
import mjs.core.types.BorderType;


/**
 * WSBorder:  The border to use when displaying a component. All components should have two border
 * types and an external border.
 * The external border is set using the setBorder() method of the
 * component.  The external border defaults to being always displayed outside the WSBorder.
 */
public class FlexibleBorder
{
   /**
    * The inner border.
    */
   private BorderType bdrInner = new BorderType();

   /**
    * The outer border.
    */
   private BorderType bdrOuter = new BorderType();

   /**
    * Is the external border displayed on the outside of this border?
    * The alternative is to display the external border on the inside.
    */
   private boolean bExternalDisplayedOutside = true;

   /**
    * Constructor. <p> Default Summary Type is FORM_TOTAL.
    */
   public FlexibleBorder(EnumerationTypeOwner owner)
   {
      bdrInner.setOwner(owner);
      bdrOuter.setOwner(owner);
   }

   /**
    * The inner border.
    */
   public BorderType getInnerBorder()
   {
      return bdrInner;
   }

   /**
    * The outer border.
    */
   public BorderType getOuterBorder()
   {
      return bdrOuter;
   }

   /**
    * The text to use if the border is a titled border.
    */
   public void setTitleText(String text)
   {
      bdrInner.setTitleText(text);
      bdrOuter.setTitleText(text);
   }

   /**
    * The text to use if the border is a titled border.
    */
   public String getTitleText()
   {
      return bdrInner.getTitleText();
   }

   /**
    * The color to use if the border is a line border.
    */
   public Color getColor()
   {
      return bdrInner.getColor();
   }

   /**
    * Inner bevel color.  Default is white.
    */
   public Color getInnerBevelColor()
   {
      return bdrInner.getInnerBevelColor();
   }

   /**
    * Inner bevel color.  Default is dark gray.
    */
   public Color getOuterBevelColor()
   {
      return bdrInner.getOuterBevelColor();
   }

   /**
    * The line width to use if the border is a line border.
    */
   public int getLineThickness()
   {
      return bdrInner.getLineThickness();
   }

   /**
    * The color to use if the border is a line border.
    */
   public void setColor(Color c)
   {
      bdrInner.setColor(c);
      bdrOuter.setColor(c);
   }

   /**
    * Inner bevel color.  Default is white.
    */
   public void setInnerBevelColor(Color c)
   {
      bdrInner.setInnerBevelColor(c);
      bdrOuter.setInnerBevelColor(c);
   }

   /**
    * Inner bevel color.  Default is dark gray.
    */
   public void setOuterBevelColor(Color c)
   {
      bdrInner.setOuterBevelColor(c);
      bdrOuter.setOuterBevelColor(c);
   }

   /**
    * The line width to use if the border is a line border.
    */
   public void setLineThickness(int nwidth)
   {
      bdrInner.setLineThickness(nwidth);
      bdrOuter.setLineThickness(nwidth);
   }

   /**
    * Is the external border displayed on the outside of this border?
    * The alternative is to display the external border on the inside.
    */
   public boolean isExternalBorderDisplayedOutside()
   {
      return bExternalDisplayedOutside;
   }

   /**
    * Is the external border displayed on the outside of this border?
    * The alternative is to display the external border on the inside.
    */
   public void setExternalBorderDisplayedOutside(boolean value)
   {
      bExternalDisplayedOutside = value;
   }

   /**
    * Create the border for this component.
    */
   public javax.swing.border.Border createBorder(Component wsComp, javax.swing.border.Border externalBorder)
   {
      javax.swing.border.Border mainBorder = null;
      // Create the main border.
      if ((bdrInner.getValue() == BorderType.BORDER_NONE) && (bdrOuter.getValue() == BorderType.BORDER_NONE))
      {
         // Return the external border.  No main border.
         return externalBorder;
      }
      else if (bdrInner.getValue() == BorderType.BORDER_NONE)
      {
         // Outer only.
         mainBorder = createBorder(bdrOuter, wsComp);
      }
      else if (bdrOuter.getValue() == BorderType.BORDER_NONE)
      {
         // Inner only.
         mainBorder = createBorder(bdrInner, wsComp);
      }
      else
      {
         // Inner and Outer.
         mainBorder = BorderFactory.createCompoundBorder(createBorder(bdrOuter, wsComp), createBorder(bdrInner, wsComp));
      }
      // Combine the main border with the external border.
      if (externalBorder == null)
      {
         // Create the outer border only.
         return mainBorder;
      }
      else if (bExternalDisplayedOutside)
      {
         // Create the outer border inside the external border.
         return BorderFactory.createCompoundBorder(externalBorder, mainBorder);
      }
      else
      {
         // Create the external border inside the outer border.
         return BorderFactory.createCompoundBorder(mainBorder, externalBorder);
      }
   }

   /**
    * Create the specified border.
    */
   private javax.swing.border.Border createBorder(BorderType type, Component wsComp)
   {
      int nBorderType = type.getValue();
      switch (nBorderType)
      {
         case(BorderType.BORDER_NONE):
            return null;
         case(BorderType.BORDER_LOWERED_BEVEL):
            return BorderFactory.createBevelBorder(BevelBorder.LOWERED, type.getInnerBevelColor(),
            type.getOuterBevelColor());
         case(BorderType.BORDER_RAISED_BEVEL):
            return BorderFactory.createBevelBorder(BevelBorder.RAISED, type.getInnerBevelColor(),
            type.getOuterBevelColor());
         case(BorderType.BORDER_TITLED):
            return createTitledBorder(wsComp);
         case(BorderType.BORDER_LINE):
            return BorderFactory.createLineBorder(type.getColor(), type.getLineThickness());
      }
      return null;
   }

   /**
    * Create the titled border for this component.
    */
   private javax.swing.border.Border createTitledBorder(Component wsComp)
   {
      javax.swing.border.Border lineBorder = BorderFactory.createBevelBorder(0, getInnerBevelColor(), getOuterBevelColor(), getOuterBevelColor(), getInnerBevelColor());
      TitledBorder bdr = (TitledBorder)(BorderFactory.createTitledBorder(getTitleText()));
      bdr.setTitleColor(wsComp.getForeground());
      bdr.setTitleFont(wsComp.getFont());
      bdr.setBorder(lineBorder);
      return (javax.swing.border.Border) bdr;
   }

   // TOGETHERSOFT DEPENDENCY.  DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# Component lnkComponent; */

}
// $Log:
//  2    Balance   1.1         3/28/2003 2:48:11 PM   Mike Shoemake   Added
//       Together diagram dependencies.
//  1    Balance   1.0         8/23/2002 2:44:42 PM   Mike Shoemake
// $
//
//     Rev 1.0   Aug 23 2002 14:44:42   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:06   mshoemake
//  Initial revision.


