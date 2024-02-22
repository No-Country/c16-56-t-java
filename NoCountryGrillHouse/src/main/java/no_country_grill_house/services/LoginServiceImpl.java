package no_country_grill_house.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import no_country_grill_house.config.JwtService;
import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.LoginRequest;
import no_country_grill_house.repositories.ClienteRepository;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        var cliente = clienteRepository.findClienteByEmail(loginRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(cliente);
        return AuthResponse.builder().token(jwtToken).build();
    }

}
