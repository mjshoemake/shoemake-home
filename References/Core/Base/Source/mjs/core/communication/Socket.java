//
// file: WSSocket.java
// desc:
// proj: ER 6.3 and later
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/communication/Socket.java-arc  $
// $Author:Mike Shoemake$
// $Revision:9$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.communication;

// Java imports
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
// Witness imports
import mjs.core.strings.SystemStrings;


/**
 * WSSocket is a wrapper class around Java's Socket class. It provides access methods to initialize sockets, send strings,
 * read strings and close Sockets.
 */
public class Socket
{
   protected String sServerAddress = null;
   protected int nServerPort;
   protected java.net.Socket clientSocket = null;
   protected PrintWriter outSocket = null;
   protected BufferedReader inSocket = null;

   /**
    * Constructor.
    * @param sServerAddress   Address of the server to connect to. eg. "sun.com" or "216.89.232.2"
    * @param nServerPort      Port number on the server to connect to.
    */
   public Socket(String pServerAddress, int pServerPort)
   {
      sServerAddress = pServerAddress;
      nServerPort = pServerPort;
   }

   /**
    * Opens a socket if the ServerAddress and Port have already been specified.  Otherwise, it throws an WSERException.
    */
   public void open() throws java.lang.Exception
   {
      try
      {
         clientSocket = new java.net.Socket(sServerAddress, nServerPort);

         /* Tie input and output Streams to a BufferedReader and
            PrintWriter for receiving and sending data.  */

         outSocket = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), SystemStrings.szUnicodeLittle));
         inSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), SystemStrings.szUnicodeLittle));
         // Set the timeout.
         // (plichten, 9/4/2002) - Increase from one minute to two minutes since some
         // commands like getEvalList can take longer than a minute for a large DB
         clientSocket.setSoTimeout(360000);
      }
      catch (java.lang.Exception e)
      {
         // Throw exception.
         throw e;
      }
   }

   /**
    * Closes the open Socket to the server. If Socket is already closed, it ignores the request.
    */
   public void close()
   {
      try
      {
         clientSocket.close();
      }
      catch (Exception e)
      {
         /* Ignore close errors.  Socket must already be closed for some
            reason.  */
      }
   }

   /**
    * Sends a carriage-return and newline terminated string to the server; the line is flushed immediately.
    * @param sMessage   The Message string to be sent to the server.
    */
   public void send(String sMessage)
   {
      outSocket.write(sMessage + "\n");
      outSocket.flush();
   }

   /**
    * Reads a line from the socket.  If there are no lines to read in the buffer, it waits until a line is received.
    */
   public String read()
   {
      String sResponse = "null";
      try
      {
         sResponse = inSocket.readLine();
      }
      catch (Exception e)
      {
         // MJS ERROR
      }
      return sResponse;
   }

   /**
    * The address to the server.
    * @param sServerAddress   Address of the server to connect to. eg. "sun.com" or "216.89.232.2"
    */
   public void setServerAddress(String sServerAddress)
   {
      this.sServerAddress = sServerAddress;
   }

   /**
    * The port to use to connect with the server.
    * @param nServerPort      Port number on the server to connect to.
    */
   public void setServerPort(int nServerPort)
   {
      this.nServerPort = nServerPort;
   }

   /**
    * The address to the server.
    * @param sServerAddress   Address of the server to connect to. eg. "sun.com" or "216.89.232.2"
    */
   public String getServerAddress()
   {
      return this.sServerAddress;
   }

   /**
    * The port to use to connect with the server.
    * @param nServerPort      Port number on the server to connect to.
    */
   public int getServerPort()
   {
      return this.nServerPort;
   }
}
