package com.ericgibson.website.services;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ericgibson.website.presenters.PhotosIndexPresenter;

import java.util.List;
import java.util.Map;

class PhotosIndex {

    private AmazonClient amazonClient;
    private PhotosIndexPresenter presenter;

    PhotosIndex(AmazonClient amazonClient, PhotosIndexPresenter presenter) {
        this.amazonClient = amazonClient;
        this.presenter = presenter;
    }

    void execute(String name) {
        Map<String, List<S3ObjectSummary>> summaries = amazonClient.listsOfObjects(name);
        presenter.present(summaries);
    }
}
