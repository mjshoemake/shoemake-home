package mjs.model;

/**
 * This is the business object interface implemented by objects that
 * will be assigned to PaginatedList.  
 */
public interface BusinessObject
{
   /**
    * The primary key.  Implemented from BusinessObject interface which
    * allows this object to be used in conjunction with PaginatedList.
    */
   String getPK();

   /**
    * The primary key.  Implemented from BusinessObject interface which
    * allows this object to be used in conjunction with PaginatedList.
    */
   void setPK(String value);

   /**
    * The name associated with the object.
    */
   String getName();

   /**
    * The name associated with the object.
    */
   void setName(String value);
}


