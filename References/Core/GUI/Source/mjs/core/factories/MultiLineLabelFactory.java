//
// file: WSMultiLineLabelFactory.java
// proj: eQuality 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/factories/MultiLineLabelFactory.java-arc  $
// $Author:Mike Shoemake$
// $Revision:2$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.factories;

// Witness imports
import mjs.core.components.Label;
import mjs.core.components.MultiLineLabel;


/**
 * This is the factory to produce multi-line labels.
 */
public class MultiLineLabelFactory implements LabelFactory
{
   /**
    * Constructor.
    */
   public MultiLineLabelFactory()
   {
   }

   /**
    * Create a new label component.
    */
   public Label createLabel()
   {
      return (Label)(new MultiLineLabel());
   }

   /**
    * Create a new label component.
    * @param  lObjectID   The numeric ID that is associated with this component.
    */
   public Label createLabel(long lObjectID)
   {
      return (Label)(new MultiLineLabel(lObjectID));
   }

   // TOGETHERSOFT DIAGRAM DEPENDENCIES.  DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# MultiLineLabel lnkMultiLineLabel; */
}
// $Log:
//  2    Balance   1.1         3/28/2003 2:48:13 PM   Mike Shoemake   Added
//       Together diagram dependencies.
//  1    Balance   1.0         8/23/2002 2:47:20 PM   Mike Shoemake
// $
//
//     Rev 1.0   Aug 23 2002 14:47:20   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:59:34   mshoemake
//  Initial revision.
//
//     Rev 1.0   May 21 2002 10:56:30   mshoemake
//  Initial revision.
//
//     Rev 1.0   Dec 17 2001 11:05:32   mshoemake
//  Initial revision.


