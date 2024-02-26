package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.JefeCocina;
import no_country_grill_house.models.dtos.JefeCocinaDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface JefeCocinaMapper {

    @Mapping(target = "authorities", ignore = true)
    JefeCocina toJefeCocina(JefeCocinaDto jefeCocinaDto);

    @Mapping(target = "password2", ignore = true)
    JefeCocinaDto toJefeCocinaDto(JefeCocina jefeCocina);

}
