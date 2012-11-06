package mjs.exceptions;

/**
 * An InvalidSessionException is a subclass of 
 * FemtoException.
 */
public class ServerException extends mjs.exceptions.CoreException {
    
    static final long serialVersionUID = -4174504602386548113L;

    /**
     * Constructor.
     * 
     * @param s
     */
    public ServerException(String s) {
        super(s);
    }

    /**
     * Constructor.
     * 
     * @param s
     * @param e
     */
    public ServerException(String s, java.lang.Exception e) {
        super(s, e);
    }

}
