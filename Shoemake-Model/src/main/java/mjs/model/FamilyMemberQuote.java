package mjs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mjs.model.BusinessObjectImpl;

/**
 * This is the data object or suitcase for a Family Member Quote.  This 
 * data object should not contain any business logic. 
 */
public @Data @EqualsAndHashCode(callSuper=true) 
    class FamilyMemberQuote extends BusinessObjectImpl {

   private static final long serialVersionUID = 1L;
   
    /**
    * The user ID for this user. This is how users should be
    * referenced in the database.
    */
   private int family_member_quotes_pk = -1;

   /**
    * The user's first name.
    */
   private String quote_date = "";

   /**
    * The user's last name.
    */
   private String quote = "";

   /**
    * The user login ID.
    */
   private String family_member_pk = "";

   /**
    * The primary key. Implemented from BusinessObject interface which allows
    * this object to be used in conjunction with PaginatedList.
    */
   public String getPk() {
       return family_member_quotes_pk + "";
   }

   /**
    * The primary key. Implemented from BusinessObject interface which allows
    * this object to be used in conjunction with PaginatedList.
    */
   public void setPk(String value) {
       family_member_quotes_pk = Integer.parseInt(value);
   }

   /**
    * The business object name.
    */
   public String getName() {
       return quote;
   }

   /**
    * The business object name.
    */
   public void setName(String value) {
       quote = value;
   }

}
