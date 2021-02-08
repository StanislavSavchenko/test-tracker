package com.test.tracker.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.test.tracker.FileSystem;
import com.test.tracker.FileSystemProvider;
import com.test.tracker.impl.HostMachineImpl;
import com.test.tracker.impl.S3FileSystemImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;


@Slf4j
@Configuration
public class FileSystemConfig {

    @Bean
    public FileSystem fileSystem() {
        String regionAsString = System.getenv("FILE_SYSTEM_PROVIDER");
        Optional<FileSystemProvider> providerOptional = FileSystemProvider.getByName(regionAsString);

        if (providerOptional.isEmpty()) {
            throw new IllegalArgumentException("unknown value of env FILE_SYSTEM_PROVIDER");
        }

        FileSystemProvider provider = providerOptional.get();

        if (provider.equals(FileSystemProvider.S3)) {
            return new S3FileSystemImpl(s3Client(), System.getenv("S3_BUCKET"));
        } else {
            return new HostMachineImpl(System.getenv("ROOT_DIR"));
        }

    }

    private AmazonS3 s3Client() {
        String regionAsString = System.getenv("S3_REGION");
        Regions region = Regions.fromName(regionAsString);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider())
                .withRegion(region)
                .build();
    }

    private AWSCredentialsProvider awsCredentialsProvider() {
        String accessKey = System.getenv("S3_ACCESS_KEY");
        String secretKey = System.getenv("S3_SECRET_KEY");
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        return new AWSStaticCredentialsProvider(awsCredentials);
    }

}
