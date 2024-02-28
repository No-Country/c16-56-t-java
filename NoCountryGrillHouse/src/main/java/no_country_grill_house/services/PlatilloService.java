package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.dtos.PlatilloDto;

public interface PlatilloService {

    PlatilloDto create(PlatilloDto platilloDto);

    PlatilloDto findById(Long id);

    List<PlatilloDto> findAll();

    void deleteById(Long id);

    void softDeleteById(Long id);

    void alta(Long id);

    PlatilloDto update(Long id, PlatilloDto platilloDto);

}
