package no_country_grill_house.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.mappers.DetalleOrdenMapper;
import no_country_grill_house.models.DetalleOrden;
import no_country_grill_house.models.dtos.DetalleOrdenDto;
import no_country_grill_house.repositories.DetalleOrdenRepository;

@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository repository;

    @Autowired
    private DetalleOrdenMapper detalleOrdenMapper;

    @Transactional
    @Override
    public DetalleOrdenDto create(DetalleOrdenDto detalleOrdenDto) {
        DetalleOrden detalleOrden = repository.save(detalleOrdenMapper.toDetalleOrden(detalleOrdenDto));
        return detalleOrdenMapper.toDetalleOrdenDto(detalleOrden);
    }

}
