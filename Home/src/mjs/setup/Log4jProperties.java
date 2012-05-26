/*
 * $Id: Log4jProperties.java 19247 2010-03-09 15:10:39Z mshoemak $
 *
 **************************************************************************
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, Cisco Systems, Inc.
 **************************************************************************
 */

package mjs.setup;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import mjs.utils.FileUtils;

/**
 * This class loads the Log4J properties file into memory.
 */
@SuppressWarnings("rawtypes")
public class Log4jProperties {
	
    private static final Logger log = Logger.getLogger("Core");

	private static Lock lock = new ReentrantLock();

	protected Properties props;
	
	protected static Log4jProperties instance;
	
    /**
     * Private constructor (singleton).
     */
	protected Log4jProperties() {
	}
	
    /**
     * Singleton instance access method.
     * 
     * @return Log4jProperties
     */	
	public synchronized static Log4jProperties getInstance() {

	    if (instance == null) {
	        instance = new Log4jProperties();
	        return instance;
	    } else {
	        return instance;
	    }
	}
	
	/**
	 * Method loads the properties for the object.
	 */
	public void loadProperties(java.net.URL url) {
        lock.lock();
		props = FileUtils.getContents(url);
		log.info("Log4J property file loaded.");
        lock.unlock();
	}
	
	/**
	 * Method gets the value for the property if it exist in not it
	 * will return a null.
	 * 
	 * @param key for the property to get
	 * @return value for the property or null
	 */
	public String getProperty(String key) {
		String rtn = null;
		lock.lock();
		rtn = instance.props.getProperty(key);
		lock.unlock();		
		return rtn;		
	}
	
	/**
	 * Method gets the value for the property if it exists if not it will use the
	 * default value  
	 * @param key for the property to get
	 * @param defaultValue to use if value does not exist
	 * @return value of the property or the default value.
	 */
	public String getProperty(String key, String defaultValue) {
		String rtn = null;
		lock.lock();
		rtn = instance.props.getProperty(key, defaultValue);
		lock.unlock();
		return rtn;		
	}
		
	/**
	 * Log the contents of the properties file.
	 */
	public void logProperties() {
		Set<Object> keySet = instance.props.keySet();
		Iterator iterator = keySet.iterator();
		log.debug("Log4J properties:");
		while (iterator.hasNext()) {
			Object key = iterator.next();
			Object value = instance.props.get(key);
			log.debug("   " + key.toString() + "=" + value.toString());
		}
	}
	
}
