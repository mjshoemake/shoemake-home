package mjs.utils;

import java.util.Map;
import java.util.HashMap;

import mjs.utils.EnvironmentVariables;

public class EnvironmentVariablesMock implements EnvironmentVariables {

	/**
	 * Constructor.
	 */
	private EnvironmentVariablesMock() {
	}
	
	/**
	 * Retrieves the singleton instance of this class.
	 * @return EnvironmentVariables
	 */
	public synchronized static EnvironmentVariables getInstance() {
		EnvironmentVariables result = null;

		// Just create a new instance every time.  For mock instance, no big deal.
		// This forces a mock instance, in case a live instance was created first.
		// Once this method has been called, all subsequent calls to getInstance() 
		// on the live class will return the mock instance.  This should be used
		// for JUnit test cases and can also be used for lock dev environments as
		// needed.
        result = new EnvironmentVariablesMock();
		String key = EnvironmentVariables.class.getName();
		SingletonInstanceManager instanceMgr = SingletonInstanceManager.getInstance();
        instanceMgr.putInstanceForKey(key, result);
		return result;
	}
	
    /**
     * Get the value for the specified environment variable.
     * @param environmentVarName String
     * @return String
     */
    public String getValue(String environmentVarName) {
    	String result = null;
    	if (environmentVarName.equals(EnvironmentVariables.ENV_MJS_CONF)) {
    		result = "undefined";
        } else {
    		result = null;
    	}
    	return result;
    }
	
    /**
     * Return the list of environment variables in the system.
     * @return Map<String,String>
     */
    public Map<String,String> getenv() {
    	Map<String,String> map = new HashMap<String,String>();
    	map.put(EnvironmentVariables.ENV_MJS_CONF, "undefined");
        return map;    	
    }
}
