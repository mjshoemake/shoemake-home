//
// file: WSRectangleUtils.java
// desc:
// proj: ER 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/RectangleUtils.java-arc  $
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
import java.util.*;
import javax.swing.*;


/**
 * WSRectangleUtils is a utility class used to do various rectangle-based
 * tasks, handling overlapping rectangles, components, etc.
 * @author   Mike Shoemake
 * @version  1.0
 * @date     5/27/2001
 */
public class RectangleUtils
{
   /**
    * The string representation of the class.
    */
   private static String sClassTitle = "WSRectangleUtils";

   /**
    * Utility method.  Combine these two rectangles into one rectangle that encompasses both.
    */
   public static Rectangle combineRectangles(Rectangle rec1, Rectangle rec2)
   {
      if ((rec1 != null) && (rec2 != null))
      {
         int nLeft = (int)Math.min(rec1.getX(), rec2.getX());
         int nTop = (int)Math.min(rec1.getY(), rec2.getY());
         int nWidth = ((int)Math.max(rec1.getX() + rec1.getWidth(), rec2.getX() + rec2.getWidth())) - nLeft;
         int nHeight = ((int)Math.max(rec1.getY() + rec1.getHeight(), rec2.getY() + rec2.getHeight())) - nTop;
         return new Rectangle(nLeft, nTop, nWidth, nHeight);
      }
      else if (rec1 != null)
      {
         // Just return rec1.
         return rec1;
      }
      else if (rec2 != null)
      {
         // Just return rec2.
         return rec2;
      }
      else { return null; }
   }

   /**
    * Utility method.  Do these two rectangles overlap?
    */
   public static boolean isOverlapping(Rectangle rec1, Rectangle rec2)
   {
      boolean overlapX = rectanglesOverlapping_XAxis(rec1, rec2);
      boolean overlapY = rectanglesOverlapping_YAxis(rec1, rec2);
      return (overlapX && overlapY);
   }

   /**
    * Utility method.  Do these two components overlap?
    */
   public static boolean isOverlapping(JComponent comp1, JComponent comp2)
   {
      return isOverlapping(comp1.getBounds(), comp2.getBounds());
   }

   /**
    * Private utility method.  Do these two rectangles overlap horizontally? <p>
    * NOTE:  This method will still return true if the two rectangles are positioned
    * one above the other even though they don't completely overlap.
    */
   private static boolean rectanglesOverlapping_XAxis(Rectangle rec1, Rectangle rec2)
   {
      boolean bOverlapping = false;
      if ((rec1 == null) || (rec2 == null))
      { return false; }
      int nLeft1 = (int)(rec1.getX());
      int nRight1 = (int)(rec1.getX()) + (int)(rec1.getWidth());
      int nLeft2 = (int)(rec2.getX());
      int nRight2 = (int)(rec2.getX()) + (int)(rec2.getWidth());
      // Find the intersections. If two lines coming from a rectangle corner
      // both have intersection points then there IS overlap.
      boolean bLeft1_Intersects = false;
      boolean bRight1_Intersects = false;
      boolean bLeft2_Intersects = false;
      boolean bRight2_Intersects = false;
      // Are there intersections based on rectangle #1?
      if ((nLeft1 > nLeft2) && (nLeft1 < nRight2))
      { bLeft1_Intersects = true; }
      if ((nRight1 > nLeft2) && (nRight1 < nRight2))
      { bRight1_Intersects = true; }
      // Are there intersections based on rectangle #2?
      if ((nLeft2 > nLeft1) && (nLeft2 < nRight1))
      { bLeft2_Intersects = true; }
      if ((nRight2 > nLeft1) && (nRight2 < nRight1))
      { bRight2_Intersects = true; }
      // Do we overlap or not?
      if (bLeft1_Intersects || bLeft2_Intersects || bRight1_Intersects || bRight2_Intersects)
      {
         bOverlapping = true;
      }
      return bOverlapping;
   }

   /**
    * Private utility method.  Do these two rectangles overlap vertically? <p>
    * NOTE:  This method will still return true if the two rectangles are positioned
    * beside each other even though they don't completely overlap.
    */
   private static boolean rectanglesOverlapping_YAxis(Rectangle rec1, Rectangle rec2)
   {
      boolean bOverlapping = false;
      if ((rec1 == null) || (rec2 == null))
      { return false; }
      int nTop1 = (int)(rec1.getY());
      int nBottom1 = (int)(rec1.getY()) + (int)(rec1.getHeight());
      int nTop2 = (int)(rec2.getY());
      int nBottom2 = (int)(rec2.getY()) + (int)(rec2.getHeight());
      // Find the intersections. If two lines coming from a rectangle corner
      // both have intersection points then there IS overlap.
      boolean bTop1_Intersects = false;
      boolean bBottom1_Intersects = false;
      boolean bTop2_Intersects = false;
      boolean bBottom2_Intersects = false;
      // Are there intersections based on rectangle #1?
      if ((nTop1 > nTop2) && (nTop1 < nBottom2))
      { bTop1_Intersects = true; }
      if ((nBottom1 > nTop2) && (nBottom1 < nBottom2))
      { bBottom1_Intersects = true; }
      // Are there intersections based on rectangle #2?
      if ((nTop2 > nTop1) && (nTop2 < nBottom1))
      { bTop2_Intersects = true; }
      if ((nBottom2 > nTop1) && (nBottom2 < nBottom1))
      { bBottom2_Intersects = true; }
      // Do we overlap or not?
      if (bTop1_Intersects || bTop2_Intersects || bBottom1_Intersects || bBottom2_Intersects)
      {
         bOverlapping = true;
      }
      return bOverlapping;
   }

   /**
    * Return a rectangle that encapsulates all rectangles in the Vector.
    * @param     vctRectangles    The list of rectangles.
    */
   public static Rectangle getRectangleListBounds(Vector vctRectangles)
   {
      // Call the version that takes an Enumeration object.
      return getRectangleListBounds(vctRectangles.elements());
   }

   /**
    * Return a rectangle that encapsulates all rectangles in the Enumeration.
    * @param     enumRectangles    The list of rectangles.
    */
   public static Rectangle getRectangleListBounds(Enumeration enumRectangles)
   {
      double nMinLeft = 20000;
      double nMinTop = 20000;
      double nMaxWidth = -1;
      double nMaxHeight = -1;
      // Process for rectangle in the list.
      while (enumRectangles.hasMoreElements())
      {
         Rectangle rBounds = (Rectangle)(enumRectangles.nextElement());
         // Find new rectangle dimensions
         if (rBounds.getX() < nMinLeft)
         { nMinLeft = rBounds.getX(); }
         if (rBounds.getY() < nMinTop)
         { nMinTop = rBounds.getY(); }
         if (rBounds.getX() + rBounds.getWidth() > nMaxWidth)
         { nMaxWidth = rBounds.getX() + rBounds.getWidth(); }
         if (rBounds.getY() + rBounds.getHeight() > nMaxHeight)
         { nMaxHeight = rBounds.getY() + rBounds.getHeight(); }
      }
      return new Rectangle((int)nMinLeft, (int)nMinTop, (int)(nMaxWidth - nMinLeft), (int)(nMaxHeight - nMinTop));
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/RectangleUtils.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:48:28   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:28   mshoemake
//  Initial revision.
//
//   Rev 1.20   Mar 10 2000 14:41:46   RSrinivasan
//tags added


