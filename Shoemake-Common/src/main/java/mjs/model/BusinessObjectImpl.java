/* Generated by Together */

package mjs.model;

// Java imports
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is the base business object class for custom component models
 * and other business classes where the ID, type, and name are relevent.
 * <p>
 * All component baseclasses should have a model that extends from
 * this class.
 */
public @Data @EqualsAndHashCode(callSuper=true) 
    abstract class BusinessObjectImpl extends ModelLoggable 
                                      implements BusinessObject
{
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L; 
    
   /**
    * An integer object ID.  This can be either specified in the constructor, set
    * using the set method, or the baseclass will auto generate a number that is unique for the class.
    */
   private long objectID = 0;

   /**
    * A user defined object type.  This can be used in conjunction with integer
    * constants defined by the developer.  It is not used internally for anything and is very flexible.
    */
   private int objectType = 0;

   /**
    * The name associated with the object if applicable.
    */
   private String displayText = "";
}


