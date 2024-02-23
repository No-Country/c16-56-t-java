package no_country_grill_house.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import no_country_grill_house.config.JwtService;
import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.LoginRequest;
import no_country_grill_house.models.enums.Rol;
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
    public Object login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            Rol rol = Rol.MESERO;
            var jwtToken = "";

            var cliente = clienteRepository.findClienteByEmail(loginRequest.getEmail());
            if (cliente != null) {
                rol = cliente.get().getRol();

                jwtToken = jwtService.generateToken(cliente.get());
            }

            return AuthResponse.builder()
                    .token(jwtToken)
                    .rol(rol)
                    .build();
        } catch (AuthenticationException e) {
            String errorMessage = "Usuario o contraseña incorrectos";
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", errorMessage);
            return errorResponse;
        }
    }

}
