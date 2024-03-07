package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.Mesero;
import no_country_grill_house.models.dtos.OrdenConDetalleDto;
import no_country_grill_house.models.dtos.OrdenDto;

public interface OrdenService {
    OrdenDto create(OrdenDto ordenDto);

    OrdenDto findById(Long id);

    List<OrdenDto> findAll();

    List<OrdenConDetalleDto> obtenerOrdenesConDetallesPorCliente(Cliente cliente);

    List<OrdenConDetalleDto> obtenerOrdenesConDetallesPorMesero(Mesero mesero);

    List<OrdenConDetalleDto> obtenerOrdenesConDetalles();
}
