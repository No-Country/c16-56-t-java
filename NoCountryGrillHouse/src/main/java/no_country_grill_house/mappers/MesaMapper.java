package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.Mesa;
import no_country_grill_house.models.dtos.MesaDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface MesaMapper {

    Mesa toMesa(MesaDto mesaDto);

    MesaDto toMesaDto(Mesa mesa);

}
