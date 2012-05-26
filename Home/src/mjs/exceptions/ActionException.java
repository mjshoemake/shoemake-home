package mjs.exceptions;

/**
 * An InvalidSessionException is a subclass of 
 * FemtoException.
 */
public class ActionException extends mjs.exceptions.CoreException {
    
    static final long serialVersionUID = -4174504602386548113L;

    /**
     * Constructor.
     * 
     * @param s
     */
    public ActionException(String s) {
        super(s);
    }

    /**
     * Constructor.
     * 
     * @param s
     * @param e
     */
    public ActionException(String s, java.lang.Exception e) {
        super(s, e);
    }

}
