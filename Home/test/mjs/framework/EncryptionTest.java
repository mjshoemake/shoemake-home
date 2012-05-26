package mjs.framework;

import org.junit.Before;
import org.junit.Test;

import mjs.core.AbstractTest;
import mjs.crypto.EncryptionManager;
import mjs.crypto.Encryptor;
import mjs.crypto.JCEStringEncrypter;

public class EncryptionTest extends AbstractTest {

    @Before
    public void setUp() throws Exception {
//        setUpEnvironment();
    }

    /**
     * Test method.
     */
    @Test
    public void testConnect() {

        try {
         Encryptor encryptor = null;
         String result = null;
        	//encryptor = EncryptionManager.getInstance(EncryptionManager.DESEDE_ALGORITHM);
        	//result = encryptor.encrypt("password");
        	//System.out.println("DESEDE Result: " + result);
        	//result = encryptor.encrypt("qwe`123");
        	//System.out.println("DESEDE MJS Result: " + result);
        	//result = encryptor.encrypt("ichthus12");
        	//System.out.println("DESEDE MRS Result: " + result);
        	
         encryptor = EncryptionManager.getInstance(EncryptionManager.DES_ALGORITHM);
         result = encryptor.encrypt("password");
         System.out.println("DES Default Password Result: " + result);
         result = encryptor.encrypt("qwe`123");
         System.out.println("DES MJS Result: " + result);
         result = encryptor.encrypt("ichthus12");
         System.out.println("DES MRS Result: " + result);

         System.out.println("Java Key to ASCII: " + decompose(JCEStringEncrypter.DEFAULT_ENCRYPTION_KEY));
         
        	System.out.println("Test complete.  Exiting.");

        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }

    public static String decompose(String s) throws Exception {
        byte[] bytes = s.getBytes("UTF8");
        System.out.println("s.length()=" + s.length() + "  bytes.length=" + bytes.length);
        return new String(bytes, "US-ASCII");
       //return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+",""); 
    }
    
    public static String utf8ToAscii(String s) {
       String value = null;
       
       
       return value;
       
    }
}
