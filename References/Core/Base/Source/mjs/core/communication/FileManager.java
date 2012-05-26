//
// file: WSFileManager.java
// desc:
// proj: ER 6.3 and later
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/communication/FileManager.java-arc  $
// $Author:Mike Shoemake$
// $Revision:6$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.communication;

// Java imports
import java.io.*;

/**
 * WSFileManager is responsible for all file manipulation.  Any
 * file I/O should be handled in this class or in an extension of this class.
 * @author   Mike Shoemake
 * @version  1.01
 * @date     3/11/2001
 */
public class FileManager
{
   /**
    * Creates a file at the specified location and saves an xml or similar document to that file.
    */
   public static void writeXMLFile(String sFileName, String sText)
   {
      try
      {
         File file = new File(sFileName);
         FileWriter fileWriter = new FileWriter(file);
         fileWriter.write(sText);
         fileWriter.flush();
      }
      catch (NullPointerException nPe)
      {
         // MJS ERROR
      }
      catch (Exception e)
      {
         // MJS ERROR
      }   
   }

   /**
    * Reads xml or simlar document from a file at the specified location.
    */
   public static String readXMLFile(String sFileName)
   {
      String sXML = "";
      try
      {
         File file = new File(sFileName);
         BufferedReader fileReader = new BufferedReader(new FileReader(file));
         String next = fileReader.readLine().trim();
         while (next != null)
         {
            // Concatonate the strings.
            sXML = sXML + next;
            next = fileReader.readLine().trim();
         }
      }
      catch (NullPointerException nPe)
      {
         // MJS ERROR
      }
      catch (Exception e)
      {
         // MJS ERROR
      }
      return sXML;
   }

   /**
    * Opens the file at the specified location for write.  It
    * returns a FileWriter object which is used to save the file content.
    */
   public static FileWriter openFileForWrite(String sFileName)
   {
      FileWriter fileWriter = null;
      try
      {
         File file = new File(sFileName);
         fileWriter = new FileWriter(file);
      }
      catch (NullPointerException nPe)
      {
         // MJS ERROR
      }
      catch (Exception e)
      {
         // MJS ERROR
      }
      return fileWriter;
   }

   /**
    * Opens the file at the specified location for read.  It returns
    * a BufferedReader object which is used to extract the file contents.
    */
   public static BufferedReader openFileForRead(String sFileName)
   {
      BufferedReader fileReader = null;
      try
      {
         File file = new File(sFileName);
         fileReader = new BufferedReader(new FileReader(file));
      }
      catch (NullPointerException nPe)
      {
         // MJS ERROR
      }
      catch (Exception e)
      {
         // MJS ERROR
      }
      return fileReader;
   }
}
