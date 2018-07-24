package com.ericgibson.website.code;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.Test;

import static com.ericgibson.website.code.TestVariables.BUCKET_NAME;
import static com.ericgibson.website.code.TestVariables.FILE_PATH;
import static org.assertj.core.api.Assertions.assertThat;

public class AmazonClientTest {

    private AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();

    private AmazonClient amazonClient = new AmazonClient(amazonS3);

    @Test
    public void shouldCreateBucket() {
        assertThat(amazonClient.createBucket(BUCKET_NAME).getName()).isEqualTo(BUCKET_NAME);
    }

    @Test
    public void shouldPutObject() {
        assertThat(amazonClient.putObject(BUCKET_NAME, FILE_PATH)).isNotNull();
    }
}
