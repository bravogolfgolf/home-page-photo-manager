package com.ericgibson.website.presenters;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ericgibson.website.services.PhotosIndexResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ericgibson.website.TestingConstants.KEY;

public class PhotosIndexPresenterTest {

    private final PhotosIndexPresenter presenter = new PhotosIndexPresenter();
    private final PhotosIndexResponse response = new PhotosIndexResponse();

    @Before
    public void setup() {
        Map<String, List<S3ObjectSummary>> summaries = new HashMap<>();
        List<S3ObjectSummary> list = new ArrayList<>();
        S3ObjectSummary summary = new S3ObjectSummary();
        summary.setKey(KEY);
        list.add(summary);
        summaries.put("photos", list);
        response.setSummaries(summaries);
        presenter.present(response);
    }

    @Test
    public void shouldReturnMapOfKeys() {
        Map<String, List<S3ObjectSummary>> result = presenter.response();
        result.containsKey("photo");
    }
}
