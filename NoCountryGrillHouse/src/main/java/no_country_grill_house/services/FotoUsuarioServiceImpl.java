package no_country_grill_house.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.FotoUsuarioMapper;
import no_country_grill_house.models.FotoUsuario;
import no_country_grill_house.models.dtos.FotoUsuarioDto;
import no_country_grill_house.repositories.FotoUsuarioRepository;

@Service
public class FotoUsuarioServiceImpl implements FotoUsuarioService {

    @Autowired
    private FotoUsuarioRepository repository;

    @Autowired
    private FotoUsuarioMapper fotoUsuarioMapper;

    @Transactional
    @Override
    public FotoUsuarioDto create(FotoUsuarioDto fotoUsuarioDto) {
        FotoUsuario fotoUsuario = repository.save(fotoUsuarioMapper.toFotoUsuario(fotoUsuarioDto));
        return fotoUsuarioMapper.toFotoUsuarioDto(fotoUsuario);
    }

    @Override
    public FotoUsuario findById(Long id) {
        FotoUsuario fotoUsuario = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Foto del Usuario con el id: " + id);
        });
        return fotoUsuario;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Foto del Usuario con el id: " + id);
        });
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public FotoUsuarioDto update(Long id, FotoUsuarioDto fotoUsuarioDto) {
        FotoUsuario fotoUsuario = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la Foto del Usuario con el id: " + id);
        });

        if (fotoUsuario.getUrl() != null)
            fotoUsuario.setUrl(fotoUsuarioDto.getUrl());
        if (fotoUsuario.getNombre() != null)
            fotoUsuario.setNombre(fotoUsuarioDto.getNombre());
        if (fotoUsuario.getFotoId() != null)
            fotoUsuario.setFotoId(fotoUsuarioDto.getFotoId());

        repository.save(fotoUsuario);
        return fotoUsuarioMapper.toFotoUsuarioDto(fotoUsuario);
    }

}
