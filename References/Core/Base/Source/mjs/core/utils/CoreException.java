package mjs.core.utils;



/**
 * An CoreException is used to daisy-chain exceptions together to
 * create a stack trace in the event of an error.
 */
public class CoreException extends java.lang.Exception
{
   /**
    * The Exception object that caused this one to be created. If you
    * follow the chain of exceptions from top to bottom, you should
    * arrive at the root cause of the error.
    */
   private java.lang.Exception next = null;

   /**
    * Constructor.
    *
    * @param s
    */
   public CoreException(String s)
   {
      super(s);
   }

   /**
    * Constructor.
    *
    * @param s
    * @param e
    */
   public CoreException(String s, java.lang.Exception e)
   {
      super(s);
      next = e;
   }

   /**
    * The Exception object that caused this one to be created. If you
    * follow the chain of exceptions from top to bottom, you should
    * arrive at the root cause of the error.
    *
    * @return   java.lang.Exception
    */
   public java.lang.Exception getNext()
   {
      return next;
   }

   /**
    * The Exception object that caused this one to be created. If you
    * follow the chain of exceptions from top to bottom, you should
    * arrive at the root cause of the error.
    *
    * @param newNext
    */
   public void setNext(java.lang.Exception newNext)
   {
      next = newNext;
   }
}
