package com.ericgibson.website;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ericgibson.website.builders.PhotosRequestBuilder;
import com.ericgibson.website.builders.PhotosServiceBuilder;
import com.ericgibson.website.requestors.Request;
import com.ericgibson.website.requestors.RequestBuilder;
import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.imaging.ThumbnailatorClient;
import com.ericgibson.website.repositories.AmazonClient;
import com.ericgibson.website.requestors.Service;
import com.ericgibson.website.requestors.ServiceBuilder;
import com.ericgibson.website.services.*;
import com.ericgibson.website.utilities.ImageUtility;
import com.ericgibson.website.webinterface.PhotosIndexPresenter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class WebsiteApplication {

    private final ImageUtility imageUtility = new ThumbnailatorClient();
    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();
    private final CloudStorageGateway gateway = new AmazonClient(amazonS3);
    private final PhotosCreateService createService = new PhotosCreateService(imageUtility, gateway);
    private final PhotosDestroyService destroyService = new PhotosDestroyService(gateway);

    private final PhotosCreateServiceRequest createRequest = new PhotosCreateServiceRequest();
    private final PhotosIndexServiceRequest indexRequest = new PhotosIndexServiceRequest();
    private final PhotosDestroyServiceRequest destroyRequest = new PhotosDestroyServiceRequest();

    private final Map<String, Request> requests = new HashMap<String, Request>() {{
        put("Create", createRequest);
        put("Index", indexRequest);
        put("Destroy", destroyRequest);
    }};

    @Bean
    public PhotosIndexPresenter PhotosIndexPresenter() {
        return new PhotosIndexPresenter();
    }

    @Bean
    public PhotosIndexService PhotosIndexService(PhotosIndexPresenter presenter) {
        return new PhotosIndexService(gateway, presenter);
    }

    @Bean
    public ServiceBuilder PhotosServiceBuilder(PhotosIndexService indexService) {
        Map<String, Service> services = new HashMap<>();
        services.put("Create", createService);
        services.put("Index", indexService);
        services.put("Destroy", destroyService);
        return new PhotosServiceBuilder(services);
    }

    @Bean
    public RequestBuilder PhotosRequestBuilder() {
        return new PhotosRequestBuilder(requests);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebsiteApplication.class, args);
    }
}
