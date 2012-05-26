/* File name:     EmptyServletResponseException.java
 * Package name:  mjs.core.exceptions.servlet
 * Project name:  Core - Base
 * Date Created:  May 1, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.model.exceptions;

// MJS imports
import mjs.core.exceptions.servlet.AbstractServletException;
import mjs.core.servlet.ServletConfig;

/**
 * The exception that signifies an empty servlet response to 
 * the user.
 * 
 * @author mshoemake
 */
public class DatabaseInsertException extends AbstractServletException
{

   /**
    * Constructor.
    * 
    * @param config
    * @param details
    * @param exception
    */
   public DatabaseInsertException(ServletConfig config,
                                  String details,
                                  Exception exception)
   {
      super(10003, config, details, exception);
   }

}
