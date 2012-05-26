/* File name:     UserWebServiceConfiguration.java
 * Package name:  mjs.home.commands
 * Project name:  Home - WebService
 * Date Created:  Apr 14, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.commands.users;

/**
 * This class is the point of contact for configuration of 
 * the User Web Service.  This should eventually go to a 
 * configuration file of some sort to get the configuration
 * data.  For now, it's hard coded in the constructor.  
 * 
 * @author mshoemake
 */
public class UserWebServiceConfiguration
{
   /**
    * The DSN to the database for this web service.
    */
   private String databaseDSN = null;
   
   /**
    * Constructor.
    */
   public UserWebServiceConfiguration()
   {
      databaseDSN = "jdbc:odbc:AccessTasks";
   }
   
   /**
    * The DSN to the database for this web service.
    */
   public String getDatabaseDSN()
   {
      return databaseDSN;
   }
}
