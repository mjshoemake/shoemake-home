/*
 * $Id: LogTag.java 22365 2010-07-20 16:34:01Z mshoemake $
 *
 ***************************************************************************
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, Cisco Systems, Inc.
 ***************************************************************************
 */
package mjs.tags;

import mjs.tags.TagExpressionHandler;
import mjs.utils.LogUtils;
import org.apache.log4j.Logger;

/**
 * Tag used to trace out the contents of an object to the log file.  
 * It uses a JSTL-like TagExpressionHandler to specify the object to 
 * use.  See TagExpressionHandler for examples of usage.
 */
public class LogTag extends AbstractTag {
    
    static final long serialVersionUID = -4174504602386548113L;

    /**
     * This is the object whose properties should be traced out to the log.
     */
    private String traceObject = null;

    /**
     * This is the message to write to the log file.
     */
    private String message = null;

    /**
     * This is the logging priority (debug, info, warn, error, fatal). Defaults to "debug".
     */
    private String priority = "debug";

    /**
     * This is the category used when writing to the trace log. Defaults to "JSP".
     */
    private String category = "JSP";

    /**
     * Constructor.
     */
    public LogTag() {
        super();
    }

    /**
     * This is the object whose properties should be traced out to the log.
     * 
     * @return The value of the TraceObject property.
     */
    public String getTraceObject() {
        return traceObject;
    }

    /**
     * This is the object whose properties should be traced out to the log.
     * 
     * @param value The new TraceObject value.
     */
    public void setTraceObject(String value) {
        traceObject = value;
    }

    /**
     * This is the message to write to the log file.
     * 
     * @return The value of the Message property.
     */
    public String getMessage() {
        return message;
    }

    /**
     * This is the message to write to the log file.
     * 
     * @param value The new Message value.
     */
    public void setMessage(String value) {
        message = value;
    }

    /**
     * This is the logging priority (debug, info, warn, error, fatal). Defaults to "debug".
     * 
     * @return The value of the Priority property.
     */
    public String getPriority() {
        return priority;
    }

    /**
     * This is the logging priority (debug, info, warn, error, fatal). Defaults to "debug".
     * 
     * @param value The new Priority value.
     */
    public void setPriority(String value) {
        priority = value;
    }

    /**
     * This is the category used when writing to the trace log. Defaults to "JSP".
     * 
     * @return The value of the Category property.
     */
    public String getCategory() {
        return category;
    }

    /**
     * This is the category used when writing to the trace log. Defaults to "JSP".
     * 
     * @param value The new Category value.
     */
    public void setCategory(String value) {
        category = value;
    }

    /**
     * Execute.
     * 
     * @param tag
     * @return Description of Return Value
     */
    public int output(AbstractTag tag) {
        Logger log = null;
        String[] lines = null;

        try {
            if (category != null)
                log = Logger.getLogger(category);
            else
                log = Logger.getLogger("JSP");

            if (priority == null)
                priority = "debug";

            Object obj = null;
            String autoMsg = null;

            if (traceObject != null) {
                // Get the object whose properties should be traced.
                obj = TagExpressionHandler.evaluateExpression(pageContext, traceObject);
                if (obj == null)
                    obj = "null";
                if (obj instanceof String) { 
                    lines = new String[1];
                    lines[0] = traceObject + "=" + obj.toString();
                } else if (obj != null) {
                    autoMsg = traceObject + ":";
                    lines = LogUtils.dataToStrings(obj);
                }    
            }

            if (priority.equals("debug") && log.isDebugEnabled()) {
                if (message != null)
                    log.debug(message);
                if (autoMsg != null)
                    log.debug(autoMsg);
                if (obj != null) {
                    for (int C = 0; C <= lines.length-1; C++)
                        log.debug("   " + lines[C]);
                }
            } else if (priority.equals("info") && log.isInfoEnabled()) {
                if (message != null)
                    log.info(message);
                if (autoMsg != null)
                    log.info(autoMsg);
                if (obj != null) {
                    for (int C = 0; C <= lines.length-1; C++)
                        log.info("   " + lines[C]);
                }
            } else if (priority.equals("warn")) {
                if (message != null)
                    log.warn(message);
                if (autoMsg != null)
                    log.warn(autoMsg);
                if (obj != null) {
                    for (int C = 0; C <= lines.length-1; C++)
                        log.warn("   " + lines[C]);
                }
            } else if (priority.equals("error")) {
                if (message != null)
                    log.error(message);
                if (autoMsg != null)
                    log.error(autoMsg);
                if (obj != null) {
                    for (int C = 0; C <= lines.length-1; C++)
                        log.error("   " + lines[C]);
                }
            } else if (priority.equals("fatal")) {
                if (message != null)
                    log.fatal(message);
                if (autoMsg != null)
                    log.fatal(autoMsg);
                if (obj != null) {
                    for (int C = 0; C <= lines.length-1; C++)
                        log.fatal("   " + lines[C]);
                }
            } else if (log.isDebugEnabled()) {
                // Default to debug.
                if (message != null)
                    log.debug(message);
                if (autoMsg != null)
                    log.debug(autoMsg);
                if (obj != null) {
                    for (int C = 0; C <= lines.length-1; C++)
                        log.debug("   " + lines[C]);
                }
            }
        } catch (java.lang.Exception e) {
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

}
