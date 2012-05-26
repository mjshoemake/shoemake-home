//
// file: WSIDGenerator.java
// desc:
// proj: ER 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/IDGenerator.java-arc  $
// $Author:Mike Shoemake$
// $Revision:7$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components;

// Java imports
import java.util.*;
// Witness imports
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;


/**
 * WSIDGenerator generates unique IDs for the baseclass components.
 * The unique IDs are generated based on the current time and a 4-digit random number.
 * @author  Roshen Chandran
 * @version 1.1
 * @date    1/18/2000
 */
public class IDGenerator
{
   /**
    * The calendar object.  This ID generated will be based on the current date and time.
    */
   private static Calendar calendar = Calendar.getInstance();

   /**
    * The most recent ID that was generated.
    */
   private static long lPreviousId = 0;

   /**
    * The upper limit.  This is used by the random number generator.
    */
   private static final int UPPER_LIMIT_INT = 999;

   /**
    * Generate the randomizer.
    */
   private static Random random = new Random(calendar.getTime().getTime());

   /**
    * Generate the ID.
    */
   public static long generateID()
   {
      long lNewId = -1;
      calendar = Calendar.getInstance();
      try
      {
         // Get numeric representation of the year, month, day, hour, minute, and second.
         int nYear = calendar.get(Calendar.YEAR);
         int nMonth = calendar.get(Calendar.MONTH);
         int nDay = calendar.get(Calendar.DAY_OF_MONTH);
         int nHour = calendar.get(Calendar.HOUR_OF_DAY);
         int nMin = calendar.get(Calendar.MINUTE);
         int nSec = calendar.get(Calendar.SECOND);
         lPreviousId += 1;
         if (lPreviousId > UPPER_LIMIT_INT)
         {
            // Reset the counter.
            lPreviousId = 0;
         }
         // Get a String representation of the day, hour, minute, and second.
         String sYear = Integer.toString(nYear);
         String sMonth = Integer.toString(nMonth);
         String sDay = Integer.toString(nDay);
         String sHour = Integer.toString(nHour);
         String sMin = Integer.toString(nMin);
         String sSec = Integer.toString(nSec);
         String sPrev = Long.toString(lPreviousId);
         // Concatonate the String values.
         String id = sYear + sMonth + sDay + sHour + sMin + sSec + sPrev;
         // Convert the new ID to a long.
         lNewId = Long.parseLong(id);
         return lNewId;
      }
      catch (java.lang.Exception e)
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10410,
                                                       "Balance",
                                                       "Evaluations",
                                                       e.getMessage(),
                                                       Message.INTERNAL,
                                                       InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                       mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "IDGenerator" );
      }
      return lNewId;
   }

   /**
    * Generate the new number.
    */
   public static long getRandomNumber()
   {
      // Get a random number less than 10,000
      calendar = Calendar.getInstance();
      random = new Random(calendar.getTime().getTime());
      return random.nextInt(UPPER_LIMIT_INT);
   }

   // TOGETHER DIAGRAM DEPENDENCIES.
   // PLEASE DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# MessageDialogHandler lnkMessageDialogHandler; */

   /**
    * @link dependency
    */
   /*# InternationalizationStrings lnkInternationalizationStrings; */

}


