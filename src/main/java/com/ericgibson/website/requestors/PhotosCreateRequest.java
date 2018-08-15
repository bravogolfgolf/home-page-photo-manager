package com.ericgibson.website.requestors;

import java.io.File;

public interface PhotosCreateRequest {
    String getStorage();

    void setStorage(String storage);

    File getFile();

    void setFile(File file);
}
