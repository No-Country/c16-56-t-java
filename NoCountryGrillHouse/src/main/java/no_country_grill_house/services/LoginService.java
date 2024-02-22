package no_country_grill_house.services;

import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.LoginRequest;

public interface LoginService {

    AuthResponse login(LoginRequest loginRequest);

}
