package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.dtos.ClienteDireccionDto;
import no_country_grill_house.models.dtos.ClienteDto;

public interface ClienteService {

    ClienteDto create(ClienteDireccionDto clienteDireccionDto);

    Cliente findById(Long id);

    List<ClienteDto> findAll();

    void deleteById(Long id);

    ClienteDto update(Long id, ClienteDto clienteDto);

}
