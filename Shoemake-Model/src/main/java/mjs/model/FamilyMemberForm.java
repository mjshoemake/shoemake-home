package mjs.model;

import java.util.Hashtable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mjs.model.AbstractForm;
import mjs.model.BusinessObject;
//import mjs.utils.FormatUtils;
import mjs.view.ValidationErrorList;

/**
 * This is the data object or suitcase for a UserForm. This data object should
 * not contain any business logic.
 */
public @Data @EqualsAndHashCode(callSuper=true) class FamilyMemberForm 
                                                extends AbstractForm 
                                                implements BusinessObject {
    
    static final long serialVersionUID = -4174504602386548113L;

    /**
     * The primary key for this object.
     */
    private String family_member_pk = "";

    /**
     * The user's first name.
     */
    private String fname = "";

    /**
     * The user's last name.
     */
    private String lname = "";

    /**
     * The family member's description.
     */
    private String description = "";

    /**
     * The family member's date of birth. 
     */
    private String dob = "";

    /**
     * The primary key.
     */
    public String getPk() {
        return family_member_pk;
    }

    /**
     * The primary key.
     */
    public void setPk(String value) {
        family_member_pk = value;
    }

    /**
     * The user's first name.
     * 
     * @param value The new Fname value.
     */
    public String getName() {
        if (fname != null && !fname.trim().equals("")) {
            return fname + " " + lname;
        } else {
            return lname;
        }
    }

    public void setName(String value) {
        // Do nothing. This method should not be called.
    }

    /**
     * Check to see if this form is valid.
     * 
     * @param mapping Description of Parameter
     * @return ValidationErrorList
     */
    @SuppressWarnings("rawtypes")
    public ValidationErrorList validate(Hashtable mapping) {
        ValidationErrorList errors = new ValidationErrorList();
        return errors;
    }

}
