package com.test.tracker;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum FileSystemProvider {
    S3,
    HOST;

    public static Optional<FileSystemProvider> getByName(String name) {
        for (FileSystemProvider provider : values()) {
            if (provider.name().equalsIgnoreCase(name)) {
                return Optional.of(provider);
            }
        }
        return Optional.empty();
    }

}
