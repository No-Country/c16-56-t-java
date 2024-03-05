package no_country_grill_house.services;

import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.dtos.AdminDto;
import no_country_grill_house.models.dtos.PasswordDto;

public interface AdminService {

    AuthResponse create(AdminDto adminDto);

    AdminDto findById(Long id);

    AdminDto update(Long id, AdminDto adminDto);

    AdminDto findByEmail(String email);

    void modificarPassword(PasswordDto passwordDto);
}
