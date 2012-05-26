//
// file: CommonResourceBundle.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/strings/CommonResourceBundle.java-arc  $
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
import java.util.Hashtable;
import java.util.ResourceBundle;


/**
 * This is the resource bundle for all internationalized strings used by classes
 * under common.
 */
public class CoreResourceBundle extends ResourceBundle
{
   /**
    * Mappings between Key words and Localized Strings.
    */
   protected Hashtable resources = new Hashtable();

   // Date and Time formats
   public static final String DateFormat = "DateFormat";
   public static final String TimeFormat = "TimeFormat";
   public static final String DateTimeFormat = "DateTimeFormat";

   /**
    * Define all resources
    */
   public CoreResourceBundle()
   {
      // Add the strings to the hashtable.
      resources.put(DateFormat, "MM/dd/yyyy");
      resources.put(TimeFormat, "hh:mm:ss a");
      resources.put(DateTimeFormat, "MM/dd/yyyy hh:mm:ss a");
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

}
