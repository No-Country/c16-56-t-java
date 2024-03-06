package no_country_grill_house.validations;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import no_country_grill_house.exceptions.DuplicateEmailException;
import no_country_grill_house.models.dtos.AdminDto;
import no_country_grill_house.models.dtos.ClienteDto;
import no_country_grill_house.models.dtos.JefeCocinaDto;
import no_country_grill_house.models.dtos.MeseroDto;
import no_country_grill_house.services.EmailVerificationService;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, Object> {

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof ClienteDto) {
            ClienteDto clienteDto = (ClienteDto) obj;
            if (!emailVerificationService.isEmailUnique(clienteDto.getEmail())) {
                throw new DuplicateEmailException("El correo electrónico ya está registrado");
            }
        } else if (obj instanceof AdminDto) {
            AdminDto adminDto = (AdminDto) obj;
            if (!emailVerificationService.isEmailUnique(adminDto.getEmail())) {
                throw new DuplicateEmailException("El correo electrónico ya está registrado");
            }
        } else if (obj instanceof JefeCocinaDto) {
            JefeCocinaDto jefeCocinaDto = (JefeCocinaDto) obj;
            if (!emailVerificationService.isEmailUnique(jefeCocinaDto.getEmail())) {
                throw new DuplicateEmailException("El correo electrónico ya está registrado");
            }
        } else if (obj instanceof MeseroDto) {
            MeseroDto meseroDto = (MeseroDto) obj;
            if (!emailVerificationService.isEmailUnique(meseroDto.getEmail())) {
                throw new DuplicateEmailException("El correo electrónico ya está registrado");
            }
        }

        return true;
    }
}
