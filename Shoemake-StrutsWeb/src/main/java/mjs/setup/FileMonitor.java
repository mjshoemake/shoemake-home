/*
 * $Id: FileMonitor.java 20219 2010-04-15 19:32:13Z tmccauley $
 *
 ***************************************************************************
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, Cisco Systems, Inc.
 *************************************************************************** 
 */

package mjs.setup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import mjs.utils.FileUtils;

import org.apache.log4j.Logger;

/**
 * This class is responsible handling the monitoring of a file on a local file system
 * for changes to the file's last modified date.  
 * 
 * @author tmccauley
 *
 */
public class FileMonitor {

	/** Reference to the <code>Logger</code> */
    private static final Logger log = Logger.getLogger("Core");

	/** Static reference to the FileMonitor ensuring only one exists per JVM */
    private static final FileMonitor instance = new FileMonitor();

    /** Timer thread to run as a daemon. */
    private Timer timer;

    /** Notification entries of FileMonitorTasks being executed by Timer */
    private Hashtable<String, FileMonitorTask> timerEntries;

    /** Static getInstance for returning a reference to this class */
    public static FileMonitor getInstance() {
        return instance;
    }

    /**
     * Overridden constructor responsible for creating the Timer and
     * notification list (timerEntries).
     */
    protected FileMonitor() {
        /* Create timer, run timer thread as daemon */
        timer = new Timer(true);
        
        /* Create timer entries */
        timerEntries = new Hashtable<String, FileMonitorTask>();
    }

    /**
     * Add a monitored file with a FileChangeListener.
     * 
     * @param listener
     *            listener to notify when the file changed.
     * @param fileName
     *            name of the file to monitor.
     * @param period
     *            polling period in milliseconds.
     */
    public void addFileChangeListener(FileChangeListener listener,
                                      String fileName,
                                      String servletPathPrefix,
                                      long period) throws FileNotFoundException {
    	
    	log.debug(String.format("Adding listener %s for file %s and polling " +
    			"period %d", listener, fileName, period));

        /* remove the existing listener for a file change */
        removeFileChangeListener(listener, fileName);
        
        /* create a new monitor task for the listener and file name */
        FileMonitorTask task = new FileMonitorTask(listener, fileName, servletPathPrefix);
        
        /* add the task and listener/filename to the notification list */
        timerEntries.put(fileName + listener.hashCode(), task);
        
        /* schedule the task, delay, and interval period on the timer */
        timer.schedule(task, 0, period);
        
        log.debug("Scheduled file listener for file " + fileName);
      
    }

    /**
     * Remove the listener from the notification list.
     * 
     * @param listener
     *            the listener to be removed.
     */
    public void removeFileChangeListener(FileChangeListener listener,
            String fileName) {
        
        FileMonitorTask task = (FileMonitorTask) timerEntries.remove(fileName
                + listener.hashCode());
        if (task != null) {
            task.cancel();
        }
    }

    /**
     * Notify the listener of the file change event.
     * 
     * @param listener
     *            the object registered to received the file change event
     * @param fileName
     *            the name of the file that changed.
     */
    protected void fireFileChangeEvent(FileChangeListener listener,
            String fileName) {
    	log.debug("File change event fired for file name " + fileName);
        listener.fileChanged(fileName);
    }

    /**
     * The inner class responsible for monitoring a file for change and
     * notifying listeners registered for its change.  
     * 
     * @author tmccauley
     * 
     */
    public class FileMonitorTask extends TimerTask {
        FileChangeListener listener;

        String fileName;

        File monitoredFile;

        long lastModified;

        /**
         * Constructor for creating a new FileMonitorTask
         * 
         * @param listener
         *            the object registered to receive a file change event
         * @param fileName
         *            the name of the file to monitor for change
         * @throws FileNotFoundException
         *             is thrown if the file to be monitored can not be found.
         */
        public FileMonitorTask(FileChangeListener listener, String fileName, String servletPathPrefix)
                throws FileNotFoundException {
            
            this.listener = listener;
            this.fileName = fileName;
            this.lastModified = 0;

            if (! FileUtils.doesFileExist(fileName)) {
                // Can't find the file.  Try adding the prefix...
                fileName = servletPathPrefix + fileName;

                if (! FileUtils.doesFileExist(fileName)) {
                    String msg = "ERROR: Cannot read the server config file [" + fileName + "]. " +
                                 "Please check the path of the init param in web.xml.";
                    FileNotFoundException e = new FileNotFoundException(msg); 
                    log.error(msg, e);
                    throw e;
                }    
            }
            
            this.monitoredFile = new File(fileName);

            if (this.monitoredFile.exists()) {
            	log.debug("File monitored " + monitoredFile.getName() + " exists");
                this.lastModified = this.monitoredFile.lastModified();
            } else {
                throw new FileNotFoundException("File Not Found: " + fileName);
            }

        }

        /**
         * The action to be performed by this FileMonitorTask.
         */
        public void run() {
            /* lookup file's lastModified date */
            long lastModified = this.monitoredFile.lastModified();
            
            
            /* if the lastModified > prev lastModified date, notify listener */
            if (lastModified != this.lastModified) {
                log.debug(String.format("File last modified %d and known last " +
                		"modified %d", lastModified, this.lastModified));
                this.lastModified = lastModified;
                fireFileChangeEvent(this.listener, this.fileName);
            }
        }
    }
}
