package mjs.view;



/**
 * This is an object used to encapsulate a select option in a picklist
 * on the JSP. It maps the display text to the numeric value.
 */
public class SelectOption
{
   /**
    * The numeric value associated with this option.
    */
   String value = "0";

   /**
    * The display text for this option.
    */
   String caption = "";

   /**
    * Constructor.
    *
    * @param value    Description of Parameter
    * @param caption  Description of Parameter
    */
   public SelectOption(String value, String caption)
   {
      this.value = value;
      this.caption = caption;
   }

   /**
    * The numeric value associated with this option.
    *
    * @return   The value of the Value property.
    */
   public String getValue()
   {
      return value;
   }

   /**
    * The numeric value associated with this option.
    *
    * @param value  The new Value value.
    */
   public void setValue(String value)
   {
      this.value = value;
   }

   /**
    * The display text for this option.
    *
    * @return   The value of the Caption property.
    */
   public String getCaption()
   {
      return caption;
   }

   /**
    * The display text for this option.
    *
    * @param value  The new Caption value.
    */
   public void setCaption(String value)
   {
      this.caption = value;
   }

}
