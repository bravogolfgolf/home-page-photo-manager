package com.ericgibson.website.services;

import com.ericgibson.website.presenters.PhotosIndexPresenterSpy;
import org.junit.Test;

import static com.ericgibson.website.TestingConstants.BUCKET_NAME;
import static com.ericgibson.website.TestingConstants.FILE;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotosServicesTests {

    private final ImageFormatter imageFormatter = new ImageFormatter();
    private final AmazonClientFake amazonClient = new AmazonClientFake(null);
    private final PhotosCreateService photosCreateService = new PhotosCreateService(BUCKET_NAME, imageFormatter, amazonClient);
    private final PhotosIndexPresenterSpy presenter = new PhotosIndexPresenterSpy();
    private final PhotosIndexService photosIndexService = new PhotosIndexService(amazonClient, presenter);

    @Test
    public void shouldCallPutObject() {
        photosCreateService.execute(FILE);
        assertThat(amazonClient.shouldCallPutObjectMethod).isTrue();
    }

    @Test
    public void shouldCallListOfObjects() {
        photosIndexService.execute(BUCKET_NAME);
        assertThat(amazonClient.shouldCallListOfObjectsMethod).isTrue();
    }

    @Test
    public void shouldCallPresenterWithResponse(){
        photosIndexService.execute(BUCKET_NAME);
        assertThat(presenter.shouldCallPresentMethod).isTrue();
    }
}
