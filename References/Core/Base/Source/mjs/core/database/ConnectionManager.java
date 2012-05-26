package com.accenture.core.model;

import java.sql.*;
import java.util.*;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * This class is a Singleton that provides access to one or many
 * connection pools defined in a Property file. A client gets
 * access to the single instance through the static getInstance()
 * method and can then check-out and check-in connections from a pool.
 * When the client shuts down it should call the release() method
 * to close all open connections and do other clean up.
 */
public class ConnectionManager 
{
   static private ConnectionManager instance;       // The single instance
   static private int clients;
   private Vector drivers = new Vector();
   private Hashtable pools = new Hashtable();
   private final int OFF = 0;
   private final int LIMITED = 1;
   private final int CREATE = 2;
   private final int ALL = 3;
   
   /**
    * The log4j logger to use when writing log
    * messages.  This is populated by extracting
    * the logger using the Logger category.  The
    * default Logger category is "Performance".  See
    * the public methods debug(), info(), etc.
    */
   protected Logger perfLog = Logger.getLogger("Performance");

   
/**
 * This inner class represents a connection pool. It creates new
 * connections on demand, up to a max number if specified.
 * It also makes sure a connection is still open before it is
 * returned to a client.
 */
class DBConnectionPool 
{
    private Vector inUseConnections = new Vector();
    private Vector freeConnections = new Vector();
    private int maxConn;
    private String name;
    private String password;
    private String URL;
    private String user;
    private String default_sql;
    private int level;

    /**
     * Creates new connection pool.
     *
     * @param name The pool name
     * @param URL The JDBC URL for the database
     * @param user The database user, or null
     * @param password The database user password, or null
     * @param maxConn The maximal number of connections, or 0
     *   for no limit
     */
    public DBConnectionPool(
        String name,
        String URL,
        String user,
        String password,
        String sql,
        int maxConn,
        int level) {
        this.name = name;
        this.URL = URL;
        this.user = user;
        this.password = password;
        this.maxConn = maxConn;
        this.level = level;
        this.default_sql = sql;
    }

    /**
     * Checks in a connection to the pool. Notify other Threads that
     * may be waiting for a connection.
     *
     * @param con The connection to check in
     */
    public synchronized void freeConnection(Connection con) {
       // Put the connection at the end of the Vector IF it is not 
       // already in the Vector.  This prevents problems if freeConnection()
       // is called multiple times.
       if (! freeConnections.contains(con))
           freeConnections.addElement(con);

       if (inUseConnections.contains(con))
          inUseConnections.remove(con);

        perfLog.info("CONNECTIONS:  Removing connection from In-Use list.  Free: "+freeConnections.size()+"  Max: "+maxConn+"  In Use Count: "+inUseConnections.size());
        notifyAll();
    }

    /**
     * Checks to see if the specified connection is in the in-use list.
     * @param con Connection
     */
    public synchronized boolean isConnectionInUse(Connection con) 
    {
       // Is this connection in the in-use list?
       return inUseConnections.contains(con);
    }

    /**
     * Get the next connection to be used.
     * @return Connection
     */
    private Connection nextConnection()
    {
       Connection con = null;
       while (freeConnections.size() > 0 && con == null)
       {
          // Pick the first Connection in the Vector
          // to get round-robin usage
          con = (Connection) freeConnections.firstElement();
          freeConnections.removeElementAt(0);

          try 
          {
             if (con.isClosed()) 
             {
                // Connection is already closed.  Keep looking.
                con = null;
             }
             else
             {
                // Test the connection to make sure it's working.
                Statement stmt = con.createStatement();
                if (stmt != null) 
                {
                   if (default_sql != null && default_sql.trim().length() > 0) 
                      stmt.execute(default_sql);

                   stmt.close();
                }   
             }
          } 
          catch (SQLException e) 
          {
             perfLog.info("CONNECTIONS:  Testing connection...  An error occurred.  Discarding connection...", e);
             // Unable to interact with the database using this connection.
             // Keep looking.
             con = null;
          }
       }
       
       if (con == null) 
       {
          // Still no connection after searching the entire freeConnection 
          // list.  If maxConn == 0, number of in-use connections is 
          // unlimited.  If # connections is unlimited or in-use is less
          // than the max, create a new connection.
          if (maxConn == 0 || inUseConnections.size() < maxConn) 
          {
             // Create a new connection.
             con = newConnection();
          }
          else if (inUseConnections.size() >= maxConn)
          {
             perfLog.info("CONNECTIONS:  No connections available!!  Free: "+freeConnections.size()+"  Max: "+maxConn+"  In Use Count: "+inUseConnections.size());
          }
       } 
       return con;
    }
    
    /**
     * Checks out a connection from the pool. If no free connection
     * is available, a new connection is created unless the max
     * number of connections has been reached. If a free connection
     * has been closed by the database, it's removed from the pool
     * and this method is called again recursively.
     */
    public synchronized Connection getConnection() {
        Connection con = null;
        
        // Remove closed connections from the in-use list.  This is a
        // cleanup activity to prevent connections that are no longer 
        // in use from getting stuck in the In Use list.
        for (int C=inUseConnections.size()-1; C >= 0; C--)
        {
           con = (Connection)inUseConnections.get(C);
           try
           {
              if (con.isClosed())
                 freeConnection(con);
           }   
           catch (SQLException e)
           {
              perfLog.error("CONNECTIONS:  Error checking to see if connection is closed.  Leaving connection alone incase it's still in use.", e);     
           }
        }

        con = nextConnection();
        if (con != null) 
            inUseConnections.add(con);

        perfLog.info("CONNECTIONS:  Free: "+freeConnections.size()+"  Max: "+maxConn+"  In Use Count: "+inUseConnections.size());
        return con;
    }

    /**
     * Checks out a connection from the pool. If no free connection
     * is available, a new connection is created unless the max
     * number of connections has been reached. If a free connection
     * has been closed by the database, it's removed from the pool
     * and this method is called again recursively.
     * <P>
     * If no connection is available and the max number has been 
     * reached, this method waits the specified time for one to be
     * checked in.
     *
     * @param timeout The timeout value in milliseconds
     */
    public synchronized Connection getConnection(long timeout)
        throws DataLayerException {
        long startTime = new Date().getTime();
        Connection con = null;
        
        try
        {
           while ((con = getConnection()) == null) 
           {
               try 
               {
                   wait(timeout);
               } 
               catch (InterruptedException e) 
               {
                  perfLog.error("Error trying to get connection.  Ignoring error.", e);
                  e.printStackTrace();
               }
               catch (java.lang.Exception ex)
               {
                  perfLog.error("Error trying to get connection.  Ignoring error.", ex);
                  ex.printStackTrace();
               }
               if ((new Date().getTime() - startTime) >= timeout) {
                   // Timeout has expired

                   log("Database Connection Unavailable: " + name);
                   //return null;
                   throw new DataLayerException("Database Connection Unavailable.");
               }
           }
        }
        catch (java.lang.Exception ex)
        {
           perfLog.error("Error trying to get connection.  Ignoring error.", ex);
        }
        return con;
    }

    /**
     * Closes all available connections.
     */
    public synchronized void release() {
        Enumeration allConnections = freeConnections.elements();
        while (allConnections.hasMoreElements()) {
            Connection con = (Connection) allConnections.nextElement();
            try {
                con.close();
                perfLog.info("CONNECTIONS:  Connection released.  Free: "+freeConnections.size()+"  Max: "+maxConn+"  In Use Count: "+inUseConnections.size());
            } catch (SQLException e) {
               perfLog.info("CONNECTIONS:  CANNOT RELEASE CONNECTION.  Free: "+freeConnections.size()+"  Max: "+maxConn+"  In Use Count: "+inUseConnections.size(), e);
            }
        }
        freeConnections.removeAllElements();
        perfLog.info("CONNECTIONS:  All available connections released.  Free: "+freeConnections.size()+"  Max: "+maxConn+"  In Use Count: "+inUseConnections.size());
    }

    /**
     * Creates a new connection, using a userid and password
     * if specified.
     */
    private Connection newConnection() {
        Connection con = null;
        try {
            if (user == null || user.trim().length() == 0) {
                nlog("Just the URL", ALL);
                con = DriverManager.getConnection(URL);
            } else {
                con = DriverManager.getConnection(URL, user, password);
            }
            perfLog.info("CONNECTIONS:  Connection created.  Free: "+freeConnections.size()+"  Max: "+maxConn+"  In Use Count: "+inUseConnections.size());
        } catch (SQLException e) {
           perfLog.info("CONNECTIONS:  CANNOT CREATE CONNECTION.  Free: "+freeConnections.size()+"  Max: "+maxConn+"  In Use Count: "+inUseConnections.size(), e);
           return null;
        }
        return con;
    }
    /**
     * Creates a new connection, using a userid and password
     * if specified.
     */
    private void nlog(String msg, int mLevel) {
        if (level >= mLevel) {
            log(msg);
        }

    }
}

   /**
    * A private constructor since this is a Singleton
    */
   private ConnectionManager(Properties props) {
      init(props);
   }
   /**
    * Returns the single instance, creating one if it's the
    * first time this method is called.
    *
    * @return PercConnectionManager The single instance.
    */
   /**
    * Creates instances of DBConnectionPool based on the properties.
    * A DBConnectionPool can be defined with the following properties:
    * <PRE>
    * &lt;poolname&gt;.url         The JDBC URL for the database
    * &lt;poolname&gt;.user        A database user (optional)
    * &lt;poolname&gt;.password    A database user password (if user specified)
    * &lt;poolname&gt;.maxconn     The maximal number of connections (optional)
    * </PRE>
    *
    * @param props The connection pool properties
    */
   private void createPools(Properties props) {
      Enumeration propNames = props.propertyNames();
      while (propNames.hasMoreElements()) {
         String name = (String) propNames.nextElement();
         if (name.endsWith(".url")) {
            String poolName = name.substring(0, name.lastIndexOf("."));
            String url = props.getProperty(poolName + ".url");
            log("URL = " + url);
            if (url == null) {
               log("No URL specified for " + poolName);
               continue;
            }
            String user = props.getProperty(poolName + ".user");
            String password = props.getProperty(poolName + ".password");
            String sql = props.getProperty(poolName + ".default_sql");
            String maxconn = props.getProperty(poolName + ".maxconn", "0");
            int max;
            try {
               max = Integer.valueOf(maxconn).intValue();
            }
            catch (NumberFormatException e) {
               log("Invalid maxconn value " + maxconn + " for " + poolName);
               max = 0;
            }
            String logLevel = props.getProperty(poolName + ".logLevel", "0");
            int level;
            try {
               level = Integer.valueOf(logLevel).intValue();
            }
            catch (NumberFormatException e) {
               level = OFF;
            }
            DBConnectionPool pool = 
               new DBConnectionPool(poolName, url, user, password, sql, max, level);
            pools.put(poolName, pool);
            
            log("Initialized Pool: " + poolName);
         }
      }
   }
   
   /**
    * Frees this connection from the specified pool.
    *
    * @param name The pool name as defined in the properties file
    * @param con The Connection
    */
   public void freeConnection(String name, Connection con) {
      DBConnectionPool pool = (DBConnectionPool) pools.get(name);
      if (pool != null) {
         pool.freeConnection(con);
      }
   }
   
   /**
    * Frees this connection in any pool where it happens to exist.
    *
    * @param name The pool name as defined in the properties file
    * @param con The Connection
    */
   public void freeConnection(Connection con) 
   {
      Enumeration enum = pools.elements();
      while (enum.hasMoreElements())
      {
         DBConnectionPool pool = (DBConnectionPool)enum.nextElement();
         if (pool != null) 
         {
            // If the connection is in the in-use list for this pool, 
            // free the connection.
            if (pool.isConnectionInUse(con))
               pool.freeConnection(con);
         }
      }
   }
   
   /**
    * Returns an open connection. If no one is available, and the max
    * number of connections has not been reached, a new connection is
    * created.
    *
    * @param name The pool name as defined in the properties file
    * @return Connection The connection or null
    */
   public Connection getConnection(String name) {
      DBConnectionPool pool = (DBConnectionPool) pools.get(name);
      if (pool != null) {
         return pool.getConnection();
      }
      return null;
   }
   /**
    * Returns an open connection. If no one is available, and the max
    * number of connections has not been reached, a new connection is
    * created. If the max number has been reached, waits until one
    * is available or the specified time has elapsed.
    *
    * @param name The pool name as defined in the properties file
    * @param time The number of milliseconds to wait

    * @return Connection The connection or null
    */
   public Connection getConnection(String name, long time) throws DataLayerException{
      DBConnectionPool pool = (DBConnectionPool) pools.get(name);
      if (pool != null) {
         return pool.getConnection(time);
      }
      return null;
   }
   
   static synchronized public ConnectionManager getInstance(Properties props) {
      if (instance == null) {
         instance = new ConnectionManager(props);
      }
      clients++;
      return instance;
   }

   /**
    * Returns the connection manager instance.  If the instance does not
    * exist, it will NOT create the new instance.  To do this, use the 
    * getInstance() method with the Properties input parameter.
    * @return ConnectionManager
    */
   static synchronized public ConnectionManager getInstance() 
   {
      return instance;
   }
   
   /**
    * Loads properties and initializes the instance with its values.
    */
   private void init(Properties props) {
      createPools(props);
      loadDrivers(props);
   }

   public static synchronized ConnectionManager regenerateConnectionManager(Properties props)
   {
      instance = new ConnectionManager(props);
      clients = 1;
      return instance;
   }
   
   /**
    * Loads and registers all JDBC drivers. This is done by the
    * DBConnectionManager, as opposed to the DBConnectionPool,
    * since many pools may share the same driver.
    *
    * @param props The connection pool properties
    */
   private void loadDrivers(Properties props) {
      String driverClasses = props.getProperty("driver");
      StringTokenizer st = new StringTokenizer(driverClasses);
      while (st.hasMoreElements()) {
         String driverClassName = st.nextToken().trim();
         try {
            Driver driver = (Driver) 
               Class.forName(driverClassName).newInstance();
            DriverManager.registerDriver(driver);
            drivers.addElement(driver);
            log("Registered JDBC driver " + driverClassName);
         }
         catch (Exception e) {
            perfLog.error("Can't register JDBC driver '" +
               driverClassName + "'.  Ignoring error.", e);
         }
      }
   }
   /**
    * Writes a message to the log file.
    */
   private void log(String msg) {
      perfLog.debug(msg);
   }
   /**
    * Writes a message with an Exception to the log file.
    */
   private void log(Throwable e, String msg) {
      perfLog.error(msg, e);
   }
   /**
    * Closes all open connections and deregisters all drivers.
    */
   public synchronized void release() {
      // Wait until called by the last client
      if (--clients != 0) {
         return;
      }
      
      Enumeration allPools = pools.elements();
      while (allPools.hasMoreElements()) {
         DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
         pool.release();
      }
      Enumeration allDrivers = drivers.elements();
      while (allDrivers.hasMoreElements()) {
         Driver driver = (Driver) allDrivers.nextElement();
         try {
            DriverManager.deregisterDriver(driver);
            log("Deregistered JDBC driver " + driver.getClass().getName());
         }
         catch (SQLException e) {
            perfLog.error("Can't deregister JDBC driver '" + driver.getClass().getName()+"'.  Ignoring error.", e);
         }
      }
   }
}
