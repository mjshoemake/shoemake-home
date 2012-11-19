package mjs.core;

import java.util.Properties;
import javax.servlet.ServletException;
import junit.framework.TestCase;
import mjs.exceptions.CoreException;
import mjs.utils.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Baseclass for unit tests that need to write to a log
 * file.
 * @author mishoema
 */
public abstract class AbstractLoggableTest extends TestCase {

    /**
     * Log4j Logger.
     */
    Logger log = null;
    
    /**
     * Log4j.properties path.
     */
    private String LOG4J_PROP_FILE_LOCATION = "/config/log4j.properties"; 

    /**
     * Setup the unit test, including loading the 
     * log4j configuration and initialize the Logger
     * objects.
     * @throws Exception
     */
    public void setUp() throws Exception {
        loadLoggingConfiguration();
  }

    /**
     * Load the log4j configuration and prepare the 
     * loggers. 
     * @param sc ServletConfig
     * @throws ServletException
     */
    private void loadLoggingConfiguration() throws ServletException {
        try {
            // Lookup the file name for log4j configuration
            String fileName = LOG4J_PROP_FILE_LOCATION;
            Properties props = FileUtils.getContents(fileName, null, null, true, true);
            PropertyConfigurator.configure(props);
            // create an instance of the logger so it maybe used
            log = Logger.getLogger("Test");
            log.debug("Logger is created.");
        } catch (CoreException e) {
            throw new ServletException("Failed to load the logging configuration.", e);
        }
    }

    /**
     * Assert failed.
     * 
     * @param msg String
     * @param test boolean
     */
    protected void assertFailed(String msg) {
        assertTrue(msg, false);
    }
    
}
