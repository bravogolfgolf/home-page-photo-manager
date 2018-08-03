package com.ericgibson.website.services;

import com.amazonaws.services.s3.AmazonS3;
import com.ericgibson.website.repositories.AmazonClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ericgibson.website.TestingConstants.BUCKET_NAME;
import static com.ericgibson.website.TestingConstants.KEY;

class AmazonClientFake extends AmazonClient {

    boolean shouldCallListOfKeysMethod = false;
    boolean shouldCallPutObjectMethod = false;
    boolean shouldCallDeleteObjectMethod = false;

    AmazonClientFake(AmazonS3 amazonS3) {
        super(amazonS3);
    }

    @Override
    public void createStorage(String name) {
    }

    @Override
    public void putObject(String name, String key, File file) {
        shouldCallPutObjectMethod =
                (name.equals(BUCKET_NAME) && key.equals("8A570DFCFF2247286D6D172414662B1E") && file.getName().equals("IMG_FILE.jpg")) ||
                        (name.equals(BUCKET_NAME) && key.equals("8A570DFCFF2247286D6D172414662B1Ethumbnail") && file.getName().equals("Thumbnail.png"));
    }

    @Override
    public List<String> listObjectKeys(String name) {
        shouldCallListOfKeysMethod = name.equals(BUCKET_NAME);
        List<String> keys = new ArrayList<>();
        keys.add(KEY);
        return keys;
    }

    @Override
    public void deleteObject(String name, String key) {
        shouldCallDeleteObjectMethod = name.equals(BUCKET_NAME) && key.equals(KEY) ||
                name.equals(BUCKET_NAME) && key.equals(KEY + "thumbnail");
    }
}
