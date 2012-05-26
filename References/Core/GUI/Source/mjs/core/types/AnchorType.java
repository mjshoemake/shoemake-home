//
// file: WSAnchorType.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/AnchorType.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.types;

// Witness imports


/**
 * WSAnchorType:  The type of anchor (left, top, right, bottom) that components that have anchor layouts use.
 */
public class AnchorType extends EnumerationType
{
   /**
    * AnchorLeft:  The first component is on the far left, the last component takes up the remaining space.
    */
   public static final int ANCHOR_LEFT = 0;

   /**
    * AnchorRight:  The first component is on the far left, the first component takes up the remaining space.
    */
   public static final int ANCHOR_RIGHT = 1;

   /**
    * DistributeLeftToRight:  The components are aligned from left to right
    * with the label widths evenly distributed based on the widths assigned.
    * As the panel width changes, the label widths change proportionately.
    */
   public static final int DISTRIBUTE_LEFT_TO_RIGHT = 2;

   /**
    * Constructor. Default Anchor Type is Distribute-Left-To-Right.
    */
   public AnchorType()
   {
      super(DISTRIBUTE_LEFT_TO_RIGHT);
   }

   /**
    * Constructor.
    * @param  nDefaultValue   The default int value.
    */
   public AnchorType(int nDefaultValue)
   {
      super(nDefaultValue);
   }

   /**
    * The minimum value allowed.
    */
   public int getMinimumValue()
   {
      return 0;
   }

   /**
    * The maximum value allowed.
    */
   public int getMaximumValue()
   {
      return 2;
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/AnchorType.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:48:02   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:02   mshoemake
//  Initial revision.


