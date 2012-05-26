/* File name:     Constants.java
 * Package name:  mjs.home.struts.actions
 * Project name:  Home - Struts
 * Date Created:  Jun 24, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.struts;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.home.model.UserRequestManager;
import mjs.home.model.DatabaseUserRequestManager;
import mjs.home.model.TestUserRequestManager;

/**
 * Constants to be used in the Shoemake Home Struts
 * application.
 * 
 * @author mshoemake
 */
public class Constants
{
   /**
    * The database DSN for this application. 
    */
   private static String DATABASE_DSN = "jdbc:odbc:AccessTasks";
   
   /**
    * The path and file location of output log file.
    * <p>
    * NOTE:  This should eventually end up in an external 
    * configuration file.
    */
   private static final String LOG_FILE_PATH = "C:\\LogFiles\\ShoemakeHomeStruts.log";    


   // PUBLIC VARIABLES   
   
   /**
    * The logging manager for this servlet.
    */
   public static LoggingManager logManager = new LoggingManager(LOG_FILE_PATH, "HomeStrutsServlet", LoggingManager.PRIORITY_DEBUG);

   /**
    * The user request manager that retrieves, adds, and updates
    * data in the database.
    * <p>
    * This could be a SOAP client or it could retrieve the 
    * data locally.
    */
   //public static UserRequestManager userReqManager = new DatabaseUserRequestManager(DATABASE_DSN, Constants.logManager);
   public static UserRequestManager userReqManager = new TestUserRequestManager(DATABASE_DSN, Constants.logManager);


   // PUBLIC KEYS

   /**
     * The application scope attribute used for passing a HelloModel
     * bean from the Action class to the View component
     */
   public static final String RETURN_VALUE_KEY = "mjs.home.ReturnValue";
}
