package com.tarana.soyafoods.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/products")
    public String productPage() {
        return "products";
    }

    @GetMapping("/gallery")
    public String galleryPage(){
        return"gallery";
    }
    @GetMapping("/contact")
    public String contactPage(){
        return"contact";
    }
}
