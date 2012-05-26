/* File name:     XSLTest.java
 * Package name:  mjs.test.castor
 * Project name:  Core - TestApp
 * Date Created:  May 1, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.test.xalan;

// Java imports
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Description of class
 * 
 * @author mshoemake
 */
public class XSLTest
{
   /**
    * Tranform the specified xml using the specified xsl file.
    * <p> 
    * This method uses the TraX interface to perform a 
    * transformation in the simplest manner possible.
    */
   public static String executeXSL(String xml, String xsl) throws java.lang.Exception
   {
      try
      {
         // Initialize the writers and readers.
         StringWriter writer = new StringWriter();
         StringReader xmlReader = new StringReader(xml);
         StringReader xslReader = new StringReader(xsl);

         // Create the transformer factory.
         TransformerFactory tFactory = TransformerFactory.newInstance();
   
         // Create the transformer, specifying the location of the XSL file.
         Transformer transformer = tFactory.newTransformer(new StreamSource(xslReader));

         // Execute the XSL transformation.
         transformer.transform(new StreamSource(xmlReader), new StreamResult(writer));
         return writer.toString();
      }
      catch (java.lang.Exception e)
      {
         throw e;
      }
   }


}
