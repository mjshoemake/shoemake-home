/* File name:     SoapClientConfiguration.java
 * Package name:  mjs.home.commands.users.client
 * Project name:  Home - WebServiceClient
 * Date Created:  Apr 18, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.commands.users.client;

/**
 * This class is the point of contact for configuration of 
 * the User Web Service Client.  
 * 
 * @author mshoemake
 */
public class SoapClientConfiguration
{
   /**
    * The URL to the User web service.
    */
   private String userWebServiceURL = null;

   /**
    * Constructor.
    */
   public SoapClientConfiguration()
   {
      userWebServiceURL = "http://localhost:8080/shoemake/UserService";
   }
   
   /**
    * The URL to the User web service.
    */
   public String getUserWebServiceURL()
   {
      return userWebServiceURL;
   }

}
