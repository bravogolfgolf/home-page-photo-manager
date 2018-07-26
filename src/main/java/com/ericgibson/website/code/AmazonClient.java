package com.ericgibson.website.code;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AmazonClient {

    private final AmazonS3 s3;

    public AmazonClient(AmazonS3 s3) {
        this.s3 = s3;
    }

    Bucket createBucket(String name) {
        if (s3.doesBucketExistV2(name))
            return getBucket(name);
        Bucket bucket = s3.createBucket(name);
        s3.setBucketPolicy(name, getPolicy(name));
        return bucket;
    }

    private String getPolicy(String name) {
        Policy policy = new Policy().withStatements(
                new Statement(Statement.Effect.Allow)
                        .withPrincipals(Principal.AllUsers)
                        .withActions(S3Actions.GetObject)
                        .withResources(new Resource("arn:aws:s3:::" + name + "/*")));
        return policy.toJson();
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
        PutObjectResult putObjectResult = null;
        try {
            String bucket = createBucket(name).getName();
            File file = createFileFrom(multipartFile);
            String key = createKeyFrom(file.getName());
            PutObjectRequest request = new PutObjectRequest(bucket, key, file).withCannedAcl(CannedAccessControlList.PublicRead);
            putObjectResult = s3.putObject(request);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return putObjectResult;
    }

    private File createFileFrom(MultipartFile multipartFile) {
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

    private String createKeyFrom(String fileName) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(fileName.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
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