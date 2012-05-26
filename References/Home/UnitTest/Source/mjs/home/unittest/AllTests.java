/* File name:     AllTests.java
 * Package name:  mjs.home.unittest
 * Project name:  Home - UnitTest
 * Date Created:  May 1, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.unittest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Description of class
 * 
 * @author mshoemake
 */
public class AllTests
{

   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(AllTests.class);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("Test for mjs.home.unittest");
      //$JUnit-BEGIN$
      suite.addTest(new mjs.home.unittest.MyTest("mjs.home.unittest.MyTest"));
      suite.addTest(new mjs.home.unittest.TestUserListXSLT("mjs.home.unittest.TestUserListXSLT"));
      //$JUnit-END$
      return suite;
   }
}
