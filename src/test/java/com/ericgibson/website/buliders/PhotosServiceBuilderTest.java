package com.ericgibson.website.buliders;

import com.ericgibson.website.builders.PhotosServiceBuilder;
import com.ericgibson.website.services.PhotosCreateService;
import com.ericgibson.website.services.PhotosDestroyService;
import com.ericgibson.website.services.PhotosIndexService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PhotosServiceBuilderTest {

    private final PhotosServiceBuilder builder = new PhotosServiceBuilder();

    @Test
    public void shouldReturnPhotosCreateService() {
        PhotosCreateService service = (PhotosCreateService) builder.create("Create");
        assertThat(service).isInstanceOf(PhotosCreateService.class);
    }

    @Test
    public void shouldReturnPhotosIndexService() {
        PhotosIndexService service = (PhotosIndexService) builder.create("Index");
        assertThat(service).isInstanceOf(PhotosIndexService.class);
    }

    @Test
    public void shouldReturnPhotosDestroyService() {
        PhotosDestroyService service = (PhotosDestroyService) builder.create("Destroy");
        assertThat(service).isInstanceOf(PhotosDestroyService.class);
    }

    @Test
    public void shouldRThrowException() {
        assertThatThrownBy(() -> builder.create("Invalid Type")).hasMessage("Type not valid.");
    }
}
