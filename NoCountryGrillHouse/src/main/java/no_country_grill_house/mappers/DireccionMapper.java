package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.Direccion;
import no_country_grill_house.models.dtos.DireccionDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface DireccionMapper {
    Direccion toDireccion(DireccionDto direccionDto);

    DireccionDto toDireccionDto(Direccion direccion);
}
