/* File name:     AddUserForm.java
 * Package name:  mjs.home.struts.actions
 * Project name:  Home - Struts
 * Date Created:  Jul 31, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.struts.actions;

// Java imports
import org.apache.struts.action.ActionForm;

// MJS imports
import mjs.home.model.User;

/**
 * This class is the server-side temporary storage for 
 * the information entered by the user into the form.
 * 
 * @author mshoemake
 */
public class AddUserForm extends ActionForm
{
   /**
    * The user object to be added or modified.
    */ 
   private User user = new User();

   /**
    * Constructor.
    */
   public AddUserForm()
   {
      super();
   }

   /**
    * The user object to be added or modified.
    */
   public User getUser()
   {
      return user;      
   }

   /**
    * The user name or login name for this user.
    */
   public String getUserName()
   {
      return user.getUserName();
   }
   
   /**
    * The password for this user.
    */
   public String getPassword()
   {
      return user.getPassword();
   }
   
   /**
    * The first name for this user.  
    * This may also include the middle name. 
    */
   public String getFirstName()
   {
      return user.getFirstName();
   }
   
   /**
    * The last name for this user. 
    */
   public String getLastName()
   {
      return user.getLastName();
   }
   
   /**
    * The person PK for this user. 
    */
   public int getUserID()
   {
      return user.getUserID();
   }
   
   /**
    * The user name or login name for this user.
    */
   public void setUserName(String value)
   {
      user.setUserName(value);
   }
   
   /**
    * The password for this user.
    */
   public void setPassword(String value)
   {
      user.setPassword(value);
   }
   
   /**
    * The first name for this user.  
    * This may also include the middle name. 
    */
   public void setFirstName(String value)
   {
      user.setFirstName(value);
   }
   
   /**
    * The last name for this user. 
    */
   public void setLastName(String value)
   {
      user.setLastName(value);
   }

   /**
    * The person PK for this user. 
    */
   public void setUserID(int value)
   {
      user.setUserID(value);
   }
   

}
