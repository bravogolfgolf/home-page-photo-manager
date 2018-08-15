package com.ericgibson.website.services;

import com.ericgibson.website.requestors.Request;

public class PhotosDestroyServiceRequest implements Request {
    private String storage;
    private String key;

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
