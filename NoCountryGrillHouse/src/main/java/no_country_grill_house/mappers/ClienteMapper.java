package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.dtos.ClienteDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface ClienteMapper {

    Cliente toCliente(ClienteDto clienteDto);

    ClienteDto toClienteDto(Cliente cliente);
}