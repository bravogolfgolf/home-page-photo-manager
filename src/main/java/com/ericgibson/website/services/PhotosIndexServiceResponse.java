package com.ericgibson.website.services;

import com.ericgibson.website.responders.PhotosIndexResponse;

import java.util.List;

public class PhotosIndexServiceResponse implements PhotosIndexResponse {

    private List<String> keys;

    @Override
    public List<String> getKeys() {
        return keys;
    }

    @Override
    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

}
