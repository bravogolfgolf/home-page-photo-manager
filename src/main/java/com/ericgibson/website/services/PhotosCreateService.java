package com.ericgibson.website.services;

import com.ericgibson.website.builders.Request;
import com.ericgibson.website.builders.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.security.MessageDigest;

public class PhotosCreateService extends Service {
    private final String bucket;
    private final ImageFormatter imageFormatter;
    private final AmazonClient amazonClient;

    public PhotosCreateService(String bucket, ImageFormatter imageFormatter, AmazonClient amazonClient) {
        this.bucket = bucket;
        this.imageFormatter = imageFormatter;
        this.amazonClient = amazonClient;
    }

    @Override
    public void execute(Request request) {
        PhotosCreateRequest photosCreateRequest = (PhotosCreateRequest) request;
        imageFormatter.setOrientation(photosCreateRequest.file);
        File thumbnail = imageFormatter.createThumbnail(photosCreateRequest.file);
        String key = createKeyFrom(photosCreateRequest.file);
        amazonClient.putObject(bucket, key, photosCreateRequest.file);
        amazonClient.putObject(bucket, key + "thumbnail", thumbnail);
        deleteFile(photosCreateRequest.file);
        deleteFile(thumbnail);
    }

    private String createKeyFrom(File file) {
        String result = null;
        try {
            String fileName = file.getName();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(fileName.getBytes());
            result = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void deleteFile(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}