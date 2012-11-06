/*******************************************************************************
 * $Id$
 *
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, 2011, Cisco Systems, Inc.
 *******************************************************************************/
package mjs.utils;

import java.util.Map;

/**
 * This class provides access to the environment variables in the 
 * system.  While classes could use System.getEnv() directly, that 
 * prevents you from being able to use mock, predefined environment
 * variables.
 * 
 * @author mshoemake
 */
public class EnvironmentVariablesLive implements EnvironmentVariables {

	/**
	 * Constructor.
	 */
	private EnvironmentVariablesLive() {
	}
	
	
	/**
	 * Retrieves the singleton instance of this class.
	 * @return EnvironmentVariables
	 */
	public synchronized static EnvironmentVariables getInstance() {
		EnvironmentVariables result = null;
		
		String key = EnvironmentVariables.class.getName();
		SingletonInstanceManager instanceMgr = SingletonInstanceManager.getInstance();
		result = (EnvironmentVariables)instanceMgr.getInstance(key);
		if (result == null) {
			result = new EnvironmentVariablesLive();
            instanceMgr.putInstanceForKey(key, result);
		}
		return result;
	}
	
    /**
     * Get the value for the specified environment variable.
     * @param environmentVarName String
     * @return String
     */
    public String getValue(String environmentVarName) {
    	return System.getenv(environmentVarName);
    }
    
    /**
     * Return the list of environment variables in the system.
     * @return Map<String,String>
     */
    public Map<String,String> getenv() {
    	return System.getenv();
    }

}
