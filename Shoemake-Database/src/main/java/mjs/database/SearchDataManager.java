package mjs.database;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;
import mjs.database.DataLayerException;
import mjs.database.DatabaseDriver;
import mjs.utils.Loggable;
import mjs.utils.SingletonInstanceManager;
import mjs.utils.StringUtils;

/**
 * The data manager class for Tax Account Codes.
 */
public class SearchDataManager extends Loggable {

   /**
    * The data manager instance to use to talk to the database.
    */
   protected DataManager manager = null;

   /**
    * The log4j logger to use when writing log messages to the "Core"
    * category.
    */
   protected Logger logResultSet = Logger.getLogger("ResultSet");

   /**
    * The default mapping file.
    */
   //protected String mappingFile = "mjs/recipes/RecipeMapping.xml";

   /**
    * The table name for this data manager.
    */
   //protected String table = "recipes";

   
   /**
    * Constructor.
    * 
    * @param newDriver com.accenture.core.model.AbstractDatabaseDriver
    * @exception DataLayerException Description of Exception
    */
   public SearchDataManager() throws DataLayerException {
       SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
       DatabaseDriver driver = (DatabaseDriver) mgr.getInstance("mjs.database.DatabaseDriver");
       manager = new DataManager(driver);
   }

   /**
    * Creates and opens a DatabaseTransaction to use to talk to the database.
    * 
    * @return Description of Return Value
    * @throws DataLayerException
    */
   public Connection open() throws DataLayerException {
      return manager.open();
   }

   /**
    * Closes the transaction. If unsuccessful, the transaction performs a
    * rollback.
    * 
    * @param successful Description of Parameter
    * @throws DataLayerException
    */
   public void close(boolean successful) throws DataLayerException {
      manager.close(successful);
   }

   /**
    * Closes the current transaction and releases the connection back to the
    * connection pool.
    * 
    * @throws DataLayerException
    */
   public void close() throws DataLayerException {
      manager.close();
   }

   /**
    * Returns a the input Map with leading "@" signs removed from the names
    * of the keys.  Ex. The key "@name" becomes "name". 
    * @param map HashMap<String,String>
    * @return HashMap<String,String>
    */
   protected HashMap<String,String> removeAtSignFromKeys(HashMap<String,String> map) {
	   HashMap<String,String> result = new HashMap<String,String>();
	   
	   Iterator<String> iterator = map.keySet().iterator();
	   while (iterator.hasNext()) {
		   String key = iterator.next();
		   if (key.startsWith("@")) {
			   result.put(key.substring(1), map.get(key));
		   } else {
			   result.put(key, map.get(key));
		   }
	   }
	   
	   return result;
   }
   
   
   protected void likeFilter(String name, String value, StringBuilder sql) {
      if (value != null && value.length() > 0) {
         String newValue = StringUtils.replaceAll(value, '*', '%');
         log.info(name + " filter: " + value + "  -> " + newValue);
         if (newValue.length() > 0 && (! newValue.contains("%"))) {
            newValue += "%";
         }
         sql.append("  and " + name + " LIKE \"");
         sql.append(newValue);
         sql.append("\" ");
      }
   }
   
   protected void intFilter(String name, String value, StringBuilder sql) {
      int numberPart = -1;
      String operator = null;
      if (value != null && value.length() > 0) {
         // Find the start of the int value.
         int i=0; 
         char ch = value.charAt(i);
         while (i <= value.length()-1 && (! Character.isDigit(ch))) {
            i++;
            ch = value.charAt(i);
         }
         log.info("IntFilter: " + value + "  Numeric found at pos: " + i);
         if (i <= value.length()-1) {
            // Found.
            numberPart = Integer.parseInt(value.substring(i));
            if (i > 0) {
                operator = value.substring(0,i).trim();
            } else {
               operator = "=";
            }
            log.info("IntFilter: " + value + " -> num=" + numberPart + " operator=" + operator);
         }

         if (operator.equals("<") ||
             operator.equals(">") ||
             operator.equals("=") ||
             operator.equals("<=") ||
             operator.equals(">=") ||
             operator.equals("<>")) {
            sql.append("  and ");
            sql.append(name);
            sql.append(" ");
            sql.append(operator);
            sql.append(" \"");
            sql.append(numberPart);
            sql.append("\" ");
         }
      }
   }
   

   /**
    * Get the mapping file location.
    * 
    * @return String
    */
   /*
   public String getMappingFile() {
      return mappingFile;
   }
*/
}
