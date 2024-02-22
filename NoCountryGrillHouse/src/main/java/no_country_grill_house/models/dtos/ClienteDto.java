package no_country_grill_house.models.dtos;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no_country_grill_house.models.Direccion;
import no_country_grill_house.models.FotoUsuario;
import no_country_grill_house.models.Platillo;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.validations.PasswordMatches;
import no_country_grill_house.validations.UniqueEmail;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
@UniqueEmail
public class ClienteDto {

  private Long id;
  @NotBlank(message = "No puede ser nulo o estar vacío")
  @Length(max = 35, message = "No puede superar los 35 caracteres")
  private String nombre;
  @NotBlank(message = "No puede ser nulo o estar vacío")
  @Length(max = 35, message = "No puede superar los 35 caracteres")
  @Email(message = "Debe ser un email válido")
  private String email;
  @NotBlank(message = "No puede ser nulo o estar vacío")
  @Pattern(regexp = "\\S+", message = "La contraseña no puede contener espacios en blanco")
  @Length(min = 8, max = 20, message = "La contraseña debe tener un mínimo de 8 caracteres y un máximo de 20 caracteres")
  private String password;
  @NotBlank(message = "No puede ser nulo o estar vacío")
  @Pattern(regexp = "\\S+", message = "La contraseña no puede contener espacios en blanco")
  @Length(min = 8, max = 20, message = "La contraseña debe tener un mínimo de 8 caracteres y un máximo de 20 caracteres")
  private String password2;
  @NotBlank(message = "No puede ser nulo o estar vacío")
  @Pattern(regexp = "^\\+?[0-9]*$", message = "Solo puede contener dígitos y opcionalmente un signo de más (+)")
  @Length(min = 7, max = 15, message = "Debe tener entre 7 y 15 dígitos")
  private String telefono;
  private LocalDateTime fechaAlta;
  private Boolean alta;
  private Rol rol;
  private Direccion direccion;
  private List<Platillo> favoritos;
  private FotoUsuario foto;

}
