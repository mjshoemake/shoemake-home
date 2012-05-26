/* File name:     Parameter.java
 * Package name:  mjs.core.model
 * Project name:  Core
 * Date Created:  Apr 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.core.model;

/**
 * This is an object representation of a name-value pair
 * for a parameter list.
 * 
 * @author mshoemake
 */
public class Parameter
{
   /**
    * The parameter name.
    */
   private String name = null;

   /**
    * The parameter value.
    */
   private Object value = null;

   /**
    * Constructor.
    */
   public Parameter(String name, Object value)
   {
      this.name = name;
      this.value = value;
   }
   
   /**
    * The parameter name.
    */
   public String getName()
   {
      return name;
   }

   /**
    * The parameter name.
    */
   public Object getValue()
   {
      return value;
   }

}
