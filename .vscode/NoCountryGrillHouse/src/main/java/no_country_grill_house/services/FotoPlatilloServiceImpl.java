package no_country_grill_house.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.FotoPlatilloMapper;
import no_country_grill_house.models.FotoPlatillo;
import no_country_grill_house.models.dtos.FotoPlatilloDto;
import no_country_grill_house.repositories.FotoPlatilloRepository;

@Service
public class FotoPlatilloServiceImpl implements FotoPlatilloService {

    @Autowired
    private FotoPlatilloRepository repository;

    @Autowired
    private FotoPlatilloMapper fotoPlatilloMapper;

    @Transactional
    @Override
    public FotoPlatilloDto create(FotoPlatilloDto fotoPlatilloDto) {
        FotoPlatillo fotoPlatillo = repository.save(fotoPlatilloMapper.toFotoPlatillo(fotoPlatilloDto));
        return fotoPlatilloMapper.toFotoPlatilloDto(fotoPlatillo);
    }

    @Override
    public FotoPlatillo findById(Long id) {
        FotoPlatillo fotoPlatillo = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Foto del Platillo con el id: " + id);
        });
        return fotoPlatillo;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Foto del Platillo con el id: " + id);
        });
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public FotoPlatilloDto update(Long id, FotoPlatilloDto fotoPlatilloDto) {
        FotoPlatillo fotoPlatillo = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Foto del Platillo con el id: " + id);
        });

        if (fotoPlatillo.getUrl() != null)
            fotoPlatillo.setUrl(fotoPlatilloDto.getUrl());
        if (fotoPlatillo.getNombre() != null)
            fotoPlatillo.setNombre(fotoPlatilloDto.getNombre());
        if (fotoPlatillo.getFotoId() != null)
            fotoPlatillo.setFotoId(fotoPlatilloDto.getFotoId());

        repository.save(fotoPlatillo);
        return fotoPlatilloMapper.toFotoPlatilloDto(fotoPlatillo);
    }

}
