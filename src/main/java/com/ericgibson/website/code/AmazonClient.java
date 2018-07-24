package com.ericgibson.website.code;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

class AmazonClient {

    private AmazonS3 s3;

    AmazonClient(AmazonS3 s3) {
        this.s3 = s3;
    }

    Bucket createBucket(String name) {
        Bucket bucket = null;
        if (s3.doesBucketExistV2(name)) {
            bucket = getBucket(name);
        } else {
            try {
                bucket = s3.createBucket(name);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }
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
}
