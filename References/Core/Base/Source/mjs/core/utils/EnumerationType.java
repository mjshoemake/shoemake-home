package mjs.core.utils;

import mjs.core.utils.CoreException;


/**
 * EnumerationType is used to define int-based enumeration types. It
 * performs range checking when the value is set to make sure that the
 * value falls within the range specified. <p>
 *
 * In order for the owner of this object to be notified when the value
 * changes, the owner must implement the EnumerationTypeListener
 * interface. The default value is always 0.
 */
public abstract class EnumerationType
{
   /**
    * The current value.
    */
   private int nValue = 0;

   /**
    * The list of registered EnumerationTypeListener objects.
    */
   private EnumerationTypeListenerList listeners = new EnumerationTypeListenerList();

   /**
    * Constructor.
    *
    * @param nDefaultValue  The default int value.
    */
   public EnumerationType(int nDefaultValue)
   {
      nValue = nDefaultValue;
   }

   /**
    * The owner of this enumeration type object.
    *
    * @param listener  Contains the EnumerationTypeListener for
    * EnumerationTypeEvent data.
    */
   public void addEnumerationTypeListener(EnumerationTypeListener listener)
   {
      listeners.add(listener);
   }

   /**
    * The current value.
    *
    * @return   The value of the Value property.
    */
   public int getValue()
   {
      return nValue;
   }

   /**
    * The current value.
    *
    * @param value                       The new int value.
    * @exception BusinessLogicException  Description of Exception
    */
   public void setValue(int value) throws CoreException
   {
      if (value < getMinimumValue() || value > getMaximumValue())
      {
         // Value out of bounds.
         throw new CoreException("Value (" + value + ") out of range (" + getMinimumValue() + "-" + getMaximumValue() + ") for " + this.getClass() + ".");
      }
      else
      {
         boolean bContinue = listeners.allowValueChange(nValue, value, this);

         if (bContinue)
         {
            // Change the value.
            nValue = value;
            // Notify listeners of change.
            listeners.onValueChanged(value, this);
         }
      }
   }

   /**
    * The minimum value allowed.
    *
    * @return   The value of the MinimumValue property.
    */
   public abstract int getMinimumValue();

   /**
    * The maximum value allowed.
    *
    * @return   The value of the MaximumValue property.
    */
   public abstract int getMaximumValue();
}
