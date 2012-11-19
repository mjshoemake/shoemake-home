package mjs.actions;

import org.junit.Before;
import org.junit.Test;

import mjs.core.ServletStarterTest;
import mjs.mocks.MockActionMapping;
import mjs.mocks.MockHttpServletRequest;
import mjs.mocks.MockHttpServletResponse;
import mjs.users.GetFamilyMemberListAction;

//@SuppressWarnings("rawtypes")
public class GetFamilyMemberListActionTest extends ServletStarterTest {

	@Before
	public void setUp() throws Exception {
		// Start server.
		startServer();
	}

	@Test
	public void testProcessRequest() {

		try {
			MockActionMapping mapping = new MockActionMapping();
			MockHttpServletRequest request = new MockHttpServletRequest();
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.getSession().setAttribute("userID", "mjshoemake");
			GetFamilyMemberListAction action = new GetFamilyMemberListAction();
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
