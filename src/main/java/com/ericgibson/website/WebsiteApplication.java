package com.ericgibson.website;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ericgibson.website.builders.PhotosRequestBuilder;
import com.ericgibson.website.builders.PhotosServiceBuilder;
import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.imaging.ThumbnailatorClient;
import com.ericgibson.website.repositories.AmazonClient;
import com.ericgibson.website.requestors.Service;
import com.ericgibson.website.requestors.ServiceBuilder;
import com.ericgibson.website.services.PhotosCreateService;
import com.ericgibson.website.services.PhotosDestroyService;
import com.ericgibson.website.services.PhotosIndexService;
import com.ericgibson.website.utilities.ImageUtility;
import com.ericgibson.website.webinterface.PhotosIndexPresenter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class WebsiteApplication {

    public static final String URL_BASE = "https://s3.amazonaws.com";
    public static final String STORAGE = "echo-juliet-golf";

    private final ImageUtility imageUtility = new ThumbnailatorClient();
    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();
    private final CloudStorageGateway gateway = new AmazonClient(amazonS3);
    private final PhotosCreateService create = new PhotosCreateService(imageUtility, gateway);
    private final PhotosDestroyService destroy = new PhotosDestroyService(gateway);

    @Bean
    public PhotosIndexPresenter PhotosIndexPresenter() {
        return new PhotosIndexPresenter();
    }

    @Bean
    public PhotosIndexService PhotosIndexService(PhotosIndexPresenter presenter) {
        return new PhotosIndexService(gateway, presenter);
    }

    @Bean
    public ServiceBuilder PhotosServiceBuilder(PhotosIndexService index) {
        Map<String, Service> services = new HashMap<>();
        services.put("Create", create);
        services.put("Index", index);
        services.put("Destroy", destroy);
        return new PhotosServiceBuilder(services);
    }

    @Bean
    public PhotosRequestBuilder PhotosRequestBuilder() {
        return new PhotosRequestBuilder();
    }

    public static void main(String[] args) {
        SpringApplication.run(WebsiteApplication.class, args);
    }
}
