
package mjs.core.utils;

// Java imports
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;
import org.apache.log4j.Logger;


/**
 * This is the utility class for string operations.  It is intended to give 
 * a standard, static-access repository for all custom string manipulation methods.
 */
public class StringUtils
{
   /**
    * Constructor.
    */
   public StringUtils()
   {
   }

   /**
    * If string #1 is found inside string #3, replace the first occurance
    * of string #1 with string #2.  Then return the result, which should be an
    * updated form of string #3.  This is fairly confusing, I know. <p> replace <x> with <y> if found in <z>. <p>
    * NOTE:  This only replaces the first occurance.
    */
   public static String replaceText(String sTextToInsert, String sTextToRemove, String sOrigText)
   {
      String sFront = null;
      String sBack = null;
      // Find the starting location of the text to replace.
      int nReplaceStartPos = sOrigText.indexOf(sTextToRemove);
      int nBackStartPos = nReplaceStartPos + sTextToRemove.length();
      int nOrigLength = sOrigText.length();
      // Get text in front.
      sFront = substr(sOrigText, 0, nReplaceStartPos);
      // Get text in back.
      sBack = substr(sOrigText, nBackStartPos, nOrigLength - nBackStartPos);
      return sFront + sTextToInsert + sBack;
   }

   /**
    * Insert string #1 into string #2, replacing the text at the specified position and length. <p>
    * This method uses absolute positioning.  So, "pos" will be a number like 1, 2, 3, 4, etc., and will NOT start with 0.
    */
   public static String replaceText(String sTextToInsert, String sOrigText, int relativePos, int length)
   {
      int absolutePos = relativePos + 1;
      String sFront = null;
      String sBack = null;
      // Find the starting location of the text to replace.
      int nReplaceStartPos = absolutePos - 1;
      int nBackStartPos = nReplaceStartPos + length;
      int nOrigLength = sOrigText.length();
      // Get text in front.
      sFront = substr(sOrigText, 0, nReplaceStartPos);
      // Get text in back.
      sBack = substr(sOrigText, nBackStartPos, nOrigLength - nBackStartPos);
      return sFront + sTextToInsert + sBack;
   }

   /**
    * Insert string #1 into string #2 at the specified position. <p>
    * This method uses relative positioning.  So, "pos" will be a number like 0, 1, 2, 3, etc.
    */
   public static String insertText(String sTextToInsert, String sOrigText, int relativePos)
   {
      int absolutePos = relativePos + 1;
      String sFront = null;
      String sBack = null;
      // Find the starting location of the text to replace.
      int nReplaceStartPos = absolutePos - 1;
      int nBackStartPos = nReplaceStartPos;
      int nOrigLength = sOrigText.length();
      // Get text in front.
      sFront = substr(sOrigText, 0, nReplaceStartPos);
      // Get text in back.
      sBack = substr(sOrigText, nBackStartPos, nOrigLength - nBackStartPos);
      return sFront + sTextToInsert + sBack;
   }

   /*
   * SubStr method.  This method uses relative position.
   * <p>
   * So, "pos" will be a number like 0, 1, 2, 3, etc.
   * <p>
   * This is intended to give an alternative to the very non-intuitive
   * substring method provided in the String class.
   */

   public static String substr(String sText, int relativePos, int length)
   {
      return sText.substring(relativePos, relativePos + length);
   }

   /**
    * Remove text from this string starting at a particular relative position and width. <p>
    * ie.  "hello", 2, 3    returns    "ho". <p> This method uses relative positioning.  So, "pos" will be a
    * number like 0, 1, 2, 3, etc.
    */
   public static String removeText(String sText, int relativePos, int length)
   {
      int absolutePos = relativePos + 1;
      String sFront = "";
      String sBack = "";
      if (absolutePos > 1)
      { sFront = substr(sText, 0, absolutePos - 1); }
      if (absolutePos <= sText.length())
      { sBack = substr(sText, absolutePos + length - 1, sText.length() - (absolutePos + length) + 1); }
      return sFront + sBack;
   }

   /*
   * Returns the current date and time as a string.
   */

   public static String getDateTime()
   {
      Date dtNow = new Date();
      return dtNow.toString();
   }

   /*
   * Assign the file extension for this file.  If the extension has already
   * been set, return the original filename.  Otherwise, append the extension to the
   * filename.  If the extension does not begin with a ".", this method will add one
   * before appending.
   */
   public static String setFileNameExtension(String sFilePath, String sExtension)
   {
      if (!sExtension.startsWith("."))
      {
         // Prepend the ".".
         sExtension = "." + sExtension;
      }
      String sTestPath = sFilePath.toLowerCase();
      String sTestExtension = sExtension.toLowerCase();
      // Check for extension ignoring the case.
      if (! sTestPath.endsWith (sTestExtension))
      {
         // This extension is not currently on the file.
         sFilePath = sFilePath + sExtension;
      }
      return sFilePath;
   }
   

   /**
    * Encrypt the input string return the result.  This is good for
    * password protection.
    * 
    * @param value  The string to encrypt.
    * @return The encrypted string. 
    * @throws java.lang.AbstractServletException
    */
   public synchronized String encrypt(String value) throws java.lang.Exception
   {
      MessageDigest md = null;

      try
      {
         // SHA (Secure Hash Algorithm) is a 160 bit algorithm and impossible
         // to decrypt.  Support for this is built into java.
         md = MessageDigest.getInstance("SHA");
      }
      catch (NoSuchAlgorithmException e)
      {
         throw e;
      }

      try
      {
         // Convert the input text into byte representation.
         md.update(value.getBytes("UTF-8"));
      }
      catch(UnsupportedEncodingException e)
      {
         throw e;
      }

      // Do the transformation.
      byte raw[] = md.digest();
      String hash = (new BASE64Encoder()).encode(raw);
      return hash;
   }

   /**
    * Converts null String to "". If value is not null, the value is
    * trimmed before being returned.
    *
    * @param value  String
    * @return       String
    */
   public static String nullToBlank(String value)
   {
      if (value == null)
         return "";
      return value.trim();
   }

   /**
    * Converts null String to "". If value is not null, the value is
    * trimmed before being returned.
    *
    * @param value  String
    * @return       String
    */
   public static String blankToSpace(String value)
   {
      if (value == null || value.trim().equals(""))
         return " ";
      else
         return value;
   }

   /**
    * Right justifies the input String by padding spaces to the left,
    * making it the specified length.
    *
    * @param value   String
    * @param length  int
    * @return        String
    */
   public static String rightJustify(String value, int length)
   {
      StringBuffer buffer = new StringBuffer();

      for (int C = 0; C <= (length - value.length()) - 1; C++)
         buffer.append(" ");
      buffer.append(value);
      return buffer.toString();
   }

   /**
    * Right justifies the input String by padding zeros to the left,
    * making it the specified length.
    *
    * @param value   String
    * @param length  int
    * @return        String
    */
   public static String leadingZeros(int value, int length)
   {
      StringBuffer buffer = new StringBuffer();
      String input = value + "";

      for (int C = 0; C <= (length - input.length()) - 1; C++)
         buffer.append("0");
      buffer.append(input);
      return buffer.toString();
   }

   /**
    * Left justifies the input String by padding spaces to the right,
    * making it the specified length.
    *
    * @param value   String
    * @param length  int
    * @return        String
    */
   public static String leftJustify(String value, int length)
   {
      StringBuffer buffer = new StringBuffer();

      if (value == null)
         value = "null";
      buffer.append(value);
      for (int C = 0; C <= (length - value.length()) - 1; C++)
         buffer.append(" ");
      return buffer.toString();
   }

   /**
    * Remove the package specifier from this class name.
    *
    * @param classname  String
    * @return           String
    */
   public static String removePackage(String classname)
   {
      int lastDot = classname.lastIndexOf(".");

      return classname.substring(lastDot + 1);
   }

   /**
    * Takes a String (primarily database field names) and formats it
    * so that it is all lowercase and all underscore characters are
    * removed.
    *
    * @param value  String
    * @return       String
    */
   public static String normalizeFieldName(String value)
   {
      // Convert to lowercase.
      value = value.toLowerCase();

      // Remove underscore characters.
      StringBuffer result = new StringBuffer();

      for (int C = 0; C <= value.length() - 1; C++)
      {
         char ch = value.charAt(C);

         if (ch != '_')
            result.append(ch);
      }
      return result.toString();
   }

   /**
    * Removes all occurances of the specified character from the
    * specified String.
    *
    * @param value
    * @param ch
    * @return       String
    */
   public static String removeAllOccurancesOfChar(String value, char ch)
   {
      // Remove underscore characters.
      StringBuffer result = new StringBuffer();

      for (int C = 0; C <= value.length() - 1; C++)
      {
         char next = value.charAt(C);

         if (next != ch)
            result.append(next);
      }
      return result.toString();
   }

   /**
    * Removes all occurances of non-numeric(anything apart from 0-9)
    * characters from the specified String.
    *
    * @param value
    * @return       String
    */
   public static String removeAllOccurancesOfNonNumericChars(String value)
   {
      // Remove underscore characters.
      StringBuffer result = new StringBuffer();

      for (int C = 0; C <= value.length() - 1; C++)
      {
         char next = value.charAt(C);

         if (next == '0' ||
               next == '1' ||
               next == '2' ||
               next == '3' ||
               next == '4' ||
               next == '5' ||
               next == '6' ||
               next == '7' ||
               next == '8' ||
               next == '9')
            result.append(next);
      }
      return result.toString();
   }

   /**
    * Returns the numeric position of the first non-numeric character
    * in the specified string. Will return -1 if no non-numeric
    * characters are found.
    *
    * @param value  String
    * @return       int
    */
   public static int posOfFirstfNonNumericChar(String value)
   {
      // Remove underscore characters.
      StringBuffer result = new StringBuffer();

      for (int C = 0; C <= value.length() - 1; C++)
      {
         char next = value.charAt(C);

         if (next != '0' &&
               next != '1' &&
               next != '2' &&
               next != '3' &&
               next != '4' &&
               next != '5' &&
               next != '6' &&
               next != '7' &&
               next != '8' &&
               next != '9')
            return C;
      }
      return -1;
   }

   /**
    * Convert an ArrayList object into an array of String objects.
    *
    * @param list  ArrayList
    * @return      String[]
    */
   public static String[] toArray(ArrayList list)
   {
      String[] result = new String[list.size()];

      for (int C = 0; C <= list.size() - 1; C++)
         result[C] = list.get(C).toString();
      return result;
   }

   /**
    * Break this string into tokens using the specified delimiter. The
    * tokens will be returned as an array of String objects.
    *
    * @param text
    * @param delimiter
    * @return           String[]
    */
   public static String[] getTokens(String text, char delimiter)
   {
      Logger log = Logger.getLogger("Core");

      log.debug("text='" + text + "' delimiter='" + delimiter + "'");

      // Remove underscore characters.
      ArrayList list = new ArrayList();

      int next = text.indexOf(delimiter);

      while (next > -1)
      {
         list.add(text.substring(0, next));
         log.debug("   next token: " + text.substring(0, next));
         text = text.substring(next + 1);
         next = text.indexOf(delimiter);
      }
      list.add(text);
      log.debug("   next token: " + text);

      String[] result = new String[list.size()];

      for (int C = 0; C <= list.size() - 1; C++)
         result[C] = list.get(C).toString();
      return result;
   }

   /**
    * Capitalize the first latter of the specified String object.
    *
    * @param value
    * @return       String
    */
   public static String capitalize(String value)
   {
      char ch = value.charAt(0);
      String front = ch + "";
      String back = value.substring(1);

      return front.toUpperCase() + back;
   }

   /**
    * Break this string into tokens using the specified delimiter. The
    * tokens will be returned as an array of String objects.
    *
    * @param text
    * @param delimiter
    * @return           String[]
    */
   public static String[] getTokens(String text, String delimiter)
   {
      Logger log = Logger.getLogger("Core");

      log.debug("text='" + text + "' delimiter='" + delimiter + "'");

      // Remove underscore characters.
      ArrayList list = new ArrayList();

      int next = text.indexOf(delimiter);

      while (next > -1)
      {
         list.add(text.substring(0, next).trim());
         log.debug("   next token: " + text.substring(0, next).trim());
         text = text.substring(next + delimiter.length());
         next = text.indexOf(delimiter);
      }
      list.add(text.trim());
      log.debug("   next token: " + text.trim());

      String[] result = new String[list.size()];

      for (int C = 0; C <= list.size() - 1; C++)
         result[C] = list.get(C).toString();
      return result;
   }

}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/StringUtils.java-arc  $
//
//     Rev 1.1   Nov 27 2002 16:13:44   mshoemake
//  Fixed minor bugs found in JUnit.
//
//     Rev 1.0   Aug 23 2002 14:48:30   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:28   mshoemake
//  Initial revision.


