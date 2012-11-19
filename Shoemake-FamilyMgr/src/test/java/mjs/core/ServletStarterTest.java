package mjs.core;

import java.util.Hashtable;
import javax.servlet.ServletConfig;
import org.apache.log4j.Logger;
import junit.framework.TestCase;
import mjs.mocks.MockServletConfig;
import mjs.setup.SetupServlet;

@SuppressWarnings("unchecked")
public abstract class ServletStarterTest extends TestCase {

    /**
     * The Logger to use when writing to the log files.
     */
    protected Logger log = null;

    /**
     * The SetupServlet which initializes the app config, logging
     * framework, etc.
     */
    SetupServlet setupServlet = new SetupServlet();

    /**
     * Start the "server" by calling SetupServlet which initializes the
     * app config, the logging framework, etc. 
     */
    @SuppressWarnings("rawtypes")
    public void startServer() throws Exception {

        Hashtable initParams = new Hashtable();
        initParams.put("log4j-prop-file", "/mjs/config/log4j.properties");
        initParams.put("main-prop-file", "/mjs/config/main.properties");
    	ServletConfig sc = new MockServletConfig(initParams);
    	setupServlet.init(sc);
    	log = Logger.getLogger("Test");
        // Server started.
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
