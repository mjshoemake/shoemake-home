/* File name:     XMLToObjectConverter.java
 * Package name:  mjs.home.controller.action
 * Project name:  Home - Controller
 * Date Created:  Apr 28, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.controller.action;

// Java imports
import java.lang.Class;

// MJS imports
import mjs.core.servlet.ServletConfig;
import mjs.core.servlet.ServletLoggableObject;
import mjs.core.xml.CastorObjectConverter;
import mjs.home.model.UserList;
import mjs.home.model.User;

/**
 * This takes an XML file and converts it into
 * an object, or converts objects into XML.  This
 * allows the actions to return XML data in the 
 * result to the controller.
 * 
 * @author mshoemake
 */
public class XMLToObjectConverter extends ServletLoggableObject
{
   /**
    * Constructor.
    */
   public XMLToObjectConverter(ServletConfig config)
   {
      super(config);
   }

   // ***************************************************
   // CONVERT OBJECTS TO XML.
   // ***************************************************

   /**
    * Convert a UserList object to XML.
    */
   public String convertUserListToXML(UserList list)
   {
      return convertObjectToXML(list,
                                "mjs.home.model.UserList",
                                "/mjs/home/model/UserListMapping.xml");   
   }


   // ***************************************************
   // CONVERT XML TO OBJECTS.
   // ***************************************************

   /**
    * Convert an XML String to a User object.
    */
   public User convertXMLToUser(String xml)
   {
      return (User)(convertXMLToObject(xml,
                                       "mjs.home.model.User",
                                       "/mjs/home/model/UserMapping.xml"));   
   }
   
   // ***************************************************
   // UTILITY METHODS.
   // ***************************************************

   /**
    * Convert an object to an XML String using Castor. 
    * 
    * @param obj
    * @param classPath
    * @param mappingFile
    */
   private String convertObjectToXML(Object obj, String classPath, String mappingFile)
   {
      try
      {
         // Create Class object.
         Class userClass = Class.forName(classPath);
         // Create URL for mapping file.
         java.net.URL url = getClass().getResource(mappingFile);
      
         return CastorObjectConverter.convertObjectToXML(obj, userClass, url);
      }   
      catch (java.lang.Exception e)
      {
         writeToLog(e);
         return null;
      }
   }

   /**
    * Convert an XML String to an object using Castor. 
    * 
    * @param xml
    * @param classPath
    * @param mappingFile
    */
   private Object convertXMLToObject(String xml, String classPath, String mappingFile)
   {
      try
      {
         // Create Class object.
         Class userClass = Class.forName(classPath);
         // Create URL for mapping file.
         java.net.URL url = getClass().getResource(mappingFile);
         //convert xml to Global Question Pool object
         return (User)(CastorObjectConverter.convertXMLToObject(xml, userClass, url));
      }
      catch (java.lang.Exception e)
      {
         writeToLog(e);
         return null;
      }
   }
   
}
