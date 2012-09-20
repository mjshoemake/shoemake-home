package mjs.view;

import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 * An object that contains all form validation errors found when the user
 * submitted the form. This object is used in conjunction with the u:inputError
 * JSP tag to display context-sensitive error messages to the user.
 */
public class ValidationErrorList {
    /**
     * The list of errors stored in a hashtable. The hashtable key is the form
     * property name in which the error was found. The value is the message
     * text.
     */
    private HashMap<String,String> errors = new HashMap<String,String>();

    /**
     * The log4j logger to use when writing log messages. This is populated by
     * extracting the logger using the Logger category. The default Logger
     * category is "Model".
     */
    protected Logger log = Logger.getLogger("Model");

    /**
     * Constructor.
     */
    public ValidationErrorList() {
    }

    /**
     * Get the error message associated with the specified form property.
     * 
     * @param property
     * @return String
     */
    public String getError(String property) {
        String msg = errors.get(property);
        if (msg == null) {
            return "";
        }
        return msg;
    }

    /**
     * Does this error list contain a message for the specified property?
     * 
     * @param property
     * @return boolean
     */
    public boolean contains(String property) {
        return errors.containsKey(property);
    }

    /**
     * Add an error message to the list associated with the specified form
     * property.
     * 
     * @param propertyName String
     * @param message String
     */
    public void addError(String propertyName,
                         String message) {
        log.debug("Validation Error: [" + propertyName + "] " + message);
        errors.put(propertyName, message);
    }

    /**
     * Clear the error list.
     */
    public void clear() {
        errors.clear();
    }

    /**
     * Get the list of properties that contained errors as an Enumeration
     * object.
     * 
     * @return Enumeration
     */
    public Iterator<String> getFailedProperties() {
        return errors.keySet().iterator();
    }

    /**
     * Is the error list empty?
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        return errors.isEmpty();
    }

    /**
     * Write the contents of this error list to the log.
     */
    public void logContents() {
        Iterator<String> it = getFailedProperties();
        log.debug("ValidationErrorList contents:");
        while (it.hasNext()) {
            String key = it.next();
            String msg = this.getError(key);
            log.debug("   " + key + " -> " + msg);
        }
    }
    
}
