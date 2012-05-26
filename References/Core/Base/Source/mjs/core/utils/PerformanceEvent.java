
package mjs.core.utils;

import java.util.Date;
import org.apache.log4j.Logger;


/**
 * This class tracks performance metrics for a single event type. Each
 * type can be triggered multiple times. The metrics for each event
 * type are: <pre>
 *    * total elapsed time
 *    * # events (number of times the event type was triggered)
 *    * average elapsed time
 *    * max elapsed time
 *    * min elapsed time
 *    * total prep time (time that elapsed from the end of the previous
 *      event, regardless of event type, to the start of this one)
 *
 */
public class PerformanceEvent extends Loggable
{
   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Test". See the public methods
    * debug(), info(), etc.
    */
   protected Logger logCore = Logger.getLogger("Core");

   /**
    * The event type name used as the key to the event hashtable in
    * the PerformanceMetrics class.
    */
   private String eventType = null;

   /**
    * The end date for the most recently used event.
    */
   private Date previousEndDate = null;

   /**
    * The start date for the current event.
    */
   private Date startDate = null;

   /**
    * The end date for the current event.
    */
   private Date endDate = null;

   /**
    * The amount of elapsed time for all events of this type since the
    * last clear.
    */
   private long elapsedTime = 0;

   /**
    * The minimum amount of elapsed time for all events of this type
    * since the last clear.
    */
   private long minElapsedTime = Long.MAX_VALUE;

   /**
    * The maximum amount of elapsed time for all events of this type
    * since the last clear.
    */
   private long maxElapsedTime = Long.MIN_VALUE;

   /**
    * The total amount of elapsed time for all events of this type.
    */
   private long totalElapsedTime = 0;

   /**
    * The total amount of elapsed time for all events of this type.
    */
   private long totalMinElapsedTime = Long.MAX_VALUE;

   /**
    * The total amount of elapsed time for all events of this type.
    */
   private long totalMaxElapsedTime = Long.MIN_VALUE;

   /**
    * The total amount of time in the "elapsed since previous event"
    * category.
    */
   private long totalElapsedSincePrevious = 0;

   /**
    * The minimum amount of time in the "elapsed since previous event"
    * category.
    */
   private long minElapsedSincePrevious = 0;

   /**
    * The maximum amount of time in the "elapsed since previous event"
    * category.
    */
   private long maxElapsedSincePrevious = 0;

   /**
    * The number of events that have occurred for this type since the
    * last clear.
    */
   private int numEvents = 0;

   /**
    * The total number of events that have occurred for this type.
    */
   private int totalNumEvents = 0;

   /**
    * Constructor.
    *
    * @param eventType  String
    */
   public PerformanceEvent(String eventType)
   {
      this.eventType = eventType;
   }

   /**
    * Start the event.
    *
    * @param previousEndDate  Date
    */
   public void startEvent(Date previousEndDate)
   {
      startDate = new Date();
      this.previousEndDate = previousEndDate;
   }

   /**
    * End the event.
    */
   public void endEvent()
   {
      endDate = new Date();

      // Calculate durations.
      long start = startDate.getTime();
      long duration = endDate.getTime() - start;
      long prevDuration = 0;

      if (previousEndDate != null)
         prevDuration = start - previousEndDate.getTime();

      // Update metrics.
      numEvents++;
      totalNumEvents++;
      totalElapsedSincePrevious += prevDuration;
      totalElapsedTime += duration;
      elapsedTime += duration;

      // Min / Max
      if (duration < minElapsedTime)
         minElapsedTime = duration;
      if (duration > maxElapsedTime)
         maxElapsedTime = duration;
      if (prevDuration < minElapsedSincePrevious)
         minElapsedSincePrevious = prevDuration;
      if (prevDuration > maxElapsedSincePrevious)
         maxElapsedSincePrevious = prevDuration;
      if (duration < totalMinElapsedTime)
         totalMinElapsedTime = duration;
      if (duration > totalMaxElapsedTime)
         totalMaxElapsedTime = duration;

   }

   /**
    * Get the header for the metrics for use with the trace log file.
    *
    * @return   String[]
    */
   public static String[] getLogHeaderText()
   {
      String[] lines = new String[2];

      lines[0] = "                                                                 Elapsed Time                            Total Elapsed Time                Elapse Time Since Previous Event ";
      lines[1] = "                                                      Total     Average   Min       Max         Total     Average   Min       Max         Total     Average   Min       Max        # Events";
      return lines;
   }

   /**
    * Get the header for the metrics for use with the trace log file.
    *
    * @param group  Description of Parameter
    * @return       String[]
    */
   public static String[] getLogHeaderText(String group)
   {
      String[] lines = new String[2];

      lines[0] = StringUtils.leftJustify(group + ":", 54) + "           Elapsed Time                            Total Elapsed Time                Elapse Time Since Previous Event ";
      lines[1] = "                                                      Total     Average   Min       Max         Total     Average   Min       Max         Total     Average   Min       Max        # Events";
      return lines;
   }

   /**
    * Return the elapsed time duration as text (HH:MM:SS).
    *
    * @return   String
    */
   public String getElapsedTimeAsText()
   {
      return longToDuration(elapsedTime);
   }

   /**
    * Get the text to write to the trace log file for these metrics.
    *
    * @return   String
    */
   public String getLogText()
   {
      StringBuffer result = new StringBuffer();

      result.append("    ");

      result.append(StringUtils.leftJustify(eventType, 50));
      result.append("  ");

      // Section 1
      result.append(longToDuration(elapsedTime));
      result.append("  ");
      if (numEvents == 0)
         result.append("N/A     ");
      else
         result.append(longToDuration(elapsedTime / numEvents));
      result.append("  ");
      result.append(longToDuration(minElapsedTime));
      result.append("  ");
      result.append(longToDuration(maxElapsedTime));
      result.append("    ");

      // Section 2
      result.append(longToDuration(totalElapsedTime));
      result.append("  ");
      if (numEvents == 0)
         result.append("N/A     ");
      else
         result.append(longToDuration(totalElapsedTime / totalNumEvents));
      result.append("  ");
      result.append(longToDuration(totalMinElapsedTime));
      result.append("  ");
      result.append(longToDuration(totalMaxElapsedTime));
      result.append("    ");

      // Section 3
      result.append(longToDuration(totalElapsedSincePrevious));
      result.append("  ");
      if (numEvents == 0)
         result.append("N/A     ");
      else
         result.append(longToDuration(totalElapsedSincePrevious / numEvents));
      result.append("  ");
      result.append(longToDuration(minElapsedSincePrevious));
      result.append("  ");
      result.append(longToDuration(maxElapsedSincePrevious));

      // # Events
      result.append("   ");
      result.append(numEvents);
      return result.toString();
   }

   /**
    * Convert milliseconds to String representation of duration
    * (HH:MM:SS).
    *
    * @param value  long
    * @return       String
    */
   private String longToDuration(long value)
   {
      StringBuffer result = new StringBuffer();
      String seconds = null;
      String minutes = null;
      String hours = null;

      // Seconds
      long newValue = value / 1000;
      long remainder = newValue % 60;

      if (remainder < 10)
         seconds = "0" + remainder;
      else
         seconds = "" + remainder;

      // Minutes
      newValue = newValue / 60;
      remainder = newValue % 60;
      if (remainder < 10)
         minutes = "0" + remainder;
      else
         minutes = "" + remainder;

      // Hours
      remainder = newValue / 60;
      if (remainder < 10)
         hours = "0" + remainder;
      else
         minutes = "" + remainder;

      result.append(hours);
      result.append(':');
      result.append(minutes);
      result.append(':');
      result.append(seconds);
      return result.toString();
   }

   /**
    * Reset (clear) the metrics to start with a clean slate. This only
    * affects the primary metrics. A running total is still kept for
    * each metric type.
    */
   public void resetMetrics()
   {
      elapsedTime = 0;
      minElapsedTime = Long.MAX_VALUE;
      maxElapsedTime = Long.MIN_VALUE;
      startDate = null;
      endDate = null;
      numEvents = 0;
   }
}
