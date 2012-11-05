/*
 * $Id: SyncReference.java 17968 2010-02-01 08:29:56Z navala $
 *
 **************************************************************************
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, Cisco Systems, Inc.
 **************************************************************************
 */

package mjs.utils;

import java.util.WeakHashMap;
import java.lang.ref.WeakReference;

import org.apache.log4j.Logger;

/**
 * This class is a singleton. It is responsible for synchronizing thread
 * access to a block of code using a common reference (value).
 * <p>
 * The <code> WeakReference</code> allows the value object to be garbage
 * collected if there are no other strong references to the value object,
 * eliminating the need for using try-catch blocks and notifyAll() on the
 * value object as used by traditional synchronization techniques.
 * </p>
 *
 * <p>
 * Utilization of this class in your own code would look something like the
 * following:<br><br>
 * <code>
 * .<br>
 * .<br>
 * .<br>
 * &nbsp;&nbsp;&nbsp;synchronized (SyncReference.getInstance().get(yourKeyObj) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.<br>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <i>//Your code that needs to be synchronized.</i> <br>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.<br>
 *
 * &nbsp;&nbsp;&nbsp;} <br>
 *
 *     .<br>
 *     .<br>
 *     .<br>
 *
 * </code>
 *
 * </p>
 *
 * <p><i>
 * This class utilizes techniques described in Java Developers Journal /
 * Volume: 07 Issue: 01 @ http://www.sys-con.com/story/?storyid=36817&DE=1#
 * </p></i>
 *
 * @author tmccauley
 * @version 1.
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class SyncReference {

    /**
     * Instance of the <code>SyncReferance</code> to make a singleton.
     */
    private static SyncReference instance;
    
    /**
     * Instance of the <code>Logger</code> object
     */	
    private static final Logger log = Logger.getLogger("Core");
    
    /**
     * Private Constructor used by the public getInstance().
     */
    private SyncReference(){}
    
    /**
     * WeakHashMap responsible for containing references to the values that
     * are currently being processed.  When the value no longer has a strong
     * reference, garbage collection removed this value from the WeakHashMap.
     */
    private WeakHashMap map = new WeakHashMap() {
        
    	/**
         * Inner class for the WeakHashMap that is responsible for retrieving a
         * specified value reference from the WeakHashMap.  If the device is
         * found in the WeakHashMap it will return the reference to this device.
         * If not found it will create the device's reference and it will be
         * inserted into the WeakHashMap, then the devices reference will be
         * returned.
         *
         * @param key that uniquely identifies the value that is being looked up 
         * in the WeakHashMap.
         * @return Object reference to the value that is now under access control
         * thanks to the WeakHashMap.
         *
         */
        public final Object get(Object key) {
            Object reference = super.get(key);

            // Attempt to get the device out of the WeakHashMap
            Object value =
                    ((reference == null) ? null :
                    ((WeakReference)reference).get());

            /*
             * If the value was not found in the WeakHashMap, create a weak
             * reference to this value and insert it into the weak map.
             */
            if (value == null) {
                log.info("SyncReference - get(): Value reference not found in"
                        + " WeakHashMap for key " + key);
                log.debug("SyncReference - get(): Create new reference"
                        + " and insert into WeakHashMap");
                value = key;
                put (value, new WeakReference(value));
            } else {
                log.info("SyncReference - get(): Value reference found in"
                        + " WeakHashMap");
            }

            return value;
        }
    };

    /**
     * Call to create an instance of the <code>SyncReference</code>
     * @return instance of the <code>SyncReference</code>
     */
    public static SyncReference getInstance(){
    if (instance == null) {
            instance = new SyncReference();
        }
      return instance;
    }

    //---- get ----
    /**
     * Method is responsible for returning a reference for a value
     * specified by the parameter <code>key</code> in the WeakHashMap.
     *
     * @param key that uniquely identifies the value that is being
     * looked up in the WeakHashMap.
     * @return Object reference to the value that is now under access control
     * thanks to the WeakHashMap.
     */
    public synchronized Object get(Object key) {
        return map.get(key);
    }

}