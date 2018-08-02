package com.ericgibson.website.presenters;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;
import java.util.Map;

public class PhotosIndexPresenterSpy extends PhotosIndexPresenter{

    public boolean shouldCallPresentMethod = false;

    @Override
    public void present(Map<String, List<S3ObjectSummary>> summaries) {
        shouldCallPresentMethod = summaries.containsKey("test");
    }
}
