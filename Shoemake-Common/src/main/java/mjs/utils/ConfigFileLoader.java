/*******************************************************************************
 * $Id$
 *
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, 2011, Cisco Systems, Inc.
 *******************************************************************************/
package mjs.utils;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import org.apache.log4j.Logger;
import mjs.exceptions.ServerException;

/**
 * This is a utility class used to load configuration files.  This
 * class tries both the class loader and the file system to find the
 * requested file.  It looks in the file system first.  If it doesn't
 * find the file there, it looks in the class loader.
 */
@SuppressWarnings("rawtypes")
public class ConfigFileLoader {

    private static Logger log = Logger.getLogger("Core");
    
    /**
     * This method can be used to determine if a file is present or not.
     * 
     * @param filetype ConfigFileType
     * @param fileName String
     * @param readFilesFromClassLoader boolean
     * @return InputStream
     * @throws ServerException
     */
    public static boolean doesFileExist(ConfigFileLocation filetype, 
                                        String fileName) throws ServerException {
        boolean exists = false;
        String path = null;
        try {
            path = filetype.getFilePath(false);
            exists = FileUtils.doesFileExist(path + fileName, false);
            log.debug("Does file exist in file system?  (" + path + fileName + ")  exists=" + exists);
        } catch (Exception e) {
            exists = false;
        }
        if (exists) {
            return true;
        }
        try {
            path = filetype.getFilePath(true);
            exists = FileUtils.doesFileExist(path + fileName, true);
            log.debug("Does file exist in class loader?  (" + path + fileName + ")  exists=" + exists);
        } catch (Exception e) {
            exists = false;
        }
        return exists;
    }
    
    /**
     * The path where the specified file was found on disk.
     * 
     * @param filetype ConfigFileType
     * @param fileName String
     * @return String
     * @throws ServerException
     */
    public static String getPathFoundOnDisk(ConfigFileLocation filetype, 
                                            String fileName) throws ServerException {
        boolean exists = false;
        String path = null;
        try {
            path = filetype.getFilePath(false);
            exists = FileUtils.doesFileExist(path + fileName, false);
            if (exists) {
                return path;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Read this file into an InputStream and return the stream to the
     * caller.  If readFilesFromClassLoader is true, the filepath will be
     * used to do a lookup of the configuration file in the classpath using
     * the classloader. If not, it will look in the file system. 
     * 
     * @param filetype ConfigFileType
     * @param fileName String
     * @param readFilesFromClassLoader boolean
     * @return InputStream
     * @throws ServerException
     */
    public static InputStream getFileAsStream(ConfigFileLocation filetype, 
                                              String fileName) throws ServerException {
        InputStream stream = null;
        try {
            String path = filetype.getFilePath(false);
            stream = FileUtils.getFileAsStream(path + fileName, false);
        } catch (Exception e) {
            stream = null;
        }
        if (stream != null) {
            return stream;
        }
        try {
            String path = filetype.getFilePath(true);
            stream = FileUtils.getFileAsStream(path + fileName, true);
        } catch (Exception e) {
            stream = null;
        }
        if (stream != null) {
            return stream;
        }
        throw new ServerException("Unable to load file " + fileName + " at path " + filetype.name + ".  File not found.");          
    }
    
    /**
     * This method is utilized to read the contents of a file:
     * <code>fileName</code> in as a FileInputStream and load it into the
     * Properties parameter and returned.
     * 
     * @param fileName The path + fileName to read the contents from.
     * 
     * @return A Properties initialized with the contents of the fileName.
     *         Properties.isEmpty() = true if the fileName could not be found or
     *         it is empty.
     */
    public static Properties loadPropertiesFile(ConfigFileLocation location, String fileName) throws ServerException {
        String timestamp = StringUtils.dateToString(new Date(), "yyyyMMdd-HHmmss"); 
        return loadPropertiesFile(location, fileName, null, timestamp, false);
    }

    /**
     * This method is utilized to read the contents of a file:
     * <code>fileName</code> in as a FileInputStream and load it into the
     * Properties parameter and returned.
     * 
     * @param fileName The path + fileName to read the contents from.
     * @param enableEnvironmentVariableReplacement Replace ${name} with
     *        environment variable value.  Ex. ${RMS_CONF}/dcc.properties  
     * 
     * @return A Properties initialized with the contents of the fileName.
     *         Properties.isEmpty() = true if the fileName could not be found or
     *         it is empty.
     */
    public static Properties loadPropertiesFile(ConfigFileLocation filetype, 
                                                String fileName,
                                                String scriptName,
                                                String timestamp,
                                                boolean enableParameterSubstitution) throws ServerException {

        Properties classLoaderResult = null;
        try {
            String path = filetype.getFilePath(true);
            classLoaderResult = FileUtils.getContents(path + fileName, 
                                                      scriptName, 
                                                      timestamp, 
                                                      enableParameterSubstitution, 
                                                      true);
            if (classLoaderResult == null) {
                classLoaderResult = new Properties();
            }
        } catch (Exception e) {
            classLoaderResult = new Properties();
        }
        Properties fileSystemResult = null;
        try {
            String path = filetype.getFilePath(false);
            fileSystemResult = FileUtils.getContents(path + fileName, 
                                                     scriptName, 
                                                     timestamp, 
                                                     enableParameterSubstitution, 
                                                     false);
            if (fileSystemResult == null) {
                fileSystemResult = new Properties();
            }
        } catch (Exception e) {
            fileSystemResult = new Properties();
        }
        return mergeProperties(classLoaderResult, 
                               fileSystemResult);
    }
    
	/**
     * This method is utilized to read the contents of a file:
     * <code>fileName</code> in as a FileInputStream and load it into the
     * Properties parameter and returned.
     * 
     * @param fileName The path + fileName to read the contents from.
     * @param enableEnvironmentVariableReplacement Replace ${name} with
     *        environment variable value.  Ex. ${RMS_CONF}/dcc.properties  
     * 
     * @return A Properties initialized with the contents of the fileName.
     *         Properties.isEmpty() = true if the fileName could not be found or
     *         it is empty.
     */
    @SuppressWarnings("unused")
    private static Properties loadPropertiesFile(String fileName,
                                                 String scriptName,
                                                 String timestamp,
    		                                     boolean enableParameterSubstitution) throws ServerException {
        Properties classLoaderResult = null;
        try {
            classLoaderResult = FileUtils.getContents(fileName, scriptName, timestamp, enableParameterSubstitution, true);
            if (classLoaderResult == null) {
                classLoaderResult = new Properties();
            }
        } catch (Exception e) {
            classLoaderResult = new Properties();
        }
        Properties fileSystemResult = null;
        try {
            fileSystemResult = FileUtils.getContents(fileName, scriptName, timestamp, enableParameterSubstitution, false);
            if (fileSystemResult == null) {
                fileSystemResult = new Properties();
            }
        } catch (Exception e) {
            fileSystemResult = new Properties();
        }
        return mergeProperties(classLoaderResult, 
                               fileSystemResult);
    }
    
    /**
     * This method merges the configured properties into the default
     * properties object.  So, the configured properties take precedence where
     * duplicates exist.
     * 
     * @param defaultProperties
     * @param configuredProperties
     * @return Properties
     */
    private static Properties mergeProperties(Properties defaultProperties,
                                              Properties configuredProperties) {
        Properties result = defaultProperties;
        Iterator it = configuredProperties.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = configuredProperties.getProperty(key);
            result.setProperty(key, value);
        }
        return result;
    }
    
    
    /**
     * Read this file into an Java File object and return it to the
     * caller.  If readFilesFromClassLoader is true, the filepath will be
     * used to do a lookup of the configuration file in the classpath using
     * the classloader. If not, it will look in the file system. 
     * 
     * @param filetype ConfigFileType
     * @param fileName String
     * @return File
     * @throws ServerException
     */
/*    
    public static File getFile(ConfigFileLocation filetype, 
                               String fileName) throws ServerException {
        File file = null;
        try {
            String path = filetype.getFilePath(false);
            file = FileUtils.getFile(path + fileName, false);
        } catch (Exception e) {
            // If an exception is thrown, don't panic.  Ignore it and try
            // to load from classloader.
            file = null;
        }
        if (file != null) {
            return file;
        }
        try {
            String path = filetype.getFilePath(true);
            file = FileUtils.getFile(path + fileName, true);
        } catch (ServerException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerException(e.getMessage(), e);
        }
        if (file != null) {
            return file;
        }
        throw new ServerException("Unable to load file " + fileName + " at path " + filetype.name + ".  File not found.");          
    }
*/    
}
