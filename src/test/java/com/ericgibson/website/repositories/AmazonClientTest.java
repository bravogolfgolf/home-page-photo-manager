package com.ericgibson.website.repositories;

import com.ericgibson.website.gateways.CloudStorageGateway;
import org.junit.Test;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;

import static com.ericgibson.website.TestingConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AmazonClientTest {

    private final S3Client amazonS3 = S3Client.builder()
            .region(Region.US_EAST_1)
            .build();

    private final CloudStorageGateway gateway = new AmazonClient(amazonS3);

    @Test
    public void shouldTestCompleteStorageLifecycle() {
        assertThat(gateway.listObjectKeys(STORAGE)).isEmpty();
        gateway.putObject(STORAGE, KEY, FILE);
        List<String> keys = gateway.listObjectKeys(STORAGE);
        assertThat(keys).isNotEmpty();
        String key = keys.get(0);
        gateway.deleteObject(STORAGE, key);
        keys = gateway.listObjectKeys(STORAGE);
        assertThat(keys).isEmpty();
    }
}
