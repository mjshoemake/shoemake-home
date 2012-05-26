
package mjs.core.utils;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.ServletException;
import mjs.core.utils.CoreException;
import mjs.core.utils.BeanUtils;
import org.apache.log4j.Logger;


/**
 * This is a utility class for use with Log4J to help with tracing
 * messages. This class is also intended to provide a way to convert
 * PercData objects (PercList, PercBean, etc.) into an array of String
 * objects that describe the object's properties. This will be used to
 * give insight into a PercData object's state at run time.
 */
public class LogUtils
{
   /**
    * The log4j logger to use when writing Perc-specific log messages.
    * The percLogger traces messages to the "Core" category rather
    * than the servlet category. The percLogger should be used for
    * information that is only relevant when debugging Perc itself.
    */
   private static Logger log = Logger.getLogger("Core");

   /**
    * Replace text inside a java String object. Needed this because
    * String.replaceAll() isn't an option in 1.3.
    *
    * @param aInput       is the original String which may contain
    * substring aOldPattern
    * @param aOldPattern  is the non-empty substring which is to be
    * replaced
    * @param aNewPattern  is the replacement for aOldPattern
    * @return             Description of Return Value
    */
   public static String replaceAll(final String aInput,
         final String aOldPattern,
         final String aNewPattern)
   {
      if (aOldPattern.equals(""))
      {
         throw new IllegalArgumentException("Old pattern must have content.");
      }

      final StringBuffer result = new StringBuffer();

      //startIdx and idxOld delimit various chunks of aInput; these
      //chunks always end where aOldPattern begins
      int startIdx = 0;
      int idxOld = 0;

      while ((idxOld = aInput.indexOf(aOldPattern, startIdx)) >= 0)
      {
         //grab a part of aInput which does not include aOldPattern
         result.append(aInput.substring(startIdx, idxOld));
         //add aNewPattern to take place of aOldPattern
         result.append(aNewPattern);

         //reset the startIdx to just after the current match, to see
         //if there are any further matches
         startIdx = idxOld + aOldPattern.length();
      }
      //the final chunk will go to the end of aInput
      result.append(aInput.substring(startIdx));
      return result.toString();
   }

   /**
    * Log the exception. If the exception is of type CoreException,
    * walk the chain of exceptions and log each one until you get to
    * the end of the chain.
    *
    * @param logger  The Log4J logger to use.
    * @param ex      The java.lang.CoreException to log.
    */
   public static void log(Logger logger, java.lang.Throwable ex)
   {
      logger.error(ex.getMessage(), ex);

      boolean bContinue = true;
      boolean childFound = false;

      while (bContinue)
      {
         if (ex instanceof ServletException)
         {
            ServletException e = (ServletException)ex;

            if (e.getRootCause() != null)
            {
               ex = e.getRootCause();
               childFound = true;
            }
            else
               bContinue = false;
         }
         else if (ex instanceof CoreException)
         {
            CoreException e = (CoreException)ex;

            if (e.getNext() != null)
            {
               ex = e.getNext();
               childFound = true;
            }
            else
               bContinue = false;
         }
         else
            bContinue = false;
      }

      if (childFound)
      {
         log.error("---ROOT CAUSE---");
         logger.error(ex.getMessage(), ex);
      }
   }

   /**
    * Log the exception. If the exception is of type CoreException,
    * walk the chain of exceptions and log each one until you get to
    * the end of the chain.
    *
    * @param logger       The Log4J logger to use.
    * @param ex           The java.lang.CoreException to log.
    * @param contextInfo  Context information about the error.
    */
   public static void log(Logger logger, java.lang.Exception ex, String contextInfo)
   {
      logger.error("ERROR: " + contextInfo);
      log(logger, ex);
   }

   /**
    * Log the exception. If the exception is of type CoreException,
    * walk the chain of exceptions and log each one until you get to
    * the end of the chain.
    *
    * @param ex      The java.lang.CoreException to log.
    * @return        The value of the StackTraceAsString property.
    */
   public static String getStackTraceAsString(java.lang.Exception ex)
   {
      // Retrieve stack trace from exception.
      Vector list = new Vector();
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      PrintStream printStream = new PrintStream(stream);

      ex.printStackTrace(printStream);
      return stream.toString();
   }

   /**
    * Convert a bean to an array of String objects that contain the
    * properties of the bean. This is used to log out the details of
    * the bean.
    *
    * @param bean                 Object
    * @return                     String[]
    * @throws DataLayerException
    */
   public static String[] dataToStrings(Object bean) throws CoreException
   {
      ArrayList lines = new ArrayList();
      String[] result = null;

      String fieldName = null;

      try
      {
         processBean(bean, 0, lines);

         result = new String[lines.size()];
         // Convert Vector to array.
         for (int F = 0; F <= lines.size() - 1; F++)
         {
            result[F] = lines.get(F).toString();
         }

         return result;
      }
      catch (Exception e)
      {
         throw new CoreException("Error loading bean (column: " + fieldName + ").", e);
      }
   }

   private static ArrayList processBean(Object bean, int level, ArrayList lines) throws CoreException
   {
      try
      {
         Object[] args = new Object[0];
         String fieldName = null;
         PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());

         if (pds == null || pds.length == 0)
         {
            return lines;
         }

         StringBuffer line = null;

         if (bean instanceof Collection)
         {
            // Append the class name of the collection.
            line = new StringBuffer();
            line.append(bean.getClass().getName());
            lines.add(line.toString());

            // Process the collection.
            Collection coll = (Collection)bean;
            Object[] list = coll.toArray();

            for (int k = 0; k < list.length; k++)
            {
               StringBuffer nextItem = new StringBuffer();

               // Add indentation to simulate object hierarchy.
               for (int m = 0; m <= level; m++)
                  nextItem.append("   ");
               nextItem.append("Item #" + k + ": " + list[k].getClass().getName());
               lines.add(nextItem.toString());

               // Process the next bean.
               processBean(list[k], level + 1, lines);
            }
         }
         else
         {
            // Loop through the properties of the bean.
            for (int i = 0; i < pds.length; i++)
            {
               fieldName = pds[i].getName();

               // Get the getter method for this property.
               Method method = pds[i].getReadMethod();

               if (method != null)
               {
                  Object value = method.invoke(bean, args);

                  if (value == null)
                     value = "null";

                  line = new StringBuffer();

                  // Add indentation to simulate object hierarchy.
                  for (int j = 0; j <= level; j++)
                     line.append("   ");

                  line.append(fieldName);
                  line.append(" = ");

                  if (value instanceof Collection)
                  {
                     // Process the collection.
                     line.append(value.getClass().getName());
                     processBean(value, level + 1, lines);
                  }
                  else if (value instanceof Integer ||
                        value instanceof Long ||
                        value instanceof Double ||
                        value instanceof java.util.Date ||
                        value instanceof Float ||
                        value instanceof String)
                  {
                     // Append the actual property value converted to a String.
                     line.append(value.toString());
                     lines.add(line.toString());
                  }
                  else
                  {
                     // Append the actual property value converted to a String.
                     line.append(value.getClass().getName());
                     lines.add(line.toString());

                     // Process the next bean.
                     processBean(value, level + 1, lines);
                  }
               }
               else
               {
                  line = new StringBuffer();
                  line.append("   ");
                  line.append(fieldName);
                  line.append(" = NO GET METHOD FOUND.");
               }
            }
         }

         return lines;
      }
      catch (Exception e)
      {
         throw new CoreException("Error processing bean to log data: " + bean.getClass().getName() + ".", e);
      }
   }

}

// Change Log:
//
// 01/10/2005  MJS   epSAP Minor Enhancements 2001 Phase 1
