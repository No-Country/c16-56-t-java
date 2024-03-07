package no_country_grill_house.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no_country_grill_house.models.Orden;
import no_country_grill_house.models.Platillo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrdenDto {

    private Long idDetalle;
    private int cantidad;
    private Orden orden;
    private Platillo platillo;

}
