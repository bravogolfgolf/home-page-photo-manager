package com.ericgibson.website.webinterface;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ericgibson.website.builders.Service;
import com.ericgibson.website.gateways.CloudStorageGateway;
import com.ericgibson.website.imaging.ThumbnailatorClient;
import com.ericgibson.website.repositories.AmazonClient;
import com.ericgibson.website.services.*;
import com.ericgibson.website.utilities.ImageUtility;
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

    private final ImageUtility imageUtility = new ThumbnailatorClient();

    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();
    private final CloudStorageGateway gateway = new AmazonClient(amazonS3);

    private final Service photosCreateService = new PhotosCreateService(STORAGE, imageUtility, gateway);

    private final PhotosIndexPresenter photosIndexPresenter = new PhotosIndexPresenter();
    private final Service photosIndexService = new PhotosIndexService(gateway, photosIndexPresenter);

    private final Service photosDestroyService = new PhotosDestroyService(gateway);

    @GetMapping("/")
    public String index(Model model) {
        PhotosIndexRequest photosIndexRequest = new PhotosIndexRequest();
        photosIndexRequest.storage = STORAGE;
        photosIndexService.execute(photosIndexRequest);
        List<String> keys = photosIndexPresenter.response();
        setModelAttributes(model, keys);
        return "index";
    }

    @GetMapping("/photos")
    public String photosIndex(Model model) {
        PhotosIndexRequest photosIndexRequest = new PhotosIndexRequest();
        photosIndexRequest.storage = STORAGE;
        photosIndexService.execute(photosIndexRequest);
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
        PhotosDestroyRequest photosDestroyRequest = new PhotosDestroyRequest();
        photosDestroyRequest.storage = STORAGE;
        photosDestroyRequest.key = key;
        photosDestroyService.execute(photosDestroyRequest);
        return "redirect:/photos";
    }
}