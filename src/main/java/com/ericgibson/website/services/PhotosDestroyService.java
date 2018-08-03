package com.ericgibson.website.services;

import com.ericgibson.website.builders.Request;
import com.ericgibson.website.builders.Service;

public class PhotosDestroyService extends Service {

    private AmazonClient amazonClient;

    public PhotosDestroyService(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @Override
    public void execute(Request request) {
        PhotosDestroyRequest photosDestroyRequest = (PhotosDestroyRequest) request;
        amazonClient.deleteObject(photosDestroyRequest.bucket, photosDestroyRequest.key);
        amazonClient.deleteObject(photosDestroyRequest.bucket, photosDestroyRequest.key + "thumbnail");
    }
}
