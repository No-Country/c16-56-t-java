package no_country_grill_house.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no_country_grill_house.models.dtos.ReservaDto;
import no_country_grill_house.services.ReservaServiceImpl;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private ReservaServiceImpl reservaServiceIml;

    @GetMapping("/listar")
    public ResponseEntity<?> get() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(reservaServiceIml.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
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
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        }
    }
    /*
     * @PostMapping("/updateStatus")
     * public ResponseEntity<?> update(@RequestBody Long id, String status){
     * 
     * }
     */
}
