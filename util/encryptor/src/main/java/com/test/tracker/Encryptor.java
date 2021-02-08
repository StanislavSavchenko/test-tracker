package com.test.tracker;

public interface Encryptor {
    String encrypt(String plainText) throws Exception;

    String decrypt(String encryptedString) throws Exception;

    String generateKeyString() throws Exception;

}