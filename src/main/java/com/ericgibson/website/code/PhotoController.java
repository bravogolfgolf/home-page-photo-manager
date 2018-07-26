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

    @GetMapping("/photos")
    public String indexOfPhotos(Model model) {
        List<S3ObjectSummary> summaries = amazonClient.listObjects(BUCKET_NAME);
        model.addAttribute("summaries", summaries);
        return "photos/index";
    }

    @GetMapping("photos/new")
    public String newPhoto() {
        return "photos/new";
    }

    @PostMapping("photos/new")
    public String createPhoto(@RequestPart(value = "file") MultipartFile file) {
        amazonClient.putObject(BUCKET_NAME, file);
        return "redirect:/photos";
    }
}