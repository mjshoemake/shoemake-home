package mjs.mocks;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MockActionMapping extends ActionMapping {

	public ActionForward findForward(String forwardName) {
        ActionForward forward = new MockActionForward(forwardName);
		return forward;
	}
}
