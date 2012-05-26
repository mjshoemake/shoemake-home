//
// file: WSButtonAlignmentType.java
// proj: eQuality 6.1 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/ButtonAlignmentType.java-arc  $
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
 * WSButtonAlignmentType:  The type of alignment used when placing buttons on a button panel.
 */
public class ButtonAlignmentType extends EnumerationType
{
   /**
    * Horizontal:  The components are lined up left to right across the panel.
    */
   public static final int HORIZONTAL = 0;

   /**
    * Vertical:  The components are lined up top to bottom down the panel.
    */
   public static final int VERTICAL = 1;

   /**
    * Constructor. <p> Default value is CENTER.
    */
   public ButtonAlignmentType()
   {
      super(HORIZONTAL);
   }

   /**
    * Constructor. <p> Default value is CENTER.
    * @param  owner           The owner of this object.
    */
   public ButtonAlignmentType(EnumerationTypeOwner owner)
   {
      super(HORIZONTAL);
      this.setOwner(owner);
   }

   /**
    * Constructor.
    * @param  nDefaultValue   The default int value.
    */
   public ButtonAlignmentType(int nDefaultValue)
   {
      super(nDefaultValue);
   }

   /**
    * Constructor.
    * @param  nDefaultValue   The default int value.
    * @param  owner           The owner of this object.
    */
   public ButtonAlignmentType(int nDefaultValue, EnumerationTypeOwner owner)
   {
      super(nDefaultValue);
      this.setOwner(owner);
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
      return 1;
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/types/ButtonAlignmentType.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:48:02   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:02   mshoemake
//  Initial revision.


