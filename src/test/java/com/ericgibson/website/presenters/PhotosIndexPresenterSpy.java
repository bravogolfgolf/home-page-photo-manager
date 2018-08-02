package com.ericgibson.website.presenters;

import com.ericgibson.website.services.PhotosIndexResponse;

public class PhotosIndexPresenterSpy extends PhotosIndexPresenter{

    public boolean shouldCallPresentMethod = false;

    @Override
    public void present(PhotosIndexResponse response) {
        shouldCallPresentMethod = response.getSummaries().containsKey("test");
    }
}
