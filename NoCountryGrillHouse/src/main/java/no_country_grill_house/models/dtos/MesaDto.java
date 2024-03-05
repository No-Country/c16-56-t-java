package no_country_grill_house.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no_country_grill_house.models.Mesero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MesaDto {

    private Long id;
    private Integer numero;
    private Integer cantidadPersonas;
    private Boolean alta;
    private Mesero mesero;

}
