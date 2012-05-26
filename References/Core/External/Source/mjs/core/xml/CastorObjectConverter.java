/* Generated by Together */
package mjs.core.xml;

// Java imports
import java.io.StringReader;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import java.io.StringWriter;


/**
 * The object converter to use for Castor object
 * marshalling and unmarshalling. 
 * 
 * @author mshoemake
 */
public class CastorObjectConverter 
{
   /**
    * Converts xml string into objects, based on a mapping file.
    *
    * @param xmlString The xml string to convert into an object
    * @param castedClass The Class of the objject that the xml will be converted into.
    * @param mappingFileName The file name of the mapping file.
    */
   public static Object convertXMLToObject(String xmlString, Class castedClass, java.net.URL mappingURL)throws Exception
   {
      // The mapping file to use for the conversion.
      Mapping mapping = null;
      System.out.println("CastorObjectConverter  convertXMLToObject()  BEGIN");
      // The object to return.
      Object castedObject = null;
      // The StringReader used by the Unmarshaller.
      StringReader stringReader = null;

      // Load mapping file
      try
      {
         // Load the mapping file.
         mapping = new Mapping();

         if (mappingURL == null)
            System.out.println("CastorObjectConverter  convertXMLToObject()  Mapping URL = NULL.");

         mapping.loadMapping(mappingURL);
         System.out.println("CastorObjectConverter  convertXMLToObject()  Created mapping object.");

         // Unmarshall the object
         Unmarshaller unmarshaller = new Unmarshaller(castedClass);
         unmarshaller.setMapping(mapping);
         System.out.println("CastorObjectConverter  convertXMLToObject()  Created unmarshaller.");
         stringReader = new StringReader(xmlString);
         System.out.println("CastorObjectConverter  convertXMLToObject()  Created StringReader.");
         castedObject =  unmarshaller.unmarshal( stringReader );
         if (castedObject == null)
            System.out.println("CastorObjectConverter  convertXMLToObject()  XML Unmarshalled.  NULL");
         else
            System.out.println("CastorObjectConverter  convertXMLToObject()  XML Unmarshalled.  "+castedObject.toString());
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (stringReader != null)
            stringReader.close();
      }
      return castedObject;
   }

   /**
    * Converts objects into xml string based on a mapping file.  Returns the XML
    * string.
    *
    * @param object The object to use to generate the XML.
    * @param castedClass The Class of the objject that the xml will be converted into.
    * @param mappingFileName The file name of the mapping file.
    */
   public static String convertObjectToXML(Object object, Class castedClass, java.net.URL mappingURL)
   {
      // The mapping file to use for the conversion.
      Mapping mapping = null;
      System.out.println("CastorObjectConverter  convertObjectToXML()  BEGIN");

      // The StringReader used by the Unmarshaller.
      StringWriter stringWriter = new StringWriter();
      System.out.println("CastorObjectConverter  convertObjectToXML()  Created StringWriter.");

      try
      {
         // Load the mapping file.
         mapping = new Mapping();
         mapping.loadMapping(mappingURL);
         System.out.println("CastorObjectConverter  convertObjectToXML()  Loaded mapping.");

         // Create a File to marshal to
         Marshaller marshaller = new Marshaller(stringWriter);
         marshaller.setMapping(mapping);
         System.out.println("CastorObjectConverter  convertObjectToXML()  Created marshaller.");

         // Marshal the person object
         marshaller.marshal(object);
         System.out.println("CastorObjectConverter  convertObjectToXML()  Created marshaller.");
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
      return stringWriter.toString();
   }
}

