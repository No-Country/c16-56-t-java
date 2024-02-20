package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.FotoPlatillo;
import no_country_grill_house.models.dtos.FotoPlatilloDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface FotoPlatilloMapper {

    FotoPlatillo toFotoPlatillo(FotoPlatilloDto fotoPlatilloDto);

    FotoPlatilloDto toFotoPlatilloDto(FotoPlatillo fotoPlatillo);

}
