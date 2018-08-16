package com.ericgibson.website.services;

import com.ericgibson.website.requestors.PhotosCreateRequest;
import com.ericgibson.website.requestors.Request;

import java.io.File;

public class PhotosCreateServiceRequest implements Request, PhotosCreateRequest {
    private String storage;
    private File file;

    public String getStorage() {
        return storage;
    }

    @Override
    public void setStorage(String storage) {
        this.storage = storage;
    }

    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }
}
