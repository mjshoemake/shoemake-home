/* File name:     User.java
 * Package name:  mjs.home.model
 * Project name:  Home - Model
 * Date Created:  Apr 14, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.model;

/**
 * This is the data object or suitcase for a User in the
 * Shoemake home page project.
 * <p>
 * This data object should not contain any business logic. 
 * 
 * @author mshoemake
 */
public class User
{
   /**
    * The user ID or user primary key for this user.  This
    * is how users should be referenced in the database.
    */
   private int userID = 0;

   /**
    * The user name or login name for this user.
    */
   private String userName = null;
   
   /**
    * The password for this user.  The password defaults to 
    * the word "password" if none is specified.
    */
   private String password = "password";
   
   /**
    * The first name for this user.  
    * This may also include the middle name. 
    */
   private String firstName = null;
   
   /**
    * The last name for this user. 
    */
   private String lastName = null;
   

   /**
    * Constructor. 
    */
   public User(int id,
               String userName, 
               String password,
               String firstName,
               String lastName)
   {
      this.userID = id;
      this.userName = userName;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
   }

   /**
    * Constructor. 
    */
   public User()
   {
   }

   /**
    * The user ID or user primary key for this user.  This
    * is how users should be referenced in the database.
    */
   public int getUserID()
   {
      return userID;
   }
   
   /**
    * The user name or login name for this user.
    */
   public String getUserName()
   {
      return userName;
   }
   
   /**
    * The password for this user.
    */
   public String getPassword()
   {
      return password;
   }
   
   /**
    * The first name for this user.  
    * This may also include the middle name. 
    */
   public String getFirstName()
   {
      return firstName;
   }
   
   /**
    * The last name for this user. 
    */
   public String getLastName()
   {
      return lastName;
   }
   
   /**
    * The user ID or user primary key for this user.  This
    * is how users should be referenced in the database.
    */
   public void setUserID(int value)
   {
      userID = value;
   }
   
   /**
    * The user name or login name for this user.
    */
   public void setUserName(String value)
   {
      userName = value;
   }
   
   /**
    * The password for this user.
    */
   public void setPassword(String value)
   {
      password = value;
   }
   
   /**
    * The first name for this user.  
    * This may also include the middle name. 
    */
   public void setFirstName(String value)
   {
      firstName = value;
   }
   
   /**
    * The last name for this user. 
    */
   public void setLastName(String value)
   {
      lastName = value;
   }
   
}
