package no_country_grill_house.controllers;

import no_country_grill_house.models.dtos.OrdenDto;
import no_country_grill_house.services.OrdenServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orden")
public class OrdenController {
    @Autowired
    private OrdenServiceImpl ordenServiceImpl;

    @GetMapping("/listar")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ordenServiceImpl.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        }
    }

   /*  @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>("GetOne Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PostMapping("/registrar")
    public ResponseEntity<?> create(@RequestBody OrdenDto ordenDto) {
        try {
            return ResponseEntity
                    .ok()
                    .body(ordenServiceImpl.create(ordenDto));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        }
    }

    /*
     * @PutMapping()
     * public ResponseEntity<?> update(@RequestBody Dto dto) {
     * try {
     * //TODO Implement Your Logic To Update Data And Return Result Through
     * ResponseEntity
     * return new ResponseEntity<>("Update Result", HttpStatus.OK);
     * } catch (Exception e) {
     * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * }
     * 
     * @DeleteMapping("/{id}")
     * public ResponseEntity<?> delete(@PathVariable Integer id) {
     * try {
     * //TODO Implement Your Logic To Destroy Data And Return Result Through
     * ResponseEntity
     * return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
     * } catch (Exception e) {
     * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * }
     */
}
