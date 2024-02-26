package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.Platillo;
import no_country_grill_house.models.dtos.PlatilloDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface PlatilloMapper {

    Platillo toPlatillo(PlatilloDto platilloDto);

    PlatilloDto toPlatilloDto(Platillo platillo);

}
