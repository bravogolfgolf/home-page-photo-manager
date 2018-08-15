package com.ericgibson.website.services;

import com.ericgibson.website.requestors.PhotosDestroyRequest;
import com.ericgibson.website.requestors.Request;

public class PhotosDestroyServiceRequest implements Request, PhotosDestroyRequest {
    private String storage;
    private String key;

    @Override
    public String getStorage() {
        return storage;
    }

    @Override
    public void setStorage(String storage) {
        this.storage = storage;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

}
