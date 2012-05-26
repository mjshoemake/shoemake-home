package mjs.xml;

// Java imports
import java.io.StringReader;
import org.apache.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import java.io.StringWriter;

import mjs.exceptions.CoreException;

/**
 * The object converter to use for Castor object marshalling and unmarshalling.
 */
public class CastorObjectConverter {

   /**
    * The log4j logger to use when writing log messages.
    */
   protected static Logger log = Logger.getLogger("XML");

   /**
    * Converts xml string into objects, based on a mapping file.
    * 
    * @param xmlString The xml string to convert into an object
    * @param castedClass The Class of the objject that the xml will be converted
    *           into.
    * @param mappingFileName The file name of the mapping file.
    */
   public static Object convertXMLToObject(String xmlString, Class castedClass, java.net.URL mappingURL) throws Exception {
      // The mapping file to use for the conversion.
      Mapping mapping = null;
      log.debug("CastorObjectConverter  convertXMLToObject()  BEGIN");
      // The object to return.
      Object castedObject = null;
      // The StringReader used by the Unmarshaller.
      StringReader stringReader = null;

      // Load mapping file
      try {
         // Load the mapping file.
         mapping = new Mapping();

         if (mappingURL == null)
            log.debug("CastorObjectConverter  convertXMLToObject()  Mapping URL = NULL.");

         mapping.loadMapping(mappingURL);
         log.debug("CastorObjectConverter  convertXMLToObject()  Created mapping object.");

         // Unmarshall the object
         Unmarshaller unmarshaller = new Unmarshaller(castedClass);
         unmarshaller.setMapping(mapping);
         log.debug("CastorObjectConverter  convertXMLToObject()  Created unmarshaller.");
         stringReader = new StringReader(xmlString);
         log.debug("CastorObjectConverter  convertXMLToObject()  Created StringReader.");
         castedObject = unmarshaller.unmarshal(stringReader);
         if (castedObject == null)
            log.debug("CastorObjectConverter  convertXMLToObject()  XML Unmarshalled.  NULL");
         else
            log.debug("CastorObjectConverter  convertXMLToObject()  XML Unmarshalled.  " + castedObject.toString());
      }
      catch (Exception e) {
         throw new CoreException("Error converting XML to object.", e);
      }
      finally {
         if (stringReader != null)
            stringReader.close();
      }
      return castedObject;
   }

   /**
    * Converts objects into xml string based on a mapping file. Returns the XML
    * string.
    * 
    * @param object The object to use to generate the XML.
    * @param castedClass The Class of the objject that the xml will be converted
    *           into.
    * @param mappingFileName The file name of the mapping file.
    */
   public static String convertObjectToXML(Object object, java.net.URL mappingURL) {
      // The mapping file to use for the conversion.
      Mapping mapping = null;
      log.debug("CastorObjectConverter  convertObjectToXML()  BEGIN");

      // The StringReader used by the Unmarshaller.
      StringWriter stringWriter = new StringWriter();
      log.debug("CastorObjectConverter  convertObjectToXML()  Created StringWriter.");

      try {
         if (mappingURL != null) {
            // Load the mapping file.
            mapping = new Mapping();
            mapping.loadMapping(mappingURL);
            log.debug("CastorObjectConverter  convertObjectToXML()  Loaded mapping.");
         }

         // Create a File to marshal to
         Marshaller marshaller = new Marshaller(stringWriter);
         if (mappingURL != null) {
            marshaller.setMapping(mapping);
         }
         log.debug("CastorObjectConverter  convertObjectToXML()  Created marshaller.");

         // Marshal the person object
         marshaller.marshal(object);
         log.debug("CastorObjectConverter  convertObjectToXML()  Created marshaller.");
      }
      catch (java.lang.Exception e) {
         e.printStackTrace();
      }
      return stringWriter.toString();
   }
}
