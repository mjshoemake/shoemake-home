package mjs.exceptions;

/**
 * An Xml Parser Exception.
 */
public class XmlParserException extends mjs.exceptions.CoreException {
    
    static final long serialVersionUID = -4174504602386548113L;

    /**
     * Constructor.
     * 
     * @param s
     */
    public XmlParserException(String s) {
        super(s);
    }

    /**
     * Constructor.
     * 
     * @param s
     * @param e
     */
    public XmlParserException(String s, java.lang.Exception e) {
        super(s, e);
    }

}
