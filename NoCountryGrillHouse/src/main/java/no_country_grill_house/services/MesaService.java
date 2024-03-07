package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.dtos.MesaDto;

public interface MesaService {

    MesaDto create(MesaDto mesaDto);

    MesaDto findById(Long id);

    List<MesaDto> findAll();

    void deleteById(Long id);

    void softDeleteById(Long id);

    void alta(Long id);

    MesaDto update(Long id, MesaDto mesaDto);

    MesaDto findByCantidad(Integer cantidadPersonas);

}
