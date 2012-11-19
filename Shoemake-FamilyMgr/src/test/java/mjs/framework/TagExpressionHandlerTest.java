package mjs.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import mjs.core.AbstractLoggableTest;
import mjs.tags.TagExpressionHandler;
import mjs.mocks.MockHttpServletRequest;
import mjs.mocks.MockHttpServletResponse;
import mjs.mocks.MockPageContext;
import mjs.mocks.MockServletConfig;


/**
 * Unit test.
 */
public class TagExpressionHandlerTest extends AbstractLoggableTest
{

   public final void testTagExpressionHandler()
   {
      //String formatted = null;
      String result = null;

         try
         {
            // Create session, etc.
            //setup();

            // Create the request and response objects.
            HttpServletRequest req = (HttpServletRequest)new MockHttpServletRequest();
            HttpServletResponse res = (HttpServletResponse)new MockHttpServletResponse();
            HttpSession session = req.getSession();
            MockServletConfig servletConfig = new MockServletConfig();
            PageContext page = new MockPageContext(session, req, res, servletConfig, servletConfig.getServletContext());

            page.setAttribute("pageApple", "Apple");
            page.setAttribute("page", page);
            req.setAttribute("requestPlum", "Plum");
            req.setAttribute("request", req);
            req.getSession().setAttribute("sessionOrange", "Orange");
            req.getSession().setAttribute("session", req.getSession());
            req.setAttribute("statusMessage", "Hello");

            result = TagExpressionHandler.evaluateExpression(page, "${sessionOrange}").toString();
            assertTrue("Value of sessionOrange attribute should be 'Orange'.", result.equals("Orange"));

            result = TagExpressionHandler.evaluateExpression(page, "${request.session.maxInactiveInterval}").toString();
            assertTrue("Value of maxInactiveInterval should equal '10000' (" + result + ").", result.equals("10000"));

            result = TagExpressionHandler.evaluateExpression(page, "${request.session.maxInactiveInterval == 10000}").toString();
            assertTrue("Value of maxInactiveInterval == 10000 should return true.", result.equals("true"));

            result = TagExpressionHandler.evaluateExpression(page, "${request.session.maxInactiveInterval < 5000}").toString();
            assertTrue("Value of maxInactiveInterval < 5000 should return false.", result.equals("false"));

            result = TagExpressionHandler.evaluateExpression(page, "${request.session.maxInactiveInterval >= 9999}").toString();
            assertTrue("Value of maxInactiveInterval >= 9999 should return true.", result.equals("true"));

            result = TagExpressionHandler.evaluateExpression(page, "${requestScope.errorMessage != null}").toString();
            assertTrue("Value of requestScope.errorMessage != null should return false.", result.equals("false"));

            result = TagExpressionHandler.evaluateExpression(page, "${requestScope.statusMessage != null}").toString();
            assertTrue("Value of requestScope.statusMessage != null should return true.", result.equals("true"));

            result = TagExpressionHandler.evaluateExpression(page, "${requestScope.statusMessage == 'Hello'}").toString();
            assertTrue("Value of requestScope.statusMessage == 'Hello' should return true.", result.equals("true"));

            result = TagExpressionHandler.evaluateExpression(page, "${requestScope.statusMessage == 'Not Hello'}").toString();
            assertTrue("Value of requestScope.statusMessage == 'Not Hello' should return false.", result.equals("false"));

            result = TagExpressionHandler.evaluateExpression(page, "${requestScope.statusMessage != 'Hello'}").toString();
            assertTrue("Value of requestScope.statusMessage != 'Hello' should return false.", result.equals("false"));

            result = TagExpressionHandler.evaluateExpression(page, "${requestScope.statusMessage != 'Not Hello'}").toString();
            assertTrue("Value of requestScope.statusMessage != 'Not Hello' should return true.", result.equals("true"));

         }
         catch (Exception e)
         {
        	e.printStackTrace();
        	assertFailed("Parsing tag expressions.");
         }
   }

}
