package mjs.actions;

import org.junit.Before;
import org.junit.Test;

import mjs.aggregation.OrderedMap;
import mjs.database.DatabaseDriver;
import mjs.database.PaginatedList;
import mjs.framework.ServletStarter;
import mjs.mocks.MockActionMapping;
import mjs.mocks.MockHttpServletRequest;
import mjs.mocks.MockHttpServletResponse;
import mjs.model.FamilyMemberForm;
import mjs.users.AddFamilyMemberAction;
import mjs.utils.Constants;
import mjs.utils.SingletonInstanceManager;

//@SuppressWarnings("rawtypes")
public class AddFamilyMemberActionTest extends ServletStarter {

	@Before
	public void setUp() throws Exception {
		// Start server.
		startServer();
	}

	/**
	 * Test method.
	 */
/*	
	@Test
	public void testAddFamilyMemberInvalidDOB() {

		try {
			MockActionMapping mapping = new MockActionMapping();
			MockHttpServletRequest request = new MockHttpServletRequest();
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.getSession().setAttribute("userID", "mjshoemake");
			DynaForm form = new DynaForm();
			form.set("family_member_pk", null);
            form.set("fname", "Test");
            form.set("lname", "Child");
            form.set("description", "Test Child");
            form.set("dob", "19730202");
			request.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_CACHE, new PaginatedList(DynaForm.class, 20, 200, "Success")); 
			
			AddFamilyMemberAction action = new AddFamilyMemberAction();
			action.execute(mapping, form, request, response);

			System.out.println("Test complete.  Exiting.");

		} catch (Exception e) {
			e.printStackTrace();
			assertFailed("Execution with no exceptions.  " + e.getMessage());
		} finally {
			// reportResults();
		}
	}
*/	
    /**
     * Test method.
     */
    @Test
    public void testAddFamilyMemberSuccess() {

        try {
            MockActionMapping mapping = new MockActionMapping();
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            request.getSession().setAttribute("userID", "mjshoemake");
            FamilyMemberForm form = new FamilyMemberForm();
            form.setFname("Test");
            form.setLname("Child");
            form.setDescription("Test Child");
            form.setDob("2/22/1973");
            request.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_CACHE, new PaginatedList(FamilyMemberForm.class, 20, 200, "Success")); 
            
            AddFamilyMemberAction action = new AddFamilyMemberAction();
            action.execute(mapping, form, request, response);

            System.out.println("Test complete.  Exiting.");

        } catch (Exception e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            // reportResults();
        }
    }
    
	public void testDateValidation() {
        try {
            FamilyMemberForm form = new FamilyMemberForm();
            form.setFname("Test");
            form.setLname("Child");
            form.setDescription("Test Child");
            form.setDob("19730202");
            String mappingFile = "/mjs/users/FamilyMemberMapping.xml";
            SingletonInstanceManager imgr = SingletonInstanceManager.getInstance();
            DatabaseDriver driver = (DatabaseDriver)imgr.getInstanceForKey(DatabaseDriver.class.getName());
            OrderedMap mapping = driver.loadMapping(mappingFile);       
            form.validate(mapping);
        } catch (Exception e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            // reportResults();
        }
	}
}
