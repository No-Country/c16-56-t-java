package no_country_grill_house.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import no_country_grill_house.models.Usuario;
import no_country_grill_house.repositories.AdminRepository;
import no_country_grill_house.repositories.ClienteRepository;
import no_country_grill_house.repositories.JefeCocinaRepository;
import no_country_grill_house.repositories.MeseroRepository;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final AdminRepository adminRepository;

    @Autowired
    private final JefeCocinaRepository jefeCocinaRepository;

    @Autowired
    private final MeseroRepository meseroRepository;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return email -> {
            Optional<? extends Usuario> cliente = clienteRepository.findClienteByEmail(email);
            if (cliente.isPresent()) {
                return cliente.get();
            }

            Optional<? extends Usuario> admin = adminRepository.findAdminByEmail(email);
            if (admin.isPresent()) {
                return admin.get();
            }

            Optional<? extends Usuario> jefeCocina = jefeCocinaRepository.findJefeCocinaByEmail(email);
            if (jefeCocina.isPresent()) {
                return jefeCocina.get();
            }

            Optional<? extends Usuario> mesero = meseroRepository.findMeseroByEmail(email);
            if (mesero.isPresent()) {
                return mesero.get();
            }

            throw new UsernameNotFoundException("Usuario no encontrado");
        };
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
