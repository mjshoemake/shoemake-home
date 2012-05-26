//
// file: UserSuitCase.java
// desc:
// proj: ER 6.x
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/forms/castor/suitcases/UserSuitCase.java-arc  $
// $Author:Charles Bassham$
// $Revision:4$
////
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.test.castor;


public class UserSuitCase
{
   int user_pk = 0;
   String username = "";
   String password = "";
   String firstName = "";
   String lastName = ""; 

   /**
    * Default Constructor
	*/
   public UserSuitCase()
   {
   }

   public void setUserID(int value)
   {
      this.user_pk = value;
   }

   public int getUserID()
   {	
      return user_pk;
   }

   public void setUserName(String username)
   {
      this.username = username;
   }

   public String getUserName()
   {
      return username;
   }

   public void setFirstName(String value)
   {
      firstName = value;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setLastName(String value)
   {
      lastName = value;
   }

   public String getLastName()
   {
      return lastName;
   }
   
   public void setPassword(String value)
   {
      password = value;
   }

   public String getPassword()
   {
      return password;
   }
   
}
