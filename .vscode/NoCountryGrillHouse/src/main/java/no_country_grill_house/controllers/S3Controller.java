package no_country_grill_house.controllers;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.services.S3ServiceImpl;

@RestController
@RequestMapping("/foto")
public class S3Controller {

    @Autowired
    private S3ServiceImpl s3ServiceImpl;

    @GetMapping("/download/{fileName}")
    public String downloadFile(@PathVariable("fileName") String fileName) throws IOException {
        return s3ServiceImpl.downloadFile(fileName);
    }

    @PostMapping("/upload/usuario")
    public ResponseEntity<?> uploadFileUsuario(@RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            Rol rol = (Rol) session.getAttribute("rol");
            String email = (String) session.getAttribute("username");
            System.out.println(rol);
            if (rol != null && email != null) {
                try {
                    String imageUrl = s3ServiceImpl.uploadFileUsuario(file, email, rol);
                    JSONObject response = new JSONObject();
                    response.put("imageUrl", imageUrl);
                    return ResponseEntity.ok(response.toString());
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error al cargar la imagen: " + e.getMessage());
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
        }
    }

    @PostMapping("/update/usuario")
    public ResponseEntity<String> updateFileUsuario(@RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            Rol rol = (Rol) session.getAttribute("rol");
            String email = (String) session.getAttribute("username");
            if (rol != null && email != null) {
                try {
                    String imageUrl = s3ServiceImpl.uploadFileUsuario(file, email, rol);
                    JSONObject response = new JSONObject();
                    response.put("imageUrl", imageUrl);
                    return ResponseEntity.ok(response.toString());
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error al actualizar la imagen: " + e.getMessage());
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public String deleteFile(@PathVariable("fileName") String fileName) throws IOException {
        return s3ServiceImpl.deleteFile(fileName);
    }

}
