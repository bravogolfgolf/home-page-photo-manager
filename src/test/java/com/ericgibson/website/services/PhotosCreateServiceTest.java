package com.ericgibson.website.services;

import org.junit.Test;

import static com.ericgibson.website.TestingConstants.BUCKET_NAME;
import static com.ericgibson.website.TestingConstants.FILE;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotosCreateServiceTest {

    private final ImageFormatter imageFormatter = new ImageFormatter();
    private final AmazonClientFake amazonClient = new AmazonClientFake(null);
    private final PhotosCreateService photosCreateService = new PhotosCreateService(BUCKET_NAME, imageFormatter, amazonClient);


    @Test
    public void shouldCallPutObject() {
        photosCreateService.execute(FILE);
        assertThat(amazonClient.shouldCallPutObjectMethod).isTrue();

    }
}
