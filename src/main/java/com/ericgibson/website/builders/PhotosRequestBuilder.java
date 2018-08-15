package com.ericgibson.website.builders;

import com.ericgibson.website.requestors.Request;
import com.ericgibson.website.requestors.RequestBuilder;
import com.ericgibson.website.services.PhotosCreateServiceRequest;
import com.ericgibson.website.services.PhotosDestroyServiceRequest;
import com.ericgibson.website.services.PhotosIndexServiceRequest;

import java.io.File;
import java.util.Map;

public class PhotosRequestBuilder implements RequestBuilder {

    @Override
    public Request create(String type, Map<String, Object> map) {
        if (type.equals("Create")) {
            PhotosCreateServiceRequest request = new PhotosCreateServiceRequest();
            request.setStorage((String) map.get("storage"));
            request.setFile((File) map.get("file"));
            return request;
        }
        if (type.equals("Index")) {
            PhotosIndexServiceRequest request = new PhotosIndexServiceRequest();
            request.setStorage((String) map.get("storage"));
            return request;
        }
        if (type.equals("Destroy")) {
            PhotosDestroyServiceRequest request = new PhotosDestroyServiceRequest();
            request.setStorage((String) map.get("storage"));
            request.setKey((String) map.get("key"));
            return request;
        }
        throw new IllegalArgumentException("Type not valid.");
    }
}
