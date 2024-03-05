package no_country_grill_house.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import no_country_grill_house.models.Direccion;
import no_country_grill_house.models.dtos.AdminDto;
import no_country_grill_house.models.dtos.PasswordDto;
import no_country_grill_house.models.dtos.UpdateRequestDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.services.AdminServiceImpl;

@Controller
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @GetMapping({ "", "/" })
    public String inicioAdmin(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Rol rol = (Rol) session.getAttribute("rol");
            if (rol != null && rol.equals(Rol.ADMIN)) {
                String email = (String) session.getAttribute("username");
                AdminDto adminDto = adminServiceImpl.findByEmail(email);
                model.addAttribute("nombre", adminDto.getNombre());
                model.addAttribute("foto", adminDto.getFoto());
                model.addAttribute("email", adminDto.getEmail());
                return "Views/admin.html";
            } else {
                return "Acceso denegado";
            }
        } else {
            return "Acceso denegado";
        }
    }

    @PostMapping("/obtenerPorEmail")
    public ResponseEntity<?> getUserByEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        AdminDto adminDto = adminServiceImpl.findByEmail(email);
        if (adminDto != null) {
            return ResponseEntity.ok(adminDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> save(@Valid @RequestBody AdminDto adminDto) {
        try {
            return ResponseEntity.ok(adminServiceImpl.create(adminDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<?> update(@RequestBody UpdateRequestDto updateRequestDto) {
        try {
            String email = updateRequestDto.getEmail();
            String nombre = updateRequestDto.getNombre();
            String telefono = updateRequestDto.getTelefono();
            String calle = updateRequestDto.getCalle();
            String numero = updateRequestDto.getNumero();
            String ciudad = updateRequestDto.getCiudad();
            Long id = updateRequestDto.getId();
            AdminDto adminDto = new AdminDto();
            adminDto.setNombre(nombre);
            adminDto.setTelefono(telefono);
            adminDto.setEmail(email);
            adminDto.setId(id);
            Direccion direccion = new Direccion();
            if (calle != null) {
                direccion.setCalle(calle);
            }
            if (numero != null) {
                direccion.setNumero(numero);
            }
            if (ciudad != null) {
                direccion.setCiudad(ciudad);
            }
            adminDto.setDireccion(direccion);
            AdminDto updatedAdmin = adminServiceImpl.update(adminDto.getId(), adminDto);
            return ResponseEntity.ok(updatedAdmin);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", errorMessage);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }

    @PostMapping("/actualizar/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordDto passwordDto) {
        try {
            adminServiceImpl.modificarPassword(passwordDto);
            String exitoMessage = "El password se actualizó correctamente!";
            Map<String, Object> exitoResponse = new HashMap<>();
            exitoResponse.put("message", exitoMessage);
            return ResponseEntity.ok(exitoResponse);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", errorMessage);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }

}
