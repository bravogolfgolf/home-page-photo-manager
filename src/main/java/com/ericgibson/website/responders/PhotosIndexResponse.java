package com.ericgibson.website.responders;

import java.util.List;

public interface PhotosIndexResponse {
    List<String> getKeys();

    void setKeys(List<String> keys);
}
