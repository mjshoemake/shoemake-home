
package mjs.utils;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.log4j.Logger;
import mjs.exceptions.CoreException;
import mjs.utils.StringUtils;

/**
 * This class tracks performance metrics for various operations.
 * It tracks metrics based on a key or event type.  Each type can
 * be triggered multiple times.  The metrics for each event type
 * are:
 * <pre>
 *    * total elapsed time
 *    * # events (number of times the event type was triggered)
 *    * average elapsed time
 *    * max elapsed time
 *    * min elapsed time
 *    * total prep time (time that elapsed from the end of the previous
 *      event, regardless of event type, to the start of this one) 
 * </pre>
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class PerformanceMetrics extends Loggable
{
   /**
    * The log4j logger to use when writing log
    * messages.  This is populated by extracting
    * the logger using the Logger category.  The
    * default Logger category is "Test".  See
    * the public methods debug(), info(), etc.
    */
   protected static Logger logCore = Logger.getLogger("Core");

   /**
    * The log4j logger to use when writing log
    * messages.  This is populated by extracting
    * the logger using the Logger category.  The
    * default Logger category is "Test".  See
    * the public methods debug(), info(), etc.
    */
   protected static Logger logPerf = Logger.getLogger("Performance");

   /**
    * A hashtable that contains a list of groups.  All event types are 
    * contained in groups.  So, to register an event for an event type,
    * you must know both the group name and the event name.  The group
    * name is the key in the groups Hashtable.  The value is a Hashtable
    * of PerformanceEvent objects.     
    */
   private Hashtable groups = new Hashtable();

   /**
    * The date time stamp of the most recent endEvent() call.
    */
   private Date previousEndDate = null;
   
   /**
    * Start the event.
    * @param group String
    * @param eventType String
    */
   public void startEvent(String group, String eventType)
   {
      if (logPerf.isInfoEnabled())
      {
         Hashtable eventList = null;
         PerformanceEvent event = null;
         
         // Get the group.  If the group doesn't exist, create it.
         if (groups.containsKey(group))
            eventList = (Hashtable)(groups.get(group));         
         else
         {
            eventList = new Hashtable();
            groups.put(group, eventList);
         }   
         
         // Get the event.
         if (eventList.containsKey(eventType))
            event = (PerformanceEvent)(eventList.get(eventType));         
         else
         {
            event = new PerformanceEvent(eventType);
            eventList.put(eventType, event);
         }
         
         event.startEvent(previousEndDate);
         logPerf.debug(StringUtils.leftJustify(group, 25)+"  "+StringUtils.leftJustify(eventType, 30)+"  Start");
      }
   }

   /**
    * End the event.
    * @param group String
    * @param eventType String
    */
   public void endEvent(String group, String eventType)
   {
      if (logPerf.isInfoEnabled())
      {
         Hashtable eventList = null;
         PerformanceEvent event = null;

         // Get the group.  If the group doesn't exist, throw an exception.
         if (groups.containsKey(group))
            eventList = (Hashtable)(groups.get(group));         
         else
         {
            CoreException e = new CoreException("PerformanceEvent was never started.  group="+group+"  eventType="+eventType);
            logPerf.error("Can't end performance metric event.", e);
         }   
         
         // Get the event.  If the event doesn't exist, throw an exception.
         if (eventList.containsKey(eventType))
            event = (PerformanceEvent)(eventList.get(eventType));         
         else
         {
            CoreException e = new CoreException("PerformanceEvent was never started.  group="+group+"  eventType="+eventType);
            logPerf.error("Can't end performance metric event.", e);
         }   

         event.endEvent();
         previousEndDate = new Date();
         logPerf.debug("  "+StringUtils.leftJustify(group, 25)+"  "+StringUtils.leftJustify(eventType, 30)+"  End  "+event.getElapsedTimeAsText());
      }
   }
   
   /**
    * Write the metrics information to the trace log file for the specified
    * group.
    * @param group String
    * @throws CoreException
    */
   public void writeMetricsToLog(String group) throws CoreException
   {
      if (logPerf.isInfoEnabled())
      {
         Hashtable eventList = null;
         PerformanceEvent event = null;
         
         // Get the group.  If the group doesn't exist, throw an exception.
         if (groups.containsKey(group))
         {
            logPerf.info("");
            eventList = (Hashtable)(groups.get(group));         

            // Process each event type.
            Enumeration e = eventList.keys();
            String[] lines = PerformanceEvent.getLogHeaderText(group); 
            logPerf.info(lines[0]);
            logPerf.info(lines[1]);
            while (e.hasMoreElements())
            {
               String next = (String)e.nextElement();
               event = (PerformanceEvent)(eventList.get(next));
               logPerf.info(event.getLogText());
            }
         }
      }
   }

   /**
    * Write the metrics information to the trace log file for all groups.
    * @throws CoreException
    */
   public void writeMetricsToLog() throws CoreException
   {
      Enumeration e = groups.keys();
      while (e.hasMoreElements())
      {
         Object next = e.nextElement();
         writeMetricsToLog(next.toString());
      }
   }   
   
   /**
    * Clear all performance metrics and start from a clean slate.
    */
   public void resetMetrics() 
   {
      Enumeration e = groups.keys();
      while (e.hasMoreElements())
      {
         Hashtable eventList = (Hashtable)e.nextElement();
         if (eventList != null)
         {
            Enumeration events = eventList.elements();
            while (events.hasMoreElements())
            {
               PerformanceEvent event = (PerformanceEvent)events.nextElement();
               event.resetMetrics();
            }
         }
      }
   }

   /**
    * Clear the performance metrics for this group and start from a
    * clean slate.
    * @param group String 
    */
   public void resetMetrics(String group)
   {
      Hashtable eventList = (Hashtable)groups.get(group);
      if (eventList != null)
      {
         Enumeration events = eventList.elements();
         while (events.hasMoreElements())
         {
            PerformanceEvent event = (PerformanceEvent)events.nextElement();
            event.resetMetrics();
         }
      }
   }
   
}
