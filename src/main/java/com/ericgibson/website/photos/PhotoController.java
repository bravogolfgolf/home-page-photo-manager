package com.ericgibson.website.photos;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PhotoController {

    private static final String URL_BASE = "https://s3.amazonaws.com";
    private static final String BUCKET_NAME = "echo-juliet-golf";
    private final AmazonClient amazonClient;

    public PhotoController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @GetMapping("/")
    public String index(Model model) {
        Map<String, List<S3ObjectSummary>> summaries = amazonClient.listsOfObjects(BUCKET_NAME);
        someMethodName(model, summaries);
        return "index";
    }

    @GetMapping("/photos")
    public String photosIndex(Model model) {
        amazonClient.getOrCreateBucket(BUCKET_NAME);
        Map<String, List<S3ObjectSummary>> summaries = amazonClient.listsOfObjects(BUCKET_NAME);
        someMethodName(model, summaries);
        return "photos/index";
    }

    private void someMethodName(Model model, Map<String, List<S3ObjectSummary>> summaries) {
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
        amazonClient.putObject(BUCKET_NAME, file);
        return "redirect:/photos";
    }

    @DeleteMapping("/photos/{key}")
    public String photoDestroy(@PathVariable String key) {
        amazonClient.deleteObject(BUCKET_NAME, key);
        return "redirect:/photos";
    }
}