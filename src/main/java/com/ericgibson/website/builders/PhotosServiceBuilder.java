package com.ericgibson.website.builders;

import com.ericgibson.website.requestors.Service;
import com.ericgibson.website.requestors.ServiceBuilder;

import java.util.Map;

public class PhotosServiceBuilder implements ServiceBuilder {

    private final Map<String, Service> services;

    public PhotosServiceBuilder(Map<String, Service> services) {
        this.services = services;
        System.out.println(services);
    }

    @Override
    public Service create(String type) {
        if (!services.containsKey(type))
            throw new IllegalArgumentException("Type not valid.");
        return services.get(type);
    }

}
