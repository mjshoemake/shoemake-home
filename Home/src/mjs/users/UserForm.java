package mjs.users;

import java.util.Hashtable;
//import com.accenture.core.model.ActionForm;
//import mjs.utils.FormatUtils;
import mjs.view.ValidationErrorList;


/**
 * This is the data object or suitcase for a UserForm. This data
 * object should not contain any business logic.
 */
public class UserForm //extends ActionForm
{
	   /**
	    * The user ID for this user. This is how users should be
	    * referenced in the database.
	    */
	   private String user_pk = "";

	   /**
	    * The user's first name.
	    */
	   private String fname = "";

	   /**
	    * The user's last name.
	    */
	   private String lname = "";

	   /**
	    * The user login ID.
	    */
	   private String username = "";

	   /**
	    * The user's password.
	    */
	   private String password = "";
	   

	   /**
	    * Constructor.
	    */
	   public UserForm(String user_pk,
	         String fname,
	         String lname,
	         String username,
	         String password)
	   {
	      this.user_pk = user_pk;
	      this.fname = fname;
	      this.lname = lname;
	      this.username = username;
	      this.password = password;
	   }

	   /**
	    * Constructor.
	    */
	   public UserForm()
	   {
	   }

	   /**
	    * The user ID for this user. This is how users should be
	    * referenced in the database.
	    */
	   public String getUser_pk()
	   {
	      return user_pk;
	   }

	   /**
	    * The user ID for this user. This is how users should be
	    * referenced in the database.
	    */
	   public void setUser_pk(String value)
	   {
	      user_pk = value;
	   }

	   /**
	    * The user's first name.
	    *
	    * @return   The value of the Fname property.
	    */
	   public String getFname()
	   {
	      return fname;
	   }

	   /**
	    * The user's first name.
	    *
	    * @param value  The new Fname value.
	    */
	   public void setFname(String value)
	   {
	      fname = value;
	   }

	   /**
	    * The user's last name.
	    *
	    * @return   The value of the Lname property.
	    */
	   public String getLname()
	   {
	      return lname;
	   }

	   /**
	    * The user's last name.
	    *
	    * @param value  The new Lname value.
	    */
	   public void setLname(String value)
	   {
	      lname = value;
	   }

	   public String getUsername()
	   {
	      return username;
	   }

	   public void setUsername(String value)
	   {
	      username = value;
	   }

	   public String getPassword()
	   {
	      return password;
	   }

	   public void setPassword(String value)
	   {
	      password = value;
	   }


   /**
    * Check to see if this form is valid.
    *
    * @param mapping  Description of Parameter
    * @return         ValidationErrorList
    */
   @SuppressWarnings("rawtypes")
   public ValidationErrorList validate(Hashtable mapping)
   {
      ValidationErrorList errors = new ValidationErrorList();

/*      
      if (uid.equals(""))
         errors.addError("user_ID", "This is a required field.");
      if (firstName.equals(""))
         errors.addError("fname", "This is a required field.");
      if (lastName.equals(""))
         errors.addError("lname", "This is a required field.");

      // Phone format.
      FieldDefinition def = (FieldDefinition)mapping.get("phone");

      if (! FormatUtils.isValidString(phone, def))
         errors.addError("phone", "Input does not match the required format: " + def.getFormat() + ".");
*/
      return errors;
   }

}
