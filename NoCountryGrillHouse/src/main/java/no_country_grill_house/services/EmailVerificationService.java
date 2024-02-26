package no_country_grill_house.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no_country_grill_house.models.Admin;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.JefeCocina;
import no_country_grill_house.repositories.AdminRepository;
import no_country_grill_house.repositories.ClienteRepository;
import no_country_grill_house.repositories.JefeCocinaRepository;

@Service
public class EmailVerificationService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JefeCocinaRepository jefeCocinaRepository;

    // Recordar agregar los demás repositorios para verificar que no exista en
    // ninguna de las tablas

    public boolean isEmailUnique(String email) {
        boolean esUnico = true;
        Optional<Cliente> clienteOptional = clienteRepository.findClienteByEmail(email);
        Optional<Admin> adminOptional = adminRepository.findAdminByEmail(email);
        Optional<JefeCocina> jefeCocinaOptional = jefeCocinaRepository.findJefeCocinaByEmail(email);

        if (!clienteOptional.isEmpty()) {
            esUnico = false;
        } else if (!adminOptional.isEmpty()) {
            esUnico = false;
        } else if (!jefeCocinaOptional.isEmpty()) {
            esUnico = false;
        }
        return esUnico;
    }
}
