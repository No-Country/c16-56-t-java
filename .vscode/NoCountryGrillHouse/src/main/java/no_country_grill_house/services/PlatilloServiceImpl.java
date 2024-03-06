package no_country_grill_house.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.FotoPlatilloMapper;
import no_country_grill_house.mappers.PlatilloMapper;
import no_country_grill_house.models.Categoria;
import no_country_grill_house.models.Platillo;
import no_country_grill_house.models.dtos.FotoPlatilloDto;
import no_country_grill_house.models.dtos.PlatilloDto;
import no_country_grill_house.repositories.PlatilloRepository;

@Service
public class PlatilloServiceImpl implements PlatilloService {

    @Autowired
    private PlatilloRepository repository;

    @Autowired
    private PlatilloMapper platilloMapper;

    @Autowired
    private FotoPlatilloMapper fotoPlatilloMapper;

    @Transactional
    @Override
    public PlatilloDto create(PlatilloDto platilloDto) {
        platilloDto.setAlta(true);
        Platillo platillo = repository.save(platilloMapper.toPlatillo(platilloDto));
        return platilloMapper.toPlatilloDto(platillo);
    }

    @Override
    public PlatilloDto findById(Long id) {
        Platillo platillo = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el Platillo con el id: " + id);
        });
        return platilloMapper.toPlatilloDto(platillo);
    }

    @Override
    public List<PlatilloDto> findAll() {
        return repository.findAll()
                .stream()
                .map(platilloMapper::toPlatilloDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlatilloDto> findByCategoria(Categoria categoria) {
        return repository.findByCategoria(categoria)
                .stream()
                .map(platilloMapper::toPlatilloDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el Platillo con el id: " + id);
        });
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void softDeleteById(Long id) {
        Platillo platillo = repository.findById(id)
                .orElseThrow(() -> new GrillHouseException("No existe el Platillo con el id: " + id));
        platillo.setAlta(false);
        repository.save(platillo);
    }

    @Transactional
    @Override
    public void alta(Long id) {
        Platillo platillo = platilloMapper.toPlatillo(findById(id));
        platillo.setAlta(true);
        repository.save(platillo);
    }

    @Transactional
    @Override
    public PlatilloDto update(Long id, PlatilloDto platilloDto) {
        Platillo platillo = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el Platillo con el id: " + id);
        });

        if (platilloDto.getNombre() != null)
            platillo.setNombre(platilloDto.getNombre());
        if (platilloDto.getDescripcion() != null)
            platillo.setDescripcion(platilloDto.getDescripcion());
        if (platilloDto.getPrecio() != null)
            platillo.setPrecio(platilloDto.getPrecio());
        if (platilloDto.getTiempoEstimado() != null)
            platillo.setTiempoEstimado(platilloDto.getTiempoEstimado());
        if (platilloDto.getCategoria() != null)
            platillo.setCategoria(platilloDto.getCategoria());

        repository.save(platillo);
        return platilloMapper.toPlatilloDto(platillo);
    }

    public void guardarFotoPerfil(Long id, FotoPlatilloDto fotoPlatilloDto) {
        Platillo platillo = platilloMapper.toPlatillo(findById(id));
        platillo.setFoto(fotoPlatilloMapper.toFotoPlatillo(fotoPlatilloDto));
        repository.save(platillo);
    }

}
