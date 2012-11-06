package mjs.utils;

import java.io.File;
import java.io.BufferedReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import mjs.exceptions.CoreException;

/**
 * This class provides synchronized read access to an input file.
 */
public class FileReader {

    /**
     * The file object.
     */
    private File file = null;

    /**
     * The FileReader object from the JDK.
     */
    private java.io.FileReader fileReader = null;
    
    /**
     * The reader used to read the file contents from the input stream.
     */
    private BufferedReader reader = null;

    /**
     * The most recent line read from the file.
     */
    private String nextLine = null;

    /**
     * Lock to synchronize loading of the config file.
     */
    private static Lock lock = new ReentrantLock();
    
    /**
     * Synchronize reading of the file so this FileReader
     * can be shared between multiple threads.
     */
    private boolean synchronizedReads = false;
    
    
    /**
     * Constructor.
     * @param filePath String
     */
    public FileReader(String filePath) throws Exception {
        
        if (filePath == null)
            throw new CoreException("No input file specified.");
        
        // If only filename specified then prepend current working
        // directory.
        String workingDirectory = System.getProperty("user.dir") + "/";
        if (! (filePath.contains("/") || filePath.contains("\\"))) {
            // Path not included in filename.  Adding working directory.
            filePath = workingDirectory + filePath;
        }
        filePath = FileUtils.forceAbsoluteFilePath(filePath);
        
        // Prepare reader objects.
        file = new File(filePath);
        fileReader = new java.io.FileReader(file);
        reader = new BufferedReader(fileReader);
    }

    /**
     * 
     * 
     * @return boolean - true if no more data to read, false if more data found.
     * @throws Exception
     */
    public boolean eof() throws Exception {
        if (! synchronizedReads) {
            if (nextLine != null)
                throw new CoreException("Cannot check for end of file while there is still data in the input buffer.  Call readLine() first.");
            
            nextLine = reader.readLine();
            if (nextLine == null)
                return true;
            else
                return false;
        } else {
            boolean result = false;

            lock.lock();
            if (nextLine == null) {
                nextLine = reader.readLine();
                if (nextLine == null)
                    result = true;
                else
                    result = false;
            } else 
                result = false;
            lock.unlock();
            return result;
        }
    }
    
    /**   
     * Read the next line from the input file.
     * 
     * @return String
     * @throws Exception
     */
    public String readLine() throws Exception {
        if (! synchronizedReads) {
            if (nextLine == null)
                throw new CoreException("No data to read.  Either end of file found or eof() has not been called yet.");

            String result = nextLine;
            nextLine = null;
            return result;
        } else {
            String result = null;
            lock.lock();
            if (nextLine == null) {
                nextLine = reader.readLine();
                if (nextLine == null)
                    result = null;
                else {
                    result = nextLine;
                    nextLine = reader.readLine();
                }    
            } else {
                result = nextLine;
                nextLine = reader.readLine();
            }    
            lock.unlock();
            return result;
        }
    }
       
    /**
     * Close the input file and release the associated resources.
     * 
     * @throws Exception
     */
    public void close() throws Exception{
        
        try {
            reader.close();
        } catch(Exception e) {
            // Attemto to close.  If fails, ignore it.
        }
        try {
            fileReader.close();
        } catch(Exception e) {
            // Attemto to close.  If fails, ignore it.
        }
    }

    /**
     * Synchronize reading of the file so this FileReader
     * can be shared between multiple threads.
     */
    public boolean isSynchronizedReadsEnabled() {
        return synchronizedReads;
    }

    /**
     * Synchronize reading of the file so this FileReader
     * can be shared between multiple threads.
     */
    public void setSynchronizedReadsEnabled(boolean value) {
        synchronizedReads = value;
    }
    
}
