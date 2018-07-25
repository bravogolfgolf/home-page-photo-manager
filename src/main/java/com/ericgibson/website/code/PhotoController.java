package com.ericgibson.website.code;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PhotoController {

    @GetMapping("/photos")
    public String indexOfPhotos(Model model) {
        model.addAttribute("file", "FileName.txt");
        return "photos/index";
    }

    @GetMapping("photos/new")
    public String newPhoto() {
        return "photos/new";
    }

    @PostMapping("photos/new")
    public String createPhoto(String filename) {
        return "redirect:/photos";
    }
}