package com.ericgibson.website.responders;

import com.ericgibson.website.services.PhotosIndexResponse;

public interface PhotosIndexResponder {
    void present(PhotosIndexResponse response);
}
