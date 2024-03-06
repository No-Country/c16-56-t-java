package no_country_grill_house.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import no_country_grill_house.models.dtos.DireccionDto;
import no_country_grill_house.services.DireccionServiceImpl;

@RestController
@RequestMapping("/direccion")

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
    public ResponseEntity<?> save(@RequestBody @Valid DireccionDto direccionDto) {
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

    @GetMapping("/buscar")
    public ResponseEntity<?> getDireccionById(@RequestBody Long id) {
        DireccionDto direccionDto = direccionServiceImpl.findById(id);
        return ResponseEntity.ok(direccionDto);
    }

    @DeleteMapping("/borrar")
    public ResponseEntity<String> deleteDireccionById(@RequestBody Long id) {
        direccionServiceImpl.softDeleteById(id);
        return ResponseEntity.ok("Categoria eliminada correctamente");
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> updateDireccion(@RequestBody DireccionDto direccionDto) {
        DireccionDto updatedDireccion = direccionServiceImpl.update(direccionDto.getId(), direccionDto);
        return ResponseEntity.ok(updatedDireccion);
    }
}
