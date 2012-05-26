
package com.accenture.core.model;

import java.sql.ResultSet;
import java.util.Vector;
import com.accenture.core.model.DataManager;
import com.accenture.core.utils.CoreException;
import com.accenture.core.utils.Loggable;
import org.apache.log4j.Logger;


/**
 * This class is used to select primary key values from an Oracle
 * sequence so that the primary key values are in a Vector ready for
 * the insert prepared statement. This is useful for improving
 * performance of a large number of inserts into the same table. This
 * can be done concurrently by multiple threads to speed up
 * performance.
 */
public class PrimaryKeyManager extends Loggable
{
   /**
    * The name of the Oracle table sequence to use when generating
    * primary keys.
    */
   private String sequenceName = null;

   /**
    * The number of primary key values to generate.
    */
   private int numKeysToRetrieve = 0;

   /**
    * The DatabaseDriver to use to talk to the database.
    */
   private DataManager manager = null;

   /**
    * The Vector to which the primary key values should be added.
    */
   private Vector keyList = null;

   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Core". See the public methods
    * debug(), info(), etc.
    */
   protected Logger log = Logger.getLogger("Core");

   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Performance". See the public
    * methods debug(), info(), etc.
    */
   protected Logger logPerf = Logger.getLogger("Performance");

   /**
    * The numeric thread ID for this thread. This is used when writing
    * to the trace log so we know which thread wrote the message.
    */
   private int threadID = 0;

   /**
    * The most recent exception thrown if one exists. Because this
    * will execute in a background thread, the calling method needs a
    * way to find out if an error has occurred inside the thread. This
    * method provides that.
    */
   private Exception error = null;

   /**
    * Constructor.
    *
    * @param sequenceName       Description of Parameter
    * @param numKeysToRetrieve  Description of Parameter
    * @param keyList            Description of Parameter
    * @param manager            Description of Parameter
    * @param threadID           Description of Parameter
    */
   public PrimaryKeyManager(String sequenceName, int numKeysToRetrieve, Vector keyList, DataManager manager, int threadID)
   {
      this.sequenceName = sequenceName;
      this.numKeysToRetrieve = numKeysToRetrieve;
      this.keyList = keyList;
      this.manager = manager;
      this.threadID = threadID;
   }

   /**
    * Generate the specified number of primary keys and store them in
    * the specified Vector. The values are specified in the
    * constructor.
    *
    * @exception CoreException  Description of Exception
    */
   public void generateKeys() throws CoreException
   {
      try
      {
         // If no sequence specified for the key field, this is also a problem.
         // Throw exception.
         if (sequenceName == null || sequenceName.trim().equals(""))
            throw new DataLayerException("Error getting next primary key value.  Sequence name is blank or null.");

         // Generate SQL statement.
         StringBuffer sql = new StringBuffer("select ");

         sql.append(sequenceName);
         sql.append(".nextval from dual");

         //manager.open();
         for (int C = 0; C <= numKeysToRetrieve - 1; C++)
         {
            // Get the next value in the primary key sequence.
            ResultSet resultSet = manager.executeSQL(sql.toString());

            resultSet.next();

            String primaryKeyValue = resultSet.getString(1);

            resultSet.getStatement().close();
            keyList.add(primaryKeyValue);
         }
         //manager.close();
      }
      catch (Exception e)
      {
         //manager.close();
         logPerf.error("Error retrieving primary keys.", e);
         error = e;
      }
   }

   /**
    * The most recent exception thrown if one exists. Because this
    * will execute in a background thread, the calling method needs a
    * way to find out if an error has occurred inside the thread. This
    * method provides that.
    *
    * @return   The value of the Exception property.
    */
   public Exception getException()
   {
      return error;
   }
}
