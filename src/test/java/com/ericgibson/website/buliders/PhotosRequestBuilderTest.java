package com.ericgibson.website.buliders;

import com.ericgibson.website.builders.PhotosRequestBuilder;
import com.ericgibson.website.requestors.Request;
import com.ericgibson.website.services.PhotosCreateRequest;
import com.ericgibson.website.services.PhotosDestroyRequest;
import com.ericgibson.website.services.PhotosIndexRequest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ericgibson.website.TestingConstants.FILE;
import static com.ericgibson.website.TestingConstants.KEY;
import static com.ericgibson.website.TestingConstants.STORAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PhotosRequestBuilderTest {

    private final Map<String, Object> map = new HashMap<>();
    private final PhotosRequestBuilder builder = new PhotosRequestBuilder();

    @Test
    public void shouldReturnPhotosCreateRequest() {
        map.put("storage", STORAGE);
        map.put("file", FILE);
        Request request = builder.create("Create", map);
        assertThat(request).isInstanceOf(PhotosCreateRequest.class);
        assertThat(((PhotosCreateRequest)request).storage).isEqualTo(STORAGE);
        assertThat(((PhotosCreateRequest)request).file).isEqualTo(FILE);
    }

    @Test
    public void shouldReturnPhotosIndexRequest() {
        map.put("storage", STORAGE);
        Request request = builder.create("Index", map);
        assertThat(request).isInstanceOf(PhotosIndexRequest.class);
        assertThat(((PhotosIndexRequest)request).storage).isEqualTo(STORAGE);
    }

    @Test
    public void shouldReturnPhotosDestroyRequest() {
        map.put("storage", STORAGE);
        map.put("key", KEY);
        Request request = builder.create("Destroy", map);
        assertThat(request).isInstanceOf(PhotosDestroyRequest.class);
        assertThat(((PhotosDestroyRequest)request).storage).isEqualTo(STORAGE);
        assertThat(((PhotosDestroyRequest)request).key).isEqualTo(KEY);
    }

    @Test
    public void shouldThrowException() {
        assertThatThrownBy(() -> builder.create("Invalid Type", map)).hasMessage("Type not valid.");
    }
}
