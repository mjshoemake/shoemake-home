package mjs.core;

import java.util.Iterator;
import mjs.aggregation.OrderedMap;
import mjs.database.DataLayerException;
import mjs.database.Field;
import mjs.exceptions.ValidationException;
import mjs.utils.FormatUtils;
import mjs.view.ValidationErrorList;

/**
 * Struts action form class which is the base DynaActionForm class for Struts
 * applications. It includes a Log4J integration, so log messages can be sent
 * without instantiating the Logger object.
 */
@SuppressWarnings("unchecked")
public class DynaForm extends org.apache.struts.action.DynaActionForm implements Form {

    static final long serialVersionUID = -4174504602386548113L;

    /**
     * Constructor.
     */
    public DynaForm() {
    }
    
    /**
     * Constructor. Because no category is specified in this constructor, the
     * category defaults to "Action".
     */
    /**
     * Check to see if this form is valid.
     * 
     * @param mapping Description of Parameter
     * @return ValidationErrorList
     */
    public ValidationErrorList validate(OrderedMap mapping) throws ValidationException {
        try {
            ValidationErrorList errors = new ValidationErrorList();
            Iterator<Field> it = mapping.values().iterator();
            while (it.hasNext()) {
                Field field = it.next();
                String value = getPropertyValue(field);
                if (field.getPattern() != null) {
                    // Do not validate this field.  This field is generated based on a pattern.
                } else if (field.getIsRequired() && value == null) {
                    errors.addError(field.getName(), "This is a required field."); 
                } else if (value.toString().length() > field.getMaxLen()) {
                    errors.addError(field.getName(), "Too long (max field length is " + field.getMaxLen() + ").");
                } else if (field.getType().equals("string") && ! FormatUtils.isValidString(value, field)) {
                    errors.addError(field.getName(), "Must match the format " + field.getFormat() + ".");
                } else if (field.getType().equals("date") && ! FormatUtils.isValidDate(value, field)) {
                    errors.addError(field.getName(), "Must be a valid date matching the format " + field.getFormat() + ".");
                }
            }

            return errors;

        } catch (Exception e) {
            throw new ValidationException("Failed to validate data entered by user. "
                + e.getMessage(), e);
        }
    }

    /**
     * Populate the values for this prepared statement based on the property
     * values of the bean. This method accepts the already loaded mapping file
     * in the form of a hashtable.
     * 
     * @param bean Object
     * @param statement PreparedStatement
     * @param mapping Hashtable
     * @param pds PropertyDescriptor[]
     * @throws DataLayerException
     */
    public String getPropertyValue(Field field) throws ValidationException {
        try {
            if (field == null) {
                throw new ValidationException(
                    "Error getting property value. Field is null.");
            }
            
            String pattern = field.getPattern();    
            if (pattern != null) {
                return evaluatePattern(pattern);
            } else {
                Object value = this.get(field.getName());
                if (value instanceof String) {
                    return (String)value;
                } else {
                    throw new ValidationException("Form objects must only have String properties. Non-string property detected (" + field.getName() +").");
                }
            }
        } catch (Exception e) {
            throw new ValidationException("Error getting property value for property " + field.getName() + ". " + e.getMessage(), e);
        }
    }
    
    /**
     * Take the specified pattern and evaluate it, replacing ${name} tokens
     * with actual property values as needed.
     * @param pattern String
     * @return String
     */
    private String evaluatePattern(String pattern) throws ValidationException {
        // If received null, return null.
        if (pattern == null) {
            throw new ValidationException("Unable to evaluate pattern for this specified DynaForm. Specified pattern is null.");
        }
        
        String current = pattern;
        StringBuilder builder = new StringBuilder();
        // Find start/end position.
        int startPos = current.indexOf("${");
        while (startPos != -1) {
            int endPos = current.indexOf("}");
            // Found token.  Keep stuff before token.
            if (startPos != 0) {
                String prefix = current.substring(0,startPos);
                builder.append(prefix);
            }
            String name = current.substring(startPos+2, endPos);
            String remainder = current.substring(endPos+1);
            Object value = null;
            if (name != null) {
                value = this.get(name);
            } else {
                value = null;
            }
            
            if (value != null) {
                builder.append(value);
            } else {
                builder.append("${");
                builder.append(name);
                builder.append("}");
            }
            current = remainder;
            
            // Look for more tokens.
            startPos = current.indexOf("${");
        }
        builder.append(current);
        return builder.toString().trim();
    }

    
    
}
