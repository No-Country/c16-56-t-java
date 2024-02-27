package no_country_grill_house.models.dtos;

import lombok.*;
import no_country_grill_house.models.Orden;
import no_country_grill_house.models.Platillo;
import no_country_grill_house.models.enums.Estado_Platillo;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalleOrdenDto {
    private Long idDetalle;
    private int cantidad;
    private Estado_Platillo estado_Platillo;
    private Orden orden;
    private Platillo platillo;

}
