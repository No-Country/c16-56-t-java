package no_country_grill_house.services;

import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.dtos.AdminDto;

public interface AdminService {

    AuthResponse create(AdminDto adminDto);

    AdminDto findById(Long id);

    AdminDto update(Long id, AdminDto adminDto);

    AdminDto findByEmail(String email);
}
