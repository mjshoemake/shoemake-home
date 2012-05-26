package mjs.core.utils;

import org.apache.log4j.Logger;


/**
 * A base class for classes that do not need to extend another class.
 * This class exposes the Log4J functionality so that can just be
 * inherited.
 *
 * @author   mshoemake
 */
public class Loggable
{
   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Model".
    */
   protected Logger log = null;

   /**
    * Constructor.
    *
    * @param logCategory  String
    */
   public Loggable(String logCategory)
   {
      if (logCategory != null)
         log = Logger.getLogger(logCategory);
      else
         log = Logger.getLogger("Core");
   }

   /**
    * Constructor.
    *
    */
   public Loggable()
   {
      log = Logger.getLogger("Core");
   }

}
