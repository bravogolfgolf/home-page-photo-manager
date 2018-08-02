package com.ericgibson.website.services;

import org.junit.Test;

import static com.ericgibson.website.TestingConstants.BUCKET_NAME;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotosIndexTest {

    private final AmazonClientSpy amazonClient = new AmazonClientSpy(null);
    private final PhotosIndex photosIndex = new PhotosIndex(amazonClient);


    @Test
    public void shouldCallPutObject() {
        photosIndex.execute(BUCKET_NAME);
        assertThat(amazonClient.shouldCallListsOfObjectsMethod).isTrue();

    }
}
