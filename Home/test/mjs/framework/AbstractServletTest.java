package mjs.framework;

import java.util.Hashtable;
import javax.servlet.ServletConfig;


import mjs.mocks.MockServletConfig;
import mjs.setup.SetupServlet;
import mjs.core.AbstractTest;

@SuppressWarnings("unchecked")
public class AbstractServletTest extends AbstractTest {

    SetupServlet setupServlet = new SetupServlet();

    /**
     * Method to pass block xml to PMG
     */
    @SuppressWarnings("rawtypes")
    public void startServer() throws Exception {

        Hashtable initParams = new Hashtable();
        initParams.put("log4j-prop-file", "/mjs/config/log4j.properties");
        initParams.put("main-prop-file", "/mjs/config/main.properties");
    	//ServletConfig sc = new MockServletConfig(setupServlet,
    	//		                                 initParams);
    	ServletConfig sc = new MockServletConfig(initParams);
    	setupServlet.init(sc);
        // Server started.
    }

}
