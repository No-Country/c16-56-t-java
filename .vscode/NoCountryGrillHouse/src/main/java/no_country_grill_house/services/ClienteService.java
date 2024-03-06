package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.dtos.ClienteDto;
import no_country_grill_house.models.dtos.PasswordDto;

public interface ClienteService {

    AuthResponse create(ClienteDto clienteDto);

    ClienteDto findById(Long id);

    List<ClienteDto> findAll();

    void deleteById(Long id);

    void softDeleteById(Long id);

    void alta(Long id);

    ClienteDto update(Long id, ClienteDto clienteDto);

    ClienteDto findByEmail(String email);

    void modificarPassword(PasswordDto passwordDto);

}
