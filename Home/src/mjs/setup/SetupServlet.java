/*
 * $Id: SetupServlet.java 22348 2010-07-20 05:02:59Z seeraman $
 *
 **************************************************************************
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, Cisco Systems, Inc.
 ************************************************************************** 
 */
package mjs.setup;

//import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import mjs.database.DatabaseConfig;
import mjs.database.DatabaseDriver;
import mjs.exceptions.CoreException;
import mjs.utils.SingletonInstanceManager;
import mjs.utils.StringUtils;
//import com.cisco.ca.sse.femto.common.ads.ADSManager;
//import mjs.setup.FileMonitor;
//import com.cisco.ca.sse.femto.common.utils.SingletonInstanceManager;
//import mjs.utils.FemtoConstants;
import mjs.utils.FileUtils;
import mjs.xml.CastorObjectConverter;

/**
 * Setup Servlet.
 */
public final class SetupServlet extends HttpServlet implements Servlet {
	
    static final long serialVersionUID = 9020182288989758191L;
    
    private String MAIN_PROP_FILE_LOCATION = "/config/main.properties"; 
    private String LOG4J_PROP_FILE_LOCATION = "/config/log4j.properties"; 
    
    private static Logger log = null;

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig sc) throws ServletException {
		super.init(sc);

		System.out.println("Shoemake Home SetupServlet:init() START.");
        // Log4J Config
		loadLoggingConfiguration(sc);

	    Logger auditLog = Logger.getLogger("Audit"); 
		
        log.info("Server restarted.  All alarms have been reset.");
        auditLog.info("");
        auditLog.info("");
        auditLog.info("**********  Server started.  **********");

        // Main Config
        loadMainConfiguration(sc);
        
        System.out.println("Shoemake Home SetupServlet:init() Setting up DB.");
        // Database Initialization
        setupDB(sc);
        setupMybatis(sc);
        
        auditLog.info("**********  Server initialized.  **********");

        System.out.println("Shoemake Home SetupServlet:init() END.");
	}

	/**
	 * Load the logging (Log4J) configuration information.
	 * 
	 * @param sc ServletConfig - The servlet configuration object.
	 */
	private void loadLoggingConfiguration(ServletConfig sc) throws ServletException {

		try {
		    // Lookup the file name for log4j configuration
	        String fileName = LOG4J_PROP_FILE_LOCATION;
	        Properties props = FileUtils.getContents(fileName, null, null, true, true);
	        PropertyConfigurator.configure(props);
	 
	        // create an instance of the logger so it maybe used
	        log = Logger.getLogger("Servlet");
	        log.debug("Logger is created.");
	        
	        // We also need to cache the log4j config info separately for use by 
	        // the administration framework to display the log files.
	        Log4jProperties log4jProperties = Log4jProperties.getInstance();
	        log4jProperties.loadProperties(fileName);
	        log.debug("Log4J properties loaded.");
	        log4jProperties.logProperties();
		} catch (CoreException e) {
			throw new ServletException("Failed to load the logging configuration.", e);
		}
	}

    /**
     * Load the main configuration information.
     * @param sc ServletConfig - The servlet configuration object.
     */
    private void loadMainConfiguration(ServletConfig sc) throws ServletException {

	    Logger auditLog = Logger.getLogger("Audit"); 
        try {

    	    // Lookup the file name for log4j configuration
            String fileName = MAIN_PROP_FILE_LOCATION;

            // We also need to cache the log4j config info separately for use by 
            // the administration framework to display the log files.
            MainProperties mainProperties = MainProperties.getInstance();
            mainProperties.loadProperties(fileName);
            log.debug("Log4J properties loaded.");
            mainProperties.logProperties();
        	auditLog.info("Loaded FPG configuration.");
        } catch (Exception e) {
        	auditLog.error("Error in loading the FPG configuration.", e);
        	throw new ServletException(e);
        }
    }
    
    /**
     * Load the main configuration information.
     * @param sc ServletConfig - The servlet configuration object.
     */
    private void setupDB(ServletConfig sc) throws ServletException {

	    Logger auditLog = Logger.getLogger("Audit"); 
        try {
        	DatabaseConfig.initialize("/config/database.xml");
        	auditLog.info("Configured MySQL database.");
        } catch (Exception e) {
        	auditLog.error("Error configuring MySQL database.", e);
        	throw new ServletException(e);
        }
    }
    
    /**
     * Load the main configuration information.
     * @param sc ServletConfig - The servlet configuration object.
     */
    private void setupMybatis(ServletConfig sc) throws ServletException {

	    Logger auditLog = Logger.getLogger("Audit"); 
        try {

    	    // Lookup the file name for log4j configuration
        	String resource = "/config/mybatis-config.xml";
        	InputStream inputStream = FileUtils.getFileAsStream(resource, true);
        	InputStreamReader reader = new InputStreamReader(inputStream);
        	SqlSessionFactory sqlSessionFactory = null;
        	try {
        	    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        	} catch (Throwable t) {
        		auditLog.error("Error creating SqlSessionFactory!!! " + t.getMessage(), t);
        	}
            SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
            mgr.putInstanceForKey(SqlSessionFactory.class.getName(), sqlSessionFactory);
        	auditLog.info("Configured Mybatis database access... DONE.");
        	
        } catch (Exception e) {
           	auditLog.error("Error configuring Mybatis database access.", e);
        	throw new ServletException(e);
        }
    }
    
    
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		super.destroy();
		
        log.debug("Destroying SetupServlet...");
        // Additional destroy tasks here.
        log.debug("Destroying SetupServlet... DONE.");
	}
	
}