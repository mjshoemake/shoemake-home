package mjs.core.communication;

// Java imports
import java.io.InputStream;
import java.awt.*;
import java.awt.image.ImageProducer;
import java.net.*;


/**
 * The class to use when extracting files (text files, images, etc.) from the
 * jar.  DTDs, JPGs, and other non-executable files can be loaded from the
 * jar using this class.  All methods of this class are static utility methods.
 */
public class JarFileExtractor
{
   /**
    * Retrieve an image from the JAR file. <p> This method reads the file out of the JAR File as an InputStream, converts
    * it to a image, and returns it to the calling method.
    * @param  location   The image file to be retrieved from the JAR file.
    */
   public static Image getImage(String location) throws java.lang.Exception
   {
      URL imageURL = Class.forName("mjs.core.communication.JarFileExtractor").getResource(location);
      Toolkit tk = Toolkit.getDefaultToolkit();
      try
      {
         ImageProducer imageProducer = (ImageProducer)(imageURL.getContent());
         return tk.createImage(imageProducer);
      }
      catch (java.lang.Exception e)
      {
         throw e;
      }
   }

   /**
    * Retrieve a text file from the JAR file. <p> This method reads the file out of the JAR File as an InputStream and
    * returns it to the calling method.
    * @param  location    The text file to be retrieved from the JAR file.
    * @param  bTruncate   Truncate the text file after the occurance of "]>".
    */
   public static String getXMLText(String location, boolean bTruncate) throws java.lang.Exception
   {
      try
      {
         // Create the input stream.
         URL textURL = Class.forName("mjs.core.communication.JarFileExtractor").getResource(location);
         InputStream file = textURL.openStream();
         // Create the buffer.
         byte[] buffer = new byte[5000];
         // Read the file into the buffer.
         file.read(buffer, 0, buffer.length);
         // Convert the buffer to a String object.
         String sDTD = new String(buffer);
         // Truncate if necessary.
         if (!bTruncate)
         {
            int nIndex = sDTD.lastIndexOf(">");
            sDTD = sDTD.substring(0, nIndex + 1);
         }
         else
         {
            int nIndex = sDTD.indexOf("]>");
            sDTD = sDTD.substring(0, nIndex + 2);
         }
         return sDTD;
      }
      catch (java.lang.Exception e)
      {
         throw e;
      }
   }
}


