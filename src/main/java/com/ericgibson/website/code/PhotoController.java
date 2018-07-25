package com.ericgibson.website.code;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhotoController {

    @GetMapping("/photos")
    public String indexOfPhotos() {
        return "photos/index";
    }

    @GetMapping("photos/new")
    public String newPhoto(){
        return  "photos/new";
    }
}