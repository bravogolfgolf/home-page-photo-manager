package com.ericgibson.website.photos;

import com.amazonaws.services.s3.AmazonS3;

import java.io.File;

import static com.ericgibson.website.TestingConstants.BUCKET_NAME;

class AmazonClientSpy extends AmazonClient {
    boolean shouldCallPutObjectMethod;

    AmazonClientSpy(AmazonS3 amazonS3) {
        super(amazonS3);
    }

    @Override
    public void putObject(String bucket, String key, File file) {
        shouldCallPutObjectMethod =
                (bucket.equals(BUCKET_NAME) && key.equals("B41CDDD788E89D1FA985C876F7D2F0C7") && file.getName().equals("IMG_FILE.jpg")) ||
                        (bucket.equals(BUCKET_NAME) && key.equals("B41CDDD788E89D1FA985C876F7D2F0C7thumbnail") && file.getName().equals("Thumbnail.png"));
    }
}
