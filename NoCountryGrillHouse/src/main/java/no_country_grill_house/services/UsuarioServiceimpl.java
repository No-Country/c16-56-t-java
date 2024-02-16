package no_country_grill_house.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.UsuarioMapper;
import no_country_grill_house.models.Usuario;
import no_country_grill_house.models.dtos.UsuarioDto;
import no_country_grill_house.repositories.UsuarioRepositoryDos;

@Service
public class UsuarioServiceimpl implements UsuarioService {

    @Autowired
    private UsuarioRepositoryDos repository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public UsuarioDto create(UsuarioDto usuarioDto) {
        Usuario usuario = repository.save(usuarioMapper.toUsuario(usuarioDto));
        return usuarioMapper.toUsuarioDto(usuario);
    }

    @Override
    public Usuario findById(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el usuario con el id: " + id);
        });
        return usuario;
    }

    @Override
    public List<UsuarioDto> findAll() {
        return repository.findAll()
                .stream()
                .map(usuarioMapper::toUsuarioDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el usuario con el id: " + id);
        });
        repository.deleteById(id);
    }

    @Override
    public UsuarioDto updateUser(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el usuario con el id: " + id);
        });

        if (usuario.getNombre() != null)
            usuario.setNombre(usuarioDto.getNombre());
        if (usuario.getEmail() != null)
            usuario.setEmail(usuarioDto.getEmail());
        if (usuario.getPassword() != null)
            usuario.setPassword(usuarioDto.getPassword());
        if (usuario.getTelefono() != null)
            usuario.setTelefono(usuarioDto.getTelefono());
        if (usuario.getDireccion() != null)
            usuario.setDireccion(usuarioDto.getDireccion());

        repository.save(usuario);
        return usuarioMapper.toUsuarioDto(usuario);
    }

}
