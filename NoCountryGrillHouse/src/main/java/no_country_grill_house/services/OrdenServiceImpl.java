package no_country_grill_house.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.OrdenMapper;
import no_country_grill_house.models.Orden;
import no_country_grill_house.models.dtos.OrdenDto;
import no_country_grill_house.repositories.OrdenRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class OrdenServiceImpl implements OrdenService {
    @Autowired
    private OrdenRepository repository;
    @Autowired
    private OrdenMapper ordenMapper;

    @Transactional
    @Override
    public OrdenDto create(OrdenDto ordenDto) {
        Orden orden = repository.save(ordenMapper.toOrden(ordenDto));
        return ordenMapper.tOrdenDto(orden);
    }

    @Override
    public Orden findById(Long id) {
        Orden orden = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe una orden con ID: " + id);
        });
        return orden;
    }

    @Override
    public List<OrdenDto> findAll() {
        return repository.findAll()
                .stream()
                .map(ordenMapper::tOrdenDto)
                .collect(Collectors.toList());
    }

}
