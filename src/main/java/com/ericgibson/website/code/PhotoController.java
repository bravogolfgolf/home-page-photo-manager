package com.ericgibson.website.code;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhotoController {

    @GetMapping("/photos")
    public String index() {
        return "photos/index";
    }
}