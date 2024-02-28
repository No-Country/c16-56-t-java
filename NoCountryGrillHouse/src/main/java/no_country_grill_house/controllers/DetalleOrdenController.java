package no_country_grill_house.controllers;

import no_country_grill_house.models.dtos.DetalleOrdenDto;
import no_country_grill_house.services.DetalleOrdenServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/detalleorden")
public class DetalleOrdenController {
    @Autowired
    DetalleOrdenServiceImpl detalleOrdenServiceImpl;

    @GetMapping("/listar")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(detalleOrdenServiceImpl.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(detalleOrdenServiceImpl.findById(id));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DetalleOrdenDto detalleOrdenDto) {
        try {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(detalleOrdenServiceImpl.create(detalleOrdenDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{idDetalle}/update")
    public ResponseEntity<?> update(@RequestBody DetalleOrdenDto detalleOrdenDto, @PathVariable Long idDetalle) {
        try {
            detalleOrdenServiceImpl.updateStatus(detalleOrdenDto, idDetalle);
            return new ResponseEntity<>("Update Ok", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        }
    }
    /*
     * @DeleteMapping("/{id}")
     * public ResponseEntity<?> delete(@PathVariable Integer id) {
     * try {
     * // TODO Implement Your Logic To Destroy Data And Return Result Through
     * // ResponseEntity
     * return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
     * } catch (Exception e) {
     * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * }
     */
}
