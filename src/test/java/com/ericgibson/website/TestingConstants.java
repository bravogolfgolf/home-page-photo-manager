package com.ericgibson.website;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;

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
    public static final String KEY = "1234567890";
    static {
        try {
            FileCopyUtils.copy(new File("IMG_ORIG.jpg"), new File("IMG_TEST.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static final File FILE = new File("IMG_TEST.jpg");
    public static MockMultipartFile MOCK_MULTIPART_FILE = null;
    static {
        try {
            MOCK_MULTIPART_FILE = new MockMultipartFile("MultipartFile", "IMG_FILE.jpg", null, new FileInputStream(new File("IMG_TEST.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}