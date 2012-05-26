package mjs.core.database;

import com.accenture.core.utils.CoreException;
import org.apache.log4j.Logger;


/**
 * Struts action form class which is the base ActionForm class for
 * Bellsouth Struts applications. It includes a Log4J integration, so
 * log messages can be sent without instantiating the Logger object.
 * <p>
 *
 * Ex. <code>log.debug("Hello");</code>
 */
public abstract class ActionForm extends org.apache.struts.action.ActionForm
{
   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Action".
    */
   protected Logger log = Logger.getLogger("Action");

   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Core".
    */
   protected Logger coreLog = Logger.getLogger("Core");

   /**
    * Constructor. Because no category is specified in this
    * constructor, the category defaults to "Action".
    */
   public ActionForm()
   {
      this(null);
   }

   /**
    * Constructor. If the category is null, it will default to
    * "Action".
    *
    * @param category
    */
   public ActionForm(String category)
   {
      try
      {
         if (category != null)
            log = Logger.getLogger(category);
      }
      catch (java.lang.Exception e)
      {
         log.error(new CoreException("Error creating action form.", e));
      }
   }
}
