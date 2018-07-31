package com.ericgibson.website.photos;

import org.junit.Test;

import static com.ericgibson.website.TestingConstants.BUCKET_NAME;
import static com.ericgibson.website.TestingConstants.MOCK_MULTIPART_FILE;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotoCreateTest {

    private final ImageFormatter imageFormatter = new ImageFormatter();
    private final AmazonClientSpy amazonClient = new AmazonClientSpy(null);
    private final PhotoCreate photoCreate = new PhotoCreate(BUCKET_NAME, imageFormatter, amazonClient);


    @Test
    public void shouldCallPutObject() {
        photoCreate.execute(MOCK_MULTIPART_FILE);
        assertThat(amazonClient.shouldCallPutObjectMethod).isTrue();

    }
}
