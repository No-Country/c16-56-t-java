package no_country_grill_house.services;

import no_country_grill_house.models.FotoPlatillo;
import no_country_grill_house.models.dtos.FotoPlatilloDto;

public interface FotoPlatilloService {

    FotoPlatilloDto create(FotoPlatilloDto fotoPlatilloDto);

    FotoPlatillo findById(Long id);

    void deleteById(Long id);

    FotoPlatilloDto update(Long id, FotoPlatilloDto fotoPlatilloDto);

}
