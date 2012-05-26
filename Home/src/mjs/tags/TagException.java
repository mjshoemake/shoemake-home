package mjs.tags;

/**
 * A TagException is an CoreException used specifically for the 
 * custom tags.
 */
public class TagException extends mjs.exceptions.CoreException {

    static final long serialVersionUID = -4174504602386548113L;

    /**
     * Constructor.
     * 
     * @param s
     */
    public TagException(String s) {
        super(s);
    }

    /**
     * Constructor.
     * 
     * @param s
     * @param e
     */
    public TagException(String s, java.lang.Exception e) {
        super(s, e);
    }

}
