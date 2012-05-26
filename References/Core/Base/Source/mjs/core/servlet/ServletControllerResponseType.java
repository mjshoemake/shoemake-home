/* File name:     ServletControllerResponseType.java
 * Package name:  mjs.core.servlet
 * Project name:  Core - Base
 * Date Created:  May 8, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.core.servlet;

// MJS imports
import mjs.core.types.EnumerationType;

/**
 * The type of response from the ServletController.  This
 * can be HTML, XML DATA, etc.  This tells the view what
 * to do with the response.
 * 
 * @author mshoemake
 */
public class ServletControllerResponseType extends EnumerationType
{
   // Main
   public static final int    RESPONSE_HTML = 0;
   public static final int    RESPONSE_XML_DATA = 1;

   /**
    * Constructor.
    * @param nDefaultValue
    */
   public ServletControllerResponseType(int nDefaultValue)
   {
      super(nDefaultValue);
   }
  
   /**
    * The maximum value for this enumeration type.
    */
   public int getMaximumValue()
   {
      return 1;
   }

   /**
    * The minimum value for this enumeration type.
    */
   public int getMinimumValue()
   {
      return 0;
   }


}
