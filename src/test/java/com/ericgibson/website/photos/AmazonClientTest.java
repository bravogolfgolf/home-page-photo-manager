package com.ericgibson.website.photos;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.After;
import org.junit.Test;

import static com.ericgibson.website.TestingConstants.BUCKET_NAME;
import static com.ericgibson.website.TestingConstants.MOCK_MULTIPART_FILE;
import static org.assertj.core.api.Assertions.assertThat;

public class AmazonClientTest {

    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();

    private final AmazonClient amazonClient = new AmazonClient(amazonS3);

    @After
    public void teardown() {
        amazonS3.listObjectsV2(BUCKET_NAME).getObjectSummaries().forEach((item) -> amazonS3.deleteObject(BUCKET_NAME, item.getKey()));
        amazonS3.deleteBucket(BUCKET_NAME);
    }

    @Test
    public void shouldCreateBucket() {
        assertThat(amazonClient.getOrCreateBucket(BUCKET_NAME).getName()).isEqualTo(BUCKET_NAME);
    }

    @Test
    public void shouldPutObject() {
        assertThat(amazonClient.putObject(BUCKET_NAME, MOCK_MULTIPART_FILE)).isTrue();
    }

    @Test
    public void shouldListObjects() {
        amazonClient.putObject(BUCKET_NAME, MOCK_MULTIPART_FILE);
        assertThat(amazonClient.listsOfObjects(BUCKET_NAME).get("thumbnails")).isNotEmpty();
        assertThat(amazonClient.listsOfObjects(BUCKET_NAME).get("photos")).isNotEmpty();
    }

    @Test
    public void shouldDeleteObject() {
        amazonClient.putObject(BUCKET_NAME, MOCK_MULTIPART_FILE);
        String key = amazonS3.listObjectsV2(BUCKET_NAME).getObjectSummaries().get(0).getKey();
        amazonClient.deleteObject(BUCKET_NAME, key);
        assertThat(amazonS3.listObjectsV2(BUCKET_NAME).getObjectSummaries()).isEmpty();
    }
}
