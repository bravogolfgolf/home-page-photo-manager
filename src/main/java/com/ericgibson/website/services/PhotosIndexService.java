package com.ericgibson.website.services;

import com.ericgibson.website.builders.Request;
import com.ericgibson.website.builders.Service;
import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.presenters.PhotosIndexPresenter;

import java.util.List;

public class PhotosIndexService extends Service {

    private final CloudStorageGateway gateway;
    private final PhotosIndexPresenter presenter;
    private final PhotosIndexResponse response = new PhotosIndexResponse();


    public PhotosIndexService(CloudStorageGateway gateway, PhotosIndexPresenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(Request request) {
        PhotosIndexRequest photosIndexRequest = (PhotosIndexRequest) request;
        gateway.createStorage(photosIndexRequest.bucket);
        List<String> keys = gateway.listObjectKeys(photosIndexRequest.bucket);
        response.setKeys(keys);
        presenter.present(response);
    }
}