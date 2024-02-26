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
import no_country_grill_house.models.dtos.JefeCocinaDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.services.JefeCocinaServiceImpl;

@Controller
@RequestMapping("/jefe_cocina")

public class JefeCocinaController {

    @Autowired
    private JefeCocinaServiceImpl jefeCocinaServiceImpl;

    @GetMapping({ "", "/" })
    public String inicioJefeCocina(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Rol rol = (Rol) session.getAttribute("rol");
            if (rol != null && rol.equals(Rol.JEFE_COCINA)) {
                return "Views/cocina.html";
            } else {
                return "Acceso denegado";
            }
        } else {
            return "Acceso denegado";
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> get() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jefeCocinaServiceImpl.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> save(@Valid @RequestBody JefeCocinaDto jefeCocinaDto) {
        try {
            return ResponseEntity.ok(jefeCocinaServiceImpl.create(jefeCocinaDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/borrar")
    public ResponseEntity<?> delete(@RequestBody Long id) {
        try {
            jefeCocinaServiceImpl.deleteById(id);
            return ResponseEntity.ok("Jefe de Cocina eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<?> update(@Valid @RequestBody JefeCocinaDto jefeCocinaDto) {
        try {
            JefeCocinaDto updatedJefeCocina = jefeCocinaServiceImpl.update(jefeCocinaDto.getId(), jefeCocinaDto);
            return ResponseEntity.ok(updatedJefeCocina);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
