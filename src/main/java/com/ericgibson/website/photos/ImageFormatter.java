package com.ericgibson.website.photos;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

class ImageFormatter {

    File createThumbnail(File file) {
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

    void setOrientation(File file) {
        try {
            Thumbnails.of(file)
                    .scale(1)
                    .toFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
