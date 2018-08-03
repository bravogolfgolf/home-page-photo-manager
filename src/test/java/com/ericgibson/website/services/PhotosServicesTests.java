package com.ericgibson.website.services;

import com.ericgibson.website.builder.Service;
import com.ericgibson.website.presenters.PhotosIndexPresenterSpy;
import org.junit.Before;
import org.junit.Test;

import static com.ericgibson.website.TestingConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotosServicesTests {

    private final ImageFormatter imageFormatter = new ImageFormatter();
    private final AmazonClientFake amazonClient = new AmazonClientFake(null);
    private final Service photosCreateService = new PhotosCreateService(BUCKET_NAME, imageFormatter, amazonClient);
    private final PhotosIndexPresenterSpy presenter = new PhotosIndexPresenterSpy();
    private final Service photosIndexService = new PhotosIndexService(amazonClient, presenter);
    private final PhotosDestroyService photosDestroyService = new PhotosDestroyService(amazonClient);
    private final PhotosIndexRequest photosIndexRequest = new PhotosIndexRequest();
    private final PhotosCreateRequest photosCreateRequest = new PhotosCreateRequest();

    @Before
    public void setup() {
        photosIndexRequest.bucket = BUCKET_NAME;
        photosCreateRequest.file = FILE;
    }

    @Test
    public void shouldCallPutObject() {
        photosCreateService.execute(photosCreateRequest);
        assertThat(amazonClient.shouldCallPutObjectMethod).isTrue();
    }

    @Test
    public void shouldCallListOfObjects() {
        photosIndexService.execute(photosIndexRequest);
        assertThat(amazonClient.shouldCallListOfObjectsMethod).isTrue();
    }

    @Test
    public void shouldCallPresenterWithResponse() {
        photosIndexService.execute(photosIndexRequest);
        assertThat(presenter.shouldCallPresentMethod).isTrue();
    }

    @Test
    public void shouldCallDeleteObject() {
        photosDestroyService.execute(BUCKET_NAME, KEY);
        assertThat(amazonClient.shouldCallDeleteObjectMethod).isTrue();
    }
}
