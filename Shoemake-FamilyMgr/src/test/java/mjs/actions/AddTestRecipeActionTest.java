package mjs.actions;

import java.util.Date;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import mjs.core.ServletStarterTest;
import mjs.database.PaginatedList;
import mjs.mocks.MockActionMapping;
import mjs.mocks.MockHttpServletRequest;
import mjs.mocks.MockHttpServletResponse;
import mjs.recipes.AddTestRecipeAction;
import mjs.model.RecipeForm;
import mjs.utils.Constants;

//@SuppressWarnings("rawtypes")
public class AddTestRecipeActionTest extends ServletStarterTest {

	@Before
	public void setUp() throws Exception {
		// Start server.
		startServer();
	}

	/**
	 * Test method.
	 */
	@Test
	public void testAddTestRecipes() {

		try {
			int randomNum = new Random(new Date().getTime()).nextInt(); 
			
			MockActionMapping mapping = new MockActionMapping();
			MockHttpServletRequest request = new MockHttpServletRequest();
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.getSession().setAttribute("userID", "mjshoemake");
			RecipeForm recipeForm = new RecipeForm();
			recipeForm.setMeals_pk("1");
			recipeForm.setMeal_categories_pk("7");
			recipeForm.setName("Test Recipe " + randomNum);
			recipeForm.setNotes("blah");
			recipeForm.setCookbook_pk("4");

			request.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_CACHE, new PaginatedList(RecipeForm.class, 20, 200, "Success")); 
			
			/*
			request.setParameter("meals_pk", "test");
		    request.setParameter("meal_categories_pk", "7");
		    request.setParameter("name", "My First Test Recipe");
		    request.setParameter("note", "blah");
		    request.setParameter("cookbook_pk", "4");
		    */
			
			AddTestRecipeAction action = new AddTestRecipeAction();
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
