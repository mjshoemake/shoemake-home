package com.accenture.ebar.controller;

import java.io.InputStream;
import java.util.Properties;
import com.accenture.core.controller.ServletConfiguration;
import com.accenture.core.model.DatabaseDriver;
import com.accenture.core.utils.CoreException;
import com.accenture.ebar.mock.MockDatabaseDriver;


/**
 * TestServletConfiguration is a configuration object used to load
 * properties from an external file and provide access to those
 * properties through getters and setters. <p>
 *
 * It's primary use is to allow the servlet to behave differently (use
 * stubs for the database access, etc.) without changes to the source
 * code itself. <p>
 *
 * This information could have been included in the web.xml, but
 * web.xml is more critical. It could potentially be dangerous to push
 * out a new web.xml file periodically just for config settings
 * related to testing. A accidental change to web.xml could disable
 * the entire site. <p>
 *
 * If the test.properties file is missing for some reason, the config
 * object will assume a production environment. This is the safest
 * course.
 */
public class TestServletConfiguration extends ServletConfiguration
{
   private final String PROP_STUB_OUT_DATABASE = "stub.out.database";
   private final String PROP_ALLOW_SENT_EMAILS = "allow.sent.emails";
   private final String PROP_IGNORE_ALL_BUT_THIS_TEST = "ignore.all.but.this.test";

   /**
    * The list of properties read in from the properties file.
    */
   Properties properties = loadFromFile("test.properties");

   /**
    * Constructor.
    */
   public TestServletConfiguration()
   {
   }

   /**
    * Load the configuration information from the file
    * test.properties.
    *
    * @param filename  Description of Parameter
    * @return          Description of Return Value
    */
   public Properties loadFromFile(String filename)
   {
      Properties properties = new Properties();

      try
      {
         log.debug("Loading test configuration from properties file (" + filename + ".");

         InputStream stream = this.getClass().getClassLoader().getResourceAsStream("test.properties");

         properties.load(stream);
      }
      catch (java.lang.Exception e)
      {
         log.info("Unable to load file.  Forcing production environment instead.");

         properties = new Properties();
         properties.put(PROP_STUB_OUT_DATABASE, "false");
         properties.put(PROP_ALLOW_SENT_EMAILS, "false");
         properties.put(PROP_IGNORE_ALL_BUT_THIS_TEST, "");
      }
      return properties;
   }

   /**
    * Create a new database driver. If servlet is configured to stub
    * out the database, this method will return an instance of
    * MockDatabaseDriver. Otherwise it will return an instance of
    * DatabaseDriver, which actually access the database.
    *
    * @param props              Description of Parameter
    * @return                   AbstractDatabaseDriver.
    * @exception CoreException  Description of Exception
    */
   protected DatabaseDriver createDatabaseDriver(Properties props) throws CoreException
   {
      if (isStubOutDatabaseEnabled())
      {
         // Stub out the database.
         return new MockDatabaseDriver(props);
      }
      else
      {
         // Database is accessible.  Use PercAdvancedDriver.
         return new DatabaseDriver(props);
      }
   }

   /**
    * Should the database be stubbed out?
    *
    * @return   The value of the StubOutDatabaseEnabled property.
    */
   public boolean isStubOutDatabaseEnabled()
   {
      String stubbed = properties.getProperty(PROP_STUB_OUT_DATABASE);

      if (stubbed == null)
      {
         // Default to false if property is missing.
         return false;
      }
      if (stubbed.toLowerCase().equals("true"))
      {
         // Property text is 'true'.
         return true;
      }
      else
         return false;
   }

   /**
    * Should the test case be allowed to send outgoing emails?
    *
    * @return   The value of the SentEmailEnabled property.
    */
   public boolean isSentEmailEnabled()
   {
      String allow = properties.getProperty(PROP_ALLOW_SENT_EMAILS);

      if (allow == null)
      {
         // Default to false if property is missing.
         return false;
      }
      if (allow.toLowerCase().equals("true"))
      {
         // Property text is 'true'.
         return true;
      }
      else
         return false;
   }

   /**
    * Should this test be allowed to run?
    *
    * @param classname  Description of Parameter
    * @return           The value of the TestEnabled property.
    */
   public boolean isTestEnabled(String classname)
   {
      String allow = properties.getProperty(PROP_IGNORE_ALL_BUT_THIS_TEST);

      if (allow == null)
      {
         log.debug("TEST: " + classname + "...   " + PROP_IGNORE_ALL_BUT_THIS_TEST + " is missing so running all tests.");
         return true;
      }
      else if (allow.equals(""))
      {
         // Default to true if property is missing.
         log.debug("TEST: " + classname + "...   " + PROP_IGNORE_ALL_BUT_THIS_TEST + " is blank so running all tests.");
         return true;
      }
      else if (allow.toLowerCase().equals(classname.toLowerCase()))
      {
         // Only this test is allowed to run.
         return true;
      }
      else
      {
         log.debug("IGNORING: " + classname + ".   " + PROP_IGNORE_ALL_BUT_THIS_TEST + "=" + allow);
         return false;
      }
   }

   /**
    * Should this test be allowed to run?
    *
    * @param type  Description of Parameter
    * @return      The value of the TestEnabled property.
    */
   public boolean isTestEnabled(Class type)
   {
      String classname = type.getName();
      int lastDot = classname.lastIndexOf(".");
      String actualname = classname.substring(lastDot + 1);

      return isTestEnabled(actualname);
   }
}
