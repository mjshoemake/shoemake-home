/* File name:     ServletControllerResponse.java
 * Package name:  mjs.core.servlet
 * Project name:  Core - Base
 * Date Created:  Apr 25, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.core.servlet;

// MJS imports
import mjs.core.model.ParameterList;

/**
 * The response object that contains the data to use
 * when generating the HTML page.
 * 
 * @author mshoemake
 */
public class ServletControllerResponse 
{
   /**
    * The XML data to use when generating the HTML page. 
    */
   private String xmlResult = null;
   
   /**
    * The list of parameters if any.
    */
   private ParameterList params = new ParameterList();
   
   /**
    * The type of response from the ServletController.  This
    * can be HTML, XML DATA, etc.  This tells the view what
    * to do with the response.
    */
   private ServletControllerResponseType type = null;
    
   /**
    * Constructor. 
    */
   public ServletControllerResponse(ServletConfig config, String xmlData, ServletControllerResponseType type)
   {
      super();
      xmlResult = xmlData;
      this.type = type;
   }

   
   /**
    * The XML data to use when generating the HTML page. 
    */
   public String getXMLData()
   {
      return xmlResult;
   }
   
   /**
    * The list of parameters if any.
    */
   public ParameterList getParameterList()
   {
      return params;
   }

   /**
    * The type of response from the ServletController.  This
    * can be HTML, XML DATA, etc.  This tells the view what
    * to do with the response.
    */
   public ServletControllerResponseType getResponseType()
   {
      return type;
   }
      
}
