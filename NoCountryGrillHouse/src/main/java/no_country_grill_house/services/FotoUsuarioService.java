package no_country_grill_house.services;

import no_country_grill_house.models.FotoUsuario;
import no_country_grill_house.models.dtos.FotoUsuarioDto;

public interface FotoUsuarioService {

    FotoUsuarioDto create(FotoUsuarioDto fotoUsuarioDto);

    FotoUsuario findById(Long id);

    void deleteById(Long id);

    FotoUsuarioDto update(Long id, FotoUsuarioDto fotoUsuarioDto);

}
