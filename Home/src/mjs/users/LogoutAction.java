package mjs.users;

//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.exceptions.ActionException;
import mjs.utils.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends AbstractAction {

   /**
    * Execute this action.
    * 
    * @param mapping
    * @param form
    * @param req
    *           Description of Parameter
    * @param res
    *           Description of Parameter
    * @return ActionForward
    * @exception Exception
    *               Description of Exception
    */
   public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
      metrics.startEvent("Logout", "action");
      try {
         req.getSession().removeAttribute(Constants.ATT_USER_ID);
         return (mapping.findForward("success"));
      }
      catch (java.lang.Exception e) {
         ActionException ex = new ActionException("Error trying to logout.", e);
         throw ex;
      }
      finally {
         metrics.endEvent("Logout", "action");
         metrics.writeMetricsToLog();
      }
   }

}
