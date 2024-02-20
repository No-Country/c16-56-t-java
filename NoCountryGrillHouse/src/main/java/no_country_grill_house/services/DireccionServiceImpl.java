package no_country_grill_house.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.DireccionMapper;
import no_country_grill_house.models.Direccion;
import no_country_grill_house.models.dtos.DireccionDto;
import no_country_grill_house.repositories.DireccionRepository;

@Service
public class DireccionServiceImpl implements DireccionService {

    @Autowired
    private DireccionRepository repository;

    @Autowired
    private DireccionMapper direccionMapper;

    @Transactional
    @Override
    public DireccionDto create(DireccionDto direccionDto) {
        direccionDto.setAlta(true);
        Direccion direccion = repository.save(direccionMapper.toDireccion(direccionDto));
        return direccionMapper.toDireccionDto(direccion);
    }

    @Override
    public Direccion findById(Long id) {
        Direccion direccion = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Direccion con el id: " + id);
        });
        return direccion;
    }

    @Override
    public List<DireccionDto> findAll() {
        return repository.findAll()
                .stream()
                .map(direccionMapper::toDireccionDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Direccion con el id: " + id);
        });
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public DireccionDto update(Long id, DireccionDto direccionDto) {
        Direccion direccion = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Direccion con el id: " + id);
        });

        if (direccion.getNumero() != null)
            direccion.setNumero(direccionDto.getNumero());
        if (direccion.getCalle() != null)
            direccion.setCalle(direccionDto.getCalle());
        if (direccion.getCiudad() != null)
            direccion.setCiudad(direccionDto.getCiudad());

        repository.save(direccion);
        return direccionMapper.toDireccionDto(direccion);
    }

}