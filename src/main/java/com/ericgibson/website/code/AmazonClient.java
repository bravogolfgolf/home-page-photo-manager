package com.ericgibson.website.code;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AmazonClient {

    private final AmazonS3 s3;

    public AmazonClient(AmazonS3 s3) {
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

    PutObjectResult putObject(String name, MultipartFile multipartFile) {
        Bucket bucket = createBucket(name);
        PutObjectResult putObjectResult = null;
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = file.getName();
            PutObjectRequest request = new PutObjectRequest(bucket.getName(), fileName, file);
            putObjectResult = s3.putObject(request);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return putObjectResult;
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }
}
