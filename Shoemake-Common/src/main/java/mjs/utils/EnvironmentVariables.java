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
public interface EnvironmentVariables {

	// Environment variable names.
    public static final String ENV_MJS_CONF = "MJS_CONF";

    
    /**
     * Get the value for the specified environment variable.
     * @param environmentVarName String
     * @return String
     */
    public String getValue(String environmentVarName);

    /**
     * Return the list of environment variables in the system.
     * @return Map<String,String>
     */
    public Map<String,String> getenv();
    
}
