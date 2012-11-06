package mjs.model;

import java.io.Serializable;

/**
 * This is the business object interface implemented by objects that
 * will be assigned to PaginatedList.  
 */
public interface BusinessObject extends Serializable
{
   /**
    * The primary key.  Implemented from BusinessObject interface which
    * allows this object to be used in conjunction with PaginatedList.
    */
   String getPk();

   /**
    * The primary key.  Implemented from BusinessObject interface which
    * allows this object to be used in conjunction with PaginatedList.
    */
   void setPk(String value);

   /**
    * The name associated with the object.
    */
   String getName();

   /**
    * The name associated with the object.
    */
   void setName(String value);
}


