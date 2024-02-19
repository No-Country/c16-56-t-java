package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.Categoria;
import no_country_grill_house.models.dtos.CategoriaDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface CategoriaMapper {

    Categoria toCategoria(CategoriaDto categoriaDto);

    CategoriaDto toCategoriaDto(Categoria categoria);

}
