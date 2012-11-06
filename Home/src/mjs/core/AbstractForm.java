package mjs.core;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import mjs.aggregation.OrderedMap;
import mjs.database.DataLayerException;
import mjs.database.Field;
import mjs.database.FormatUtils;
import mjs.exceptions.ValidationException;
import mjs.utils.BeanUtils;
import mjs.view.ValidationErrorList;

/**
 * Struts action form class which is the base ActionForm class for Struts
 * applications. It includes a Log4J integration, so log messages can be sent
 * without instantiating the Logger object.
 */
public abstract class AbstractForm extends org.apache.struts.action.ActionForm implements Form {
    static final long serialVersionUID = -4174504602386548113L;

    private static final Logger log = Logger.getLogger("Core");
    
    /**
     * Constructor. Because no category is specified in this constructor, the
     * category defaults to "Action".
     */
    public AbstractForm() {
    }

    /**
     * Check to see if this form is valid.
     * 
     * @param mapping Description of Parameter
     * @return ValidationErrorList
     */
    public ValidationErrorList validate(OrderedMap mapping) throws ValidationException {
        try {
            ValidationErrorList errors = new ValidationErrorList();
            PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(this.getClass());

            // if (fname.equals("")) {
            // errors.addError("fname", "This is a required field.");
            // }
            // try {
            // DateUtils.parseDate(dob, DateUtils.DATE_PATTERN);
            // } catch (Exception e) {
            // errors.addError("dob", "This is not a valid date. Must be " +
            // DateUtils.DATE_PATTERN + ".");
            // }
            // }

            for (PropertyDescriptor pd : pds) {
                String value = getPropertyValue(this, pd);
                Field field = (Field)mapping.get(pd.getName());
                if (field == null) {
                    log.debug("Unable to find field " + pd.getName() + " in mapping file.");
                } else if (field.getIsRequired() && value == null) {
                    errors.addError(pd.getName(), "This is a required field."); 
                } else if (value.toString().length() > field.getMaxLen()) {
                    errors.addError(pd.getName(), "Too long (max field length is " + field.getMaxLen() + ").");
                } else if (field.getType().equals("string") && ! FormatUtils.isValidString(value, field)) {
                    errors.addError(pd.getName(), "Must match the format " + field.getFormat() + ".");
                } else if (field.getType().equals("date") && ! FormatUtils.isValidDate(value, field)) {
                    errors.addError(pd.getName(), "Must be a valid date matching the format " + field.getFormat() + ".");
                }
            }

            /*
             * if (uid.equals("")) errors.addError("user_ID",
             * "This is a required field."); if (firstName.equals(""))
             * errors.addError("fname", "This is a required field."); if
             * (lastName.equals("")) errors.addError("lname",
             * "This is a required field.");
             * 
             * // Phone format. FieldDefinition def =
             * (FieldDefinition)mapping.get("phone");
             * 
             * if (! FormatUtils.isValidString(phone, def))
             * errors.addError("phone",
             * "Input does not match the required format: " + def.getFormat() +
             * ".");
             */
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
    public String getPropertyValue(Object bean,
                                   PropertyDescriptor pd) throws ValidationException {
        try {
            if (pd == null)
                throw new ValidationException(
                    "Error getting property values. PropertyDescriptor is missing.");

            Method method = pd.getReadMethod();
            Object value = null;
            if (method != null) {
                Object[] args = {};
                value = method.invoke(bean, args);
            }
            if (value instanceof String) {
                return (String)value;
            } else {
                throw new ValidationException("Form objects must only have String properties. Non-string property detected (" + pd.getName() +").");
            }
        } catch (Exception e) {
            throw new ValidationException("Error getting property value for property " + pd.getName() + ". " + e.getMessage(), e);
        }
    }

}
