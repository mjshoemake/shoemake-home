package mjs.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * This class represents a connection pool. It creates new connections
 * on demand, up to a max number if specified. It also makes sure a
 * connection is still open before it is returned to a client.
 */
public class DBConnectionPool
{
       /**
	    * The log4j logger to use when writing log messages. This is
	    * populated by extracting the logger using the Logger category.
	    * The default Logger category is "Action".
	    */
	   protected Logger log = Logger.getLogger("Model");

	  /** 
	   * The number of checked out connections.
	   */
   private int checkedOut;

   /**
    *  List of free connections.
    */
   private ArrayList<Connection> freeConnections = new ArrayList<Connection>();

   /**
    * The maximum # of connections in the pool.
    */
   private int maxConn = 0;

   /**
    * The database username.
    */
   private String user;

   /**
    * The database password.
    */
   private String password;

   /**
    * The database URL.
    */
   private String url;

   /**
    * Creates new connection pool.
    * 
    * @param url  The JDBC URL for the database
    * @param user  The database user, or null
    * @param password  The database user password, or null
    * @param maxConn  The maximal number of connections, or 0 for no limit
    */
   public DBConnectionPool(String driverClass,
 		                  String url, 
 		                  String user, 
 		                  String password, 
 		                  int maxConn) {
      this.url = url;
      this.user = user;
      this.password = password;
      this.maxConn = maxConn;
      loadDriver(driverClass);
      log.info("Database connection pool initialized.");
      log.info("   URL: " + url);
      log.info("   User: " + user);
      log.info("   Password: " + password);
      log.info("   MaxConn: " + maxConn);
      Connection conn = getNewConnection();
      freeConnection(conn);
   }

   /**
    * Loads and registers all JDBC drivers. This is done by the
    * BergstenConnectionPool, as opposed to the DBConnectionPool, since many
    * pools may share the same driver.
    * 
    * @param props  The connection pool properties
    */
   private void loadDriver(String driverClassName)
   {
      try
      {
         Driver driver = (Driver) Class.forName(driverClassName).newInstance();
         DriverManager.registerDriver(driver);
         log.debug("Registered JDBC driver " + driverClassName + ".");
      }
      catch (Exception e)
      {
         log.debug("Can't register JDBC driver: " + driverClassName + ", Exception: " + e);
      }
   }

   /**
    * Checks in a connection to the pool. Notify other Threads that may be
    * waiting for a connection.
    * 
    * @param con  The connection to check in
    */
   public synchronized void freeConnection(Connection con)
   {
      // Put the connection at the end of the Vector
      freeConnections.add(con);
      checkedOut--;
      log.info("freeConnection(); Freed connection.  # checked out: "+checkedOut+"  total connections: "+(freeConnections.size()+checkedOut));
      notifyAll();
   }

   /**
    * Checks out a connection from the pool. If no free connection is
    * available, a new connection is created unless the max number of
    * connections has been reached. If a free connection has been closed by
    * the database, it's removed from the pool and this method is called
    * again recursively.
    */
   public synchronized Connection getConnection() throws DataLayerException
   {
      Connection con = doGetConnection();
      
      if (con != null)
      {
         checkedOut++;
         log.info("Getting connection...  # checked out: "+checkedOut+"  total connections: "+(freeConnections.size()+checkedOut));
      } else {
     	 throw new DataLayerException("Failed to get new DB connection.  DB connection pool may be full.  # checked out: "+checkedOut+"  total connections: "+(freeConnections.size()+checkedOut));
      }
      return con;         
   }

   public synchronized Connection getNewConnection()
   {
      Connection con = null;
      if (freeConnections.size() > 0)
      {
         // Pick the first Connection in the Vector
         // to get round-robin usage
         con = (Connection) freeConnections.get(0);
         freeConnections.remove(0);
         con = newConnection();
      }
      else if (maxConn == 0 || checkedOut < maxConn)
      {
         con = newConnection();
         log.info("Created new connection.");
      }
      if (con != null)
      {
         checkedOut++;
         log.info("Getting new connection...  # checked out: "+checkedOut+"  total connections: "+(freeConnections.size()+checkedOut));
      }
      return con;
      
   }
   
   /**
    * Private utility method used by getConnection().  This is needed
    * because of the recursive nature of the code below.  This prevents
    * the checkedOut counter from being incremented with each recursive call.
    * 
    * @return Connection
    */
   private Connection doGetConnection()
   {
      
      Connection con = null;
      if (freeConnections.size() > 0)
      {
         // Pick the first Connection in the Vector
         // to get round-robin usage
         con = (Connection) freeConnections.get(0);
         freeConnections.remove(0);
         try
         {
            if (con.isClosed())
            {
               log.info("Removed bad connection.");
               // Try again recursively
               con = doGetConnection();
            }
         }
         catch (SQLException e)
         {
             log.info("Removed bad connection.");
            // Try again recursively
            con = doGetConnection();
         }
      }
      else if (maxConn == 0 || checkedOut < maxConn)
      {
         con = newConnection();
         log.info("Created new connection.");
      }
      return con;
   }

   /**
    * Checks out a connection from the pool. If no free connection is
    * available, a new connection is created unless the max number of
    * connections has been reached. If a free connection has been closed by
    * the database, it's removed from the pool and this method is called
    * again recursively.
    * <P>
    * If no connection is available and the max number has been reached, this
    * method waits the specified time for one to be checked in.
    * 
    * @param timeout  The timeout value in milliseconds
    */
   public synchronized Connection getConnection(long timeout) throws DataLayerException
   {
      long startTime = new Date().getTime();
      Connection con;
      while ((con = getConnection()) == null)
      {
         try
         {
            wait(timeout);
         }
         catch (InterruptedException e)
         {
         }
         if ((new Date().getTime() - startTime) >= timeout)
         {
            // Timeout has expired
            return null;
         }
      }
      return con;
   }

   /**
    * Closes all available connections.
    */
   public synchronized void release()
   {
      for (int i=freeConnections.size()-1; i >= 0; i--)
      {
         Connection con = (Connection)freeConnections.get(i);
         try
         {
            con.close();
            freeConnections.remove(con);
            log.debug("Closed connection for pool.");
         }
         catch (SQLException e)
         {
            freeConnections.remove(con);
            log.debug("Can't close connection for pool.", e);
         }
      }
   }

   /**
    * Creates a new connection, using a userid and password if specified.
    */
   private Connection newConnection()
   {
      Connection con = null;
      try
      {
         if (user == null)
         {
            log.info("Getting new database connection (NO user/password)...");
            con = DriverManager.getConnection(url);
            log.info("Getting new database connection (NO user/password)... DONE.");
         }
         else
         {
            log.info("Getting new database connection w/ user/password...");
            con = DriverManager.getConnection(url, user, password);
            log.info("Getting new database connection w/ user/password... DONE.");
         }
         log.debug("Created a new connection.");
      }
      catch (SQLException e)
      {
         log.debug("Can't create a new connection.", e);
         return null;
      }
      return con;
   }
}
