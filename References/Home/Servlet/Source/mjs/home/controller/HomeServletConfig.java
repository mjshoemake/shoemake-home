/* File name:     HomeServletConfig.java
 * Package name:  mjs.home.controller
 * Project name:  Home - Controller
 * Date Created:  Apr 28, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.controller;

// Java imports
import java.util.Locale;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.core.servlet.ServletConfig;
import mjs.core.servlet.ServletController;
import mjs.core.xml.resourceBundle.MessageResourceBundle;

/**
 * The cofiguration applet for the Home Servlet.
 * This class stores information to be used further in, 
 * such as the locale, application name, LoggingManager object,
 * etc.  
 * 
 * @author mshoemake
 */
public class HomeServletConfig extends ServletConfig
{
   /**
    * The DSN to the database for this web service.
    */
   private String databaseDSN = null;

   /**
    * @param appName
    * @param msgBundle
    * @param myLocale
    * @param logManager
    * @param controller
    * @param defaultState
    */
   public HomeServletConfig(String appName,
                            MessageResourceBundle msgBundle,
                            Locale myLocale,
                            LoggingManager logManager,
                            ServletController controller,
                            int defaultState, 
                            String databaseDSN)
   {
      super(appName, msgBundle, myLocale, logManager, controller, defaultState);
      this.databaseDSN = databaseDSN;
   }
   
   /**
    * The DSN to the database for this web service.
    */
   public String getDatabaseDSN()
   {
      return databaseDSN;
   }
   
   /**
    * The DSN to the database for this web service.
    */
   public void setDatabaseDSN(String value)
   {
      databaseDSN = value;
   }

}
