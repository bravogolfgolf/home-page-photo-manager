package com.ericgibson.website.imaging;

import com.ericgibson.website.utilities.ImageUtility;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class ThumbnailatorClient implements ImageUtility {

    @Override
    public File createThumbnail(File file) {
        File thumbnail = null;
        try {
            thumbnail = new File("Thumbnail.png");
            Thumbnails.of(file)
                    .size(200, 200)
                    .keepAspectRatio(true)
                    .toFile(thumbnail);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thumbnail;
    }

    @Override
    public void setOrientation(File file) {
        try {
            Thumbnails.of(file)
                    .scale(1)
                    .toFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
