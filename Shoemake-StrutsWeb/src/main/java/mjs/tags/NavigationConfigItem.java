package mjs.tags;

import java.util.ArrayList;

/**
 * The suitcase class that contains the configuration properties
 * for a message processor.  This is used to hold the config info
 * found in the server-config.xml file.
 *  
 * @author mshoemake
 */
@SuppressWarnings("rawtypes")
public class NavigationConfigItem implements Comparable {

    /**
     * Unique ID for each navigation config item.
     */
    private String id = null;
    
    /**
     * The caption of the navigation item.
     */
    private String caption = null;
    
    /**
     * The plugin name associated with this nav item.
     */
    private String pluginName = null;
    
    /**
     * The URL to trigger when the navigation item
     * is selected.
     */
    private String url = null;
    
    /**
     * The selected top nav ID.
     */
    private String topNavID = null;
    
    /**
     * The selected sub navigation ID (the parent
     * nav item for this item).
     */
    private String subNavID = null;
    
    /**
     * The feature associated with this navigation item.
     */
    private String feature = null;
    
    /**
     * The sort order associated with this navigation item.
     */
    private int order = -1;
    
    /**
     * The name of the Msg class to use to handle this
     * message.
     */
    private ArrayList<NavigationConfigItem> children = new ArrayList<NavigationConfigItem>();
    
    /**
     * Default constructor.
     */
    public NavigationConfigItem() {
    }
    
    /**
     * Unique ID for each navigation config item.
     */
    public String getID() {
        return id;
    }
    
    /**
     * Unique ID for each navigation config item.
     */
    public void setID(String value) {
        id = value;
    }
    
    /**
     * The caption of the navigation item.
     */
    public String getCaption() {
        return caption;
    }
    
    /**
     * The caption of the navigation item.
     */
    public void setCaption(String value) {
        caption = value;
    }
    
    /**
     * The URL to trigger when the navigation item
     * is selected.
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * The URL to trigger when the navigation item
     * is selected.
     */
    public void setUrl(String value) {
        url = value;
    }
    
    /**
     * The name of the Msg class to use to handle this
     * message.
     */
    public ArrayList<NavigationConfigItem> getChildren() {
        return children;
    }
    
    /**
     * The name of the Msg class to use to handle this
     * message.
     */
    public void setChildren(ArrayList<NavigationConfigItem> value) {
        children = value;
    }
    
    /**
     * The plugin name associated with this nav item.
     */
    public String getPluginName() {
        return pluginName;
    }
    
    /**
     * The plugin name associated with this nav item.
     */
    public void setPluginName(String value) {
        pluginName = value;
    }
    
    /**
     * The selected top nav ID.
     */
    public String getTopNavID() {
        return topNavID;
    }
    
    /**
     * The selected top nav ID.
     */
    public void setTopNavID(String value) {
        topNavID = value;
    }

    /**
     * The feature associated with this navigation item.
     */
    public String getFeature() {
        return feature;
    }
    
    /**
     * The feature associated with this navigation item.
     */
    public void setFeature(String value) {
        feature = value;
    }

    /**
     * The sort order associated with this navigation item.
     */
    public int getOrder() {
        return order;
    }
    
    /**
     * The sort order associated with this navigation item.
     */
    public void setOrder(int value) {
        order = value;
    }

    /**
     * The selected sub navigation ID (the parent
     * nav item for this item).
     */
    public String getSubNavID() {
        return subNavID;
    }
    
    /**
     * The selected sub navigation ID (the parent
     * nav item for this item).
     */
    public void setSubNavID(String value) {
        subNavID = value;
    }
    
    /**
     * Compare this object with the specified object.
     * @param comparee NavigationConfigItem
     * @return int
     */
    public int compareTo(NavigationConfigItem comparee) {
        Integer integer = new Integer(order);
        return integer.compareTo(new Integer(comparee.getOrder()));
    }
    
    /**
     * Compare this object with the specified object.
     * @param obj Object
     * @return int
     */
    public int compareTo(Object obj) {
        NavigationConfigItem comparee = (NavigationConfigItem)obj;
        Integer integer = new Integer(order);
        return integer.compareTo(new Integer(comparee.getOrder()));
    }
    
}
