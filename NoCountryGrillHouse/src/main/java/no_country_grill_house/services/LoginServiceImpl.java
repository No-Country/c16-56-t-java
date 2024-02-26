package no_country_grill_house.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import no_country_grill_house.config.JwtService;
import no_country_grill_house.models.Admin;
import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.JefeCocina;
import no_country_grill_house.models.LoginRequest;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.repositories.AdminRepository;
import no_country_grill_house.repositories.ClienteRepository;
import no_country_grill_house.repositories.JefeCocinaRepository;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JefeCocinaRepository jefeCocinaRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Object login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            Rol rol = null;
            var jwtToken = "";

            Optional<Cliente> cliente = clienteRepository.findClienteByEmail(loginRequest.getEmail());
            Optional<Admin> admin = adminRepository.findAdminByEmail(loginRequest.getEmail());
            Optional<JefeCocina> jefeCocina = jefeCocinaRepository.findJefeCocinaByEmail(
                    loginRequest.getEmail());

            if (cliente.isPresent()) {
                rol = cliente.get().getRol();

                jwtToken = jwtService.generateToken(cliente.get());
            } else {
                if (admin.isPresent()) {
                    rol = admin.get().getRol();

                    jwtToken = jwtService.generateToken(admin.get());
                } else {
                    if (jefeCocina.isPresent()) {
                        rol = jefeCocina.get().getRol();

                        jwtToken = jwtService.generateToken(jefeCocina.get());
                    }
                }
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
