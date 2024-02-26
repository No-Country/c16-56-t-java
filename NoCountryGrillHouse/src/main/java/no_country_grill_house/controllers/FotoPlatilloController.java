package no_country_grill_house.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import no_country_grill_house.models.dtos.FotoPlatilloDto;
import no_country_grill_house.models.dtos.PlatilloDto;
import no_country_grill_house.services.CloudinaryService;
import no_country_grill_house.services.FotoPlatilloServiceImpl;
import no_country_grill_house.services.PlatilloServiceImpl;

@RequestMapping("/imagenes/platillos")
@CrossOrigin
@Controller
public class FotoPlatilloController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private FotoPlatilloServiceImpl fotoPlatilloServiceImpl;

    @Autowired
    private PlatilloServiceImpl platilloServiceImpl;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile, PlatilloDto platilloDto) {

        // BufferedImage entry = ImageIO.read(multipartFile.getInputStream());

        try {

            @SuppressWarnings("rawtypes")
            Map resultado = cloudinaryService.subirFoto(multipartFile);
            FotoPlatilloDto fotoPlatilloDto = new FotoPlatilloDto();
            fotoPlatilloDto.setNombre((String) resultado.get("original_filename"));
            fotoPlatilloDto.setUrl((String) resultado.get("url"));
            fotoPlatilloDto.setFotoId((String) resultado.get("public_id"));

            fotoPlatilloDto = fotoPlatilloServiceImpl.create(fotoPlatilloDto);

            if (platilloDto != null) {
                platilloServiceImpl.guardarFotoPerfil(platilloDto.getId(), fotoPlatilloDto);
            }

            return ResponseEntity.ok("Imagen cargada exitosamente!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cargar la imagen: " + e.getMessage());
        }
    }

    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<?> delete(@PathVariable("id") Long id) throws
    // IOException {
    // if(!imagenService.existePorId(id))
    // return ResponseEntity.notFound().build();
    // FotoUsuario foto = imagenService.encontrarImagen(id).get();
    // Map resultado = cloudinaryService.delete(imagen.getImagenId());
    // imagenService.borraraPorId(id);
    // return new ResponseEntity(new String ("imagen eliminada"),HttpStatus.OK);
    // }
}
