package com.ericgibson.website.webinterface;

import com.ericgibson.website.services.PhotosIndexServiceResponse;

import static com.ericgibson.website.TestingConstants.KEY;

public class PhotosIndexPresenterSpy extends PhotosIndexPresenter {

    public boolean shouldCallPresentMethod = false;

    @Override
    public void present(PhotosIndexServiceResponse response) {
        shouldCallPresentMethod = response.getKeys().contains(KEY);
    }
}
