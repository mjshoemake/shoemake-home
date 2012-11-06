package mjs.mocks;

import org.apache.struts.action.ActionForward;

public class MockActionForward extends ActionForward {

	private String forwardName = null;
	
	public MockActionForward(String forwardName) {
		this.forwardName = forwardName;
	}
	
	public String getForwardName() {
		return forwardName;
	}
}
