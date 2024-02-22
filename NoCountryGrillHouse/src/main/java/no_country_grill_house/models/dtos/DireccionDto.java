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
    @NotBlank
    private String calle;
    @NotBlank
    private String numero;
    @NotBlank
    private String ciudad;
    private Boolean alta;

}
