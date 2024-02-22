package no_country_grill_house.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import no_country_grill_house.models.dtos.ClienteDto;
import no_country_grill_house.services.ClienteServiceImpl;

@Controller
@RequestMapping("/clientes")

public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

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

    // @PostMapping("/registrar")
    // public String save(@ModelAttribute ClienteDireccionDto clienteDireccionDto,
    // ModelMap model) {

    // try {
    // clienteServiceImpl.create(clienteDireccionDto);
    // model.put("clienteDireccionDto", clienteDireccionDto);
    // model.put("exito", "Cliente registrado correctamente!");
    // } catch (GrillHouseException ex) {
    // model.put("clienteDireccionDto", clienteDireccionDto);
    // model.put("error", ex.getMessage());
    // return "thymeleaf.html";
    // }
    // return "thymeleaf.html";
    // }

}
