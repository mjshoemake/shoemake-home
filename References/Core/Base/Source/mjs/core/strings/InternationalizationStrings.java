//
// file: WSCommonStrings.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/strings/InternationalizationStrings.java-arc  $
// $Author:Mike Shoemake$
// $Revision:7$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.strings;

// Witness imports
import mjs.core.utils.StringUtils;


/**
 * This is the resource access class for all of the classes under common.
 * Each project should have it's own resource manager which is similar
 * but separate from this file.
 * <p>
 * This class also handles the Exception messages, returning the
 * text that is associated with the error type.  It also allows for
 * parameters so that %1 and %2 can be replaced by text values before the
 * message is displayed.
 */
public class InternationalizationStrings
{
   /**
    * The generic one.  This will work for now.
    */
   public static String RESOURCE_CLASS = "mjs.core.strings.CoreResourceBundle";

   /**
    * The resource bundle manager to extract the strings.
    */
//   public static InternationalizationResource resource = InternationalizationResource.getInternationalizationResource();


//   public static final String szDateFormat = resource.getMessage(CoreResourceBundle.DateFormat, RESOURCE_CLASS);
//   public static final String szTimeFormat = resource.getMessage(CoreResourceBundle.TimeFormat, RESOURCE_CLASS);
//   public static final String szDateTimeFormat = resource.getMessage(CoreResourceBundle.DateTimeFormat, RESOURCE_CLASS);

   /**
    * Returns a message from the Resource Bundle provide by the
    * classpath.
    *
    * @param key String ID that identifies a Message in a Resource Bundle.
    * @param classPath Path the the location of the Resource Bundle.
    *
    * @return The Message String linked to the Key and ClassPath provide.
    */
/*
   public String getMessage( String key, String classPath )
   {
       ResourceBundle res = getBundle( classPath, null );
       String message = res.getString( key );
       return message;
   }
*/

   /**
    * Retrieves a Resource Bundle based on the classPath and locale
    * provide.
    *
    * @param classPath Path the the location of the Resource Bundle.
    * @param locale The object contain the Country/Language information
    *               used to determine which Resource Bundle to obtain
    *               a message.
    * @return The Resource Bundle based on the classPath and locale
    *         provided.
    */
/*   
   private ResourceBundle getBundle( String classPath, Locale locale )
   {
       ResourceBundle res = null;

       if ( classPath != null && locale != null )
       {
           res = ResourceBundle.getBundle( classPath, locale );
       }
       else if( classPath != null && locale == null )
       {
           res = ResourceBundle.getBundle( classPath );
       }
       return res;
   }
*/
   /**
    * Take the input string and replace %1 with value in param1.
    */
   public static String insertParams(String text, String param1)
   {
      if (param1 != null)
      {
         // Replace param #1.
         text = StringUtils.replaceText(param1, "%1", text);
      }
      // Return the text.
      return text;
   }

   /**
    * Take the input string and replace %1 with the value in param1, %2 with the
    * value in param2, etc.
    */
   public static String insertParams(String text, String param1, String param2)
   {
      if (param1 != null)
      {
         // Replace param #1.
         text = StringUtils.replaceText(param1, "%1", text);
      }
      if (param2 != null)
      {
         // Replace param #2.
         text = StringUtils.replaceText(param2, "%2", text);
      }
      // Return the text.
      return text;
   }

   /**
    * Take the input string and replace %1 with the value in param1, %2 with the
    * value in param2, etc.
    */
   public static String insertParams(String text, String param1, String param2, String param3)
   {
      if (param1 != null)
      {
         // Replace param #1.
         text = StringUtils.replaceText(param1, "%1", text);
      }
      if (param2 != null)
      {
         // Replace param #2.
         text = StringUtils.replaceText(param2, "%2", text);
      }
      if (param3 != null)
      {
         // Replace param #3.
         text = StringUtils.replaceText(param2, "%3", text);
      }
      // Return the text.
      return text;
   }

   /**
    * Take the input string and replace %1 with the value in param1, %2 with the
    * value in param2, etc.
    */
   public static String insertParams(String text, String param1, String param2, String param3, String param4)
   {
      if (param1 != null)
      {
         // Replace param #1.
         text = StringUtils.replaceText(param1, "%1", text);
      }
      if (param2 != null)
      {
         // Replace param #2.
         text = StringUtils.replaceText(param2, "%2", text);
      }
      if (param3 != null)
      {
         // Replace param #3.
         text = StringUtils.replaceText(param2, "%3", text);
      }
      if (param4 != null)
      {
         // Replace param #4.
         text = StringUtils.replaceText(param2, "%4", text);
      }
      // Return the text.
      return text;
   }
}
