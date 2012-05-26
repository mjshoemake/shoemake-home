//
// file: WSCommandFactory.java
// desc:
// proj: ER 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/communication/CommandFactory.java-arc  $
// $Author:Mike Shoemake$
// $Revision:3$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.communication;


/**
 * WSCommandFactory is the base factory to instantiate all command objects. It
 * contains a instance of WSBaseCommandExecuter that has methods necessary to execute a command.
 * @author   Jing Zou
 * @version  1.0
 * @date     1/31/2002
 */
public abstract class CommandFactory
{
   /**
    * The command executer object used by the command to talk to the socket manager.
    */
   protected BaseCommandExecuter wsBaseCommandExecuter;

   /**
    * Write message to log file
    * @param   sMsg  The message to be writen to log file
    */
   public void writeToLog(String sMsg)
   {
      wsBaseCommandExecuter.writeToLog(sMsg);
   }
}
// $Log:
//  3    Balance   1.2         2/17/2003 10:46:58 AM  Mike Shoemake   Changes
//       to CommandTest utility.
//  2    Balance   1.1         12/2/2002 5:33:22 PM   Mike Shoemake   Made this
//       class abstract.
//  1    Balance   1.0         8/23/2002 2:44:18 PM   Mike Shoemake
// $
//
//     Rev 1.1   Dec 02 2002 17:33:22   mshoemake
//  Made this class abstract.
//
//     Rev 1.0   Aug 23 2002 14:44:18   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:25:50   mshoemake
//  Initial revision.
//
//     Rev 1.1   Feb 15 2002 14:50:46   jzou
//  Added PVCS tag for the check-in notes.


