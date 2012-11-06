package mjs.users;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.DynaForm;
import mjs.core.AbstractAction;
import mjs.database.PaginatedList;
import mjs.database.TableDataManager;
import mjs.exceptions.ActionException;
import mjs.mybatis.UserMapper;
import mjs.utils.Constants;
import mjs.utils.SingletonInstanceManager;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


@SuppressWarnings("rawtypes")
public class GetFamilyMemberListAction extends AbstractAction {

    public static final String GLOBAL_FORWARD = "/GetFamilyMemberList";

    /**
     * Execute this action.
     * 
     * @param mapping
     * @param form
     * @param req Description of Parameter
     * @param res Description of Parameter
     * @return ActionForward
     * @exception Exception Description of Exception
     */
    public ActionForward processRequest(ActionMapping mapping,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res) throws Exception {
        metrics.startEvent("GetFamilyMemberList", "action");

        try {
            TableDataManager dbMgr = getTable("FamilyMemberMapping.xml");
            clearBreadcrumbs(req);
            addBreadcrumbs(req, "Family Members", "../GetFamilyMemberList.do");

            boolean listFound = false;
            PaginatedList list = (PaginatedList) req.getSession()
                .getAttribute(Constants.ATT_PAGINATED_LIST_CACHE);
            if (list != null && list.getGlobalForward().equals(GLOBAL_FORWARD)) {
                listFound = true;
            }
            log.debug("Matching PaginatedList retrieved from cache: " + listFound);

            // Have changes occured in the data (updates, adds, etc.) that
            // require a reload of the data in the list?
            String listDirty = (String) req.getSession()
                .getAttribute(Constants.ATT_PAGINATED_LIST_DIRTY);
            boolean paginatedListDirty = false;
            if (listDirty != null) {
                paginatedListDirty = Boolean.parseBoolean(listDirty);
            }

            log.debug("listFound=" + listFound);
            log.debug("listDirty=" + paginatedListDirty);
            if (!listFound || paginatedListDirty) {
                // Get all family members.
                try {
                    // 
                    //String whereClause = " order by lname, fname";
                    //dbMgr.open();
                    //list = dbMgr.loadList(whereClause, 30,450, GLOBAL_FORWARD);
                    //dbMgr.close();
                    
                    // Mybatis DB implementation
                    SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
                    Object factory = mgr.getInstanceForKey(SqlSessionFactory.class.getName());
                    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)factory;
                    SqlSession session = sqlSessionFactory.openSession();
                    try {
                        UserMapper mapper = session.getMapper(UserMapper.class);
                        List<HashMap> members = mapper.getFamilyMemberList();
                        list = new PaginatedList(DynaForm.class, 30, 450, GLOBAL_FORWARD);
                        list.appendList(members);
                    } finally {
                        session.close();
                    }
                    
                    req.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_CACHE, list);
                    req.getSession().removeAttribute(Constants.ATT_PAGINATED_LIST_DIRTY);
                } catch (Exception e) {
                    dbMgr.close(false);
                    throw e;
                }
            } else {
                log.debug("Existing list is ok.  Don't load the list.");
            }

            return (mapping.findForward("success"));
        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException("Error trying to get the list of family members.", e);
            throw ex;
        } finally {
            metrics.endEvent("GetFamilyMemberList", "action");
            metrics.writeMetricsToLog();
        }
    }

}
