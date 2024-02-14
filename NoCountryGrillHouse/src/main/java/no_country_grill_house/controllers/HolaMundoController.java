/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no_country_grill_house.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author SantiagoPaguaga
 */
@Controller
@RequestMapping("/api")
public class HolaMundoController {
  
  @GetMapping("/saludo")
  public String index(){
    return "index.html";
  }
  
}
