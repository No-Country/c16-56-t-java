package no_country_grill_house.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import no_country_grill_house.models.dtos.ClienteDto;
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
    public ResponseEntity<?> update(@Valid @RequestBody ClienteDto clienteDto) {
        try {
            ClienteDto updatedCliente = clienteServiceImpl.update(clienteDto.getId(), clienteDto);
            return ResponseEntity.ok(updatedCliente);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
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
