package mjs.model;



/**
 * A DataLayerException is an CoreException used specifically for the
 * data access layer found in com.accenture.core.model.
 */
public class ModelException extends mjs.exceptions.CoreException
{
    static final long serialVersionUID = 9020182288989758191L;
    
   /**
    * Constructor.
    *
    * @param s
    */
   public ModelException(String s)
   {
      super(s);
   }

   /**
    * Constructor.
    *
    * @param s
    * @param e
    */
   public ModelException(String s, java.lang.Throwable e)
   {
      super(s, e);
   }

}
