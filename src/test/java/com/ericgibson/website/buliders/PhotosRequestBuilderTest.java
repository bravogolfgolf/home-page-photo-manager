package com.ericgibson.website.buliders;

import com.ericgibson.website.builders.PhotosRequestBuilder;
import com.ericgibson.website.requestors.Request;
import com.ericgibson.website.services.PhotosCreateServiceRequest;
import com.ericgibson.website.services.PhotosDestroyServiceRequest;
import com.ericgibson.website.services.PhotosIndexServiceRequest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ericgibson.website.TestingConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PhotosRequestBuilderTest {

    private final Map<String, Object> map = new HashMap<>();

    private final PhotosCreateServiceRequest create = new PhotosCreateServiceRequest();
    private final PhotosIndexServiceRequest index = new PhotosIndexServiceRequest();
    private final PhotosDestroyServiceRequest destroy = new PhotosDestroyServiceRequest();

    private final Map<String, Request> requests = new HashMap<String, Request>() {{
        put("Create", create);
        put("Index", index);
        put("Destroy", destroy);
    }};

    private final PhotosRequestBuilder builder = new PhotosRequestBuilder(requests);

    @Test
    public void shouldReturnPhotosCreateRequest() {
        map.put("storage", STORAGE);
        map.put("file", FILE);
        Request request = builder.create("Create", map);
        assertThat(request).isInstanceOf(PhotosCreateServiceRequest.class);
        assertThat(((PhotosCreateServiceRequest) request).getStorage()).isEqualTo(STORAGE);
        assertThat(((PhotosCreateServiceRequest) request).getFile()).isEqualTo(FILE);
    }

    @Test
    public void shouldReturnPhotosIndexRequest() {
        map.put("storage", STORAGE);
        Request request = builder.create("Index", map);
        assertThat(request).isInstanceOf(PhotosIndexServiceRequest.class);
        assertThat(((PhotosIndexServiceRequest) request).getStorage()).isEqualTo(STORAGE);
    }

    @Test
    public void shouldReturnPhotosDestroyRequest() {
        map.put("storage", STORAGE);
        map.put("key", KEY);
        Request request = builder.create("Destroy", map);
        assertThat(request).isInstanceOf(PhotosDestroyServiceRequest.class);
        assertThat(((PhotosDestroyServiceRequest) request).getStorage()).isEqualTo(STORAGE);
        assertThat(((PhotosDestroyServiceRequest) request).getKey()).isEqualTo(KEY);
    }

    @Test
    public void shouldThrowException() {
        assertThatThrownBy(() -> builder.create("Invalid Type", map)).hasMessage("Type not valid.");
    }
}
