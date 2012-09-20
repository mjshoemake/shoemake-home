package mjs.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import mjs.database.Field;
import mjs.exceptions.CoreException;


/**
 * This is a utility class which contains formatting functions to
 * convert int, long, double, float, date, and other things to a
 * String based on the format information contained in a
 * FieldDefinition object.
 */
@SuppressWarnings("rawtypes")
public class FormatUtils
{
   /**
    * Formats a Boolean object using the formatting instructions in
    * the FieldDefinition object.
    *
    * @param value  Boolean
    * @param def    FieldDefinition
    * @return       String
    */
   public static String formatBoolean(Boolean value, Field def)
   {
      String format = def.getFormat();
      //int maxLen = def.getMaxLen();

      String trueCaption = "true";
      String falseCaption = "false";
      int slashPos = format.indexOf('/');

      if (slashPos != -1)
      {
         trueCaption = format.substring(0, slashPos);
         falseCaption = format.substring(slashPos + 1);
      }

      String caption = null;

      if (value.booleanValue())
         caption = trueCaption;
      else
         caption = falseCaption;
      return caption;
   }

   /**
    * Determines whether or not a String contains a valid boolean
    * based on the formatting instructions in the FieldDefinition
    * object.
    *
    * @param value  String
    * @param def    FieldDefinition
    * @return       boolean
    */
   public static boolean isValidBoolean(String value, Field def)
   {
      String format = def.getFormat();
      String trueCaption = "true";
      String falseCaption = "false";
      int slashPos = format.indexOf('/');

      if (slashPos != -1)
      {
         trueCaption = format.substring(0, slashPos);
         falseCaption = format.substring(slashPos + 1);
      }

      if (value.equals(trueCaption) || value.equals(falseCaption) ||
            value.equals("true") || value.equals("false"))
         return true;
      else
         return false;
   }

   /**
    * Determines whether or not a String contains a valid boolean
    * based on the formatting instructions in the FieldDefinition
    * object.
    *
    * @param value  String
    * @param def    FieldDefinition
    * @return       boolean
    */
   public static Boolean parseBoolean(String value, Field def)
   {
      String format = def.getFormat();
      String trueCaption = "true";
      int slashPos = format.indexOf('/');

      if (slashPos != -1)
      {
         trueCaption = format.substring(0, slashPos);
      }

      Boolean result = null;

      if (value.equals(trueCaption) || value.equals("true"))
         result = new Boolean(true);
      else
         result = new Boolean(false);
      return result;
   }

   /**
    * Formats a Date object using the formatting instructions in the
    * FieldDefinition object.
    *
    * @param date  Date
    * @param def   FieldDefinition
    * @return      String
    */
   public static String formatDate(Date date, Field def)
   {
      String format = def.getFormat();

      if (format == null || format.trim().equals(""))
         format = "MM/dd/yyyy";

      SimpleDateFormat df = new SimpleDateFormat(format);

      df.setLenient(false);
      if (date == null || date.equals(""))
         return null;
      else
         return df.format(date);
   }

   /**
    * Parses a String into a Date object using the formatting
    * instructions in the FieldDefinition object.
    *
    * @param date               Description of Parameter
    * @param def                FieldDefinition
    * @return                   Date
    * @exception CoreException  Description of Exception
    */
   public static Date parseDate(String date, Field def) throws CoreException
   {
      try
      {
         String format = def.getFormat();

         if (format == null || format.trim().equals(""))
            format = "MM/dd/yyyy";

         SimpleDateFormat df = new SimpleDateFormat(format);

         df.setLenient(false);
         return df.parse(date);
      }
      catch (java.lang.Exception e)
      {
         throw new CoreException("Error parsing date '" + date + "'.  Invalid date.", e);
      }
   }

   /**
    * Determines whether or not a String contains a valid date based
    * on the formatting instructions in the FieldDefinition object.
    *
    * @param date  String
    * @param def   FieldDefinition
    * @return      boolean
    */
   public static boolean isValidDate(String date, Field def)
   {
      try
      {
         String format = def.getFormat();

         if (format == null || format.trim().equals(""))
            format = "MM/dd/yyyy";

         SimpleDateFormat df = new SimpleDateFormat(format);

         df.setLenient(false);
         Date dDate = df.parse(date);
         if (dDate != null) {
             return true;
         } else {
             return false;
         }
      }
      catch (java.lang.Exception e)
      {
         return false;
      }
   }

   /**
    * Formats an Integer object using the formatting instructions in
    * the FieldDefinition object.
    *
    * @param number  Integer
    * @param def     FieldDefinition
    * @return        String
    */
   public static String formatInteger(Integer number, Field def)
   {
      return doIntegerNumber(number.toString(), def);
   }

   /**
    * Parses a String object and converts it to an integer by dropping
    * all non numeric characters and stopping at the decimal point.
    *
    * @param number  String
    * @return        Integer
    */
   public static Integer parseInteger(String number)
   {
      int c = 0;
      StringBuffer buffer = new StringBuffer();

      while (c <= number.length() - 1 && number.charAt(c) != '.')
      {
         char ch = number.charAt(c);

         if (ch == '0' ||
               ch == '1' ||
               ch == '2' ||
               ch == '3' ||
               ch == '4' ||
               ch == '5' ||
               ch == '6' ||
               ch == '7' ||
               ch == '8' ||
               ch == '9')
         {
            // Keep only the valid digits.
            buffer.append(ch);
         }
         c++;
      }

      Integer result = new Integer(Integer.parseInt(buffer.toString()));

      return result;
   }

   /**
    * Determines whether or not a String contains a valid integer.
    *
    * @param number  String
    * @param def     FieldDefinition
    * @return        boolean
    */
   @SuppressWarnings("unchecked")
   public static boolean isValidInteger(String number, Field def)
   {
      int numDigits = 0;
      String format = def.getFormat();

      // A hashtable with all digits 0-9.
      Hashtable digitTable = createDigitTable();

      // Create a Hashtable any characters other than 0-9 and the decimal
      // point that exist in the format string.
      Hashtable extraChars = createFormatCharsTable(format);

      extraChars.put("-", new Character('-'));

      // Process input.  Look for characters that are not digits,
      // decimal, or in the extra characters list.  If found, number
      // is invalid.
      int c = 0;

      while (c <= number.length() - 1)
      {
         char ch = number.charAt(c);

         if (digitTable.containsKey(ch + ""))
         {
            numDigits++;
         }
         else if (! extraChars.containsKey(ch + ""))
         {
            // Not a valid character.
            return false;
         }

         c++;
      }

      if (numDigits > 0 && numDigits < 11)
         return true;
      else
         return false;
   }

   /**
    * Formats a Long object using the formatting instructions in the
    * FieldDefinition object.
    *
    * @param number  Long
    * @param def     FieldDefinition
    * @return        String
    */
   public static String formatLong(Long number, Field def)
   {
      return doIntegerNumber(number.toString(), def);
   }

   /**
    * Parses a String object and converts it to a Long by dropping all
    * non numeric characters and stopping at the decimal point.
    *
    * @param number  String
    * @return        Long
    */
   public static Long parseLong(String number)
   {
      int c = 0;
      StringBuffer buffer = new StringBuffer();

      while (c <= number.length() - 1 && number.charAt(c) != '.')
      {
         char ch = number.charAt(c);

         if (ch == '0' ||
               ch == '1' ||
               ch == '2' ||
               ch == '3' ||
               ch == '4' ||
               ch == '5' ||
               ch == '6' ||
               ch == '7' ||
               ch == '8' ||
               ch == '9')
         {
            // Keep only the valid digits.
            buffer.append(ch);
         }
         c++;
      }

      Long result = new Long(Long.parseLong(buffer.toString()));

      return result;
   }

   /**
    * Determines whether or not a String contains a valid integer
    * based on the formatting instructions in the FieldDefinition
    * object.
    *
    * @param number  String
    * @param def     FieldDefinition
    * @return        boolean
    */
   @SuppressWarnings("unchecked")
   public static boolean isValidLong(String number, Field def)
   {
      int numDigits = 0;
      String format = def.getFormat();

      // A hashtable with all digits 0-9.
      Hashtable digitTable = createDigitTable();

      // Create a Hashtable any characters other than 0-9 and the decimal
      // point that exist in the format string.
      Hashtable extraChars = createFormatCharsTable(format);

      extraChars.put("-", new Character('-'));

      // Process input.  Look for characters that are not digits,
      // decimal, or in the extra characters list.  If found, number
      // is invalid.
      int c = 0;

      while (c <= number.length() - 1)
      {
         char ch = number.charAt(c);

         if (digitTable.containsKey(ch + ""))
         {
            numDigits++;
         }
         else if (! extraChars.containsKey(ch + ""))
         {
            // Not a valid character.
            return false;
         }

         c++;
      }

      if (numDigits > 0 && numDigits < 20)
         return true;
      else
         return false;
   }

   /**
    * Formats a BigDecimal object representing a double or float value
    * using the formatting instructions in the FieldDefinition object.
    *
    * @param number  BigDecimal
    * @param def     FieldDefinition
    * @return        String
    */
   public static String formatBigDecimal(BigDecimal number, Field def)
   {
      StringBuffer buffer = new StringBuffer();
      String format = def.getFormat();

      // Check for null values.
      if ((number == null) && (format == null || format.trim().equals("") || format.indexOf('0') == -1))
         return "";
      if (number == null)
         number = new BigDecimal("0.0");
      if (format == null || format.trim().equals(""))
         return number.toString();

      int decimalPos = format.indexOf('.');
      int maxLen = def.getMaxLen();
      String input = number.toString();
      boolean isPercent = def.getIsPercent();

      // It's a percent, so multiply by 100 to get the value to display to the
      // user.
      if (isPercent)
      {
         // Multiple the number by 100
         BigDecimal temp = new BigDecimal(number.toString());

         temp.multiply(new BigDecimal(100));
         input = temp.toString();
      }

      //  Break input string at the decimal point.
      int inputDecimalPos = input.indexOf('.');
      String inputFront = null;
      String inputBack = null;

      if (inputDecimalPos == -1)
      {
         // No decimal found in input.
         inputFront = input;
         inputBack = "";
      }
      else
      {
         inputFront = input.substring(0, inputDecimalPos);
         inputBack = input.substring(inputDecimalPos + 1).trim();
      }

      if (decimalPos == -1)
      {
         // No decimal found in format.  Display as integer.
         processWholeNumber(inputFront, buffer, format, maxLen);
      }
      else
      {
         // Break format string at the decimal point.
         String formatFront = format.substring(0, decimalPos);
         String formatBack = format.substring(decimalPos + 1).trim();

         processWholeNumber(inputFront, buffer, formatFront, maxLen);

         int roomLeft = maxLen - buffer.length() - 1;

         processDecimalNumber(inputBack, buffer, formatBack, roomLeft);
      }
      return buffer.toString();
   }

   /**
    * Parses a String object and converts it to a BigDecimal object
    * (floating point) by dropping all non numeric characters (except
    * the decimal point).
    *
    * @param number  String
    * @param def     Description of Parameter
    * @return        BigDecimal
    */
   public static BigDecimal parseBigDecimal(String number, Field def)
   {
      String format = def.getFormat();

      // Check for null input.
      if ((number == null) && (format == null || format.trim().equals("") || format.indexOf('0') == -1))
         return null;
      if (number == null)
         return new BigDecimal("0.0");
      if (format == null || format.trim().equals(""))
         return new BigDecimal(number);

      boolean isPercent = def.getIsPercent();
      StringBuffer buffer = new StringBuffer();
      int decimalPos = number.indexOf('.');

      if (decimalPos != -1)
      {
         // Remove trailing zeros.
         int truncatePos = number.length() - 1;

         while (number.charAt(truncatePos) == '0' && truncatePos >= decimalPos)
            truncatePos--;

         if (truncatePos < number.length() - 1)
            number = number.substring(0, truncatePos + 1);
      }

      int c = 0;

      while (c <= number.length() - 1)
      {
         char ch = number.charAt(c);

         if (ch == '0' ||
               ch == '1' ||
               ch == '2' ||
               ch == '3' ||
               ch == '4' ||
               ch == '5' ||
               ch == '6' ||
               ch == '7' ||
               ch == '8' ||
               ch == '9' ||
               ch == '-' ||
               ch == '.')
         {
            // Keep only the valid digits.
            buffer.append(ch);
         }
         c++;
      }

      BigDecimal result = new BigDecimal(buffer.toString());

      // It's a percent, so divide by 100 to get the actual value.
      if (isPercent)
      {
         // Divide the number by 100
         result.divide(new BigDecimal(100), result.scale() + 2);
      }

      return result;
   }

   /**
    * Determines whether or not a String contains a valid floating
    * point number based on the formatting instructions in the
    * FieldDefinition object.
    *
    * @param number  String
    * @param def     FieldDefinition
    * @return        boolean
    */
   @SuppressWarnings("unchecked")
   public static boolean isValidBigDecimal(String number, Field def)
   {
      int numDigits = 0;
      String format = def.getFormat();

      // A hashtable with all digits 0-9.
      Hashtable digitTable = createDigitTable();

      // Create a Hashtable any characters other than 0-9 and the decimal
      // point that exist in the format string.
      Hashtable extraChars = createFormatCharsTable(format);

      extraChars.put("-", new Character('-'));

      // Process input.  Look for characters that are not digits,
      // decimal, or in the extra characters list.  If found, number
      // is invalid.
      int c = 0;

      while (c <= number.length() - 1 && number.charAt(c) != '.')
      {
         char ch = number.charAt(c);

         if (digitTable.containsKey(ch + ""))
         {
            numDigits++;
         }
         else if (! extraChars.containsKey(ch + ""))
         {
            // Not a valid character.
            return false;
         }

         c++;
      }

      // Skip the decimal point if there is one.
      if (c <= number.length() - 1 && number.charAt(c) == '.')
         c++;

      // Process digit after the decimal point.
      while (c <= number.length() - 1)
      {
         char ch = number.charAt(c);

         if (digitTable.containsKey(ch + ""))
         {
            numDigits++;
         }
         else if (! extraChars.containsKey(ch + ""))
         {
            // Not a valid character.
            return false;
         }

         c++;
      }

      if (numDigits > 0)
         return true;
      else
         return false;
   }

   /**
    * Formats a String based on the format String and max length.
    *
    * @param input   String
    * @param def     Description of Parameter
    * @return        Description of Return Value
    */
   public static String formatString(String input, Field def)
   {
      StringBuffer buffer = new StringBuffer();
      String format = def.getFormat();
      // Get the max length.
      int maxLen = def.getMaxLen();

      if (input == null || input.equals(""))
         return "";

      // If no formatting required, return the input string.
      if (format == null || format.equals(""))
      {
         if (input.length() <= maxLen)
            return input;
         else
            return input.substring(0, maxLen);
      }

      format = format.toUpperCase();

      int formatPos = 0;
      int inputPos = 0;
      char TAB = '\u0009';
      char formatChar = TAB;
      char inputChar = TAB;
      char lastFormatChar = TAB;

      while (formatPos <= format.length() - 1)
      {
         // Get next format character.
         formatChar = format.charAt(formatPos);

         // Get next input character.
         if (inputPos <= input.length() - 1)
            inputChar = input.charAt(inputPos);
         else
            inputChar = TAB;

         if (formatChar == 'X' ||
               formatChar == 'A' ||
               formatChar == '9')
         {
            // If no input characters, output a space.
            lastFormatChar = formatChar;
            if (inputChar == TAB)
            {
               // No more input characters.
               if (buffer.length() < maxLen)
                  buffer.append(' ');
            }
            else
            {
               // Valid input character.
               buffer.append(inputChar);
               inputPos++;
            }

         }
         else
         {
            // Output the format character.
            lastFormatChar = formatChar;
            buffer.append(formatChar);
         }

         formatPos++;
      }

      // Move the rest of the input string if there's any left.  Only do
      // this if the last format character processed was an X.
      if (lastFormatChar == 'X')
      {
         while (inputPos <= input.length() - 1)
         {
            inputChar = input.charAt(inputPos);
            buffer.append(inputChar);
            inputPos++;
         }
      }

      String result = buffer.toString();

      if (result.length() > maxLen)
         result = result.substring(0, maxLen);

      return result;
   }

   /**
    * Determines whether or not a String contains valid data based on
    * the formatting instructions in the FieldDefinition object.
    *
    * @param value  String
    * @param def    FieldDefinition
    * @return       boolean
    */
   public static boolean isValidString(String value, Field def)
   {
      String format = def.getFormat();
      int maxLen = def.getMaxLen();

      // If format is blank, assume valid.
      if (format == null || format.length() == 0)
         return true;
      // If value is blank, assume valid.
      if (value == null || value.equals(""))
         return true;
      if (value.length() > maxLen)
    	  return false;

      char ch = ' ';

      for (int c = 0; c <= format.length() - 1; c++)
      {
         // Check each character in the input string.
         ch = format.charAt(c);

         char in_ch = value.charAt(c);

         if ((ch != 'X') &&
               (ch != 'A') &&
               (ch != '9'))
         {
            // Not a valid symbol.  Must be literal text.  Make sure the
            // literal text exists in the input string.

            if (ch != in_ch)
               return false;
         }
         else if (ch == '9')
         {
            // Make sure this is digits only.
            if (in_ch != '0' &&
                  in_ch != '1' &&
                  in_ch != '2' &&
                  in_ch != '3' &&
                  in_ch != '4' &&
                  in_ch != '5' &&
                  in_ch != '6' &&
                  in_ch != '7' &&
                  in_ch != '8' &&
                  in_ch != '9')
               return false;
         }
      }

      if (value.length() == format.length())
         return true;
      else if (value.length() > format.length() && ch == 'X')
         return true;
      else
         return false;
   }

   /**
    * Parses a String object and reverses the formatting so it can be
    * saved to the database.
    *
    * @param input              Description of Parameter
    * @param def                FieldDefinition
    * @return                   String
    * @exception CoreException  Description of Exception
    */
   public static String parseString(String input, Field def) throws CoreException
   {
      String format = def.getFormat();
      int maxLen = def.getMaxLen();
      StringBuffer buffer = new StringBuffer();

      // If input is blank, return blank.
      if (input == null || input.equals(""))
         return "";

      if (format == null || format.length() == 0)
      {
         if (input.length() <= maxLen)
            return input;
         else
            return input.substring(0, maxLen);
      }

      char ch = ' ';

      for (int c = 0; c <= format.length() - 1; c++)
      {
         // Check each character in the input string.
         ch = format.charAt(c);

         char in_ch = input.charAt(c);

         if ((ch != 'X') &&
               (ch != 'A') &&
               (ch != '9'))
         {
            // Not a valid symbol.  Must be literal text.  Make sure the
            // literal text exists in the input string.
            if (ch != in_ch)
               throw new CoreException("Error parsing string '" + input + "'.  String is not formatted properly ('" + format + "').");
         }
         else if (ch == '9')
         {
            // Make sure this is digits only.
            if (in_ch != '0' &&
                  in_ch != '1' &&
                  in_ch != '2' &&
                  in_ch != '3' &&
                  in_ch != '4' &&
                  in_ch != '5' &&
                  in_ch != '6' &&
                  in_ch != '7' &&
                  in_ch != '8' &&
                  in_ch != '9')
               throw new CoreException("Error parsing string '" + input + "'.  Digit (0-9) expected but not found.  String is not formatted properly ('" + format + ").");
            else
               // Add character to buffer.
               buffer.append(in_ch);
         }
         else
         {
            // Add character to buffer.
            buffer.append(in_ch);
         }
      }

      // If extra characters found, append those to the buffer as well.
      if (input.length() > format.length() && ch == 'X')
      {
         for (int c = format.length(); c <= input.length() - 1; c++)
            buffer.append(input.charAt(c));
      }

      return buffer.toString();
   }

   /**
    * Formats a whole number (long or integer) based on the format
    * String and max length.
    *
    * @param input   String
    * @param buffer  StringBuffer
    * @param format  String
    * @param maxLen  int
    */
   private static void processWholeNumber(String input, StringBuffer buffer, String format, int maxLen)
   {
      // Check maxLen first.
      int c = 0;
      char TAB = '\u0009';

      format = format.toUpperCase();

      int zeroPos = format.indexOf('0');
      int formatPos = format.length() - 1;
      int inputPos = input.length() - 1;
      StringBuffer tempBuffer = new StringBuffer();
      char formatChar = TAB;
      char inputChar = TAB;

      while (formatPos >= 0)
      {
         formatChar = format.charAt(formatPos);

         if (inputPos >= 0)
            inputChar = input.charAt(inputPos);
         else
            inputChar = TAB;

         if (inputChar == '-')
         {
            // Valid input character.
            tempBuffer.append(inputChar);
            inputPos--;
            continue;
         }
         else if (formatChar == '0')
         {
            if (inputChar == TAB)
            {
               if (tempBuffer.length() < maxLen)
                  tempBuffer.append('0');
            }
            else
            {
               // Valid input character.
               tempBuffer.append(inputChar);
               inputPos--;
            }

         }
         else if (formatChar == 'X' ||
               formatChar == '#' ||
               formatChar == '9')
         {
            if (inputChar == TAB)
            {
               // No more input characters.  Pad leading zeros if necessary.
               if (zeroPos > -1 && formatPos >= zeroPos)
               {
                  // Still more leading zeros.  Keep padding.
                  if (tempBuffer.length() < maxLen)
                     tempBuffer.append('0');
               }
            }
            else
            {
               // Valid input character.
               inputPos--;
               tempBuffer.append(inputChar);
            }
         }
         else
         {
            if (inputChar == TAB)
            {
               // No more input characters.  Pad leading zeros if necessary.
               if (zeroPos > -1 && formatPos >= zeroPos)
               {
                  // Still more leading zeros.  Keep padding.
                  if (tempBuffer.length() < maxLen)
                     tempBuffer.append(formatChar);
               }
            }
            else
            {
               // Valid input character.
               tempBuffer.append(formatChar);
            }
         }

         formatPos--;
      }

      // Move the rest of the input string if the input string was longer
      // than the format string.
      while (inputPos >= 0)
      {
         inputChar = input.charAt(inputPos);
         tempBuffer.append(inputChar);
         inputPos--;
      }

      if (tempBuffer.length() > maxLen)
      {
         StringBuffer trace = new StringBuffer();
         String temp = tempBuffer.toString();

         for (c = temp.length() - 1; c >= 0; c--)
            trace.append(temp.charAt(c));

         // Number too long.  Display '######' instead (similar
         // to Excel).
         for (c = 0; c <= maxLen - 1; c++)
            buffer.append('#');
         return;
      }

      // TempBuffer is backwards.  Move to buffer in the correct order.
      String temp = tempBuffer.toString();

      for (c = temp.length() - 1; c >= 0; c--)
         buffer.append(temp.charAt(c));
   }

   /**
    * Formats the decimal part of a number based on the format String
    * and max length.
    *
    * @param input   String
    * @param buffer  StringBuffer
    * @param format  String
    * @param maxLen  int
    */
   private static void processDecimalNumber(String input, StringBuffer buffer, String format, int maxLen)
   {
      // Check maxLen first.
      int c = 0;

      if (format.length() < (maxLen))
         maxLen = format.length();

      if (maxLen <= 0)
      {
         // No room for anything else.  We're not going to put a decimal point
         // on the end without any digits, so stop right here.
         return;
      }

      if (input.length() > maxLen)
      {
         // Number too long.  Display only what will fit.
         String newInput = input.substring(0, maxLen);

         buffer.append('.');
         buffer.append(newInput);
         return;
      }

      int zeroPos = format.lastIndexOf('0');

      if (zeroPos == -1)
      {
         // No zeros found.  Return current value.
         buffer.append('.');
         buffer.append(input);
      }
      else
      {
         // Zeros found.  Must pad zeros to the point of the last zero.
         if (zeroPos > maxLen - 1)
            zeroPos = maxLen - 1;

         int numPaddedZeros = (zeroPos + 1) - input.length();

         // Pad trailing zeros.
         buffer.append('.');
         buffer.append(input);
         for (c = 0; c <= numPaddedZeros - 1; c++)
            buffer.append('0');
      }
   }

   /**
    * Formats a number (integer or long) using the formatting
    * instructions in the FieldDefinition object.
    *
    * @param input  String
    * @param def    FieldDefinition
    * @return       String
    */
   private static String doIntegerNumber(String input, Field def)
   {
      StringBuffer buffer = new StringBuffer();
      String format = def.getFormat();
      int decimalPos = format.indexOf('.');
      int maxLen = def.getMaxLen();

      if (decimalPos == -1)
      {
         // No decimal found in format.  Display as integer.
         processWholeNumber(input, buffer, format, maxLen);
      }
      else
      {
         // Decimal found.  Break format string at the decimal point.
         String formatFront = format.substring(0, decimalPos);
         String formatBack = format.substring(decimalPos + 1).trim();

         processWholeNumber(input, buffer, formatFront, maxLen);

         int roomLeft = maxLen - buffer.length() - 1;

         processDecimalNumber("", buffer, formatBack, roomLeft);
      }
      return buffer.toString();
   }

   /**
    * Create a Hashtable that contains all digits 0-9.
    *
    * @return   Hashtable
    */
   @SuppressWarnings("unchecked")
   private static Hashtable createDigitTable()
   {
      Hashtable digits = new Hashtable();

      digits.put("0", "0");
      digits.put("1", "1");
      digits.put("2", "2");
      digits.put("3", "3");
      digits.put("4", "4");
      digits.put("5", "5");
      digits.put("6", "6");
      digits.put("7", "7");
      digits.put("8", "8");
      digits.put("9", "9");
      return digits;
   }

   /**
    * Create a Hashtable that contains the non-digit characters of the
    * specified format string. The decimal point is excluded.
    *
    * @param format  String
    * @return        Hashtable
    */
   @SuppressWarnings("unchecked")
   private static Hashtable createFormatCharsTable(String format)
   {
      Hashtable digits = createDigitTable();
      Hashtable extraChars = new Hashtable();

      for (int c = 0; c <= format.length() - 1; c++)
      {
         char ch = format.charAt(c);

         if ((! extraChars.containsKey(ch + "")) && (! digits.containsKey(ch + "")))
         {
            // If character is not in the list, add it.
            extraChars.put(ch + "", new Character(ch));
         }
      }
      // The decimal point should not be in the list.
      extraChars.remove(".");
      return extraChars;
   }

}
