package no_country_grill_house.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no_country_grill_house.models.Platillo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleDto {

    private Platillo platillo;
    private Integer cantidad;

}
