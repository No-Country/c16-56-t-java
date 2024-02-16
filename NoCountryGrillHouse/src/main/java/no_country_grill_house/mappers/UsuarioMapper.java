package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.Usuario;
import no_country_grill_house.models.dtos.UsuarioDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface UsuarioMapper {
    Usuario toUsuario(UsuarioDto usuarioDto);

    UsuarioDto toUsuarioDto(Usuario usuario);
}
