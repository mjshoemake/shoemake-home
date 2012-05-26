/* File name:     MyTest.java
 * Package name:  mjs.home.unittest
 * Project name:  Home - UnitTest
 * Date Created:  May 1, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.unittest;

import junit.framework.TestCase;

/**
 * Description of class
 * 
 * @author mshoemake
 */
public class MyTest extends TestCase
{

   /**
    * Constructor for MyTest.
    * @param arg0
    */
   public MyTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
   }

   public void testNothing()
   {
      assertTrue(true);
   }
}
