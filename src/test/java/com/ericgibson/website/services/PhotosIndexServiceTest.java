package com.ericgibson.website.services;

import com.ericgibson.website.presenters.PhotosIndexPresenterSpy;
import org.junit.Before;
import org.junit.Test;

import static com.ericgibson.website.TestingConstants.BUCKET_NAME;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotosIndexServiceTest {

    private final AmazonClientFake amazonClient = new AmazonClientFake(null);
    private final PhotosIndexPresenterSpy presenter = new PhotosIndexPresenterSpy();
    private final PhotosIndexService photosIndexService = new PhotosIndexService(amazonClient, presenter);


    @Before
    public void setup(){
        photosIndexService.execute(BUCKET_NAME);
    }

    @Test
    public void shouldCallPutObject() {
        assertThat(amazonClient.shouldCallListsOfObjectsMethod).isTrue();
    }

    @Test
    public void shouldCallPresenterWithResponse(){
        assertThat(presenter.shouldCallPresentMethod).isTrue();
    }
}
