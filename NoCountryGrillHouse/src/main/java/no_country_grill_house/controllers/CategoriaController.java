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
import no_country_grill_house.models.dtos.CategoriaDto;
import no_country_grill_house.services.CategoriaServiceImpl;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaServiceImpl categoriaService;

    public CategoriaController(CategoriaServiceImpl categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaDto>> getAllCategorias() {
        List<CategoriaDto> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> save(@RequestBody @Valid CategoriaDto categoriaDto) {
        try {
            return ResponseEntity
                    .ok()
                    .body(categoriaService.create(categoriaDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> getCategoriaById(@RequestBody Long id) {
        CategoriaDto categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/borrar")
    public ResponseEntity<String> deleteCategoriaById(@RequestBody Long id) {
        categoriaService.softDeleteById(id);
        return ResponseEntity.ok("Categoria eliminada correctamente");
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> updateCategoria(@RequestBody CategoriaDto categoriaDto) {
        CategoriaDto updatedCategoria = categoriaService.update(categoriaDto.getId(), categoriaDto);
        return ResponseEntity.ok(updatedCategoria);
    }

}
