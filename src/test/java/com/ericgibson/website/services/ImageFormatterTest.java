package com.ericgibson.website.services;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class ImageFormatterTest {

    private final ImageFormatter imageFormatter = new ImageFormatter();
    private final File file = new File("IMG_0574.jpg");

    @Test
    public void shouldCreateThumbnailPreservingRatio() throws IOException {
        File thumbnail = imageFormatter.createThumbnail(file);
        BufferedImage fromFileImage = ImageIO.read(thumbnail);
        assertThat(fromFileImage.getWidth()).isEqualTo(200);
        thumbnail.delete();
    }

    @Test
    public void shouldModifyPhotoToHaveProperOrientation() {
//      Not sure how to test...
        imageFormatter.setOrientation(file);
    }
}
