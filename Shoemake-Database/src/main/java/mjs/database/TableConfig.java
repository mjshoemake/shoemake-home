package mjs.database;

/**
 * This is the configuration object for a database
 * table.
 * 
 * @author mshoemake
 */
public class TableConfig
{
   /**
    * The table name.
    */
   private String tableName = null;

   /**
    * The mapping file name.
    */
   private String mappingFileName = null;
   
   /**
    * The class name to use when generating 
    * the objects from the table data.
    */
   private String className = null;

   /**
    * Default constructor.
    */
   public TableConfig() {
   }
   
   /**
    * Constructor.
    */
   public TableConfig(String tableName, 
		              String mappingFileName,
		              String className)
   {
	   this.tableName = tableName;
	   this.mappingFileName = mappingFileName;
	   this.className = className;
   }
   
   /**
    * The table name.
    */
   public String getTableName()
   {
      return tableName;
   }

   /**
    * The table name.
    */
   public void setTableName(String value)
   {
      tableName = value;
   }

   /**
    * The full path and filename of the mapping file
    * for this table.
    */
   public String getMappingFileName()
   {
      return mappingFileName;
   }

   /**
    * The full path and filename of the mapping file
    * for this table.
    */
   public void setMappingFileName(String value)
   {
      mappingFileName = value;
   }

   /**
    * The class name to use when generating 
    * the objects from the table data.
    */
   public String getClassName()
   {
      return className;
   }

   /**
    * The class name to use when generating 
    * the objects from the table data.
    */
   public void setClassName(String value)
   {
      className = value;
   }

}
