package com.ericgibson.website.services;

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
import java.util.List;

public class AmazonClient {

    private final AmazonS3 s3;

    public AmazonClient(AmazonS3 s3) {
        this.s3 = s3;
    }

    public Bucket createBucketIfDoesNotExist(String name) {
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

    public void putObject(String bucket, String key, File file) {
        s3.putObject(new PutObjectRequest(bucket, key, file).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public List<S3ObjectSummary> listOfObjects(String name) {
        return s3.listObjectsV2(name).getObjectSummaries();
    }

    public void deleteObject(String name, String key) {
        try {
            s3.deleteObject(name, key);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }
}