package com.accenture.core.model;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;


/**
 * A base class for classes that do not need to extend another class.
 * This class exposes the Log4J functionality so that can just be
 * inherited.
 *
 * @author   mshoemake
 */
public class LoggableActionForm extends ActionForm
{
   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Model".
    */
   protected Logger log = Logger.getLogger("Model");

}
