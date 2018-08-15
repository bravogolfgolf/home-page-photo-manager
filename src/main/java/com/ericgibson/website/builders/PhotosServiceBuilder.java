package com.ericgibson.website.builders;

import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.requestors.Service;
import com.ericgibson.website.requestors.ServiceBuilder;
import com.ericgibson.website.responders.PhotosIndexResponder;
import com.ericgibson.website.services.PhotosCreateService;
import com.ericgibson.website.services.PhotosDestroyService;
import com.ericgibson.website.services.PhotosIndexService;
import com.ericgibson.website.utilities.ImageUtility;

import java.util.Map;

public class PhotosServiceBuilder implements ServiceBuilder {

    private ImageUtility imageUtility;
    private CloudStorageGateway gateway;
    private PhotosIndexResponder presenter;
    private Map<String, Service> services;

    public PhotosServiceBuilder(CloudStorageGateway gateway, ImageUtility imageUtility, PhotosIndexResponder presenter) {
        this.gateway = gateway;
        this.imageUtility = imageUtility;
        this.presenter = presenter;
    }

    public PhotosServiceBuilder(Map<String, Service> services) {
        this.services = services;
    }

    @Override
    public Service create(String type) {
        if (type.equals("Create"))
            return new PhotosCreateService(imageUtility, gateway);
        if (type.equals("Index"))
            return new PhotosIndexService(gateway, presenter);
        if (type.equals("Destroy"))
            return new PhotosDestroyService(gateway);
        throw new IllegalArgumentException("Type not valid.");
    }

    public Service make(String type) {
        if (!services.containsKey(type))
            throw new IllegalArgumentException("Type not valid.");
        return services.get(type);
    }
}
