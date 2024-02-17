package no_country_grill_house.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DireccionDto {

    private Long id;
    private String calle;
    private String numero;
    private String ciudad;

}
