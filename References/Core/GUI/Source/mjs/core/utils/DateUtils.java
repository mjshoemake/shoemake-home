//
// file: WSDateUtils
// desc: Standard Witness dialog baseclass.
// proj: eQuality 6.1 and later
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/DateUtils.java-arc  $
// $Author:Mike Shoemake$
// $Revision:4$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.utils;

// Java imports
import java.text.*;
import java.util.*;
// Witness imports
import mjs.core.strings.InternationalizationStrings;


/**
 * The date handling and formatting utility class.  All date related functions should
 * be placed in and accessed from this class.  All methods in this class should be static, utility methods.
 */
public class DateUtils
{
   /**
    * The default date format ("MM/dd/yyyy").
    */
   public static final String DATE_PATTERN = InternationalizationStrings.szDateFormat;

   /**
    * The default time format ("hh:mm:ss a").
    */
   public static final String TIME_PATTERN = InternationalizationStrings.szTimeFormat;

   /**
    * The default date/time format ("MM/dd/yyyy hh:mm:ss a").
    */
   public static final String DATE_TIME_PATTERN = InternationalizationStrings.szDateTimeFormat;

   /**
    * The date format manager.  This handles all the date formatting functionality.
    */
   private static SimpleDateFormat fmtDateHandler = new SimpleDateFormat();

   /**
    * Parse the String to return a valid Date object. <p> This method uses the time zone.
    * @param    sDateTime    The String value that contains the date.
    * @param    sPattern     The String value that contains the pattern to use
    * (ie. "yyyy/MM/dd hh:mm:ss") during the conversion.
    */
   public static Date parseDate(String sDateTime, String sPattern)
   {
      TimeZone timeZone = TimeZone.getDefault();
      Date date = parseDate(sDateTime, sPattern, timeZone);
      return date;
   }

   /**
    * Parse the String to return a valid Date object.
    * @param    sDateTime    The String value that contains the date.
    * @param    sPattern     The String value that contains the pattern to use
    * (ie. "yyyy/MM/dd hh:mm:ss") during the conversion.
    * @param    timeZone     The specified time zone to use during the conversion.
    */
   public static Date parseDate(String sDateTime, String sPattern, TimeZone timeZone)
   {
      ParsePosition pos = new ParsePosition(0);
      fmtDateHandler.applyPattern(sPattern);
      fmtDateHandler.setTimeZone(timeZone);
      // Parse the input string.
      return fmtDateHandler.parse(sDateTime, pos);
   }

   /**
    * Convert the date to a String representation using the pattern specified.
    * @param    date         The given date.
    * @param    sPattern     The String value that contains the pattern to use
    * (ie. "yyyy/MM/dd hh:mm:ss") during the conversion.
    */
   public static String dateToString(Date date, String sPattern)
   {
      if (date != null)
      {
         SimpleDateFormat fmtDateHandler = new SimpleDateFormat(sPattern);
         // Perform conversion.
         return fmtDateHandler.format(date);
      }
      else
      {
         // Date is null.
         return "";
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
      boolean isDaylightSavings = false;
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
      boolean isDaylightSavings = false;
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

   // TOGETHER DIAGRAM DEPENDENCIES.
   // PLEASE DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# InternationalizationStrings lnkInternationalizationStrings; */
}


