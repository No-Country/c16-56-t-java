package no_country_grill_house.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequestDto {

    private String email;
    private String nombre;
    private String telefono;
    private String calle;
    private String numero;
    private String ciudad;
    private Long id;

}
