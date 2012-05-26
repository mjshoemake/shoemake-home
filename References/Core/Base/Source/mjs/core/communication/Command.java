//
// file: WSCommand.java
// desc:
// proj: ER 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/communication/Command.java-arc  $
// $Author:   mshoemake  $
// $Revision:   1.1  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.communication;


/**
 * WSCommand is the base class for all concrete command classes.
 * @author   Jing Zou
 * @version  1.0
 * @date     1/31/2002
 */
abstract public class Command
{
   public Command(BaseCommandExecuter wsBaseCommandExecuter, boolean bEnableLogging)
   {
      this.wsBaseCommandExecuter = wsBaseCommandExecuter;
      this.bEnableLogging = bEnableLogging;
   }

   /**
    * Method to execute the command.
    */
   abstract public Object execute() throws Exception;

   /**
    * Write message to log file
    * @param   sMsg  The message to be writen to log file
    */
   public void writeToLog(String sMsg)
   {
      wsBaseCommandExecuter.writeToLog(sMsg);
   }

   /**
    * Retrieve log setting. If true, messages will be logged.
    */
   public boolean getLogSetting()
   {
      return bEnableLogging;
   }

   protected BaseCommandExecuter wsBaseCommandExecuter;
   protected boolean bEnableLogging;
}
// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/communication/Command.java-arc  $
//
//     Rev 1.1   Oct 11 2002 08:54:10   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:44:16   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:25:50   mshoemake
//  Initial revision.
//
//     Rev 1.1   Feb 15 2002 14:50:46   jzou
//  Added PVCS tag for the check-in notes.


