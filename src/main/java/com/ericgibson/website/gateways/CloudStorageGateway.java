package com.ericgibson.website.gateways;

import java.io.File;
import java.util.List;

public interface CloudStorageGateway {
    void createStorage(String name);

    void putObject(String name, String key, File file);

    List<String> listObjectKeys(String name);

    void deleteObject(String name, String key);
}
