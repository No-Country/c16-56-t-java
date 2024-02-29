package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.dtos.ReservaDto;

public interface ReservaService {
    ReservaDto create(ReservaDto reservaDto);

    ReservaDto findById(long id);

    List<ReservaDto> findAll();

    ReservaDto updateStatus(ReservaDto reservaDto);

    ReservaDto update(Long id, ReservaDto reservaDto);

    void softDeleteById(Long id);

    void deleteById(Long id);

}
