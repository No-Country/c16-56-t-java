package no_country_grill_house.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no_country_grill_house.services.UsuarioServiceimpl;

@RestController
@RequestMapping("/api")

public class UsuarioController {

    @Autowired
    private UsuarioServiceimpl usuarioServiceimpl;

    @GetMapping("/usuarios")
    public ResponseEntity<?> getUsers() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuarioServiceimpl.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
