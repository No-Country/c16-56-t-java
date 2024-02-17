package no_country_grill_house.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDireccionDto {
    private ClienteDto clienteDto;
    private DireccionDto direccionDto;
}
