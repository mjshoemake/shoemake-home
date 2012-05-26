package mjs.exceptions;

public class CoreException extends Exception {

    static final long serialVersionUID = -4174504602386548113L;
    
	public CoreException() {
	}

	public CoreException(String message) {
		super(message);
	}

	public CoreException(Throwable cause) {
		super(cause);
	}

	public CoreException(String message, Throwable cause) {
		super(message, cause);
	}

}
