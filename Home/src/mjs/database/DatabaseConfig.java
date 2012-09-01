package mjs.database;

import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import mjs.exceptions.CoreException;
import mjs.setup.SetupServlet;
import mjs.utils.FileUtils;
import mjs.utils.SingletonInstanceManager;
import mjs.utils.StringUtils;
import mjs.xml.CastorObjectConverter;

/**
 * This is the configuration object for a database.
 * 
 * @author mshoemake
 */
@SuppressWarnings("rawtypes")
public class DatabaseConfig {
	
    /**
     * Logger.
     */
    protected final Logger log = Logger.getLogger("Model");
    
    /**
	 * The database driver class name.
	 */
	private String driver = null;

	/**
	 * The JDBC URL to use when connecting to the database.
	 */
	private String url = null;

	/**
	 * The username to use when connecting to the database.
	 */
	private String username = null;
	
	/**
	 * The password to use when connecting to the database.
	 */
	private String password = null;
	
	/**
	 * The max number of connections allowed to this database.
	 */
	private int maxConn = 20;

	/**
	 * The map of Table objects.  The key is the name of 
	 * the mapping xml file name.  
	 */
	Map<String, TableDataManager> tableMap = new Hashtable<String, TableDataManager>();
	
	/**
	 * The list of TableConfig objects.  This is set by the XML.
	 * Once that happens, the inner map is updated as well so that
	 * the Table objects can be referenced by xml name. 
	 */
	List<TableConfig> tableList = new Vector<TableConfig>();
	
    /**
     * Default constructor.	
     */
	public DatabaseConfig() {
	}
	
	/**
	 * Constructor.
	 */
	public DatabaseConfig(String driver, 
			              String url, 
			              String username,
	           		      String password, 
	           		      int maxConn) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.maxConn = maxConn;
	}

	/**
	 * The JDBC driver classpath.
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * The JDBC driver classpath.
	 */
	public void setDriver(String value) {
		driver = value;
	}

	/**
	 * The URL to the JDBC database.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * The URL to the JDBC database.
	 */
	public void setUrl(String value) {
		url = value;
	}

	/**
	 * The username to use when logging into the database.
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * The username to use when logging into the database.
	 */
	public void setUserName(String value) {
		username = value;
	}

	/**
	 * The password to use when logging into the database.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * The password to use when logging into the database.
	 */
	public void setPassword(String value) {
		password = value;
	}

	/**
	 * The password to use when logging into the database.
	 */
	public int getMaxConnections() {
		return maxConn;
	}

	/**
	 * The password to use when logging into the database.
	 */
	public void setMaxConnections(int value) {
		maxConn = value;
	}
	
	/**
	 * The list of TableConfig objects.  This is set by the XML.
	 * Once that happens, the inner map is updated as well so that
	 * the Table objects can be referenced by xml name. 
	 */
	public List<TableConfig> getTableConfigs() {
		return tableList;
	}
	
	/**
	 * The list of TableConfig objects.  This is set by the XML.
	 * Once that happens, the inner map is updated as well so that
	 * the Table objects can be referenced by xml name. 
	 */
	public  void setTableConfigs(List<TableConfig> tables) throws CoreException, ClassNotFoundException {
		this.tableList = tables;
	}
	
	/**
	 * Initialize the database based on the specified configFileName.
	 * Example: "/config/database.xml", where this file is found in
	 * the classpath (not in the file system itself). 
	 * 
	 * @param configFileName String
	 * @throws Exception
	 */
	public static void initialize(String configFileName) throws Exception {
	    Logger log = Logger.getLogger("Model");
	    log.debug("Initializing DatabaseConfig...");
        SingletonInstanceManager imgr = SingletonInstanceManager.getInstance();
        InputStream stream = DatabaseConfig.class.getResourceAsStream(configFileName);

        if (stream == null)
            throw new CoreException("Error occured loading database.xml. InputStream is null. Filename=" + configFileName);
        
        String xml = StringUtils.inputStreamToString(stream);          
    	
        URL mappingURL = SetupServlet.class.getResource("/mjs/database/DatabaseMapping.xml");
	    DatabaseConfig dbConfig = (DatabaseConfig)CastorObjectConverter.convertXMLToObject(xml, 
	                                                                                       DatabaseConfig.class, 
	                                                                                       mappingURL);
	    if (dbConfig != null) {
            imgr.putInstanceForKey(DatabaseConfig.class.getName(), dbConfig);
            dbConfig.setup();
	    }    
		
	    
	}
	
	private void setup() throws CoreException, ClassNotFoundException {
		
        SingletonInstanceManager imgr = SingletonInstanceManager.getInstance();
        DatabaseDriver dbDriver = new DatabaseDriver(driver, url, username, password, maxConn); 
        imgr.putInstanceForKey(DatabaseDriver.class.getName(), dbDriver);

        if (driver == null)
           throw new CoreException("Unable to create database managers.  Driver is null.");

        
        for (TableConfig config : tableList) {
			String tableName = config.getTableName();
			String mappingFileName = config.getMappingFileName();
			String key = FileUtils.stripFilePath(mappingFileName);
			String className = config.getClassName();
			Class myClass = Class.forName(className);
			
	        log.debug("Adding TableDataManager for " + tableName + ", mapping=" + mappingFileName + "...");
			TableDataManager mgr = new TableDataManager(dbDriver,
					                                    tableName, 
					                                    mappingFileName, 
					                                    myClass);
            log.debug("Adding to map: key=" + key + ", obj=" + mgr.toString());
			tableMap.put(key, mgr);
		}
	}

	/**
	 * Return the Table manager object associated with the specified 
	 * mapping file.
	 * @return Table
	 */
	public TableDataManager getTable(String mappingFileName) throws CoreException, ClassNotFoundException {
		return tableMap.get(FileUtils.stripFilePath(mappingFileName));
	}
	
	/**
	 * The number of configured tables in the database.
	 * @return int
	 */
	public int getTableCount() {
		return tableMap.size();
	}
	
	
}