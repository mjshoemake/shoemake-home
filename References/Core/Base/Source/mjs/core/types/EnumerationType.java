/* File name:     EnumerationType.java
 * Package name:  mjs.core.types
 * Project name:  Core
 * Date Created:  Apr 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.core.types;


/**
 * EnumerationType:  This class is used to define int-based user
 * defined types.  It performs range checking when the value is
 * set to make sure that the value falls within the range specified. <p>
 * In order to for the owner of this object to be notified when the
 * value changes, the owner must implement the WSEnumerationTypeOwner interface.
 */
public abstract class EnumerationType
{
   /**
    * The current value.
    */
   private int nValue = 0;

   /**
    * The owner of this enumeration type object.
    */
   private EnumerationTypeOwner wsOwner = null;

   /**
    * Constructor.
    * @param  nDefaultValue   The default int value.
    */
   public EnumerationType(int nDefaultValue)
   {
      nValue = nDefaultValue;
   }

   /**
    * The owner of this enumeration type object.
    */
   public void setOwner(EnumerationTypeOwner owner)
   {
      wsOwner = owner;
   }

   /**
    * The current value.
    */
   public int getValue()
   {
      return nValue;
   }

   /**
    * The current value.
    * @param  value   The new int value.
    */
   public void setValue(int value)
   {
      if(  value < getMinimumValue() || value > getMaximumValue()  )
      {
         // MJS ERROR
        //return;
      }
      else
      {
         nValue = value;
         if (wsOwner != null)
         {
            // Notify owner of change.
            wsOwner.event_OnEnumerationValueChanged(this);
         }
      }
   }

   /**
    * The minimum value allowed.
    */
   public abstract int getMinimumValue();

   /**
    * The maximum value allowed.
    */
   public abstract int getMaximumValue();
}
