package mjs.database;



/**
 * A DataLayerException is an CoreException used specifically for the
 * data access layer found in com.accenture.core.model.
 */
public class DataLayerException extends mjs.exceptions.CoreException
{
    static final long serialVersionUID = 9020182288989758191L;
    
   /**
    * Constructor.
    *
    * @param s
    */
   public DataLayerException(String s)
   {
      super(s);
   }

   /**
    * Constructor.
    *
    * @param s
    * @param e
    */
   public DataLayerException(String s, java.lang.Throwable e)
   {
      super(s, e);
   }

}
