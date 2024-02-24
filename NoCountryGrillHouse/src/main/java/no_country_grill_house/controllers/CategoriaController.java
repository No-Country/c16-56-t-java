package no_country_grill_house.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no_country_grill_house.models.dtos.CategoriaDto;
import no_country_grill_house.services.CategoriaServiceImpl;

@RestController
@RequestMapping("/categorias")
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

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> getCategoriaById(@PathVariable Long id) {
        CategoriaDto categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoriaById(@PathVariable Long id) {
        categoriaService.softDeleteById(id);
        return ResponseEntity.ok("Categoria eliminada correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> updateCategoria(@PathVariable Long id, @RequestBody CategoriaDto categoriaDto) {
        CategoriaDto updatedCategoria = categoriaService.update(id, categoriaDto);
        return ResponseEntity.ok(updatedCategoria);
    }

}
