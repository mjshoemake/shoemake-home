//
// file: WSBaseCommandExecuter.java
// desc:
// proj: ER 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/communication/BaseCommandExecuter.java-arc  $
// $Author:Mike Shoemake$
// $Revision:5$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.communication;

import java.util.*;


/**
 * WSBaseCommandExecuter is an interface that declares abstract methods necessary to execute commands.
 * @author   Jing Zou
 * @version  1.0
 * @date     1/31/2002
 */
public interface BaseCommandExecuter
{
   /**
    * Method to send out command strings.
    * @param   sCommand        The type of the command.
    * @param   vctLines        The indivisual command strings.
    * @param   bEnableLogging  Should this command write to the logfile?
    * @param   nThreadNumber   An int value used to display a thread number in
    *                          the logfile for multi-threaded tasks.
    */
   public String sendCmd(String sCommand, Vector vctLines, boolean bEnableLogging, int nThreadNumber) throws Exception;

   /**
    * Method to put message to log.
    * @param   sLogMsg   The message to be logged.
    */
   public boolean writeToLog(String sLogMsg);
}
// $Log:
//  5    Balance   1.4         3/28/2003 8:38:17 AM   Mike Shoemake   Added
//       Togethersoft dependencies for the diagrams.
//  4    Balance   1.3         1/17/2003 8:50:24 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  3    Balance   1.2         10/11/2002 8:54:10 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  2    Balance   1.1         9/4/2002 7:30:18 AM    Mike Shoemake   Updated
//       CommandTest project to simulate multiple users creating evaluations
//       simultaneously.
//  1    Balance   1.0         8/23/2002 2:44:16 PM   Mike Shoemake
// $
//
//     Rev 1.2   Oct 11 2002 08:54:10   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.1   Sep 04 2002 07:30:18   mshoemake
//  Updated CommandTest project to simulate multiple users creating evaluations simultaneously.
//
//     Rev 1.0   Aug 23 2002 14:44:16   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:25:50   mshoemake
//  Initial revision.
//
//     Rev 1.1   Feb 15 2002 14:50:46   jzou
//  Added PVCS tag for the check-in notes.


