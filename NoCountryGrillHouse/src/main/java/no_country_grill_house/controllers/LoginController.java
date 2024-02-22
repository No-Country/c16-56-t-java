package no_country_grill_house.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.LoginRequest;
import no_country_grill_house.services.LoginServiceImpl;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginServiceImpl.login(request));
    }

}
