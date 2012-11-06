package mjs.utils;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import mjs.exceptions.CoreException;

/**
 * This class allows classes that are not designed as singletons to be used as
 * singletons. The actual object instances are managed in a Hashtable indexed by
 * class name. The getInstance() method checks the Hashtable to see if an object
 * exists for that class. If not, it creates a new one, places it in the
 * Hashtable, and return the new object to the caller. Otherwise it returns the
 * one already in hand.
 * <p>
 * This class is itself a singleton.
 * 
 * @author mshoemake
 */
public class SingletonInstanceManager {

    private static final Logger log = Logger.getLogger("Servlet");

    /**
     * The list of singleton instances indexed by class name.
     */
    private Hashtable<String, Object> instanceList = new Hashtable<String, Object>();

    /**
     * The singleton instance of this class.
     */
    private static SingletonInstanceManager instance = null;

    /**
     * Default constructor.
     */
    private SingletonInstanceManager() {
    }

    /**
     * Singleton instance access method.
     * 
     * @return SingletonInstanceManager
     */
    public static synchronized SingletonInstanceManager getInstance() {
        if (instance == null) {
            instance = new SingletonInstanceManager();
        } 
        return instance;
    }

    /**
     * Gets (or creates) an instance of the specified classname.  If 
     * an instance of this class is not found then SingletonInstanceManager
     * will create, store, and return a new instance.
     *  
     * @param className String
     * @return Object 



     * @throws CoreException
     */
    @SuppressWarnings("rawtypes")

    public synchronized Object getInstance(String className) {
        Object classInstance = null;

        try {
            classInstance = instanceList.get(className);
            if (classInstance == null) {
                // Instantiate object for specified class name.
                Class objClass = Class.forName(className);
                classInstance = objClass.newInstance();

                if (classInstance == null) {
                 	return null;
                }
                
                instanceList.put(className, classInstance);
                return classInstance;
            } else {
                return classInstance;
            }
        } catch (Exception e) {
        	return null;
        }
    }

    /**
     * Get the stored instance associated with the specified key.  
     * @param key String
     * @throws CoreException
     */
    public synchronized Object getInstanceForKey(String key) throws CoreException {
        Object classInstance = null;

        try {
            classInstance = instanceList.get(key);
            if (classInstance == null) {
                // Instantiate object for specified class name.
                throw new CoreException("Error getting instance for key " + key + ".  No instance found.");
            } else {
                return classInstance;
            }
        } catch (Exception e) {
            throw new CoreException("Error instantiating object for key " + key + ".", e);
        }
    }

    /**
     * Put the specified instance and assign the specified key to it.  
     * @param name String
     * @param obj Object
     * @throws CoreException
     */
    public synchronized void putInstanceForKey(String key, Object obj) {

        try {
            if (obj == null) {
                throw new CoreException("Unable to add the specified object (null) as a singleton instance.  key = " + key);
            }   
            if (key == null) {
                throw new CoreException("Unable to add the specified object as a singleton instance.  Key is null.");
            }   
            instanceList.put(key, obj);
        } catch (Exception e) {
            log.error("Error trying to add the specified singleton object for key " + key + ".  This could cause multiple instances to exist for the specified singleton key.", e);
        }
    }
}
