package mjs.utils;

import java.io.PrintWriter;
import java.util.List;

import mjs.exceptions.CoreException;

/**
 * This is a helper class to make it easy to write output files for groovy
 * scripts.
 */
public class FileWriter {

    /**
     * The FileWriter object. 
     */
    private java.io.FileWriter outFile = null;
    
    /**
     * Flush after each println (true/false)?
     */
    private boolean flushEachLineEnabled = false;
    
    /**
     * Synchronize writing to the file so this FileWriter
     * can be shared between multiple threads.
     */
    private boolean synchronizedWrites = false;
    
    /**
     * The PrintWriter object used to format the file.
     */
    PrintWriter out = null;

    /**
     * Constructor.  Append to File defaults to false.
     * @param filePath String
     */
    public FileWriter(String filePath) throws Exception {
        this(filePath, false);
    }

    /**
     * Constructor.
     * @param filePath String
     * @param overwriteFile boolean
     */
    public FileWriter(String filePath, boolean overwriteFile) throws Exception {
        
    	boolean appendFile = !overwriteFile;
    	
        if (filePath == null)
            throw new CoreException("No output file specified.");
        
        // If only filename specified then prepend current working
        // directory.
        filePath = FileUtils.forceAbsoluteFilePath(filePath);
        
        // Prepare reader objects.
        outFile = new java.io.FileWriter(filePath, appendFile);
        out = new PrintWriter(outFile);
    }

    /**   
     * Write the next line to the output file.
     * 
     * @throws Exception
     */
    public void println(String line) throws Exception {

        if (synchronizedWrites) {
            syncPrintln(line);            
        } else if (line != null) {
            out.println(line);
            if (flushEachLineEnabled)
                out.flush(); // send it out to the file.
        }
    }
       
    /**   
     * Write the next line to the output file.
     * 
     * @throws Exception
     */
    public synchronized void syncPrintln(String line) throws Exception {

        if (line != null) {
            out.println(line);
            if (flushEachLineEnabled)
                out.flush(); // send it out to the file.
        }
    }
       
    /**   
     * Write the list of Strings to the output file.
     * 
     * @throws Exception
     */
    public void println(List<String> lines) throws Exception {

        if (synchronizedWrites) {
            syncPrintln(lines);            
        } else {
            if (lines != null) {
                int cnt = 0;
                for(String s : lines) {           
                    out.println(s);

                    if (flushEachLineEnabled) {
                        cnt++;
                        if(cnt > 25) {
                            out.flush();
                            cnt = 0;
                        }
                    }
                }
                if (flushEachLineEnabled)
                    out.flush(); // send it out to the file.
            } else {
                throw new CoreException("List of String objects to print is null.");
            }
        }
    }
       
    /**   
     * Write the list of Strings to the output file.
     * 
     * @throws Exception
     */
    private synchronized void syncPrintln(List<String> lines) throws Exception {

        if (lines != null) {
            int cnt = 0;
            for(String s : lines) {           
                out.println(s);

                if (flushEachLineEnabled) {
                    cnt++;
                    if(cnt > 25) {
                        out.flush();
                        cnt = 0;
                    }
                }
            }
            if (flushEachLineEnabled)
                out.flush(); // send it out to the file.
        } else {
            throw new CoreException("List of String objects to print is null.");
        }
    }
       
    /**
     * Close the output file and release the associated resources.
     * 
     * @throws Exception
     */
    public void close() {

        try {
            outFile.close();
        } catch(Exception e) {
            // Attemto to close.  If fails, ignore it.
        }
        try {
            out.close();
        } catch(Exception e) {
            // Attemto to close.  If fails, ignore it.
        }
    }

    /**
     * Flush after each println (true/false)?
     */
    public boolean isFlushEachLineEnabled() {
        return flushEachLineEnabled;
    }
    
    /**
     * Flush after each println (true/false)?
     */
    public void setFlushEachLineEnabled(boolean value) {
        flushEachLineEnabled = value;
    }
    
    /**
     * Synchronize writing to the file so this FileWriter
     * can be shared between multiple threads.
     */
    public boolean isSynchronizedWritesEnabled() {
        return synchronizedWrites;
    }

    /**
     * Synchronize writing to the file so this FileWriter
     * can be shared between multiple threads.
     */
    public void setSynchronizedWritesEnabled(boolean value) {
        synchronizedWrites = value;
    }
}
