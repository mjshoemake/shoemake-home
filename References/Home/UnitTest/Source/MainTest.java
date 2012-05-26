/* File name:     MainTest.java
 * Package name:  
 * Project name:  Home - UnitTest
 * Date Created:  May 1, 2004
 * Created by:    mshoemake
 * Year:          2004
 */

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.core.model.LoggableObject;


/**
 * The main class for this Shoemake home automated 
 * unit tests.
 * 
 * @author mshoemake
 */
public class MainTest extends LoggableObject
{
   /**
    * The log manager for test results.  The main log manager
    * is for trace output.
    */
   LoggingManager resultsLog = new LoggingManager("C:\\LogFiles\\HomeUnitTest.log", "UnitTest", LoggingManager.PRIORITY_DEBUG);

   /**
    * Constructor.
    * @param logManager
    */
   public MainTest()
   {
      super(new LoggingManager("C:\\LogFiles\\HomeUnitTest.log", "UnitTest", LoggingManager.PRIORITY_DEBUG));
   }

   /**
    * Main executable method.
    * @param args
    */
   public static void main(String[] args)
   {
      
   }
   
   
}
