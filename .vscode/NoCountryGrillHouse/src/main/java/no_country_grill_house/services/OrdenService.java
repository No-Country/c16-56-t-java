package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.dtos.OrdenDto;

public interface OrdenService {
    OrdenDto create(OrdenDto ordenDto);

    OrdenDto findById(Long id);

    List<OrdenDto> findAll();
}
