package com.test.tracker.impl;

import com.test.tracker.FileSystem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
public class HostMachineImpl implements FileSystem {

    private final String rootPath;

    public HostMachineImpl(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public void saveFile(String fileName, File fileToSave) {
        Path targetFile = Paths.get(rootPath, fileName);

        try (FileChannel readCh = FileChannel.open(fileToSave.toPath(), StandardOpenOption.READ)) {
            try (FileChannel writeCh = FileChannel.open(targetFile, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
                 FileLock fileLock = acquireFileLock(writeCh)) {
                writeCh.transferFrom(readCh, 0, Long.MAX_VALUE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FileLock acquireFileLock(FileChannel ch) throws IOException {
        FileLock fileLock;
        while ((fileLock = tryAcquireFileLock(ch)) == null) ;
        return fileLock;
    }

    private FileLock tryAcquireFileLock(FileChannel ch) throws IOException {
        try {
            return ch.tryLock();
        } catch (OverlappingFileLockException e) {
            return null;
        }
    }

    @Override
    public void readFile(String fileName, File pathWhereToSave) throws FileNotFoundException {

        Path source = Paths.get(rootPath, fileName)
                .resolve(fileName);
        if (!Files.exists(source)) {
            throw new FileNotFoundException(rootPath + fileName);
        }

        try {
            FileUtils.copyFile(source.toFile(), pathWhereToSave);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
