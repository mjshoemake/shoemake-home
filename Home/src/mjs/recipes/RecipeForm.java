package mjs.recipes;

import mjs.aggregation.OrderedMap;
import mjs.core.AbstractForm;
import mjs.model.BusinessObject;
import mjs.view.ValidationErrorList;

/**
 * This is the data object or suitcase for a RecipeForm. This data object should
 * not contain any business logic.
 */
public class RecipeForm extends AbstractForm implements BusinessObject {
	static final long serialVersionUID = -4174504602386548113L;

	/**
	 * The recipes primary key. This is how users should be referenced in the
	 * database.
	 */
	private String recipes_pk = "";

	/**
	 * The recipe name.
	 */
	private String name = "";

	/**
	 * The recipe directions.
	 */
	private String directions = "";

	/**
	 * The recipe ingredients.
	 */
	private String ingredients = "";

	/**
	 * The servings information.
	 */
	private String servings = "";

	/**
	 * The serving size.
	 */
	private String serving_size = "";

	/**
	 * The cookbook PK.
	 */
	private String cookbook_pk = "";

	/**
	 * The nutrition information.
	 */
	private String nutrition = "";

	/**
	 * Calories per serving.
	 */
	private String calories_per_serving = "";

	/**
	 * The picture filename.
	 */
	private String picture_filename = "";

	/**
	 * The meals PK.
	 */
	private String meals_pk = "";

	/**
	 * The meal categories PK.
	 */
	private String meal_categories_pk = "";

	/**
	 * The notes.
	 */
	private String notes = "";

	/**
	 * Favorite (Y/N)?
	 */
	private String favorite = "";

	/**
	 * Constructor.
	 */
	public RecipeForm(String recipes_pk, String name, String directions,
			String directions_filename) {
		this.recipes_pk = recipes_pk;
		this.name = name;
	}

	/**
	 * Constructor.
	 */
	public RecipeForm() {
	}

	/**
	 * The recipes primary key. This is how users should be referenced in the
	 * database.
	 */
	public String getRecipes_pk() {
		return recipes_pk;
	}

	/**
	 * The recipes primary key. This is how users should be referenced in the
	 * database.
	 */
	public void setRecipes_pk(String value) {
		recipes_pk = value;
	}

	/**
	 * The primary key. Implemented from BusinessObject interface which allows
	 * this object to be used in conjunction with PaginatedList.
	 */
	public String getPK() {
		return recipes_pk;
	}

	/**
	 * The primary key. Implemented from BusinessObject interface which allows
	 * this object to be used in conjunction with PaginatedList.
	 */
	public void setPK(String value) {
		recipes_pk = value;
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
	 * The recipe directions.
	 */
	public String getDirections() {
		return directions;
	}

	/**
	 * The recipe directions.
	 */
	public String getIngredients() {
		return ingredients;
	}

	/**
	 * The servings information.
	 */
	public String getServings() {
		return servings;
	}

	/**
	 * The cookbook PK.
	 */
	public String getCookbook_pk() {
		return cookbook_pk;
	}

	/**
	 * The nutrition information.
	 */
	public String getNutrition() {
		return nutrition;
	}

	/**
	 * The calories per serving.
	 */
	public String getCalories_per_serving() {
		return calories_per_serving;
	}

	/**
	 * The calories per serving.
	 */
	public String getServing_size() {
		return serving_size;
	}

	/**
	 * The picture filename.
	 */
	public String getPicture_filename() {
		return picture_filename;
	}

	/**
	 * The meals PK.
	 */
	public String getMeals_pk() {
		return meals_pk;
	}

	/**
	 * The meal categories PK.
	 */
	public String getMeal_categories_pk() {
		return meal_categories_pk;
	}

	/**
	 * The notes.
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * The recipe directions.
	 */
	public void setDirections(String value) {
		directions = value;
	}

	/**
	 * The recipe ingredients.
	 */
	public void setIngredients(String value) {
		ingredients = value;
	}

	/**
	 * The servings information.
	 */
	public void setServings(String value) {
		servings = value;
	}

	/**
	 * The cookbook PK.
	 */
	public void setCookbook_pk(String value) {
		cookbook_pk = value;
	}

	/**
	 * The nutrition information.
	 */
	public void setNutrition(String value) {
		nutrition = value;
	}

	/**
	 * The calories per serving.
	 */
	public void setCalories_per_serving(String value) {
		calories_per_serving = value;
	}

	/**
	 * The picture filename.
	 */
	public void setPicture_filename(String value) {
		picture_filename = value;
	}

	/**
	 * The meals PK.
	 */
	public void setMeals_pk(String value) {
		meals_pk = value;
	}

	/**
	 * The meal categories PK.
	 */
	public void setMeal_categories_pk(String value) {
		meal_categories_pk = value;
	}

	/**
	 * The notes.
	 */
	public void setNotes(String value) {
		notes = value;
	}

	/**
	 * The calories per serving.
	 */
	public void setServing_size(String value) {
		serving_size = value;
	}

	/**
	 * Favorite (Y/N)?  If not, this recipe is considered a recipe to try.
	 */
	public String getFavorite() {
		return favorite;
	}

	/**
	 * Favorite (Y/N)?  If not, this recipe is considered a recipe to try.
	 */
	public void setFavorite(String value) {
		if (value != null && value.toUpperCase().startsWith("Y")) {
			value = "Yes";
		} else {
			value = "No";
		}
		favorite = value;
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
