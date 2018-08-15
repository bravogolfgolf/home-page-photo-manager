package com.ericgibson.website.services;

import com.ericgibson.website.imaging.ThumbnailatorClient;
import com.ericgibson.website.repositories.AmazonClientFake;
import com.ericgibson.website.requestors.Service;
import com.ericgibson.website.utilities.ImageUtility;
import com.ericgibson.website.webinterface.PhotosIndexPresenterSpy;
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
    private final PhotosIndexServiceRequest photosIndexServiceRequest = new PhotosIndexServiceRequest();
    private final PhotosCreateServiceRequest photosCreateServiceRequest = new PhotosCreateServiceRequest();
    private final PhotosDestroyServiceRequest photosDestroyServiceRequest = new PhotosDestroyServiceRequest();

    @Before
    public void setup() {
        photosIndexServiceRequest.setStorage(STORAGE);
        photosCreateServiceRequest.setFile(FILE);
        photosCreateServiceRequest.setStorage(STORAGE);
        photosDestroyServiceRequest.setStorage(STORAGE);
        photosDestroyServiceRequest.setKey(KEY);
    }

    @Test
    public void shouldCallPutObject() {
        photosCreateService.execute(photosCreateServiceRequest);
        assertThat(amazonClient.shouldCallPutObjectMethod).isTrue();
    }

    @Test
    public void shouldCallListOfObjects() {
        photosIndexService.execute(photosIndexServiceRequest);
        assertThat(amazonClient.shouldCallListOfKeysMethod).isTrue();
    }

    @Test
    public void shouldCallPresenterWithResponse() {
        photosIndexService.execute(photosIndexServiceRequest);
        assertThat(presenter.shouldCallPresentMethod).isTrue();
    }

    @Test
    public void shouldCallDeleteObject() {
        photosDestroyService.execute(photosDestroyServiceRequest);
        assertThat(amazonClient.shouldCallDeleteObjectMethod).isTrue();
    }
}
