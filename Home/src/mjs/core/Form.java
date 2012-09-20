package mjs.core;

import mjs.aggregation.OrderedMap;
import mjs.exceptions.ValidationException;
import mjs.view.ValidationErrorList;


/**
 * Struts action form class which is the base ActionForm class for
 * Struts applications. It includes a Log4J integration, so
 * log messages can be sent without instantiating the Logger object.
 */
public interface Form {

	/**
	 * Check to see if this form is valid.
	 * 
	 * @param mapping Description of Parameter
	 * @return ValidationErrorList
	 */
    public ValidationErrorList validate(OrderedMap mapping) throws ValidationException;
}
