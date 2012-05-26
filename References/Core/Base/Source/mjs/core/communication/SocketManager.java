//
// file: WSSocketManager.java
// desc:
// proj: ER 6.3 and later
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/communication/SocketManager.java-arc  $
// $Author:Mike Shoemake$
// $Revision:6$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.communication;

// Java imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Timer;
import java.util.*;

// Witness imports
import mjs.core.strings.SystemStrings;

/**
 * WSSocketManager is responsible for all client socket communication.
 * It works with the WSSocket class to send and retrieve information from the server. <P>
 * The WSSocketManager keeps track of the last Socket request. If a socket stays idle for longer than the nUserSessionTimeout,
 * the WSSocketManager sends a logout request to the server. The
 * nUserSessionTimeout may be set by invoking the setUserSessionTimeOut
 * method with the delay in minutes; the default setting for the timeout is 120 minutes.
 * @author   Mike Shoemake
 * @version  1.01
 * @date     3/11/2001
 */
public class SocketManager
{
   /**
    * The port to use when connecting to the server.
    */
   private int m_iServerPort = 1001;

   /**
    * The length of time to wait before a time out occurs.
    */
   private int m_iUserSessionTimeOut = 120;

   /**
    * The address of the server.
    */
   private String m_sServerAddress = null;

   /**
    * The session ID of the currently logged in user.
    */
   private String m_sUserSessionId = null;

   /**
    * The Locale.
    */
   private Locale m_Locale = null;

   /**
    * The socket timer.
    */
   private SocketTimer m_hSocketTimer = null;
   private Socket lnkSocket;

   /**
    * Constructor.
    * @param   sServerAddress   The address to the server.
    * @param   iServerPort      The port to use when connecting with the server.
    * @param   sUserSessionId   The session ID of the currently logged in user.
    */
   public SocketManager(String sServerAddress, int iServerPort, String sUserSessionId)
   {
      this.m_sServerAddress = sServerAddress;
      this.m_iServerPort = iServerPort;
      this.m_sUserSessionId = sUserSessionId;
      this.m_hSocketTimer = new SocketTimer(m_iUserSessionTimeOut);
      initializeLocale();
   }

   /**
    * Constructor.
    * @param   sServerAddress   The address to the server.
    * @param   iServerPort      The port to use when connecting with the server.
    * @param   sUserSessionId   The session ID of the currently logged in user.
    */
   public SocketManager(String sServerAddress, int iServerPort, String sUserSessionId, String sResourceClassPath)
   {
      this.m_sServerAddress = sServerAddress;
      this.m_iServerPort = iServerPort;
      this.m_sUserSessionId = sUserSessionId;
      this.m_hSocketTimer = new SocketTimer(m_iUserSessionTimeOut);
      initializeLocale();
   }

   /**
    * Constructor.
    * @param   sServerAddress       The address to the server.
    * @param   iServerPort          The port to use when connecting with the server.
    * @param   iUserSessionTimeout  The amount of time the socket port will remain
    * open without any response from the socket server.
    * @param   sUserSessionId       The session ID of the currently logged in user.
    */
   public SocketManager(String sServerAddress, int iServerPort, int iUserSessionTimeOut, String sUserSessionId)
   {
      this.m_sServerAddress = sServerAddress;
      this.m_iServerPort = iServerPort;
      this.m_sUserSessionId = sUserSessionId;
      this.m_iUserSessionTimeOut = iUserSessionTimeOut;
      this.m_hSocketTimer = new SocketTimer(m_iUserSessionTimeOut);
      initializeLocale();
   }


   /**
    * This class keeps track of inactivity on the communication link
    * It operates a timer that ticks every minute and increments a waiting counter. <p>
    * If the minutes waited exceed the settings for the UserSessionTimeOut, it sends a logout request to the server.
    * The start and stop methods of the class restart and stop the timer respectively.
    */
   class SocketTimer implements ActionListener
   {
      // Wait time, in minutes.
      int m_iWaiting;
      int m_iUserSessionTimeOut;
      Timer m_hTimer;

      /**
       * Constructor (Socket Timer).
       */
      public SocketTimer(int iUserSessionTimeOut)
      {
         //Initialize a 1-minute timer
         m_hTimer = new javax.swing.Timer(60000, this);
         this.m_iUserSessionTimeOut = iUserSessionTimeOut;
      }

      /**
       * ActionPerformed event (Socket Timer).
       */
      public void actionPerformed(ActionEvent e)
      {
         m_iWaiting++;
         if (m_iWaiting >= this.m_iUserSessionTimeOut)
         {
            try
            {
               // Send the logout command.
               logout();
            }
            catch (Exception ex)
            {
               // Problem logging out.
               // MJS ERROR
            }
         }
      }

      /**
       * Start the timer.
       */
      public void start()
      {
         this.m_iWaiting = 0;
         m_hTimer.start();
      }

      /**
       * Stop the timer.
       */
      public void stop()
      {
         m_hTimer.stop();
      }

      /**
       * Is the timer currently running or is it in a wait state?
       */
      public boolean isRunning()
      {
         return this.isRunning();
      }
   }


   /**
    * Send a logout command to the server.
    */
   public void logout() throws Exception
   {
     String szCMD_Logout = "Logout";
     try
     {
        requestData(szCMD_Logout);
     }
     catch (Exception e)
     {
        throw e;
     }
   }

   /**
    * Opens a SocketSession and presents session ID to the server.
    * This is a protected method, to be used only by higher level methods in this class.
    */
   protected Socket openSocketSession() throws Exception
   {
      this.m_hSocketTimer.stop();
      // create and open a new socket.
      Socket clientSocket = new Socket(m_sServerAddress, m_iServerPort);

      try
      {
         clientSocket.open();
         // Send session id.
         presentUser(clientSocket);
         return clientSocket;
      }
      catch (java.lang.Exception ex)
      {
         // MJS ERROR
         return null;
      }
   }

   /**
    * Closes the WSClientSocket, and starts a Timer that ticks every minute.
    */
   protected void closeSocketSession(Socket clientSocket)
   {
      clientSocket.close();
      m_hSocketTimer.start();
   }

   /**
    * This method sends the UserId and UserSessionId to the server. <p> Format:  "PresentUser <i>sUserSessionId</i>" <p>
    * This is invoked by the openSocketSession method of this class.
    */
   protected void presentUser(Socket clientSocket)
   {
      clientSocket.send(SystemStrings.szSVR_PresentUser + " " + m_sUserSessionId);
   }

   /**
    * Send this data across the socket and wait for a response.  Then validate the response and check for errors.
    */
   protected String send(String sCommand, Vector vctLines) throws Exception
   {
      String sCmd = null;
      boolean bSuccess = true;
      try
      {
         // Create and open the session.
         Socket clientSocket = openSocketSession();

         // Send the command across the socket.
         for (int C = 0; C <= vctLines.size() - 1; C++)
         {
            String sNextLine = (String)(vctLines.get(C));
            clientSocket.send(sNextLine);
         }

         // Read status response on line 1
         String sStatusResponse = clientSocket.read();

         // Process "Failure" response
         if (receivedServerError(sCommand, sStatusResponse))
         {
            // Server Error!!
            bSuccess = false;

            // Read the error ID on line 2
            String sErrorID = clientSocket.read();

            // Read the third party message on line 3
            String sThirdPartyMessage = clientSocket.read();

            // Generate Client Server AbstractServletException

            // Close socket
            closeSocketSession(clientSocket);
            
            return sStatusResponse;
         }

         else
         // Process "Success" response
         {
            // Read response on line 2
            String sResponse = clientSocket.read();

            // Close socket
            closeSocketSession(clientSocket);

            return sResponse;
         }
      }
      catch (Exception e)
      {
         throw e;
      }
   }

   private boolean receivedServerError(String sCommand, String sResponse)
   {
      // Was there an error on the server?
      if (sResponse.equalsIgnoreCase(SystemStrings.szSVR_PresentUser + " " + SystemStrings.szSVR_Failure) ||
      sResponse.equalsIgnoreCase(sCommand + " " + SystemStrings.szSVR_Failure) ||
      sResponse.endsWith(SystemStrings.szSVR_Failure) ||
      sResponse.trim().equalsIgnoreCase(SystemStrings.szSVR_Failure))
      {
         // Server error found.
         return true;
      }
      else
      {
         // Server command executed properly.
         return false;
      }
   }
   // ******  Generic Command-Template Methods ******

   /**
    * Generic list request method. <p> This method handles all commands that are parameterless
    * one-line "retrieval" commands.  The return value is the server's response to the request (usually XML). <p>
    * @param   sCommand   The actual command string (GetFormList, etc.).
    */
   public String requestData(String sCommand) throws Exception
   {
      try
      {
         Vector vctCommand = createCommandVector(sCommand);
         return send(sCommand, vctCommand);
      }
      catch (Exception e)
      {
         throw e;
      }
   }

   /**
    * Generic list request method. <p> This method handles all commands that are parameterless
    * one-line "retrieval" commands.  The return value is the server's response to the request (usually XML). <p>
    * @param   sCommand    The actual command string (GetFormList, etc.).
    * @param   sParameter  A String parameter.
    */
   public String requestData(String sCommand, String sParam) throws Exception
   {
      try
      {
         Vector vctCommand = createCommandVector(sCommand + " " + sParam);
         return send(sCommand, vctCommand);
      }
      catch (Exception e)
      {
         throw e;
      }
   }

   /**
    * Generic list request method. <p> This method handles all commands that are parameterless
    * one-line "retrieval" commands.  The return value is the server's response to the request (usually XML). <p>
    * @param   sCommand    The actual command string (GetFormList, etc.).
    * @param   lParameter  A long parameter of some type (usually ID or PK).
    */
   public String requestData(String sCommand, long lParameter) throws Exception
   {
      try
      {
         String sParam = toUnicode(lParameter);
         Vector vctCommand = createCommandVector(sCommand + " " + sParam);
         return send(sCommand, vctCommand);
      }
      catch (Exception e)
      {
         throw e;
      }
   }

   /**
    * Generic list request method. <p> This method handles all commands that are parameterless
    * one-line "retrieval" commands.  The return value is the server's response to the request (usually XML). <p>
    * @param   sCommand      The actual command string (GetFormList, etc.).
    * @param   lParameter    A long parameter of some kind (usually ID or PK).
    * @param   sParameter    A String parameter of some kind.
    */
   public String requestData(String sCommand, long lParam1, String sParam2) throws Exception
   {
      try
      {
         String sParam1 = toUnicode(lParam1);
         Vector vctCommand = createCommandVector(sCommand + " " + sParam1 + " " + sParam2);
         return send(sCommand, vctCommand);
      }
      catch (Exception e)
      {
         throw e;
      }
   }
   // ******  Utility methods ******

   /**
    * Generate server command Vector (single line command).
    */
   protected Vector createCommandVector(String sLine1)
   {
      Vector vctCommand = new Vector();
      vctCommand.add(sLine1);
      return vctCommand;
   }

   /**
    * Generate server command Vector (single line command).
    */
   protected Vector createCommandVector(String sLine1, String sLine2)
   {
      Vector vctCommand = new Vector();
      vctCommand.add(sLine1);
      vctCommand.add(sLine2);
      return vctCommand;
   }

   /**
    * Generate server command Vector (single line command).
    */
   protected Vector createCommandVector(String sLine1, String sLine2, String sLine3)
   {
      Vector vctCommand = new Vector();
      vctCommand.add(sLine1);
      vctCommand.add(sLine2);
      vctCommand.add(sLine3);
      return vctCommand;
   }

   /**
    * Converts an integer to a Unicode string. eg. the number 1234 gets converted to the 4-character "1234" Unicode string
    * @param nInt The integer to be converted.
    * @return     The converted string.
    */
   protected String toUnicode(int nInt)
   {
      return (new Integer(nInt)).toString();
   }

   /**
    * Converts a long integer to a Unicode string. eg. the number 1234 gets converted to the 4-character "1234" Unicode string
    * @param lInt The long to be converted.
    * @return     The converted string.
    */
   protected String toUnicode(long lInt)
   {
      return (new Long(lInt)).toString();
   }

   /**
    * Creates the Locale for the system.
    * @return     The system Locale.
    */
   protected void initializeLocale()
   {
      String region;
      String JREVersion = System.getProperty( "java.version" );
      String language = System.getProperty( "user.language" );
      if ( JREVersion.regionMatches( true, 0, "1.4", 0, 3 ) )
      {
         region = System.getProperty( "user.country" );
      }
      else
      {
         region = System.getProperty( "user.region" );
      }
      m_Locale = new Locale( language, region );
   }

   /**
    * Creates the Locale for the system.
    * @return     The system Locale.
    */
   protected Locale getLocale()
   {
      //MessageLog.Debug("SocketManager:: getLocale() - Locale = " + m_Locale);
      return m_Locale;
   }
}
