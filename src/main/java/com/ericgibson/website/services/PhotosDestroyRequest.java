package com.ericgibson.website.services;

import com.ericgibson.website.requestors.Request;

public class PhotosDestroyRequest implements Request {
    public String storage;
    public String key;
}
