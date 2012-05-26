package mjs.utils;

public interface Constants {

	// Properties
    public static final String PROP_TEST_ENVIRONMENT = "test.environment";
	
    public static final String TYPE_INTEGER = "java.lang.Integer";
    public static final String TYPE_BOOLEAN = "java.lang.Boolean";
    public static final String TYPE_FLOAT = "java.lang.Float";
    public static final String TYPE_STRING = "java.lang.String";
    public static final String TYPE_LONG = "java.lang.Long";
    public static final String TYPE_XSD_INTEGER = "xsd:integer";
    public static final String TYPE_XSD_BOOLEAN = "xsd:boolean";
    public static final String TYPE_XSD_FLOAT = "xsd:float";
    public static final String TYPE_XSD_STRING = "xsd:string";
    public static final String TYPE_XSD_LONG = "xsd:long";
	
	// Request/Session Attribute Names
    //public static final String ATT_INITIAL_PAGE = "startpage";
    //public static final String ATT_INITIAL_TOP_NAV = "initialTopSel";
    //public static final String ATT_INITIAL_SUB_NAV = "initialSubSel";
    //public static final String ATT_LEFTNAV = "leftnav";
    //public static final String ATT_TOPNAV = "topnav";
    //public static final String ATT_PREV_LOGIN = "prevlogin";
    //public static final String ATT_ROLENAME = "rolename";
    public static final String ATT_SEARCH_CRITERIA = "SearchCriteria";
    public static final String ATT_USER_ID = "userID";
    public static final String ATT_STATUS_MSG = "statusmsg";
    public static final String ATT_BREADCRUMBS = "breadcrumbs";
    //public static final String ATT_ALLOWED_FEATURES = "allowedFeatures";
    public static final String ATT_PAGINATED_LIST_CACHE = "paginatedListCache";
    public static final String ATT_LETTER = "letter";
	
    // Request Parameter Names
    public static final String PARAM_ID = "id";
    
	// Action Forwards
    //public static final String FWD_CHANGE_PASSWORD = "/ChangePassword";
    //public static final String FWD_LOGIN = "/LoginPage";
    //public static final String FWD_RETRY_LOGIN = "/RetryLogin";
	public static final String FWD_SUCCESS = "success";
	public static final String FWD_VALIDATION_ERROR = "invalid";
    public static final String FWD_SESSION_TIMEOUT = "/SessionTimeout";
	
}
