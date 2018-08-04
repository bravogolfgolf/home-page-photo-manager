package com.ericgibson.website.webinterface;

import com.ericgibson.website.responders.PhotosIndexResponse;

import static com.ericgibson.website.TestingConstants.KEY;

public class PhotosIndexPresenterSpy extends PhotosIndexPresenter {

    public boolean shouldCallPresentMethod = false;

    @Override
    public void present(PhotosIndexResponse response) {
        shouldCallPresentMethod = response.getKeys().contains(KEY);
    }
}
