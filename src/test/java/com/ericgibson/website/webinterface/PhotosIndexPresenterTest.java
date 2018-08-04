package com.ericgibson.website.webinterface;

import com.ericgibson.website.responders.PhotosIndexResponse;
import com.ericgibson.website.services.PhotosIndexServiceResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ericgibson.website.TestingConstants.KEY;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotosIndexPresenterTest {

    private final PhotosIndexPresenter presenter = new PhotosIndexPresenter();
    private final PhotosIndexResponse response = new PhotosIndexServiceResponse();

    @Before
    public void setup() {
        List<String> keys = new ArrayList<>();
        keys.add(KEY);
        response.setKeys(keys);
        presenter.present(response);
    }

    @Test
    public void shouldReturnMapOfKeys() {
        List<String> keys = presenter.response();
        assertThat(keys.get(0)).contains(KEY);
    }
}
