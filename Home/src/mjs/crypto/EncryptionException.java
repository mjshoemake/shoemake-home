package mjs.crypto;

/**
 * Base exception which when something goes awry in encrypting/decrypting
 * objects
 *
 * @author T. McCauley
 * @version 1.0
 */
public class EncryptionException extends Exception {
    
    static final long serialVersionUID = -1000666872062049246L;    
	/**
	 * @param message
	 */
	public EncryptionException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public EncryptionException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EncryptionException(String message, Throwable cause) {
		super(message, cause);
	}

}
