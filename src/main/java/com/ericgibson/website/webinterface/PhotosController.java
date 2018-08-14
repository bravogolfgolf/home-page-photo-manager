package com.ericgibson.website.webinterface;

import com.ericgibson.website.builders.PhotosRequestBuilder;
import com.ericgibson.website.builders.PhotosServiceBuilder;
import com.ericgibson.website.builders.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ericgibson.website.WebsiteApplication.STORAGE;
import static com.ericgibson.website.WebsiteApplication.URL_BASE;

@Controller
public class PhotosController {

    private final Map<String, Object> map = new HashMap<>();
    private final PhotosIndexPresenter presenter;
    private final PhotosRequestBuilder requestBuilder;
    private final PhotosServiceBuilder serviceBuilder;

    public PhotosController(PhotosServiceBuilder serviceBuilder, PhotosRequestBuilder requestBuilder, PhotosIndexPresenter presenter) {
        this.serviceBuilder = serviceBuilder;
        this.requestBuilder = requestBuilder;
        this.presenter = presenter;
    }

    @GetMapping(value = "/js/photos.js")
    public String common(Model model) {
        map.put("storage", STORAGE);
        Request request = requestBuilder.create("Index", map);
        serviceBuilder.create("Index").execute(request);
        List<String> keys = presenter.response();
        setModelAttributes(model, keys);
        return "photos.js";
    }

    @GetMapping("/")
    public String index(Model model) {
        map.put("storage", STORAGE);
        Request request = requestBuilder.create("Index", map);
        serviceBuilder.create("Index").execute(request);
        List<String> keys = presenter.response();
        setModelAttributes(model, keys);
        return "index.html";
    }

    @GetMapping("/photos")
    public String photosIndex(Model model) {
        map.put("storage", STORAGE);
        Request request = requestBuilder.create("Index", map);
        serviceBuilder.create("Index").execute(request);
        List<String> keys = presenter.response();
        setModelAttributes(model, keys);
        return "photos/index.html";
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
        return "photos/new.html";
    }

    @PostMapping("/photos")
    public String photosCreate(@RequestPart(value = "MultipartFile") MultipartFile multipartFile) {
        File file = createFileFrom(multipartFile);
        map.put("storage", STORAGE);
        map.put("file", file);
        Request request = requestBuilder.create("Create", map);
        serviceBuilder.create("Create").execute(request);
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
        map.put("storage", STORAGE);
        map.put("key", key);
        Request request = requestBuilder.create("Destroy", map);
        serviceBuilder.create("Destroy").execute(request);
        return "redirect:/photos";
    }
}