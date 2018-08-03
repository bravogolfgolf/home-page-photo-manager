package com.ericgibson.website.controllers;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ericgibson.website.builder.Service;
import com.ericgibson.website.presenters.PhotosIndexPresenter;
import com.ericgibson.website.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
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

    private final Service photosCreateService = new PhotosCreateService(BUCKET_NAME, imageFormatter, amazonClient);

    private final PhotosIndexPresenter photosIndexPresenter = new PhotosIndexPresenter();
    private final Service photosIndexService = new PhotosIndexService(amazonClient, photosIndexPresenter);

    private final PhotosDestroyService photosDestroyService = new PhotosDestroyService(amazonClient);

    @GetMapping("/")
    public String index(Model model) {
        PhotosIndexRequest photosIndexRequest = new PhotosIndexRequest();
        photosIndexRequest.bucket = BUCKET_NAME;
        photosIndexService.execute(photosIndexRequest);
        Map<String, List<S3ObjectSummary>> summaries = photosIndexPresenter.response();
        setModelAttributes(model, summaries);
        return "index";
    }

    @GetMapping("/photos")
    public String photosIndex(Model model) {
        PhotosIndexRequest photosIndexRequest = new PhotosIndexRequest();
        photosIndexRequest.bucket = BUCKET_NAME;
        photosIndexService.execute(photosIndexRequest);
        Map<String, List<S3ObjectSummary>> summaries = photosIndexPresenter.response();
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
    public String photosCreate(@RequestPart(value = "MultipartFile") MultipartFile multipartFile) {
        File file = createFileFrom(multipartFile);
        PhotosCreateRequest request = new PhotosCreateRequest();
        request.file = file;
        photosCreateService.execute(request);
        return "redirect:/photos";
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

    @DeleteMapping("/photos/{key}")
    public String photosDestroy(@PathVariable String key) {
        photosDestroyService.execute(BUCKET_NAME, key);
        return "redirect:/photos";
    }
}