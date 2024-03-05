package no_country_grill_house.models.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordDto {

    private String email;
    private String passwordActual;
    @NotBlank(message = "La contraeña no puede ser nula o estar vacía")
    @Pattern(regexp = "\\S+", message = "La contraseña no puede contener espacios en blanco")
    @Length(min = 8, max = 20, message = "La contraseña debe tener un mínimo de 8 caracteres y un máximo de 20 caracteres")
    private String password1;
    @NotBlank(message = "La contraeña no puede ser nula o estar vacía")
    @Pattern(regexp = "\\S+", message = "La contraseña no puede contener espacios en blanco")
    @Length(min = 8, max = 20, message = "La contraseña debe tener un mínimo de 8 caracteres y un máximo de 20 caracteres")
    private String password2;

}