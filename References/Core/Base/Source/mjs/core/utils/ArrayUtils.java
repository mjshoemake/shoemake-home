//
// file: WSArrayUtils.java
// desc:
// proj: ER 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/ArrayUtils.java-arc  $
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


/**
 * WSArrayUtils is a utility class used to do various array-based tasks, including converting arrays to Vector objects.
 * @author   Mike Shoemake
 * @version  1.0
 * @date     5/27/2001
 */
public class ArrayUtils
{
   /**
    * The string representation of the class.
    */
   private static String sClassTitle = "WSArrayUtils";

   /**
    * Utility method.  Convert this array to a Vector object.
    */
   public static Vector arrayToVector(Object[] array)
   {
      Vector vector = new Vector();
      for (int C = 0; C <= array.length - 1; C++)
      {
         vector.add(array[C]);
      }
      return vector;
   }

   /**
    * Utility method.  Convert this array to a Vector object.
    */
   public static Vector arrayToVector(Component[] array)
   {
      Vector vector = new Vector();
      for (int C = 0; C <= array.length - 1; C++)
      {
         vector.add((Object)array[C]);
      }
      return vector;
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/ArrayUtils.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:48:24   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:22   mshoemake
//  Initial revision.
//
//   Rev 1.20   Mar 10 2000 14:41:46   RSrinivasan
//tags added


