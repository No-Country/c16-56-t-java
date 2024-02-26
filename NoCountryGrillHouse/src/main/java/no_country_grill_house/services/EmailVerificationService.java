package no_country_grill_house.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no_country_grill_house.models.Cliente;
import no_country_grill_house.repositories.ClienteRepository;

@Service
public class EmailVerificationService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Recordar agregar los demás repositorios para verificar que no exista en
    // ninguna de las tablas

    public boolean isEmailUnique(String email) {
        Optional<Cliente> clienteOptional = clienteRepository.findClienteByEmail(email);
        return clienteOptional.isEmpty();
    }
}
