package mjs.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is intended to be used with the <code>Runtime</code> 
 * <code>Process</code> input and error streams.  This class takes
 * each of the streams and once started reads line by line from the streams.
 * Once the Process waitFor() returns each of the StreamGobblers data that
 * was read can be obtained by calling the getReadData().
 */
public class StreamGobbler extends Thread {

    private StreamGobblerRunnable runnable = null;
    
	/**
	 * Method returns true when the gobbler has completed.
	 * @return true went complete else false.
	 */
	public boolean isDone() {
        if (runnable != null)
            return runnable.isDone();
        else
            return false;
	}
	
	/**
	 * Method returns a List of String data the gobbler has read.
	 * 
	 * @return list of String data the gobbler has read
	 */
	public List<String> getReadData() {
		if (runnable != null)
		    return runnable.getReadData();
		else
		    return new ArrayList<String>();
	}
	
	/**
	 * Method returns the type of gobbler that has been set.
	 * 
	 * @return gobbler type
	 */
	//public String getType() {
	//	return type.toString();
	//}
	
	
	/**
	 * Constructor that takes an input stream, gobbler type and 
	 * output stream
	 * 
	 * @param is
	 * 			InputStream that reads Strings.
	 * @param type
	 * 			StreamGobblerType this is
	 * @param os
	 * 			OutputStream that Strings can be output to
	 */
	public StreamGobbler(StreamGobblerRunnable runnable) {
	    
	    super(runnable);
	    this.runnable = runnable;
	}

}

