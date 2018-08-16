package com.ericgibson.website.requestors;

import java.io.File;

public interface PhotosCreateRequest {

    void setStorage(String storage);

    void setFile(File file);
}
