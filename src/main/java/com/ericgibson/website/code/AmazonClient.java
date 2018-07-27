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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmazonClient {

    private final AmazonS3 s3;

    public AmazonClient(AmazonS3 s3) {
        this.s3 = s3;
    }

    Bucket getOrCreateBucket(String name) {
        if (s3.doesBucketExistV2(name))
            return getBucket(name);
        Bucket bucket = s3.createBucket(name);
        String policy = createPolicy(name);
        s3.setBucketPolicy(name, policy);
        return bucket;
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

    private String createPolicy(String name) {
        Policy policy = new Policy().withStatements(
                new Statement(Statement.Effect.Allow)
                        .withPrincipals(Principal.AllUsers)
                        .withActions(S3Actions.GetObject)
                        .withResources(new Resource("arn:aws:s3:::" + name + "/*")));
        return policy.toJson();
    }

    boolean putObject(String name, MultipartFile multipartFile) {
        File photo = createFileFrom(multipartFile);
        if (photo == null)
            return false;
        scalePhoto(photo);
        String bucket = getOrCreateBucket(name).getName();
        File thumbnail = createThumbnail(photo);
        String key = createKeyFrom(photo);
        saveObjects(bucket, key, photo, thumbnail);
        return deleteTempFiles(photo, thumbnail);
    }

    private File createFileFrom(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename() == null ? "" : multipartFile.getOriginalFilename();
        File file = new File(fileName);
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(multipartFile.getBytes());
            stream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void scalePhoto(File photo) {
        try {
            Thumbnails.of(photo)
                    .scale(1)
                    .toFile(photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createThumbnail(File photo) {
        File thumbnail = new File("Thumbnail.png");
        try {
            Thumbnails.of(photo)
                    .size(200, 200)
                    .keepAspectRatio(true)
                    .toFile(thumbnail);
            return thumbnail;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String createKeyFrom(File file) {
        String result = null;
        try {
            String fileName = file.getName();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(fileName.getBytes());
            result = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void saveObjects(String bucket, String key, File photo, File thumbnail) {
        PutObjectRequest photoRequest = new PutObjectRequest(bucket, key, photo).withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectRequest thumbnailRequest = new PutObjectRequest(bucket, key + "thumbnail", thumbnail).withCannedAcl(CannedAccessControlList.PublicRead);
        s3.putObject(photoRequest);
        s3.putObject(thumbnailRequest);
    }

    private boolean deleteTempFiles(File photo, File thumbnail) {
        return photo.delete() && thumbnail.delete();
    }

    Map<String, List<S3ObjectSummary>> listsOfObjects(String name) {
        List<S3ObjectSummary> summaries = s3.listObjectsV2(name).getObjectSummaries();
        return separateThumbnailsAndPhotosIntoTwoLists(summaries);
    }

    private Map<String, List<S3ObjectSummary>> separateThumbnailsAndPhotosIntoTwoLists(List<S3ObjectSummary> summaries) {
        Map<String, List<S3ObjectSummary>> results = new HashMap<>();
        List<S3ObjectSummary> thumbnails = new ArrayList<>();
        List<S3ObjectSummary> photos = new ArrayList<>();
        for (S3ObjectSummary summary : summaries) {
            if (summary.getKey().contains("thumbnail"))
                thumbnails.add(summary);
            else
                photos.add(summary);
        }
        results.put("thumbnails", thumbnails);
        results.put("photos", photos);
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