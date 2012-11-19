package mjs.actions;

import org.junit.Before;
import org.junit.Test;

import mjs.core.ServletStarterTest;
import mjs.mocks.MockActionMapping;
import mjs.mocks.MockHttpServletRequest;
import mjs.mocks.MockHttpServletResponse;
import mjs.model.RecipeForm;
import mjs.recipes.ViewRecipeAction;

//@SuppressWarnings("rawtypes")
public class ViewRecipeActionTest extends ServletStarterTest {

	@Before
	public void setUp() throws Exception {
		// Start server.
		startServer();
	}

	@Test
	public void testViewRecipe() {

		try {
			MockActionMapping mapping = new MockActionMapping();
			MockHttpServletRequest request = new MockHttpServletRequest();
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.getSession().setAttribute("userID", "mjshoemake");
			request.setParameter("id", "1");
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
