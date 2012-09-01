package mjs.users;

import java.sql.Connection;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import javax.naming.Context;
//import javax.naming.NameNotFoundException;
//import javax.naming.directory.Attribute;
//import javax.naming.directory.Attributes;
//import javax.naming.directory.DirContext;
//import javax.naming.directory.InitialDirContext;
//import mjs.database.AbstractDataManager;
import mjs.database.DataLayerException;
import mjs.database.DatabaseDriver;
import mjs.database.PaginatedList;
import mjs.database.TableDataManager;
//import mjs.database.fielddef.FieldDefinition;
//import mjs.database.fielddef.impl.FieldDefinitionImpl;
//import mjs.utils.FormatUtils;
import mjs.utils.Loggable;
//import mjs.utils.StringUtils;
//import com.accenture.core.view.SelectOption;



/**
 * The data manager class for Tax Account Codes.
 */
public class UserManager extends Loggable
{
   /**
    * The data manager instance to use to talk to the database.
    */
   protected TableDataManager manager = null;

   /**
    * The default mapping file.
    */
   protected String mappingFile = "mjs/users/UserMapping.xml";
   
   /**
    * The name of the database table.
    */
   protected String tableName = "users";

   /**
    * Constructor.
    *
    * @param newDriver
    * com.accenture.core.model.AbstractDatabaseDriver
    * @exception DataLayerException  Description of Exception
    */
   public UserManager(DatabaseDriver newDriver) throws DataLayerException
   {
      manager = new TableDataManager(newDriver,
                                     tableName,
                                     mappingFile,
                                     UserForm.class);
   }

   /**
    * Creates and opens a DatabaseTransaction to use to talk to the
    * database.
    *
    * @return                     Description of Return Value
    * @throws DataLayerException
    */
/*   
   public Connection open() throws DataLayerException
   {
      return manager.open();
   }
*/
   /**
    * Closes the transaction. If unsuccessful, the transaction
    * performs a rollback.
    *
    * @param successful           Description of Parameter
    * @throws DataLayerException
    */
/*   
   public void close(boolean successful) throws DataLayerException
   {
      manager.close(successful);
   }
*/
   /**
    * Closes the current transaction and releases the connection back
    * to the connection pool.
    *
    * @throws DataLayerException
    */
/*   
   public void close() throws DataLayerException
   {
      manager.close();
   }
*/
   /**
    * @param applicationPK        Description of Parameter
    * @return
    * @throws DataLayerException
    */
/*   
   public ArrayList getUserListAsSelectOptions(String applicationPK) throws DataLayerException
   {
      ArrayList listOfUserIds = null;

      try
      {
         StringBuffer sql = new StringBuffer("select ");

         sql.append("u.user_id, u.fname, u.mname, u.lname ");
         sql.append("from users u, user_permissions p ");
         sql.append("where p.user_id = u.user_id and p.application_pk = ");
         sql.append(applicationPK);
         sql.append(" order by u.lname, u.fname, u.mname");

         ResultSet resultSet = manager.executeSQL(sql.toString());

         if (resultSet != null)
         {

            listOfUserIds = new ArrayList();
            while (resultSet.next())
            {
               String userID = resultSet.getString(1);
               String fName = resultSet.getString(2);
               String mName = resultSet.getString(3);
               String lName = resultSet.getString(4);
               StringBuffer value = new StringBuffer(fName);

               if (mName != null && ! mName.equals(""))
               {
                  value.append(" ");
                  value.append(mName);
                  value.append(" ");
               }
               if (lName != null && ! lName.equals(""))
               {
                  value.append(" ");
                  value.append(lName);
               }
               listOfUserIds.add(new SelectOption(userID, value.toString()));
            }
         }

      }
      catch (Exception e)
      {
         throw new DataLayerException("Error while retrieving list of users." + e.getMessage());
      }
      return listOfUserIds;
   }
   //end of getUserList()
*/

   /**
    * Loads the list of objects from the database.
    *
    * @param pageSize             Description of Parameter
    * @param maxRows              Description of Parameter
    * @return                     The value of the UserList property.
    * @throws DataLayerException
    */
/*   
   public PaginatedList getUserList(int pageSize, int maxRows) throws DataLayerException
   {
      try
      {
         PaginatedList list = manager.loadList("", pageSize, maxRows, "");

         return list;
      }
      catch (DataLayerException ex)
      {
         throw new DataLayerException("Error loading the sample bean list.", ex);
      }
   }
*/
   /**
    * Update this user in the database.
    *
    * @param bean                 UserForm
    * @param applicationPK        String
    * @throws DataLayerException
    */
/*   
   public void updateUser(UserForm bean) throws DataLayerException
   {
      try
      {
         //int randomNum = (int)Math.random() * 100000;
         String table = "users";
         String whereClause = "where user_pk = " + bean.getUser_pk();
         log.debug("Calling Update Bean...");
         manager.updateBean(table, mappingFile, bean, whereClause);
      }
      catch (DataLayerException ex)
      {
         throw new DataLayerException("Error inserting the user " + bean.getFname() + " " + bean.getLname() + ".", ex);
      }
   }
*/   
   /**
    * Delete this user in the database.
    *
    * @param bean                 UserForm
    * @param applicationPK        String
    * @throws DataLayerException
    */
/*   
   public void deleteUser(UserForm bean) throws DataLayerException
   {
      try
      {
         //int randomNum = (int)Math.random() * 100000;
         String whereClause = "where user_pk = " + bean.getUser_pk();
         log.debug("Calling Delete Bean...");
         manager.delete(whereClause);
      }
      catch (DataLayerException ex)
      {
         throw new DataLayerException("Error inserting the user " + bean.getFname() + " " + bean.getLname() + ".", ex);
      }
   }
*/   
   /**
    * Delete this user in the database.
    *
    * @param bean                 UserForm
    * @param applicationPK        String
    * @throws DataLayerException
    */
/*
   public void deleteUser(int user_pk) throws DataLayerException
   {
      try
      {
         //int randomNum = (int)Math.random() * 100000;
         String whereClause = "where user_pk = " + user_pk;
         log.debug("Calling Delete Bean...");
         manager.delete(whereClause);
      }
      catch (DataLayerException ex)
      {
         throw new DataLayerException("Error inserting the user primary key " + user_pk + ".", ex);
      }
   }
*/   
   
   /**
    * Insert this user in the database.
    *
    * @param bean                 UserForm
    * @throws DataLayerException
    */
/*   
   public void insertUser(UserForm bean) throws DataLayerException
   {
      try
      {
         String table = "users";
         log.debug("Calling Insert Bean...");
         manager.insertBean(table, mappingFile, bean);
      }
      catch (DataLayerException ex)
      {
         throw new DataLayerException("Error inserting the user " + bean.getFname() + " " + bean.getLname() + ".", ex);
      }
   }
*/   
   /**
    * Returns the number of rows in the specified table that match the
    * specified where clause.
    *
    * @param table                String
    * @param whereClause          String
    * @return                     int
    * @throws DataLayerException
    */
/*   
   public int countRows(String table, String whereClause) throws DataLayerException
   {
      try
      {
         int results = manager.countRows(table, whereClause);

         manager.close(true);
         return results;
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (Exception e)
      {
         throw new DataLayerException("Error counting rows in table " + table + ".", e);
      }
   }
*/
   /**
    * Loads the specified user based on the UID.
    * @throws DataLayerException
    */
 /*   
   public void getUser(int user_pk, UserForm form)
          throws DataLayerException
   {
      try
      {
         if (form == null)
            throw new DataLayerException("Unable to get the user properties.  Form is null.");

         manager.loadBean(form, " where user_pk = " + user_pk);
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (Exception e)
      {
         throw new DataLayerException("Error loading the user for user ID " + user_pk + ".", e);
      }
   }
*/
   
   /**
    * Loads the specified user based on the UID.
    * @throws DataLayerException
    */
/*   
   public void getUserByUserName(UserForm form, String username)
          throws DataLayerException
   {
      try
      {
         if (form == null)
            throw new DataLayerException("Unable to get the user properties.  Form is null.");

         manager.loadBean(form, " where username = '" + username + "'");
      }
      catch (DataLayerException e)
      {
         throw e;
      }
      catch (Exception e)
      {
         throw new DataLayerException("Error loading the user for username " + username + ".", e);
      }
   }
*/   

   /**
    * Generate the SQL for the user search.
    *
    * @param form           UserSearchForm
    * @param applicationPK  Description of Parameter
    * @return               String
    */
/*   
   protected String generateSearchSQL(UserSearchForm form, String applicationPK)
   {
      StringBuffer sql = new StringBuffer();

      sql.append("select u.user_id, u.fname, u.mname, u.lname, u.phone, u.email, u.ipager, p.permission_level ");
      sql.append("from users u, user_permissions p ");
      sql.append("where upper(p.user_id) = upper(u.user_id) and upper(p.application_pk )= ");
      sql.append(applicationPK.toUpperCase());
      //sql.append(" and u.deleted=''N''");

      if (! form.getSearchUser_ID().trim().equals(""))
      {
         sql.append(" and upper(u.user_id) like '");
         sql.append(AbstractDataManager.quoteTicks(form.getSearchUser_ID().trim().toUpperCase().replace('*', '%')));
         sql.append("%'");
      }
      if (! form.getSearchFname().trim().equals(""))
      {
         sql.append(" and upper(u.fname) like '");
         sql.append(AbstractDataManager.quoteTicks(form.getSearchFname().trim().toUpperCase().replace('*', '%')));
         sql.append("%'");
      }
      if (! form.getSearchMname().trim().equals(""))
      {
         sql.append(" and upper(u.mname) like '");
         sql.append(AbstractDataManager.quoteTicks(form.getSearchMname().trim().toUpperCase().replace('*', '%')));
         sql.append("%'");
      }
      if (! form.getSearchLname().trim().equals(""))
      {
         sql.append(" and upper(u.lname) like '");
         sql.append(AbstractDataManager.quoteTicks(form.getSearchLname().trim().toUpperCase().replace('*', '%')));
         sql.append("%'");
      }
      if (! form.getSearchPhone().trim().equals(""))
      {
         sql.append(" and upper(u.phone) like '");
         sql.append(AbstractDataManager.quoteTicks(form.getSearchPhone().trim().toUpperCase().replace('*', '%')));
         sql.append("%'");
      }
      if (! form.getSearchEmail().trim().toUpperCase().equals(""))
      {
         sql.append(" and upper(u.email) like '");
         sql.append(AbstractDataManager.quoteTicks(form.getSearchEmail().trim().toUpperCase().replace('*', '%')));
         sql.append("%'");
      }
      if (! form.getSearchPermissionLevel().trim().equals("-1"))
      {
         sql.append(" and p.permission_level = ");
         sql.append(form.getSearchPermissionLevel().trim());
      }
      if (! form.getSearchIpager().trim().toUpperCase().equals(""))
      {
         sql.append(" and upper(u.ipager) like '");
         sql.append(AbstractDataManager.quoteTicks(form.getSearchIpager().trim().toUpperCase().replace('*', '%')));
         sql.append("%'");
      }
      sql.append(" order by u.lname, u.fname, u.mname");
      return sql.toString();
   }
*/
   
   /**
    * Get the mapping file location.
    *
    * @return   String
    */
/*   
   public String getMappingFile()
   {
      return mappingFile;
   }
*/
}
