package com.ericgibson.website.code;

import org.springframework.mock.web.MockMultipartFile;

import java.util.Date;

class TestingConstants {
    static final Integer ID_INTEGER = 1;
    static final String USERNAME = "username";
    static final String PASSWORD = "password";
    static final String EMAIL = "username@example.com";
    static final String AUTHORITY_ADMIN = "ADMIN";
    static final String AUTHORITY_USER = "USER";

    static final String BUCKET_NAME = "tango-echo-sierra-tango";
    static final MockMultipartFile MOCK_MULTIPART_FILE = new MockMultipartFile("file", "PutObjectTestFile.txt", null, new Date().toString().getBytes());
}