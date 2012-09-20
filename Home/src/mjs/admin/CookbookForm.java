package mjs.admin;

import mjs.aggregation.OrderedMap;
import mjs.model.BusinessObject;
import mjs.core.AbstractForm;
//import mjs.utils.FormatUtils;
import mjs.view.ValidationErrorList;

/**
 * This is the data object or suitcase for a Cookbook. This data object should
 * not contain any business logic.
 */
public class CookbookForm extends AbstractForm implements BusinessObject {
	static final long serialVersionUID = -4174504602386548113L;

	/**
	 * The primary key. This is how users should be referenced in the
	 * database.
	 */
	private String cookbooks_pk = "";

	/**
	 * The name.
	 */
	private String name = "";

	/**
	 * Constructor.
	 */
	public CookbookForm(String pk, String name) {
		this.cookbooks_pk = pk;
		this.name = name;
	}

	/**
	 * Constructor.
	 */
	public CookbookForm() {
	}

	/**
	 * The primary key. This is how users should be referenced in the
	 * database.
	 */
	public String getCookbooks_pk() {
		return cookbooks_pk;
	}

	/**
	 * The primary key. This is how users should be referenced in the
	 * database.
	 */
	public void setCookbooks_pk(String value) {
		cookbooks_pk = value;
	}

	/**
	 * The primary key. Implemented from BusinessObject interface which allows
	 * this object to be used in conjunction with PaginatedList.
	 */
	public String getPk() {
		return cookbooks_pk;
	}

	/**
	 * The primary key. Implemented from BusinessObject interface which allows
	 * this object to be used in conjunction with PaginatedList.
	 */
	public void setPk(String value) {
		cookbooks_pk = value;
	}

	/**
	 * The recipe name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * The recipe name.
	 */
	public void setName(String value) {
		name = value;
	}

	/**
	 * Check to see if this form is valid.
	 * 
	 * @param mapping
	 *            Description of Parameter
	 * @return ValidationErrorList
	 */
	public ValidationErrorList validate(OrderedMap mapping) {
		ValidationErrorList errors = new ValidationErrorList();

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
		 * if (! FormatUtils.isValidString(phone, def)) errors.addError("phone",
		 * "Input does not match the required format: " + def.getFormat() +
		 * ".");
		 */
		return errors;
	}

}
