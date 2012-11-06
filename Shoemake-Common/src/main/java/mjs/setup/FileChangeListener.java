package mjs.setup;

/**
 * This interface should be implemented by all classes wishing to be notified
 * when a file's last modified date changes.
 */
public interface FileChangeListener {
    
    /**
     * This method should be invoked when a file changes (last modified date 
     * changes).   
     * 
     * @param fileThatChanged name of the file that changed.
     */
    public void fileChanged(String fileThatChanged);
}
