package com.ericgibson.website.repositories;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ericgibson.website.gateways.CloudStorageGateway;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class AmazonClient implements CloudStorageGateway {

    private final AmazonS3 s3;

    public AmazonClient(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public void createStorage(String storage) {
        if (bucketDoesNotExist(storage)) {
            s3.createBucket(storage);
            String policy = createPolicy(storage);
            s3.setBucketPolicy(storage, policy);
        }
    }

    private boolean bucketDoesNotExist(String storage) {
        return !s3.doesBucketExistV2(storage);
    }

    private String createPolicy(String storage) {
        Policy policy = new Policy().withStatements(
                new Statement(Statement.Effect.Allow)
                        .withPrincipals(Principal.AllUsers)
                        .withActions(S3Actions.GetObject)
                        .withResources(new Resource("arn:aws:s3:::" + storage + "/*")));
        return policy.toJson();
    }

    @Override
    public void putObject(String storage, String key, File file) {
        s3.putObject(new PutObjectRequest(storage, key, file).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    @Override
    public List<String> listObjectKeys(String storage) {
        List<S3ObjectSummary> summaries = s3.listObjectsV2(storage).getObjectSummaries();
        return summaries.stream()
                .map(S3ObjectSummary::getKey)
                .filter(k -> !k.contains("thumbnail"))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteObject(String storage, String key) {
        try {
            s3.deleteObject(storage, key);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }
}