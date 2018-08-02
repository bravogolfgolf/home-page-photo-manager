package com.ericgibson.website.services;

class PhotosIndex {

    private AmazonClient amazonClient;

    PhotosIndex(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    void execute(String name) {
        amazonClient.listsOfObjects(name);
    }
}
