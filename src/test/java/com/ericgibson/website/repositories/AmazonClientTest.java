package com.ericgibson.website.repositories;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
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
        amazonS3.listObjectsV2(STORAGE).getObjectSummaries().forEach((item) -> amazonS3.deleteObject(STORAGE, item.getKey()));
        amazonS3.deleteBucket(STORAGE);
    }

    @Test
    public void shouldTestCompleteStorageLifecycle() {
        gateway.createStorage(STORAGE);
        assertThat(amazonS3.doesBucketExistV2(STORAGE)).isTrue();
        assertThat(gateway.listObjectKeys(STORAGE)).isEmpty();
        gateway.putObject(STORAGE, KEY, FILE);
        List<String> keys = gateway.listObjectKeys(STORAGE);
        assertThat(keys).isNotEmpty();
        String key = keys.get(0);
        gateway.deleteObject(STORAGE, key);
        assertThat(amazonS3.listObjectsV2(STORAGE).getObjectSummaries()).isEmpty();
    }
}
