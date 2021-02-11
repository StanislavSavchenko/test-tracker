package com.test.tracker.core.service;

import com.test.tracker.FileSystem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class FileSystemService {

    private final FileSystem fileSystem;

    @Autowired
    public FileSystemService(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void upload(String fileName, String fileExtension, MultipartFile document) {
        try {
            File documentFile = File.createTempFile(fileName, "." + fileExtension);
            FileUtils.copyInputStreamToFile(document.getInputStream(), documentFile);
            documentFile.deleteOnExit();

            fileSystem.saveFile(fileName + "." + fileExtension, documentFile);
        } catch (IOException e) {
            log.error("Unable to save file " + e.getMessage());
            throw new RuntimeException("Unable to save file");
        }
    }

    public File readFile(String filePath,String fileName) {
        String fileExtension = FilenameUtils.getExtension(filePath);
        try {
            File file = File.createTempFile(fileName, "." + fileExtension);
            file.deleteOnExit();
            fileSystem.readFile("/" + filePath, file);
            return file;
        } catch (IOException e) {
            log.error("Unable to read file " + e.getMessage());
            throw new RuntimeException("Unable to read file");
        }
    }

}
