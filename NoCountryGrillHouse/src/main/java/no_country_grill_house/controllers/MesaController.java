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
import no_country_grill_house.models.dtos.MesaDto;
import no_country_grill_house.services.MesaServiceImpl;

@RestController
@RequestMapping("/mesa")
public class MesaController {

    private final MesaServiceImpl mesaServiceImpl;

    public MesaController(MesaServiceImpl mesaServiceImpl) {
        this.mesaServiceImpl = mesaServiceImpl;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MesaDto>> getAllCategorias() {
        List<MesaDto> mesas = mesaServiceImpl.findAll();
        return ResponseEntity.ok(mesas);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> save(@RequestBody @Valid MesaDto mesaDto) {
        try {
            return ResponseEntity
                    .ok()
                    .body(mesaServiceImpl.create(mesaDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> getCategoriaById(@RequestBody Long id) {
        MesaDto mesa = mesaServiceImpl.findById(id);
        return ResponseEntity.ok(mesa);
    }

    @DeleteMapping("/borrar")
    public ResponseEntity<String> deleteCategoriaById(@RequestBody Long id) {
        mesaServiceImpl.softDeleteById(id);
        return ResponseEntity.ok("Mesa eliminada correctamente");
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> updateCategoria(@RequestBody MesaDto mesaDto) {
        MesaDto updatedMesa = mesaServiceImpl.update(mesaDto.getId(), mesaDto);
        return ResponseEntity.ok(updatedMesa);
    }

}
