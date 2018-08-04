package com.ericgibson.website.responders;

import com.ericgibson.website.services.PhotosIndexServiceResponse;

public interface PhotosIndexResponder {
    void present(PhotosIndexServiceResponse response);
}
