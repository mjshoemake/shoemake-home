package mjs.hibernate;

/**
 * An HibernateException is a subclass of CoreException.
 */
public class HibernateException extends mjs.exceptions.CoreException {
    
    static final long serialVersionUID = -4174504602386548113L;

    /**
     * Constructor.
     * 
     * @param s
     */
    public HibernateException(String s) {
        super(s);
    }

    /**
     * Constructor.
     * 
     * @param s
     * @param e
     */
    public HibernateException(String s, java.lang.Exception e) {
        super(s, e);
    }

}
