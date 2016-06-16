
package clasescomunes.utils;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;



public class PasswordHash {
    
    private static PasswordHash instance;
    
    private String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";
    private int SALT_BYTE_SIZE;
    private int PBKDF2_ITERATIONS;
    private int HASH_BYTE_SIZE;
    private int ITERATION_INDEX;
    private int SALT_INDEX;
    private int PBKDF2_INDEX;
    
    public static PasswordHash getInstance(){
        if (instance == null)
            instance = new PasswordHash();
        
        return instance;
    }
    
    private PasswordHash(){
        SALT_BYTE_SIZE = 24;
        PBKDF2_ITERATIONS = 1000;
        HASH_BYTE_SIZE = 24;
        ITERATION_INDEX = 0;
        SALT_INDEX = 1;
        PBKDF2_INDEX = 2;
        
    }


    public byte[] cifra(String sinCifrar) throws Exception {
        byte[] bytes = sinCifrar.getBytes("UTF-8");
        Cipher aes = obtieneCipher(true);
        byte[] cifrado = aes.doFinal(bytes);
        return cifrado;
    }
    
    public byte[] cifra(String sinCifrar, SecretKey key) throws Exception {
        byte[] bytes = sinCifrar.getBytes("UTF-8");
        Cipher aes = obtieneCipher(true, key);
        byte[] cifrado = aes.doFinal(bytes);
        return cifrado;
    }
    

    public String descifra(byte[] cifrado) throws Exception {
        Cipher aes = obtieneCipher(false);
        byte[] bytes = aes.doFinal(cifrado);
        String sinCifrar = new String(bytes, "UTF-8");
        return sinCifrar;
    }
    
    public String descifra(byte[] cifrado,SecretKey key) throws Exception {
        final Cipher aes = obtieneCipher(false,key);
        final byte[] bytes = aes.doFinal(cifrado);
        final String sinCifrar = new String(bytes, "UTF-8");
        return sinCifrar;
    }

    private Cipher obtieneCipher(boolean paraCifrar) throws Exception {
        String frase = "pakquieressaberesojajajajunsaludo";
        MessageDigest digest = 
                MessageDigest.getInstance("SHA-1");
        digest.update(frase.getBytes("UTF-8"));
        SecretKeySpec key = new SecretKeySpec(
                digest.digest(), 0, 16, "AES");

        Cipher aes = Cipher.getInstance(
                "AES/ECB/PKCS5Padding");
        if (paraCifrar) {
            aes.init(Cipher.ENCRYPT_MODE, key);
        } else {
            aes.init(Cipher.DECRYPT_MODE, key);
        }

        return aes;
    }
    
    private Cipher obtieneCipher(boolean paraCifrar,SecretKey key) throws Exception {

        final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        if (paraCifrar) {
            aes.init(Cipher.ENCRYPT_MODE, key);
        } else {
            aes.init(Cipher.DECRYPT_MODE, key);
        }

        return aes;
    }
    
    public String createHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return createHash(password.toCharArray());
    }
    
    public String createHash(char[] password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }
    
    private byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }
    
    private String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
    
    public  boolean validatePassword(String password, String correctHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return validatePassword(password.toCharArray(), correctHash);
    }
    
    public  boolean validatePassword(char[] password, String correctHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Decode the hash into its parameters
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] salt = fromHex(params[SALT_INDEX]);
        byte[] hash = fromHex(params[PBKDF2_INDEX]);
        // Compute the hash of the provided password, using the same salt, 
        // iteration count, and hash length
        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash, testHash);
    }
    
    private byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }
    
    private boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    public byte[] generarLinkActivacion(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        return salt;
    }

}
