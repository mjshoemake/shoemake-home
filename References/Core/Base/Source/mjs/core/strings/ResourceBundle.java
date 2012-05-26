//
// file: ResourceBundle.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/strings/ResourceBundle.java-arc  $
// $Author:Mike Shoemake$
// $Revision:6$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.strings;

// Java imports
import java.util.Enumeration;

// MJS imports
import mjs.core.aggregation.Hashtable;

/**
 * This is the resource bundle for all internationalized strings used by classes
 * under common.
 */
public class ResourceBundle extends java.util.ResourceBundle
{

   /**
    * Mappings between Key words and Localized Strings.
    */
   protected Hashtable resources = new Hashtable();

   /**
    * Define all resources
    */
   public ResourceBundle(String resourceFilePath)
   {
      // Add message strings
      mapResourceStrings();
   }

   /**
    * Returns an Enumeration of all Message Bundle Keys.
    *
    * @return Enumeration of all Message Bundle Keys.
    */
   public Enumeration getKeys()
   {
      return resources.keys();
   }

   /**
    * Returns the localized statement mapped to the Key string provided.
    *
    * @param key Message Bundle Keyword to retrieve a localized statement.
    *
    * @return Localized statement mapped to the Key string provided.
    *
    */
   protected Object handleGetObject(String key)
   {
      return resources.get(key);
   }

   /**
    *  Maps message IDs to localized message strings and stores in hash table.
    */
   private void mapResourceStrings()
   {
   }

}
