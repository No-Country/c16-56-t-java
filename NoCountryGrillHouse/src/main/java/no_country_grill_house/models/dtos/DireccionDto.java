package no_country_grill_house.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DireccionDto {

    private Long id;
    @NotBlank(message = "La calle no puede ser nula o estar vacía")
    private String calle;
    @NotBlank(message = "El número no puede ser nulo o estar vacío")
    private String numero;
    @NotBlank(message = "La ciudad no puede ser nula o estar vacía")
    private String ciudad;
    private Boolean alta;

}
