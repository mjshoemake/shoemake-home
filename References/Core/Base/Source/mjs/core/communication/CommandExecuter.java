//
// file: WSCommandExecuter.java
// desc:
// proj: ER 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/communication/CommandExecuter.java-arc  $
// $Author:Mike Shoemake$
// $Revision:7$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.communication;

import java.util.*;
import java.io.*;
import mjs.core.communication.SocketManager;
import mjs.core.utils.DateUtils;

/**
 * WSCommandExecuter is a implementation of WSBaseCommandExecuter.
 * @author   Jing Zou
 * @version  1.0
 * @date     1/31/2002
 */
public class CommandExecuter implements BaseCommandExecuter
{
   private SocketManager socketMgr = null;
   private FileWriter fileWriter = null;
   private PrintWriter printWriter = null;
   private FileManager lnkFileManager;

   /**
    * Constructor. To instantiate a object of SocketManager.
    * @param   sServerAddress   The address to the server.
    * @param   nServerPort      The port to use when connecting with the server.
    * @param   sUserSessionId   The session ID of the currently logged in user.
    */
   public CommandExecuter(String sServerAddress, int nServerPort, String sUserSessionId)
   {
      socketMgr = new SocketManager(sServerAddress, nServerPort, sUserSessionId);
      fileWriter = null;
   }

   /**
    * Constructor. To intantiate a FileWriter object.
    * @param   sServerAddress   The address to the server.
    * @param   nServerPort      The port to use when connecting with the server.
    * @param   sUserSessionId   The session ID of the currently logged in user.
    * @param   sLogFile         The log file name (including path).
    */
   public CommandExecuter(String sServerAddress, int nServerPort, String sUserSessionId, String sLogFile)
   {
      this(sServerAddress, nServerPort, sUserSessionId);
      try
      {
         fileWriter = new FileWriter(new File(sLogFile));
         printWriter = new PrintWriter(fileWriter);
      }
      catch (java.lang.Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Method to call send method of SocketManager.
    * @param   sCommand        The type of the command.
    * @param   vctLines        The indivisual command strings.
    * @param   bEnableLogging  Should this command write to the logfile?
    * @param   nThreadNumber   An int value used to display a thread number in
    *                          the logfile for multi-threaded tasks.
    */
   public String sendCmd(String sCommand, Vector vctLines, boolean bEnableLogging, int nThreadNumber) throws Exception
   {
      try
      {
         // Log the beginning of the request.
         if (bEnableLogging == true)
            writeToLog(DateUtils.getNow() + "        Thread #" + nThreadNumber + " " + sCommand + ": BEGIN EXECUTION.");

         Date startDate = new Date();
         String result = socketMgr.send(sCommand, vctLines);

         // The server is done with the request.
         Date endDate = new Date();
         if (bEnableLogging == true)
         {
            String duration = DateUtils.getElapsedTimeHHMMSS(startDate, endDate);
            writeToLog(DateUtils.getNow() + "        Thread #" + nThreadNumber + " " + sCommand + ": FINISHED.  Duration = "+duration);
         }
         return result;
      }
      catch (Exception e)
      {
         throw e;
      }
   }

   /**
    * Method to write log message to a file.
    * @param   sLogMsg   The message to be logged.
    */
   public boolean writeToLog(String sLogMsg)
   {
      if (printWriter != null)
      {
         try
         {
            printWriter.println(sLogMsg);
            printWriter.flush();
            return true;
         }
         catch (java.lang.Exception e)
         {
            // MJS ERROR
         }
      }
      return false;
   }

}
