package com.ericgibson.website.services;

import com.ericgibson.website.builders.Request;
import com.ericgibson.website.requestors.Service;
import com.ericgibson.website.gateways.CloudStorageGateway;

public class PhotosDestroyService implements Service {

    private final CloudStorageGateway gateway;

    public PhotosDestroyService(CloudStorageGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(Request request) {
        PhotosDestroyRequest photosDestroyRequest = (PhotosDestroyRequest) request;
        gateway.deleteObject(photosDestroyRequest.storage, photosDestroyRequest.key);
        gateway.deleteObject(photosDestroyRequest.storage, photosDestroyRequest.key + "thumbnail");
    }
}
