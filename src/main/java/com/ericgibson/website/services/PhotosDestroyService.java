package com.ericgibson.website.services;

import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.requestors.Request;
import com.ericgibson.website.requestors.Service;

public class PhotosDestroyService implements Service {

    private final CloudStorageGateway gateway;

    public PhotosDestroyService(CloudStorageGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(Request request) {
        PhotosDestroyServiceRequest photosDestroyServiceRequest = (PhotosDestroyServiceRequest) request;
        gateway.deleteObject(photosDestroyServiceRequest.getStorage(), photosDestroyServiceRequest.getKey());
        gateway.deleteObject(photosDestroyServiceRequest.getStorage(), photosDestroyServiceRequest.getKey() + "thumbnail");
    }
}
