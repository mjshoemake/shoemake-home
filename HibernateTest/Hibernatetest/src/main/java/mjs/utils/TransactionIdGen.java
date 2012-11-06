package mjs.utils;

//import java.util.Date;
import java.util.UUID;

/**
 * This class generates a unique transaction ID.  Previous versions
 * of this class required a synchronized method call to nextVal() but
 * this is no longer the case.  It was a requirement that we be able
 * to use this class from multiple instances of the JVM.  Each groovy
 * script runs in it's own JVM so synchronized method calls don't work
 * in that case.  Instead, we're using the UUID class to guarantee
 * uniqueness from multiple JVMs.
 * 
 */
public class TransactionIdGen {
    /**
     * Generate the next unique transaction ID value and
     * append the default prefix of "FPG".  If a specific
     * prefix is needed then use the alternate syntax
     * with the prefix parameter.
     */
    public static String nextVal() {
        return nextVal("MJS");
    }    
    
    /**
     * Generate the next unique transaction ID value and
     * append the specified prefix.
     */
    public static String nextVal(String prefix) {
        UUID uuid = UUID.randomUUID();
        return prefix + uuid.toString();
    }    
}
