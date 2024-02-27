package no_country_grill_house.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDto {

    private Long id;
    @NotBlank(message = "El nombre de la categoría no puede ser nulo o estar vacío")
    private String nombre;
    private Boolean alta;

}
