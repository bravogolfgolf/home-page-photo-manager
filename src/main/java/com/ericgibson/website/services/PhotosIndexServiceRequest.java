package com.ericgibson.website.services;

import com.ericgibson.website.requestors.PhotosIndexRequest;
import com.ericgibson.website.requestors.Request;

public class PhotosIndexServiceRequest implements Request, PhotosIndexRequest {
    private String storage;

    @Override
    public String getStorage() {
        return storage;
    }

    @Override
    public void setStorage(String storage) {
        this.storage = storage;
    }

}
