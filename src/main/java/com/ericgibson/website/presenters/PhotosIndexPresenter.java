package com.ericgibson.website.presenters;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ericgibson.website.services.PhotosIndexResponse;

import java.util.List;
import java.util.Map;

public class PhotosIndexPresenter {

    private Map<String, List<S3ObjectSummary>> summaries;

    public void present(PhotosIndexResponse response) {
        summaries = response.getSummaries();
    }

    public Map<String, List<S3ObjectSummary>> response() {
        return summaries;
    }
}
