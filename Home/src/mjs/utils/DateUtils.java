
package mjs.utils;

// Java imports
import java.text.*;
import java.util.*;
import mjs.exceptions.CoreException;


/**
 * The date handling and formatting utility class.  All date related functions should
 * be placed in and accessed from this class.  All methods in this class should be 
 * static, utility methods.
 */
public class DateUtils
{
   /**
    * The default date format ("MM/dd/yyyy").
    */
   public static final String DATE_PATTERN = "MM/dd/yyyy";

   /**
    * The default time format ("hh:mm:ss a").
    */
   public static final String TIME_PATTERN = "hh:mm:ss a";

   /**
    * The default date/time format ("MM/dd/yyyy hh:mm:ss a").
    */
   public static final String DATE_TIME_PATTERN = "MM/dd/yyyy hh:mm:ss a";

   /**
    * Parse the String to return a valid Date object. <p> This method uses the time zone.
    * @param    sDateTime    The String value that contains the date.
    * @param    sPattern     The String value that contains the pattern to use
    * (ie. "yyyy/MM/dd hh:mm:ss") during the conversion.
    */
   public static Date parseDate(String sDateTime, String sPattern) throws CoreException
   {
      TimeZone timeZone = TimeZone.getDefault();
      Date date = parseDate(sDateTime, sPattern, timeZone);
      if (date == null) {
          throw new CoreException("Invalid date '" + sDateTime + "'. Expected format " + sPattern + ".");
      }
      return date;
   }

   /**
    * Parse the String to return a valid Date object.
    * @param    sDateTime    The String value that contains the date.
    * @param    sPattern     The String value that contains the pattern to use
    * (ie. "yyyy/MM/dd hh:mm:ss") during the conversion.
    * @param    timeZone     The specified time zone to use during the conversion.
    */
   public static Date parseDate(String sDateTime, String sPattern, TimeZone timeZone) throws CoreException
   {
      ParsePosition pos = new ParsePosition(0);
      SimpleDateFormat fmtDateHandler = new SimpleDateFormat();
      fmtDateHandler.applyPattern(sPattern);
      fmtDateHandler.setTimeZone(timeZone);
      Date result = fmtDateHandler.parse(sDateTime, pos);
      if (result != null) {
          return result;
      } else {
          throw new CoreException("Unable to parse date '" + sDateTime + "' because it doe not match the correct format (" + sPattern + ").");
      }
      
   }

   /**
    * Get the current system date and time converted into String format.
    * @return  A String representation of the date.
    */
   public static String getNow()
   {
      Date dtNow = new Date();
      return dtNow.toString();
   }

   /**
    * Return the elapsed time between the start date and now.  Returns a String
    * representation in HH:MM:SS format.
    * @param startDate  The initial start date.
    * @return           Elapsed time between the start date and now.
    */
   public static long getElapsedTimeInMilliseconds(Date startDate, Date endDate)
   {
      // Convert to milliseconds.
      long startMilliseconds = startDate.getTime();
      long endMilliseconds = endDate.getTime();
      return endMilliseconds - startMilliseconds;
   }

   /**
    * Return the elapsed time between the start date and now.  Returns a String
    * representation in HH:MM:SS format.
    * @param startDate  The initial start date.
    * @return           Elapsed time between the start date and now.
    */
   public static String getElapsedTimeHHMMSS(Date startDate, Date endDate)
   {
      // Convert to milliseconds.
      long startMilliseconds = startDate.getTime();
      long endMilliseconds = endDate.getTime();
      long difference = endMilliseconds - startMilliseconds;

      return millisecondsToHHMMSS(difference);
   }

   /**
    * Convert the date to a Long value.
    * @param    date         The given date.
    */
   public static long dateToLong(Date date)
   {
      return date.getTime();
   }

   /**
    * Convert a duration that is based on fraction-of-a-day to milliseconds.
    */
   public static long fractionOfDayToMilliseconds(float fFractionOfDay)
   {
      // convert from fraction of day to milliseconds
      float fDuration = (fFractionOfDay * 24 * 60 * 60 * 1000);
      return (long)fDuration;
   }

   /**
    * Converts the date entered to GMT according to the Current Time Zone and Daylight Savins Time.
    */
   public static Date localToGMT(Date normalDate)
   {
      long GMTOffset;
      //boolean isDaylightSavings = false;
      Calendar cal = Calendar.getInstance(TimeZone.getDefault());
      Date GMTDate = new Date();
      cal.setTime(normalDate);
      GMTOffset = (-1 * (cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)));
      GMTDate.setTime(normalDate.getTime() + GMTOffset);
      return GMTDate;
   }

   /**
    * Converts the date entered to GMT according to the Current Time Zone and Daylight Savings Time.
    */
   public static Date gmtToLocal(Date gmtDate)
   {
      long GMTOffset;
      //boolean isDaylightSavings = false;
      Calendar cal = Calendar.getInstance(TimeZone.getDefault());
      Date NewGMTDate = new Date();
      cal.setTime(gmtDate);
      GMTOffset = (-1 * (cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)));
      NewGMTDate.setTime(gmtDate.getTime() - GMTOffset);
      return NewGMTDate;
   }

   /**
    * Convert a time in milliseconds to "hh:mm:ss" format.
    */
   public static String millisecondsToHHMMSS(long milliseconds)
   {
      // convert from fraction of day to milliseconds
      long total = (milliseconds / 1000);
      long hours = total / 3600;
      total = total - (hours * 3600);
      long minutes = total / 60;
      total = total - (minutes * 60);
      long seconds = total;
      String sHours = hours + "";
      String sMinutes = minutes + "";
      String sSeconds = seconds + "";
      if (hours < 1)
      { sHours = "00"; }
      else if (hours < 10)
      { sHours = "0" + sHours; }
      if (minutes < 1)
      { sMinutes = "00"; }
      else if (minutes < 10)
      { sMinutes = "0" + sMinutes; }
      if (seconds < 1)
      { sSeconds = "00"; }
      else if (seconds < 10)
      { sSeconds = "0" + sSeconds; }
      return sHours + ":" + sMinutes + ":" + sSeconds;
   }

   /**
    * Accepts 2/1/2005 and converts to 02/01/2005.
    *
    * @param date  java.lang.String
    * @return      boolean
    */
   public static String forceMMDDYYYY(String date)
   {
      // If the length is 10 (ie. "10/12/2005" then just return
      // the original string.
      if (date == null || date.trim().length() == 0)
      {
         return date;
      }
      else if (date.trim().length() == 10)
      {
         return date;
      }

      StringBuffer newDate = new StringBuffer();

      int firstSlashPos = date.indexOf("/");
      String month = date.substring(0, firstSlashPos);
      String rest = date.substring(firstSlashPos + 1, date.length());
      int secondSlashPos = rest.indexOf("/");
      String day = rest.substring(0, secondSlashPos);
      String year = rest.substring(secondSlashPos + 1, rest.length());

      if (month.length() < 2)
         newDate.append("0");
      newDate.append(month);
      newDate.append("/");
      if (day.length() < 2)
         newDate.append("0");
      newDate.append(day);
      newDate.append("/");
      newDate.append(year);

      return newDate.toString();
   }

   /**
    * Accepts "2/1/2005" and converts to a date.
    *
    * @param date               java.lang.String
    * @return                   boolean
    * @exception CoreException  Description of Exception
    */
   public static Date mmddyyyyToDate(String date) throws CoreException
   {
      // If the length is 10 (ie. "10/12/2005" then just return
      // the original string.
      date = forceMMDDYYYY(date);
      try
      {
         SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

         df.setLenient(false);
         return df.parse(date);
      }
      catch (ParseException e)
      {
         throw new CoreException("Date '" + date + "' is not valid.", e);
      }
   }

   /**
    * Accepts Date and converts to a String with the specified format.
    *
    * @param date
    * @param format
    * @return        String
    */
   public static String dateToString(Date date, String format)
   {
      SimpleDateFormat df = new SimpleDateFormat(format);

      df.setLenient(false);
      return df.format(date);
   }

   /**
    * Accepts a String date in the specified format and converts to a
    * Date object.
    *
    * @param date
    * @param format
    * @return                   Date
    * @exception CoreException  Description of Exception
    */
   public static Date stringToDate(String date, String format) throws CoreException
   {
      try
      {
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
    * Accepts Date and converts to "02/01/2004".
    *
    * @param date
    * @return      String
    */
   public static String dateToString(Date date)
   {
      return dateToString(date, "MM/dd/yyyy");
   }

   /**
    * Accepts a String date in the specified format and converts to a
    * Date object.
    *
    * @param date
    * @return                   Date
    * @exception CoreException  Description of Exception
    */
   public static Date stringToDate(String date) throws CoreException
   {
      return stringToDate(date, "MM/dd/yyyy");
   }

   /**
    * What quarter (1-4) is this date in? 1/1 to 3/31 is 1st quarter,
    * 4/1 to 6/30 is 2nd quarter, etc.
    *
    * @param date
    * @return                int
    * @throws CoreException
    */
   public static int quarterOfYear(Date date) throws CoreException
   {
      try
      {
         GregorianCalendar calendar = new GregorianCalendar();

         calendar.setTime(date);

         int year = calendar.get(GregorianCalendar.YEAR);
         Date begApril = mmddyyyyToDate("04/01/" + year);
         Date begJuly = mmddyyyyToDate("07/01/" + year);
         Date begOct = mmddyyyyToDate("10/01/" + year);

         if (date.compareTo(begApril) < 0)
            return 1;
         else if (date.compareTo(begJuly) < 0)
            return 2;
         else if (date.compareTo(begOct) < 0)
            return 3;
         else
            return 4;
      }
      catch (java.lang.Exception e)
      {
         throw new CoreException("Unable to get quarter of year for this date.", e);
      }
   }

   /**
    * What year is this date in?
    *
    * @param date
    * @return                int
    * @throws CoreException
    */
   public static int getYear(Date date) throws CoreException
   {
      try
      {
         GregorianCalendar calendar = new GregorianCalendar();

         calendar.setTime(date);
         return calendar.get(GregorianCalendar.YEAR);
      }
      catch (java.lang.Exception e)
      {
         throw new CoreException("Unable to extract the year from this date.", e);
      }
   }

   /**
    * What is the first day of this quarter in the specified year?
    *
    * @param quarter
    * @param year
    * @return
    * @throws CoreException
    */
   public static Date firstDayOfQuarter(int quarter, int year) throws CoreException
   {
      try
      {
         if (quarter == 1)
            return mmddyyyyToDate("01/01/" + year);
         else if (quarter == 2)
            return mmddyyyyToDate("04/01/" + year);
         else if (quarter == 3)
            return mmddyyyyToDate("07/01/" + year);
         else if (quarter == 4)
            return mmddyyyyToDate("10/01/" + year);
         else
            throw new CoreException("Error determining the first day of quarter " + quarter + ".  Quarter must be between 1-4.");
      }
      catch (java.lang.Exception e)
      {
         throw new CoreException("Error determining the first day of quarter " + quarter + ".", e);
      }
   }

   /**
    * What is the last day of this quarter in the specified year?
    *
    * @param quarter
    * @param year
    * @return
    * @throws CoreException
    */
   public static Date lastDayOfQuarter(int quarter, int year) throws CoreException
   {
      try
      {
         if (quarter == 1)
            return mmddyyyyToDate("03/31/" + year);
         else if (quarter == 2)
            return mmddyyyyToDate("06/30/" + year);
         else if (quarter == 3)
            return mmddyyyyToDate("09/30/" + year);
         else if (quarter == 4)
            return mmddyyyyToDate("12/31/" + year);
         else
            throw new CoreException("Error determining the last day of quarter " + quarter + ".  Quarter must be between 1-4.");
      }
      catch (java.lang.Exception e)
      {
         throw new CoreException("Error determining the last day of quarter " + quarter + ".", e);
      }
   }

   /**
    * Convert specified month (MM/YYYY format) to the previous month.
    * Ex. Converts "01/2005" to "12/2004".
    *
    * @param currentMonth  String
    * @return              String
    * @throws Exception
    */
   public static String previousMonth(String currentMonth) throws Exception
   {
      String previousDate = null;
      int intyear = Integer.parseInt(currentMonth.substring(0, 4));
      int intmonth = Integer.parseInt(currentMonth.substring(4, 6));
      String strMonth;

      if (intmonth == 1)
      {
         intmonth = 12;
         --intyear;
      }
      else
         --intmonth;

      if (intmonth < 10)
         strMonth = "0" + String.valueOf(intmonth);
      else
         strMonth = String.valueOf(intmonth);

      previousDate = String.valueOf(intyear) + strMonth;
      return previousDate;
   }

   /**
    * Convert specified month (MM/YYYY format) to the next month. Ex.
    * Converts "12/2004" to "01/2005".
    *
    * @param currentMonth  String
    * @return              String
    * @throws Exception
    */
   public static String nextMonth(String currentMonth) throws Exception
   {
      String nextDate = null;
      int intyear = Integer.parseInt(currentMonth.substring(0, 4));
      int intmonth = Integer.parseInt(currentMonth.substring(4, 6));
      String strMonth;

      if (intmonth == 12)
      {
         intmonth = 1;
         ++intyear;
      }
      else
         ++intmonth;

      if (intmonth < 10)
         strMonth = "0" + String.valueOf(intmonth);
      else
         strMonth = String.valueOf(intmonth);

      nextDate = String.valueOf(intyear) + strMonth;
      return nextDate;
   }
}


