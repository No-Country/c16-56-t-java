package no_country_grill_house.services;

import java.util.List;

import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.dtos.JefeCocinaDto;
import no_country_grill_house.models.dtos.PasswordDto;

public interface JefeCocinaService {

    AuthResponse create(JefeCocinaDto jefeCocinaDto);

    JefeCocinaDto findById(Long id);

    List<JefeCocinaDto> findAll();

    void deleteById(Long id);

    void softDeleteById(Long id);

    void alta(Long id);

    JefeCocinaDto update(Long id, JefeCocinaDto jefeCocinaDto);

    JefeCocinaDto findByEmail(String email);

    void modificarPassword(PasswordDto passwordDto);
}
