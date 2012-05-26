//
// file: WSMessageLog.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/MessageLog.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.0  $
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.utils;


/**
 * WSMessageLog:  This is the interface into the java log.  NO DIRECT CALLS to System.out.println
 * should be used in the code.  Everything that gets written to the log should go through this interface. <p>
 * This was pretty much stolen directly out of the source for eQuality 6.0.
 */
public class MessageLog
{
   private static int theMessageLevel       = 3;
   public static final int PERFORMANCE_MODE = -1;
   public static final int DEBUG_MODE       = 3;
   public static final int INFO_MODE        = 2;
   public static final int ERROR_MODE       = 1;
   public static final int QUIET_MODE       = 0;

   /**
    * This class should NOT be instantiated.  It should be used through the static methods provided.
    */
   protected MessageLog()
   {
   }

   /**
    * The amount and type of messages desired to be displayed in the Java console.
    * <p>   DEBUG_MODE    Displays all types of messages except performance.
    * <p>   INFO_MODE     Displays error and info messages only. <p>   ERROR_MODE    Displays error messages only.
    * <p>   QUIET_MODE    Displays no messages. <p>   PERFORMANCE   Displays performance messages only.
    */
   public static void setLoggingLevel(int nLevel)
   {
      theMessageLevel = nLevel;
   }

   /**
    * Display a debug message if the logging level permits it.
    */
   public static void Debug(String str)
   {
      if (theMessageLevel >= DEBUG_MODE)
      { System.out.println("Debug: " + str); }
   }

   /**
    * Display an info message if the logging level permits it.
    */
   public static void Info(String str)
   {
      if (theMessageLevel >= INFO_MODE)
      { System.out.println("Info: " + str); }
   }

   /**
    * Display an error message if the logging level permits it.
    */
   public static void Error(String str)
   {
      if (theMessageLevel >= ERROR_MODE)
      { System.out.println("Error: " + str); }
   }

   /**
    * Display an internal error message if the logging level permits it. <p>
    * This should only be called by the WSException class.  This is not intended for normal use.
    */
   public static void InternalError(String str)
   {
      if (theMessageLevel >= ERROR_MODE)
      { System.out.println(str); }
   }

   /**
    * Display a performance message if the logging level permits it.
    */
   public static void Performance(String str)
   {
      if (theMessageLevel == PERFORMANCE_MODE)
      { System.out.println("Performance: " + str); }
   }

   /**
    * If the logging level is INFO or DEBUG, then return true.  Otherwise return false.
    */
   public static boolean isDebugging()
   {
      if (theMessageLevel >= INFO_MODE)
      { return true; }
      else { return false; }
   }
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/MessageLog.java-arc  $
//
//     Rev 1.0   Aug 23 2002 14:48:28   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 18:00:26   mshoemake
//  Initial revision.


