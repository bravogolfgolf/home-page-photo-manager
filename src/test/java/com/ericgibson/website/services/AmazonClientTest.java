package com.ericgibson.website.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ericgibson.website.repositories.AmazonClient;
import com.ericgibson.website.gateways.CloudStorageGateway;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static com.ericgibson.website.TestingConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AmazonClientTest {

    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();

    private final CloudStorageGateway gateway = new AmazonClient(amazonS3);

    @After
    public void teardown() {
        amazonS3.listObjectsV2(BUCKET_NAME).getObjectSummaries().forEach((item) -> amazonS3.deleteObject(BUCKET_NAME, item.getKey()));
        amazonS3.deleteBucket(BUCKET_NAME);
    }

    @Test
    public void shouldTestCompleteStorageLifecycle() {
        gateway.createStorage(BUCKET_NAME);
        assertThat(amazonS3.doesBucketExistV2(BUCKET_NAME)).isTrue();
        assertThat(gateway.listObjectKeys(BUCKET_NAME)).isEmpty();
        gateway.putObject(BUCKET_NAME, KEY, FILE);
        List<String> keys = gateway.listObjectKeys(BUCKET_NAME);
        assertThat(keys).isNotEmpty();
        String key = keys.get(0);
        gateway.deleteObject(BUCKET_NAME, key);
        assertThat(amazonS3.listObjectsV2(BUCKET_NAME).getObjectSummaries()).isEmpty();
    }
}
