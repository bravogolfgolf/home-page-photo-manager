package com.ericgibson.website.imaging;

import com.ericgibson.website.utilities.ImageUtility;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class ThumbnailatorClientTest {

    private final ImageUtility imageUtility = new ThumbnailatorClient();
    private final File file = new File("IMG_TEST.jpg");

    @Test
    public void shouldCreateThumbnailPreservingRatio() throws IOException {
        File thumbnail = imageUtility.createThumbnail(file);
        BufferedImage fromFileImage = ImageIO.read(thumbnail);
        assertThat(fromFileImage.getHeight()).isEqualTo(200);
        thumbnail.delete();
    }

    @Test
    public void shouldModifyPhotoToHaveProperOrientation() {
//      Not sure how to test...
        imageUtility.setOrientation(file);
    }
}
