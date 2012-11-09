package mjs.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import mjs.aggregation.OrderedMap;
import mjs.exceptions.CoreException;

/**
 * This is a utility class which contains bean manipulation utility functions.
 */
@SuppressWarnings("rawtypes")
public class BeanUtils {

   /**
    * The log4j logger to use when writing Perc-specific log messages. The
    * percLogger traces messages to the "Core" category rather than the servlet
    * category. The percLogger should be used for information that is only
    * relevant when debugging Perc itself.
    */
   private static Logger log = Logger.getLogger("Core");

   /**
    * Uses introspection to get the list of properties for a particular class.
    * 
    * @param type The class.
    * @return The value of the PropertyDescriptors property.
    * @throws ModelException
    */
   public static PropertyDescriptor[] getPropertyDescriptors(Class type) throws CoreException {
      try {
         BeanInfo beanInfo = Introspector.getBeanInfo(type, type.getSuperclass());

         return beanInfo.getPropertyDescriptors();
      }
      catch (Exception e) {
         throw new CoreException("Unable to get PropertyDescriptor list for type " + type.getName() + ".", e);
      }
   }

   /**
    * Uses introspection to get the list of properties for a particular class.
    * This method returns only those properties that are also present in the
    * mapping Hashtable.
    * 
    * @param type The class.
    * @param mapping Description of Parameter
    * @return The value of the PropertyDescriptors property.
    * @throws ModelException
    */
   @SuppressWarnings("unchecked")
   public static PropertyDescriptor[] getPropertyDescriptors(Class type, OrderedMap mapping) throws CoreException {
      try {
         BeanInfo beanInfo = Introspector.getBeanInfo(type, type.getSuperclass());
         PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

         // Create a Vector with only the properties that have both
         // a read and write method.
         Vector newList = new Vector();

         for (int C = 0; C <= pds.length - 1; C++) {
            if (mapping.containsKey(pds[C].getName().toLowerCase()))
               newList.add(pds[C]);
            else
               log.debug(pds[C].getName() + " NOT FOUND in mapping file.");
         }

         // Create a new array with the contents of the Vector.
         pds = new PropertyDescriptor[newList.size()];
         for (int D = 0; D <= newList.size() - 1; D++) {
            pds[D] = (PropertyDescriptor) newList.get(D);
         }

         return pds;
      }
      catch (Exception e) {
         throw new CoreException("Unable to get PropertyDescriptor list for type " + type.getName() + ".", e);
      }
   }

   /**
    * Get the bean properties for this object using reflection.
    * 
    * @param bean Object
    * @return Map<String,String>
    * @throws CoreException
    */
   public static Map<String, String> getBeanProperties(Object bean) throws CoreException {

      HashMap<String, String> result = new HashMap<String, String>();
      if (bean == null) {
         return result;
      }
      try {
         Object[] args = new Object[0];
         String fieldName = null;
         PropertyDescriptor[] pds = getPropertyDescriptors(bean.getClass());

         if (pds == null || pds.length == 0) {
            return result;
         }

         if (bean instanceof Map) {
            Map map = (Map) bean;
            Iterator keys = map.keySet().iterator();
            // Check for empty Map.
            if (!keys.hasNext())
               return result;

            while (keys.hasNext()) {
               String key = keys.next().toString();
               Object val = map.get(key);
               if (val instanceof Integer || val instanceof Long || val instanceof Double || val instanceof Boolean || val instanceof BigDecimal || val instanceof BigInteger
                     || val instanceof java.util.Date || val instanceof Float || val instanceof String) {
                  result.put(key, val.toString());
               }
            }
         } else if (bean instanceof Document) {
            return result;
         } else if (bean instanceof Node) {
            return result;
         } else if (bean instanceof Collection) {
            return result;
         } else {
            // Loop through the properties of the bean.
            // log.debug(indent(level) +
            // "Extracting bean properties...   count=" + pds.length);
            for (int i = 0; i < pds.length; i++) {
               fieldName = pds[i].getName();

               try {
                  if (!(fieldName.equalsIgnoreCase("class") || fieldName.equalsIgnoreCase("parent") || fieldName.equalsIgnoreCase("declaringclass"))) {
                     // log.debug(indent(level) + "   " + fieldName);

                     // Get the getter method for this property.
                     Method method = pds[i].getReadMethod();

                     if (method != null) {
                        Object value = method.invoke(bean, args);

                        if (value == null)
                           value = "";

                        if (value instanceof Map) {
                           // Do nothing.
                        } else if (value instanceof Collection) {} else if (value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Boolean
                              || value instanceof BigDecimal || value instanceof BigInteger || value instanceof java.util.Date || value instanceof Float || value instanceof String) {
                           // Append the actual property value converted to a
                           // String.
                           result.put(fieldName, value.toString());
                        } else {}
                     } else {}
                  }
               }
               catch (Exception e) {
                  log.error("Error getting bean properties.  Field: " + fieldName, e);
               }
            }
         }

         return result;
      }
      catch (Exception e) {
         String className = null;
         if (bean != null)
            className = bean.getClass().getName();
         throw new CoreException("Error processing bean to log data: " + className + ".", e);
      }
   }

}
