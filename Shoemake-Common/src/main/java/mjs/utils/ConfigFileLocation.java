package mjs.utils;

/**
 * The configuration file type used by ConfigFileLoader to determine
 * the path to use to load the file.  This enum returns the filepath
 * to use for the file to be loaded.
 * 
 * NOTE: Had to make make this a class instead of an Enum so I could
 * instantiate dynamic instances.  Some of the directories may not
 * be known at design time.   
 * @author mishoema
 */
public class ConfigFileLocation {
    public final static ConfigFileLocation CONFIG = new ConfigFileLocation("/config/");
    public final static ConfigFileLocation MJS_PACKAGE = new ConfigFileLocation("/mjs/");
    public final static ConfigFileLocation NONE = new ConfigFileLocation("");

    /**
     * The name of this key.
     */
    public final String name;
    
    /**
     * Constructor.
     * @param name String
     * @param absolutePath boolean
     */
    private ConfigFileLocation(String name) {
        this.name = name;
    }
    
    /**
     * A String representation of this ConfigFileType.
     */
    public String getFilePath(boolean readFilesFromClassLoader) {
        return name;
    }
    
    /**
     * Creates a ConfigFileLocation object with the specified path.  If 
     * absolutePath is false, the specified path will be prepended by 
     * the value of the RMS_CONF environment variable.  Otherwise, it 
     * is use as specified.
     * 
     * @param path String
     * @param absolutePath boolean
     */
    public static ConfigFileLocation createLocation(String path) {
        return new ConfigFileLocation(path);
    }
}

