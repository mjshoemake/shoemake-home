package mjs.model;

import mjs.aggregation.OrderedMap;
import mjs.model.AbstractForm;
import mjs.view.ValidationErrorList;

/**
 * This is the data object or suitcase for a UserForm. This data object should
 * not contain any business logic.
 */
public class LoginForm extends AbstractForm {
	static final long serialVersionUID = -4174504602386548113L;

	/**
	 * The user login ID.
	 */
	private String username = "";

	/**
	 * The user's password.
	 */
	private String password = "";

	/**
	 * Constructor.
	 */
	public LoginForm(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Constructor.
	 */
	public LoginForm() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String value) {
		username = value;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String value) {
		password = value;
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
		if (username.equals(""))
			errors.addError("username", "This is a required field.");
		if (password.equals(""))
			errors.addError("password", "This is a required field.");
		return errors;
	}

}
