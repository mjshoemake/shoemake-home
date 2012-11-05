package mjs.crypto;

/**
 * Scicare default implementation encryption/decryption algorithm
 *
 * @author T. McCauley
 * @version 1.0
 */
public class ScicareEncryptorImpl implements Encryptor {

	/**
     * Encode a string of unicode characters, breaking up the bytes in
     * the character and shifting them to get a predefined value.
     *
     * @param toEncode Object (which in this implementation must be a String)
     *                 which will be encoded.
     *
     * @return String containing the encode values.
     */
    public String encrypt (String objToEncrypt) throws EncryptionException {
        if( (objToEncrypt == null) || !(objToEncrypt instanceof String) ) {
            throw new EncryptionException("Object to encrypt is either null or" +
            		" not an instance of String");
        }

        String toEncode = (String) objToEncrypt;

        char[] charIndex =  {'1', 'E', 'T', '%',
                             'Q', '8', '3', '0',
                             'Z', 'A', 'U', 'M',
                             '2', 'I', 'a', '6'};

        int len = toEncode.length();
        byte[] byteStorage = new byte[len];
        char[] charStorage = new char[len*2];
        int count = 0;

        /* Get the String as a byte array */
        byteStorage = toEncode.getBytes();

        /* Loop through each byte, perform a shift and promote to a char */
        for (int loop = 0; loop < len; ++loop) {
            charStorage[count++] = charIndex[byteStorage[loop] >> 0x4 & 0xF];
            charStorage[count++] = charIndex[byteStorage[loop] & 0xF];
        }
        return  new String(charStorage);
    }

    /**
     * Decode the string previously encoded with encode method.
     *
     * @param toDecode Object (which in this implementation must be a String)
     *                 to decode
     *
     * @return String which has been decoded.
     */
    public String decrypt (String encryptedObj) throws EncryptionException {
        if( (encryptedObj == null) || !(encryptedObj instanceof String) ) {
            throw new EncryptionException("Object to dencrypt is either null or" +
            		" not an instance of String");
        }

        String toDecode = (String) encryptedObj;

        int len = toDecode.length();
        char[] charStorage = new char[len];
        byte[] byteStorage4 = new byte[len/2];
        int count = 0;
        int loop = 0;

        /* Get character array from string */
        toDecode.getChars(0, len, charStorage, 0);

        /* Loop through chars and unshift to bytes. */
        while (loop < len) {
            byteStorage4[count] = (byte)((decodeChar(charStorage[loop++]) << 0x4
                    | decodeChar(charStorage[loop++])) & 0xFF);

            ++count;
        }
        return  new String(byteStorage4);
    }

    /**
     * Decode the char previously encoded with encode() method.
     * @param toByte char to decode
     *
     * @return byte which has been decoded.
     */
    private byte decodeChar (char toByte) {
        if (toByte == 49)
            return  (byte)0;
        else if (toByte == 69)
            return  (byte)1;
        else if (toByte == 84)
            return  (byte)2;
        else if (toByte == 37)
            return  (byte)3;
        else if (toByte == 81)
            return  (byte)4;
        else if (toByte == 56)
            return  (byte)5;
        else if (toByte == 51)
            return  (byte)6;
        else if (toByte == 48)
            return  (byte)7;
        else if (toByte == 90)
            return  (byte)8;
        else if (toByte == 65)
            return  (byte)9;
        else if (toByte == 85)
            return  (byte)10;
        else if (toByte == 77)
            return  (byte)11;
        else if (toByte == 50)
            return  (byte)12;
        else if (toByte == 73)
            return  (byte)13;
        else if (toByte == 97)
            return  (byte)14;
        else if (toByte == 54)
            return  (byte)15;
        else
            return  (byte)-1;
    }
}