//
// file: WSPendingJobs.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/administration/PendingJobsList.java-arc  $
// $Author:Mike Shoemake$
// $Revision:7$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.administration;

// Java imports
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.Timer;
// Witness imports
// **************************************************************************
// IF THIS IMPORT STATEMENT CHANGES PLEASE NOTIFY THE TEAM LEAD SO THE CLASS
// DIAGRAMS CAN BE UPDATED.
// **************************************************************************
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;


/**
 * WSPendingJobs:  This is the class that tracks and schedules jobs
 * that are intended to be run in the background.  The derived class
 * implements and registers the jobs to execute.  The base class
 * provides the interface to add and remove jobs from the queue.  This
 * class encapsulates swing's Timer class and performs a check every x milliseconds to see which pending jobs need to execute.
 * <p> Several empty event methods have been made public to allow the developer to
 * perform some action when the event occurs without having to add their own
 * listeners to the component.  To handle the events simply override the event
 * handler methods and implement the desired functionality.  Not all of the events
 * are triggered by listeners.  Some, like OnDelete, are called when the delete() method is called. <code>
 * <p>   public abstract void   event_OnExecuteJob (Integer iNextJob);
 * <p>   public          void   event_BeforeStartingCheck()            {} </code>
 */
public abstract class PendingJobsList
{
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */

   /**
    * This is the handler for the different types of jobs.
    */
   public abstract void event_OnExecuteJob(Integer iNextJob);

   /**
    * This is the handler for the different types of jobs.
    */
   public void event_BeforeStartingCheck() { }

   /**
    * This is used to prevent the checkPendingJobs() methods from ever running multiple
    * copies of itself at the same time.  See checkPendingJobs()
    */
   private int nCheckingPendingJobs = 0;

   /**
    * This is used to allow a class on the outside to know which job is currently
    * executing.  If no jobs are executing, this will be set to -1.
    */
   private int nCurrentJob = -1;

   /**
    * This is the list of jobs that are pending (two or more processes must be completed before
    * this job executes.  Need to check periodically to see if both processes are complete so we can execute the pending job.
    */
   private Vector vctPendingJobs = new Vector();

   /**
    * The timer that will be handling the
    */
   private Timer timer = null;

   /**
    * Constructor. <p> The frequency defaults to 1000 milliseconds (1 second).
    */
   public PendingJobsList()
   {
      this(1000);
   }

   /**
    * Constructor.
    * @param    milliseconds   The frequency (how often) to check the pending job list (1000 milliseconds = 1 second).
    */
   public PendingJobsList(int milliseconds)
   {
      timer = new Timer(milliseconds,
      new java.awt.event.ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            checkPendingJobs();
         }
      });
      timer.setInitialDelay(0);
   }

   /**
    * Add to the pending jobs list.  Before adding to the list, this method checks
    * to see if the specified job is already in the list.  If it is, it does not add it a second time.
    * @param  nNewJob   The int value corresponding to the job to add to the queue.
    */
   public void addToPendingJobs(int nNewJob)
   {
      boolean bFound = false;
      Integer iNextJob = new Integer(nNewJob);
      if ((nNewJob < getMinimumJobValue()) || (nNewJob > getMaximumJobValue()))
      {
         // Value out of range.
         // Create message object
         Message message = MessageFactory.createMessage( 10415,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSPendingJobs" );
         return;
      }
      // Is this job already in the list?
      if (vctPendingJobs.size() != 0)
      {
         for (int C = 0; C <= vctPendingJobs.size() - 1; C++)
         {
            Integer iJob = (Integer)(vctPendingJobs.get(C));
            if (nNewJob == iJob.intValue())
            { bFound = true; }
         }
      }
      // If already in the list, don't add it again.
      if (!bFound)
      {
         vctPendingJobs.add(iNextJob);
         // New job added.  Start the timer if it is not currently running.
         if (!timer.isRunning())
         {
            timer.start();
         }
      }
   }

   /**
    * Remove from the pending jobs list.  If the job to be removed is the currently
    * running job, it will not be interrupted.  Instead, it will finish it's
    * execution and will not start again.  Jobs that execute are NOT automatically
    * & removed from the list and will execute again if they are not removed from the
    * list when they execute.  This allows for jobs remain in the list that have
    * been triggered but were unable to completely execute due to some dependency.
    * @param   iJobToRemove    An Integer object whose int value corresponds to the job to be removed from the list.
    */
   public void removeFromPendingJobs(Integer iJobToRemove)
   {
      vctPendingJobs.remove(iJobToRemove);
   }

   /**
    * Remove from the pending jobs list.  If the job to be removed is the currently
    * running job, it will not be interrupted.  Instead, it will finish it's
    * execution and will not start again.  Jobs that execute are NOT automatically
    * & removed from the list and will execute again if they are not removed from the
    * list when they execute.  This allows for jobs remain in the list that have
    * been triggered but were unable to completely execute due to some dependency.
    * @param   nJobToRemove    An int value that corresponds to the job to be removed from the list.
    */
   public void removeFromPendingJobs(int nJobToRemove)
   {
      if (vctPendingJobs.size() != 0)
      {
         for (int C = 0; C <= vctPendingJobs.size() - 1; C++)
         {
            Integer iNext = (Integer)(vctPendingJobs.get(C));
            if (iNext.intValue() == nJobToRemove)
            {
               vctPendingJobs.remove(iNext);
               return;
            }
         }
      }
   }

   /**
    * Is this job currently in the list?
    * @param   nJob   An int value that corresponds to the job for which to check.
    */
   public boolean contains(int nJob)
   {
      if (vctPendingJobs.size() != 0)
      {
         for (int C = 0; C <= vctPendingJobs.size() - 1; C++)
         {
            Integer iNext = (Integer)(vctPendingJobs.get(C));
            if (iNext.intValue() == nJob)
            {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Check the list for jobs and run the jobs currently in the list.
    */
   private void checkPendingJobs()
   {
      nCheckingPendingJobs = nCheckingPendingJobs + 1;
      if (nCheckingPendingJobs == 1)
      {
         // Only want one instance of this method running at a time.
         // We have to handle it this way because of the multi-threaded applet creation.
         checkPendingJobs_Singleton();
      }
      nCheckingPendingJobs = nCheckingPendingJobs - 1;
   }

   /**
    * This is the method to execute the pending jobs.  Only one copy of this method can be running at one time.
    */
   private void checkPendingJobs_Singleton()
   {
      Enumeration eJobs = vctPendingJobs.elements();
      Integer iNextJob = null;
      int nJob = -1;
      // EVENT.
      event_BeforeStartingCheck();
      while (eJobs.hasMoreElements())
      {
         iNextJob = (Integer)(eJobs.nextElement());
         // EVENT (implemented in derived class)
         event_OnExecuteJob(iNextJob);
      }
      // Stop timer if no more jobs.  It will restart when a new job is added.
      if (vctPendingJobs.size() == 0)
      {
         timer.stop();
      }
   }

   /**
    * This is used to allow a class on the outside to know which job is currently
    * executing.  If no jobs are executing, this will be set to -1.
    */
   public int getCurrentJob()
   {
      return nCurrentJob;
   }

   /**
    * Is the timer currently running?
    */
   public boolean isTimerRunning()
   {
      return timer.isRunning();
   }

   /**
    * How long in milliseconds will the timer wait between executions?
    */
   public int getTimerDelay()
   {
      return timer.getDelay();
   }

   /**
    * How long in milliseconds will the timer wait between executions?
    */
   public void setTimerDelay(int milliseconds)
   {
      timer.setDelay(milliseconds);
   }

   /**
    * The lowest integer value assigned to a job.  This is used to perform range checking when adding a new job.
    */
   public abstract int getMinimumJobValue();

   /**
    * The lowest integer value assigned to a job.  This is used to perform range checking when adding a new job.
    */
   public abstract int getMaximumJobValue();

   // TOGETHERSOFT DIAGRAM DEPENDENCIES.  DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# MessageDialogHandler lnkMessageDialogHandler; */

   /**
    * @link dependency
    */
   /*# ComponentUtils lnkComponentUtils; */

}
// $Log:
//  7    Balance   1.6         6/6/2003 8:40:18 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  6    Balance   1.5         3/28/2003 2:48:09 PM   Mike Shoemake   Added
//       Together diagram dependencies.
//  5    Balance   1.4         3/7/2003 9:28:07 AM    Mike Shoemake   Removed
//       reference to forms.utils.ComponentUtils.  Replaced with
//       core.utils.ComponentUtils.  Not sure yet why the same method is in
//       both classes.
//  4    Balance   1.3         1/29/2003 4:47:10 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  3    Balance   1.2         1/17/2003 8:51:03 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  2    Balance   1.1         10/11/2002 8:54:00 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:43:46 PM   Mike Shoemake
// $

//
//     Rev 1.1   Oct 11 2002 08:54:00   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:43:46   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:25:08   mshoemake
//  Initial revision.
//
//   Rev 1.11   Apr 22 2002 16:44:20   mshoemake
//Update to comments.
//
//   Rev 1.10   Apr 16 2002 16:50:08   mshoemake
//Update to import statement.
//
//   Rev 1.9   Mar 05 2002 12:46:18   sputtagunta
//added check for nullpointer exception.
//
//   Rev 1.8   Feb 07 2002 16:12:30   hfaynzilberg
//declared "vctPendingJobs" and "timer" fields "private" based on JTest warning
//
//   Rev 1.7   Dec 07 2001 09:22:24   mshoemake
//Update to WSExceptionFactory class.  Changed throwException() to handleException().


