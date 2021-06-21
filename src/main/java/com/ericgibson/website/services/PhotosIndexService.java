package com.ericgibson.website.services;

import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.requestors.Request;
import com.ericgibson.website.requestors.Service;
import com.ericgibson.website.responders.PhotosIndexResponder;
import com.ericgibson.website.responders.PhotosIndexResponse;

import java.util.List;

public class PhotosIndexService implements Service {

    private final CloudStorageGateway gateway;
    private final PhotosIndexResponder responder;
    private final PhotosIndexResponse response = new PhotosIndexServiceResponse();


    public PhotosIndexService(CloudStorageGateway gateway, PhotosIndexResponder responder) {
        this.gateway = gateway;
        this.responder = responder;
    }

    @Override
    public void execute(Request request) {
        PhotosIndexServiceRequest photosIndexServiceRequest = (PhotosIndexServiceRequest) request;
        List<String> keys = gateway.listObjectKeys(photosIndexServiceRequest.getStorage());
        response.setKeys(keys);
        responder.present(response);
    }
}