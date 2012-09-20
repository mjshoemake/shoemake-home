package mjs.recipes;

import mjs.aggregation.OrderedMap;
import mjs.core.AbstractForm;
import mjs.model.BusinessObject;
import mjs.view.ValidationErrorList;

/**
 * This is the data object or suitcase for a RecipeForm. This data object should
 * not contain any business logic.
 */
public class RecipeSearchForm extends AbstractForm implements BusinessObject {

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
    * The cookbook.
    */
   private String cookbook = "";

   /**
    * The meal.
    */
   private String meal = "";

   /**
    * The meal category.
    */
   private String meal_category = "";

	/**
	 * The serving size.
	 */
	private String serving_size = "";

   /**
    * Calories per serving.
    */
   private String calories_per_serving = "";
   
   /**
    * Favorite (Y/N).
    */
   private String favorite = "";

	/**
	 * Constructor.
	 */
	public RecipeSearchForm() {
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
   public String getPk() {
      return recipes_pk;
   }

   /**
    * The primary key. Implemented from BusinessObject interface which allows
    * this object to be used in conjunction with PaginatedList.
    */
   public void setPk(String value) {
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
	 * The cookbook.
	 */
	public String getCookbook() {
		return cookbook;
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
	 * The meals.
	 */
	public String getMeal() {
		return meal;
	}

	/**
	 * The meal category.
	 */
	public String getMeal_category() {
		return meal_category;
	}

	/**
	 * The cookbook PK.
	 */
	public void setCookbook(String value) {
		cookbook = value;
	}

	/**
	 * Favorite (Y/N).
	 */
	public void setFavorite(String value) {
		favorite = value;
	}

	/**
	 * Favorite (Y/N).
	 */
	public String getFavorite() {
		return favorite;
	}

	/**
	 * The calories per serving.
	 */
	public void setCalories_per_serving(String value) {
		calories_per_serving = value;
	}

	/**
	 * The meal.
	 */
	public void setMeal(String value) {
		meal = value;
	}

	/**
	 * The meal categories PK.
	 */
	public void setMeal_category(String value) {
		meal_category = value;
	}

	/**
	 * The calories per serving.
	 */
	public void setServing_size(String value) {
		serving_size = value;
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
