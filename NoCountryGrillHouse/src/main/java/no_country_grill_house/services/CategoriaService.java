package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.Categoria;
import no_country_grill_house.models.dtos.CategoriaDto;

public interface CategoriaService {

    CategoriaDto create(CategoriaDto categoriaDto);

    Categoria findById(Long id);

    List<CategoriaDto> findAll();

    void deleteById(Long id);

    CategoriaDto update(Long id, CategoriaDto categoriaDto);

}