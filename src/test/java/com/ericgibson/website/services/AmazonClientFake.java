package com.ericgibson.website.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ericgibson.website.TestingConstants.BUCKET_NAME;
import static com.ericgibson.website.TestingConstants.KEY;

class AmazonClientFake extends AmazonClient {
    boolean shouldCallPutObjectMethod = false;
    boolean shouldCallListsOfObjectsMethod = false;

    AmazonClientFake(AmazonS3 amazonS3) {
        super(amazonS3);
    }

    @Override
    public void putObject(String bucket, String key, File file) {
        shouldCallPutObjectMethod =
                (bucket.equals(BUCKET_NAME) && key.equals("B41CDDD788E89D1FA985C876F7D2F0C7") && file.getName().equals("IMG_FILE.jpg")) ||
                        (bucket.equals(BUCKET_NAME) && key.equals("B41CDDD788E89D1FA985C876F7D2F0C7thumbnail") && file.getName().equals("Thumbnail.png"));
    }

    @Override
    public List<S3ObjectSummary> listsOfObjects(String name) {
        shouldCallListsOfObjectsMethod = name.equals(BUCKET_NAME);
        List<S3ObjectSummary> list = new ArrayList<>();
        S3ObjectSummary summary = new S3ObjectSummary();
        summary.setKey(KEY);
        list.add(summary);
        return list;
    }
}
