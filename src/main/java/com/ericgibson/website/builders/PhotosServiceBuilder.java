package com.ericgibson.website.builders;

import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.responders.PhotosIndexResponder;
import com.ericgibson.website.services.PhotosCreateService;
import com.ericgibson.website.services.PhotosDestroyService;
import com.ericgibson.website.services.PhotosIndexService;
import com.ericgibson.website.utilities.ImageUtility;

public class PhotosServiceBuilder {

    private final ImageUtility imageUtility;
    private final CloudStorageGateway gateway;
    private final PhotosIndexResponder presenter;

    public PhotosServiceBuilder(CloudStorageGateway gateway, ImageUtility imageUtility, PhotosIndexResponder presenter) {
        this.gateway = gateway;
        this.imageUtility = imageUtility;
        this.presenter = presenter;
    }

    public Service create(String type) {
        if (type.equals("Create"))
            return new PhotosCreateService(imageUtility, gateway);
        if (type.equals("Index"))
            return new PhotosIndexService(gateway, presenter);
        if (type.equals("Destroy"))
            return new PhotosDestroyService(gateway);
        throw new IllegalArgumentException("Type not valid.");
    }
}
