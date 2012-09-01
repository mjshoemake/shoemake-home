package mjs.database;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.net.URL;

import mjs.core.AbstractTest;
import mjs.database.Field;
import mjs.database.FieldDefs;
import mjs.setup.SetupServlet;
import mjs.utils.LogUtils;
import mjs.xml.CastorObjectConverter;

public class DatabaseConfigTest extends AbstractTest {

    @Before
    public void setUp() throws Exception {
//        setUpEnvironment();
    }

    /**
     * Test method.
     */
    @Test
    public void testInitialize() {

        try {
            DatabaseConfig.initialize("/config/database.xml");
            
            System.out.println("Test complete.  Exiting.");
            assertTrue("Completed successfully.", true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }
    
}
