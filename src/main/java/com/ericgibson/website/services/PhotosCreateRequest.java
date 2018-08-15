package com.ericgibson.website.services;

import com.ericgibson.website.requestors.Request;

import java.io.File;

public class PhotosCreateRequest implements Request {
    public String storage;
    public File file;
}
