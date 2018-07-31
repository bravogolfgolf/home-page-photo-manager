package com.ericgibson.website.photos;

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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AmazonClient {

    private final AmazonS3 s3;

    AmazonClient(AmazonS3 s3) {
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

    void putObject(String bucket, String key, File file) {
        s3.putObject(new PutObjectRequest(bucket, key, file).withCannedAcl(CannedAccessControlList.PublicRead));
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