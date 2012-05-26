/* File name:     TestUserListXSLT.java
 * Package name:  mjs.home.unittest
 * Project name:  Home - UnitTest
 * Date Created:  May 1, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.unittest;

// Java imports
import junit.framework.TestCase;

// MJS imports
import mjs.core.communication.LoggingManager;
import mjs.core.communication.JarFileExtractor;
import mjs.core.xsl.XSLTransformer;

/**
 * Description of class
 * 
 * @author mshoemake
 */
public class TestUserListXSLT extends TestCase
{

   /**
    * Constructor for TestUserListXSLT.
    * @param arg0
    */
   public TestUserListXSLT(String arg0)
   {
      super(arg0);
   }

   final public void testSendHttpResponse()
   {
      try
      {
         String xslPath = "";
         String xml = "";
         String xsl = JarFileExtractor.getXMLText(xslPath, false);
         //writeToLog("HomeViewManager  sendHttpResponse()  Extracted xsl.  xsl = "+xsl, LoggingManager.PRIORITY_DEBUG);
         String html = XSLTransformer.executeXSL(xml, xsl);
         //writeToLog("HomeViewManager  sendHttpResponse()  Extracted xsl.  xsl = "+xsl, LoggingManager.PRIORITY_DEBUG);
      }
      catch (java.lang.Exception e)
      {
      }
   }

}
