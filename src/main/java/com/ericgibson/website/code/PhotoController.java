package com.ericgibson.website.code;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class PhotoController {

    private static final String BUCKET_NAME = "echo-juliet-golf";
    private final AmazonClient amazonClient;

    public PhotoController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/photos")
    public String photosIndex(Model model) {
        amazonClient.createBucket(BUCKET_NAME);
        List<S3ObjectSummary> summaries = amazonClient.listObjectsThumbnails(BUCKET_NAME);
        model.addAttribute("summaries", summaries);
        return "photos/index";
    }

    @GetMapping("/photos/new")
    public String photosNew() {
        return "photos/new";
    }

    @PostMapping("/photos")
    public String photosCreate(@RequestPart(value = "MultipartFile") MultipartFile file) {
        amazonClient.putObject(BUCKET_NAME, file);
        return "redirect:/photos";
    }
}