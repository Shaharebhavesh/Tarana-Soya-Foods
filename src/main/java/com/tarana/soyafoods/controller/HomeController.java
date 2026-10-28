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
    @GetMapping("/recipes")
    public String contactPage(){
        return"recipes";
    }
    @GetMapping("/quality")
    public String qualityPage(){
        return"quality";
    }

    @GetMapping("/why-soya")
    public String whysoyaPage(){
        return"why-soya";
    }

    @GetMapping("/get-in-touch")
    public String getintouchPage(){
        return"get-in-touch";
    }

}
