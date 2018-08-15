package com.ericgibson.website.requestors;

public interface PhotosDestroyRequest {
    String getStorage();

    void setStorage(String storage);

    String getKey();

    void setKey(String key);
}
