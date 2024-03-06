package no_country_grill_house.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDireccionDto {
    private ClienteDto clienteDto;
    private DireccionDto direccionDto;
}
