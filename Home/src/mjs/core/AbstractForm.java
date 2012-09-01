package mjs.core;

import mjs.aggregation.OrderedMap;
import mjs.exceptions.CoreException;
import mjs.view.ValidationErrorList;

import org.apache.log4j.Logger;


/**
 * Struts action form class which is the base ActionForm class for
 * Struts applications. It includes a Log4J integration, so
 * log messages can be sent without instantiating the Logger object.
 */
public abstract class AbstractForm extends org.apache.struts.action.ActionForm  implements Form
{
    static final long serialVersionUID = -4174504602386548113L;

    /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Action".
    */
   protected Logger log = Logger.getLogger("Actions");

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
   public AbstractForm()
   {
	   this(null);
   }

   /**
    * Constructor. If the category is null, it will default to
    * "Action".
    *
    * @param category
    */
   public AbstractForm(String category)
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
   

	/**
	 * Check to see if this form is valid.
	 * 
	 * @param mapping Description of Parameter
	 * @return ValidationErrorList
	 */
	public ValidationErrorList validate(OrderedMap mapping) {
		ValidationErrorList errors = new ValidationErrorList();

		return errors;
	}
   
}
