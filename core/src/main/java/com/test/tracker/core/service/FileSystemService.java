package com.test.tracker.core.service;

import com.test.tracker.FileSystem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileSystemService {

    private final FileSystem fileSystem;

    @Autowired
    public FileSystemService(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void upload(Long taskId, MultipartFile document) {
        String fileExtension = FilenameUtils.getExtension(document.getResource().getFilename());
        String fileName = document.getName();
        try {
            File documentFile = File.createTempFile(fileName, "." + fileExtension);
            FileUtils.copyInputStreamToFile(document.getInputStream(), documentFile);
            documentFile.deleteOnExit();

            fileSystem.saveFile(fileName + "." + fileExtension, documentFile);
        } catch (IOException e) {
        }
    }

}
