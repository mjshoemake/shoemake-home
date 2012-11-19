package mjs.framework;

import org.junit.Before;
import org.junit.Test;

import mjs.core.ServletStarterTest;
import mjs.mocks.MockActionMapping;
import mjs.mocks.MockHttpServletRequest;
import mjs.mocks.MockHttpServletResponse;
import mjs.model.RecipeForm;
import mjs.recipes.AddTestRecipeAction;
import mjs.recipes.GetRecipesByLetterAction;
import mjs.recipes.ViewRecipeAction;

public class RecipeActionsTest extends ServletStarterTest {

	@Before
	public void setUp() throws Exception {
		// Start server.
		startServer();
	}

	/**
	 * Test method.
	 */
	@Test
	public void tesAddTestRecipes() {

		try {
			MockActionMapping mapping = new MockActionMapping();
			MockHttpServletRequest request = new MockHttpServletRequest();
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.setParameter("letter", "G");
			RecipeForm recipeForm = new RecipeForm();
			recipeForm.setMeals_pk("test");
			recipeForm.setMeal_categories_pk("7");
			recipeForm.setName("My First Test Recipe");
			recipeForm.setNotes("blah");
			recipeForm.setCookbook_pk("4");
			
			/*
			request.setParameter("meals_pk", "test");
		    request.setParameter("meal_categories_pk", "7");
		    request.setParameter("name", "My First Test Recipe");
		    request.setParameter("note", "blah");
		    request.setParameter("cookbook_pk", "4");
		    */
			
			AddTestRecipeAction action = new AddTestRecipeAction();
			action.execute(mapping, null, request, response);

			System.out.println("Test complete.  Exiting.");

		} catch (Exception e) {
			e.printStackTrace();
			assertFailed("Execution with no exceptions.  " + e.getMessage());
		} finally {
			// reportResults();
		}
	}

	public void testGetRecipesByLetter() {

		try {
			MockActionMapping mapping = new MockActionMapping();
			MockHttpServletRequest request = new MockHttpServletRequest();
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.setParameter("letter", "G");
			GetRecipesByLetterAction action = new GetRecipesByLetterAction();
			action.execute(mapping, null, request, response);

			System.out.println("Test complete.  Exiting.");

		} catch (Exception e) {
			e.printStackTrace();
			assertFailed("Execution with no exceptions.  " + e.getMessage());
		} finally {
			// reportResults();
		}
	}

	@Test
	public void testViewRecipe() {

		try {
			MockActionMapping mapping = new MockActionMapping();
			MockHttpServletRequest request = new MockHttpServletRequest();
			MockHttpServletResponse response = new MockHttpServletResponse();
			// request.setParameter("id", recipe.getRecipes_pk());
			request.setParameter("id", "7");
			RecipeForm recipeForm = new RecipeForm();
			ViewRecipeAction action = new ViewRecipeAction();
			action.execute(mapping, recipeForm, request, response);

			System.out.println("Test complete.  Exiting.");

		} catch (Exception e) {
			e.printStackTrace();
			assertFailed("Execution with no exceptions.  " + e.getMessage());
		} finally {
			// reportResults();
		}
	}

}
