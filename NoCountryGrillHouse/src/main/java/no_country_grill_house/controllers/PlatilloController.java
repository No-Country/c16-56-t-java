package no_country_grill_house.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import no_country_grill_house.mappers.CategoriaMapper;
import no_country_grill_house.mappers.FotoPlatilloMapper;
import no_country_grill_house.models.Categoria;
import no_country_grill_house.models.dtos.CategoriaDto;
import no_country_grill_house.models.dtos.FotoPlatilloDto;
import no_country_grill_house.models.dtos.PlatilloClienteRequestDto;
import no_country_grill_house.models.dtos.PlatilloDto;
import no_country_grill_house.services.CategoriaServiceImpl;
import no_country_grill_house.services.PlatilloServiceImpl;
import no_country_grill_house.services.S3ServiceImpl;

@RestController
@RequestMapping("/platillo")
public class PlatilloController {

    @Autowired
    private S3ServiceImpl s3ServiceImpl;

    @Autowired
    private FotoPlatilloMapper fotoPlatilloMapper;

    @Autowired
    private CategoriaServiceImpl categoriaServiceImpl;

    @Autowired
    private CategoriaMapper categoriaMapper;

    private final PlatilloServiceImpl platilloServiceImpl;

    public PlatilloController(PlatilloServiceImpl platilloServiceImpl) {
        this.platilloServiceImpl = platilloServiceImpl;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PlatilloDto>> getAllCategorias() {
        List<PlatilloDto> platillos = platilloServiceImpl.findAll();
        return ResponseEntity.ok(platillos);
    }

    @GetMapping("/listar/categoria")
    public ResponseEntity<?> getByCategoria(@RequestParam String nombre) {
        try {
            CategoriaDto categoriaDto = categoriaServiceImpl.findByNombre(nombre);
            List<PlatilloDto> platillos = platilloServiceImpl
                    .findByCategoria(categoriaMapper.toCategoria(categoriaDto));
            return ResponseEntity.ok(platillos);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", errorMessage);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> save(@RequestParam("imagen") MultipartFile imagen, @RequestParam("nombre") String nombre,
            @RequestParam("categoria") String categoria, @RequestParam("precio") Double precio,
            @RequestParam("tiempo") String tiempo, @RequestParam("descripcion") String descripcion) {
        try {
            FotoPlatilloDto fotoPlatilloDto = s3ServiceImpl.uploadFilePlatillo(imagen);
            Categoria getCategoria = categoriaMapper.toCategoria(categoriaServiceImpl.findByNombre(categoria));

            PlatilloDto platilloDto = new PlatilloDto();
            platilloDto.setNombre(nombre);
            platilloDto.setDescripcion(descripcion);
            platilloDto.setTiempoEstimado(tiempo);
            platilloDto.setPrecio(precio);
            platilloDto.setFoto(fotoPlatilloMapper.toFotoPlatillo(fotoPlatilloDto));
            platilloDto.setCategoria(getCategoria);

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

    @PostMapping("/borrar")
    public ResponseEntity<?> delete(@RequestBody PlatilloClienteRequestDto requestDto) {
        try {
            Long id = requestDto.getId();
            platilloServiceImpl.deleteById(id);
            return ResponseEntity.ok("Platillo eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> updatePlatillo(@RequestBody PlatilloDto platilloDto) {
        PlatilloDto updatedPlatillo = platilloServiceImpl.update(platilloDto.getId(), platilloDto);
        return ResponseEntity.ok(updatedPlatillo);
    }

}