package no_country_grill_house.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/")
public class homeController {
    
    @GetMapping("")
    public String home() {
        return "Views/home";
    }
    
}
