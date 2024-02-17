package no_country_grill_house.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no_country_grill_house.models.dtos.DireccionDto;
import no_country_grill_house.services.DireccionServiceImpl;

@RestController
@RequestMapping("/direcciones")

public class DireccionController {

    @Autowired
    private DireccionServiceImpl direccionServiceImpl;

    @GetMapping("/listar")
    public ResponseEntity<?> get() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(direccionServiceImpl.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> save(@RequestBody DireccionDto direccionDto) {
        try {
            return ResponseEntity
                    .ok()
                    .body(direccionServiceImpl.create(direccionDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
