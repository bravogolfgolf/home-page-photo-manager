package com.ericgibson.website.buliders;

import com.ericgibson.website.builders.PhotosServiceBuilder;
import com.ericgibson.website.builders.Service;
import com.ericgibson.website.services.PhotosCreateService;
import com.ericgibson.website.services.PhotosDestroyService;
import com.ericgibson.website.services.PhotosIndexService;
import com.ericgibson.website.webinterface.PhotosIndexPresenter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PhotosServiceBuilderTest {

    private final PhotosIndexPresenter presenter = new PhotosIndexPresenter();
    private final PhotosServiceBuilder builder = new PhotosServiceBuilder(presenter);

    @Test
    public void shouldReturnPhotosCreateService() {
        Service service = builder.create("Create");
        assertThat(service).isInstanceOf(PhotosCreateService.class);
    }

    @Test
    public void shouldReturnPhotosIndexService() {
        Service service = builder.create("Index");
        assertThat(service).isInstanceOf(PhotosIndexService.class);
    }

    @Test
    public void shouldReturnPhotosDestroyService() {
        Service service = builder.create("Destroy");
        assertThat(service).isInstanceOf(PhotosDestroyService.class);
    }

    @Test
    public void shouldThrowException() {
        assertThatThrownBy(() -> builder.create("Invalid Type")).hasMessage("Type not valid.");
    }
}
