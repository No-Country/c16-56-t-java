package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.Orden;
import no_country_grill_house.models.dtos.OrdenDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface OrdenMapper {
    Orden toOrden(OrdenDto ordenDto);

    OrdenDto tOrdenDto(Orden orden);
}
