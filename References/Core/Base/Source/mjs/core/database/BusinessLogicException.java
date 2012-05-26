package com.accenture.core.model;



/**
 * A DataLayerException is an CoreException used specifically for the
 * data access layer found in com.accenture.core.model.
 */
public class BusinessLogicException extends com.accenture.core.utils.CoreException
{
   /**
    * Constructor.
    *
    * @param s
    */
   public BusinessLogicException(String s)
   {
      super(s);
   }

   /**
    * Constructor.
    *
    * @param s
    * @param e
    */
   public BusinessLogicException(String s, java.lang.Exception e)
   {
      super(s, e);
   }

}
