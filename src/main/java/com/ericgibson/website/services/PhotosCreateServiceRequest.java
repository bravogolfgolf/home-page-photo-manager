package com.ericgibson.website.services;

import com.ericgibson.website.requestors.Request;

import java.io.File;

public class PhotosCreateServiceRequest implements Request {
    private String storage;
    private File file;

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
