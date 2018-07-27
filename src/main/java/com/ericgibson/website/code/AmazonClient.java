package com.ericgibson.website.code;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

    boolean putObject(String name, MultipartFile multipartFile) {
        File thumbnail = new File("Thumbnail.png");
        try {
            String bucket = createBucket(name).getName();
            File photo = createFileFrom(multipartFile);
            String key = createKeyFrom(photo.getName());

            try {
                Thumbnails.of(photo)
                        .size(200, 200)
                        .keepAspectRatio(true)
                        .toFile(thumbnail);

                PutObjectRequest photoRequest = new PutObjectRequest(bucket, key, photo).withCannedAcl(CannedAccessControlList.PublicRead);
                PutObjectRequest thumbnailRequest = new PutObjectRequest(bucket, key + "thumbnail", thumbnail).withCannedAcl(CannedAccessControlList.PublicRead);
                s3.putObject(photoRequest);
                s3.putObject(thumbnailRequest);
                photo.delete();
                thumbnail.delete();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
        md.reset();
        md.update(fileName.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    List<S3ObjectSummary> listObjectsThumbnails(String name) {
        List<S3ObjectSummary> results = new ArrayList<>();
        List<S3ObjectSummary> summaries = s3.listObjectsV2(name).getObjectSummaries();
        for (S3ObjectSummary summary : summaries) {
            if (summary.getKey().contains("thumbnail"))
                results.add(summary);
        }
        return results;
    }

    void deleteObject(String name, String key) {
        try {
            s3.deleteObject(name, key);
            s3.deleteObject(name, key + "thumbnail");
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }
}