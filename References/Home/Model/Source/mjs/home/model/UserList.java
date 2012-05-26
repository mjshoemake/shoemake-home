/* File name:     UserList.java
 * Package name:  mjs.home.model
 * Project name:  Home - Model
 * Date Created:  Apr 14, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.model;

// Java imports
import java.util.Vector;


/**
 * A list of users.
 * 
 * @author mshoemake
 */
public class UserList 
{
   /**
    * The list of user objects.
    */
   private Vector userList = new Vector();

   /**
    * Constructor.
    */
   public UserList()
   {
   }

   /**
    * The list of user objects.
    */
   public Vector asVector()
   {
      return (Vector)(userList.clone());
   }
   
   /**
    * The list of user objects.
    */
   public void setUserList(Vector value)
   {
      userList = value;
   }

   /**
    * Utility method.  Convert this list into an array of Users.
    */
   public User[] toUserArray()
   {
      User[] users = new User[userList.size()];
      for (int C = 0; C <= userList.size()-1; C++)
      {
         users[C] = (User)(userList.get(C)); 
      }
      return users;
   }
}
