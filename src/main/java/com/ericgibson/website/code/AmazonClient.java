package com.ericgibson.website.code;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

class AmazonClient {

    private AmazonS3 s3;

    AmazonClient(AmazonS3 s3) {
        this.s3 = s3;
    }

    Bucket createBucket(String bucketName) {
        Bucket bucket = null;
        if (s3.doesBucketExistV2(bucketName)) {
            bucket = getBucket(bucketName);
        } else {
            try {
                bucket = s3.createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }
        return bucket;
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
        PutObjectResult putObjectResult = null;
        try {
//            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, new File(filePath)).withCannedAcl(CannedAccessControlList.PublicReadWrite);
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, new File(filePath));
            putObjectResult = s3.putObject(request);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        return putObjectResult;
    }
}
