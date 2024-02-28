package no_country_grill_house.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.DetalleOrdenMapper;
import no_country_grill_house.models.DetalleOrden;
import no_country_grill_house.models.dtos.DetalleOrdenDto;
import no_country_grill_house.repositories.DetalleOrdenRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DetalleOrdenServiceImpl implements DetalleOrdenService {

    @Autowired
    DetalleOrdenRepository repository;
    @Autowired
    DetalleOrdenMapper detalleOrdenMapper;

    @Override
    public DetalleOrdenDto create(DetalleOrdenDto detalleOrdenDto) {
        DetalleOrden detalleOrden = repository.save(detalleOrdenMapper.toDetalleOrden(detalleOrdenDto));
        return detalleOrdenMapper.toDetalleOrdenDto(detalleOrden);
    }

    @Override
    public DetalleOrden findById(Long id) {
        DetalleOrden detalleOrden = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe Detalle orden con Id: " + id);
        });
        return detalleOrden;

    }

    @Override
    public List<DetalleOrdenDto> findAll() {
        return repository.findAll()
                .stream()
                .map(detalleOrdenMapper::toDetalleOrdenDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(DetalleOrdenDto detalleOrdenDto, Long id) {
        DetalleOrden detalleOrden = this.findById(id);
        if (detalleOrdenDto.getEstado_Platillo() != detalleOrden.getEstado_Platillo()) {
            detalleOrden.setEstado_Platillo(detalleOrdenDto.getEstado_Platillo());
        }

    }

}
