package mjs.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;
import mjs.core.utils.CoreException;


/**
 * This is a utility class which contains bean manipulation utility
 * functions.
 */
public class BeanUtils
{
   /**
    * The log4j logger to use when writing Perc-specific log messages.
    * The percLogger traces messages to the "Core" category rather
    * than the servlet category. The percLogger should be used for
    * information that is only relevant when debugging Perc itself.
    */
   private static Logger log = Logger.getLogger("Core");

   /**
    * Uses introspection to get the list of properties for a
    * particular class.
    *
    * @param type                 The class.
    * @return                     The value of the PropertyDescriptors
    * property.
    * @throws DataLayerException
    */
   public static PropertyDescriptor[] getPropertyDescriptors(Class type) throws CoreException
   {
      try
      {
         BeanInfo beanInfo = Introspector.getBeanInfo(type, type.getSuperclass());

         return beanInfo.getPropertyDescriptors();
      }
      catch (Exception e)
      {
         throw new CoreException("Unable to get PropertyDescriptor list for type " + type.getName() + ".", e);
      }
   }

   /**
    * Uses introspection to get the list of properties for a
    * particular class. This method returns only those properties that
    * are also present in the mapping Hashtable.
    *
    * @param type                 The class.
    * @param mapping              Description of Parameter
    * @return                     The value of the PropertyDescriptors
    * property.
    * @throws DataLayerException
    */
   public static PropertyDescriptor[] getPropertyDescriptors(Class type, Hashtable mapping) throws CoreException
   {
      try
      {
         BeanInfo beanInfo = Introspector.getBeanInfo(type, type.getSuperclass());
         PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

         // Create a Vector with only the properties that have both
         // a read and write method.
         Vector newList = new Vector();

         for (int C = 0; C <= pds.length - 1; C++)
         {
            if (mapping.containsKey(pds[C].getName().toLowerCase()))
               newList.add(pds[C]);
            else
               log.debug(pds[C].getName() + " NOT FOUND in mapping file.");
         }

         // Create a new array with the contents of the Vector.
         pds = new PropertyDescriptor[newList.size()];
         log.debug("Property Descriptors for " + type.getName() + ":  size = " + pds.length);
         for (int D = 0; D <= newList.size() - 1; D++)
         {
            pds[D] = (PropertyDescriptor)newList.get(D);
            log.debug("   " + D + ": " + pds[D].getName());
         }

         return pds;
      }
      catch (Exception e)
      {
         throw new CoreException("Unable to get PropertyDescriptor list for type " + type.getName() + ".", e);
      }
   }

}
