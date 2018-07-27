package com.ericgibson.website.code;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
        Map<String, List<S3ObjectSummary>> summaries = amazonClient.listsOfObjects(BUCKET_NAME);
        model.addAttribute("photos", summaries.get("photos"));
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

    @DeleteMapping("/photos/{key}")
    public String photoDestroy(@PathVariable String key) {
        amazonClient.deleteObject(BUCKET_NAME, key);
        return "redirect:/photos";
    }
}