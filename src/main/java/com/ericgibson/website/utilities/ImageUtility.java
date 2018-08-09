package com.ericgibson.website.utilities;

import java.io.File;

public interface ImageUtility {
    File createThumbnail(File file);

    void setOrientation(File file);

    boolean isPortrait(File file);
}
