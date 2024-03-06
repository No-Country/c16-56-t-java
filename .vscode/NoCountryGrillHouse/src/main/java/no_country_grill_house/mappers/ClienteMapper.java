package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.dtos.ClienteDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface ClienteMapper {

    @Mapping(target = "authorities", ignore = true)
    Cliente toCliente(ClienteDto clienteDto);

    @Mapping(target = "password2", ignore = true)
    ClienteDto toClienteDto(Cliente cliente);

}