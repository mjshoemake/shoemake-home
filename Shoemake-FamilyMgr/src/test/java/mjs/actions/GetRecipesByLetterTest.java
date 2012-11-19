package mjs.actions;

import org.junit.Before;
import org.junit.Test;

import mjs.core.ServletStarterTest;
import mjs.mocks.MockActionMapping;
import mjs.mocks.MockHttpServletRequest;
import mjs.mocks.MockHttpServletResponse;
import mjs.recipes.GetRecipesByLetterAction;

//@SuppressWarnings("rawtypes")
public class GetRecipesByLetterTest extends ServletStarterTest {

	@Before
	public void setUp() throws Exception {
		// Start server.
		startServer();
	}

	@Test
	public void testGetRecipesByLetter() {

		try {
			MockActionMapping mapping = new MockActionMapping();
			MockHttpServletRequest request = new MockHttpServletRequest();
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.getSession().setAttribute("userID", "mjshoemake");
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

}
