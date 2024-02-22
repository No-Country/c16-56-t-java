package no_country_grill_house.services;


import no_country_grill_house.models.Orden;
import no_country_grill_house.models.dtos.OrdenDto;
import java.util.List;

public interface OrdenService {
    OrdenDto create(OrdenDto ordenDto);
    Orden findById(Long id);
    List<OrdenDto> findAll();   
}
