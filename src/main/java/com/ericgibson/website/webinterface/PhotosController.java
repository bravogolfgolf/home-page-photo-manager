package com.ericgibson.website.webinterface;

import com.ericgibson.website.builders.PhotosServiceBuilder;
import com.ericgibson.website.services.PhotosCreateRequest;
import com.ericgibson.website.services.PhotosDestroyRequest;
import com.ericgibson.website.services.PhotosIndexRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Controller
public class PhotosController {

    private static final String URL_BASE = "https://s3.amazonaws.com";
    private static final String STORAGE = "echo-juliet-golf";

    private final PhotosIndexPresenter photosIndexPresenter = new PhotosIndexPresenter();
    private final PhotosServiceBuilder builder = new PhotosServiceBuilder(photosIndexPresenter);

    @GetMapping("/")
    public String index(Model model) {
        PhotosIndexRequest request = new PhotosIndexRequest();
        request.storage = STORAGE;
        builder.create("Index").execute(request);
        List<String> keys = photosIndexPresenter.response();
        setModelAttributes(model, keys);
        return "index";
    }

    @GetMapping("/photos")
    public String photosIndex(Model model) {
        PhotosIndexRequest request = new PhotosIndexRequest();
        request.storage = STORAGE;
        builder.create("Index").execute(request);
        List<String> keys = photosIndexPresenter.response();
        setModelAttributes(model, keys);
        return "photos/index";
    }

    private void setModelAttributes(Model model, List<String> keys) {
        if (keys.size() > 0) {
            model.addAttribute("urlBase", URL_BASE);
            model.addAttribute("storage", STORAGE);
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
        request.storage = STORAGE;
        request.file = file;
        builder.create("Create").execute(request);
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
        PhotosDestroyRequest request = new PhotosDestroyRequest();
        request.storage = STORAGE;
        request.key = key;
        builder.create("Destroy").execute(request);
        return "redirect:/photos";
    }
}