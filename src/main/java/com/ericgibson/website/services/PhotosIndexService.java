package com.ericgibson.website.services;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ericgibson.website.presenters.PhotosIndexPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotosIndexService {

    private AmazonClient amazonClient;
    private PhotosIndexPresenter presenter;
    private final PhotosIndexResponse response = new PhotosIndexResponse();


    public PhotosIndexService(AmazonClient amazonClient, PhotosIndexPresenter presenter) {
        this.amazonClient = amazonClient;
        this.presenter = presenter;
    }

    public void execute(String name) {
        List<S3ObjectSummary> s3summaries = amazonClient.listsOfObjects(name);
        Map<String, List<S3ObjectSummary>> maps = createResponse(s3summaries);
        response.setSummaries(maps);
        presenter.present(response);
    }

    private Map<String, List<S3ObjectSummary>> createResponse(List<S3ObjectSummary> summaries) {
        return separateThumbnailsAndPhotosIntoTwoLists(summaries);
    }

    private Map<String, List<S3ObjectSummary>> separateThumbnailsAndPhotosIntoTwoLists(List<S3ObjectSummary> summaries) {
        Map<String, List<S3ObjectSummary>> results = new HashMap<>();
        List<S3ObjectSummary> thumbnails = new ArrayList<>();
        List<S3ObjectSummary> photos = new ArrayList<>();
        for (S3ObjectSummary summary : summaries) {
            if (summary.getKey().contains("thumbnail"))
                thumbnails.add(summary);
            else
                photos.add(summary);
        }
        results.put("thumbnails", thumbnails);
        results.put("photos", photos);
        return results;
    }
}
