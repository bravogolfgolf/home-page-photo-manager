package com.ericgibson.website.builders;

import com.ericgibson.website.requestors.*;

import java.io.File;
import java.util.Map;

public class PhotosRequestBuilder implements RequestBuilder {

    private final Map<String, Request> requests;

    public PhotosRequestBuilder(Map<String, Request> requests) {
        this.requests = requests;
    }

    @Override
    public Request create(String type, Map<String, Object> map) {
        if (type.equals("Create")) {
            PhotosCreateRequest request = (PhotosCreateRequest) requests.get(type);
            request.setStorage((String) map.get("storage"));
            request.setFile((File) map.get("file"));
            return (Request) request;
        }
        if (type.equals("Index")) {
            PhotosIndexRequest request = (PhotosIndexRequest) requests.get(type);
            request.setStorage((String) map.get("storage"));
            return (Request) request;
        }
        if (type.equals("Destroy")) {
            PhotosDestroyRequest request = (PhotosDestroyRequest) requests.get(type);
            request.setStorage((String) map.get("storage"));
            request.setKey((String) map.get("key"));
            return (Request) request;
        }
        throw new IllegalArgumentException("Request Type not valid.");
    }
}
