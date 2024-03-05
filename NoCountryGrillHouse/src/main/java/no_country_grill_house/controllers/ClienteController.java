package no_country_grill_house.controllers;

import java.util.HashMap;
import java.util.Map;

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
import no_country_grill_house.models.Direccion;
import no_country_grill_house.models.dtos.ClienteDto;
import no_country_grill_house.models.dtos.PasswordDto;
import no_country_grill_house.models.dtos.UpdateRequestDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.services.ClienteServiceImpl;

@Controller
@RequestMapping("/cliente")

public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    @GetMapping({ "", "/" })
    public String inicioCliente(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Rol rol = (Rol) session.getAttribute("rol");
            if (rol != null && rol.equals(Rol.CLIENTE)) {
                return "Views/cliente.html";
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
                    .body(clienteServiceImpl.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> save(@Valid @RequestBody ClienteDto clienteDto) {
        try {
            return ResponseEntity.ok(clienteServiceImpl.create(clienteDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/borrar")
    public ResponseEntity<?> delete(@RequestBody Long id) {
        try {
            clienteServiceImpl.deleteById(id);
            return ResponseEntity.ok("Cliente eliminado correctamente");
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
            ClienteDto clienteDto = new ClienteDto();
            clienteDto.setNombre(nombre);
            clienteDto.setTelefono(telefono);
            clienteDto.setEmail(email);
            clienteDto.setId(id);
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
            clienteDto.setDireccion(direccion);
            ClienteDto updateCliente = clienteServiceImpl.update(clienteDto.getId(), clienteDto);
            return ResponseEntity.ok(updateCliente);
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
            clienteServiceImpl.modificarPassword(passwordDto);
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

    // @PostMapping("/registrar")
    // public ResponseEntity<?> save(@RequestBody ClienteDireccionDto
    // clienteDireccionDto) {
    // try {
    // return ResponseEntity
    // .ok()
    // .body(clienteServiceImpl.create(clienteDireccionDto));
    // } catch (Exception e) {
    // return ResponseEntity
    // .status(HttpStatus.NOT_FOUND)
    // .body(e.getMessage());
    // }
    // }

    // @PreAuthorize("hasAuthority('CLIENTE')")
    // @GetMapping({ "", "/" })
    // public String inicioCliente(HttpServletRequest request) {
    // String token = request.getHeader("Authorization").substring(7);
    // List<String> roles = jwtService.getRolesFromToken(token);

    // if (roles.contains("CLIENTE")) {
    // return "Views/cliente.html";
    // } else {
    // return "Acceso denegado";
    // }
    // }
}
