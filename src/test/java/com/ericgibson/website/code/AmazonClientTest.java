package com.ericgibson.website.code;

import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.auth.policy.resources.S3ObjectResource;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.Before;
import org.junit.Test;

import static com.ericgibson.website.code.TestVariables.BUCKET_NAME;
import static com.ericgibson.website.code.TestVariables.FILE_PATH;
import static org.assertj.core.api.Assertions.assertThat;

public class AmazonClientTest {

    private Statement allowAll = new Statement(Statement.Effect.Allow)
            .withPrincipals(Principal.AllUsers)
            .withActions(S3Actions.AllS3Actions)
            .withResources(new S3ObjectResource(BUCKET_NAME, "*"));

    private Policy policy = new Policy()
            .withStatements(allowAll);

    private AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();

    private AmazonClient amazonClient;

    @Before
    public void setup(){
//        amazonS3.setBucketPolicy(BUCKET_NAME, policy.toJson());
        amazonClient = new AmazonClient(amazonS3);
    }

    @Test
    public void shouldCreateBucket() {
        assertThat(amazonClient.createBucket(BUCKET_NAME).getName()).isEqualTo(BUCKET_NAME);
    }

    @Test
    public void shouldPutObject() {
        assertThat(amazonClient.putObject(BUCKET_NAME, FILE_PATH)).isNotNull();
    }
}
