package com.ericgibson.website;

import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestingConstants {
    public static final Integer ID_INTEGER = 1;
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "username@example.com";
    public static final String AUTHORITY_ADMIN = "ADMIN";
    public static final String AUTHORITY_USER = "USER";

    public static final String BUCKET_NAME = "tango-echo-sierra-tango";
    public static MockMultipartFile MOCK_MULTIPART_FILE = null;

    static {
        try {
            MOCK_MULTIPART_FILE = new MockMultipartFile("MultipartFile", "IMG_FILE.jpg", null, new FileInputStream(new File("IMG_0574.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}