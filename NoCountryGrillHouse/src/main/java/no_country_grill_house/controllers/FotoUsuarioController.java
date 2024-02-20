package no_country_grill_house.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import no_country_grill_house.models.Usuario;
import no_country_grill_house.models.dtos.FotoUsuarioDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.services.ClienteServiceImpl;
import no_country_grill_house.services.CloudinaryService;
import no_country_grill_house.services.FotoUsuarioServiceImpl;

@RequestMapping("/imagenes/usuarios")
@CrossOrigin
@Controller
public class FotoUsuarioController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private FotoUsuarioServiceImpl fotoUsuarioServiceImpl;

    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile, HttpSession session) {

        // BufferedImage entry = ImageIO.read(multipartFile.getInputStream());

        try {

            @SuppressWarnings("rawtypes")
            Map resultado = cloudinaryService.subirFoto(multipartFile);
            FotoUsuarioDto fotoUsuarioDto = new FotoUsuarioDto();
            fotoUsuarioDto.setNombre((String) resultado.get("original_filename"));
            fotoUsuarioDto.setUrl((String) resultado.get("url"));
            fotoUsuarioDto.setFotoId((String) resultado.get("public_id"));

            fotoUsuarioDto = fotoUsuarioServiceImpl.create(fotoUsuarioDto);

            Usuario usuario = (Usuario) session.getAttribute("usuariosession");

            if (usuario != null) {
                if (usuario.getRol() == Rol.CLIENTE) {
                    clienteServiceImpl.guardarFotoPerfil(usuario.getId(), fotoUsuarioDto);
                } else if (usuario.getRol() == Rol.MESERO) {

                } else if (usuario.getRol() == Rol.JEFE_COCINA) {

                } else if (usuario.getRol() == Rol.ADMIN) {

                }
            }

            return ResponseEntity.ok("Imagen cargada exitosamente!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
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
