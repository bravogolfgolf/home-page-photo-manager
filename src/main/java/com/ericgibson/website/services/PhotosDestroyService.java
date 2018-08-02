package com.ericgibson.website.services;

public class PhotosDestroyService {

    private AmazonClient amazonClient;

    public PhotosDestroyService(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    public void execute(String name, String key) {
        amazonClient.deleteObject(name, key);
        amazonClient.deleteObject(name, key + "thumbnail");
    }
}
