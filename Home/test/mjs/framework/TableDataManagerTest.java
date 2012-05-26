package mjs.framework;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import mjs.database.DatabaseDriver;
import mjs.database.PaginatedList;
import mjs.users.UserForm;
import mjs.users.UserManager;
import mjs.utils.LogUtils;
import mjs.utils.SingletonInstanceManager;

@SuppressWarnings("rawtypes")
public class TableDataManagerTest extends AbstractServletTest {

    @Before
    public void setUp() throws Exception {
    	// Start server.
    	startServer();
    }

    /**
     * Test method.
     */
    @Test
    public void testLoadUsers() {

        try {
            SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
            DatabaseDriver driver = (DatabaseDriver)mgr.getInstance(DatabaseDriver.class.getName());

            // Get user counts.
            UserManager dbMgr = new UserManager(driver);
            dbMgr.open();
            int rows = dbMgr.countRows("users", "");
            dbMgr.close();
            System.out.println("Count rows = " + rows);
            
            // Get user list.
            dbMgr.open();
            PaginatedList userList = dbMgr.getUserList(50, 500);
            dbMgr.close();
            ArrayList pageList = userList.getItemsOnCurrentPage();
            for (int i=0; i <= pageList.size()-1; i++) {
                Object next = pageList.get(i);
                String[] lines = LogUtils.dataToStrings(next);
                System.out.println("getUserList() result: User #" + i);
                for (int j=0; j <= lines.length-1; j++) {
                	System.out.println("   " + lines[j]);
                }
            }

            int pk = -1;
            UserForm target = null;
            if (rows > 0) {

                // Get specific user.
            	Object obj = pageList.get(0);
                UserForm user = (UserForm)obj;
                target = new UserForm();
                dbMgr.open();
                pk = Integer.parseInt(user.getUser_pk());
                System.out.println("Loading user (pk=" + pk + ").");
                dbMgr.getUser(pk, target);
                dbMgr.close();

                String[] lines = LogUtils.dataToStrings(target);
                System.out.println("getUser() result:");
                for (int j=0; j <= lines.length-1; j++) {
                	System.out.println("   " + lines[j]);
                }

                // Update user.
                if (target.getPassword().equals("1234")) {
                	target.setPassword("password");
                } else if (target.getPassword().equals("password")) {
                	target.setPassword("1234");
                }
               
                try {
                    dbMgr.open();
                    dbMgr.updateUser(target);
                    dbMgr.close(true);
                } catch (Exception e) {
                    dbMgr.close(false);
                    throw e;
                }
                System.out.println("Test complete.  Exiting.");

                // Verify update.
                UserForm check = new UserForm();
                if (rows > 0) {
                    System.out.println("Loading user (pk=" + pk + ").");
                    dbMgr.open();
                    dbMgr.getUser(pk, check);
                    dbMgr.close();

                    lines = LogUtils.dataToStrings(target);
                    System.out.println("getUser() result:");
                    for (int j=0; j <= lines.length-1; j++) {
                    	System.out.println("   " + lines[j]);
                    }
                } 
                
                assertTrue("Password updated correctly.", check.getPassword().equals(target.getPassword()));
            } 

            // Insert new user.
            target = new UserForm();
            target.setFname("Tester");
            target.setLname("Tester");
            target.setUsername("testerUT1");
            target.setPassword("password");
            try {
                dbMgr.open();
                dbMgr.insertUser(target);
                dbMgr.close(true);
            } catch (Exception e) {
                dbMgr.close(false);
                throw e;
            }

            // Verify insert (get user list).
            int insertedPk = -1;
            dbMgr.open();
            userList = dbMgr.getUserList(50, 500);
            dbMgr.close();
            UserForm insertedUser = null;
            pageList = userList.getItemsOnCurrentPage();
            for (int i=0; i <= pageList.size()-1; i++) {
                Object next = pageList.get(i);
                insertedUser = (UserForm)next;
                if (insertedUser.getUsername().equals("testerUT1")) {
                	insertedPk = Integer.parseInt(insertedUser.getUser_pk());
                }	
            }
            System.out.println("Inserted pk: " + insertedPk);

            // Get user by user ID.
            dbMgr.open();
            System.out.println("Loading user (username=" + insertedUser.getUsername() + ").");
            dbMgr.getUserByUserName(target, insertedUser.getUsername());
            dbMgr.close();
            
            assertTrue("Correct user found by username.", insertedUser.getUsername().equals(target.getUsername()));
            
            // Delete the user.
            try {
                dbMgr.open();
                dbMgr.deleteUser(insertedPk);
                dbMgr.close(true);
            } catch (Exception e) {
                dbMgr.close(false);
                throw e;
            }
            
            System.out.println("Test complete.  Exiting.");
            
        } catch (Exception e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }
    
}
