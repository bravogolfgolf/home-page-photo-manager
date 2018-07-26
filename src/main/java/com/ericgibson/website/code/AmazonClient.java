package com.ericgibson.website.code;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class AmazonClient {

    private final AmazonS3 s3;

    public AmazonClient(AmazonS3 s3) {
        this.s3 = s3;
    }

    Bucket createBucket(String name) {
        if (s3.doesBucketExistV2(name))
            return getBucket(name);
        return s3.createBucket(name);
    }

    private Bucket getBucket(String name) {
        Bucket bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(name)) {
                bucket = b;
            }
        }
        return bucket;
    }

    PutObjectResult putObject(String name, MultipartFile multipartFile) {
        Bucket bucket = createBucket(name);
        PutObjectResult putObjectResult = null;
        try {
            File file = convertMultiPartToFile(multipartFile);
            String key = file.getName();
            PutObjectRequest request = new PutObjectRequest(bucket.getName(), key, file).withCannedAcl(CannedAccessControlList.PublicRead);
            putObjectResult = s3.putObject(request);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return putObjectResult;
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) {
        File file = null;
        try {
            file = new File(multipartFile.getOriginalFilename());
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(multipartFile.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    List<S3ObjectSummary> listObjects(String name) {
        ListObjectsV2Result result = s3.listObjectsV2(name);
        return result.getObjectSummaries();
    }

    void deleteObject(String name, String key) {
        try {
            s3.deleteObject(name, key);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }
}