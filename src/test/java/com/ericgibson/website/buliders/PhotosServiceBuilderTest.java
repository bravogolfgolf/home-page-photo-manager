package com.ericgibson.website.buliders;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ericgibson.website.builders.PhotosServiceBuilder;
import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.imaging.ThumbnailatorClient;
import com.ericgibson.website.repositories.AmazonClient;
import com.ericgibson.website.requestors.Service;
import com.ericgibson.website.responders.PhotosIndexResponder;
import com.ericgibson.website.services.PhotosCreateService;
import com.ericgibson.website.services.PhotosDestroyService;
import com.ericgibson.website.services.PhotosIndexService;
import com.ericgibson.website.utilities.ImageUtility;
import com.ericgibson.website.webinterface.PhotosIndexPresenter;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PhotosServiceBuilderTest {

    private final ImageUtility imageUtility = new ThumbnailatorClient();
    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();
    private final CloudStorageGateway gateway = new AmazonClient(amazonS3);
    private final PhotosIndexResponder presenter = new PhotosIndexPresenter();
    private final PhotosCreateService create = new PhotosCreateService(imageUtility, gateway);
    private final PhotosIndexService index = new PhotosIndexService(gateway, presenter);
    private final PhotosDestroyService destroy = new PhotosDestroyService(gateway);

    private final Map<String, Service> services = new HashMap<String, Service>() {{
        put("Create", create);
        put("Index", index);
        put("Destroy", destroy);
    }};

    private final PhotosServiceBuilder builder = new PhotosServiceBuilder(services);

    @Test
    public void shouldReturnPhotosCreateService() {
        Service service = builder.create("Create");
        assertThat(service).isInstanceOf(PhotosCreateService.class);
    }

    @Test
    public void shouldReturnPhotosIndexService() {
        Service service = builder.create("Index");
        assertThat(service).isInstanceOf(PhotosIndexService.class);
    }

    @Test
    public void shouldReturnPhotosDestroyService() {
        Service service = builder.create("Destroy");
        assertThat(service).isInstanceOf(PhotosDestroyService.class);
    }

    @Test
    public void shouldThrowException() {
        assertThatThrownBy(() -> builder.create("Invalid Type")).hasMessage("Type not valid.");
    }
}
