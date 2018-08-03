package com.ericgibson.website.presenters;

import com.ericgibson.website.services.PhotosIndexResponse;

import java.util.List;

public class PhotosIndexPresenter {

    private List<String> keys;

    public void present(PhotosIndexResponse response) {
        keys = response.getKeys();
    }

    public List<String> response() {
        return keys;
    }
}
