package no_country_grill_house.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import no_country_grill_house.models.dtos.AdminDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.services.AdminServiceImpl;

@Controller
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @GetMapping({ "", "/" })
    public String inicioAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Rol rol = (Rol) session.getAttribute("rol");
            if (rol != null && rol.equals(Rol.ADMIN)) {
                return "Views/admin.html";
            } else {
                return "Acceso denegado";
            }
        } else {
            return "Acceso denegado";
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
    public ResponseEntity<?> update(@Valid @RequestBody AdminDto adminDto) {
        try {
            AdminDto updatedAdmin = adminServiceImpl.update(adminDto.getId(), adminDto);
            return ResponseEntity.ok(updatedAdmin);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}
