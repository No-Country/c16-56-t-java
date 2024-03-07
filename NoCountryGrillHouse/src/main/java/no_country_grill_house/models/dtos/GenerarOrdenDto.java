package no_country_grill_house.models.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerarOrdenDto {

    private String email;
    private Integer cantidadPersonas;
    private List<DetalleDto> detalles;

}
