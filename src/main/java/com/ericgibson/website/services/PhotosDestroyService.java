package com.ericgibson.website.services;

import com.ericgibson.website.builders.Request;
import com.ericgibson.website.builders.Service;
import com.ericgibson.website.gateways.CloudStorageGateway;

public class PhotosDestroyService extends Service {

    private final CloudStorageGateway gateway;

    public PhotosDestroyService(CloudStorageGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(Request request) {
        PhotosDestroyRequest photosDestroyRequest = (PhotosDestroyRequest) request;
        gateway.deleteObject(photosDestroyRequest.bucket, photosDestroyRequest.key);
        gateway.deleteObject(photosDestroyRequest.bucket, photosDestroyRequest.key + "thumbnail");
    }
}
