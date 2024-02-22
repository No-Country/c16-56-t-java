package no_country_grill_house.mappers;

import no_country_grill_house.models.Reserva;
import no_country_grill_house.models.dtos.ReservaDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface ReservaMapper {
    Reserva toReserva(ReservaDto reservaDto);
    ReservaDto toReservaDto(Reserva reserva);
}
