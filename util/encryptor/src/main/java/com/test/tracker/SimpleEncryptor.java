package com.test.tracker;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SimpleEncryptor implements Encryptor {
    private static final String ALGORITHM = "AES";
    private static final String DEFAULT_SECRET_KEY = "asadasasdomaskdSd";

    private Key secretKeySpec;

    public SimpleEncryptor()
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        this(null);
    }

    public SimpleEncryptor(String secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {
        this.secretKeySpec = generateKey(secretKey);
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1 || args.length == 2) {
            String plainText = args[0];
            String secretKey = args.length == 2 ? args[1] : null;
            SimpleEncryptor aes = secretKey != null ? new SimpleEncryptor(secretKey) : new SimpleEncryptor();
            String encryptedString = aes.encrypt(plainText);
            System.out.println(encryptedString);
        } else {
            System.out.println("USAGE: java -jar EncryptDataSource.jar <string-to-encrypt>");
        }
    }

    public String encrypt(String plainText) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return asHexString(encrypted);
    }

    public String decrypt(String encryptedString) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] original = cipher.doFinal(toByteArray(encryptedString));
        return new String(original);
    }

    private Key generateKey(String secretKey) throws NoSuchAlgorithmException {
        if (secretKey == null) {
            secretKey = DEFAULT_SECRET_KEY;
        }
        byte[] key = (secretKey).getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // use only the first 128 bit

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128); // 192 and 256 bits may not be available

        return new SecretKeySpec(key, ALGORITHM);
    }

    public String generateKeyString() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();
        return asHexString(secretKey.getEncoded());
    }

    private String asHexString(byte buf[]) {
        StringBuilder strbuf = new StringBuilder();
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    private byte[] toByteArray(String hexString) {
        int arrLength = hexString.length() >> 1;
        byte buf[] = new byte[arrLength];
        for (int ii = 0; ii < arrLength; ii++) {
            int index = ii << 1;
            String l_digit = hexString.substring(index, index + 2);
            buf[ii] = (byte) Integer.parseInt(l_digit, 16);
        }
        return buf;
    }

}
