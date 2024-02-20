package no_country_grill_house.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.CategoriaMapper;
import no_country_grill_house.models.Categoria;
import no_country_grill_house.models.dtos.CategoriaDto;
import no_country_grill_house.repositories.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Transactional
    @Override
    public CategoriaDto create(CategoriaDto categoriaDto) {
        Categoria categoria = repository.save(categoriaMapper.toCategoria(categoriaDto));
        return categoriaMapper.toCategoriaDto(categoria);
    }

    @Override
    public Categoria findById(Long id) {
        Categoria categoria = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Categoria con el id: " + id);
        });
        return categoria;
    }

    @Override
    public List<CategoriaDto> findAll() {
        return repository.findAll()
                .stream()
                .map(categoriaMapper::toCategoriaDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Categoria con el id: " + id);
        });
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public CategoriaDto update(Long id, CategoriaDto categoriaDto) {
        Categoria categoria = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Categoria con el id: " + id);
        });

        if (categoria.getNombre() != null)
            categoria.setNombre(categoriaDto.getNombre());

        repository.save(categoria);
        return categoriaMapper.toCategoriaDto(categoria);
    }

}