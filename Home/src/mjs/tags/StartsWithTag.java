package mjs.tags;

/**
 * Tag used to determine whether a string starts with another string.
 */
public class StartsWithTag extends AbstractTag {

    static final long serialVersionUID = -4174504602386548113L;

    /**
     * The test variable. This string will be tested to see if it 
     * starts with the "startsWith" string.
     */
    private String testVar = null;

    /**
     * This is the string that the "value" string should start with. 
     * If so, the var is set to true. Otherwise, false.
     */
    private String startsWith = null;

    /**
     * The variable name to use to return the result (true/false).
     */
    private String var = null;

    /**
     * Constructor.
     */
    public StartsWithTag() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param testVar_ Description of Parameter
     * @param startsWith_ Description of Parameter
     * @param var_ Description of Parameter
     */
    public StartsWithTag(String testVar_, String startsWith_, String var_) {
        super();
        setTestVar(testVar_);
        setStartsWith(startsWith_);
        setVar(var_);
    }

    /**
     * The test string. This string will be tested to see if it starts with the "startsWith" string.
     * 
     * @return The value of the TestVar property.
     */
    public String getTestVar() {
        return testVar;
    }

    /**
     * The test string. This string will be tested to see if it starts with the "startsWith" string.
     * 
     * @param value The new TestVar value.
     */
    public void setTestVar(String value) {
        testVar = value;
    }

    /**
     * This is the string that the "value" string should start with. If so, the var is set to true. Otherwise, false.
     * 
     * @return The value of the StartsWith property.
     */
    public String getStartsWith() {
        return startsWith;
    }

    /**
     * This is the string that the "value" string should start with. If so, the var is set to true. Otherwise, false.
     * 
     * @param value The new StartsWith value.
     */
    public void setStartsWith(String value) {
        startsWith = value;
    }

    /**
     * The variable name to use to return the result (true/false).
     * 
     * @return The value of the Var property.
     */
    public String getVar() {
        return var;
    }

    /**
     * The variable name to use to return the result (true/false).
     * 
     * @param value The new Var value.
     */
    public void setVar(String value) {
        var = value;
    }

    /**
     * Execute.
     * 
     * @param tag
     * @return Description of Return Value
     */
    public int output(AbstractTag tag) {
        String value = null;

        try {
            Object obj = TagExpressionHandler.evaluateExpression(pageContext, testVar);
            if (obj != null && ! (obj instanceof String))
                throw new TagException("Value at " + testVar + " is not a String object (" + obj.getClass().getName() + ").");                
            
            value = obj.toString();
            if (value != null) {
                if (value.startsWith(startsWith))
                    pageContext.setAttribute(var, new Boolean(true));
                else
                    pageContext.setAttribute(var, new Boolean(false));
            } else
                pageContext.setAttribute(var, new Boolean(false));

            log.debug("Checking to see if '" + value + "' starts with '" + startsWith
                + "'... Result is " + pageContext.getAttribute(var));
        } catch (java.lang.Exception e) {
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

}
