package com.ericgibson.website.services;

import com.ericgibson.website.requestors.Request;

public class PhotosIndexServiceRequest implements Request {
    private String storage;

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

}
