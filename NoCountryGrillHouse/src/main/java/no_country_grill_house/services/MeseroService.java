package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.dtos.MeseroDto;

public interface MeseroService {

    AuthResponse create(MeseroDto meseroDto);

    MeseroDto findById(Long id);

    List<MeseroDto> findAll();

    void deleteById(Long id);

    void softDeleteById(Long id);

    void alta(Long id);

    MeseroDto update(Long id, MeseroDto meseroDto);

    MeseroDto findByEmail(String email);

}
