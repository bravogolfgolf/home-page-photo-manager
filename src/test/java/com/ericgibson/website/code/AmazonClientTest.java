package com.ericgibson.website.code;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.After;
import org.junit.Test;

import static com.ericgibson.website.code.TestingConstants.BUCKET_NAME;
import static com.ericgibson.website.code.TestingConstants.MOCK_MULTIPART_FILE;
import static org.assertj.core.api.Assertions.assertThat;

public class AmazonClientTest {

    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();

    private final AmazonClient amazonClient = new AmazonClient(amazonS3);

    @After
    public void teardown() {
        amazonS3.deleteObject(BUCKET_NAME, MOCK_MULTIPART_FILE.getOriginalFilename());
        amazonS3.deleteBucket(BUCKET_NAME);
    }

    @Test
    public void shouldCreateBucket() {
        assertThat(amazonClient.createBucket(BUCKET_NAME).getName()).isEqualTo(BUCKET_NAME);
    }

    @Test
    public void shouldPutObject() {
        assertThat(amazonClient.putObject(BUCKET_NAME, MOCK_MULTIPART_FILE)).isNotNull();
    }

    @Test
    public void shouldListObjects() {
        amazonClient.putObject(BUCKET_NAME, MOCK_MULTIPART_FILE);
        assertThat(amazonClient.listObjects(BUCKET_NAME)).isNotEmpty();
    }

    @Test
    public void shouldDeleteObject() {
        amazonClient.putObject(BUCKET_NAME, MOCK_MULTIPART_FILE);
        amazonClient.deleteObject(BUCKET_NAME, MOCK_MULTIPART_FILE.getOriginalFilename());
        assertThat(amazonClient.listObjects(BUCKET_NAME)).isEmpty();
    }
}
