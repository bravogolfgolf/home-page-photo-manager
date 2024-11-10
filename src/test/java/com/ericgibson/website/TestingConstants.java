package com.ericgibson.website;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestingConstants {
    public static final String STORAGE = "tango-echo-sierra-tango";
    public static final String KEY = "1234567890";

    static {
        try {
            FileCopyUtils.copy(new File("IMG_ORIG.jpg"), new File("IMG_TEST.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final File FILE = new File("IMG_TEST.jpg");
    public static MockMultipartFile MOCK_MULTIPART_FILE;

    static {
        try {
            MOCK_MULTIPART_FILE = new MockMultipartFile("MultipartFile", "IMG_FILE.jpg", null, new FileInputStream("IMG_TEST.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}