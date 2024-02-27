package no_country_grill_house.controllers;

import java.util.List;

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
import no_country_grill_house.models.dtos.PlatilloDto;
import no_country_grill_house.services.PlatilloServiceImpl;

@RestController
@RequestMapping("/platillo")
public class PlatilloController {

    private final PlatilloServiceImpl platilloServiceImpl;

    public PlatilloController(PlatilloServiceImpl platilloServiceImpl) {
        this.platilloServiceImpl = platilloServiceImpl;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PlatilloDto>> getAllCategorias() {
        List<PlatilloDto> platillos = platilloServiceImpl.findAll();
        return ResponseEntity.ok(platillos);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> save(@RequestBody @Valid PlatilloDto platilloDto) {
        try {
            return ResponseEntity
                    .ok()
                    .body(platilloServiceImpl.create(platilloDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> getPlatilloById(@RequestBody Long id) {
        PlatilloDto platillo = platilloServiceImpl.findById(id);
        return ResponseEntity.ok(platillo);
    }

    @DeleteMapping("/borrar")
    public ResponseEntity<String> deletePlatilloById(@RequestBody Long id) {
        platilloServiceImpl.softDeleteById(id);
        return ResponseEntity.ok("Platillo eliminado correctamente");
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> updatePlatillo(@RequestBody PlatilloDto platilloDto) {
        PlatilloDto updatedPlatillo = platilloServiceImpl.update(platilloDto.getId(), platilloDto);
        return ResponseEntity.ok(updatedPlatillo);
    }

}