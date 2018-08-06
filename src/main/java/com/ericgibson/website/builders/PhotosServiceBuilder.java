package com.ericgibson.website.builders;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.imaging.ThumbnailatorClient;
import com.ericgibson.website.repositories.AmazonClient;
import com.ericgibson.website.services.PhotosCreateService;
import com.ericgibson.website.services.PhotosDestroyService;
import com.ericgibson.website.services.PhotosIndexService;
import com.ericgibson.website.utilities.ImageUtility;
import com.ericgibson.website.webinterface.PhotosIndexPresenter;

public class PhotosServiceBuilder {

    private static final String STORAGE = "echo-juliet-golf";
    private final ImageUtility imageUtility = new ThumbnailatorClient();
    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();
    private final CloudStorageGateway gateway = new AmazonClient(amazonS3);
    private final PhotosIndexPresenter presenter = new PhotosIndexPresenter();


    public Service create(String type) {
        if (type.equals("Create"))
            return new PhotosCreateService(STORAGE, imageUtility, gateway);
        if (type.equals("Index"))
            return new PhotosIndexService(gateway, presenter);
        if (type.equals("Destroy"))
            return new PhotosDestroyService(gateway);
        throw new IllegalArgumentException("Type not valid.");
    }
}
