package mjs.framework;

import org.junit.Before;
import org.junit.Test;
import java.sql.*;

import mjs.core.ServletStarterTest;
import mjs.database.DBConnectionPool;

public class DBConnectTest extends ServletStarterTest {

    @Before
    public void setUp() throws Exception {
//        setUpEnvironment();
    }

    /**
     * Test method.
     */
    @Test
    public void testConnect() {

        try {
        	String userName = "root";
            String password = "Ihcsppp1";
            String url = "jdbc:mysql://localhost/test";
            Class.forName ("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection (url, userName, password);
            System.out.println ("Database connection established.");

            conn.close();
        	System.out.println("Test complete.  Exiting.");

        } catch (Exception e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }
    
    /**
     * Test method.
     */
    @Test
    public void testConnectionPool() {

        try {
        	String userName = "root";
            String password = "Ihcsppp1";
            String url = "jdbc:mysql://localhost/test";
            String driverClass = "com.mysql.jdbc.Driver"; 
            DBConnectionPool pool = new DBConnectionPool(driverClass, url, userName, password, 5);

            Connection conn1 = pool.getConnection();
            String conn1Name = conn1.toString();
            System.out.println ("Database connection established #1:  " + conn1Name);
            
            Connection conn2 = pool.getConnection();
            String conn2Name = conn2.toString();
            System.out.println ("Database connection established #2:  " + conn2Name);
            
            Connection conn3 = pool.getConnection();
            String conn3Name = conn3.toString();
            System.out.println ("Database connection established #3:  " + conn3Name);
            
            Connection conn4 = pool.getConnection();
            String conn4Name = conn4.toString();
            System.out.println ("Database connection established #4:  " + conn4Name);
            
            Connection conn5 = pool.getConnection();
            String conn5Name = conn5.toString();
            System.out.println ("Database connection established #5:  " + conn5Name);
            
            try {
                Connection conn6 = pool.getConnection();
                String conn6Name = conn6.toString();
                System.out.println ("Ruh roh...  Database connection established #6:  " + conn6Name);
                assertFailed("Expected exception and didn't get it.");
            } catch (Exception e) {
                System.out.println("Expected exception received: " + e.getMessage()); 
            }
            
            pool.freeConnection(conn3);
            System.out.println ("Database connection #3 closed.");
            
            Connection conn7 = pool.getConnection();
            String conn7Name = conn7.toString();
            System.out.println ("Database connection established #7:  " + conn7Name);
            
            System.out.println("Connection #7 should be the same as connection #3.");
            System.out.println("   3: " + conn3Name);
            System.out.println("   7: " + conn7Name);
            assertTrue("Connection #7 should be the same as connection #3.", conn3Name.equals(conn7Name));

        	conn1.close();
        	conn2.close();
        	conn3.close();
        	conn4.close();
        	conn5.close();
        	System.out.println("All connections closed.");
        	System.out.println("Test complete.  Exiting.");
        } catch (Exception e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }

}
