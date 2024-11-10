package com.ericgibson.website.webinterface;

import com.ericgibson.website.requestors.Request;
import com.ericgibson.website.requestors.RequestBuilder;
import com.ericgibson.website.requestors.ServiceBuilder;
import com.ericgibson.website.responders.Responder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PhotosController {

    private static final String URL_BASE = "https://s3.amazonaws.com";
    private static final String STORAGE = "echo-juliet-golf";

    private final Map<String, Object> map = new HashMap<>();
    private final Responder presenter;
    private final RequestBuilder requestBuilder;
    private final ServiceBuilder serviceBuilder;

    public PhotosController(ServiceBuilder serviceBuilder, RequestBuilder requestBuilder, Responder presenter) {
        this.serviceBuilder = serviceBuilder;
        this.requestBuilder = requestBuilder;
        this.presenter = presenter;
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
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
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @PostMapping("/photos/delete/{key}")
    public String photosDestroy(@PathVariable String key) {
        map.put("storage", STORAGE);
        map.put("key", key);
        Request request = requestBuilder.create("Destroy", map);
        serviceBuilder.create("Destroy").execute(request);
        return "redirect:/photos";
    }
}