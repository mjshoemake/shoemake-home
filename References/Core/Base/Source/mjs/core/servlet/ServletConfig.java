/* File name:     ServletConfig.java
 * Package name:  mjs.core.servlet
 * Project name:  Core
 * Date Created:  Apr 22, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.core.servlet;

// Java imports
import java.util.Locale;

// MJS imports
import mjs.core.xml.resourceBundle.MessageResourceBundle;

/**
 * The cofiguration applet for the base Servlet core class.
 * This class stores information to be used further in, 
 * such as the locale, application name, LoggingManager object,
 * etc.
 * 
 * @author mshoemake
 */
public class ServletConfig
{
   /** 
    * The name of the Product associated with the message.
    */
   private String application;

   /**
    * The classpath to the ExceptionLocalResource Bundle to use.
    */
   private MessageResourceBundle messageBundle;

   /**
    * The Country/Language to use when localizing the message text.
    */
   private Locale locale;

   /**
    * The servlet controller object that processes the 
    * requests.
    */
   private ServletController controller;

   /**
    * The default state for this servlet.  This can be any 
    * integer value.
    */
   private int defaultState = 0;

   /**
    * Constructor. 
    */
   public ServletConfig(String appName, 
                        MessageResourceBundle msgBundle, 
                        Locale myLocale, 
                        ServletController controller,
                        int defaultState)
   {
      application = appName;
      messageBundle = msgBundle;
      locale = myLocale;
      this.defaultState = defaultState;
      this.controller = controller;
   }

   /** 
    * The name of the Product associated with the message.
    */
   public String getApplicationName()
   {
      return application;
   }

   /**
    * The classpath to the ExceptionLocalResource Bundle to use.
    */
   public MessageResourceBundle getMessageResourceBundle()
   {
      return messageBundle;
   }

   /**
    * The Country/Language to use when localizing the message text.
    */
   public Locale getLocale()
   {
      return locale;
   }
   
   /**
    * The Country/Language to use when localizing the message text.
    */
   public void setLocale(Locale value)
   {
      locale = value;
   }
   
   /**
    * The default state for this servlet.  This can be any 
    * integer value.
    */
   public int getDefaultState()
   {
      return defaultState;
   }

   /**
    * The servlet controller object that processes the 
    * requests.
    */
   public ServletController getServletController()
   {
      return controller;
   }
   
   /**
    * The servlet controller object that processes the 
    * requests.
    */
   public void setServletController(ServletController controller)
   {
      this.controller = controller;
   }
   
}
