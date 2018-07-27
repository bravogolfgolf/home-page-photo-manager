package com.ericgibson.website.code;

import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class TestingConstants {
    static final Integer ID_INTEGER = 1;
    static final String USERNAME = "username";
    static final String PASSWORD = "password";
    static final String EMAIL = "username@example.com";
    static final String AUTHORITY_ADMIN = "ADMIN";
    static final String AUTHORITY_USER = "USER";

    static final String BUCKET_NAME = "tango-echo-sierra-tango";
    static MockMultipartFile MOCK_MULTIPART_FILE = null;

    static {
        try {
            MOCK_MULTIPART_FILE = new MockMultipartFile("MultipartFile", "IMG_FILE.jpg", null, new FileInputStream(new File("IMG_0574.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}