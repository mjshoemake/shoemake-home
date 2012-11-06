package mjs.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mjs.model.ModelLoggable;

/**
 * This is the data object or suitcase for a User. This data object
 * should not contain any business logic. This class is used by the
 * AuthenticationManager.
 */
public @Data @EqualsAndHashCode(callSuper=true) class User extends ModelLoggable
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
    * The user's password.
    */
   private String pw = "";

}
