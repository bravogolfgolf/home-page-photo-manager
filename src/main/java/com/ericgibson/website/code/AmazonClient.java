package com.ericgibson.website.code;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

class AmazonClient {

    private AmazonS3 s3;

    AmazonClient(AmazonS3 s3) {
        this.s3 = s3;
    }

    Bucket createBucket(String bucketName) {
        if (s3.doesBucketExistV2(bucketName))
            return getBucket(bucketName);
        return s3.createBucket(bucketName);
    }

    private Bucket getBucket(String bucketName) {
        Bucket bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucketName)) {
                bucket = b;
            }
        }
        return bucket;
    }

    PutObjectResult putObject(String bucketName, String filePath) {
        String fileName = Paths.get(filePath).getFileName().toString();
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, new File(filePath));
        return s3.putObject(request);
    }
}
