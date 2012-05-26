/* File name:     PageType.java
 * Package name:  mjs.home.controller
 * Project name:  Home - Controller
 * Date Created:  Apr 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.controller;

// MJS imports
import mjs.core.types.EnumerationType;

/**
 * This is the class that encapsulates the type of
 * page to display.  Each page will have a unique 
 * number assigned to it.
 * 
 * @author mshoemake
 */
public class PageType extends EnumerationType
{
   // Main
   public static final int    STATE_INITIAL = -1;
   public static final int    STATE_LOGIN = 0;
   public static final int    STATE_MAIN = 1;

   // Errors   
   public static final int    STATE_INVALID_LOGIN = 2;
   public static final int    STATE_INVALID_POST = 3;
   
   // Users
   public static final int    STATE_USER_LIST = 4;
   public static final int    STATE_EDIT_USER = 5;
   public static final int    STATE_ADD_USER = 6;
   public static final int    STATE_DELETE_USER = 7;
     
   
   /**
    * Constructor.
    */
   public PageType()
   {
      super(0);
   }

   /**
    * The maximum value for this enumeration type.
    */
   public int getMaximumValue()
   {
      return 7;
   }

   /**
    * The minimum value for this enumeration type.
    */
   public int getMinimumValue()
   {
      return -1;
   }

}
