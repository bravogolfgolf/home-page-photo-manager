package com.ericgibson.website.webinterface;

import com.ericgibson.website.responders.PhotosIndexResponder;
import com.ericgibson.website.responders.Responder;
import com.ericgibson.website.responders.PhotosIndexResponse;

import java.util.List;

public class PhotosIndexPresenter implements Responder, PhotosIndexResponder {

    private List<String> keys;

    @Override
    public void present(PhotosIndexResponse response) {
        keys = response.getKeys();
    }

    @Override
    public List<String> response() {
        return keys;
    }
}
