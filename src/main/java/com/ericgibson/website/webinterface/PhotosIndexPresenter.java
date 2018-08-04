package com.ericgibson.website.webinterface;

import com.ericgibson.website.responders.Responder;
import com.ericgibson.website.services.PhotosIndexServiceResponse;

import java.util.List;

public class PhotosIndexPresenter implements Responder, com.ericgibson.website.responders.PhotosIndexResponder {

    private List<String> keys;

    @Override
    public void present(PhotosIndexServiceResponse response) {
        keys = response.getKeys();
    }

    @Override
    public List<String> response() {
        return keys;
    }
}