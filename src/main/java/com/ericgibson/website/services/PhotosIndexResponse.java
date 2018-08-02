package com.ericgibson.website.services;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;
import java.util.Map;

public class PhotosIndexResponse {

    private Map<String, List<S3ObjectSummary>> summaries;

    public Map<String, List<S3ObjectSummary>> getSummaries() {
        return summaries;
    }

    public void setSummaries(Map<String, List<S3ObjectSummary>> summaries) {
        this.summaries = summaries;
    }
}
