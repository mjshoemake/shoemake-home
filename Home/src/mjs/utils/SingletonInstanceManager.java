package mjs.utils;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import mjs.exceptions.CoreException;

/**
 * This class allows classes that are not designed as singletons to 
 * be used as singletons.  The actual object instances are managed
 * in a Hashtable indexed by class name.  The getInstance() method 
 * checks the Hashtable to see if an object exists for that class.
 * If not, it creates a new one, places it in the Hashtable, and
 * return the new object to the caller.  Otherwise it returns the one
 * already in hand.
 * <p>
 * This class is itself a singleton.    
 *  
 * @author mshoemake
 */
public class SingletonInstanceManager {

    private static final Logger log = Logger.getLogger("Core");
    
    /**
     * The list of singleton instances indexed by class name.
     */
    private Hashtable<String,Object> instanceList = new Hashtable<String,Object>();
    
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
            log.debug("Creating new SingletonInstanceManager object...");
            instance = new SingletonInstanceManager();
        } else {
            log.debug("Using existing SingletonInstanceManager object...");
        }
        return instance;
    }

    /**
     * 
     * @param className
     * @return
     * @throws ServerException
     */
    @SuppressWarnings("rawtypes")
    public synchronized Object getInstance(String className) throws CoreException {
        Object classInstance = null;
        
        try {
            classInstance = instanceList.get(className);
            if (classInstance == null) {
                // Instantiate object for specified class name.
                Class objClass = Class.forName(className);
                classInstance = objClass.newInstance();
                instanceList.put(className, classInstance);
                log.debug("Creating new singleton instance: " + className);
                return classInstance;
            } else {
                log.debug("Reusing existing singleton instance: " + className);
                return classInstance;
            }
        } catch (Exception e) {
            throw new CoreException("Error instantiating object for class " + className + ".", e);            
        }
    }
    
    /**
     * Keep this instance of the specified class.  
     * @param object Object
     */
    public synchronized void storeInstance(Object object) {
    	String className = object.getClass().getName();
    	instanceList.put(className, object);
    	log.debug("Storing new singleton instance: " + className);
    }
}
