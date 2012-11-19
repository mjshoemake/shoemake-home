package mjs.hibernate;

import java.io.InputStream;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import mjs.core.ServletStarterTest;
import mjs.hibernate.DataConnection;
import mjs.model.User;
import mjs.utils.LogUtils;
import mjs.utils.TransactionIdGen;

public class HibernateConfigTest extends ServletStarterTest {

    @Before
    public void setUp() throws Exception {
        startServer();
    }

    /**
     * Test method. Covers the following functions:
     * <pre>
     *   - Add row
     *   - Update row
     *   - Load row
     *   - Delete row
     * </pre>
     */
    @Test
    public void testInitialize() {
        try {
            // Configure Hibernate.
            Transaction txn = null;
            User user = null;
            User user2 = null;
            User user3 = null;
            
            String filename = "/hibernate.cfg.xml";
            DataConnection connection = DataConnection.getInstance();
            connection.initialize(filename);
            SessionFactory sessionFactory = DataConnection.getSessionFactory();
            
            // Add User #1
            Session session = sessionFactory.openSession();
            try {
                String guid = TransactionIdGen.nextVal("Test");
                txn = session.beginTransaction(); 
                user = new User();
                user.setFname(guid.substring(0, 19));
                user.setLname("MyTest");
                user.setLogin_enabled("N");
                user.setPassword("password");
                user.setUsername(guid.substring(0, 19));
                session.save(user);
                txn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (txn != null) {
                    txn.rollback();
                }
            } finally {
                session.close();
            }
            
            log.debug("Added user:");
            String[] lines = LogUtils.dataToStrings(user);
            for (String s : lines) {
                 log.debug("   "+ s);               
            }
            
            // Add User #2
            session = sessionFactory.openSession();
            try {
                String guid = TransactionIdGen.nextVal("Test");
                txn = session.beginTransaction(); 
                user2 = new User();
                user2.setFname(guid.substring(0, 19));
                user2.setLname("MyTest");
                user2.setLogin_enabled("N");
                user2.setPassword("password");
                user2.setUsername(guid.substring(0, 19));
                session.save(user2);
                txn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (txn != null) {
                    txn.rollback();
                }
            } finally {
                session.close();
            }
            
            // Add User #3
            session = sessionFactory.openSession();
            try {
                String guid = TransactionIdGen.nextVal("Test");
                txn = session.beginTransaction(); 
                user3 = new User();
                user3.setFname(guid.substring(0, 19));
                user3.setLname("MyTest");
                user3.setLogin_enabled("N");
                user3.setPassword("password");
                user3.setUsername(guid.substring(0, 19));
                session.save(user3);
                txn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (txn != null) {
                    txn.rollback();
                }
            } finally {
                session.close();
            }
            
            // Update User
            session = sessionFactory.openSession();
            try {
                txn = session.beginTransaction(); 
                user.setLogin_enabled("Y");
                session.update(user);
                txn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (txn != null) {
                    txn.rollback();
                }
            } finally {
                session.close();
            }
            
            // Load User
            session = sessionFactory.openSession();
            User retrieved = null;
            try {
                retrieved = (User)session.get(User.class, user.getUser_pk());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }

            log.debug("Loaded user:");
            lines = LogUtils.dataToStrings(retrieved);
            for (String s : lines) {
                 log.debug("   "+ s);               
            }
            
            // Filter #1
            session = sessionFactory.openSession();
            try {
                Criteria criteria = session.createCriteria(User.class);
                criteria.add(Restrictions.eq("lname", "MyTest"));
                List<User> results = criteria.list();
                log.debug("Filter results #1: count=" + results.size());
                log.debug("Filtered list:");
                lines = LogUtils.dataToStrings(results);
                for (String s : lines) {
                     log.debug("   "+ s);               
                }
                assertTrue("Should return 3 rows.", results.size() == 3);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
            
            // Filter #2
            session = sessionFactory.openSession();
            try {
                Criteria criteria = session.createCriteria(User.class);
                criteria.add(Restrictions.eq("lname", "MyTest"));
                criteria.add(Restrictions.eq("login_enabled", "N"));
                List<User> results = criteria.list();
                log.debug("Filter results #2: count=" + results.size());
                log.debug("Filtered list:");
                lines = LogUtils.dataToStrings(results);
                for (String s : lines) {
                     log.debug("   "+ s);               
                }
                assertTrue("Should return 2 rows.", results.size() == 2);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
            
            // Delete User
            session = sessionFactory.openSession();
            try {
                txn = session.beginTransaction(); 
                session.delete(user);
                session.delete(user2);
                session.delete(user3);
                txn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (txn != null) {
                    txn.rollback();
                }
            } finally {
                session.close();
            }

            
            
            
            System.out.println("Test complete.  Exiting.");
            assertTrue("Completed successfully.", true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }
    
}
