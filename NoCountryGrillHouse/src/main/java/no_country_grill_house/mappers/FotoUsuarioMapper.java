package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.FotoUsuario;
import no_country_grill_house.models.dtos.FotoUsuarioDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface FotoUsuarioMapper {

    FotoUsuario toFotoUsuario(FotoUsuarioDto fotoUsuarioDto);

    FotoUsuarioDto toFotoUsuarioDto(FotoUsuario fotoUsuario);

}
