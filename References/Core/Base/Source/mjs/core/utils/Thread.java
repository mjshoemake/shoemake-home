
package mjs.core.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import mjs.core.utils.CoreException;
import org.apache.log4j.Logger;


/**
 * This is the thread class used to perform various behind-the-scenes
 * or simultaneous tasks. It is designed for ease of use and should
 * allow for minimum coding as new uses for threads arise. When the
 * thread starts, the run() method is called. The run method calls the
 * specified execute method on the specified target object. The
 * execute method will have no parameters, as this implementation does
 * not allow for a parameter list.
 */
public class Thread extends java.lang.Thread
{
   /**
    * The method to be called on the target object when the run()
    * thread is called for this thread.
    */
   private String executeMethod = "";

   /**
    * The object that is used to perform an action when the thread
    * starts. This can be any type of object. This Thread class will
    * use reflection to call the method whose name is stored in the
    * "executeMethod" attribute.
    */
   private Object targetObject = null;

   /**
    * The list of parameters to be passed in to the execute method
    * when it is called.
    */
   private Object[] params = null;

   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Test". See the public methods
    * debug(), info(), etc.
    */
   protected Logger log = Logger.getLogger("Core");

   /**
    * Constructor.
    *
    * @param targetObject   Description of Parameter
    * @param executeMethod  Description of Parameter
    * @param params         Description of Parameter
    */
   public Thread(Object targetObject, String executeMethod, Object[] params)
   {
      this.executeMethod = executeMethod;
      this.targetObject = targetObject;
      this.params = params;
   }

   /**
    * The method to be called on the target object when the run()
    * thread is called for this thread.
    *
    * @return   The value of the ExecuteMethod property.
    */
   public String getExecuteMethod()
   {
      return executeMethod;
   }

   /**
    * The method to be called on the target object when the run()
    * thread is called for this thread.
    *
    * @param value  The new ExecuteMethod value.
    */
   public void setExecuteMethod(String value)
   {
      executeMethod = value;
   }

   /**
    * Creates and returns an instance of the Thread class.
    *
    * @param targetObject   Description of Parameter
    * @param executeMethod  Description of Parameter
    * @param params         Description of Parameter
    * @return               Description of Return Value
    */
   private static Thread createNewThread(Object targetObject, String executeMethod, Object[] params)
   {
      return new Thread(targetObject, executeMethod, params);
   }

   /**
    * Execute a maximum priority thread with the given target object
    * and execute method.
    *
    * @param targetObject   Description of Parameter
    * @param executeMethod  Description of Parameter
    * @param params         Description of Parameter
    * @return               Description of Return Value
    */
   public static Thread executePriorityThread(Object targetObject, String executeMethod, Object[] params)
   {
      Thread thread = null;

      // Create the instance of the thread.
      thread = createNewThread(targetObject, executeMethod, params);
      thread.setPriority(Thread.MAX_PRIORITY);
      // Execute the thread.
      thread.start();
      return thread;
   }

   /**
    * Execute a normal priority thread with the given target object
    * and execute method.
    *
    * @param targetObject   Description of Parameter
    * @param executeMethod  Description of Parameter
    * @param params         Description of Parameter
    * @return               Description of Return Value
    */
   public static Thread executeNormalThread(Object targetObject, String executeMethod, Object[] params)
   {
      Thread thread = null;

      // Create the instance of the thread.
      thread = createNewThread(targetObject, executeMethod, params);
      thread.setPriority(Thread.NORM_PRIORITY);
      // Execute the thread.
      thread.start();
      return thread;
   }

   /**
    * Execute a low priority (background) thread with the given target
    * object and execute method.
    *
    * @param targetObject   Description of Parameter
    * @param executeMethod  Description of Parameter
    * @param params         Description of Parameter
    * @return               Description of Return Value
    */
   public static Thread executeBackgroundThread(Object targetObject, String executeMethod, Object[] params)
   {
      Thread thread = null;

      // Create the instance of the thread.
      thread = createNewThread(targetObject, executeMethod, params);
      thread.setPriority(Thread.MIN_PRIORITY);
      // Execute the thread.
      thread.start();
      return thread;
   }

   /**
    * This will execute multiple threads at once and will wait for all
    * of the threads to finish processing before it returns to the
    * caller.
    *
    * @param targetObject    Description of Parameter
    * @param executeMethods  Description of Parameter
    * @param params          Description of Parameter
    */
   public static void executeThreadGroup(Object targetObject, String[] executeMethods, Object[] params)
   {
      ArrayList threadList = new ArrayList();
      Thread thread = null;

      for (int C = 0; C <= executeMethods.length - 1; C++)
      {
         thread = executePriorityThread(targetObject, executeMethods[C], params);
         threadList.add(thread);
      }
      while (! threadList.isEmpty())
      {
         // Monitor threads until all have completed processing.
         for (int D = threadList.size() - 1; D >= 0; D--)
         {
            thread = (Thread)(threadList.get(D));
            // If thread has finished processing, remove from the list.
            if (! thread.isAlive())
            {
               threadList.remove(thread);
            }
         }
      }
   }

   /**
    * This will execute multiple threads at once and will wait for all
    * of the threads to finish processing before it returns to the
    * caller.
    *
    * @param targetObjectList  Description of Parameter
    * @param executeMethods    Description of Parameter
    * @param params            Description of Parameter
    */
   public static void executeThreadGroup(Object[] targetObjectList, String[] executeMethods, Object[] params)
   {
      ArrayList threadList = new ArrayList();
      Thread thread = null;

      for (int C = 0; C <= targetObjectList.length - 1; C++)
      {
         thread = executePriorityThread(targetObjectList[C], executeMethods[C], params);
         threadList.add(thread);
      }
      while (! threadList.isEmpty())
      {
         // Monitor threads until all have completed processing.
         for (int D = threadList.size() - 1; D >= 0; D--)
         {
            thread = (Thread)(threadList.get(D));
            // If thread has finished processing, remove from the list.
            if (! thread.isAlive())
            {
               threadList.remove(thread);
            }
         }
      }
   }

   /**
    * This will execute multiple threads at once and will wait for all
    * of the threads to finish processing before it returns to the
    * caller.
    *
    * @param targetObjectList  Description of Parameter
    * @param executeMethod     Description of Parameter
    * @param params            Description of Parameter
    */
   public static void executeThreadGroup(Object[] targetObjectList, String executeMethod, Object[] params)
   {
      ArrayList threadList = new ArrayList();
      Thread thread = null;

      for (int C = 0; C <= targetObjectList.length - 1; C++)
      {
         thread = executePriorityThread(targetObjectList[C], executeMethod, params);
         threadList.add(thread);
      }
      while (! threadList.isEmpty())
      {
         // Monitor threads until all have completed processing.
         for (int D = threadList.size() - 1; D >= 0; D--)
         {
            thread = (Thread)(threadList.get(D));
            // If thread has finished processing, remove from the list.
            if (! thread.isAlive())
            {
               threadList.remove(thread);
            }
         }
      }
   }

   /**
    * This will execute multiple threads at once and will wait for all
    * of the threads to finish processing before it returns to the
    * caller.
    *
    * @param targetObjectList  Description of Parameter
    * @param executeMethods    Description of Parameter
    * @param params            Description of Parameter
    */
   public static void executeThreadGroup(ArrayList targetObjectList, String[] executeMethods, Object[] params)
   {
      ArrayList threadList = new ArrayList();
      Thread thread = null;

      for (int C = 0; C <= targetObjectList.size() - 1; C++)
      {
         thread = executePriorityThread(targetObjectList.get(C), executeMethods[C], params);
         threadList.add(thread);
      }
      while (! threadList.isEmpty())
      {
         // Monitor threads until all have completed processing.
         for (int D = threadList.size() - 1; D >= 0; D--)
         {
            thread = (Thread)(threadList.get(D));
            // If thread has finished processing, remove from the list.
            if (! thread.isAlive())
            {
               threadList.remove(thread);
            }
         }
      }
   }

   /**
    * This will execute multiple threads at once and will wait for all
    * of the threads to finish processing before it returns to the
    * caller.
    *
    * @param targetObjectList  Description of Parameter
    * @param executeMethod     Description of Parameter
    * @param params            Description of Parameter
    */
   public static void executeThreadGroup(ArrayList targetObjectList, String executeMethod, Object[] params)
   {
      ArrayList threadList = new ArrayList();
      Thread thread = null;

      for (int C = 0; C <= targetObjectList.size() - 1; C++)
      {
         thread = executePriorityThread(targetObjectList.get(C), executeMethod, params);
         threadList.add(thread);
      }
      while (! threadList.isEmpty())
      {
         // Monitor threads until all have completed processing.
         for (int D = threadList.size() - 1; D >= 0; D--)
         {
            thread = (Thread)(threadList.get(D));
            // If thread has finished processing, remove from the list.
            if (! thread.isAlive())
            {
               threadList.remove(thread);
            }
         }
      }
   }

   /**
    * This method takes an ArrayList of threads created and executed
    * by the caller and waits for all of the threads to finish
    * processing before returning to the caller. It's assumed that the
    * threads will be created and executed with MAX_PRIORITY using the
    * executePriorityThread() method.
    *
    * @param threadList  Description of Parameter
    */
   public static void waitUntilThreadsFinished(ArrayList threadList)
   {
      Thread thread = null;

      while (! threadList.isEmpty())
      {
         // Monitor threads until all have completed processing.
         for (int D = threadList.size() - 1; D >= 0; D--)
         {
            thread = (Thread)(threadList.get(D));
            // If thread has finished processing, remove from the list.
            if (! thread.isAlive())
            {
               threadList.remove(thread);
            }
         }
      }
   }

   /**
    * This is the method to be executed when the start() method is
    * called for the thread. The run() method turns control over to
    * the target object by calling the execute method.
    */
   public void run()
   {
      try
      {
         // Call the execute method.
         Method method = targetObject.getClass().getMethod(executeMethod, new Class[0]);
         Object[] args = new Object[0];

         method.invoke(targetObject, args);
      }
      catch (java.lang.Exception e)
      {
         CoreException ex = new CoreException("Error occurred in thread.  method='" + executeMethod + "()', object=" + targetObject.getClass().getName() + ".", e);

         LogUtils.log(log, ex);
      }
   }
}
