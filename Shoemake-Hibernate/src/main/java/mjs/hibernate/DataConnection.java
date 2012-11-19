package mjs.hibernate;

import java.io.InputStream;
import mjs.utils.FileUtils;
import mjs.utils.XMLBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Document;

/**
 * The singleton connection to a Hibernate database.
 * @author mishoema
 */
public class DataConnection {

    /**
     * The Hibernate SessionFactory used to create new database
     * sessions.
     */
    SessionFactory sessionFactory = null;
    
    /**
     * The Singleton instance.
     */
    public static DataConnection instance = null;
    
    /**
     * Private constructor.
     */
    private DataConnection() {
    }
    
    /**
     * Retrieve the singleton DataConnection instance.
     * @return DataConnection
     */
    public synchronized static DataConnection getInstance() {
        if (instance == null) {
            instance = new DataConnection();
        } 
        return instance;
    }
    
    /**
     * Is the Hibernate database connection initialized? 
     * @return  boolean
     */
    public boolean isInitialized() {
        return sessionFactory != null;
    }

    /**
     * Initialize Hibernate data access using the specified
     * filename.
     * @param filename String
     * @throws Exception
     */
    public void initialize(String filename) throws Exception {
        InputStream stream = FileUtils.getFileAsStream(filename, true);
        Document xml = parseXml(stream);
        Configuration config = new Configuration().configure(xml);
        sessionFactory = config.buildSessionFactory();
    }
    
    /**
     * Retrieves the current SessionFactory object if the 
     * DataConnection has been initialized already.  If not,
     * it throws a HibernateException.
     * @return SessionFactory
     * @throws HibernateException
     */
    public static SessionFactory getSessionFactory() throws HibernateException {
        DataConnection conn = getInstance();
        if (conn.isInitialized()) {
            return conn.sessionFactory;
        } else {
            throw new HibernateException("SessionFactory requested but DataConnection has not been initialized yet. Call DataConnection.initialize() first.");
        }
    }

    /**
     * Validate the specified XML document against the specified XSD.
     * @param xml InputStream - The XML received from FPG.
     */
    private Document parseXml(InputStream xml) throws Exception {
        try {
            return XMLBuilder.parse(xml);
        } catch (Exception e) {
            throw e;
        }
    }
}