package no_country_grill_house.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class PortalController {

    // @GetMapping("/")
    // public String home(ModelMap model) {
    // ClienteDireccionDto clienteDireccionDto = new ClienteDireccionDto();
    // model.addAttribute("clienteDireccionDto", clienteDireccionDto);
    // return "thymeleaf.html";
    // }

    @GetMapping("/")
    public String home() {
        return "fetch.html";
    }

    @GetMapping("/saludo")
    public String saludo() {
        return "thymeleaf.html";
    }
}
