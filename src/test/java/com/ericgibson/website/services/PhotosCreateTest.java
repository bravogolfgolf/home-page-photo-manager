package com.ericgibson.website.services;

import org.junit.Test;

import static com.ericgibson.website.TestingConstants.BUCKET_NAME;
import static com.ericgibson.website.TestingConstants.MOCK_MULTIPART_FILE;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotosCreateTest {

    private final ImageFormatter imageFormatter = new ImageFormatter();
    private final AmazonClientSpy amazonClient = new AmazonClientSpy(null);
    private final PhotosCreate photosCreate = new PhotosCreate(BUCKET_NAME, imageFormatter, amazonClient);


    @Test
    public void shouldCallPutObject() {
        photosCreate.execute(MOCK_MULTIPART_FILE);
        assertThat(amazonClient.shouldCallPutObjectMethod).isTrue();

    }
}
