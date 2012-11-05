package mjs.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * This is a data bean used to encapsulate the two output streams
 * from an executable execution.  This is used by FileUtils to
 * return the two output streams back to the caller.
 */
public class OutputStreams {

    /**
     * The output results as a List of String objects.
     */
    List<String> outData = new ArrayList<String>();
    
    /**
     * The error results as a List of String objects.
     */
    List<String> errData = new ArrayList<String>();
    
    
    /**
     * Constructor.
     * 
     * @param outData List<String>
     * @param errData List<String>
     */
    public OutputStreams(List<String> outData, List<String> errData) {
        this.outData = outData;
        this.errData = errData;
    }
   
    
    /**
     * The output results as a List of String objects.
     */
    public List<String> getOutData() {
        return outData;
    }
    
    /**
     * The error results as a List of String objects.
     */
    public List<String> getErrorData() {
        return errData;
    }
    
}
