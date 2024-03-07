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
import no_country_grill_house.models.dtos.MeseroDto;
import no_country_grill_house.models.dtos.PasswordDto;
import no_country_grill_house.models.dtos.UpdateRequestDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.services.MeseroServiceImpl;

@Controller
@RequestMapping("/mesero")

public class MeseroController {

    @Autowired
    private MeseroServiceImpl meseroServiceImpl;

    @GetMapping({ "", "/" })
    public String inicioAdmin(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Rol rol = (Rol) session.getAttribute("rol");
            if (rol != null && rol.equals(Rol.MESERO)) {
                String email = (String) session.getAttribute("username");
                MeseroDto meseroDto = meseroServiceImpl.findByEmail(email);
                model.addAttribute("nombre", meseroDto.getNombre());
                model.addAttribute("foto", meseroDto.getFoto());
                model.addAttribute("email", meseroDto.getEmail());
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
            meseroServiceImpl.deleteById(id);
            return ResponseEntity.ok("Mesero eliminado correctamente");
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
            MeseroDto meseroDto = new MeseroDto();
            meseroDto.setNombre(nombre);
            meseroDto.setTelefono(telefono);
            meseroDto.setEmail(email);
            meseroDto.setId(id);
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
            meseroDto.setDireccion(direccion);
            MeseroDto updateMesero = meseroServiceImpl.update(meseroDto.getId(), meseroDto);
            return ResponseEntity.ok(updateMesero);
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
            meseroServiceImpl.modificarPassword(passwordDto);
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
