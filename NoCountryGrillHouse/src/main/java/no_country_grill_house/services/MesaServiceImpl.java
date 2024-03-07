package no_country_grill_house.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.MesaMapper;
import no_country_grill_house.models.Mesa;
import no_country_grill_house.models.dtos.MesaDto;
import no_country_grill_house.repositories.MesaRepository;

@Service
public class MesaServiceImpl implements MesaService {

    @Autowired
    private MesaRepository repository;

    @Autowired
    private MesaMapper mesaMapper;

    @Transactional
    @Override
    public MesaDto create(MesaDto mesaDto) {
        mesaDto.setAlta(true);
        Mesa mesa = repository.save(mesaMapper.toMesa(mesaDto));
        return mesaMapper.toMesaDto(mesa);
    }

    @Override
    public MesaDto findById(Long id) {
        Mesa mesa = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Mesa con el id: " + id);
        });
        return mesaMapper.toMesaDto(mesa);
    }

    @Override
    public MesaDto findByCantidad(Integer cantidadPersonas) {
        List<MesaDto> mesas = repository.findByCantidadPersonas(cantidadPersonas)
                .stream()
                .map(mesaMapper::toMesaDto)
                .collect(Collectors.toList());
        if (mesas.size() > 0) {
            Mesa mesa = mesaMapper.toMesa(mesas.get(0));
            mesa.setAlta(false);
            repository.save(mesa);
            return mesaMapper.toMesaDto(mesa);
        } else {
            return null;
        }
    }

    @Override
    public List<MesaDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mesaMapper::toMesaDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Mesa con el id: " + id);
        });
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void softDeleteById(Long id) {
        Mesa mesa = repository.findById(id)
                .orElseThrow(() -> new GrillHouseException("No existe la Mesa con el id: " + id));
        mesa.setAlta(false);
        repository.save(mesa);
    }

    @Transactional
    @Override
    public void alta(Long id) {
        Mesa mesa = mesaMapper.toMesa(findById(id));
        mesa.setAlta(true);
        repository.save(mesa);
    }

    @Transactional
    @Override
    public MesaDto update(Long id, MesaDto mesaDto) {
        Mesa mesa = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Mesa con el id: " + id);
        });

        if (mesaDto.getNumero() != null)
            mesa.setNumero(mesaDto.getNumero());
        if (mesaDto.getCantidadPersonas() != null)
            mesa.setCantidadPersonas(mesaDto.getCantidadPersonas());
        if (mesaDto.getMesero() != null)
            mesa.setMesero(mesaDto.getMesero());

        repository.save(mesa);
        return mesaMapper.toMesaDto(mesa);
    }

}
