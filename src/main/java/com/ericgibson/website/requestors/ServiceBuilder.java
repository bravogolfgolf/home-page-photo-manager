package com.ericgibson.website.requestors;

public interface ServiceBuilder {
    Service create(String type);
}
