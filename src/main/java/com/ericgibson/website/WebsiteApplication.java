package com.ericgibson.website;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ericgibson.website.builders.PhotosRequestBuilder;
import com.ericgibson.website.builders.PhotosServiceBuilder;
import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.imaging.ThumbnailatorClient;
import com.ericgibson.website.repositories.AmazonClient;
import com.ericgibson.website.utilities.ImageUtility;
import com.ericgibson.website.webinterface.PhotosIndexPresenter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebsiteApplication {

    public static final String URL_BASE = "https://s3.amazonaws.com";
    public static final String STORAGE = "echo-juliet-golf";

    private final ImageUtility imageUtility = new ThumbnailatorClient();
    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();
    private final CloudStorageGateway gateway = new AmazonClient(amazonS3);

    @Bean
    public PhotosIndexPresenter PhotosIndexPresenter(){
        return new PhotosIndexPresenter();
    }

    @Bean
    public PhotosServiceBuilder PhotosServiceBuilder(PhotosIndexPresenter presenter) {
        return new PhotosServiceBuilder(gateway, imageUtility, presenter);
    }

    @Bean
    public PhotosRequestBuilder PhotosRequestBuilder() {
        return new PhotosRequestBuilder();
    }

    public static void main(String[] args) {
        SpringApplication.run(WebsiteApplication.class, args);
    }
}
