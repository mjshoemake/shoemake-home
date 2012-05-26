package mjs.tags;

import org.apache.taglibs.standard.tag.rt.core.UrlTag;

/**
 * Tag used to display navigation options in the
 * Femto UI framework.  This tag uses the JSTL
 * url tag to encode the URL and ensure that the
 * context is correct.
 */
public abstract class AbstractNavTag extends AbstractTag {
    
    static final long serialVersionUID = -4174504602386548113L;

    /**
     * Constructor.
     */
    public AbstractNavTag() {
        super();
    }

    /**
     * Encode the URL using JSTL UrlTag.
     * @param url String
     * @return String
     */
    protected String encodeUrl(String url, String context) {
        StringBuffer result = new StringBuffer();
        try {
            if (url != null && ! url.equals(""))
                result.append(UrlTag.resolveUrl(url, context, this.pageContext));
            else
                result.append(url);
            String sessionId = req.getSession().getId();
            if (sessionId != null && ! url.contains("jsessionid"))
                result.append(";jsessionid=" + sessionId); 
        } catch (Exception e) {
            log.error("Error encoding URL " + url + " using JSTL UrlTag.  Returning initial URL.", e);
            return url;
        }
        
        return result.toString();
    }
    
}
