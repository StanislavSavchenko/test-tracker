package com.test.tracker;

public interface EncryptorSupplier {
    SimpleEncryptor get(String key) throws Exception;
}
