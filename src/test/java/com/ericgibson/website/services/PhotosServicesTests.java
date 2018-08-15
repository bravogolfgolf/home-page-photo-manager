package com.ericgibson.website.services;

import com.ericgibson.website.requestors.Service;
import com.ericgibson.website.utilities.ImageUtility;
import com.ericgibson.website.imaging.ThumbnailatorClient;
import com.ericgibson.website.webinterface.PhotosIndexPresenterSpy;
import com.ericgibson.website.repositories.AmazonClientFake;
import org.junit.Before;
import org.junit.Test;

import static com.ericgibson.website.TestingConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotosServicesTests {

    private final ImageUtility imageUtility = new ThumbnailatorClient();
    private final AmazonClientFake amazonClient = new AmazonClientFake(null);
    private final Service photosCreateService = new PhotosCreateService(imageUtility, amazonClient);
    private final PhotosIndexPresenterSpy presenter = new PhotosIndexPresenterSpy();
    private final Service photosIndexService = new PhotosIndexService(amazonClient, presenter);
    private final Service photosDestroyService = new PhotosDestroyService(amazonClient);
    private final PhotosIndexRequest photosIndexRequest = new PhotosIndexRequest();
    private final PhotosCreateRequest photosCreateRequest = new PhotosCreateRequest();
    private final PhotosDestroyRequest photosDestroyRequest = new PhotosDestroyRequest();

    @Before
    public void setup() {
        photosIndexRequest.storage = STORAGE;
        photosCreateRequest.file = FILE;
        photosCreateRequest.storage = STORAGE;
        photosDestroyRequest.storage = STORAGE;
        photosDestroyRequest.key = KEY;
    }

    @Test
    public void shouldCallPutObject() {
        photosCreateService.execute(photosCreateRequest);
        assertThat(amazonClient.shouldCallPutObjectMethod).isTrue();
    }

    @Test
    public void shouldCallListOfObjects() {
        photosIndexService.execute(photosIndexRequest);
        assertThat(amazonClient.shouldCallListOfKeysMethod).isTrue();
    }

    @Test
    public void shouldCallPresenterWithResponse() {
        photosIndexService.execute(photosIndexRequest);
        assertThat(presenter.shouldCallPresentMethod).isTrue();
    }

    @Test
    public void shouldCallDeleteObject() {
        photosDestroyService.execute(photosDestroyRequest);
        assertThat(amazonClient.shouldCallDeleteObjectMethod).isTrue();
    }
}
