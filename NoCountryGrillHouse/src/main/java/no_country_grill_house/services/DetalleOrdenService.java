package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.DetalleOrden;
import no_country_grill_house.models.dtos.DetalleOrdenDto;

public interface DetalleOrdenService {
    DetalleOrdenDto create(DetalleOrdenDto detalleOrdenDto);
    DetalleOrden findById(Long id);
    List<DetalleOrdenDto> findAll();
    void updateStatus(DetalleOrdenDto detalleOrden, Long id);
}
