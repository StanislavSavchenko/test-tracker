package com.test.tracker.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.test.tracker.FileSystem;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;

@Slf4j
public class S3FileSystemImpl implements FileSystem {

    private final AmazonS3 s3Client;
    private final String bucketName;

    public S3FileSystemImpl(AmazonS3 s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public void saveFile(String fileName, File fileToSave) {
        log.debug("Uploading {}  to S3 bucket {}...", fileName, bucketName);

        try {
            s3Client.putObject(bucketName, fileName, fileToSave);
        } catch (AmazonServiceException e) {
            log.error("Upload of {}  to S3 bucket {} FAILED", fileName, bucketName);
            throw e;
        }

        log.debug("Upload of {}  to S3 bucket {} SUCCEEDED", fileName, bucketName);
    }

    @Override
    public void readFile(String fileName, File pathWhereToSave) throws FileNotFoundException {
        log.debug("Trying to read file {}", fileName);
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
        s3Client.getObject(getObjectRequest, pathWhereToSave);
    }

}
