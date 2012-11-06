package mjs.admin;

import lombok.Data;
import mjs.aggregation.OrderedMap;
import mjs.core.AbstractForm;
import mjs.model.BusinessObject;
import mjs.view.ValidationErrorList;

/**
 * This is the data object or suitcase for a Meal. This data object should
 * not contain any business logic.
 */
public @Data class MealCategoryForm extends AbstractForm implements BusinessObject {
	static final long serialVersionUID = -4174504602386548113L;

	/**
	 * The primary key. This is how users should be referenced in the
	 * database.
	 */
	private String meal_categories_pk = "";

	/**
	 * The name.
	 */
	private String name = "";

	/**
	 * The primary key. Implemented from BusinessObject interface which allows
	 * this object to be used in conjunction with PaginatedList.
	 */
	public String getPk() {
		return meal_categories_pk;
	}

	/**
	 * The primary key. Implemented from BusinessObject interface which allows
	 * this object to be used in conjunction with PaginatedList.
	 */
	public void setPk(String value) {
	   meal_categories_pk = value;
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
		return errors;
	}

}
