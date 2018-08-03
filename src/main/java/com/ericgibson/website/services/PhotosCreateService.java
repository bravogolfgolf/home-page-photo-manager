package com.ericgibson.website.services;

import com.ericgibson.website.builders.Request;
import com.ericgibson.website.builders.Service;
import com.ericgibson.website.gateways.CloudStorageGateway;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.security.MessageDigest;

public class PhotosCreateService extends Service {
    private final String bucket;
    private final ImageFormatter imageFormatter;
    private final CloudStorageGateway gateway;

    public PhotosCreateService(String bucket, ImageFormatter imageFormatter, CloudStorageGateway gateway) {
        this.bucket = bucket;
        this.imageFormatter = imageFormatter;
        this.gateway = gateway;
    }

    @Override
    public void execute(Request request) {
        PhotosCreateRequest photosCreateRequest = (PhotosCreateRequest) request;
        imageFormatter.setOrientation(photosCreateRequest.file);
        File thumbnail = imageFormatter.createThumbnail(photosCreateRequest.file);
        String key = createKeyFrom(photosCreateRequest.file);
        gateway.putObject(bucket, key, photosCreateRequest.file);
        gateway.putObject(bucket, key + "thumbnail", thumbnail);
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