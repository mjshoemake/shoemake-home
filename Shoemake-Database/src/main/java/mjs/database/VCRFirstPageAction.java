
package mjs.database;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.exceptions.ActionException;
import mjs.utils.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class VCRFirstPageAction extends AbstractAction
{
   /**
    * Execute this action.
    *
    * @param mapping
    * @param form
    * @param req                Description of Parameter
    * @param res                Description of Parameter
    * @return                   ActionForward
    * @exception Exception      Description of Exception
    */
   public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception
   {
      try
      {
         Object obj = req.getSession().getAttribute(Constants.ATT_PAGINATED_LIST_CACHE);
         PaginatedList list = null;
         if (obj instanceof PaginatedList) {
            list = (PaginatedList)obj;
            list.firstPage();
         }
         return (mapping.findForward(list.getGlobalForward()));
      } catch (java.lang.Exception e) {
         ActionException ex = new ActionException("Error trying to create a new meal.", e);
         log.error(e.getMessage(), e);
         return error(mapping, req, ex);
      } 
   }

}
