package mjs.users;

import mjs.utils.Loggable;


/**
 * This is the data object or suitcase for a User. This data object
 * should not contain any business logic. This class is used by the
 * AuthenticationManager.
 */
public class User extends Loggable
{
   /**
    * The user ID for this user. This is how users should be
    * referenced in the database.
    */
   private int user_pk = -1;

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
   public User(int user_pk,
         String fname,
         String lname,
         String username,
         String password)
   {
      super("Model");
      this.user_pk = user_pk;
      this.fname = fname;
      this.lname = lname;
      this.username = username;
      this.password = password;
   }

   /**
    * Constructor.
    */
   public User()
   {
      super("Model");
   }

   /**
    * The user ID for this user. This is how users should be
    * referenced in the database.
    */
   public int getUser_pk()
   {
      return user_pk;
   }

   /**
    * The user ID for this user. This is how users should be
    * referenced in the database.
    */
   public void setUser_pk(int value)
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

}
