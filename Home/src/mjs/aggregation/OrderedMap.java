package mjs.aggregation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import org.apache.log4j.Logger;


/**
 * A combined Map/ArrayList implementation so that if functions both as 
 * a List and a Map.  In essence, you can access the data either way from 
 * one object.  This class actually extends HashMap but maintains order
 * by adding items to an inner ArrayList.  When you do put(), it automatically
 * adds the item to the ArrayList to maintain order.  You can then reference
 * the items by position as desired.
 *
 * @author   mshoemake
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class OrderedMap extends HashMap
{
   static final long serialVersionUID = 9020182288989758191L;

   /**
    * The list contained in this OrderedMap.
    */
   private ArrayList list = new ArrayList();
   
   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Model".
    */
   protected Logger log = Logger.getLogger("Core");

   /**
    * Constructor.
    *
    * @param type           Description of Parameter
    * @param newPageLength  Description of Parameter
    * @param newMaxRecords  Description of Parameter
    * @param globalForward  The global forward to use for to get back to the current JSP page.
    *                       This is used by the pagination actions.
    */
   public OrderedMap()
   {
   }

   /**
    * Remove the specified key from this Map.  This method
    * also removes the item from the inner ArrayList.
    */
   public Object remove(Object key) {
      Object value = super.get(key);
      list.remove(value);
      return super.remove(key);
   }
   
   /**
    * Add the specified key/value pair to this Map.
    * This method also adds the item to the inner
    * ArrayList.
    */
   public Object put(Object key, Object value) {
      Object result = super.put(key, value);
      list.add(value);
      return result;
   }
   
   /**
    * Allows indexed access to items list the HashMap.
    * @param index int
    * @return Object
    */
   public Object get(int index) {
      return list.get(index);
   }

   /**
    * This method puts all items from the specified
    * Map into this Map.  It also syncs up the inner
    * ArrayList.  NOTE:  Order is random because a Map
    * (the input) has no implied order.
    */
   public void putAll(Map map) {
      super.putAll(map);
      Iterator iterator = map.values().iterator();
      while (iterator.hasNext()) {
         Object next = iterator.next();
         list.add(next);
      }
   }
   
   /**
    * Get the iterator of values (not the keys).
    * @return Iterator
    */
   public Iterator iterator() {
      return list.iterator();
   }
}
