package com.ericgibson.website.services;

import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.requestors.Request;
import com.ericgibson.website.requestors.Service;
import com.ericgibson.website.utilities.ImageUtility;

import jakarta.xml.bind.DatatypeConverter;
import java.io.File;
import java.security.MessageDigest;

public class PhotosCreateService implements Service {
    private final ImageUtility utility;
    private final CloudStorageGateway gateway;

    public PhotosCreateService(ImageUtility utility, CloudStorageGateway gateway) {
        this.utility = utility;
        this.gateway = gateway;
    }

    @Override
    public void execute(Request request) {
        PhotosCreateServiceRequest photosCreateServiceRequest = (PhotosCreateServiceRequest) request;
        utility.setOrientation(photosCreateServiceRequest.getFile());
        File thumbnail = utility.createThumbnail(photosCreateServiceRequest.getFile());
        String key = createKeyFrom(photosCreateServiceRequest.getFile());
        gateway.putObject(photosCreateServiceRequest.getStorage(), key, photosCreateServiceRequest.getFile());
        gateway.putObject(photosCreateServiceRequest.getStorage(), key + "thumbnail", thumbnail);
        try {
            thumbnail.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}