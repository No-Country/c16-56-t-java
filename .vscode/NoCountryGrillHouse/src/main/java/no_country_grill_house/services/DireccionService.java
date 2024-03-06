package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.dtos.DireccionDto;

public interface DireccionService {

    DireccionDto create(DireccionDto direccion);

    DireccionDto findById(Long id);

    List<DireccionDto> findAll();

    void deleteById(Long id);

    void softDeleteById(Long id);

    void alta(Long id);

    DireccionDto update(Long id, DireccionDto direccionDto);

}
