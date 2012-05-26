package mjs.core;

//import java.util.ArrayList;

import org.apache.log4j.Logger;
import junit.framework.TestCase;

/**
 * This is the base class for test cases.
 */
public abstract class AbstractTest extends TestCase {

    protected Logger log = null;

    //private ArrayList<String> assertionFailures = new ArrayList<String>();

    /**
     * Report results.
     */
/*    
    public void reportResults() {
        log = Logger.getLogger("Test");
        
        // Process assertions.
        ArrayList<String> assertionFailures = getAssertionFailures();
        if (log != null) {
        	log.debug("Assertion failure count=" + assertionFailures.size());
        } else {
        	System.out.println("Assertion failure count=" + assertionFailures.size());
        }

        for (int i = 0; i <= assertionFailures.size() - 1; i++) {
            log.debug("   FAILURE #" + i + ": " + assertionFailures.get(i));
            Assert.assertTrue(assertionFailures.get(i), false);
        }

        log.debug("Test complete.  Exiting.");
    }
*/
    
    /**
     * Assert true.
     * 
     * @param msg String
     * @param test boolean
     */
 /*   
    protected void assertTrue(String msg,
                              boolean test) {
        log = Logger.getLogger("Test");
        if (!test) {
            assertionFailures.add(msg);
        } else {
            log.info("Assert success: " + msg);
        }
    }
*/    

    /**
     * Assert ok.
     * 
     * @param msg String
     * @param test boolean
     */
/*
    protected void assertOk(String msg) {
        log = Logger.getLogger("Test");
        log.info("Assert success: " + msg);
    }
*/
    
    /**
     * Assert failed.
     * 
     * @param msg String
     * @param test boolean
     */
    protected void assertFailed(String msg) {
    	assertTrue(msg, false);
    }
    
    /**
     * Get the list of assertion failures for this test.
     * 
     * @return ArrayList<String>
     */
/*    
    public ArrayList<String> getAssertionFailures() {
        return assertionFailures;
    }
*/
}
