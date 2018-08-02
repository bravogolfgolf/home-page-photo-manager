package com.ericgibson.website.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.After;
import org.junit.Test;

import static com.ericgibson.website.TestingConstants.*;
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
    public void shouldTestCompleteBucketLifecycle() {
        assertThat(amazonClient.getOrCreateBucket(BUCKET_NAME).getName()).isEqualTo(BUCKET_NAME);
        amazonClient.putObject(BUCKET_NAME, KEY, FILE);
        assertThat(amazonClient.listsOfObjects(BUCKET_NAME).get("photos")).isNotEmpty();
        String key = amazonS3.listObjectsV2(BUCKET_NAME).getObjectSummaries().get(0).getKey();
        amazonClient.deleteObject(BUCKET_NAME, key);
        assertThat(amazonS3.listObjectsV2(BUCKET_NAME).getObjectSummaries()).isEmpty();
    }
}
