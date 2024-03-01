package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.Mesero;
import no_country_grill_house.models.dtos.MeseroDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface MeseroMapper {

    @Mapping(target = "authorities", ignore = true)
    Mesero toMesero(MeseroDto meseroDto);

    @Mapping(target = "password2", ignore = true)
    MeseroDto toMeseroDto(Mesero mesero);

}
