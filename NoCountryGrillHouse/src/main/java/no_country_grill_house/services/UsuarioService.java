package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.Usuario;
import no_country_grill_house.models.dtos.UsuarioDto;

public interface UsuarioService {

    UsuarioDto create(UsuarioDto usuario);

    Usuario findById(Long id);

    List<UsuarioDto> findAll();

    void deleteById(Long id);

    UsuarioDto updateUser(Long id, UsuarioDto usuarioDto);

}
