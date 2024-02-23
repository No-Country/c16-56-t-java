package no_country_grill_house.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import no_country_grill_house.config.JwtService;
import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.LoginRequest;
import no_country_grill_house.services.LoginServiceImpl;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        Object response = loginServiceImpl.login(request);

        if (response instanceof AuthResponse) {
            AuthResponse authResponse = (AuthResponse) response;
            if (authResponse.getToken() != null) {
                String username = jwtService.getUserName(authResponse.getToken());

                HttpSession session = httpRequest.getSession(true);

                // Almacenas los datos relevantes en la sesión
                session.setAttribute("username", username);
                session.setAttribute("authenticated", true);
                session.setAttribute("rol", authResponse.getRol());
                return ResponseEntity.ok(authResponse);
            }

        } else if (response instanceof Map<?, ?>) {
            Map<?, ?> errorResponse = (Map<?, ?>) response;
            // Aquí puedes manejar el mapa de error como desees
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
