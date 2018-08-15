package com.ericgibson.website.services;

import com.ericgibson.website.builders.Request;
import com.ericgibson.website.requestors.Service;
import com.ericgibson.website.gateways.CloudStorageGateway;
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
        PhotosIndexRequest photosIndexRequest = (PhotosIndexRequest) request;
        gateway.createStorage(photosIndexRequest.storage);
        List<String> keys = gateway.listObjectKeys(photosIndexRequest.storage);
        response.setKeys(keys);
        responder.present(response);
    }
}