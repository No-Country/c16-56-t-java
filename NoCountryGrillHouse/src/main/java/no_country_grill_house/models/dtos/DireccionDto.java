package no_country_grill_house.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DireccionDto {

    private Long id;
    private String calle;
    private String numero;
    private String ciudad;
    private Boolean alta;

}
