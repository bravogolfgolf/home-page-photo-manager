package com.ericgibson.website.repositories;

import com.ericgibson.website.gateways.CloudStorageGateway;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class AmazonClient implements CloudStorageGateway {

    private final S3Client s3;

    public AmazonClient(S3Client s3) {
        this.s3 = s3;
    }

    @Override
    public void putObject(String storage, String key, File file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(storage).key(key).build();
        s3.putObject(putObjectRequest, file.toPath());
    }

    @Override
    public List<String> listObjectKeys(String storage) {
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder().bucket(storage).build();
        List<S3Object> summaries = s3.listObjectsV2(listObjectsRequest).contents();
        return summaries.stream()
                .map(S3Object::key)
                .filter(k -> !k.contains("thumbnail"))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteObject(String storage, String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(storage).key(key).build();
            s3.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
