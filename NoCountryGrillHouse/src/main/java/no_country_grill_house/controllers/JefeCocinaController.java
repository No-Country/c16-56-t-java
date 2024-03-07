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
import no_country_grill_house.models.dtos.JefeCocinaDto;
import no_country_grill_house.models.dtos.PasswordDto;
import no_country_grill_house.models.dtos.UpdateRequestDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.services.JefeCocinaServiceImpl;

@Controller
@RequestMapping("/jefe_cocina")

public class JefeCocinaController {

    @Autowired
    private JefeCocinaServiceImpl jefeCocinaServiceImpl;

    @GetMapping({ "", "/" })
    public String inicioAdmin(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Rol rol = (Rol) session.getAttribute("rol");
            if (rol != null && rol.equals(Rol.JEFE_COCINA)) {
                String email = (String) session.getAttribute("username");
                JefeCocinaDto jefeCocinaDto = jefeCocinaServiceImpl.findByEmail(email);
                model.addAttribute("nombre", jefeCocinaDto.getNombre());
                model.addAttribute("foto", jefeCocinaDto.getFoto());
                model.addAttribute("email", jefeCocinaDto.getEmail());
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
    public ResponseEntity<?> update(@RequestBody UpdateRequestDto updateRequestDto) {
        try {
            String email = updateRequestDto.getEmail();
            String nombre = updateRequestDto.getNombre();
            String telefono = updateRequestDto.getTelefono();
            String calle = updateRequestDto.getCalle();
            String numero = updateRequestDto.getNumero();
            String ciudad = updateRequestDto.getCiudad();
            Long id = updateRequestDto.getId();
            JefeCocinaDto jefeCocinaDto = new JefeCocinaDto();
            jefeCocinaDto.setNombre(nombre);
            jefeCocinaDto.setTelefono(telefono);
            jefeCocinaDto.setEmail(email);
            jefeCocinaDto.setId(id);
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
            jefeCocinaDto.setDireccion(direccion);
            JefeCocinaDto updateJefeCocina = jefeCocinaServiceImpl.update(jefeCocinaDto.getId(), jefeCocinaDto);
            return ResponseEntity.ok(updateJefeCocina);
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
            jefeCocinaServiceImpl.modificarPassword(passwordDto);
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
