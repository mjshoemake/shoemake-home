package mjs.database;



/**
 * A DataLayerException is an CoreException used specifically for the
 * data access layer found in com.accenture.core.model.
 */
public class BusinessLogicException extends mjs.exceptions.CoreException
{
    static final long serialVersionUID = 9020182288989758191L;

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
