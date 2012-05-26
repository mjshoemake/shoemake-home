
package mjs.core.utils;



/**
 * This listener should be implemented by any object that wants to
 * listen for the change in value of an enumeration object.
 */
public interface EnumerationTypeListener
{
   /**
    * Called by an enumeration type when the value changes.
    *
    * @param newValue  The new int value of the enumeration type.
    * @param object    An object of type WSEnumerationType that
    * contains the new value.
    */
   public void onValueChanged(int newValue, EnumerationType object);

   /**
    * Called by an enumeration type before the value changes. This
    * method returns a boolean value. If the result is true, the
    * change operation will continue. If not, the change operation
    * will be aborted. This event allows the owner of the
    * EnumerationType object to decide whether or not the change is
    * allowed.
    *
    * @param previousValue  The original value before the change
    * request.
    * @param newValue       The new int value of the enumeration type.
    * @param object         An object of type EnumerationType that
    * contains the new value.
    * @return               Description of Return Value
    */
   public boolean allowValueCange(int previousValue, int newValue, EnumerationType object);
}
