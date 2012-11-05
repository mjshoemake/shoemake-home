package mjs.crypto;

/**
 * Base interface for all encryption/decryption algorithms used
 *
 * @author T. McCauley
 * @version 1.0
 */
public interface Encryptor {
    /**
     * Method for encrypting an object (Java class, a String, etc) into an
     * encrypted form
     */
    public String encrypt(String objToEncrypt) throws EncryptionException;

    /**
     * Method for decrypting an encrypted object into it's normal form
     */
    public String decrypt(String encryptedObj) throws EncryptionException;
}