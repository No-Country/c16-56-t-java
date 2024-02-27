package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.DetalleOrden;
import no_country_grill_house.models.dtos.DetalleOrdenDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface DetalleOrdenMapper {
    DetalleOrden tDetalleOrden(DetalleOrdenDto detalleOrdenDto);

    DetalleOrdenDto tDetalleOrdenDto(DetalleOrden detalleOrden);
}
