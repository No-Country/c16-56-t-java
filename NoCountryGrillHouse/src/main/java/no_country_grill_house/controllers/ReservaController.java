package no_country_grill_house.controllers;

import no_country_grill_house.models.dtos.ReservaDto;
import no_country_grill_house.services.ReservaServiceImpl;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private ReservaServiceImpl reservaServiceIml;

    @GetMapping("/listar")
    public ResponseEntity<?> get() {
        try {
            return ResponseEntity
                    .status(HttpStatus.SC_OK)
                    .body(reservaServiceIml.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.SC_BAD_REQUEST)
                    .body(e.getMessage());

        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> save(@RequestBody ReservaDto reservaDto) {
        try {
            return ResponseEntity
                    .ok()
                    .body(reservaServiceIml.create(reservaDto));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.SC_NOT_FOUND)
                    .body(e.getMessage());

        }
    }
    /*@PostMapping("/updateStatus")
    public ResponseEntity<?> update(@RequestBody Long id, String status){

    }*/
}
