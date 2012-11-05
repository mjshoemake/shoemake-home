package mjs.crypto;

/**
 * Factory class for encryption mechanisms.  This class will return an instance
 * of the default encryption algorithm, which will implement the <code>
 * Encryptor</code> interface.
 *
 * @author T. McCauley
 * @version 1.0
 *
 * @see Encryptor
 */
public class EncryptionManager {
    
    public static final int DES_ALGORITHM = 1;
    public static final int DESEDE_ALGORITHM = 2;
    public static final int SHIFT_ALGORITHM = 3;
    
    /**
     * Factory method to get an instance of the default encryption algorithm
     *
     * @return an instance of the default encryption algorithm, which will
     *         implement the encryptor interface
     */
    public static synchronized Encryptor getInstance() {
        return new ScicareEncryptorImpl();
    }

    /**
     * Factory method to get an instance of either 
     *     DES
     *     DESede
     *     Shift
     * Encrypter with String encrypt/decrypt.  If the type
     * is not known the SHIFT algorithm will be used.
     * 
     * @param type type of encrypter to create either
     *     EncryptionManager.DES_ALGORITHM
     *     EncryptionManager.DESEDE_ALGORITHM
     *     EncryptionManager.SHIFT_ALGORITHM
     * @return Encryptor with the give encryption algorithm
     * @throws EncryptionException
     */
    public static synchronized Encryptor getInstance(int type) throws EncryptionException {
        
        Encryptor encrypter;
        
        switch(type) {
            case 1:
                encrypter = new JCEStringEncrypter(JCEStringEncrypter.DES_ENCRYPTION_SCHEME);
                break;
            case 2:
                encrypter = new JCEStringEncrypter(JCEStringEncrypter.DESEDE_ENCRYPTION_SCHEME);
                break;
            default:
                encrypter = new ScicareEncryptorImpl();
        }
        return encrypter;
        
    }
}