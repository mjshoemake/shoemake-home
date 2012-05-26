package mjs.framework;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import mjs.database.DatabaseDriver;
import mjs.setup.SetupServlet;
import mjs.utils.SingletonInstanceManager;

public class SetupServletTest extends AbstractServletTest {

    SetupServlet setupServlet = new SetupServlet();

    @Before
    public void setUp() throws Exception {
    }

    /**
     * Method to pass block xml to PMG
     */
    @Test
    public void testStartup() {

        try {
        	startServer();

        	// Test get connection.
            SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
            DatabaseDriver driver = (DatabaseDriver)mgr.getInstance(DatabaseDriver.class.getName());
            Connection conn = driver.getConnection();
            driver.releaseConnection(conn);
        	
            System.out.println("Test complete.  Exiting.");

        } catch (Exception e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }

}
