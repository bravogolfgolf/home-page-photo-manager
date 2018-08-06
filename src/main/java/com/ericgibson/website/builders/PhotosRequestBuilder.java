package com.ericgibson.website.builders;

import com.ericgibson.website.services.PhotosCreateRequest;
import com.ericgibson.website.services.PhotosDestroyRequest;
import com.ericgibson.website.services.PhotosIndexRequest;

import java.io.File;
import java.util.Map;

public class PhotosRequestBuilder {
    public Request create(String type, Map<String, Object> map) {
        if (type.equals("Create")) {
            PhotosCreateRequest request = new PhotosCreateRequest();
            request.storage = (String) map.get("storage");
            request.file = (File) map.get("file");
            return request;
        }
        if(type.equals("Index")){
            PhotosIndexRequest request = new PhotosIndexRequest();
            request.storage = (String) map.get("storage");
            return request;
        }
        if(type.equals("Destroy")) {
            PhotosDestroyRequest request = new PhotosDestroyRequest();
            request.storage = (String) map.get("storage");
            request.key = (String) map.get("key");
            return request;
        }
        throw new IllegalArgumentException("Type not valid.");
    }
}
