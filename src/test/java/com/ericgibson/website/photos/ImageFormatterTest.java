package com.ericgibson.website.photos;

import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageFormatterTest {

    private final ImageFormatter imageFormatter = new ImageFormatter();
    private final File file = new File("IMG_0574.jpg");

    @Test
    public void shouldCreatePhotoManipulate() {
        File thumbnail = imageFormatter.createThumbnail(file);
        assertThat(thumbnail.length()).isLessThan(file.length());
        thumbnail.delete();
    }

    @Test
    public void shouldCreateSameSizePhotoWithProperOrientationMetadata() {
//      Not sure how to test...
        imageFormatter.setOrientation(file);
    }
}
