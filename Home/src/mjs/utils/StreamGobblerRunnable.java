package mjs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is intended to be used with the <code>Runtime</code> 
 * <code>Process</code> input and error streams. This class
 * takes each of the streams and once started reads line by line from the streams. Once the Process waitFor() returns each of the
 * StreamGobblers data that was read can be obtained by calling the getReadData().
 */
public class StreamGobblerRunnable implements Runnable {

    /**
     * InputStream to read
     */
    private InputStream is = null;

    /**
     * OutputStream usually a FileOutputStream to steam the data to
     */
    private OutputStream os = null;

    /**
     * List that contains the data read on the stream
     */
    private List<String> dataRead = new ArrayList<String>();

    /**
     * sentinel to tell when the gobbler has completed
     */
    private boolean done = false;

    /**
     * Constructor that takes an input stream, gobbler type and output stream
     * 
     * @param is InputStream that reads Strings.
     * @param type StreamGobblerType this is
     * @param os OutputStream that Strings can be output to
     */
    public StreamGobblerRunnable(InputStream is) {
        this.is = is;
    }

    /**
     * Constructor that takes an input stream, gobbler type and output stream
     * 
     * @param is InputStream that reads Strings.
     * @param type StreamGobblerType this is
     * @param os OutputStream that Strings can be output to
     */
    public StreamGobblerRunnable(InputStream is, OutputStream os) {
        this.is = is;
        this.os = os;
    }

    /**
     * Method returns true when the gobbler has completed.
     * @return true went complete else false.
     */
    public boolean isDone() {
        return done;
    }
    
    /**
     * Method returns a List of String data the gobbler has read.
     * 
     * @return list of String data the gobbler has read
     */
    public List<String> getReadData() {
        return dataRead;
    }
    
    /**
     * Method reads all the String data line by line that the InputStream has and puts it into a List line by line and if an
     * output stream is provided will output to that stream line by line.
     */
    public void run() {
        try {
            PrintWriter pw = null;
            if (os != null) {
                pw = new PrintWriter(os);
            }
            /**
             * Constructor that takes an input stream and gobbler type.
             * 
             * @param is InputStream that reads Strings.
             * @param type StreamGobblerType this is
             */

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line = null;
            while ((line = br.readLine()) != null) {
                if (pw != null) {
                    pw.println(line);
                }
                dataRead.add(line);
            }

            if (pw != null) {
                pw.flush();
            }

            done = true;

        } catch (IOException ioe) {
            /* replace with log statement */
            ioe.printStackTrace();
        }
    }

}
