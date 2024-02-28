package no_country_grill_house.services;

import no_country_grill_house.models.Reserva;
import no_country_grill_house.models.dtos.ReservaDto;

import java.util.List;

public interface ReservaService {
    ReservaDto create(ReservaDto reservaDto);

    Reserva findById(long id);

    List<ReservaDto> findAll();

    ReservaDto updateStatus(Long id, ReservaDto reservaDto);

}
