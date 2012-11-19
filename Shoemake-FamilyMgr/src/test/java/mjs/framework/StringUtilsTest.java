package mjs.framework;

import org.junit.Before;
import org.junit.Test;
import mjs.core.AbstractLoggableTest;

public class StringUtilsTest extends AbstractLoggableTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test method.
     */
    @Test
    public void testFormat() {

        try {
            String input = "ABCDE%sHIJKL";
            String result = String.format(input, "FG");
        	System.out.println(result);
        	
        	System.out.println("Test complete.  Exiting.");

        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }
    
}
