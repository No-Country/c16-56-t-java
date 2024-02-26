package no_country_grill_house.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import no_country_grill_house.exceptions.PasswordMismatchException;
import no_country_grill_house.models.dtos.ClienteDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof ClienteDto) {
            ClienteDto clienteDto = (ClienteDto) obj;
            if (!clienteDto.getPassword().equals(clienteDto.getPassword2())) {
                throw new PasswordMismatchException("Las contraseñas no coinciden");
            }
        } // else if (obj instanceof OtraClaseDto) { } Recordar que luego debo agregar
          // para mis otros Dto
        return true;
    }
}
