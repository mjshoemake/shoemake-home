package mjs.core.database.fielddef;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import mjs.core.database.fielddef.impl.FieldDefinitionImpl;
import mjs.core.database.fielddef.impl.FieldDefinitionListImpl;
import mjs.core.utils.CoreException;
import mjs.core.utils.Loggable;


/**
 * This takes an XML file and converts it into an object, or converts
 * objects into XML. This allows the actions to return XML data in the
 * result to the controller.
 *
 * @author   mshoemake
 */
public class FieldDefMappingLoader extends Loggable
{
   /**
    * Constructor.
    *
    */
   public FieldDefMappingLoader()
   {
      super();
   }

   /**
    * Loads field definition mapping file and returns it as a
    * hashtable where the field name is the hashtable key and the
    * field type is the lookup value. The field type should be one of
    * the following values and is case insensitive: <pre>
    *    key
    *    string
    *    int
    *    long
    *    float
    *    date
    *    boolean
    * </pre>
    *
    * @param mappingFile        Description of Parameter
    * @return                   Description of Return Value
    * @exception CoreException  Description of Exception
    */
   public Hashtable loadMapping(String mappingFile) throws CoreException
   {
      String packageName = "com.accenture.core.model.fielddef";

      if (mappingFile == null)
         throw new CoreException("Error occured loading mapping file.  MappingFile is null.");

      try
      {
         InputStream stream = this.getClass().getClassLoader().getResourceAsStream(mappingFile);

         if (stream == null)
            throw new CoreException("Error occured loading mapping file.  InputStream is null.");

         // Create context.
         JAXBContext jaxbContext = JAXBContext.newInstance(packageName);

         // Create unmarshaller.
         Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

         // Convert xml to object.
         FieldDefinitionListImpl result = (FieldDefinitionListImpl)unmarshaller.unmarshal(stream);

         return populateHashtable(result);
      }
      catch (java.lang.Exception e)
      {
         throw new CoreException("Error loading database table field type definitions.", e);
      }
   }

   /**
    * Populate a Hashtable with the contents of the
    * FieldDefinitionList so the data layer can easily look up the
    * mapping information for a field.
    *
    * @param result  FieldDefinitionListImpl
    * @return        Hashtable
    */
   private Hashtable populateHashtable(FieldDefinitionListImpl result)
   {
      Hashtable hashtable = new Hashtable();

      log.debug("Results:");

      List list = result.getField();

      for (int C = 0; C <= list.size() - 1; C++)
      {
         FieldDefinitionImpl def = (FieldDefinitionImpl)(list.get(C));
         String fieldName = def.getName().toLowerCase();

         hashtable.put(fieldName, def);
         log.debug("   name = " + fieldName + ",  type=" + def.getType() + ", format=" + def.getFormat() + ", maxLen=" + def.getMaxlen() + ", isPercent=" + def.isIspercent() + ", sequence=" + def.getSequence());
      }
      return hashtable;
   }

}
