package com.accenture.ebar.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.accenture.ebar.controller.TestStrutsApplicationConfig;
import org.apache.commons.digester.Digester;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionFormBean;
import org.apache.struts.action.ActionFormBeans;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForwards;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMappings;
import org.apache.struts.action.DynaActionFormClass;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ApplicationConfig;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.MessageResourcesConfig;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * The TestServlet is extends the EPSAPServlet class to add
 * functionality for running the servlet in a unit test environment
 * (stubbing out the database, etc.).
 */
public class TestServlet extends com.accenture.ebar.controller.SiteMinderServlet
{
   /**
    * javax.servlet.ServletConfig object used by the base Servlet.
    */
   private ServletConfig servletConfig = null;

   /**
    * The RequestProcessor object used by the Struts ActionServlet.
    * This version of the RequestProcessor contains additional tracing
    * information to help with the testing of the system.
    */
   private TestStrutsRequestProcessor requestProcessor = null;

   /**
    * The ApplicationConfig object that contains the Action Mappings.
    */
   private TestStrutsApplicationConfig appConfig = null;

   /**
    * Constructor.
    */
   public TestServlet()
   {
      super();

      logger.debug("Test Mode Activated.");
   }

   /**
    * Overriding this method to specify the class of the config object
    * to use. This Class must be a descendent of
    * com.acenture.core.controller.ServletConfiguration or an
    * exception will be thrown at run time.
    *
    * @return   Class
    */
   protected Class getConfigurationType()
   {
      return TestServletConfiguration.class;
   }

   /**
    * The ApplicationConfig object that contains the Action Mappings.
    *
    * @return   ApplicationConfig
    */
   public ApplicationConfig getApplicationConfig()
   {
      return appConfig;
   }

   /**
    * Allows us to call the protected doPost() method from the outside
    * for testing purposes.
    *
    * @param req  The HTTP request.
    * @param res  The HTTP response.
    */
   public void testDoPost(HttpServletRequest req, HttpServletResponse res)
   {
      try
      {
         doPost(req, res);
      }
      catch (java.lang.Exception e)
      {
         logger.error("Error in doPost() of TestServlet.", e);
      }
   }

   /**
    * Allows test method to set the config object for use later.
    *
    * @param servletConfig  The new ConfigObject value.
    */
   public void setConfigObject(ServletConfig servletConfig)
   {
      this.servletConfig = servletConfig;
   }

   /**
    * Overrides the getServletContext() method to make sure the
    * ServletContext object is accessible. Was getting null pointer
    * exceptions in a test environment without overriding the method.
    *
    * @return   The value of the ServletContext property.
    */
   public ServletContext getServletContext()
   {
      if (servletConfig != null)
      {
         return servletConfig.getServletContext();
      }
      else
      {
         return super.getServletContext();
      }
   }

   /**
    * Look up and return the {@link RequestProcessor} responsible for
    * the specified module, creating a new one if necessary.
    *
    * @param config                The module configuration for which
    * to acquire and return a RequestProcessor.
    * @return                      The value of the RequestProcessor
    * property.
    * @exception ServletException  if we cannot instantiate a
    * RequestProcessor instance
    * @since                       Struts 1.1
    */
   protected synchronized RequestProcessor getRequestProcessor(ApplicationConfig config) throws ServletException
   {

      if (requestProcessor == null)
      {
         requestProcessor = new TestStrutsRequestProcessor();
         requestProcessor.init(this, getApplicationConfig());
         logger.debug("RequestProcessor created.  init() method called.");
      }
      else
      {
         logger.debug("Existing RequestProcessor used.");
      }
      return requestProcessor;
   }

   /**
    * <p>
    *
    * Create (if needed) and return a new Digester instance that has
    * been initialized to process Struts module configuraiton files
    * and configure a corresponding ModuleConfig object (which must be
    * pushed on to the evaluation stack before parsing begins).</p>
    *
    * @since                       Struts 1.1
    */
   /*
   protected Digester initConfigDigester() throws ServletException {
       // Do we have an existing instance?
       if (configDigester != null) {
           return (configDigester);
       }
       // Check the status of the "validating" initialization parameter
       boolean validating = true;
       String value = getServletConfig().getInitParameter("validating");
       if ("false".equalsIgnoreCase(value)
           || "no".equalsIgnoreCase(value)
           || "n".equalsIgnoreCase(value)
           || "0".equalsIgnoreCase(value)) {
           validating = false;
       }
       // Create a new Digester instance with standard capabilities
       logger.debug("Creating struts-config mapping file Digester...");
       configDigester = new Digester();
       logger.debug("Initializing Digester...");
       configDigester.setNamespaceAware(true);
       configDigester.setValidating(validating);
       configDigester.setUseContextClassLoader(true);
       configDigester.addRuleSet(new ConfigRuleSet());
       for (int i = 0; i < registrations.length; i += 2) {
           URL url = this.getClass().getResource(registrations[i+1]);
           if (url != null) {
              logger.debug("Registering "+url.getPath()+"...  file: "+url.getFile());
              configDigester.register(registrations[i], url.toString());
           }
       }
       // Add any custom RuleSet instances that have been specified
       String rulesets = getServletConfig().getInitParameter("rulesets");
       if (rulesets == null) {
           rulesets = "";
       }
       rulesets = rulesets.trim();
       logger.debug("Rulesets = "+rulesets);
       String ruleset = null;
       while (rulesets.length() > 0) {
           int comma = rulesets.indexOf(",");
           if (comma < 0) {
               ruleset = rulesets.trim();
               rulesets = "";
           } else {
               ruleset = rulesets.substring(0, comma).trim();
               rulesets = rulesets.substring(comma + 1).trim();
           }
           if (logger.isDebugEnabled()) {
               logger.debug("Configuring custom Digester Ruleset of type " + ruleset);
           }
           try {
               RuleSet instance = (RuleSet) RequestUtils.applicationInstance(ruleset);
               configDigester.addRuleSet(instance);
           } catch (Exception e) {
               logger.error("Exception configuring custom Digester RuleSet", e);
               throw new ServletException(e);
           }
       }
       logger.debug("Digester initialized with ruleset '"+rulesets+"'.");
       // Return the completely configured Digester instance
       return (configDigester);
   }
*/
   /**
    * <p>
    *
    * Initialize the application configuration information for the
    * specified module.</p>
    *
    * @param prefix                Module prefix for this module
    * @param paths                 Comma-separated list of
    * context-relative resource path(s) for this modules's
    * configuration resource(s)
    * @return                      Description of Return Value
    * @exception ServletException  if initialization cannot be
    * performed
    * @since                       Struts 1.1
    */
   protected ApplicationConfig initApplicationConfig(String prefix, String paths) throws ServletException
   {
      if (logger.isDebugEnabled())
      {
         logger.debug("Initializing module path '"
               + prefix
               + "' configuration from '"
               + paths
               + "'");
      }

      // Parse the configuration for this module
      appConfig = new TestStrutsApplicationConfig(prefix);

      ApplicationConfig config = appConfig;

      logger.debug("ApplicationConfig object created.");

      // Support for module-wide ActionMapping type override
      String mapping = getServletConfig().getInitParameter("mapping");

      if (mapping != null)
      {
         logger.debug("Setting Action Mapping Class to '" + mapping + "'...");
         config.setActionMappingClass(mapping);
      }
      else
      {
         logger.debug("Unable to set Action Mapping Class.  Mapping is null.");
      }

      // Configure the Digester instance we will use
      Digester digester = initConfigDigester();

      // Process each specified resource path
      logger.debug("Processing the resource paths '" + paths + "'...");
      while (paths.length() > 0)
      {
         digester.push(config);

         String path = null;
         int comma = paths.indexOf(',');

         if (comma >= 0)
         {
            path = paths.substring(0, comma).trim();
            paths = paths.substring(comma + 1);
         }
         else
         {
            path = paths.trim();
            paths = "";
         }

         if (path.length() < 1)
         {
            break;
         }

         logger.debug("Parsing config file '" + path + "'...");
         this.parseConfigFile(prefix, paths, config, digester, path);
      }

      // Force creation and registration of DynaActionFormClass instances
      // for all dynamic form beans we wil be using
      FormBeanConfig fbs[] = config.findFormBeanConfigs();

      for (int i = 0; i < fbs.length; i++)
      {
         if (fbs[i].getDynamic())
         {
            DynaActionFormClass.createDynaActionFormClass(fbs[i]);
         }
      }

      // Special handling for the default module (for
      // backwards compatibility only, will be removed later)
      if (prefix.length() < 1)
      {
         this.
               defaultControllerConfig(config);
         defaultMessageResourcesConfig(config);
         defaultFormBeansConfig(config);
         defaultForwardsConfig(config);
         defaultMappingsConfig(config);
      }

      logger.debug("Creation and initialization of configuration object complete.");
      // Return the completed configuration object
      //config.freeze();  // Now done after plugins init
      return (config);
   }

   /**
    * Parses one module config file. <p>
    *
    * NOTE: This method is unchanged from the original copy in
    * ActionServlet. Pulled it in to give access in this class because
    * it was declared private.
    *
    * @param prefix
    * @param paths
    * @param config
    * @param digester               Digester instance that does the
    * parsing
    * @param path                   The path to the config file to
    * parse.
    * @throws UnavailableException
    */
   private void parseConfigFile(String prefix, String paths, ApplicationConfig config, Digester digester, String path) throws UnavailableException
   {
      InputStream input = null;

      try
      {
         URL url = getServletContext().getResource(path);
         InputSource is = new InputSource(url.toExternalForm());

         input = getServletContext().getResourceAsStream(path);
         is.setByteStream(input);
         digester.parse(is);
         getServletContext().setAttribute(Globals.APPLICATION_KEY + prefix, config);

      }
      catch (MalformedURLException e)
      {
         logger.error("Error occured while parsing config file.", e);
         handleConfigException(paths, e);
      }
      catch (IOException e)
      {
         logger.error("Error occured while parsing config file.", e);
         handleConfigException(paths, e);
      }
      catch (SAXException e)
      {
         logger.error("Error occured while parsing config file.", e);
         handleConfigException(paths, e);
      }
      finally
      {
         if (input != null)
         {
            try
            {
               input.close();
            }
            catch (IOException e)
            {
               logger.error("Error occured while parsing config file.  Unable to close input file.", e);
               throw new UnavailableException(e.getMessage());
            }
         }
      }
   }

   /**
    * Simplifies exception handling in the parseModuleConfigFile()
    * method. <p>
    *
    * NOTE: This method is unchanged from the original copy in
    * ActionServlet. Pulled it in to give access in this class because
    * it was declared private.
    *
    * @param paths
    * @param e
    * @throws UnavailableException
    */
   private void handleConfigException(String paths, Exception e) throws UnavailableException
   {
      logger.error(internal.getMessage("configParse", paths), e);
      throw new UnavailableException(internal.getMessage("configParse", paths));
   }

   /**
    * Perform backwards-compatible configuration of the default
    * module's controller configuration from servlet initialization
    * parameters (as were used in Struts 1.0).
    *
    * @param config  The ModuleConfig object for the default module
    * @since         Struts 1.1
    * @deprecated    Will be removed in a release after Struts 1.1.
    */
   private void defaultControllerConfig(ApplicationConfig config)
   {

      String value = null;
      ControllerConfig cc = config.getControllerConfig();

      value = getServletConfig().getInitParameter("bufferSize");
      if (value != null)
      {
         cc.setBufferSize(Integer.parseInt(value));
      }

      value = getServletConfig().getInitParameter("content");
      if (value != null)
      {
         cc.setContentType(value);
      }

      value = getServletConfig().getInitParameter("locale");
      // must check for null here
      if (value != null)
      {
         if ("true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value))
         {
            cc.setLocale(true);
         }
         else
         {
            cc.setLocale(false);
         }
      }

      value = getServletConfig().getInitParameter("maxFileSize");
      if (value != null)
      {
         cc.setMaxFileSize(value);
      }

      value = getServletConfig().getInitParameter("nocache");
      if (value != null)
      {
         if ("true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value))
         {
            cc.setNocache(true);
         }
         else
         {
            cc.setNocache(false);
         }
      }

      value = getServletConfig().getInitParameter("multipartClass");
      if (value != null)
      {
         cc.setMultipartClass(value);
      }

      value = getServletConfig().getInitParameter("tempDir");
      if (value != null)
      {
         cc.setTempDir(value);
      }
   }

   /**
    * Perform backwards-compatible configuration of an ActionFormBeans
    * collection, and expose it as a servlet context attribute (as was
    * used in Struts 1.0). Note that the current controller code does
    * not (and should not) reference this attribute for any reason.
    * <p>
    *
    * NOTE: This method is unchanged from the original copy in
    * ActionServlet. Pulled it in to give access in this class because
    * it was declared private.
    *
    * @param config  The ModuleConfig object for the default app
    * @since         Struts 1.1
    * @deprecated    Will be removed in a release after Struts 1.1.
    */
   private void defaultFormBeansConfig(ApplicationConfig config)
   {

      FormBeanConfig fbcs[] = config.findFormBeanConfigs();
      ActionFormBeans afb = new ActionFormBeans();

      afb.setFast(false);
      for (int i = 0; i < fbcs.length; i++)
      {
         afb.addFormBean((ActionFormBean)fbcs[i]);
      }
      afb.setFast(true);
      getServletContext().setAttribute(Globals.FORM_BEANS_KEY, afb);
   }

   /**
    * Perform backwards-compatible configuration of an ActionForwards
    * collection, and expose it as a servlet context attribute (as was
    * used in Struts 1.0). Note that the current controller code does
    * not (and should not) reference this attribute for any reason.
    * <p>
    *
    * NOTE: This method is unchanged from the original copy in
    * ActionServlet. Pulled it in to give access in this class because
    * it was declared private.
    *
    * @param config  The ModuleConfig object for the default app
    * @since         Struts 1.1
    * @deprecated    Will be removed in a release after Struts 1.1.
    */
   private void defaultForwardsConfig(ApplicationConfig config)
   {

      ForwardConfig fcs[] = config.findForwardConfigs();
      ActionForwards af = new ActionForwards();

      af.setFast(false);
      for (int i = 0; i < fcs.length; i++)
      {
         af.addForward((ActionForward)fcs[i]);
      }
      af.setFast(true);
      getServletContext().setAttribute(Globals.FORWARDS_KEY, af);
   }

   /**
    * Perform backwards-compatible configuration of an ActionMappings
    * collection, and expose it as a servlet context attribute (as was
    * used in Struts 1.0). Note that the current controller code does
    * not (and should not) reference this attribute for any reason.
    * <p>
    *
    * NOTE: This method is unchanged from the original copy in
    * ActionServlet. Pulled it in to give access in this class because
    * it was declared private.
    *
    * @param config  The ModuleConfig object for the default app
    * @since         Struts 1.1
    * @deprecated    Will be removed in a release after Struts 1.1.
    */
   private void defaultMappingsConfig(ApplicationConfig config)
   {

      ActionConfig acs[] = config.findActionConfigs();
      ActionMappings am = new ActionMappings();

      am.setServlet(this);
      am.setFast(false);
      for (int i = 0; i < acs.length; i++)
      {
         am.addMapping((ActionMapping)acs[i]);
      }
      am.setFast(true);
      getServletContext().setAttribute(Globals.MAPPINGS_KEY, am);
   }

   /**
    * Perform backwards-compatible configuration of the default
    * module's message resources configuration from servlet
    * initialization parameters (as were used in Struts 1.0). <p>
    *
    * NOTE: This method is unchanged from the original copy in
    * ActionServlet. Pulled it in to give access in this class because
    * it was declared private.
    *
    * @param config  The ModuleConfig object for the default module
    * @since         Struts 1.1
    * @deprecated    Will be removed in a release after Struts 1.1.
    */
   private void defaultMessageResourcesConfig(ApplicationConfig config)
   {

      String value = null;

      MessageResourcesConfig mrc =
            config.findMessageResourcesConfig(Globals.MESSAGES_KEY);

      if (mrc == null)
      {
         mrc = new MessageResourcesConfig();
         mrc.setKey(Globals.MESSAGES_KEY);
         config.addMessageResourcesConfig(mrc);
      }
      value = getServletConfig().getInitParameter("application");
      if (value != null)
      {
         mrc.setParameter(value);
      }
      value = getServletConfig().getInitParameter("factory");
      if (value != null)
      {
         mrc.setFactory(value);
      }
      value = getServletConfig().getInitParameter("null");
      if (value != null)
      {
         if (value.equalsIgnoreCase("true") ||
               value.equalsIgnoreCase("yes"))
         {
            mrc.setNull(true);
         }
         else
         {
            mrc.setNull(false);
         }
      }
   }
}
