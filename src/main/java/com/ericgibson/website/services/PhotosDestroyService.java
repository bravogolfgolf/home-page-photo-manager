package com.ericgibson.website.services;

import com.ericgibson.website.builder.Request;
import com.ericgibson.website.builder.Service;

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
