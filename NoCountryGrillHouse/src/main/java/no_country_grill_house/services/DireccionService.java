package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.Direccion;
import no_country_grill_house.models.dtos.DireccionDto;

public interface DireccionService {

    DireccionDto create(DireccionDto direccion);

    Direccion findById(Long id);

    List<DireccionDto> findAll();

    void deleteById(Long id);

    DireccionDto update(Long id, DireccionDto direccionDto);

}
