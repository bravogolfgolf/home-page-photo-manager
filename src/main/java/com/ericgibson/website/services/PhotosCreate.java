package com.ericgibson.website.services;

import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;

public class PhotosCreate {
    private final String bucket;
    private final ImageFormatter imageFormatter;
    private final AmazonClient amazonClient;

    public PhotosCreate(String bucket, ImageFormatter imageFormatter, AmazonClient amazonClient) {
        this.bucket = bucket;
        this.imageFormatter = imageFormatter;
        this.amazonClient = amazonClient;
    }

    public void execute(MultipartFile multipartFile) {
        File file = createFileFrom(multipartFile);
        imageFormatter.setOrientation(file);
        File thumbnail = imageFormatter.createThumbnail(file);
        String key = createKeyFrom(file);
        amazonClient.putObject(bucket, key, file);
        amazonClient.putObject(bucket, key + "thumbnail", thumbnail);
        deleteFile(file);
        deleteFile(thumbnail);
    }

    private File createFileFrom(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename() == null ? "" : multipartFile.getOriginalFilename();
        File file = new File(fileName);
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(multipartFile.getBytes());
            stream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

    private void deleteFile(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}