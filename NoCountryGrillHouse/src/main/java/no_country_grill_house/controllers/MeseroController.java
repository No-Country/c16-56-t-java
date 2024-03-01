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
import no_country_grill_house.models.dtos.MeseroDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.services.MeseroServiceImpl;

@Controller
@RequestMapping("/mesero")

public class MeseroController {

    @Autowired
    private MeseroServiceImpl meseroServiceImpl;

    @GetMapping({ "", "/" })
    public String inicioMesero(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Rol rol = (Rol) session.getAttribute("rol");
            if (rol != null && rol.equals(Rol.MESERO)) {
                return "Views/mesero.html";
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
                    .body(meseroServiceImpl.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> save(@Valid @RequestBody MeseroDto meseroDto) {
        try {
            return ResponseEntity.ok(meseroServiceImpl.create(meseroDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/borrar")
    public ResponseEntity<?> delete(@RequestBody Long id) {
        try {
            meseroServiceImpl.softDeleteById(id);
            return ResponseEntity.ok("Mesero eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<?> update(@Valid @RequestBody MeseroDto meseroDto) {
        try {
            MeseroDto updatedMesero = meseroServiceImpl.update(meseroDto.getId(), meseroDto);
            return ResponseEntity.ok(updatedMesero);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}
