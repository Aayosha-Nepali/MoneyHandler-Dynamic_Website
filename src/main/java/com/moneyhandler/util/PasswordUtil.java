package com.moneyhandler.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for AES-GCM password encryption and decryption.
 * Passwords are encrypted per user using the username as the key source.
 */
public class PasswordUtil {

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    // Generate a random nonce (IV or salt)
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    // Generate an AES key from a password and salt using PBKDF2
    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, "Key generation failed", ex);
            return null;
        }
    }

    // Encrypt the password using AES-GCM, prefixing IV and salt
    public static String encrypt(String username, String password) {
        try {
            byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);
            byte[] iv = getRandomNonce(IV_LENGTH_BYTE);
            SecretKey aesKey = getAESKeyFromPassword(username.toCharArray(), salt);

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
            byte[] cipherText = cipher.doFinal(password.getBytes(UTF_8));

            byte[] encryptedData = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                                             .put(iv)
                                             .put(salt)
                                             .put(cipherText)
                                             .array();

            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, "Encryption failed", ex);
            return null;
        }
    }

    // Decrypt the AES-GCM encrypted password using the original username
    public static String decrypt(String encryptedPassword, String username) {
        try {
            byte[] decoded = Base64.getDecoder().decode(encryptedPassword.getBytes(UTF_8));
            ByteBuffer bb = ByteBuffer.wrap(decoded);

            byte[] iv = new byte[IV_LENGTH_BYTE];
            bb.get(iv);

            byte[] salt = new byte[SALT_LENGTH_BYTE];
            bb.get(salt);

            byte[] cipherText = new byte[bb.remaining()];
            bb.get(cipherText);

            SecretKey aesKey = getAESKeyFromPassword(username.toCharArray(), salt);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, aesKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            byte[] plainText = cipher.doFinal(cipherText);
            return new String(plainText, UTF_8);
        } catch (Exception ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, "Decryption failed", ex);
            return null;
        }
    }
}
