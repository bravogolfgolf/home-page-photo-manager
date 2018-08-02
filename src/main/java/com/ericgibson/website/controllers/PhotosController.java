package com.ericgibson.website.controllers;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ericgibson.website.services.AmazonClient;
import com.ericgibson.website.services.ImageFormatter;
import com.ericgibson.website.services.PhotosCreate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PhotosController {

    private static final String URL_BASE = "https://s3.amazonaws.com";
    private static final String BUCKET_NAME = "echo-juliet-golf";
    private final ImageFormatter imageFormatter = new ImageFormatter();
    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();
    private final AmazonClient amazonClient = new AmazonClient(amazonS3);
    private final PhotosCreate photosCreate = new PhotosCreate(BUCKET_NAME, imageFormatter, amazonClient);

    @GetMapping("/")
    public String index(Model model) {
        Map<String, List<S3ObjectSummary>> summaries = amazonClient.listsOfObjects(BUCKET_NAME);
        setModelAttributes(model, summaries);
        return "index";
    }

    @GetMapping("/photos")
    public String photosIndex(Model model) {
        amazonClient.getOrCreateBucket(BUCKET_NAME);
        Map<String, List<S3ObjectSummary>> summaries = amazonClient.listsOfObjects(BUCKET_NAME);
        setModelAttributes(model, summaries);
        return "photos/index";
    }

    private void setModelAttributes(Model model, Map<String, List<S3ObjectSummary>> summaries) {
        List<String> keys;
        if (summaries.size() > 0) {
            keys = summaries.get("photos").stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
            model.addAttribute("urlBase", URL_BASE);
            model.addAttribute("bucket", BUCKET_NAME);
            model.addAttribute("keys", keys);
        }
    }

    @GetMapping("/photos/new")
    public String photosNew() {
        return "photos/new";
    }

    @PostMapping("/photos")
    public String photosCreate(@RequestPart(value = "MultipartFile") MultipartFile file) {
        photosCreate.execute(file);
        return "redirect:/photos";
    }

    @DeleteMapping("/photos/{key}")
    public String photosDestroy(@PathVariable String key) {
        amazonClient.deleteObject(BUCKET_NAME, key);
        return "redirect:/photos";
    }
}