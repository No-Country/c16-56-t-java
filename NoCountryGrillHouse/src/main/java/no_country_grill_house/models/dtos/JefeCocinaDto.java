package no_country_grill_house.models.dtos;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no_country_grill_house.models.Direccion;
import no_country_grill_house.models.FotoUsuario;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.validations.PasswordMatches;
import no_country_grill_house.validations.UniqueEmail;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
@UniqueEmail
public class JefeCocinaDto {

    private Long id;
    @NotBlank(message = "El nombre no puede ser nulo o estar vacío")
    @Length(max = 35, message = "El nombre no puede superar los 35 caracteres")
    private String nombre;
    @NotBlank(message = "El email no puede ser nulo o estar vacío")
    @Length(max = 35, message = "El email no puede superar los 35 caracteres")
    @Email(message = "Debe ser un email válido")
    private String email;
    @NotBlank(message = "La contraeña no puede ser nula o estar vacía")
    @Pattern(regexp = "\\S+", message = "La contraseña no puede contener espacios en blanco")
    @Length(min = 8, max = 20, message = "La contraseña debe tener un mínimo de 8 caracteres y un máximo de 20 caracteres")
    private String password;
    @NotBlank(message = "La contraseña no puede ser nula o estar vacía")
    @Pattern(regexp = "\\S+", message = "La contraseña no puede contener espacios en blanco")
    @Length(min = 8, max = 20, message = "La contraseña debe tener un mínimo de 8 caracteres y un máximo de 20 caracteres")
    private String password2;
    @NotBlank(message = "El teléfono no puede ser nulo o estar vacío")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "El teléfono solo puede contener dígitos y opcionalmente un signo de más (+)")
    @Length(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 dígitos")
    private String telefono;
    private LocalDateTime fechaAlta;
    private Boolean alta;
    private Rol rol;
    private Direccion direccion;
    private FotoUsuario foto;

}
